/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 03/20/2019
 *Cinco Computer Consultants (CCC) project
 * 
 * This class helps me to load InvoiceData data from database and 
 * put it into a list for future process
 */
package loadMySQLDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.cinco.Person;

import customerData.CustomerData;
import databaseConnect.DataConnector;
import invoice.InvoiceData;
import invoice.ProductOrder;
import productData.Licenses;
import productData.ProductData;
import readFile.ReadCustomerFile;
import readFile.ReadInvoiceFile;
import readFile.ReadPersonFile;
import readFile.ReadProductDataFile;

public class LoadInvoiceData {
	// use the log4j to help me track the error layer
	public static Logger log = Logger.getLogger(LoadInvoiceData.class);

	public static List<InvoiceData> loadInvoiceData() {
		// call the function I made connect to Mysql database
		Connection conn = DataConnector.dataConnectionFunction();
		// create a list for InvoiceData to store all the data
		List<InvoiceData> invoice = new ArrayList<>();
		// simply error check before load data
		if (LoadPersonData.loadPersonData() == null) {
			log.info("Loading all the Person data");
			log.debug("Person data might not properly load into database");
		} else if (LoadCustomerData.loadCustomerData() == null) {
			log.info("Loading all the Customer data");
			log.debug("Customer data might not properly load into database");
		} else if (LoadProductData.loadProductData()==null) {
			log.info("Loading all the Product data");
			log.debug("Product data might not properly load into database");
		}
		// call function I made put all person object into a hash map for future process
		HashMap<String, Person> salesPersonHashMap = ReadPersonFile.perosnListToMap(LoadPersonData.loadPersonData());
		// call function I made put all CustomerData object into a hash map for future
		// process
		HashMap<String, CustomerData> customerHashMap = ReadCustomerFile
				.customerListToMap(LoadCustomerData.loadCustomerData());
		// call function I made put all ProductData object into a hash map for future
		// process
		HashMap<String, ProductData> productHashMap = ReadProductDataFile
				.productListToMap(LoadProductData.loadProductData());
		/*
		 * This part I just get half information of Invoice data (invoiceCode,
		 * personCode, customerCode) In next part, i will get the productOrder in
		 * invoice data
		 */
		// a query retrieve (invoiceCode, personCode, customerCode) from database
		String query = "SELECT  i.invoiceCode,  p.personCode,   c.customerCode FROM Invoice i "
				+ "JOIN Person p  ON i.personId = p.personId JOIN Customer c  ON i.customerId = c.customerId";
		PreparedStatement ps = null;
		ResultSet rs = null;
		// use try catch block to execute query
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			// load all the invoice data (invoiceCode, personCode, customerCode) information
			while (rs.next()) {
				String invoiceCode = rs.getString("invoiceCode").trim();
				String personCode = rs.getString("personCode").trim();
				String customerCode = rs.getString("customerCode").trim();
				// set the productOrder list null first for future process
				InvoiceData item = new InvoiceData(invoiceCode, customerHashMap.get(customerCode),
						salesPersonHashMap.get(personCode), null, 0);
				invoice.add(item);
			}
		} catch (SQLException e) {
			log.info("Retrieving all (invoiceCode, personCode, customerCode) information each InvoiceData.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when retrieve information during this process");
			throw new RuntimeException(e);
		}
		/*
		 * This is the second part to get the productOrder in invoice data
		 */
		// a query to get detail product information for each piece of invoice
		query = "SELECT p.productCode  ,pro.units  ,pro.startDate, pro.endDate "
				+ "FROM ProductOrder pro JOIN Invoice i ON pro.invoiceId = i.invoiceId "
				+ " JOIN Product p ON pro.productId = p.productId WHERE i.invoiceCode = ?";
		// use try catch block to execute query
		try {
			ps = conn.prepareStatement(query);
			// use for loop for each invoice data
			for (InvoiceData invoices : invoice) {
				// create a individual productOrders list for each invoice
				List<ProductOrder> productOrders = new ArrayList<>();
				ps.setString(1, invoices.getInvoiceCode());
				rs = ps.executeQuery();
				// loop through all the productOrder information
				while (rs.next()) {
					String productCode = rs.getString("productCode");
					// check if product is Liences
					if (productHashMap.get(productCode) instanceof Licenses) {
						String startDate = rs.getString("startDate").trim();
						String endDate = rs.getString("endDate").trim();
						// calling the function I made to get the units
						double units = ReadInvoiceFile.parseDateToUnits(startDate, endDate);
						// put data to productOrder list
						ProductOrder item = new ProductOrder(productHashMap.get(productCode), units);
						productOrders.add(item);
					} else {
						// for Equipment and Consultations just get its units
						double units = rs.getDouble("units");
						ProductOrder item = new ProductOrder(productHashMap.get(productCode), units);
						// put data to productOrder list
						productOrders.add(item);
					}
				}
				// put each invoices ProductOrder list in to InvoiceData class
				invoices.setProductOrders(productOrders);
			}
		} catch (SQLException e) {
			log.info("Retrieving all detail infomation  each InvoiceData.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when retrieve information during this process");
			throw new RuntimeException(e);
		}
		// call function to close all the connection
		DataConnector.closeConnection(conn, ps, rs);
		return invoice;
	}

}

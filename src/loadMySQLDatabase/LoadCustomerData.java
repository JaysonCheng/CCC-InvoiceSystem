/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 03/20/2019
 *Cinco Computer Consultants (CCC) project
 * 
 * This class helps me to load Customer data from database and 
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

import com.cinco.Address;
import com.cinco.Person;

import customerData.Corporate;
import customerData.CustomerData;
import customerData.Government;
import databaseConnect.DataConnector;
import readFile.ReadPersonFile;

public class LoadCustomerData {
	// use the log4j to help me track the error layer
	public static Logger log = Logger.getLogger(LoadCustomerData.class);

	public static List<CustomerData> loadCustomerData() {
		// call function I made put all person object into a hash map for future process
		HashMap<String, Person> primaryContactInfoHashMap = ReadPersonFile
				.perosnListToMap(LoadPersonData.loadPersonData());
		// call the function I made connect to Mysql database
		Connection conn = DataConnector.dataConnectionFunction();
		// a query retrieve (customerCode, type, primaryContact, fullName, address and
		// primaryContactInfo) from database
		String query = "SELECT "
				+ "  c.customerCode,  c.type,  c.primaryContact,  c.fullName,  a.street,  a.city,  a.STATE,  a.zip,  a.country "
				+ "FROM Customer c JOIN Address a  ON c.addressId = a.addressId";
		PreparedStatement ps = null;
		ResultSet rs = null;
		// create customer list to store all the customer object
		List<CustomerData> customer = new ArrayList<>();
		// use try catch block to execute query
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			// load all the customer detail information
			while (rs.next()) {
				String customerCode = rs.getString("customerCode").trim();
				String type = rs.getString("type").trim();
				String primaryContact = rs.getString("primaryContact").trim();
				String fullName = rs.getString("fullName").trim();
				String street = rs.getString("street").trim();
				String city = rs.getString("city").trim();
				String STATE = rs.getString("STATE").trim();
				String zip = rs.getString("zip").trim();
				String country = rs.getString("country").trim();
				Address address = new Address(street, city, STATE, zip, country);
				// check which type is this customer if he is Government put it into Government
				// class(subclass of CustomerData)
				if (type.equals("G")) {
					Government item = new Government(customerCode, primaryContact, fullName, address,
							primaryContactInfoHashMap.get(primaryContact), type);
					// add data to the Customer list
					customer.add(item);
					// check which type is this customer if he is Corporate put it into Corporate
					// class(subclass of CustomerData)
				} else if (type.equals("C")) {
					Corporate item = new Corporate(customerCode, primaryContact, fullName, address,
							primaryContactInfoHashMap.get(primaryContact), type);
					customer.add(item);
				}

			}
		} catch (SQLException e) {
			log.info("Retrieving all Email Address field each Person.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when retrieve email address this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
		return customer;
	}

}

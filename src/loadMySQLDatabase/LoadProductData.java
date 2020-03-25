/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 03/20/2019
 *Cinco Computer Consultants (CCC) project
 * 
 * This class helps me to load Product data from database and 
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

import databaseConnect.DataConnector;
import productData.Consultations;
import productData.Equipment;
import productData.Licenses;
import productData.ProductData;
import readFile.ReadPersonFile;

public class LoadProductData {
	// use the log4j to help me track the error layer
	public static Logger log = Logger.getLogger(LoadProductData.class);

	public static List<ProductData> loadProductData() {
		// call function I made put all person object into a hash map for future process
		HashMap<String, Person> consultantHashMap = ReadPersonFile.perosnListToMap(LoadPersonData.loadPersonData());
		// call the function I made connect to Mysql database
		Connection conn = DataConnector.dataConnectionFunction();
		// a query retrieve (productCode, type, name, pricePerUnit, serviceFee
		// annualLicenseFee, consultantPersonCode , hourlyFee from database
		String query = "SELECT "
				+ "  p.productCode, p.type,  p.name,  p.pricePerUnit,   p.serviceFee, p.annualLicenseFee,  "
				+ "p.consultantPersonCode, p.hourlyFee FROM Product p";

		PreparedStatement ps = null;
		ResultSet rs = null;
		// create ProductData list to store all the data
		List<ProductData> productData = new ArrayList<>();
		// use try catch block to execute query
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			// load all the ProductData detail information
			while (rs.next()) {
				String productCode = rs.getString("productCode").trim();
				String type = rs.getString("type").trim();
				String name = rs.getString("name").trim();
				/*
				 * This part is for after I load the data from database check which type is the
				 * product and put them into specific class (subclass of ProductData class)
				 */
				// if it is Equipment, put it into Equipment class
				if (type.equals("E")) {
					double pricePerUnit = rs.getDouble("pricePerUnit");
					Equipment item = new Equipment(productCode, type, name, pricePerUnit);
					// add this data to the equipment (master) list
					productData.add(item);
					// if it is Licenses, put it into Licenses class
				} else if (type.equals("L")) {
					double serviceFee = rs.getDouble("serviceFee");
					double annualLicenseFee = rs.getDouble("annualLicenseFee");
					Licenses item = new Licenses(productCode, type, name, serviceFee, annualLicenseFee);
					// add this data to the licenses (master) list
					productData.add(item);
				}
				// if it is Consultations, put it into Consultations class
				if (type.equals("C")) {
					double hourlyFee = rs.getDouble("hourlyFee");
					String consultantPersonCode = rs.getString("consultantPersonCode").trim();
					Consultations item = new Consultations(productCode, type, name, consultantPersonCode, hourlyFee,
							consultantHashMap.get(consultantPersonCode));
					// add this data to the Consultations (master) list
					productData.add(item);
				}
			}
		} catch (SQLException e) {
			log.info("Retrieving all major field each ProductData.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when retrieve all major field during this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
		return productData;
	}

}

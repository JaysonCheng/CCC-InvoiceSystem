/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 04/9/2019
 *Cinco Computer Consultants (CCC) project
 *
 * This class helps me to interact with the database
 */
package com.cinco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import databaseConnect.DataConnector;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {
	// use the log4j to help me track the error layer
	public static Logger log = Logger.getLogger(InvoiceData.class);

	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
		Connection conn = DataConnector.dataConnectionFunction();
		// retriving all the personCode in the database
		String query = "SELECT personCode FROM Person";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// use PreparedStatement to get the data
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			// using while loop to delete all the relate data for each person
			while (rs.next()) {
				String personCode = rs.getString("personCode");
				// call the removePerson function to delete the
				removePerson(personCode);
			}
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Deleteing all the Person data");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when Deleteing all the Person data this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
		removeAllAddress();
	}

	/**
	 * Removes the person record from the database corresponding to the provided
	 * <code>personCode</code>
	 *
	 * @param personCode
	 */
	public static void removePerson(String personCode) {
		// delete all the customer data relate to each person
		removeAllCustomers();
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// using the personCode to find the personId(primary key) in database
		int personId = getPersonId(personCode);
		// delete each person's email address first
		String query = "DELETE FROM EmailAddress WHERE personid = ? ";
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Deleteing all Email Address field each Person.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when deleting email address for single person this process");
			throw new RuntimeException(e);
		}
		// delete actual person's info by person Id
		query = "DELETE FROM Person WHERE personId = ?";
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			ps.executeUpdate();
		} catch (SQLException e) {
			// error handing and message for user
			log.info("DELETE single person info.....");
			log.debug("Something wrong when DELETE PersonData info process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Method to add a person record to the database with the provided data.
	 *
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city,
			String state, String zip, String country) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// call the function I made add address info to database and get the last
		// AddressForeignKey
		int AddressForeignKey = addAddress(street, city, state, zip, country);
		// insert Person info to database
		String query = "INSERT INTO Person (personCode, firstName, lastName, addressId) VALUES (?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setInt(4, AddressForeignKey);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Trying to add a single Person info");
			log.debug("Something Wrong with the insert Person");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 *
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		// using personCode to get Person Id in database (primary key)
		int personId = getPersonId(personCode);
		PreparedStatement ps = null;
		ResultSet rs = null;
		// insert email address to the specific person
		String query = "INSERT INTO EmailAddress (email, personId) VALUES (?, ?)";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setInt(2, personId);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Adding an Email Address for a specfic Person.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when adding an Email Address for a specfic Person this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Method that removes every customer record from the database
	 */
	public static void removeAllCustomers() {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// get all the customer Id (primary key)in database
		String query = "SELECT c.customerid FROM Customer c ";
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			// using a loop to clear each customer's info
			while (rs.next()) {
				int customerId = rs.getInt("customerId");
				// call removeCustomers to remove each Customer's info
				removeCustomers(customerId);
			}
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Deleting all customer Info field for each Cutsomer.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when delete all customer Info this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Method to add a customer record to the database with the provided data.
	 *
	 * @param customerCode
	 * @param type
	 * @param primaryContactPersonCode
	 * @param name
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addCustomer(String customerCode, String type, String primaryContactPersonCode, String name,
			String street, String city, String state, String zip, String country) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// add all address info to database and get the last AddressForeignKey
		int AddressForeignKey = addAddress(street, city, state, zip, country);
		// using the primaryContactPersonCode(personCode) find personId(primary key) in
		// database
		int personId = getPersonId(primaryContactPersonCode);
		String query = "INSERT INTO Customer (customerCode, type, primaryContact, fullName, addressId, personId) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, customerCode);
			ps.setString(2, type);
			ps.setString(3, primaryContactPersonCode);
			ps.setString(4, name);
			ps.setInt(5, AddressForeignKey);
			ps.setInt(6, personId);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Adding all provided data field for each customer.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when adding all provided data for customer this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Removes all product records from the database
	 */
	public static void removeAllProducts() {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// retrieve all the productCode from product in database
		String query = "SELECT p.productCode FROM Product p";
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			// using loop to delete all the product info in database
			while (rs.next()) {
				String productCode = rs.getString("productCode");
				// using productCode to delete each product in database
				removeProduct(productCode);
			}
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Deleting all major field the Product info.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when deleting all major field the Product this process");
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Removes a particular product record from the database corresponding to the
	 * provided <code>productCode</code>
	 *
	 * @param assetCode
	 */
	public static void removeProduct(String productCode) {
		// using productCode to find the product Id(primary key) database
		int productId = getProductId(productCode);
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// delete the ProductOrder data first (children table of product)
		String query = "DELETE FROM ProductOrder WHERE productId=?";
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, productId);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Deleting specfic major field the ProductOrder info.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when deleting specfic major field of ProductOrder this process");
			throw new RuntimeException(e);
		}
		// delete the Product data second
		query = "DELETE FROM Product WHERE productId=?";
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, productId);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Delete all major field each Product.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when delete all major field each Product this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds an equipment record to the database with the provided data.
	 */
	public static void addEquipment(String productCode, String name, Double pricePerUnit) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// insert all the major info to database
		String query = "INSERT INTO Product (productCode, type, name, pricePerUnit) VALUES (?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			ps.setString(2, "E");
			ps.setString(3, name);
			ps.setDouble(4, pricePerUnit);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Adding all major field each Equipment.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when all major field each Equipment this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds an license record to the database with the provided data.
	 */
	public static void addLicense(String productCode, String name, double serviceFee, double annualFee) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// insert all the major info to database
		String query = "INSERT INTO Product (productCode, type, name, serviceFee, annualLicenseFee) VALUES (?, ?, ? ,?, ?)";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			ps.setString(2, "L");
			ps.setString(3, name);
			ps.setDouble(4, serviceFee);
			ps.setDouble(5, annualFee);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Adding all major field each License.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when adding all major field each License this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds an consultation record to the database with the provided data.
	 */
	public static void addConsultation(String productCode, String name, String consultantPersonCode, Double hourlyFee) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// using the consultantPersonCode find the Person Id(primary key) in Person
		// Table
		int consultantId = getPersonId(consultantPersonCode);
		// insert major info to Product table in database
		String query = "INSERT INTO Product (productCode, type, name,  consultantPersonCode, hourlyFee, consultantId) VALUES ( ?, ?, ?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			ps.setString(2, "C");
			ps.setString(3, name);
			ps.setString(4, consultantPersonCode);
			ps.setDouble(5, hourlyFee);
			ps.setInt(6, consultantId);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Adding all major field each Consultation.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when adding all major field each Consultation this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Removes all invoice records from the database
	 */
	public static void removeAllInvoices() {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// Retrieving all invoiceCode in database
		String query = "SELECT invoiceCode FROM Invoice";
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			// using a loop to delete each invoice data
			while (rs.next()) {
				String invoiceCode = rs.getString("invoiceCode");
				// call removeInvoice to delete each invoice and relate data
				removeInvoice(invoiceCode);
			}
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Deleting all Invoices major field each Invoices.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when deleting all Invoices major field each Invoices this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Removes the invoice record from the database corresponding to the provided
	 * <code>invoiceCode</code>
	 *
	 * @param invoiceCode
	 */
	public static void removeInvoice(String invoiceCode) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// using the invoiceCode to retrieving invoice Id in database
		int invoiceId = getInvoiceId(invoiceCode);
		// remove Product table(child table) first
		removeAllProducts();
		// delete invoice data by invoice Id(primary key)
		String query = "DELETE FROM Invoice WHERE invoiceId = ?";
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, invoiceId);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Deleting Invoices major field each Invoices.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when delete each Invoices this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds an invoice record to the database with the given data.
	 */
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// using salesPersonCode to find the person Id (primary Id) in Person table
		int salesPersonId = getPersonId(salesPersonCode);
		// using customerCode to find the customer Id (primary key) in customer table
		String query = "SELECT c.customerId FROM Customer c WHERE c.customerCode = ?";
		// initialize the customerId for future process
		int customerId = 0;
		try {
			// use PreparedStatement to get the data
			ps = conn.prepareStatement(query);
			ps.setString(1, customerCode);
			rs = ps.executeQuery();
			// get the customer Id
			if (rs.next()) {
				customerId = rs.getInt("customerId");
				// can not find customer Id in database
			} else {
				log.info("it seems we don't have this customer in database");
				log.debug("Please double cheak customerCode ");
			}
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Retrieving get customerId from customer info.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when retrieving get customerId from customer info this process");
			throw new RuntimeException(e);
		}
		// insert major invoice data to invoice table
		query = "INSERT INTO Invoice (invoiceCode, customerId, personId) VALUES (?, ?, ?)";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			ps.setInt(2, customerId);
			ps.setInt(3, salesPersonId);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Adding all major field each Invoice.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when adding all major field each Invoice this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds a particular equipment (corresponding to <code>productCode</code> to an
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of units
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String productCode, int numUnits) {
		/**
		 * call the addConsultationToInvoice since I use productOrder table to connect
		 * invoice table and product table which use (productId ,units and its related
		 * invoice)
		 *
		 * addConsultationToInvoice (String invoiceCode, String, productCode, double
		 * numHours) which can be reuse for this function since they basic do the same
		 * thing: put (productId ,units and its related invoice) into productOrder table
		 *
		 */
		addConsultationToInvoice(invoiceCode, productCode, numUnits);
	}

	/**
	 * Adds a particular equipment (corresponding to <code>productCode</code> to an
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * begin/end dates
	 */
	public static void addLicenseToInvoice(String invoiceCode, String productCode, String startDate, String endDate) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// using product code to find the productId(primary key)in product table
		int productId = getProductId(productCode);
		// using invoiceCode to find the invoiceId(primary key)in invoice table
		int invoiceId = getInvoiceId(invoiceCode);
		// insert major info the ProductOrder table
		String query = "INSERT INTO ProductOrder (productId, startDate, endDate, invoiceId) VALUES ( ?, ?, ?, ?);";
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, productId);
			ps.setString(2, startDate);
			ps.setString(3, endDate);
			ps.setInt(4, invoiceId);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Adding all major info the ProductOrder table for each License.....");
			log.warn("Please check SQL query ");
			log.debug(
					"Something wrong when adding all major info the ProductOrder table for each License this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds a particular equipment (corresponding to <code>productCode</code> to an
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of billable hours.
	 */
	public static void addConsultationToInvoice(String invoiceCode, String productCode, double numHours) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// using product code to find the productId(primary key)in product table
		int productId = getProductId(productCode);
		// using invoiceCode to find the invoiceId(primary key)in invoice table
		int invoiceId = getInvoiceId(invoiceCode);
		// insert major info the ProductOrder table
		String query = "INSERT INTO ProductOrder (productId, units, invoiceId) VALUES ( ?, ?, ?)";
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, productId);
			ps.setDouble(2, numHours);
			ps.setInt(3, invoiceId);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Adding all major info the ProductOrder table for each Consultation.....");
			log.warn("Please check SQL query ");
			log.debug(
					"Something wrong when adding all major info the ProductOrder table for each Consultation this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * This is a helper method to add a Address record to the database with the
	 * provided data.
	 *
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static int addAddress(String street, String city, String state, String zip, String country) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "INSERT INTO Address (street, city, STATE, zip, country) VALUES (?, ?, ?, ?, ?)";
		int foreignKeyId;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.executeUpdate();
			// after insert data get the foreignKeyId
			ps = conn.prepareStatement("SELECT LAST_INSERT_ID()");
			rs = ps.executeQuery();
			rs.next();
			foreignKeyId = rs.getInt("LAST_INSERT_ID()");
			// error handing and message for user
		} catch (SQLException sqle) {
			log.info("Trying to set the Address");
			log.debug("Something Wrong with seting the Address");
			throw new RuntimeException(sqle);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
		// return the last address foreignKey
		return foreignKeyId;
	}

	/**
	 * This is a helper method to get a PersonId record to the database with the
	 * provided data.
	 *
	 * @param personCode
	 *
	 */
	public static int getPersonId(String personCode) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// using the personCode to find the person Id(primary key) in person Table
		String query = "SELECT p.personId FROM Person p WHERE p.personCode = ?";
		// initialize the personId
		int personId = 0;
		try {
			// use PreparedStatement to get the data
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			// check if personCode in database
			if (rs.next()) {
				personId = rs.getInt("personId");
				// if personCode doesn't in database
			} else {
				log.info("it seems we don't have this person in database");
				log.debug("Please double check personCode");
			}
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Retrieving  personId from PersonData info.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when get personId this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
		return personId;
	}

	/**
	 * This is a helper method to get a invoiceId record to the database with the
	 * provided data.
	 *
	 * @param invoiceCode
	 *
	 */
	public static int getInvoiceId(String invoiceCode) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// using the invoiceCode to find the invoice Id(primary key) in invoice Table
		String query = "SELECT i.invoiceId FROM Invoice i WHERE i.invoiceCode =?";
		// initialize the personId
		int invoiceId = 0;
		try {
			// use PreparedStatement to get the data
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			// check if invoiceCode in database
			if (rs.next()) {
				invoiceId = rs.getInt("invoiceId");
				// if invoiceCode doesn't in database
			} else {
				log.info("it seems we don't have this invoice in database");
				log.debug("Please double check invoiceCode");
			}
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Retrieving invoiceId from invoiceCode info.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when retrieving invoiceId from invoiceCode info this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
		return invoiceId;
	}

	/**
	 * This is a helper method to get a productId record to the database with the
	 * provided data.
	 *
	 * @param productCode
	 *
	 */
	public static int getProductId(String productCode) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// using the productCode to find the product Id(primary key) in product Table
		String query = "SELECT p.productId FROM Product p WHERE p.productCode =?";
		// initialize the productId
		int productId = 0;
		try {
			// use PreparedStatement to get the data
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			// check if productCode in database
			if (rs.next()) {
				productId = rs.getInt("productId");
				log.debug("Please double check productCode");
				// if productCode doesn't in database
			} else {
				log.info("it seems we don't have this product in database");
			}
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Retrieving ProductId from Product info.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when retrieving ProductId from Product info this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
		return productId;
	}

	/**
	 * This is a helper method to remove a customer record by customerId
	 *
	 * @param customerId
	 *
	 */
	public static void removeCustomers(int customerId) {
		// clear the relate invoice data info first
		removeAllInvoices();
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// using the customerId(primary key) in table to delete customer info
		String query = "DELETE FROM Customer WHERE customerId=?";
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, customerId);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Deleting single customer info.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when deleting single customer info this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * This is a helper method to remove an single address record by addressId
	 *
	 * @param addressId
	 *
	 */
	public static void removeAddress(int addressId) {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// using the addressId(primary key) in table to delete address info
		String query = "DELETE FROM Address WHERE addressId=?";
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, addressId);
			ps.executeUpdate();
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Deleting single address info.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when deleting single address info this process");
			throw new RuntimeException(e);
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}

	/**
	 * This is a helper method to remove all address record
	 *
	 */
	public static void removeAllAddress() {
		// create connection to database
		Connection conn = DataConnector.dataConnectionFunction();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// retrieve all the addressId from product in database
		String query = "SELECT addressId FROM Address";
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			// using loop to delete all the address info in database
			while (rs.next()) {
				int addressId = rs.getInt("addressId");
				// using addressId to delete each address in database
				removeAddress(addressId);
			}
			// error handing and message for user
		} catch (SQLException e) {
			log.info("Deleting all major field the address info.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when deleting all major field the address info this process");
		}
		// close all the connection
		DataConnector.closeConnection(conn, ps, rs);
	}
}

/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 03/20/2019
 *Cinco Computer Consultants (CCC) project
 * 
 * This class helps me to load Person data from database and 
 * put it into a list for future process
 */
package loadMySQLDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cinco.Address;
import com.cinco.Person;

import databaseConnect.DataConnector;

public class LoadPersonData {
	// use the log4j to help me track the error layer
	public static Logger log = Logger.getLogger(LoadPersonData.class);

	public static List<Person> loadPersonData() {
		// call the function I made connect to Mysql database
		Connection conn = DataConnector.dataConnectionFunction();

		/*
		 * This part I will just get the (personCode,personCode, firstName, lastName,
		 * address) and give email Address list null
		 */
		String query = "SELECT "
				+ "  p.personCode,  p.firstName,  p.lastName,  a.street,  a.city, a.STATE,  a.zip,  a.country "
				+ "  FROM Person p   JOIN Address a  ON p.addressId = a.addressId";
		PreparedStatement ps = null;
		ResultSet rs = null;
		// create a list to store the Person data
		List<Person> person = new ArrayList<Person>();
		// use try-catch block to ensure I can handle the error message
		try {
			// use PreparedStatement to get the data
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			// load all the Person detail information
			while (rs.next()) {
				String personCode = rs.getString("personCode").trim();
				String firstName = rs.getString("firstName").trim();
				String lastName = rs.getString("lastName").trim();
				String street = rs.getString("street").trim();
				String city = rs.getString("city").trim();
				String STATE = rs.getString("STATE").trim();
				String zip = rs.getString("zip").trim();
				String country = rs.getString("country").trim();
				// put all the address information to the Address class
				Address address = new Address(street, city, STATE, zip, country);
				// for the email address list I give null since people will have multiple email
				Person item = new Person(personCode, firstName, lastName, address, null);
				person.add(item);
			}
		} catch (SQLException e) {
			log.info("Retrieving all major field the PersonData info.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when getting (personCode,personCode, firstName, lastName,address) this process");
			throw new RuntimeException(e);
		}
		/*
		 * This part I will take care the multiple email for each person by using a loop
		 * go through all the person in database by using personCode to retrieve all the
		 * email he has
		 */

		query = "SELECT e.email FROM EmailAddress e  JOIN Person p  ON p.personId = e.personId WHERE p.personCode = ?";
		try {
			ps = conn.prepareStatement(query);
			for (Person persons : person) {
				// create a list to store all the email information for each person
				List<String> emailAddress = new ArrayList<>();
				ps.setString(1, persons.getPersonCode());
				rs = ps.executeQuery();
				while (rs.next()) {
					String emails = rs.getString("email").trim();
					// put all the email address to the list
					emailAddress.add(emails);
				}
				// put each person's email address list in to Person class
				persons.setEmailaddress(emailAddress);
			}
		} catch (SQLException e) {
			log.info("Retrieving all Email Address field each Person.....");
			log.warn("Please check SQL query ");
			log.debug("Something wrong when retrieve email address this process");
			throw new RuntimeException(e);
		}

		// call function to close all the connection
		DataConnector.closeConnection(conn, ps, rs);
		return person;
	}
}

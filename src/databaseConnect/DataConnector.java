/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 03/21/2019
 * Cinco Computer Consultants (CCC) project
 * 
 * This class helps me to set up the connection between Mysql database 
 * and my java program
 */
package databaseConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DataConnector {
	// use the log4j to help me track the error layer
	public static Logger log = Logger.getLogger(DataConnector.class);

	// Load the JDBC Driver
	public static Connection dataConnectionFunction() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		// Create a connection to my database
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			// log information here!
			log.error("Cannot connect to server, Please double check your DatabaseInfo class", e);
			throw new RuntimeException(e);
		}

		// return Connection to future reference
		return conn;
	}

	/*
	 * This is the class helps me to close the connection from the database it will
	 * take Connection, PreparedStatement,ResultSet check if it is still connect
	 * then close it
	 */
	public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			// check ResultSet if still on
			if (rs != null && !rs.isClosed())
				rs.close();
			// check PreparedStatement if still on
			if (ps != null && !ps.isClosed())
				ps.close();
			// check Connection if still on
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			log.info("Closeing all the Connection, PreparedStatement, ResultSet....");
			log.warn("Please check Connection, PreparedStatement, ResultSet.... ");
			log.debug("Something wrong when Closeing all the Connection, PreparedStatement, ResultSet this process");
			throw new RuntimeException(e);
		}
	}

}

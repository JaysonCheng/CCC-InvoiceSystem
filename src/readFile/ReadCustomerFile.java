/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Cinco Computer Consultants (CCC) project
 * 
 * 
 * this function is a reader function it helps me read the Customers .dat file
 * and put it into a list for future process and have a function helps me translate list to map 
 *
 */
package readFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.cinco.Address;
import com.cinco.Person;

import customerData.Corporate;
import customerData.CustomerData;
import customerData.Government;

public class ReadCustomerFile {
	public static List<CustomerData> readCustomerFileFunction() {
		String fileName = "data/Customers.dat";
		Scanner s = null;
		// read the file line by line until the end of file
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		// skip the first line
		int k = Integer.parseInt(s.nextLine());
		List<CustomerData> customer = new ArrayList<>();
		// add person list to program to fill the primaryContact Info
		List<Person> primaryContactInfo = ReadPersonFile.readPersonFileFunction();
		// read the file line by line until the end of file
		while (s.hasNext() && customer.size() < k) {
			String line = s.nextLine();
			// token each line by ";"
			String[] token = line.split(";", 5);
			String customerCode = token[0].trim();
			// use trim function to help me for the future compare
			String primaryContact = token[2].trim();
			String name = token[3];
			// token again by"," to get the street,city,state,zip,country
			String[] adress = token[4].split(",", -5);
			// put those data to the address class for future process
			Address address = new Address(adress[0], adress[1], adress[2], adress[3], adress[4]);

			if (token[1].equals("G")) {
				// create a loop using primaryContact code to find the person in the
				// primaryContactInfo list, if I find it, then add it to the customer list
				for (int i = 0; i < primaryContactInfo.size(); i++) {
					// compare the two code
					if (primaryContact.equals((primaryContactInfo.get(i)).getPersonCode())) {
						// put those data into the Customer class
						Government item = new Government(customerCode, primaryContact, name, address,
								primaryContactInfo.get(i), token[1]);
						// add data to the Customer list
						customer.add(item);

					}
				}
			}
			if (token[1].equals("C")) {
				// create a loop using primaryContact code to find the person in the
				// primaryContactInfo list, if I find it, then add it to the customer list
				for (int i = 0; i < primaryContactInfo.size(); i++) {
					// compare the two code
					if (primaryContact.equals((primaryContactInfo.get(i)).getPersonCode())) {
						// put those data into the Customer class
						Corporate item = new Corporate(customerCode, primaryContact, name, address,
								primaryContactInfo.get(i), token[1]);
						// add data to the Customer list
						customer.add(item);

					}
				}
			}

		}

		return customer;
	}

	/**
	 * This is function I made help me put the CustomerData list----->CustomerData
	 * MAP(code, CustomerData) for the future searching and processing
	 */
	public static HashMap<String, CustomerData> customerListToMap(List<CustomerData> customer) {
		HashMap<String, CustomerData> customerHashMap = new HashMap<String, CustomerData>();
		// basic error check to ensure I load the data correctly
		if (customer == null) {
			System.out.println("Please check if load the data correctly......");
			System.exit(0);
		}
		// create the loop all the data I have and put it to Hash map
		for (int i = 0; i < customer.size(); i++) {
			customerHashMap.put(customer.get(i).getCustomerCode(), customer.get(i));
		}
		return customerHashMap;
	}

}
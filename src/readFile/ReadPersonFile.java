/*
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Cinco Computer Consultants (CCC) project
 * 
 * this function is a reader function it helps me read the Persons .dat file and
 * put it into a list for future process and have a function helps me translate list to map 
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

public class ReadPersonFile {

	public static List<Person> readPersonFileFunction() {
		String fileName = "data/Persons.dat";
		// use the try-catch block to read the file
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
			// handle the error
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		// skip the first line
		int k = Integer.parseInt(s.nextLine());
		List<Person> person = new ArrayList<>();
		// read the file line by line until the end of file
		while (s.hasNext() && person.size() < k) {
			String line = s.nextLine();
			// token each line by ";"
			String[] token = line.split(";", 4);
			String personCode = token[0].trim();
			// token again by"," to get the last and first name
			String[] name = token[1].split(",", 2);
			// token again by"," to get the street,city,state,zip,country
			String[] adress = token[2].split(",", 5);
			// put those data to the address class for future process
			Address address = new Address(adress[0], adress[1], adress[2], adress[3], adress[4]);
			// handle when the time the data don't have the email address situation
			if (token.length != 4) {
				// create a string list
				List<String> emailAddress = new ArrayList<>();
				// put the empty string into list
				String email = new String("");
				emailAddress.add(email);
				// put those data into the Person class
				Person item = new Person(personCode, name[0], name[1], address, emailAddress);
				person.add(item);
				// handle when the time the data have multiple email addresse situation
			} else if (token.length == 4) {
				// token again by"," to get whole email
				String[] email = token[3].split(",", -1);
				// create a list for the email address
				List<String> emailAddress = new ArrayList<>();
				// put every email into the email list
				for (int j = 0; j < email.length; j++) {
					emailAddress.add(email[j]);
				}
				// put those data into the Person class
				Person item = new Person(personCode, name[0], name[1], address, emailAddress);
				// add data to the Person list
				person.add(item);
			}
		}

		return person;
	}

	/**
	 * This is function I made help me put the Person list----->Person MAP(code,
	 * Person) for the future searching and processing
	 */
	public static HashMap<String, Person> perosnListToMap(List<Person> person) {
		HashMap<String, Person> personHashMap = new HashMap<String, Person>();
		// basic error check to ensure I load the data correctly
		if (person == null) {
			System.out.println("Please check if load the data correctly......");
			System.exit(0);
		}
		// create the loop all the data I have and put it to Hash map
		for (int i = 0; i < person.size(); i++) {
			personHashMap.put(person.get(i).getPersonCode(), person.get(i));
		}

		return personHashMap;
	}

}

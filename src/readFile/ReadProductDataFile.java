/*
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Assignment 2
 * 
 * this function is a reader function it helps me read the Persons .dat file and
 * put it into a list for future process
 *
 *	
 *
 * this function is a reader function it helps me read the Products .dat file
 * and put it into a list for future process
 * 
 */
package readFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.cinco.Person;

import productData.Consultations;
import productData.Equipment;
import productData.Licenses;
import productData.ProductData;

public class ReadProductDataFile {
	public static List<ProductData> readProductDataFileFunction() {
		String fileName = "data/Products.dat";
		Scanner s = null;
		// use the try-catch block to read the file
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		// skip the first line
		int k = Integer.parseInt(s.nextLine());
		// create the master list for the whole data for futrue process
		List<ProductData> productData = new ArrayList<>();
		// add person list to program to fill the consultant Info
		List<Person> consultant = ReadPersonFile.readPersonFileFunction();
		// read the file line by line until the end of file
		while (s.hasNext() && productData.size() < k) {
			String line = s.nextLine();
			// token each line by ";"
			String[] token = line.split(";", -1);
			// get the general information that those three type data all have
			String productCode = token[0].trim();
			String type = token[1];
			String name = token[2];
			/*
			 * use if statement to check different type data in the file and use different
			 * way to process
			 */
			// when it is equipment, get the pricePerUnit
			if (token[1].equals("E")) {
				double pricePerUnit = Double.parseDouble(token[3]);
				// put those data into Equipment class
				Equipment item = new Equipment(productCode, type, name, pricePerUnit);
				// add this data to the equipment (master) list
				productData.add(item);
				// when it is equipment, get the serviceFee and annualLicenseFee
			} else if (token[1].equals("L")) {
				double serviceFee = Double.parseDouble(token[3]);
				double annualLicenseFee = Double.parseDouble(token[4]);
				// put those data into Licenses class
				Licenses item = new Licenses(productCode, type, name, serviceFee, annualLicenseFee);
				// add this data to the licenses (master) list
				productData.add(item);
				// when it is equipment, get the consultantPersonCode and hourlyFee
			} else if (token[1].equals("C")) {
				String consultantPersonCode = token[3].trim();
				double hourlyFee = Double.parseDouble(token[4]);
				// create a loop using consultantPersonCode code to find the consultant in the
				// consultant list, if I find it, then add it to the Consultations list
				for (int i = 0; i < consultant.size(); i++) {
					if (consultantPersonCode.equals((consultant.get(i)).getPersonCode())) {
						// put those data into Consultations class
						Consultations item = new Consultations(productCode, type, name, consultantPersonCode, hourlyFee,
								consultant.get(i));
						// add this data to the Consultations (master) list
						productData.add(item);
					}
				}
			}
		}

		return productData;
	}

	/**
	 * This is function I made help me put the ProductData list----->ProductData
	 * MAP(code, ProductData) for the future searching and processing
	 */
	public static HashMap<String, ProductData> productListToMap(List<ProductData> product) {
		HashMap<String, ProductData> productHashMap = new HashMap<String, ProductData>();
		// basic error check to ensure I load the data correctly
		if (product == null) {
			System.out.println("Please check if load the data correctly......");
			System.exit(0);
		}
		for (int i = 0; i < product.size(); i++) {
			// create the loop all the data I have and put it to Hash map
			productHashMap.put(product.get(i).getCode(), product.get(i));
		}

		return productHashMap;
	}

}

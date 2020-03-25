/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/20/2019
 * Cinco Computer Consultants (CCC) project
 * 
 * 
 * this function is a reader function it helps me read the Invoices.dat file
 * and put it into a list for future process and have a function helps me translate list to map 
 *
 */
package readFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.cinco.Person;

import customerData.CustomerData;
import invoice.InvoiceData;
import invoice.ProductOrder;
import productData.Licenses;
import productData.ProductData;

public class ReadInvoiceFile {

	public static List<InvoiceData> readInvoiceFileFunction() {
		String fileName = "data/Invoices.dat";
		// use the try-catch block to read the file
		Scanner s = null;
		// read the file line by line until the end of file
		try {
			s = new Scanner(new File(fileName));
			// handle the error
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		// skip the first line
		int k = Integer.parseInt(s.nextLine());
		List<InvoiceData> invoice = new ArrayList<>();
		/*
		 * This part is I calling the function I made and get the Hash map for
		 * Person,CustomerData and ProductData class for future searching
		 */
		HashMap<String, Person> personHashMap = ReadPersonFile.perosnListToMap(ReadPersonFile.readPersonFileFunction());
		HashMap<String, CustomerData> customerHashMap = ReadCustomerFile
				.customerListToMap(ReadCustomerFile.readCustomerFileFunction());
		HashMap<String, ProductData> productHashMap = ReadProductDataFile
				.productListToMap(ReadProductDataFile.readProductDataFileFunction());
		// read the file line by line until the end of file
		while (s.hasNext() && invoice.size() < k) {
			// each time using a new productOrder list
			List<ProductOrder> productOrders = new ArrayList<>();
			String line = s.nextLine();
			// token each line by ";"
			String[] token = line.split(";", 4);
			String invoiceCode = token[0].trim();
			String customerCode = token[1].trim();
			String salesPersonCode = token[2].trim();
			// basic error check
			if (customerHashMap.get(customerCode) == null) {
				System.out.println(
						"Please double check customer.dat file" + customerCode + "not found in customer.dat file");
				System.exit(1);
				// basic error checks
			} else if (personHashMap.get(salesPersonCode) == null) {
				System.out.println(
						"Please double check person.dat file" + salesPersonCode + "not found in person.dat file");
				System.exit(1);
			}
			// token again by"," to get whole products list
			String[] productList = token[3].split(",");
			for (int j = 0; j < productList.length; j++) {
				// token again by ":", to get the productCode and determine with data it is
				String[] productListToken = productList[j].trim().split(":", -1);
				String productCode = productListToken[0];
				// basic error check
				if (productHashMap.get(productCode) == null) {
					System.out.println(
							"Please double check product.dat file" + productCode + "not found in product.dat file");
					System.exit(1);
				}
				// using the code to determine the product type if it is Licenses
				if (productHashMap.get(productCode) instanceof Licenses) {
					// calling the function I made to get the startDate
					double units = parseDateToUnits(productListToken[1],productListToken[2]);
					// put data to productOrder list
					ProductOrder item = new ProductOrder(productHashMap.get(productCode), units);
					productOrders.add(item);
				} else {
					// if data are the other two type of data simply put it in to the
					// productOrders list
					double billingHour = Double.parseDouble(productListToken[1]);
					ProductOrder item = new ProductOrder(productHashMap.get(productCode), billingHour);
					productOrders.add(item);
				}

			}
			// after put all product in to product orders list, put invoiceCode, the
			// customer information , salesPersonCode information and product orders
			// information to the invoice data
			InvoiceData item = new InvoiceData(invoiceCode, customerHashMap.get(customerCode),
					personHashMap.get(salesPersonCode), productOrders, 0);
			invoice.add(item);
		}

		return invoice;
	}

	// This is helping function. It will take a string and translate it to a
	// LocalDate(YYYY-MM-DD) to help me in the productOrder class for Licenses 's
	// billing Hours
	public static double parseDateToUnits(String start,String end) {
		// using the correct format
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		LocalDate startDate = LocalDate.parse(start, dateTimeFormatter);
		LocalDate endDate = LocalDate.parse(end, dateTimeFormatter);
		// calculate the effectiveDates
		long effectiveDates = ChronoUnit.DAYS.between(startDate, endDate);
		// translate effectiveDates to billingHour (Since it is Lience: using the
		// effectiveDates/year)
		double units = effectiveDates / 365.0;
		return units;
	}

}

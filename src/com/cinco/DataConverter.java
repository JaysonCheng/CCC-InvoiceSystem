/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Cinco Computer Consultants (CCC) project
 * 
 * This is the driver for the whole program it call each function in different packeage and 
 * generate XML and Gson file for the user
 */
package com.cinco;

import customerData.CustomerData;
import productData.ProductData;
import readFile.ReadCustomerFile;
import readFile.ReadPersonFile;
import readFile.ReadProductDataFile;
import writeJson.WriteJsonCustomerFile;
import writeJson.WriteJsonPersonFile;
import writeJson.WriteJsonProductsFile;
import writeXML.WriteCustomerXMLFile;
import writeXML.WritePersonXMLFile;
import writeXML.WriteProductsXMLFile;

import java.util.List;

public class DataConverter {

	public static void main(String[] args) {
		/*
		 * This part is I calling all the function I made and generate the all Json file
		 */
		List<Person> person = ReadPersonFile.readPersonFileFunction();
		String personJsonFile = new String("data/Person.json");
		WriteJsonPersonFile.writeJsonPersonFileFunction(person, personJsonFile);
		List<CustomerData> customer = ReadCustomerFile.readCustomerFileFunction();
		String customerJsonFile = new String("data/Customers.json");
		WriteJsonCustomerFile.writeJsonCustomerFileFunction(customer, customerJsonFile);
		List<ProductData> productData = ReadProductDataFile.readProductDataFileFunction();
		String productsJsonFile = new String("data/Products.json");
		WriteJsonProductsFile.writeJsonProductsFileFunction(productData, productsJsonFile);
		
		/*
		 * This part is I calling all the function I made and generate the all Xml file
		 */
		List<Person> personXML = ReadPersonFile.readPersonFileFunction();
		String personXMLFile = new String("data/Persons.xml");
		WritePersonXMLFile.writePersonXMLFileFunction(personXML, personXMLFile);
		List<CustomerData> customerXML = ReadCustomerFile.readCustomerFileFunction();
		String customerXMLFile = new String("data/Customers.xml");
		WriteCustomerXMLFile.writeCustomerXMLFileFunction(customerXML, customerXMLFile);
		List<ProductData> productDataXML = ReadProductDataFile.readProductDataFileFunction();
		String productsXMLFile = new String("data/Products.xml");
		WriteProductsXMLFile.writeProductsXMLFileFunction(productDataXML, productsXMLFile);
	
	}

}

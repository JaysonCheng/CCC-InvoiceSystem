/*
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Assignment 2
 * 
 * this function is a wirter function it helps me write the list of ProductData
 * class data to the .xml file in the given string filename in the data file
 */
package writeXML;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import productData.Consultations;
import productData.Equipment;
import productData.Licenses;
import productData.ProductData;

public class WriteProductsXMLFile {
	public static void writeProductsXMLFileFunction(List<ProductData> productData, String filename) {
		XStream xstream = new XStream();
		xstream.setMode(XStream.NO_REFERENCES);
		// set the alias for the class in my data
		xstream.alias("products", List.class);
		xstream.alias("ProductData", ProductData.class);
		xstream.alias("Equipment", Equipment.class);
		xstream.alias("Licenses", Licenses.class);
		xstream.alias("Consultations", Consultations.class);
		// translate ProductData list to the xml style
		String xml = xstream.toXML(productData);
		try {
			PrintWriter out = new PrintWriter(filename);
			// write the whole data to the json file
			out.println(xml);
			out.close();
			// handle the error
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		}
	}

}

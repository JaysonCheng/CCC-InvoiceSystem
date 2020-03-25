/** Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Assignment 2
 * 
 * this function is a wirter function it helps me write the list of Customer
 * class data to the .xml file in the given string filename in the data file
 */
package writeXML;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import customerData.Corporate;
import customerData.CustomerData;
import customerData.Government;

public class WriteCustomerXMLFile {
	public static void writeCustomerXMLFileFunction(List<CustomerData> customer, String filename) {
		XStream xstream = new XStream();
		xstream.setMode(XStream.NO_REFERENCES);
		// set the alias for the class in my data
		xstream.alias("customers", List.class);
		xstream.alias("companyCustomer", Corporate.class);
		xstream.alias("governmentCustomer", Government.class);
		// translate ProductData list to the xml style
		String xml = xstream.toXML(customer);
		try {
			PrintWriter out = new PrintWriter(filename);
			// write the whole data to the json file
			out.println(xml);
			out.close();
		} catch (FileNotFoundException fnfe) {
			// handle the error
			throw new RuntimeException(fnfe);
		}

	}

}

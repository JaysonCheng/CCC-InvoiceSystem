/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Assignment 2
 * 
 * 
 * this function is a wirter function it helps me write the list of Person class
 * data to the .xml file in the given string filename in the data file
 */
package writeXML;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import com.cinco.Person;
import com.thoughtworks.xstream.XStream;

public class WritePersonXMLFile {
	public static void writePersonXMLFileFunction(List<Person> person, String filename) {

		XStream xstream = new XStream();
		xstream.setMode(XStream.NO_REFERENCES);
		// set the alias for the class in my data
		xstream.alias("persons", List.class);
		xstream.alias("person", Person.class);
		// translate ProductData list to the xml style
		String xml = xstream.toXML(person);
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

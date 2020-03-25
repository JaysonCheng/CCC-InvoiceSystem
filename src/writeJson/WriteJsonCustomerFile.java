/** Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Assignment 2
 * this function is a wirter function it helps me write the list of Customer
 * class data to the .json file in the given string filename in the data file
 */
package writeJson;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import customerData.CustomerData;

public class WriteJsonCustomerFile {
	public static void writeJsonCustomerFileFunction(List<CustomerData> customers, String filename) {
		// set the default for the data
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// translate Customer list to the json style
		String item = gson.toJson(customers);
		try {
			PrintWriter out = new PrintWriter(filename);
			// write the whole data to the json file
			out.println(item);
			out.close();
		} catch (FileNotFoundException fnfe) {
			// handle the error
			throw new RuntimeException(fnfe);
		}

	}
}

/* * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Assignment 2
 * this function is a wirter function it helps me write the list of Person class
 * data to the .json file in the given string filename in the data file
 */
package writeJson;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import com.cinco.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WriteJsonPersonFile {
	public static void writeJsonPersonFileFunction(List<Person> person, String filename) {
		// set the default for the data
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// translate person list to the json style
		String item = gson.toJson(person);
		// write the whole data to the json file
		try {
			PrintWriter out = new PrintWriter(filename);
			out.println(item);
			out.close();
			// handle the error
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		}

	}
}

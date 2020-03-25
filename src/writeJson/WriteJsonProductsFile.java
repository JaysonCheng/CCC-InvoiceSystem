/* * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Assignment 2
 * 
 * this function is a wirter function it helps me write the list of ProductData
 * class data to the .json file in the given string filename in the data file
 */
package writeJson;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import productData.ProductData;

public class WriteJsonProductsFile {
	public static void writeJsonProductsFileFunction(List<ProductData> productData, String filename) {
		// set the default for the data
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// translate ProductData list to the json style
		String item = gson.toJson(productData);

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

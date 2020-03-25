/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Cinco Computer Consultants (CCC) project
 * 
 * This class contains the Government class, in each piece of Data it has customerCode, type,state,primaryContact,
 * name and a address class. In this file, we have setter and getter function and toString to help us 
 */
package customerData;

import com.cinco.Address;
import com.cinco.Person;

public class Government extends CustomerData {
	private String type;

	public Government(String customerCode, String primaryContact, String name, Address address,
			Person primaryContactInfo, String type) {
		super(customerCode, primaryContact, name, address, primaryContactInfo);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Government [type=" + type + ", toString()=" + super.toString() + "]";
	}

	// This is Inherit function from the the abstract function and it means when the
	// customer is Government, we will charge him $125.00
	public double getComplianceFee() {
		return 125.00;
	}

	// This is Inherit function from the the abstract function and it means when the
	// customer is Government, we will charge him $0.00
	public double getTaxes() {
		return 0.00;
	}

}

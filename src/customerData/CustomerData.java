/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Cinco Computer Consultants (CCC) project
 * 
 * This class contains the Customer class, in each piece of Data it has customerCode, type,state,primaryContact,
 * name and a address class. In this file, we have setter and getter function and toString to help us 
 */
package customerData;

import com.cinco.Address;
import com.cinco.Person;

public abstract class CustomerData {

	private String customerCode;
	private String primaryContact;
	private String name;
	private Address address;
	private Person primaryContactInfo;

	public CustomerData(String customerCode, String primaryContact, String name, Address address,
			Person primaryContactInfo) {
		super();
		this.customerCode = customerCode;
		this.primaryContact = primaryContact;
		this.name = name;
		this.address = address;
		this.primaryContactInfo = primaryContactInfo;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Person getPrimaryContactInfo() {
		return primaryContactInfo;
	}

	public void setPrimaryContactInfo(Person primaryContactInfo) {
		this.primaryContactInfo = primaryContactInfo;
	}

	// Return a string representation of the object
	@Override
	public String toString() {
		return "CustomerData [customerCode=" + customerCode + ", primaryContact=" + primaryContact + ", name=" + name
				+ ", address=" + address + ", primaryContactInfo=" + primaryContactInfo + "]";
	}

//this is a abstract function which can helps me calculate the ComplianceFee in their subclasses
	public abstract double getComplianceFee();

// this is a abstract function which can helps me calculate the Taxes in their subclasses
	public abstract double getTaxes();
}

/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Cinco Computer Consultants (CCC) project
 * 
 * This class contains the persons Data class, in each piece of persons Data it has Person Code,Name Address class 
 * and it may has the email address. In this file, we have setter and getter function and toString to help us 
 */
package com.cinco;

import java.util.ArrayList;
import java.util.List;

public class Person {
	private String personCode;
	private String firstName;
	private String lastName;
	Address address;
	List<String> emailaddress = new ArrayList<>();

	public Person(String personCode, String lastName, String firstName, Address address, List<String> emailaddress) {
		super();
		this.personCode = personCode;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.emailaddress = emailaddress;
	}

	public List<String> getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(List<String> emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	// Return a string representation of the object
	@Override
	public String toString() {
		return "Person [personCode=" + personCode + ", firstName=" + firstName + ", lastName=" + lastName + ", address="
				+ address + ", emailaddress=" + emailaddress + "]";
	}

	// This is a convenience function for me to get the Full name of a person
	public String getName() {
		return this.lastName + "," + this.firstName;
	}
}

/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Cinco Computer Consultants (CCC) project
 * 
 * This class contains Product Data and it is subclass for the productData class, 
 * in each piece of Product Data it has Code,Name, type, PersonCode 
 * and hourlyFee In this file, we have setter and getter function and toString to help us 
 */
package productData;

import com.cinco.Person;

public class Consultations extends ProductData {
	private String consultantPersonCode;
	private double hourlyFee;
	private Person consultant;

	public Consultations(String code, String type, String name, String consultantPersonCode, double hourlyFee,
			Person consultant) {
		super(code, type, name);
		this.consultantPersonCode = consultantPersonCode;
		this.hourlyFee = hourlyFee;
		this.consultant = consultant;
	}

	public String getConsultantPersonCode() {
		return consultantPersonCode;
	}

	public void setConsultantPersonCode(String consultantPersonCode) {
		this.consultantPersonCode = consultantPersonCode;
	}

	public double getHourlyFee() {
		return hourlyFee;
	}

	public void setHourlyFee(double hourlyFee) {
		this.hourlyFee = hourlyFee;
	}

	public Person getConsultant() {
		return consultant;
	}

	public void setConsultant(Person consultant) {
		this.consultant = consultant;
	}

	// Return a string representation of the object
	@Override
	public String toString() {
		return "Consultations [toString()=" + super.toString() + ", consultantPersonCode=" + consultantPersonCode
				+ ", hourlyFee=" + hourlyFee + ", consultant=" + consultant + "]";
	}

	// This is Inherit function from the the abstract function and it means when the
	// product is Consultations, we will charge him $150.00
	public double getSericeFee() {
		return 150.00;
	}

	// This is Inherit function from the the abstract function and it means when the
	// product is Consultations,the product tax rate is 4.25%
	public double getProductTaxesRate() {
		return 0.0425;
	}

	// This is Inherit function from the the abstract function and it means when the
	// product is Consultations, we will give this kind of print to show the
	// ProductUsage
	public String getProductUsage() {
		return "hrs  " + this.hourlyFee + "/hr)";
	}
}

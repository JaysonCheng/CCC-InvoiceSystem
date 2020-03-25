/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Cinco Computer Consultants (CCC) project
 * 
 * This class contains Product Data and it is subclass for the productData class,
 * in each piece of Product Data it has Code,Name, type, serviceFee and annualLicenseFee
 *  we have setter and getter function and toString to help us 
 */
package productData;

public class Licenses extends ProductData {
	private double serviceFee;
	private double annualLicenseFee;

	public Licenses(String code, String type, String name, double serviceFee, double annualLicenseFee) {
		super(code, type, name);
		this.serviceFee = serviceFee;
		this.annualLicenseFee = annualLicenseFee;
	}

	public double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public double getAnnualLicenseFee() {
		return annualLicenseFee;
	}

	public void setAnnualLicenseFee(double annualLicenseFee) {
		this.annualLicenseFee = annualLicenseFee;
	}

	// Return a string representation of the object
	@Override
	public String toString() {
		return "Licenses [toString()=" + super.toString() + ", serviceFee=" + serviceFee + ", annualLicenseFee="
				+ annualLicenseFee + "]";
	}

	// This is Inherit function from the the abstract function and it means when the
	// product is Licenses, we will charge him service Fee which in this class
	public double getSericeFee() {
		return this.serviceFee;
	}

	// This is Inherit function from the the abstract function and it means when the
	// product is Licenses,the product tax rate is 4.25%
	public double getProductTaxesRate() {
		return 0.0425;
	}

	// This is Inherit function from the the abstract function and it means when the
	// product is Consultations, we will give this kind of print to show the
	// ProductUsage
	public String getProductUsage() {
		return "days  " + this.annualLicenseFee + "/yr)";
	}
}

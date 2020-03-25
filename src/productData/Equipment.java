/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Cinco Computer Consultants (CCC) project
 * 
 * This class contains Product Data and it is subclass for the productData class,
 * in each piece of Product Data it has Code,Name, type, pricePerUnit
 *  we have setter and getter function and toString to help us 
 */
package productData;

public class Equipment extends ProductData {
	private double pricePerUnit;

	public Equipment(String code, String type, String name, double pricePerUnit) {
		super(code, type, name);
		this.pricePerUnit = pricePerUnit;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	@Override
	public String toString() {
		return "Equipment [toString()=" + super.toString() + ", pricePerUnit=" + pricePerUnit + "]";
	}

	// This is Inherit function from the the abstract function and it means when the
	// product is Consultations, we will charge him $0.00
	public double getSericeFee() {
		return 0.00;
	}

	// This is Inherit function from the the abstract function and it means when the
	// product is Consultations,the product tax rate is 7%
	public double getProductTaxesRate() {
		return 0.07;
	}

	// This is Inherit function from the the abstract function and it means when the
	// product is Equipment, we will give this kind of print to show the
	// ProductUsage
	public String getProductUsage() {
		return "units  " + this.pricePerUnit + "/unit)";
	}
}

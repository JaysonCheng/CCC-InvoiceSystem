/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/08/2019
 * Cinco Computer Consultants (CCC) project
 * 
 * This class contains Product Data and it is super class for the Consultation, Equipment and Licence class,
 * in each piece of Product Data it has Code,Name, type we have setter and getter function and toString to help us 
 */
package productData;

public abstract class ProductData {
	private String productCode;
	private String type;
	private String name;

	public ProductData(String code, String type, String name) {
		super();
		this.productCode = code;
		this.type = type;
		this.name = name;
	}

	public String getCode() {
		return productCode;
	}

	public void setCode(String code) {
		this.productCode = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Return a string representation of the object
	@Override
	public String toString() {
		return "productData [code=" + productCode + ", type=" + type + ", name=" + name + "]";
	}

	// this is a abstract function which can helps me calculate the Serice Fee in
	// their subclasses
	public abstract double getSericeFee();

	// this is a abstract function which can helps me to get the ProductTaxesRate
	// their subclasses
	public abstract double getProductTaxesRate();

	// this is a abstract function which can helps me calculate the ProductUsage in
	// their subclasses
	public abstract String getProductUsage();

}
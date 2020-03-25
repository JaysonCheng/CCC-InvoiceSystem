/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/20/2019
 * Cinco Computer Consultants (CCC) project
 * 
 *  This class contains the productOrder class, in each piece of Invoice Data it has ProductData class,billingHour, 
 *  and it has a subSubtotal to help me finish the calculation.
 *  The reason I made this class is because I found all the product is (unitFee*billingHour) 
 *  This class contains the function helps me calculate the each product's SubSubtotal, taxes and usage function
 */
package invoice;

import productData.Consultations;
import productData.Equipment;
import productData.Licenses;
import productData.ProductData;

public class ProductOrder {
	private ProductData productData;
	private double units;
	private double subSubtotal;

	public ProductOrder(ProductData productData, double units) {
		super();
		this.productData = productData;
		this.units = units;
	}

	public ProductData getProductData() {
		return productData;
	}

	public void setProductData(ProductData productData) {
		this.productData = productData;
	}

	public double getBillingHour() {
		return units;
	}

	public void setBillingHour(double billingHour) {
		this.units = billingHour;
	}

	public double getSubSubtotal() {
		return subSubtotal;
	}

	public void setSubSubtotal(double subSubtotal) {
		this.subSubtotal = subSubtotal;
	}

	@Override
	public String toString() {
		return "productOrder [productData=" + productData + ", billingHour=" + units + ", subSubtotal=" + subSubtotal
				+ "]";
	}

	/*
	 * This function is help me to give the SubSubTotal(each product's total=
	 * (unitPrice * billingHour) for each piece of products. This function will
	 * determine which kind of product it is and calculate the SubSubTotal
	 */
	public double getSubSubTotal() {
		// check if it is Equipment
		if (this.productData instanceof Equipment) {
			this.subSubtotal = ((Equipment) productData).getPricePerUnit() * units;
			return this.subSubtotal;
			// check if it is Licenses
		} else if (this.productData instanceof Licenses) {
			this.subSubtotal = ((Licenses) productData).getAnnualLicenseFee() * units;
			return this.subSubtotal;
			// check if it is Consultations
		} else if (this.productData instanceof Consultations) {
			this.subSubtotal = ((Consultations) productData).getHourlyFee() * units;
			return this.subSubtotal;
		}
		return -1;
	}

	/*
	 * This function is help me to give the SericeFee to each product and it helps
	 * me to do the future calculation
	 */
	public double getSericeFee() {
		return productData.getSericeFee();
	}

	/*
	 * This function is help me to give the SubProductTaxes(each product's Taxes=
	 * ((SubSubTotal(unitPrice * billingHour) * ProductTaxesRate) for each piece of
	 * products. This function will determine which kind of product it is and
	 * calculate the ProductTaxes
	 */
	public double getSubProductTaxes() {
		double taxes = 0;
		taxes = getSubSubTotal() * productData.getProductTaxesRate();
		return taxes;
	}

	/*
	 * This function is help me to give the Usage(billingHour) for each piece of
	 * products. This function will determine which kind of product it is and
	 * calculate the billingHour
	 */
	public String getUsage() {
		String usage = "";
		// if it is a Licenses then using the billingHour*365 to get the human readable
		// days
		if (productData instanceof Licenses) {
			usage = "(" + this.units * 365 + productData.getProductUsage();
			return usage;
			// if they are the other type of the productData just give the normal usage
		} else {
			usage = "(" + this.units + productData.getProductUsage();
		}
		return usage;
	}

}

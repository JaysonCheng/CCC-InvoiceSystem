/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/20/2019
 * Cinco Computer Consultants (CCC) project
 * 
 *  This class contains the InvoiceData class, in each piece of Invoice Data it has invoiceCode,Customer class ,Person class 
 *  and it has productOrders class list(contain the actual product and billing Hour) and this class also has a subtotal
 *  to help me finish the calculation 
 */
package invoice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.cinco.Person;

import customerData.Corporate;
import customerData.CustomerData;
import customerData.Government;

public class InvoiceData {
	private String invoiceCode;
	private CustomerData customer;
	private Person salesPerson;
	private List<ProductOrder> productOrders = new ArrayList<>();
	private double subtotal;

	public InvoiceData(String invoiceCode, CustomerData customer, Person salesPerson, List<ProductOrder> productOrders,
			double subtotal) {
		super();
		this.invoiceCode = invoiceCode;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.productOrders = productOrders;
		this.subtotal = subtotal;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public CustomerData getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerData customer) {
		this.customer = customer;
	}

	public Person getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(Person salesPerson) {
		this.salesPerson = salesPerson;
	}

	public List<ProductOrder> getProductOrders() {
		return productOrders;
	}

	public void setProductOrders(List<ProductOrder> productOrders) {
		this.productOrders = productOrders;
	}

	// Return a string representation of the object
	@Override
	public String toString() {
		return "InvoiceData [invoiceCode=" + invoiceCode + ", customer=" + customer + ", salesPerson=" + salesPerson
				+ ", productOrders=" + productOrders + ", subtotal=" + subtotal + "]";
	}

	/*
	 * This function is help me to give the subtotal for each piece of data. The
	 * function is using loop go through all the product in the productOrder list
	 * and get a subtotal
	 */
	public double getSubTotal() {
		this.subtotal = 0;
		// looping through all the product SubSubTotal and add them together to get the
		// sub total
		for (int i = 0; i < productOrders.size(); i++) {
			this.subtotal = this.subtotal + productOrders.get(i).getSubSubTotal();
		}
		return this.subtotal;
	}

	/*
	 * This function is help me to give the SericeFee for each piece of data. The
	 * function is using loop go through all the product in the productOrder list
	 * and get a getSericeFee and all them together
	 */
	public double getTemporarilyFee() {
		double sum = 0;
		// start loop and get each SericeFee and add them together to get a sum
		for (int i = 0; i < productOrders.size(); i++) {
			sum = sum + productOrders.get(i).getSericeFee();
		}
		return sum;
	}

	/*
	 * This function is help me to give the Compliance Fee for each piece of data.
	 * This function give me each piece of invoice data's Compliance Fee for future
	 * calculation
	 */
	public double getComplianceFee() {
		return customer.getComplianceFee();
	}

	/*
	 * This function is help me to give the Taxes for each piece of data. if the
	 * customer is Government then, he don't need to pay the taxes, while if he is
	 * the Corporate customer he will need to pay the taxes
	 */
	public double getTaxes() {
		double taxes = 0;
		// check if it is a Government customer or not
		if (customer instanceof Government) {
			return customer.getTaxes();
			// check if it is a Corporate customer or not
		} else if (customer instanceof Corporate) {
			for (int i = 0; i < productOrders.size(); i++) {
				// get each taxes and add them together to get the each piece of invoice data's
				// taxes
				taxes = taxes + productOrders.get(i).getSubProductTaxes();
			}
		}
		return taxes;
	}

	/*
	 * This function is help me to give the Each Total for each piece of data. I
	 * just call the previous function I made and add them together to get the Total
	 */
	public double getEachTotal() {
		return getSubTotal() + getTemporarilyFee() + getTaxes() + getComplianceFee();
	}

	/*
	 * A Comparator function helps me sort by the customer name
	 */
	public static Comparator<InvoiceData> compareCustomerByName = new Comparator<InvoiceData>() {
		public int compare(InvoiceData invioice1, InvoiceData invioice2) {
			return invioice1.getCustomer().getName().compareTo(invioice2.getCustomer().getName());
		}
	};

}

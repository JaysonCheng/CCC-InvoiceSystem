/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 02/20/2019
 *Cinco Computer Consultants (CCC) project
 * 
 * This is the driver for the whole program it call each function in different packeages and 
 * generate the report to the users and it also provide the specific individual report for user 
 * to check the number
 */
package com.cinco;

import java.util.Collections;
import java.util.List;
import org.apache.log4j.BasicConfigurator;

import invoice.InvoiceData;
import invoice.ProductOrder;
import loadMySQLDatabase.LoadInvoiceData;

public class InvoiceReport {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		/****
		 * This part of code is helps me to generate a Executive Summary Report for the
		 * users
		 */
		List<InvoiceData> invoices = LoadInvoiceData.loadInvoiceData();
		// call the sort function I made sort data by CustomerByName
		Collections.sort(invoices, InvoiceData.compareCustomerByName);
		// build a string to for the future print
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-8s %-34s %-20s %-20s %-20s %-23s %-22s %-20s\n", "Invoice", "Customer",
				"Salesperson", "Subtotal", "Service Fee", "Compliance Fee", "Taxes", "Total"));
		double finalSubTotal = 0, finalFees = 0, finalTaxes = 0, finalTotal = 0, finalComplianceFee = 0;
		System.out.println("Executive Summary Report");
		System.out.println("=========================");
		// for each invoice data calling the function a made to get the each type of
		// money amount
		for (InvoiceData invocies : invoices) {
			// format their information
			sb.append((String.format("%-8s %-33s %-20s $%-20.2f $%-20.2f $%-20.2f $%-20.2f $%-20.2f\n",
					invocies.getInvoiceCode(), invocies.getCustomer().getName(), invocies.getSalesPerson().getName(),
					invocies.getSubTotal(), invocies.getTemporarilyFee(), invocies.getComplianceFee(),
					invocies.getTaxes(), invocies.getEachTotal())));
			// calculate the final(big) Total for the report
			finalSubTotal = finalSubTotal + invocies.getSubTotal();
			finalFees = finalFees + invocies.getTemporarilyFee();
			finalComplianceFee = finalComplianceFee + invocies.getComplianceFee();
			finalTaxes = finalTaxes + invocies.getTaxes();
			finalTotal = finalTotal + invocies.getEachTotal();
		}
		System.out.print(sb.toString());
		System.out.printf(
				"=========================================================================================================================================================================\n");
		System.out.printf((String.format("%-62s  $%-20.2f $%-20.2f $%-20.2f $%-20.2f $%-20.2f", "TOTAL", finalSubTotal,
				finalFees, finalComplianceFee, finalTaxes, finalTotal)));
		System.out.printf("\n\n");
		/**
		 * this is big loop for the program to print the each piece data information and
		 * give detail information for the users //
		 */
		System.out.printf("Individual Invoice Detail Reports\n");
		System.out.printf("==================================================\n");
		for (InvoiceData detailInvoice : invoices) {
			System.out.println(detailInvoice.getInvoiceCode());
			System.out.println("========================");
			System.out.println("Salesperson: " + detailInvoice.getSalesPerson().getName());
			System.out.println("Customer Info: ");
			System.out.printf("%-5s (%-3s)\n", detailInvoice.getCustomer().getName(),
					detailInvoice.getCustomer().getCustomerCode());
			System.out.printf("%-5s\n", detailInvoice.getCustomer().getPrimaryContactInfo().getName());
			System.out.println(detailInvoice.getCustomer().getAddress());
			System.out.printf("-------------------------------------------\n");
			StringBuilder newString = new StringBuilder();
			newString.append(
					String.format("%-10s %-44s %-32s %-20s %-33s\n", "Code", "Item", "Usage", "Fees", " Total"));
			/***
			 * This is another loop to help me to print the detail information for the each
			 * piece of data information for users which contain the code, name, usage,
			 * SericeFee and getSubSubTotal
			 **/

			for (ProductOrder productOrderInfo : detailInvoice.getProductOrders()) {
				newString.append(String.format("%-10s %-36s %-40s $%-20.2f $%-20.2f\n",
						productOrderInfo.getProductData().getCode(), productOrderInfo.getProductData().getName(),
						productOrderInfo.getUsage(), productOrderInfo.getSericeFee(),
						productOrderInfo.getSubSubTotal()));

			}
			System.out.print(newString.toString());
			System.out.printf(
					"                                              ========================================================================\n");
			System.out.printf("%-110s $%-40.2f \n", "SUB-TOTALS", detailInvoice.getSubTotal());
			System.out.printf("%-110s $%-40.2f \n", "ComplianceFee", detailInvoice.getComplianceFee());
			System.out.printf("%-110s $%-40.2f \n", "TAXES", detailInvoice.getTaxes());
			System.out.printf("%-110s $%-40.2f \n", "FEES", detailInvoice.getTemporarilyFee());
			System.out.printf("%-110s $%-40.2f \n", "TOTAL", detailInvoice.getEachTotal());
			// This part is the end of each piece of data and helps me give enough space for
			// each data
			System.out.printf("\n\n");
		}
	}

}

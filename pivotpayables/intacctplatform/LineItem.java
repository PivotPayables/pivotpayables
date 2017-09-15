/**
 * 
 */
package com.pivotpayables.intacctplatform;

import static java.lang.System.out;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author John
 *
 */
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.pivotpayables.concurplatform.ExpenseEntry;

@XmlRootElement(name="lineitem")
@XmlAccessorType (XmlAccessType.FIELD)
public class LineItem {

	@XmlElement(name = "glaccountno")
	String AccountNumber;
	
	@XmlElement(name = "amount")
	double Amount;
	
	@XmlElement(name = "locationid")
	String LocationID;
	
	
	public void display() {
		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";

		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		
		out.println("Account: " + AccountNumber);
		out.println("Amount: " + decimalFormat.format(Amount));
		out.println("Location ID: " + LocationID);

	}


	public String getAccountNumber() {
		return AccountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}


	public double getAmount() {
		return Amount;
	}


	public void setAmount(double amount) {
		Amount = amount;
	}


	public String getLocationID() {
		return LocationID;
	}


	public void setLocationID(String locationID) {
		LocationID = locationID;
	}



}

/**
 * 
 */
package com.pivotpayables.intacctplatform;

import static java.lang.System.out;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author John
 * The is the base class for the Invoice object used to read existing AR invoices
 *
 */
@XmlRootElement(name="arinvoice")
@XmlAccessorType (XmlAccessType.FIELD)
public class Invoice {
	
	@XmlElement(name = "RECORDNO")
	private String Record;
	
	@XmlElement(name = "RECORDID")
	private String InvoiceID;
	
	
	@XmlElement(name = "CUSTOMERID")
	private String CustomerID;
	
	@XmlElement(name = "CUSTOMERNAME")
	private String CustomerName;
	
	@XmlElement(name = "BILLTO.CONTACTNAME")
	private String BillToContactName;
	
	@XmlElement(name = "SHIPTO.CONTACTNAME")
	private String ShipToContactName;
	
	
	
	@XmlElement(name = "BASECURR")
	private String BaseCurrency;
	
	@XmlElement(name = "CURRENCY")
	private String Currency;
	
	@XmlElement(name = "EXCHANGE_RATE")
	private double ExchangeRate;
	
	@XmlElement(name = "TOTALDUE")
	private double TotalDue;
	
	@XmlElement(name = "WHENCREATED")
	private Date DateCreated;
	
	@XmlElement(name = "WHENDUE")
	private Date DateDue;

	
	
	
	
	public void display() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";
		String strDate = null;

		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		out.println("Record " + Record);
		out.println("Invoice ID " + InvoiceID);
		out.println("Customer Name: " + CustomerName);
		out.println("Bill To Contact Name: " + BillToContactName);
		out.println("Ship To Contact Name: " + ShipToContactName);
		out.println("Amount: " + decimalFormat.format(TotalDue) + " " + Currency);
		if (DateCreated != null) {
			strDate = sdf.format(DateCreated);	 
			out.println("Date Created: " + strDate);
		}
		if (DateDue != null) {
			strDate = sdf.format(DateDue);	 
			out.println("Date Due: " + strDate);
		}
	}

}
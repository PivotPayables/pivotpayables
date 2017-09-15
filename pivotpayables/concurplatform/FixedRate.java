package com.pivotpayables.concurplatform;

import static java.lang.System.out;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement (name="Rate")
@XmlAccessorType (XmlAccessType.FIELD)
public class FixedRate {

	@XmlElement
	String Description;
	
	@XmlElement
	String Currency;
	
	@XmlElement
	boolean IsPrimary;
	
	@XmlElement
	String SemanticsCode;
	
	@XmlElement
	String SemanticsVendorType;
	
	@XmlElement
	String PerUnit;
	
	@XmlElement
	double NumUnits;
	
	@XmlElement
	double Amount;
	
	double total=0;
	
    public void display() {

		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		
		out.println("Fixed Rate Charge");
		if (Description != null){
			out.println("Description: " + Description);
		}
		if (Currency != null){
			out.println("Currency: " + Currency);
		}
		if (PerUnit != null){
			out.println("Unit of Measure: " + PerUnit);
		}
		out.println("Number of Units: " + decimalFormat.format(NumUnits));
		out.println("Per Unit Amount: " + decimalFormat.format(Amount));
		total = NumUnits*Amount;
		out.println("Total Charge Amount: " + decimalFormat.format(total));
    }
}

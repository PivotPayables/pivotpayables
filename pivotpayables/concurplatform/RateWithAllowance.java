package com.pivotpayables.concurplatform;

import static java.lang.System.out;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement (name="RateWithAllowance")
@XmlAccessorType (XmlAccessType.FIELD)
public class RateWithAllowance {

	@XmlElement
	String Currency;
	
	@XmlElement
	double Amount;
	
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
	double AllowanceNumUnits;
	
	@XmlElement
	double AllowanceAmount;
	
	@XmlElement
	boolean AllowanceIsUnlimited;

    public void display() {

		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		out.println("Rate with Allowance Charge");
		if (SemanticsCode != null){
			out.println("Semantics Code: " + SemanticsCode);
		}
		
		if (PerUnit != null){
			out.println("Per Unit: " + PerUnit);
		}
		if (Currency != null){
			out.println("Currency: " + Currency);
		}
		out.println("Number of Units: " + decimalFormat.format(NumUnits) + " " + NumUnits);
		out.println("Amount: " + decimalFormat.format(Amount) + " " + Amount);
		out.println("Allowance Number of Units: " + decimalFormat.format(AllowanceNumUnits) + " " + AllowanceNumUnits);
		out.println("Allowance Amount: " + decimalFormat.format(AllowanceAmount) + " " + AllowanceAmount);
    }
}

package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 6/21/2016
 * This is the base class for the VATData resource.
 * 
 * Moved doctoVATData
 *
 */

import static java.lang.System.out;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.Locale;
import java.text.DecimalFormat;
import java.text.NumberFormat;



import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jackson.annotate.JsonProperty;
public class VATData {
	
	// these are elements in the Concur API
	@XmlElement
	@JsonProperty
	private String TaxName;
	
	@XmlElement
	@JsonProperty
	private String TaxAuthorityLabel;
	
	@XmlElement
	@JsonProperty
	private double TaxTransactionAmount;
	
	@XmlElement
	@JsonProperty
	private double TaxPostedAmount;
	
	@XmlElement
	@JsonProperty
	private String Source;
	
	@XmlElement
	@JsonProperty
	private double TaxReclaimTransactionAmount;
	
	@XmlElement
	@JsonProperty
	private double TaxReclaimPostedAmount;
	
	// these are elements not in the Concur API
	private String ID;
	private String PostedCurrency;
	private String OriginalCurrency;
	
	public VATData() {}// no args constructor required by Jackson
	
	public void display() {
				Locale locale  = new Locale("en", "US");
		String pattern = "###.##";

		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		out.println("ID: " +  ID);
		out.println("Tax Name: " +  TaxName);
		out.println("Tax Authority Label: " +  TaxAuthorityLabel);
		out.println("Tax Transaction Amount: " + decimalFormat.format(TaxTransactionAmount) + " " + OriginalCurrency);
		out.println("Tax Posted Amount: " + decimalFormat.format(TaxPostedAmount) + " " + PostedCurrency);
		out.println("Tax Reclaim Transaction Amount: " + decimalFormat.format(TaxReclaimTransactionAmount) + " " + OriginalCurrency);
		out.println("Tax Reclaim Posted Amount: " + decimalFormat.format(TaxReclaimPostedAmount) + " " + PostedCurrency);
		out.println("Source: " +  Source);
	}
	public BasicDBObject getDocument () {//this method returns the VATData as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myVATData = new BasicDBObject("VATDataID",this.ID);
		
		myVATData.append("TaxName",this.TaxName);
		myVATData.append("TaxAuthorityLabel",this.TaxAuthorityLabel);
		myVATData.append("Source",this.Source);
		myVATData.append("OriginalCurrency",this.OriginalCurrency);
		myVATData.append("PostedCurrency",this.PostedCurrency);
		myVATData.append("TaxTransactionAmount",this.TaxTransactionAmount);
		myVATData.append("TaxPostedAmount",this.TaxPostedAmount);
		myVATData.append("TaxReclaimTransactionAmount",this.TaxReclaimTransactionAmount);
		myVATData.append("TaxReclaimPostedAmount",this.TaxReclaimPostedAmount);

		return myVATData;
	}
	public VATData doctoVATData (DBObject doc) {

		VATData myVATData = new VATData();
		String ID = doc.get("VATDataID").toString();
		myVATData.setID(ID);
		String TaxName = doc.get("TaxName").toString();
		myVATData.setTaxName(TaxName);
		String TaxAuthorityLabel = doc.get("TaxAuthorityLabel").toString();
		myVATData.setTaxAuthorityLabel(TaxAuthorityLabel);
		String Source = doc.get("Source").toString();
		myVATData.setSource(Source);
		String OriginalCurrency = doc.get("OriginalCurrency").toString();
		myVATData.setOriginalCurrency(OriginalCurrency);
		String PostedCurrency = doc.get("PostedCurrency").toString();
		myVATData.setPostedCurrency(PostedCurrency);
		String amt;//placeholder for an amount represented as a String
		amt = doc.get("TaxTransactionAmount").toString();
		double Amount = Double.parseDouble(amt);
		myVATData.setTaxTransactionAmount(Amount);
		amt = doc.get("TaxPostedAmount").toString();
		Amount = Double.parseDouble(amt);
		myVATData.setTaxPostedAmount(Amount);
		amt = doc.get("TaxReclaimTransactionAmount").toString();
		Amount = Double.parseDouble(amt);
		myVATData.setTaxReclaimTransactionAmount(Amount);
		amt = doc.get("TaxReclaimPostedAmount").toString();
		Amount = Double.parseDouble(amt);
		myVATData.setTaxReclaimPostedAmount(Amount);

				
		return myVATData;
	}
	public String getTaxName() {
		return TaxName;
	}

	public void setTaxName(String taxName) {
		TaxName = taxName;
	}

	public String getTaxAuthorityLabel() {
		return TaxAuthorityLabel;
	}

	public void setTaxAuthorityLabel(String taxAuthorityLabel) {
		TaxAuthorityLabel = taxAuthorityLabel;
	}

	public double getTaxTransactionAmount() {
		return TaxTransactionAmount;
	}

	public void setTaxTransactionAmount(double taxTransactionAmount) {
		TaxTransactionAmount = taxTransactionAmount;
	}

	public double getTaxPostedAmount() {
		return TaxPostedAmount;
	}

	public void setTaxPostedAmount(double taxPostedAmount) {
		TaxPostedAmount = taxPostedAmount;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	public double getTaxReclaimTransactionAmount() {
		return TaxReclaimTransactionAmount;
	}

	public void setTaxReclaimTransactionAmount(double taxReclaimTransactionAmount) {
		TaxReclaimTransactionAmount = taxReclaimTransactionAmount;
	}

	public double getTaxReclaimPostedAmount() {
		return TaxReclaimPostedAmount;
	}

	public void setTaxReclaimPostedAmount(double taxReclaimPostedAmount) {
		TaxReclaimPostedAmount = taxReclaimPostedAmount;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPostedCurrency() {
		return PostedCurrency;
	}

	public void setPostedCurrency(String postedCurrency) {
		PostedCurrency = postedCurrency;
	}

	public String getOriginalCurrency() {
		return OriginalCurrency;
	}

	public void setOriginalCurrency(String originalCurrency) {
		OriginalCurrency = originalCurrency;
	}

		
	

}

package com.pivotpayables.concurplatform;
/**
 * @author John Toman
 * 6/21/2016
 * This is the base class for the Journal Entry resource.
 * 
 * It includes elements in the Concur API as well as as elements that aren't
 * 
 * Moved doctoJournalEntry
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

public class JournalEntry {
	

	// these are elements not in the Concur API
	@XmlElement
	@JsonProperty
	private String PayerPaymentTypeName;
	
	@XmlElement
	@JsonProperty
	private String PayeePaymentTypeName;
	
	@XmlElement
	@JsonProperty
	private String PayeePaymentTypeCode;
	
	@XmlElement
	@JsonProperty
	private String AccountCode;	
	
	@XmlElement
	@JsonProperty
	private String DebitOrCredit;
	
	@XmlElement
	@JsonProperty
	private double Amount;
	
	@XmlElement
	@JsonProperty
	private String JobRunKey;
	
	// these are elements not in the Concur API
	private String ID;
	private String PostedCurrency;

	
	public JournalEntry() {}// no args constructor required by Jackson
	
	public void display() {

		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";

		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		
		out.println("PayerPaymentTypeName: " +  PayerPaymentTypeName);
		out.println("PayeePaymentTypeName: " +  PayeePaymentTypeName);
		out.println("PayeePaymentTypeCode: " +  PayeePaymentTypeCode);
		out.println("AccountCode: " +  AccountCode);	
		out.println("DebitOrCredit: " + DebitOrCredit);
		out.println("Amount: " + decimalFormat.format(Amount) + " " + PostedCurrency);
		if (JobRunKey != null){
			out.println("JobRunKey: " +  JobRunKey);
		}
	}
	
	public BasicDBObject getDocument () {//this method returns the Jouranl Entry as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myJournalEntry = new BasicDBObject("JournalEntryID",this.ID);
		
		myJournalEntry.append("PayerPaymentTypeName",this.PayerPaymentTypeName);
		myJournalEntry.append("PayeePaymentTypeName",this.PayeePaymentTypeName);
		myJournalEntry.append("PayeePaymentTypeCode",this.PayeePaymentTypeCode);
		myJournalEntry.append("AccountCode",this.AccountCode);
		myJournalEntry.append("DebitOrCredit",this.DebitOrCredit);
		myJournalEntry.append("Amount",this.Amount);
		myJournalEntry.append("PostedCurrency",this.PostedCurrency);
		myJournalEntry.append("JobRunKey",this.JobRunKey);


		return myJournalEntry;
	}
	public JournalEntry doctoJournalEntry (DBObject doc) {
		
		JournalEntry myJournalEntry = new JournalEntry();
		String ID = doc.get("JournalEntryID").toString();
		myJournalEntry.setID(ID);
		String PostedCurrency = doc.get("PostedCurrency").toString();
		myJournalEntry.setPostedCurrency(PostedCurrency);
		String PayerPaymentTypeName = doc.get("PayerPaymentTypeName").toString();
		myJournalEntry.setPayerPaymentTypeName(PayerPaymentTypeName);
		String PayeePaymentTypeName = doc.get("PayeePaymentTypeName").toString();
		myJournalEntry.setPayeePaymentTypeName(PayeePaymentTypeName);
		String PayeePaymentTypeCode = doc.get("PayeePaymentTypeCode").toString();
		myJournalEntry.setPayeePaymentTypeCode(PayeePaymentTypeCode);
		String AccountCode = null;
		if (doc.get("AccountCode") != null) {
			AccountCode = doc.get("AccountCode").toString();
			myJournalEntry.setAccountCode(AccountCode);
		}
		
		String amt;//placeholder for an amount represented as a String
		amt = doc.get("Amount").toString();
		double Amount = Double.parseDouble(amt);
		myJournalEntry.setAmount(Amount);
		String DebitOrCredit = doc.get("DebitOrCredit").toString();
		myJournalEntry.setDebitOrCredit(DebitOrCredit);
		if (doc.get("JobRunKey") != null){
			String JobRunKey = doc.get("JobRunKey").toString();
			myJournalEntry.setJobRunKey(JobRunKey);
		}
		
		
				
		return myJournalEntry;
	}		

	public String getPayerPaymentTypeName() {
		return PayerPaymentTypeName;
	}

	public void setPayerPaymentTypeName(String payerPaymentTypeName) {
		PayerPaymentTypeName = payerPaymentTypeName;
	}

	public String getPayeePaymentTypeName() {
		return PayeePaymentTypeName;
	}

	public void setPayeePaymentTypeName(String payeePaymentTypeName) {
		PayeePaymentTypeName = payeePaymentTypeName;
	}

	public String getPayeePaymentTypeCode() {
		return PayeePaymentTypeCode;
	}

	public void setPayeePaymentTypeCode(String payeePaymentTypeCode) {
		PayeePaymentTypeCode = payeePaymentTypeCode;
	}

	public String getAccountCode() {
		return AccountCode;
	}

	public void setAccountCode(String accountCode) {
		AccountCode = accountCode;
	}

	public String getDebitOrCredit() {
		return DebitOrCredit;
	}

	public void setDebitOrCredit(String debitOrCredit) {
		DebitOrCredit = debitOrCredit;
	}
	public double getAmount() {
		return Amount;
	}

	public void setAmount(double amount) {
		Amount = amount;
	}

	public String getJobRunKey() {
		return JobRunKey;
	}

	public void setJobRunKey(String jobRunKey) {
		JobRunKey = jobRunKey;
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


}

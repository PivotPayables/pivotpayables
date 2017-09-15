package com.pivotpayables.nexus;
/*
**
* @author John Toman
* 6/21/2016
* 
* This is the base class for a ReportPayee
* 
* A Report Payee is a payee on an expense report, or payment request.  A payee is someone or some organization the company owes an amount. 
* Payees can be: an employee, a card issuer, or a vendor.
* An expense report can have one or more payees.  A payment request has only one payee. 

* 
* 
* 
*/



import static java.lang.System.out;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Locale;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ReportPayee implements Comparable<ReportPayee> {
	private String ID;
	private String CompanyID;// the Company this  payee belongs
	private String ReportID;// the the unique identifier Concur assigsn to the expense report 
	private String BatchDefinitionID;// the unique identifier for the Batch Definition used to create this Report Payee
	private String PaymentTypeName;// the name of the Payment Type the employee used to purchase the good or service
	private String PaymentTypeCode;// the code of the Payee Payment Type
	private double Amount;// amount due the payee
	private String Currency;// the Currency Code for the Payee 
	private String PaymentMethod;// the method the employee is reimbursed or the card issuer or vendor is paid
	private String Status;// the processing status of this ReportPayee: Not Processed, Processed, Missing Definition
	
public ReportPayee() {}// no args constructor
	
	public void display () {
		Locale locale  = new Locale("en", "US");
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		
		if (ID != null) {
			out.println("ID: " + ID);
		}
		if (CompanyID != null) {
			out.println("Company ID: " + CompanyID);
		}
		if (ReportID != null) {
			out.println("Report ID: " + ReportID);
		}
		if (BatchDefinitionID != null) {
			out.println("Batch Definition ID: " + BatchDefinitionID);
		}
		
		if (PaymentTypeName != null) {
			out.println("Payment Type Name: " + PaymentTypeName);
		}
		if (PaymentTypeCode != null) {
			out.println("Payment Type Code: " + PaymentTypeCode);
		}
		if (PaymentMethod != null) {
			out.println("Payment Method: " + PaymentMethod);
		}
		out.println("Amount: " + decimalFormat.format(Amount) + " " + Currency);
		if (Status != null) {
			out.println("Status: " + Status);
		}
		
	}
	public BasicDBObject getDocument () {//this method returns the ReportPayee object as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myDoc = new BasicDBObject("ID",this.ID);		
		myDoc.append("CompanyID", this.CompanyID);
		myDoc.append("ReportID", this.ReportID);
		myDoc.append("BatchDefinitionID", this.BatchDefinitionID);
		myDoc.append("PaymentTypeName",this.PaymentTypeName);
		myDoc.append("PaymentTypeCode",this.PaymentTypeCode);
		myDoc.append("PaymentMethod",this.PaymentMethod);
		myDoc.append("Amount", this.Amount);
		myDoc.append("Currency", this.Currency);
		myDoc.append("Status", this.Status);
		
		return myDoc;
	}
	public ReportPayee doctoReportPayee (DBObject doc) {
		String ID= doc.get("ID").toString();
		String CompanyID = doc.get("CompanyID").toString();
		String ReportID = doc.get("ReportID").toString();
		String BatchDefinitionID = null;
		String PaymentTypeName = null;
		String PaymentTypeCode= null;
		String PaymentMethod = null;
		String Currency = null;
		double Amount=0;
		String Status = null;
		if (doc.get("BatchDefinitionID") != null){
			BatchDefinitionID = doc.get("BatchDefinitionID").toString();
		}
		if (doc.get("PaymentTypeName") != null){
			PaymentTypeName = doc.get("PaymentTypeName").toString();
		}
		if (doc.get("PaymentTypeCode") != null){
			PaymentTypeCode = doc.get("PaymentTypeCode").toString();
		}
		if (doc.get("PaymentMethod") != null){
			PaymentMethod = doc.get("PaymentMethod").toString();
		}	
		if (doc.get("Currency") != null){
			Currency = doc.get("Currency").toString();
		}
		if (doc.get("Amount") != null){
			Amount = Double.parseDouble(doc.get("Amount").toString());
		}
		if (doc.get("Status") != null){
			Status = doc.get("Status").toString();
		}
		

		ReportPayee myReportPayee = new ReportPayee();
		myReportPayee.setID(ID);
		myReportPayee.setCompanyID(CompanyID);
		myReportPayee.setReportID(ReportID);
		myReportPayee.setBatchDefinitionID(BatchDefinitionID);
		myReportPayee.setPaymentTypeName(PaymentTypeName);
		myReportPayee.setPaymentTypeCode(PaymentTypeCode);
		myReportPayee.setPaymentMethod(PaymentMethod);
		myReportPayee.setCurrency(Currency);
		myReportPayee.setAmount(Amount);
		myReportPayee.setStatus(Status);

		return myReportPayee;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCompanyID() {
		return CompanyID;
	}

	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}

	public String getReportID() {
		return ReportID;
	}

	public void setReportID(String ID) {
		ReportID = ID;
	}

	public String getBatchDefinitionID() {
		return BatchDefinitionID;
	}

	public void setBatchDefinitionID(String batchDefinitionID) {
		BatchDefinitionID = batchDefinitionID;
	}

	public String getPaymentTypeName() {
		return PaymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		PaymentTypeName = paymentTypeName;
	}

	public String getPaymentTypeCode() {
		return PaymentTypeCode;
	}

	public void setPaymentTypeCode(String paymentTypeCode) {
		PaymentTypeCode = paymentTypeCode;
	}

	public double getAmount() {
		return Amount;
	}

	public void setAmount(double amount) {
		Amount = amount;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getPaymentMethod() {
		return PaymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		PaymentMethod = paymentMethod;
	}
	
	public static Comparator<ReportPayee> batchdefinitionIDComparator = new Comparator<ReportPayee>() {
		// This method helps sort an ArrayList of ReportPayee objects by BatchDefintionID
	    @Override         
	    public int compare(ReportPayee rp1, ReportPayee rp2) {             
	      return (int) (rp1.getBatchDefinitionID().compareTo(rp2.getBatchDefinitionID()));         
	    }     
	  };

	@Override
	public int compareTo(ReportPayee o) {
		// TODO Auto-generated method stub
		return 0;
	}         
	

}

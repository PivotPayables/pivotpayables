package com.pivotpayables.nexus;

/**
 * @author John Toman
 * 7/21/2017
 * This is the base class for the Detail object for a PivotNexus canonical transaction.
 * 

 *
 */
import static java.lang.System.out;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Detail {
	
	private String DetailType;//Indicates the type of transaction this detail or distribution line item represents
	private double Amount;// the amount of the detail in the currency specified in the Header
	private String ChargeType;
	/* 
	 * For a Credit Card Charge, whether the amount is a purchase (charge) or a refund (credit)
	 * For a Receivable, whether the amount is for a purchase or a refund
	 */
	private String AccountingObjectID;// the unique identifier for the Expense account, Item, or whatever object the Accounting application uses to make the entry
	private String PostingType;// whether this detail is a debit or credit.  default is debit
	private String Description;// a description for the detail
	private String OrgUnitID;// the unique identifier in the accounting application for the Organization Unit such as QuickBooks Class associated with this detail
	private String CustomerID;// the unique identifier in the accounting application for the customer such as the QuickBooks Customer:Job associated with this detail
	private Boolean Billable;// whether this is billable to the specified customer
	private String TransactionID;// the unique identifier for transaction this detail is associated
	/*
	 * For a Payment, the AP transaction it pays
	 * For a Remittance, the AR transaction it pays
	 * For a Credit Memo, the AP transaction to apply this credit
	 * For a Vendor Memo, the AP tranaction to apply this credit
	 */
	private String TxLineID;// the unique identifier for the line item from the transaction this detail is associated

	private HashMap<String, String> CustomFields;// zero or more detail level custom fields represented as a key-value pair, where key = field's name value = field's value
	private static Iterator<Entry<String, String>> it;// placeholder for an Iterator object
	
	public void display () {
		Locale locale  = new Locale("en", "US");
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		

		if (DetailType != null) {
			out.println("Detail Type: " + DetailType);
		}
		if (AccountingObjectID != null) {
			out.println("Accounting Object ID: " + AccountingObjectID);
		}
		if (PostingType != null) {
			out.println("Posting Type: " + PostingType);
		}
		out.println("Amount: " + decimalFormat.format(Amount) );
		if (ChargeType != null) {
			out.println("Charge Type: " + ChargeType);
		}
		if (Description != null) {
			out.println("Description: " + Description);
		}
		if (OrgUnitID != null) {
			out.println("Org Unit ID: " + OrgUnitID);
		}
		if (CustomerID != null) {
			out.println("Customer ID: " + CustomerID);
		}
		if (Billable != null) {
			out.println("Billable: " + Billable);
		}
		if (TransactionID != null) {
			out.println("Transaction ID: " + TransactionID);
		}
		if (TxLineID != null) {
			out.println("Transaction Line ID: " + TxLineID);
		}
		if (CustomFields != null){
			out.print("Custom Field Mappings");
			out.print("__________________________________");
			out.println();
			it = CustomFields.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        out.println("Field Name: " + (String) pair.getKey());// get the key for the Map entry, which is the field's name
		        out.println("Field Value: " + (String) pair.getValue());// get the value for the Map entry, which is the field's value
				out.print("--------------------------");
				out.println();
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }// while (it.hasNext())

		}// if (CustomFields.size()>0)
		
	}
	public BasicDBObject getDocument () {//this method returns the context as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myDoc = new BasicDBObject("DetailType",this.getDetailType());		
		myDoc.append("AccountingObjectID", this.AccountingObjectID);
		myDoc.append("PostingType", this.PostingType);
		myDoc.append("Amount", this.Amount);
		myDoc.append("ChargeType", this.ChargeType);
		myDoc.append("Description", this.Description);
		myDoc.append("OrgUnitID", this.OrgUnitID);
		myDoc.append("CustomerID",this.CustomerID);
		myDoc.append("Billable",this.Billable);
		myDoc.append("TransactionID", this.TransactionID);
		myDoc.append("TxLineID", this.TxLineID);
		if (CustomFields.size()>0){
			BasicDBList customfields = new BasicDBList();
			BasicDBObject Doc = new BasicDBObject();
			it = CustomFields.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
				Doc = new BasicDBObject("FieldName",(String) pair.getKey());// get the key for the Map entry, which is the field's name
		        Doc.append("FieldValue", (String) pair.getValue());// get the value for the Map entry, which is the field's value
		        customfields.add(Doc);// add the custom field document
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }// while (it.hasNext())
			myDoc.append("CustomFields", customfields);// add the BasicDBList of custom fields to the Detail document
		}

		return myDoc;
	}
	public Detail doctoDetail (DBObject doc) {
		String key="";
		String value="";
		Detail myDetail = new Detail();

		if (doc.get("DetailType") != null){
			DetailType = doc.get("DetailType").toString();
			myDetail.setDetailType(DetailType);
		}
		double Amount=0;
		if (doc.get("Amount") != null){
			Amount = Double.parseDouble(doc.get("Amount").toString());
			myDetail.setAmount(Amount);
		}
		if (doc.get("AccountingObjectID") != null){
			AccountingObjectID = doc.get("AccountingObjectID").toString();
			myDetail.setAccountingObjectID(AccountingObjectID);
		}
		if (doc.get("ChargeType") != null){
			ChargeType = doc.get("ChargeType").toString();
			myDetail.setChargeType(ChargeType);
		}
		if (doc.get("PostingType") != null){
			PostingType = doc.get("PostingType").toString();
			myDetail.setPostingType(PostingType);
		}
		if (doc.get("Description") != null){
			Description = doc.get("Description").toString();
			myDetail.setDescription(Description);
		}
		if (doc.get("OrgUnitID") != null){
			OrgUnitID = doc.get("OrgUnitID").toString();
			myDetail.setOrgUnitID(OrgUnitID);
		}
		if (doc.get("CustomerID") != null){
			CustomerID = doc.get("CustomerID").toString();
			myDetail.setCustomerID(CustomerID);
		}
		if (doc.get("Billable") != null){
			Billable = (Boolean) doc.get("Billable");
			myDetail.setBillable(Billable);
		}
		if (doc.get("TransactionID") != null){
			TransactionID = doc.get("TransactionID").toString();
			myDetail.setTransactionID(TransactionID);
		}
		if (doc.get("TxLineID") != null){
			TxLineID = doc.get("TxLineID").toString();
			myDetail.setTxLineID(TxLineID);
		}
		if (doc.get("CustomFields") !=null){
			DBObject Doc;
			BasicDBList fields = new BasicDBList();
			fields = (BasicDBList) doc.get("CustomFields");
			HashMap<String,String> CustomFields = new HashMap<String,String>();
			
			int fieldcount = fields.size();//the number of custom field documents
			if (fieldcount > 0) {//then there are details for this transaction
				for (int i=0; i<fieldcount; i++) {//iterate for each detail
					Doc = (DBObject) fields.get(i);
					key = Doc.get("FieldName").toString();
					value = Doc.get("FieldValue").toString();
					CustomFields.put(key, value);
				}
			}
			myDetail.setCustomFields(CustomFields);
		}	

		return myDetail;
	}
	public String getDetailType() {
		return DetailType;
	}
	public void setDetailType(String detailType) {
		DetailType = detailType;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}
	
	public String getChargeType() {
		return ChargeType;
	}
	public void setChargeType(String chargeType) {
		ChargeType = chargeType;
	}
	public String getAccountingObjectID() {
		return AccountingObjectID;
	}
	public void setAccountingObjectID(String accountingObjectID) {
		AccountingObjectID = accountingObjectID;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getOrgUnitID() {
		return OrgUnitID;
	}
	public void setOrgUnitID(String orgUnitID) {
		OrgUnitID = orgUnitID;
	}
	public String getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}
	public Boolean getBillable() {
		return Billable;
	}
	public void setBillable(Boolean billable) {
		Billable = billable;
	}

	public String getPostingType() {
		return PostingType;
	}
	public void setPostingType(String postingType) {
		PostingType = postingType;
	}
	public String getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(String transactionID) {
		TransactionID = transactionID;
	}
	public String getTxLineID() {
		return TxLineID;
	}
	public void setTxLineID(String txLineID) {
		TxLineID = txLineID;
	}
	public HashMap<String, String> getCustomFields() {
		return CustomFields;
	}
	public void setCustomFields(HashMap<String, String> customFields) {
		CustomFields = customFields;
	}

}

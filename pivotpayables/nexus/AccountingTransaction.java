package com.pivotpayables.nexus;
/**
 * @author John Toman
 * 6/28/2017
 * This is the base class for the AccountingTransaction object, which represents a PivotNexus, canonical transaction.
 * 

 *
 */
import static java.lang.System.out;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class AccountingTransaction {
	private String ID;// the unique identifier the accounting application assigned to this bill
	private String CompanyID;// the company associated to this transaction
	private String Type;
	/* indicates the transaction type. 
	 * BILL is for an AP Transaction.  
	 * CARD is for Credit Card Charge transaction. 
	 * PMNT is for a Payment transaction.
	 * JRNL is for a Journal Entry transaction.
	 * INVC is for an AR Transaction.
	 * REMT is for a Remittance transaction.
	 * MEMO is for a Credit Memo.
	 * VMMO is for a Vendor Memo.
	 * 
	 */
	private String LedgerID;// the unique identifier for the ledger this transactions resides
	private String VendorID;
	/* the unique identifier from the Vendor Master List for the vendor.  
	 * For a Bill, this is the vendor for the payable.  
	 * For a Card, this is the merchant for the purchase.  
	 * For a Payment, this is the payee.  
	 * For other transaction types, it's not applicable
	 */
	
	private String AddressID;// the unique identifier from the Vendor Master List for the remittance address.  Used only for a Bill.
	
	private String CustomerID;
	/* the unique identifier from the Customer List for the customer.  
	 * For an Invoice, this is the customer for the receivable.  
	 * For a Remittance, this is the customer making the remittance.
	 * For other transaction types, it's not applicable.
	 */
	
	private String ReferenceNumber;
	/* This is a customer-facing or vendor-facing identifier for the transaction.  It provides both the buyer and seller a reference to this transaction.
	 * For a Bill, this is the vendor's invoice number.  
	 * For a Card, this is the charge's Transaction ID assigned by the card issuer.  
	 * For a Payment, this is the check # or other identifier for the payment.
	 * For an Invoice, this is the customer-facing unique identifier for the invoice
	 * For a Journal Entry, it's not applicable.
	 * For a Remittance, this is the customer's unique identifier for their remittance 
	 */
	private double Amount;
	/* The Header amount in the currency specified by the CurrencyCode. 
	 * For a Bill, this is the amount due to the vendor.  
	 * For a Card, this is the Transaction Amount in the currency paid to the merchant.  
	 * For a Payment, this is the amount paid to the payee.  
	 * For a Journal Entry, this is the amount to charge the header entry.
	 * For an Invoice, this is amount the customer owes.
	 * For a Remittance, this is the amount the customer paid.
	 */
	private String CurrencyCode;
	/* this the ISO 3-letter currency code e.g., USD or EUR, for the amounts in this transaction. 
	 * For a Bill, this is the currency to pay the vendor.  
	 * For a Card, this is the currency paid to the merchant.  For a Payment, this is the currency paid to the payee.  For a Journal Entry, it's not applicable.
	 */
	private double ExchangeRate;// the number to multiply the Amount value to arrive at transaction Amount in Home currency (the currency of the ledger)
	private Date PostingDate;
	/* This is the date for this transaction in the account application.  It impacts financial statements.
	 * For a Bill, this is the posting date for the AP transaction (not the vendor’s invoice date).  
	 * For a Card, this is the Posting Date the card issuer assigned to the charge.  
	 * For a Payment, this the payment date.
	 * For a Journal Entry, this is the entry's date.
	 * For an Invoice, this is the Invoice Date, or the receivables date
	 * For a Remittance, this is when the remittance was processed
	 */
	private Date InvoiceDate;
	/* For a Bill, this is the date on the vendor's invoice. 
	 * For a Card, this is the Transaction Date for the charge.  
	 * For other transaction types, this isn't applicable.
	 */
	private String AccountingObjectID;
	/* the unique identifier for the account for the header record.  
	 * For a Bill, this is the Liability, or AP, account.  
	 * For a Card, this is the Credit Card account.  
	 * For a Payment, this the Asset, or Cash, account.  
	 * For a Journal, this is the account for the header entry
	 */
	private String PostingType;// whether the Header is a credit or debit
	private String Description;// a description or "memo" for the header
	private String PrivateNote;// a note readable only by the AP department (i.e., it's a private note); this note won't appear on the Remittance Advice
	private String ExternalNote;// a note readable by the Vendor or Customer; typically, this note appears on the Purchase Order, Invoice, or Remittance Advice sent the Customer or to the Vendor
	private HashMap<String, String> CustomFields;// zero or more header level custom fields represented as a key-value pair, where key = field's name value = field's value
	private ArrayList<Detail> Details = new ArrayList<Detail>();// an ArrayList of Detail or "Distribution" objects
	String OrgUnitID;// the unique identifier in the accounting application for the Organization Unit such as QuickBooks Class associated with this transaction
	
	private static Iterator<Entry<String, String>> it;// placeholder for an Iterator object
	
	public void display () {
		Locale locale  = new Locale("en", "US");
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = null;
		
		if (ID != null) {
			out.println("ID: " + ID);
		}
		if (CompanyID != null) {
			out.println("Company ID: " + CompanyID);
		}
		if (Type != null) {
			out.println("Type: " + Type);
		}

		if (LedgerID != null) {
			out.println("Ledger ID: " + LedgerID);
		}
		out.println("Amount: " + decimalFormat.format(Amount) );
		if (Description != null) {
			out.println("Description: " + Description);
		}
		
		if (VendorID != null) {
			out.println("VendorID: " + VendorID);
		}
		
		if (AddressID != null) {
			out.println("Address ID: " + AddressID);
		}
		if (CustomerID != null) {
			out.println("Customer ID: " + CustomerID);
		}
		if (CurrencyCode != null) {
			out.println("Currency Code: " + CurrencyCode);
		}
		if (OrgUnitID != null) {
			out.println("Org Unit ID: " + OrgUnitID);
		}
		if (PostingDate != null) {
			strDate = sdf.format(PostingDate);	 
			out.println("Posting Date: " + strDate);
		}
		if (InvoiceDate != null) {
			strDate = sdf.format(InvoiceDate);	 
			out.println("Invoice Date: " + strDate);
		}
		if (AccountingObjectID != null) {
			out.println("Header Accounting Object: " + AccountingObjectID);
		}
		if (PostingType != null) {
			out.println("Posting Type: " + PostingType);
		}
		if (PrivateNote != null) {
			out.println("PrivateNote: " + PrivateNote);
		}
		if (ExternalNote != null) {
			out.println("External Note: " + ExternalNote);
		}
		if (CustomFields !=null){
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
		
		if (Details.size()>0){// then there is at least one detail
			for (Detail detail:Details){
				out.println("DETAIL");
				detail.display();
				out.println("End of Detail------------------------------------------");
				out.println();
			}// i loop
			
		}// if (details.size()>0)
		
	}
	
	public BasicDBObject getDocument () {//this method returns the AP transaction object as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myDoc = new BasicDBObject("Type",this.getType());
		myDoc.append("ID", this.ID);
		myDoc.append("CompanyID", this.CompanyID);
		myDoc.append("Type", this.Type);
		myDoc.append("LedgerID", this.LedgerID);
		myDoc.append("Amount", this.Amount);
		myDoc.append("Description", this.Description);
		myDoc.append("VendorID", this.VendorID);
		myDoc.append("AddressID",this.AddressID);
		myDoc.append("CustomerID",this.CustomerID);
		myDoc.append("OrgUnitID", this.OrgUnitID);
		myDoc.append("CurrencyCode",this.CurrencyCode);
		myDoc.append("ReferenceNumber",this.ReferenceNumber);
		myDoc.append("ExchangeRate",this.ExchangeRate);
		myDoc.append("PostingDate",this.PostingDate);
		myDoc.append("InvoiceDate",this.InvoiceDate);
		myDoc.append("AccountingObjectID",this.AccountingObjectID);
		myDoc.append("PostingType",this.PostingType);
		myDoc.append("PrivateNote",this.PrivateNote);
		myDoc.append("ExternalNote",this.ExternalNote);
		if (CustomFields != null){
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
			myDoc.append("CustomFields", customfields);// add the BasicDBList of header custom fields to the AP Transaction document
		}
		if (Details != null){// then there is at least one detail
			BasicDBList detailslist = new BasicDBList();
			BasicDBObject Doc = new BasicDBObject();
			for (Detail detail:Details){
				Doc = detail.getDocument();// get the document for this Detail
				detailslist.add(Doc);// add this document to the list of Detail documents
			}// for (Detail detail:Details)
			myDoc.append("Details", detailslist);// add the list of Detail documents to the AP Transaction document
		}// if (Details.size()>0)
		return myDoc;
	}
	
	public AccountingTransaction doctoAccountingTransaction (DBObject doc) {// this converts an AP Transaction document into an AP Transaction object

		String key="";
		String value="";
		
		AccountingTransaction myAccountingTransaction = new AccountingTransaction();
		if (doc.get("ID") != null){
			ID= doc.get("ID").toString();
			myAccountingTransaction.setID(ID);
		}
		if (doc.get("CompanyID") != null){
			CompanyID = doc.get("CompanyID").toString();
			myAccountingTransaction.setCompanyID(CompanyID);
		}
		if (doc.get("Type") != null){
			Type = doc.get("Type").toString();
			myAccountingTransaction.setType(Type);
		}

		if (doc.get("LedgerID") != null){
			LedgerID = doc.get("LedgerID").toString();
			myAccountingTransaction.setLedgerID(LedgerID);
		}
		if (doc.get("VendorID") != null){
			VendorID = doc.get("VendorID").toString();
			myAccountingTransaction.setVendorID(VendorID);
		}
		if (doc.get("AddressID") != null){
			AddressID = doc.get("AddressID").toString();
			myAccountingTransaction.setAddressID(AddressID);
		}
		if (doc.get("CustomerID") != null){
			CustomerID = doc.get("CustomerID").toString();
			myAccountingTransaction.setCustomerID(CustomerID);
		}
		if (doc.get("OrgUnitID") != null){
			OrgUnitID = doc.get("OrgUnitID").toString();
			myAccountingTransaction.setOrgUnitID(OrgUnitID);
		}
		if (doc.get("ReferenceNumber") != null){
			ReferenceNumber = doc.get("ReferenceNumber").toString();
			myAccountingTransaction.setReferenceNumber(ReferenceNumber);
		}
		double Amount=0;
		if (doc.get("Amount") != null){
			Amount = Double.parseDouble(doc.get("Amount").toString());
			myAccountingTransaction.setAmount(Amount);
		}
		if (doc.get("CurrencyCode") != null){
			CurrencyCode = doc.get("CurrencyCode").toString();
			myAccountingTransaction.setCurrencyCode(CurrencyCode);
		}
		double ExchangeRate=0;
		if (doc.get("ExchangeRate") != null){
			ExchangeRate = Double.parseDouble(doc.get("ExchangeRate").toString());
			myAccountingTransaction.setExchangeRate(ExchangeRate);
		}
		if (doc.get("PostingDate") != null){
			Date PostingDate = (Date)doc.get("PostingDate");
			myAccountingTransaction.setPostingDate(PostingDate);
		}
		if (doc.get("InvoiceDate") != null){
			Date InvoiceDate = (Date)doc.get("InvoiceDate");
			myAccountingTransaction.setInvoiceDate(InvoiceDate);
		}
		if (doc.get("AccountingObjectID") != null){
			AccountingObjectID = doc.get("AccountingObjectID").toString();
			myAccountingTransaction.setAccountingObjectID(AccountingObjectID);
		}
		if (doc.get("PostingType") != null){
			PostingType = doc.get("PostingType").toString();
			myAccountingTransaction.setPostingType(PostingType);
		}
		if (doc.get("Description") != null){
			Description = doc.get("Description").toString();
			myAccountingTransaction.setDescription(Description);
		}
		if (doc.get("PrivateNote") != null){
			PrivateNote = doc.get("PrivateNote").toString();
			myAccountingTransaction.setPrivateNote(PrivateNote);
		}
		if (doc.get("ExternalNote") != null){
			ExternalNote = doc.get("ExternalNote").toString();
			myAccountingTransaction.setExternalNote(ExternalNote);
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
			myAccountingTransaction.setCustomFields(CustomFields);
		}
		if (Details != null){
			DBObject Doc;
			BasicDBList details = new BasicDBList();
			details = (BasicDBList) doc.get("Details");
			ArrayList<Detail> Details = new ArrayList<Detail>();// initialize an ArrayList of Detail object elements
			Detail detail = new Detail();
			
			int detailcount = details.size();//the number of Detail documents
			if (detailcount > 0) {//then there are details for this transaction
				for (int i=0; i<detailcount; i++) {//iterate for each detail
					Doc = (DBObject) details.get(i);
					detail = detail.doctoDetail(Doc);//get the Detail object
					Details.add(detail);
				}
			}
			myAccountingTransaction.setDetails(Details);
		}
		return myAccountingTransaction;
	}
	
	

	public String getCompanyID() {
		return CompanyID;
	}

	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getLedgerID() {
		return LedgerID;
	}

	public void setLedgerID(String ledgerID) {
		LedgerID = ledgerID;
	}

	public String getVendorID() {
		return VendorID;
	}

	public void setVendorID(String vendorID) {
		VendorID = vendorID;
	}

	public String getAddressID() {
		return AddressID;
	}

	public void setAddressID(String addressID) {
		AddressID = addressID;
	}

	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public String getReferenceNumber() {
		return ReferenceNumber;
	}

	public void setReferenceNumber(String invoiceNumber) {
		ReferenceNumber = invoiceNumber;
	}

	public double getAmount() {
		return Amount;
	}

	public void setAmount(double amount) {
		Amount = amount;
	}

	public String getCurrencyCode() {
		return CurrencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}

	public double getExchangeRate() {
		return ExchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		ExchangeRate = exchangeRate;
	}

	public Date getPostingDate() {
		return PostingDate;
	}

	public void setPostingDate(Date postingDate) {
		PostingDate = postingDate;
	}

	public Date getInvoiceDate() {
		return InvoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		InvoiceDate = invoiceDate;
	}

	public String getAccountingObjectID() {
		return AccountingObjectID;
	}

	public void setAccountingObjectID(String liabilityAccount) {
		AccountingObjectID = liabilityAccount;
	}
	

	public String getPostingType() {
		return PostingType;
	}

	public void setPostingType(String postingType) {
		PostingType = postingType;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPrivateNote() {
		return PrivateNote;
	}

	public void setPrivateNote(String privateNote) {
		PrivateNote = privateNote;
	}

	public String getExternalNote() {
		return ExternalNote;
	}

	public void setExternalNote(String vendorNote) {
		ExternalNote = vendorNote;
	}

	public HashMap<String, String> getCustomFields() {
		return CustomFields;
	}

	public void setCustomFields(HashMap<String, String> customFields) {
		CustomFields = customFields;
	}

	public ArrayList<Detail> getDetails() {
		return Details;
	}

	public void setDetails(ArrayList<Detail> details) {
		Details = details;
	}

	public String getOrgUnitID() {
		return OrgUnitID;
	}

	public void setOrgUnitID(String orgUnitID) {
		OrgUnitID = orgUnitID;
	}


	

}

/**
 * 
 */
package com.pivotpayables.nexus;
/*
**
* @author John Toman
* 6/19/2017
* 
* This is the base class for a PivotNexus Batch Definition
* 
* Nexus uses the Batch Definition as the way to know what meta-data and/or rules to use to process an expense entry pulled from Concur Expense/Invoice (or, PivotPrime for that matter) into canonical transactions.
* To keep it simple, an "expense entry" refers to an expense entry from Concur Expense (or, any Expense Management application for that matter) or a payment request (i.e., invoice) from Concur Invoice (or, any AP automation application for that matter).
* A Batch Definition allows Integration Administrators to process expense entries organized along certain criteria into batches. 
* A Batch Definition provides a way to determine the meta-data, field mappings, and/or rules necessary to transform expense entries in a batch into the desired canonical transactions ready to be loaded by the specified Adapter into the target ERP application.
* 
* See PPP-416 for more detail about Batch Definitions
*/



import static java.lang.System.out;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class BatchDefinition {
	private String ID;
	
	// Batch Definition Key; these fields determine what expense entries to apply this Batch Definition
	private String CompanyID;// the Company this batch definition belongs
	private String PaymentTypeName;// the name of the Payee Payment Type
	private String PaymentTypeCode;// the code of the Payee Payment Type
	private String Country;// the Country Code for Payee
	private String Currency;// the Currency Code for the Payee
	private HashMap<String, String> Group;// key-value pair describing how to determine the Expense Group the Payee is a member
	/*
	 * Expense Group can be one of these key, value pairs:
	 * Null - when Group is null this uses the Global group, which means this batch definition applies to ALL groups
	 * CustomField Name, Value - the key is the name of the Expense field that is a CustomField that holds the Expense Group and the value is the name of the Group this batch definition applies
	 */
	
	
	/* 
	 * The following fields provide the meta-data, field mappings, and/or rules necessary to transform expense entries in a batch into the desired canonical transactions
	 */
	
	
	private String TransactionType;// the type of canonical transaction to create
	/*
	 * BILL - AP transaction
	 * CARD - Credit Card Charge
	 * PMNT - Payment transaction
	 * JOUR - Journal Entry
	 * INVC - AR Transaction
	 * RMTC - Remittance transaction
	 * CMEM - Credit Memo transaction
	 * VMEM - Vendor Memo transaction
	 * 
	 */
	private String PaymentMethod;// the method the payee will be paid: 
	/*
	 * AP - the Accounts Payable application will handle payment
	 * CNQRPAY - ExpensePay or InvoicePay will pay the payee
	 * SRVC - a payment service outside the AP application will handle it
	 */
	private String PayeeType;// the type of payee
	/*
	 * EMP - employee
	 * CARD - card issuer
	 * VEN - a vendor
	 */
	private HashMap<String, String> LedgerID;// the unique identifier for the target application assigned to the Ledger where to create the transaction
	/*
	 * Ledger ID can be one of these:
	 *
	 * ExpenseField, Field Name - this uses a field from the Expense object, such as, ReportID, OrgUnit1, or Custom5
	 * Constant, Value - this uses the specified constant value
	 * 
	 */
	
	
	private HashMap<String, String> InvoiceNumber;// key-value pair describing how to determine the invoice number
	/*
	 * Invoice Number can be one of these:
	 * 
	 * Serial, blank  - this creates a Serial Number by incrementing the CurrentSerialNumber stored with the batch defintion
	 * GUID, # - this creates a GUID with the specified number of 4 character segments
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, ExpenseField, ReportID
	 * None, blank - this doesn't create an invoice number
	 */	
	private HashMap<String, String> PostingDate;// key-value pair describing how to determine the Posting Date for the transaction
	/*
	 * Posting Date can be one of these
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, PaidDate
	 * Constant, Value - this uses a constant specified as the Value
	 * Today, Blank - this uses today's date
	  */
	private HashMap<String, String> InvoiceDate;//  key-value pair describing how to determine the Invoice Date for the transaction
	/*
	 * Invoice Date can be one of these:
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, PaidDate
	 * Constant, Value - this uses a constant specified as the Value
	 * Today, Blank - this uses today's date
	 */
	private HashMap<String, String> TransactionCurrency;//  key-value pair describing how to determine the currency for the transaction
	/*
	 * Transaction Currency can be one of these:
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, PaidDate
	 * Constant, Value - this uses a constant specified as the Value
	 * 
	 */
	private HashMap<String, String> HeaderAccountObject;// key-value pair describing how to determine the AccountingObjectID for the Header record
	/*
	 * It can be one of these:
	 * 
	 * JournalAccountCode, blank - this uses the JournalAccountCode field from the JournalEntry object
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, ExpenseField, Custom1
	 * ItemField, Field Name - this uses a field from the Itemization object, for example, ItemField, Custom1
	 * AllocationField, Field Name - this uses a field from the Allocation object, for example, AllocationField, Custom2
	 * Constant, Value - this uses a constant specified as the Value
	 * 
	 */
	private HashMap<String, String> EntityID;// key-value pair describing how to determine the "Entity" associated the Header record
	/*
	 * An "Entity" can be an Employee, a Customer, or a Vendor depending on the transaction type
	 * 
	 * For a Bill, this the vendor who is the payee
	 * For a Card, this is the merchant or "payee" the employee purchased the good or service
	 * For an Invoice, this is the customer who purchased products or services
	 * For a Payment, this is vendor who is the payee for this payment
	 * For a Journal Entry, it's not applicable.
	 * For a Remittance, this is the customer who is the payer for this remittance
	 * 
	 * It can be one of these:
	 * 
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, ExpenseField, Custom1
	 * Constant, Value - this uses a constant specified as the Value
	 * 
	 */
	private HashMap<String, String> HeaderDescription;// key-value pair describing how to determine the Description for the transaction
	/*
	 * The Header Description can be one of these:
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, ExpenseField, ReportID
	 * None, blank - this doesn't create an invoice number
	 * 
	 */
	private HashMap<String, String> PrivateNote;// key-value pair describing how to determine the AP Note for the transaction
	/*
	 * The Private Note can be one of these:
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, ExpenseField, ReportID
	 * Constant, Value - this uses a constant specified as the Value
	 * 
	 */
	private HashMap<String, String> VendorNote;// key-value pair describing how to determine the AP Note for the transaction
	/*
	 * The Vendor Note can be one of these:
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, ExpenseField, ReportID
	 * Constant, Value - this uses a constant specified as the Value
	 * 
	 */
	private ArrayList<FieldMapping> HeaderCustomFields;// zero or more header level field mappings; each mapping describes the source form (ojbect type), source field, target form (object type), and target field 
	
	private HashMap<String, String> DetailAccountObject;// key-value pair describing how to determine the AccountingObjectID for a Detail record
	/*
	 * It can be one of these:
	 * 
	 * JournalAccountCode, blank - this uses the JournalAccountCode field from the JournalEntry object
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, ExpenseField, Custom1
	 * ItemField, Field Name - this uses a field from the Itemization object, for example, ItemField, Custom1
	 * AllocationField, Field Name - this uses a field from the Allocation object, for example, AllocationField, Custom2
	 * Lookup, Accounting Object Table ID - this uses the specified AccountObjectTable to lookup the Accounting Object ID
	 * Constant, Value - this uses a constant specified as the Value
	 * 
	 */
	private HashMap<String, String> DetailDescription;// key-value pair describing how to determine the Description for a Detail 
	/*
	 * The Detail Description can be one of these:
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, ExpenseField, Description
	 * ItemField, Field Name - this uses a field from the Itemization object, for example, ItemField, Custom1
	 * Constant, Value - this uses a constant specified as the Value
	 * 
	 */
	private HashMap<String, String> OrgUnitID;// key-value pair describing how to determine the Organization Unit for a Detail
	/*
	 * The Org Unit ID is a field that identifies the organization unit (e.g., Department or Division) to associate the transaction.  For example, with QuickBooks
	 * it is common to associate an expense line item to a Class, which typical is the organization unit.
	 * 
	 * It can be one of these:
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, ExpenseField, Custom3
	 * ItemField, Field Name - this uses a field from the Itemization object, for example, ItemField, Custom1
	 * AllocationField, Field Name - this uses a field from the Allocation object, for example, AllocationField, Custom2
	 * Constant, Value - this uses a constant specified as the Value
	 * 
	 */
	private HashMap<String, String> CustomerID;// key-value pair describing how to determine the Customer for a Detail
	/*
	 * The Customer ID is a field that identifies the customer to associate the transaction. 
	 * 
	 * Itcan be one of these:
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, ExpenseField, ReportID
	 * ItemField, Field Name - this uses a field from the Itemization object, for example, ItemField, Custom1
	 * AllocationField, Field Name - this uses a field from the Allocation object, for example, AllocationField, Custom2
	 * Constant, Value - this uses a constant specified as the Value
	 * 
	 */
	private HashMap<String, String> Billable;// key-value pair describing how to determine the Is Billable value for a Detail
	/*
	 * The Is Billable value can be one of these:
	 * ExpenseField, Field Name - this uses a field from the Expense object, for example, ExpenseField, ReportID
	 * ItemField, Field Name - this uses a field from the Itemization object, for example, Custom1
	 * AllocationField, Field Name - this uses a field from the Allocation object, for example, Custom2
	 * Constant, Value - this uses a constant specified as the Value
	 * 
	 */
	private ArrayList<FieldMapping> DetailCustomFields;// zero or more detail level field mappings; each mapping describes the source form (object type), source field, target form (object type), and target field
	/*
	 * DetailCustomFields provides a way to map an Expense object field to a transaction object field.  It uses the format of
	 * form type, form field to provide the flexibility to specify both the form (object) and the field for both the source and target.
	 */
	
	private int CurrentSerialNumber;// this is a way to keep track of what serial number to generate for a transaction
	
	protected static String key =  null;
	protected static String value = null;
	protected static BasicDBObject Doc;
	
	
	public BatchDefinition() {}// no args constructor
	
	public void display () {
		if (ID != null) {
			out.println("ID: " + ID);
		}
		if (CompanyID != null) {
			out.println("Company ID: " + CompanyID);
		}
		if (PaymentTypeName != null) {
			out.println("Payment Type Name: " + PaymentTypeName);
		}
		if (PaymentTypeCode != null) {
			out.println("Payment Type Code: " + PaymentTypeCode);
		}
		if (Country != null) {
			out.println("Payee Country: " + Country);
		}
		if (Currency != null) {
			out.println("Payee Currency: " + Currency);
		}
		if (Group != null){
			Iterator<Entry<String, String>> it = Group.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		} else {
			out.println("Group = Global");
		}
		if (TransactionType != null) {
			out.println("Transaction Type: " + TransactionType);
		}
		if (PaymentMethod != null) {
			out.println("PaymentMethod: " + PaymentMethod);
		}
		if (PayeeType != null) {
			out.println("Payee Type: " + PayeeType);
		}
		if (LedgerID != null){
			Iterator<Entry<String, String>> it = LedgerID.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Ledger ID Key: " + key + " Ledger ID Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		} else {
			out.println("Ledger ID during display is null");
		}

		if (EntityID != null){
			Iterator<Entry<String, String>> it = EntityID.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Entity ID Key: " + key + " Entity ID Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (TransactionCurrency != null){
			Iterator<Entry<String, String>> it = TransactionCurrency.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Transaction Currency Key: " + key + " Transaction Currency Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (InvoiceNumber != null){
			Iterator<Entry<String, String>> it = InvoiceNumber.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Invoice Number Key: " + key + " Invoice Number Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		out.println("CurrentSerialNumber: " + CurrentSerialNumber);
		if (PostingDate != null){
			Iterator<Entry<String, String>> it = PostingDate.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Posting Date Key: " + key + " Posting Date Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (InvoiceDate != null){
			Iterator<Entry<String, String>> it = InvoiceDate.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Invoice Date Key: " + key + " Invoice Date Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (HeaderAccountObject != null){
			Iterator<Entry<String, String>> it = HeaderAccountObject.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Header Account Key: " + key + " Header Account Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (HeaderDescription != null){
			Iterator<Entry<String, String>> it = HeaderDescription.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Header Description Key: " + key + " Header Description Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (PrivateNote != null){
			Iterator<Entry<String, String>> it = PrivateNote.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Private Note Key: " + key + " Private Note Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (VendorNote != null){
			Iterator<Entry<String, String>> it = VendorNote.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Vendor Note Key: " + key + " Vendor Note Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (HeaderCustomFields != null){
			
			for (FieldMapping mapping:HeaderCustomFields){
				mapping.display();
			}
		}
		if (DetailAccountObject != null){
			Iterator<Entry<String, String>> it = DetailAccountObject.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Detail Accounting Object ID Key: " + key + " Detail Accounting Object ID Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (DetailDescription != null){
			Iterator<Entry<String, String>> it = DetailDescription.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Detail Description Key: " + key + " Detail Description Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (OrgUnitID != null){
			Iterator<Entry<String, String>> it = OrgUnitID.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Org Unit ID Key: " + key + " Org Unit ID Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (CustomerID != null){
			Iterator<Entry<String, String>> it = CustomerID.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Customer ID Key: " + key + " Customer ID Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (Billable != null){
			Iterator<Entry<String, String>> it = Billable.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        out.println("Billable Key: " + key + " Billable Value: " + value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (DetailCustomFields != null){
			for (FieldMapping mapping:DetailCustomFields){
				mapping.display();
			}
		}


	}
	public BasicDBObject getDocument () {//this method returns the batch definition as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myDoc = new BasicDBObject("ID",this.ID);		
		myDoc.append("CompanyID", this.CompanyID);
		myDoc.append("PaymentTypeName",this.PaymentTypeName);
		myDoc.append("PaymentTypeCode",this.PaymentTypeCode);
		myDoc.append("Country", this.Country);
		myDoc.append("Currency", this.Currency);
		if (this.Group != null){
			Iterator<Entry<String, String>> it = Group.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
				myDoc.append("GroupField",key);
				myDoc.append("GroupValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		myDoc.append("TransactionType",this.TransactionType);
		myDoc.append("PaymentMethod", this.PaymentMethod);
		myDoc.append("PayeeType",this.PayeeType);
		if (this.LedgerID != null){
			Iterator<Entry<String, String>> it = LedgerID.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("LedgerIDKey",key);
		        myDoc.append("LedgerIDValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (this.EntityID != null){
			Iterator<Entry<String, String>> it = EntityID.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("EntityIDKey",key);
		        myDoc.append("EntityIDValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (this.TransactionCurrency != null){
			Iterator<Entry<String, String>> it = TransactionCurrency.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("TransactionCurrencyKey",key);
		        myDoc.append("TransactionCurrencyValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (this.InvoiceNumber != null){
			Iterator<Entry<String, String>> it = InvoiceNumber.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("InvoiceNumberKey",key);
		        myDoc.append("InvoiceNumberValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		myDoc.append("CurrentSerialNumber", this.CurrentSerialNumber);
		if (this.PostingDate != null){
			Iterator<Entry<String, String>> it = PostingDate.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("PostingDateKey",key);
		        myDoc.append("PostingDateValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (this.InvoiceDate != null){
			Iterator<Entry<String, String>> it = InvoiceDate.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("InvoiceDateKey",key);
		        myDoc.append("InvoiceDateValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (this.HeaderAccountObject != null){
			Iterator<Entry<String, String>> it = HeaderAccountObject.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("HeaderAccountObjectKey",key);
		        myDoc.append("HeaderAccountObjectValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (this.HeaderDescription != null){
			Iterator<Entry<String, String>> it = HeaderDescription.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("HeaderDescriptionKey",key);
		        myDoc.append("HeaderDescriptionValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (this.PrivateNote != null){
			Iterator<Entry<String, String>> it = PrivateNote.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("PrivateNoteKey",key);
		        myDoc.append("PrivateNoteValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (this.VendorNote != null){
			Iterator<Entry<String, String>> it = VendorNote.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("VendorNoteKey",key);
		        myDoc.append("VendorNoteValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (this.HeaderCustomFields != null){
			FieldMapping mapping = new FieldMapping();
			BasicDBList mappings = new BasicDBList();
			for (int i=0; i<HeaderCustomFields.size(); i++){
				mapping = HeaderCustomFields.get(i);
				Doc = new BasicDBObject();
				Doc = mapping.getDocument();
				mappings.add(Doc);
			}
			myDoc.append("HeaderCustomFields", mappings);
		}
		if (this.DetailAccountObject != null){
			Iterator<Entry<String, String>> it = DetailAccountObject.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("DetailAccountObjectKey",key);
		        myDoc.append("DetailAccountObjectValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (this.DetailDescription != null){
			Iterator<Entry<String, String>> it = DetailDescription.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("DetailDescriptionKey",key);
		        myDoc.append("DetailDescriptionValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}

		if (this.OrgUnitID != null){
			Iterator<Entry<String, String>> it = OrgUnitID.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("OrgUnitIDKey",key);
		        myDoc.append("OrgUnitIDValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (this.CustomerID != null){
			Iterator<Entry<String, String>> it = CustomerID.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("CustomerIDKey",key);
		        myDoc.append("CustomerIDValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (this.Billable != null){
			Iterator<Entry<String, String>> it = Billable.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        key = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
		        value =  (String) pair.getValue();// get the value for the Map entry, which is the group value
		        myDoc.append("BillableKey",key);
		        myDoc.append("BillableValue",value);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		if (this.DetailCustomFields != null){
			BasicDBList mappings = new BasicDBList();
			for (FieldMapping mapping:DetailCustomFields){
				Doc = new BasicDBObject();
				Doc = mapping.getDocument();
				mappings.add(Doc);
			}
			myDoc.append("DetailCustomFields", mappings);
		}
		
		return myDoc;
	}
	public BatchDefinition doctoBatchDefinition (DBObject doc) {
		BatchDefinition myBatchDefinition = new BatchDefinition();
		if (doc.get("ID") != null){
			ID= doc.get("ID").toString();
			myBatchDefinition.setID(ID);
		}
		if (doc.get("CompanyID") != null){
			CompanyID = doc.get("CompanyID").toString();
			myBatchDefinition.setCompanyID(CompanyID);
		}
		if (doc.get("PaymentTypeName") != null){
			PaymentTypeName = doc.get("PaymentTypeName").toString();
			myBatchDefinition.setPaymentTypeName(PaymentTypeName);
		}
		if (doc.get("PaymentTypeCode") != null){
			PaymentTypeCode = doc.get("PaymentTypeCode").toString(); 
			myBatchDefinition.setPaymentTypeCode(PaymentTypeCode);
		}
		if (doc.get("Country") != null){
			Country = doc.get("Country").toString();
			myBatchDefinition.setCountry(Country);
		}
		if (doc.get("Currency") != null){
			Currency = doc.get("Currency").toString();
			myBatchDefinition.setCurrency(Currency);
		}
		if (doc.get("TransactionType") != null){
			TransactionType = doc.get("TransactionType").toString();
			myBatchDefinition.setTransactionType(TransactionType);
		}
		if (doc.get("PaymentMethod") != null){
			PaymentMethod = doc.get("PaymentMethod").toString();
			myBatchDefinition.setPaymentMethod(PaymentMethod);
		}
		if (doc.get("PayeeType") != null){
			PayeeType = doc.get("PayeeType").toString();
			myBatchDefinition.setPayeeType(PayeeType);
		}
		if (doc.get("LedgerIDKey")!= null){
			key = doc.get("LedgerIDKey").toString();
			value = doc.get("LedgerIDValue").toString();
			HashMap<String,String> LedgerID = new HashMap<String,String>();
			LedgerID.put(key, value);
			myBatchDefinition.setLedgerID(LedgerID);
		} 
		if (doc.get("EntityIDKey")!= null){
			key = doc.get("EntityIDKey").toString();
			value = doc.get("EntityIDValue").toString();
			HashMap<String,String> EntityID = new HashMap<String,String>();
			EntityID.put(key, value);
			myBatchDefinition.setEntityID(EntityID);
		}
		if (doc.get("EntityIDKey")!= null){
			key = doc.get("EntityIDKey").toString();
			value = doc.get("EntityIDValue").toString();
			HashMap<String,String> EntityID = new HashMap<String,String>();
			EntityID.put(key, value);
			myBatchDefinition.setEntityID(EntityID);
		}
		if (doc.get("TransactionCurrencyKey")!= null){
			key = doc.get("TransactionCurrencyKey").toString();
			value = doc.get("TransactionCurrencyValue").toString();
			HashMap<String,String> TransactionCurrency = new HashMap<String,String>();
			TransactionCurrency.put(key, value);
			myBatchDefinition.setTransactionCurrency(TransactionCurrency);
		}
		if (doc.get("InvoiceNumberKey") != null){
			key = doc.get("InvoiceNumberKey").toString();
			value = doc.get("InvoiceNumberValue").toString();
			HashMap<String,String> InvoiceNumber = new HashMap<String,String>();
			InvoiceNumber.put(key, value);
			myBatchDefinition.setInvoiceNumber(InvoiceNumber);
		}
		if (doc.get("CurrentSerialNumberKey") != null){
			CurrentSerialNumber = Integer.valueOf(doc.get("CurrentSerialNumber").toString());
			myBatchDefinition.setCurrentSerialNumber(CurrentSerialNumber);
		}
		if (doc.get("PostingDateKey") != null){
			key = doc.get("PostingDateKey").toString();
			value = doc.get("PostingDateValue").toString();
			HashMap<String,String> PostingDate = new HashMap<String,String>();
			PostingDate.put(key, value);
			myBatchDefinition.setPostingDate(PostingDate);
		}
		if (doc.get("InvoiceDateKey") != null){
			key = doc.get("InvoiceDateKey").toString();
			value = doc.get("InvoiceDateValue").toString();
			HashMap<String,String> InvoiceDate = new HashMap<String,String>();
			InvoiceDate.put(key, value);
			myBatchDefinition.setInvoiceDate(InvoiceDate);
		}
		if (doc.get("HeaderAccountObjectKey") != null){
			key = doc.get("HeaderAccountObjectKey").toString();
			value = doc.get("HeaderAccountObjectValue").toString();
			HashMap<String,String> HeaderAccountObject = new HashMap<String,String>();
			HeaderAccountObject.put(key, value);
			myBatchDefinition.setHeaderAccountObject(HeaderAccountObject);
		}
		if (doc.get("HeaderDescriptionKey") != null){
			key = doc.get("HeaderDescriptionKey").toString();
			value = doc.get("HeaderDescriptionValue").toString();
			HashMap<String,String> HeaderDescription = new HashMap<String,String>();
			HeaderDescription.put(key, value);
			myBatchDefinition.setHeaderDescription(HeaderDescription);
		}
		if (doc.get("PrivateNoteKey") != null){
			key = doc.get("PrivateNoteKey").toString();
			value = doc.get("PrivateNoteValue").toString();
			HashMap<String,String> PrivateNote = new HashMap<String,String>();
			PrivateNote.put(key, value);
			myBatchDefinition.setPrivateNote(PrivateNote);
		}
		if (doc.get("VendorNoteKey") != null){
			key = doc.get("VendorNoteKey").toString();
			value = doc.get("VendorNoteValue").toString();
			HashMap<String,String> VendorNote = new HashMap<String,String>();
			VendorNote.put(key, value);
			myBatchDefinition.setVendorNote(VendorNote);
		}
		if (doc.get("HeaderCustomFields") != null){
			BasicDBList customfields = new BasicDBList();
			DBObject Doc;
			customfields = (BasicDBList) doc.get("HeaderCustomFields");
			ArrayList<FieldMapping> mappings = new ArrayList<FieldMapping>();// initialize an ArrayList of FieldMapping object elements
			FieldMapping mapping = new FieldMapping();// placeholder for a FieldMapping object
			
			int fieldcount = customfields.size();//the number of custom field mappings
			if (fieldcount > 0) {//then there are header custom fields documents
				for (int i=0; i<fieldcount; i++) {//iterate for each custom field mapping
					Doc = (DBObject) customfields.get(i);
					mapping = new FieldMapping();// placeholder for a FieldMapping object
					mapping = mapping.doctoFieldMapping(Doc);//get the FieldMapping object
					mappings.add(mapping);
				}
				myBatchDefinition.setHeaderCustomFields(mappings);
			}
			

		}
		if (doc.get("DetailAccountObjectKey") != null){
			key = doc.get("DetailAccountObjectKey").toString();
			value = doc.get("DetailAccountObjectValue").toString();
			HashMap<String,String> DetailAccountObject = new HashMap<String,String>();
			DetailAccountObject.put(key, value);
			myBatchDefinition.setDetailAccountObject(DetailAccountObject);
		}
		if (doc.get("DetailDescriptionKey") != null){
			key = doc.get("DetailDescriptionKey").toString();
			value = doc.get("DetailDescriptionValue").toString();
			HashMap<String,String> DetailDescription = new HashMap<String,String>();
			DetailDescription.put(key, value);
			myBatchDefinition.setDetailDescription(DetailDescription);
		}
		if (doc.get("OrgUnitIDKey") != null){
			key = doc.get("OrgUnitIDKey").toString();
			value = doc.get("OrgUnitIDValue").toString();
			HashMap<String,String> OrgUnitID = new HashMap<String,String>();
			OrgUnitID.put(key, value);
			myBatchDefinition.setOrgUnitID(OrgUnitID);
		}
		if (doc.get("CustomerIDKey") != null){
			key = doc.get("CustomerIDKey").toString();
			value = doc.get("CustomerIDValue").toString();
			HashMap<String,String> CustomerID = new HashMap<String,String>();
			CustomerID.put(key, value);
			myBatchDefinition.setCustomerID(CustomerID);
		}
		if (doc.get("BillableKey") != null){
			key = doc.get("BillableKey").toString();
			value = doc.get("BillableValue").toString();
			HashMap<String,String> Billable = new HashMap<String,String>();
			Billable.put(key, value);
			myBatchDefinition.setBillable(Billable);
		}
		if (doc.get("DetailCustomFields") != null){
			BasicDBList customfields = new BasicDBList();
			DBObject Doc;
			customfields = (BasicDBList) doc.get("DetailCustomFields");
			ArrayList<FieldMapping> mappings = new ArrayList<FieldMapping>();// initialize an ArrayList of FieldMapping object elements
			FieldMapping mapping = new FieldMapping();// placeholder for a FieldMapping object
			
			int fieldcount = customfields.size();//the number of custom field mappings
			if (fieldcount > 0) {//then there are header custom fields documents
				for (int i=0; i<fieldcount; i++) {//iterate for each custom field mapping
					Doc = (DBObject) customfields.get(i);
					mapping = new FieldMapping();
					mapping = mapping.doctoFieldMapping(Doc);//get the FieldMapping object
					mappings.add(mapping);
				}
				myBatchDefinition.setDetailCustomFields(mappings);
			}
		}

		return myBatchDefinition;
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

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public HashMap<String, String> getLedgerID() {
		return LedgerID;
	}

	public void setLedgerID(HashMap<String, String> ledgerID) {
		LedgerID = ledgerID;
	}

	public HashMap<String, String> getEntityID() {
		return EntityID;
	}

	public void setEntityID(HashMap<String, String> entityID) {
		EntityID = entityID;
	}

	public HashMap<String, String> getInvoiceNumber() {
		return InvoiceNumber;
	}

	public void setInvoiceNumber(HashMap<String, String> invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}

	public HashMap<String, String> getPostingDate() {
		return PostingDate;
	}

	public void setPostingDate(HashMap<String, String> postingDate) {
		PostingDate = postingDate;
	}

	public HashMap<String, String> getInvoiceDate() {
		return InvoiceDate;
	}

	public void setInvoiceDate(HashMap<String, String> invoiceDate) {
		InvoiceDate = invoiceDate;
	}

	public HashMap<String, String> getTransactionCurrency() {
		return TransactionCurrency;
	}

	public void setTransactionCurrency(HashMap<String, String> transactionCurrency) {
		TransactionCurrency = transactionCurrency;
	}

	public HashMap<String, String> getHeaderAccountObject() {
		return HeaderAccountObject;
	}

	public void setHeaderAccountObject(HashMap<String, String> liabilityAccount) {
		HeaderAccountObject = liabilityAccount;
	}

	public HashMap<String, String> getHeaderDescription() {
		return HeaderDescription;
	}

	public void setHeaderDescription(HashMap<String, String> headerDescription) {
		HeaderDescription = headerDescription;
	}

	public HashMap<String, String> getPrivateNote() {
		return PrivateNote;
	}

	public void setPrivateNote(HashMap<String, String> privateNote) {
		PrivateNote = privateNote;
	}

	public HashMap<String, String> getVendorNote() {
		return VendorNote;
	}

	public void setVendorNote(HashMap<String, String> vendorNote) {
		VendorNote = vendorNote;
	}

	public ArrayList<FieldMapping> getHeaderCustomFields() {
		return HeaderCustomFields;
	}

	public void setHeaderCustomFields(ArrayList<FieldMapping> headerCustomFields) {
		HeaderCustomFields = headerCustomFields;
	}

	public HashMap<String, String> getDetailAccountObject() {
		return DetailAccountObject;
	}

	public void setDetailAccountObject(HashMap<String, String> accountingObjectID) {
		DetailAccountObject = accountingObjectID;
	}

	public HashMap<String, String> getDetailDescription() {
		return DetailDescription;
	}

	public void setDetailDescription(
			HashMap<String, String> detailDescription) {
		DetailDescription = detailDescription;
	}

	public HashMap<String, String> getOrgUnitID() {
		return OrgUnitID;
	}

	public void setOrgUnitID(HashMap<String, String> orgUnitID) {
		OrgUnitID = orgUnitID;
	}

	public HashMap<String, String> getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(HashMap<String, String> customerID) {
		CustomerID = customerID;
	}

	public HashMap<String, String> getBillable() {
		return Billable;
	}

	public void setBillable(HashMap<String, String> billable) {
		Billable = billable;
	}

	public ArrayList<FieldMapping> getDetailCustomFields() {
		return DetailCustomFields;
	}

	public void setDetailCustomFields(
			ArrayList<FieldMapping> detailCustomFields) {
		DetailCustomFields = detailCustomFields;
	}

	public HashMap<String, String> getGroup() {
		return Group;
	}

	public void setGroup(HashMap<String, String> group) {
		Group = group;
	}

	public String getPaymentMethod() {
		return PaymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		PaymentMethod = paymentMethod;
	}

	public String getTransactionType() {
		return TransactionType;
	}

	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}

	public String getPayeeType() {
		return PayeeType;
	}

	public void setPayeeType(String payeeType) {
		PayeeType = payeeType;
	}

	public int getCurrentSerialNumber() {
		return CurrentSerialNumber;
	}

	public void setCurrentSerialNumber(int currentSerialNumber) {
		CurrentSerialNumber = currentSerialNumber;
	}
	
	
}

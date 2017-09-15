/**
 * 
 */
package com.pivotpayables.nexus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Allocation;
import com.pivotpayables.concurplatform.Expense;
import com.pivotpayables.concurplatform.Itemization;
import com.pivotpayables.concurplatform.JournalEntry;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

/**
 * @author John
 *
 */

public class CreateCreditCardChargeTransaction {
	static final String host = "localhost";
	static final int port = 27017;
	
	
	// Connect to the Company_Data database, and the get the necessary collections
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection batCollection = myMongoCompanyFunctions.setCollection("BatchDefinitions");// get the BatchDefintions collection
	
	@SuppressWarnings("rawtypes")
	public static AccountingTransaction createTransaction (ArrayList<Expense> expenses, BatchDefinition bd) {

	BatchDefinition definition = bd;// copy the provided batch definition into a Batch Definition object
	Expense expense = new Expense();// placeholder for an Expense object
	ArrayList<Itemization> items;// ArrayList of Itemization objects
	Itemization item = new Itemization();// placeholder for an Itemization object
	ArrayList<Allocation> allocations;// ArrayList of Allocation objects
	Allocation allocation = new Allocation();// placeholder for an Allocation object
	ArrayList<JournalEntry> journals;// ArrayList of JournalEntry objects
	JournalEntry journal = new JournalEntry();// placeholder for a JournalEntry object
	HashMap<String, String> CustomFieldsDefs=null;// placheholder for a custom field definition represented as a key-value pair, where key = form name value = field name
	HashMap<String, String> CustomFieldsValue=null;// placheholder for a custom field value represented as a key-value pair, where key = field's name value = field's valueArrayList<Detail> details;// an ArrayList of Detail objects
	Detail detail;
	double amount;
	ArrayList<Detail> details;
	String targetfield;// placeholder to target field name
	String targetvalue;// placeholder to target field value
	String sourceform;// placeholder to source form name
	String sourcefield;// placeholder to source field name
	
	HashMap<String, String> map = new HashMap<String, String>();// placeholder for a HashMap object
	Iterator<Entry<String, String>> it;// placeholder for an Iterator object
	Map.Entry pair;// placeholder for a map entry
	String mapkey;// placeholder for a map entry key
	String mapvalue;// placeholder for a map entry value
	String fieldvalue = "";// placeholder for a transaction field value
	String journalID = "";
	Boolean booleanvalue = null;
	DBObject mydoc=null;// placeholder DBObject
	
	AccountingTransaction transaction = new AccountingTransaction();
	// create the Header
	try {
		
		/*
		 * Begin by creating the transaction level values for the transaction
		 */
		expense = expenses.get(0);// get the first Expense for this Report Payee to use for any Header field that uses a field from the Expense object
		
		
		transaction.setType("CARD");// set Type to CARD indicating this is a Credit Card Charge Transaction
		transaction.setID(GUID.getGUID(4));
		transaction.setCompanyID(expense.getCompanyID());
		
		// Create the Header for an Credit Card Charge Transaction
		
		// get the Vendor who is the Payee, or the Merchant who sold the goods or services purchased with this charge transactions
		
		map = definition.getEntityID();// get the hash map for the Entity ID
		it = map.entrySet().iterator();// create an Iterator to iterate the entries in the Map
		fieldvalue = CreateFieldValue.createCompoundValue(it, expense, "");// create the field value for Payee ID for the Merchant
		transaction.setVendorID(fieldvalue);// set the VendorID to the value the method createFieldValue returns
		
		
		// get the Ledger ID

		map = definition.getLedgerID();// get the hashmap for the Ledger ID
		it = map.entrySet().iterator();// create an Iterator to iterate the entries in the Map
		fieldvalue = CreateFieldValue.createCompoundValue(it, expense, "");// create the field value for Ledger ID
		transaction.setLedgerID(fieldvalue);// set the LedgerID to the value the method createFieldValue returns
		
		// get the Credit Card Account to use as the Header Accounting Object
		
		map = definition.getHeaderAccountObject();// get the HashMap for the Header Account Object, which in the case of a Card is the Asset Account for the Credit Card
		it = map.entrySet().iterator();// create an Iterator to iterate the entries in the Map
		fieldvalue = CreateFieldValue.createCompoundValue(it, expense, "");// create the field value for the Credit Card Account
		transaction.setAccountingObjectID(fieldvalue);// set the AccountingObjectID to the value the method createFieldValue returns
		
		// set the Posting Type to credit
		transaction.setPostingType("credit");
		
		// get the Currency for the Card Transaction; this is the currency the merchant received for this purchase
		map = definition.getTransactionCurrency();// get the hashmap for the Transaction Currency
		it = map.entrySet().iterator();// create an Iterator to iterate the entries in the Map
		fieldvalue = CreateFieldValue.createCompoundValue(it, expense, "");// create the field value for Transaction Currency
		transaction.setCurrencyCode(fieldvalue);;// set the LedgerID to the value the method createFieldValue returns
				
		transaction.setExchangeRate(expense.getExchangeRate());//
		
		// get Invoice Date
		map = definition.getInvoiceDate();
		transaction.setInvoiceDate(CreateFieldValue.createDate(map, expense, ""));//set the InvoiceDate to the value the method createDate returns
		
		//TODO Write Code for handling Posting Date; Presently, Concur Expense doesnt' support Posting Date for Card Transactions
		/*
		// get the Posting Date
		map = definition.getPostingDate();
		transaction.setPostingDate(CreateFieldValue.createDate(map, expense, ""));
		*/
		
		
		ArrayList<FieldMapping> mappings;
		// create custom, Header fields
		if (definition.getHeaderCustomFields() != null){// then there are custom, Header fields
			CustomFieldsDefs = new HashMap<String, String>();// initialize the HashMap for the Header, Custom Field Definition
			CustomFieldsValue = new HashMap<String, String>();
			mappings = definition.getHeaderCustomFields();// get the Field Mappings for the custom, Header fields
			for (FieldMapping fm:mappings){// iterate for each Field Mapping
				sourceform = fm.getSourceFormName();// get the source form's name
				sourcefield = fm.getSourceFieldName();// get the source field's name
				CustomFieldsDefs.put(sourceform, sourcefield);// add the key-value pair for the source form and source field to the CustomFieldDefs object
				targetfield = fm.getTargetFieldName();// get the target field's name									
				/* 
				 * Get the target field's value.
				 * This is the return value from the creatText method.
				 * Pass to createText the CustomFieldDefs 
				 */
				targetvalue = CreateFieldValue.createText(CustomFieldsDefs.entrySet().iterator().next(), expense,"","");
				CustomFieldsValue.put(targetfield, targetvalue);// add the custom field to the HashMap
			}//
			transaction.setCustomFields(CustomFieldsValue);// set the Header, Custom Fields to this Credit Card Charge transaction
		}// if (definition.getHeaderCustomFields().size()>0)
		
		
		/* create Details for the transaction.  There is one Detail record per journal entry.
		 * This involves iterating through each journal entry for each expense associated to the report payee.
		 */
		amount = 0;// initialize the header amount for this Credit Card Charge transaction
		details = new ArrayList<Detail>();// initialize the ArrayList of Detail objects for this Credit Card Charge transaction

		for (Expense itexpense:expenses){// iterate for each expense assoicated to the report payee
			expense = itexpense;
			
			items = expense.getItems();// get the itemizations for this expense
			for (Itemization ititem:items){// iterate for each Itemization
				item = ititem;
				allocations = item.getAllocations();// get the allocations for this itemization
				for (Allocation itallocation:allocations){// iterate for each allocation
					allocation = itallocation;
					journals = allocation.getJournals();// get the journal entries for this allocation
					for (JournalEntry itjournal:journals){// iterate for each Journal Entry
						// create a Detail for each Journal Entry
						//TODO determine why the batch definition object must be refreshed for each iteration
						/*
						 * During debugging this class, I discovered that for each iteration the definition
						 * object lost values for some, but not all, of its fields.  As there is no code that writes 
						 * to the definition object, I was baffled.
						 * 
						 * So, for a work around, I refresh the definition object for each iteration.
						 */
						mydoc = myMongoCompanyFunctions.getDoc(batCollection, bd.getID());// get the Batch Definition document for this Report Payee
						definition = definition.doctoBatchDefinition(mydoc);// convert the Batch Definition document into a Batch Definition object

						journal = itjournal;// get the Journal Entry for this iteration
						journalID = journal.getID();
						// create the Detail for this journal entry
						detail = new Detail();// initialize the Detail object
						detail.setDetailType("Distribution");// set the type to a Distribution

						
						 // get the Accounting Object ID
						
						map = definition.getDetailAccountObject();// get the hash map for the Accounting Object ID for the Detail
						it = map.entrySet().iterator();// create an Iterator to iterate the entries in the Map
						while (it.hasNext()){// iterate through each map entry
							pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
					        mapkey = (String) pair.getKey();// get the key for the Map entry, which is what field to find the group value
					        mapvalue =  (String) pair.getValue();// get the value for the Map entry, which is how to determine the Accounting Object ID
					        if (mapkey.equals("JournalField")){// then use the Journal Account Code value
					        	detail.setAccountingObjectID(journal.getAccountCode());
					        } else if (mapkey.equals("Lookup")){// then the Accounting Object ID must be looked up in an Accounting Object Table
					        	fieldvalue = AccountingLookup.lookupAccountingObjectID(expense, journal.getID(), mapvalue);// get the AccountObjectID from the Accounting Object Table; the map value is the ID for the Accounting Object Table to use
					        	if (fieldvalue != null){
					        		detail.setAccountingObjectID(fieldvalue);
					        	} else {
					        		detail.setAccountingObjectID("Lookup was unable to find an accounting object");
					        	}
					        			
					        } else {// use createText to build the Accounting Object ID value from a series of iterations (pair).
					    		fieldvalue = CreateFieldValue.createCompoundValue(it, expense, "");// create the field value for the Private Note
					        	detail.setAccountingObjectID(fieldvalue);// add the value for this iteration
					        } 
					        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
						}
						
						// set the Posting Type to debit
						detail.setPostingType("debit");
						
						detail.setAmount(journal.getAmount());// set the detail amount to Journal Entry Amount
						if (journal.getAmount() > 0){
							detail.setChargeType("purchase");// set the Charge Type to a purchase
						} else {
							detail.setChargeType("refund");// set the Charge Type to a refund
						}
							
						amount = amount + journal.getAmount();// increment the Credit Card Charge transaction amount by the journal amount
						

						booleanvalue = CreateFieldValue.createBoolean(definition.getBillable(), expense, journalID);//
						detail.setBillable(booleanvalue);// set the IsBillable value using the value createBoolean returns
			
						// Get Detail Description

						map = definition.getDetailDescription();// get the definition for the Detail Description
						it = map.entrySet().iterator();// create an Iterator to iterate the entries in the Map	
						fieldvalue = CreateFieldValue.createCompoundValue(it, expense, "");// create the field value for the Header Description
						detail.setDescription(fieldvalue);// set the Detail Description to the value the method createFieldValue returns
						
						// Get Customer
						map = definition.getCustomerID();// get the definition for the Customer
						it = map.entrySet().iterator();// create an Iterator to iterate the entries in the Map	
						fieldvalue = CreateFieldValue.createCompoundValue(it, expense, "");// create the field value for the Header Description
						detail.setCustomerID(fieldvalue);// set the CustomerID to the value the method createFieldValue returns
						
						// Get Org Unit ID
						map = definition.getOrgUnitID();// get the definition for the Org Unit ID
						it = map.entrySet().iterator();// create an Iterator to iterate the entries in the Map
						fieldvalue = CreateFieldValue.createCompoundValue(it, expense, "");// create the field value for the Header Description
	
						detail.setOrgUnitID(fieldvalue);// set the OrgUnitID to the value the method createFieldValue returns
						
						// create custom, Detail fields

						if (definition.getDetailCustomFields().size()>0){// then there are custom, detail fields
							CustomFieldsDefs = new HashMap<String, String>();// initialize the HashMap for the Detail, Custom Field Definition
							CustomFieldsValue = new HashMap<String, String>();
							mappings = definition.getDetailCustomFields();// get the hash maps for the Field Mappings for the custom, Detail fields

							for (FieldMapping fm:mappings){// iterate for each Field Mapping
								sourceform = fm.getSourceFormName();// get the source form's name
								sourcefield = fm.getSourceFieldName();// get the source field's name
								CustomFieldsDefs.put(sourceform, sourcefield);// add the key-value pair for the source form and source field to the CustomFieldDefs object
								
								targetfield = fm.getTargetFieldName();// get the target field's name									
								/* 
								 * Get the target field's value.
								 * This is the return value from the creatText method.
								 * Pass to createText the CustomFieldDefs 
								 */
								targetvalue = CreateFieldValue.createText(CustomFieldsDefs.entrySet().iterator().next(), expense,"","");
								CustomFieldsValue.put(targetfield, targetvalue);// add the custom field to the HashMap
							}//
							
						}// if (definition.getHeaderCustomFields().size()>0)
						detail.setCustomFields(CustomFieldsValue);// add the custom fields for the Detail
						details.add(detail);// add this detail to the list of details
					}// journal loop
				}// allocation loop
			}// itemization loop

		}// expense loop
		transaction.setDetails(details);// set the Details for this Credit Card Charge transaction
		
		// set the Header amount equal to the sum of the Journal Amount (or, the Detail Amount) for each Journal Entry (or, Detail)
		// This ensures the Header amount equals the sum of the Details amounts
		transaction.setAmount(amount);

		
		} catch (Exception e) {
			transaction = null;
			e.printStackTrace();
		}
		return transaction;
	}

}

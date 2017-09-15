/**
 * 
 */
package com.pivotpayables.test;
import static java.lang.System.out;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.concurplatform.FindConcurCompany;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.nexus.BatchDefinition;
import com.pivotpayables.nexus.FieldMapping;


/**
 * @author John
 * 
 * This class creates batch definitions for a specified company
 *
 */
public class CreateBatchDefinitions {
	private static BatchDefinition definition = new BatchDefinition();// placeholder for a Batch Definition

	
	private static String host= "localhost";//the MongoDB server host
	private static int port = 27017;//the MongoDB server port
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection batCollection = myMongoCompanyFunctions.setCollection("BatchDefinitions");// get the BatchDefinitions collection
	
	
	private static BasicDBObject myDoc;//placeholder for a Basic DBObject document
	private static String CompanyID;
	private static HashMap<String, String> map = new HashMap<String, String>();// placheholder for a HashMap object

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		String key = "0_NJTIPIpaJTl1XOyn9SmfFFrK0=";
		CompanyID = FindConcurCompany.getCompanyID(key);// get the CompanyID for the company associated to the owner of the key

		// Begin by deleting any existing batch definitions for this company
		myMongoCompanyFunctions.deleteDoc(batCollection, "CompanyID", CompanyID);
		
		
		// Create the Batch Definitions

		// Batch Definition for the Cash payment type
		definition.setID(GUID.getGUID(2));
		definition.setCompanyID(CompanyID);
		definition.setPaymentTypeCode("CASH");// Payment Type Code for Cash
		definition.setPaymentTypeName("Cash");// Payment Type Code for Cash
		definition.setPaymentMethod("CNQRPAY");// Payment Method for Concur Pay
		definition.setTransactionType("BILL");// Transaction Type for a Bill or AP Transactions
		definition.setCountry("US");
		definition.setCurrency("USD");
		definition.setPayeeType("EMP");
		
		// Set Ledger ID HashMap
		map = new HashMap<String, String>();
		map.put("Constant", "Apex");// set to use the constant, Apex
		definition.setLedgerID(map);
		
		// Set Entity ID to the Employee ID
		map = new HashMap<String, String>();
		map.put("ExpenseField", "EmployeeID");// set to use the EmployeeID Expense field
		definition.setEntityID(map);
		
		// Set Invoice Number HashMap
		map = new HashMap<String, String>();
		map.put("ExpenseField", "ReportID");// set to use the ReportID Expense field
		definition.setInvoiceNumber(map);
		
		// Set Posting Date HashMap
		map = new HashMap<String, String>();
		map.put("Today", "");// set to use today's date
		definition.setPostingDate(map);
		
		// Set Invoice Date HashMap
		map = new HashMap<String, String>();
		map.put("ExpenseField", "PaidDate");// set to use the PaidDate Expense field
		definition.setInvoiceDate(map);
		
		// Set the Header Accounting Object HashMap
		map = new HashMap<String, String>();
		map.put("Constant", "Accounts Payable");// set to use the constant Accounts Payable
		definition.setHeaderAccountObject(map);
		
		// Set Header Description HashMap
		map = new HashMap<String, String>();
		map.put("ExpenseField", "ReportName");// set to use the ReportName Expense field
		definition.setHeaderDescription(map);
		
		// Set PrivateNote HashMap
		map = new HashMap<String, String>();
		map.put("Constant", "Test Private Note");// set to use the ReportName Expense field
		definition.setPrivateNote(map);
		
		// Set VendorNote HashMap
		map = new HashMap<String, String>();
		map.put("Constant", "Test Vendor Note");// set to use the ReportName Expense field
		definition.setVendorNote(map);
		
		// Set Header Custom Field Mappings

		ArrayList<FieldMapping> mappings = new ArrayList<FieldMapping>();
		FieldMapping mapping = new FieldMapping();
		String SourceFormName = "ExpenseField" ;// the name of the data entry form or "object"; Constant means instead of using a form field, use a constant value. The value is the SourceFieldName.
		String SourceFieldName = "ReportName";// the name of the field on this data entry form or object, or the value of the constant
		String TargetFormName="Bill Header";// the name of the data entry form or "object" in the target application
		String TargetFieldName= "Memo";// the name of the field on this data entry form or object
		mapping.setSourceFormName(SourceFormName);
		mapping.setSourceFieldName(SourceFieldName);
		mapping.setTargetFormName(TargetFormName);
		mapping.setTargetFieldName(TargetFieldName);
		mappings.add(mapping);
		
		mapping = new FieldMapping();
		SourceFormName = "ExpenseField" ;// the name of the data entry form or "object"; Constant means instead of using a form field, use a constant value. The value is the SourceFieldName.
		SourceFieldName = "EmployeeID";// the name of the field on this data entry form or object, or the value of the constant
		TargetFormName="Bill Header";// the name of the data entry form or "object" in the target application
		TargetFieldName= "Reference";// the name of the field on this data entry form or object
		mapping.setSourceFormName(SourceFormName);
		mapping.setSourceFieldName(SourceFieldName);
		mapping.setTargetFormName(TargetFormName);
		mapping.setTargetFieldName(TargetFieldName);
		mappings.add(mapping);
		definition.setHeaderCustomFields(mappings);
		
		// Set Detail Account HashMap
		map = new HashMap<String, String>();
		map.put("Lookup", "BRNA2204PYOC9531");// set to use the constant Accounting Object Lookup
		definition.setDetailAccountObject(map);
		
		// Set Detail Description HashMap
		map = new HashMap<String, String>();
		map.put("ExpenseField", "Description");// set to use the ReportName Expense field
		definition.setDetailDescription(map);
		
		// Set Detail OrgUnit HashMap
		map = new HashMap<String, String>();
		map.put("Constant", "Test Org Unit");// set to use the ReportName Expense field
		definition.setOrgUnitID(map);
		
		// Set Customer HashMap
		map = new HashMap<String, String>();
		map.put("AllocationField", "Custom2");// set to use the ReportName Expense field
		definition.setCustomerID(map);
		
		// Set Billable HashMap
		map = new HashMap<String, String>();
		map.put("AllocationField", "Custom3");// set to use the ReportName Expense field
		definition.setBillable(map);
		
		// Set Detail Custom Field Mappings

		mappings = new ArrayList<FieldMapping>();
		mapping = new FieldMapping();
		SourceFormName = "ItemField" ;// the name of the data entry form or "object"; Constant means instead of using a form field, use a constant value. The value is the SourceFieldName.
		SourceFieldName = "ExpenseTypeName";// the name of the field on this data entry form or object, or the value of the constant
		TargetFormName="Bill Detail";// the name of the data entry form or "object" in the target application
		TargetFieldName= "Expense Type";// the name of the field on this data entry form or object
		mapping.setSourceFormName(SourceFormName);
		mapping.setSourceFieldName(SourceFieldName);
		mapping.setTargetFormName(TargetFormName);
		mapping.setTargetFieldName(TargetFieldName);
		mappings.add(mapping);
		
		mapping = new FieldMapping();
		SourceFormName = "ItemField" ;// the name of the data entry form or "object"; Constant means instead of using a form field, use a constant value. The value is the SourceFieldName.
		SourceFieldName = "Date";// the name of the field on this data entry form or object, or the value of the constant
		TargetFormName="Bill Detail";// the name of the data entry form or "object" in the target application
		TargetFieldName= "Date";// the name of the field on this data entry form or object
		mapping.setSourceFormName(SourceFormName);
		mapping.setSourceFieldName(SourceFieldName);
		mapping.setTargetFormName(TargetFormName);
		mapping.setTargetFieldName(TargetFieldName);
		mappings.add(mapping);
		definition.setDetailCustomFields(mappings);
						
						
		out.println("----------------------");
		myDoc = definition.getDocument();
		myMongoCompanyFunctions.addDoc(batCollection, myDoc);
		definition.display();


		
		//Batch Definition for Company Paid Credit Card
		definition.setID(GUID.getGUID(2));
		definition.setCompanyID(CompanyID);
		definition.setPaymentTypeCode("COPD");// Payment Type Code for Company Paid
		definition.setPaymentTypeName("Company Paid");// Payment Type Code for Company Paid
		definition.setPaymentMethod("AP");
		definition.setTransactionType("CARD");// Transaction Type for a Bill or AP Transactions
		definition.setCountry("US");
		definition.setCurrency("USD");
		definition.setPayeeType("CARD");
		
		// Set Ledger ID HashMap
		map = new HashMap<String, String>();
		map.put("Constant", "Apex");// set to use the constant, Apex
		definition.setLedgerID(map);
		
		// Set the Header Accounting Object HashMap to the Asset account for the Credit Card
		map = new HashMap<String, String>();
		map.put("Constant", "American Express");// set to use the constant American Express
		definition.setHeaderAccountObject(map);
			
		// Set Entity ID to the Merchant Name
		map = new HashMap<String, String>();
		map.put("ExpenseField", "MerchantName");// set to use the MerchantName Expense field
		definition.setEntityID(map);
		
		// Set Transaction Currency to the OriginalCurrency
		map = new HashMap<String, String>();
		map.put("ExpenseField", "OriginalCurrency");// set to use the OriginalCurrency Expense field
		definition.setTransactionCurrency(map);
		
		
		// Set Posting Date HashMap
		map = new HashMap<String, String>();
		map.put("ExpenseField", "PostingDate");// set to the PostingDate Expense Field
		definition.setPostingDate(map);
		
		// Set Invoice Date HashMap
		map = new HashMap<String, String>();
		map.put("ExpenseField", "TransactionDate");// set to use the TransactionDate Expense field
		definition.setInvoiceDate(map);
		
	
		
		// Set PrivateNote HashMap
		map = new HashMap<String, String>();
		map.put("Constant", "Test Private Note");// set to use the ReportName Expense field
		definition.setPrivateNote(map);
		
		definition.setHeaderCustomFields(null);// set Header Custom fields to Null
		// Set Detail Account HashMap
		map = new HashMap<String, String>();
		map.put("Lookup", "BRNA2204PYOC9531");// set to use the constant Accounting Object Lookup
		definition.setDetailAccountObject(map);
		
		// Set Detail Description HashMap
		map = new HashMap<String, String>();
		map.put("ExpenseField", "Description");// set to use the ReportName Expense field
		definition.setDetailDescription(map);
		
		// Set Detail OrgUnit HashMap
		map = new HashMap<String, String>();
		map.put("Constant", "Test Org Unit");// set to use the ReportName Expense field
		definition.setOrgUnitID(map);
		
		// Set Customer HashMap
		map = new HashMap<String, String>();
		map.put("AllocationField", "Custom2");// set to use the ReportName Expense field
		definition.setCustomerID(map);
		
		// Set Billable HashMap
		map = new HashMap<String, String>();
		map.put("AllocationField", "Custom3");// set to use the ReportName Expense field
		definition.setBillable(map);
		
		// Set Detail Custom Field Mappings

		mappings = new ArrayList<FieldMapping>();
		mapping = new FieldMapping();
		SourceFormName = "ItemField" ;// the name of the data entry form or "object"; Constant means instead of using a form field, use a constant value. The value is the SourceFieldName.
		SourceFieldName = "ExpenseTypeName";// the name of the field on this data entry form or object, or the value of the constant
		TargetFormName="Bill Detail";// the name of the data entry form or "object" in the target application
		TargetFieldName= "Expense Type";// the name of the field on this data entry form or object
		mapping.setSourceFormName(SourceFormName);
		mapping.setSourceFieldName(SourceFieldName);
		mapping.setTargetFormName(TargetFormName);
		mapping.setTargetFieldName(TargetFieldName);
		mappings.add(mapping);
		
		mapping = new FieldMapping();
		SourceFormName = "ItemField" ;// the name of the data entry form or "object"; Constant means instead of using a form field, use a constant value. The value is the SourceFieldName.
		SourceFieldName = "Date";// the name of the field on this data entry form or object, or the value of the constant
		TargetFormName="Bill Detail";// the name of the data entry form or "object" in the target application
		TargetFieldName= "Date";// the name of the field on this data entry form or object
		mapping.setSourceFormName(SourceFormName);
		mapping.setSourceFieldName(SourceFieldName);
		mapping.setTargetFormName(TargetFormName);
		mapping.setTargetFieldName(TargetFieldName);
		mappings.add(mapping);
		definition.setDetailCustomFields(mappings);
		
		out.println("----------------------");

		myDoc = definition.getDocument();
		myMongoCompanyFunctions.addDoc(batCollection, myDoc);
		definition.display();
				

	}

}

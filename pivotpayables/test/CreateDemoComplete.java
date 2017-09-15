package com.pivotpayables.test;
/**
 * @author John Toman
 * 6/27/16
 * 
 * This class generates in the MongoDB Company_Data database the necessary data to demonstrate or test using a specified Company's OAuth Token.
 * It creates,
 * - Company that is associated to the Token
 * - an Employee who is associated to this Company, and who is associated to the Token
 * - a Token for the specified Production, OAuth Token
 * 
 * It associates this Token to the Employee. It also creates Field Contexts to create PivotPrime charge documents and Batch Definitions for PivotNexus.
 * 
 * It begins by creating default Locations that are necessary to create the Company and Employee.
 * 
 *
 */

import static java.lang.System.out;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Date;


import java.util.HashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.pivotpayables.concurplatform.Location;
import com.pivotpayables.concurplatform.OAuthToken;
import com.pivotpayables.expensesimulator.Company;
import com.pivotpayables.expensesimulator.Employee;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.nexus.BatchDefinition;
import com.pivotpayables.nexus.FieldMapping;
import com.pivotpayables.prime.FieldContext;

public class CreateDemoComplete {
	private static String LegalName;//compose the company's name
	private static String Domain;//compose the company's domain
	private static String Token;//OAuth access token
	private static String Industry;
	private static String city;
	private static String FormType;// the Concur form type for Field Context
	
	private static String DBAName;
	private static Location HQ;// placeholder for the HQ location
	private static String PostingCurrency;
	private static String firstname = "Admin";// first name of the user (employee) who the OAuth Token is associated
	private static String lastname = "User";// last name of the user
	private static String login;// Pivot Payables user account login for this user
	private static String coid;// placeholder for
	private static String Instance = "https://www.concursolutions.com/";
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");

	
	private static String[] contexts = new String[5];// String array for the contexts
	private static String[] fields = new String[5];// String array for the fields
	private static FieldContext context = new FieldContext();// placeholder for a Field Context
	private static BatchDefinition definition = new BatchDefinition();// placeholder for a Batch Definition

	
	private static String host= "localhost";//the MongoDB server host
	private static int port = 27017;//the MongoDB server port
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection comCollection = myMongoCompanyFunctions.setCollection("Companies");// get the Companies collection
	private static DBCollection empCollection = myMongoCompanyFunctions.setCollection("Employees");// get the Locations collection
	private static DBCollection locCollection = myMongoCompanyFunctions.setCollection("Locations");// get the Locations collection
	private static DBCollection tokenCollection = myMongoCompanyFunctions.setCollection("Tokens");// get the Tokens collection
	private static DBCollection conCollection = myMongoCompanyFunctions.setCollection("FieldContexts");// get the FieldContexts collection
	private static DBCollection batCollection = myMongoCompanyFunctions.setCollection("BatchDefinitions");// get the BatchDefinitions collection
	
	
	private static BasicDBObject myDoc;//placeholder for a Basic DBObject document
	private static Company company  = new Company();// placeholder for a Company object
	private static Employee employee = new Employee ();// placeholder for an Employee object
	private static OAuthToken token = new OAuthToken();
	private static HashMap<String, String> map = new HashMap<String, String>();// placheholder for a HashMap object
	private static Location location = new Location();// placeholder for a Location object
	private static String EmployeeID;
	private static String CompanyID;

	
	public static void main(String[] args) throws ParseException {
		// Begin by creating default locations in the MongoDB
		
		CreateLocations.main(args);// create default locations
		contexts[0] = "Account";
		contexts[1] = "Activity";
		contexts[2] = "Phase";
		contexts[3] = "Task";
		contexts[4] = "IsBillable";
		
		
		// Create Apex Consulting
		
		LegalName = "Apex Consulting";//"Pragmatic Works";//compose the company's name
		Domain = "apexconsulting.com";//"pragmaticworks.com";//compose the company's domain
		Token = "0_NJTIPIpaJTl1XOyn9SmfFFrK0=";//Apex"0_6eHXFt6m8vtwG/rPWaSpci9XI=";// OAuth access token
		Industry = "Consulting";
		city = "Bellevue";
		
		
		DBAName = LegalName;
		
		
		HQ = location.getCity(locCollection, city);
		HQ.display();
		if (HQ.getCountryCode().equals("CA")) {//then it's a Canadian company
			PostingCurrency = "CAD";
		} else if (HQ.getCountryCode().equals("GB")) {//then it's a United Kingdom company
			PostingCurrency = "GBP";
		} else if (HQ.getCountryCode().equals("AU")) {//then it's a Australian company
			PostingCurrency = "AUD";
		}else {//then its an American company
			PostingCurrency = "USD";
		}// if block
	
		
		company.setID(GUID.getGUID(4));
		company.setDBAName(DBAName);
		company.setLegalName(LegalName);
		company.setDomain(Domain);
		company.setIndustry(Industry);
		company.setHQ(HQ);
		company.setPostingCurrency(PostingCurrency);

		
		coid = company.getID();
		out.println("----------------------");
		company.display();
		myDoc = company.getDocument();//convert the Company object into a BasicDBObject document
		myMongoCompanyFunctions.addDoc(comCollection, myDoc);
		
		login="Support@" + Domain;
		
		employee.setID(GUID.getGUID(4));
		employee.setDisplayName(firstname + " "+ lastname);
		employee.setEmployerCompanyID(coid);
		employee.setEmployerCompanyName(LegalName);
		employee.setHome(HQ);
		employee.setLastName(lastname);
		employee.setFirstName(firstname);
		employee.setLoginID(login);

		out.println("----------------------");
		employee.display();
		myDoc = employee.getDocument();// convert this Employee object into a BasicDBObject
		myMongoCompanyFunctions.addDoc(empCollection, myDoc);// add this BasicDBObject to the MongoDB
		
		String strDate = "11/21/2017 04:58:55 AM";// assign the Expriration_Date to this "String date"
		Date ExpirationDate = sdf.parse(strDate);// parse the "String date" into a Date object using the SimpleDateFormat object sdf
		

		EmployeeID = employee.getID();// assign the ID for this Employee
		CompanyID = employee.getEmployerCompanyID();// assign the Company ID for this employee
		
		token.setID(GUID.getGUID(4));
		token.setInstanceURL(Instance);
		token.setToken(Token);
		token.setExpirationDate(ExpirationDate);
		token.setEmployeeID(EmployeeID);
		token.setCompanyID(CompanyID);

		out.println("----------------------");
		token.display();
		myDoc = token.getDocument();// convert the OAuthToken object into a BasicDBObject document
		myMongoCompanyFunctions.addDoc(tokenCollection, myDoc);// add the BasicDBObject to the Tokens collection
		
		
		FormType = "Allocations";// the Concur form type for the Expense Field Context
		fields[0] = "Custom2";
		fields[1] = "Custom1";
		fields[2] = null;
		fields[3] = null;
		fields[4] = "Custom3";
		out.println("----------------------");
		for (int i=0; i<fields.length; i++) {// iterate through each field context
			if (fields[i] != null) {// then there is a field mapped for this context
				context.setID(GUID.getGUID(2));
				context.setCompanyID(CompanyID);
				context.setFormType(FormType);
				context.setFieldID(fields[i]);
				context.setContext(contexts[i]);
				context.display();
				myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
				myMongoCompanyFunctions.addDoc(conCollection, myDoc);
			}
		}
		
		FormType = "PaymentRequest";// the Concur form type for the Invoice Field Context
		fields[0] = "Custom2";
		fields[1] = "Custom1";
		fields[2] = null;
		fields[3] = null;
		fields[4] = "Custom3";
		out.println("----------------------");
		for (int i=0; i<fields.length; i++) {// iterate through each field context
			if (fields[i] != null) {// then there is a field mapped for this context
				context.setID(GUID.getGUID(2));
				context.setCompanyID(CompanyID);
				context.setFormType(FormType);
				context.setFieldID(fields[i]);
				context.setContext(contexts[i]);
				context.display();
				myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
				myMongoCompanyFunctions.addDoc(conCollection, myDoc);
			}
		}
		context = new FieldContext();
		context.setID(GUID.getGUID(2));
		context.setCompanyID(CompanyID);
		context.setFormType("ListID");
		context.setFieldID("gWttu9GqZ$sP$s9cjsbG8aXnTMYTyRvZK4JpA");
		context.setContext("ConnectedList");
		context.display();
		myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
		myMongoCompanyFunctions.addDoc(conCollection, myDoc);
		
		context = new FieldContext();
		context.setID(GUID.getGUID(2));
		context.setCompanyID(CompanyID);
		context.setFormType("Level1");
		context.setFieldID("Custom2");
		context.setContext("ConnectedList");
		context.display();
		myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
		myMongoCompanyFunctions.addDoc(conCollection, myDoc);
		
		context = new FieldContext();
		context.setID(GUID.getGUID(2));
		context.setCompanyID(CompanyID);
		context.setFormType("Level2");
		context.setFieldID("Custom1");
		context.setContext("ConnectedList");
		context.display();
		myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
		myMongoCompanyFunctions.addDoc(conCollection, myDoc);
		
		context = new FieldContext();
		context.setID(GUID.getGUID(2));
		context.setCompanyID(CompanyID);
		context.setFormType("");
		context.setFieldID("Custom3");
		context.setContext("SimpleList");
		context.display();
		myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
		myMongoCompanyFunctions.addDoc(conCollection, myDoc);
		
		
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
		
		// Set Liability Account HashMap
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
		FieldMapping mapping = new FieldMapping();
		ArrayList<FieldMapping> mappings = new ArrayList<FieldMapping>();
		
		String SourceFormName = "ExpenseField" ;// the name of the data entry form or "object"; Constant means instead of using a form field, use a constant value. The value is the SourceFieldName.
		String SourceFieldName = "ReportName";// the name of the field on this data entry form or object, or the value of the constant
		String TargetFormName="Bill Header";// the name of the data entry form or "object" in the target application
		String TargetFieldName= "Memo";// the name of the field on this data entry form or object
		mapping.setSourceFormName(SourceFormName);
		mapping.setSourceFieldName(SourceFieldName);
		mapping.setTargetFormName(TargetFormName);
		mapping.setTargetFieldName(TargetFieldName);
		mappings.add(mapping);
		
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
		map.put("JournalEntryField", "AccountCode");// set to use the constant Accounts Payable
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
		mapping = new FieldMapping();
		mappings = new ArrayList<FieldMapping>();
		
		SourceFormName = "ItemField" ;// the name of the data entry form or "object"; Constant means instead of using a form field, use a constant value. The value is the SourceFieldName.
		SourceFieldName = "ExpenseTypeName";// the name of the field on this data entry form or object, or the value of the constant
		TargetFormName="Bill Detail";// the name of the data entry form or "object" in the target application
		TargetFieldName= "Expense Type";// the name of the field on this data entry form or object
		mapping.setSourceFormName(SourceFormName);
		mapping.setSourceFieldName(SourceFieldName);
		mapping.setTargetFormName(TargetFormName);
		mapping.setTargetFieldName(TargetFieldName);
		mappings.add(mapping);
		
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
		definition.setTransactionType("BILL");// Transaction Type for a Bill or AP Transactions
		definition.setCountry("US");
		definition.setCurrency("USD");
		//definition.setCountry(HQ.getCountryCode());
		//definition.setCurrency(PostingCurrency);
		definition.setPayeeType("CARD");
		
		// Set Ledger ID HashMap
		map = new HashMap<String, String>();
		map.put("Constant", "Apex");// set to use the constant, Apex
		definition.setLedgerID(map);
		
		// Set Card Vendor ID HashMap
		map = new HashMap<String, String>();
		map.put("Constant", "AMEX");// set to use the constant, AMEX
		definition.setCardIssuerVendorID(map);
		
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
		
		// Set Liability Account HashMap
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
		mapping = new FieldMapping();
		mappings = new ArrayList<FieldMapping>();
		
		SourceFormName = "ExpenseField" ;// the name of the data entry form or "object"; Constant means instead of using a form field, use a constant value. The value is the SourceFieldName.
		SourceFieldName = "ReportName";// the name of the field on this data entry form or object, or the value of the constant
		TargetFormName="Bill Header";// the name of the data entry form or "object" in the target application
		TargetFieldName= "Memo";// the name of the field on this data entry form or object
		mapping.setSourceFormName(SourceFormName);
		mapping.setSourceFieldName(SourceFieldName);
		mapping.setTargetFormName(TargetFormName);
		mapping.setTargetFieldName(TargetFieldName);
		mappings.add(mapping);
		
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
		map.put("JournalField", "AccountCode");// set to use the constant Accounts Payable
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
		mapping = new FieldMapping();
		mappings = new ArrayList<FieldMapping>();
		
		SourceFormName = "ItemField" ;// the name of the data entry form or "object"; Constant means instead of using a form field, use a constant value. The value is the SourceFieldName.
		SourceFieldName = "ExpenseTypeName";// the name of the field on this data entry form or object, or the value of the constant
		TargetFormName="Bill Detail";// the name of the data entry form or "object" in the target application
		TargetFieldName= "Expense Type";// the name of the field on this data entry form or object
		mapping.setSourceFormName(SourceFormName);
		mapping.setSourceFieldName(SourceFieldName);
		mapping.setTargetFormName(TargetFormName);
		mapping.setTargetFieldName(TargetFieldName);
		mappings.add(mapping);
		
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
		
		/*
		// Create Nesoi Solutions
				LegalName = "Nesoi Solutions";
				Domain = "nesoisolutions.com";
				Token = "0_H8UDN5wGoLs/KEid6HkF7EB4I=";
				Industry = "Consulting";
				city = "Sydney";
				FormType = "Allocations";// the Concur form type for Field Context
				
				HQ = location.getCity(locCollection, city);
				if (HQ.getCountryCode().equals("CA")) {//then it's a Canadian company
					PostingCurrency = "CAD";
				} else if (HQ.getCountryCode().equals("GB")) {//then it's a United Kingdom company
					PostingCurrency = "GBP";
				} else if (HQ.getCountryCode().equals("AU")) {//then it's a Australian company
					PostingCurrency = "AUD";
				}else {//then its an American company
					PostingCurrency = "USD";
				}// if block
			
				DBAName = LegalName;
				company  = new Company();
				company.setID(GUID.getGUID(4));
				company.setDBAName(DBAName);
				company.setLegalName(LegalName);
				company.setDomain(Domain);
				company.setIndustry(Industry);
				company.setHQ(HQ);
				company.setPostingCurrency(PostingCurrency);
				coid = company.getID();
				out.println("----------------------");
				company.display();
				myDoc = company.getDocument();//convert the Company object into a BasicDBObject document
				myMongoCompanyFunctions.addDoc(comCollection, myDoc);
				
				employee = new Employee();
				login="Support@" + Domain;
				
				employee.setID(GUID.getGUID(4));
				employee.setDisplayName(firstname + " "+ lastname);
				employee.setEmployerCompanyID(coid);
				employee.setEmployerCompanyName(LegalName);
				employee.setHome(HQ);
				employee.setLastName(lastname);
				employee.setFirstName(firstname);
				employee.setLoginID(login);
				out.println("----------------------");
				employee.display();
				myDoc = employee.getDocument();// convert this Employee object into a BasicDBObject
				myMongoCompanyFunctions.addDoc(empCollection, myDoc);// add this BasicDBObject to the MongoDB
				

				EmployeeID = employee.getID();// assign the ID for this Employee
				CompanyID = employee.getEmployerCompanyID();// assign the Company ID for this employee
				
				token.setID(GUID.getGUID(4));
				token.setInstanceURL(Instance);
				token.setToken(Token);
				token.setExpirationDate(ExpirationDate);
				token.setEmployeeID(EmployeeID);
				token.setCompanyID(CompanyID);

				out.println("----------------------");
				token.display();
				myDoc = token.getDocument();// convert the OAuthToken object into a BasicDBObject document
				myMongoCompanyFunctions.addDoc(tokenCollection, myDoc);// add the BasicDBObject to the Tokens collection

				
				fields[0] = "Custom3";// the Account context
				fields[1] = "Custom4";// the Activity context
				fields[2] = null;// the Phase context
				fields[3] = null;// the Task context
				fields[4] = null;// the IsBillable context
				out.println("----------------------");
				for (int i=0; i<fields.length; i++) {// iterate through each field context
					if (fields[i] != null) {// then there is a field mapped for this context
						context.setID(GUID.getGUID(2));
						context.setCompanyID(CompanyID);
						context.setFormType(FormType);
						context.setFieldID(fields[i]);
						context.setContext(contexts[i]);
						context.display();
						myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
						myMongoCompanyFunctions.addDoc(conCollection, myDoc);
					}
				}
				
				context = new FieldContext();
				context.setID(GUID.getGUID(2));
				context.setCompanyID(CompanyID);
				context.setContext("VAT");
				context.setFormType("Domestic");
				context.setFieldID("All");
				context.display();
				myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
				myMongoCompanyFunctions.addDoc(conCollection, myDoc);
				
			
				// Create the Batch Definitions
				definition.setID(GUID.getGUID(2));
				definition.setCompanyID(CompanyID);
				definition.setPaymentTypeCode("CASH");// Payment Type Code for Cash
				definition.setPaymentTypeName("Cash");// Payment Type Code for Cash
				definition.setPaymentMethod("CNQRPAY");
				definition.setCountry(HQ.getCountryCode());
				definition.setCurrency(PostingCurrency);
				definition.setPayeeType("EMP");
				
				
								
				out.println("----------------------");
				definition.display();
				myDoc = definition.getDocument();
				myMongoCompanyFunctions.addDoc(batCollection, myDoc);
				
				//Batch Defintion for Company Paid
				definition.setID(GUID.getGUID(2));
				definition.setCompanyID(CompanyID);
				definition.setPaymentTypeCode("COPD");// Payment Type Code for Cash
				definition.setPaymentTypeName("Company Paid");// Payment Type Code for Cash
				definition.setPaymentMethod("AP");
				definition.setCountry(HQ.getCountryCode());
				definition.setCurrency(PostingCurrency);
				definition.setPayeeType("CARD");
				out.println("----------------------");
				definition.display();
				myDoc = definition.getDocument();
				myMongoCompanyFunctions.addDoc(batCollection, myDoc);
	
		
		// Create Global Excel
		LegalName = "Global Excel";
		Domain = "globalexcel.com";
		Token = "0_VWAHhSrZARamL5zcMyCDAf400=";
		Industry = "Consulting";
		city = "Quebec City";
		
		
		HQ = location.getCity(locCollection, city);
		if (HQ.getCountryCode().equals("CA")) {//then it's a Canadian company
			PostingCurrency = "CAD";
		} else if (HQ.getCountryCode().equals("GB")) {//then it's a United Kingdom company
			PostingCurrency = "GBP";
		} else if (HQ.getCountryCode().equals("AU")) {//then it's a Australian company
			PostingCurrency = "AUD";
		}else {//then its an American company
			PostingCurrency = "USD";
		}// if block
	
		DBAName = LegalName;
		company  = new Company();
		company.setID(GUID.getGUID(4));
		company.setDBAName(DBAName);
		company.setLegalName(LegalName);
		company.setDomain(Domain);
		company.setIndustry(Industry);
		company.setHQ(HQ);
		company.setPostingCurrency(PostingCurrency);
		coid = company.getID();
		out.println("----------------------");
		company.display();
		myDoc = company.getDocument();//convert the Company object into a BasicDBObject document
		myMongoCompanyFunctions.addDoc(comCollection, myDoc);
		
		employee = new Employee();
		login="Support@" + Domain;
		
		employee.setID(GUID.getGUID(4));
		employee.setDisplayName(firstname + " "+ lastname);
		employee.setEmployerCompanyID(coid);
		employee.setEmployerCompanyName(LegalName);
		employee.setHome(HQ);
		employee.setLastName(lastname);
		employee.setFirstName(firstname);
		employee.setLoginID(login);
		out.println("----------------------");
		employee.display();
		myDoc = employee.getDocument();// convert this Employee object into a BasicDBObject
		myMongoCompanyFunctions.addDoc(empCollection, myDoc);// add this BasicDBObject to the MongoDB
		

		EmployeeID = employee.getID();// assign the ID for this Employee
		CompanyID = employee.getEmployerCompanyID();// assign the Company ID for this employee

		
		token.setID(GUID.getGUID(4));
		token.setInstanceURL(Instance);
		token.setToken(Token);
		String strDate = "8/19/2017 04:58:55 AM";// assign the Expriration_Date to this "String date"
		Date ExpirationDate = sdf.parse(strDate);// parse the "String date" into a Date object using the SimpleDateFormat object sdf
		token.setExpirationDate(ExpirationDate);
		token.setEmployeeID(EmployeeID);
		token.setCompanyID(CompanyID);

		out.println("----------------------");
		token.display();
		myDoc = token.getDocument();// convert the OAuthToken object into a BasicDBObject document
		myMongoCompanyFunctions.addDoc(tokenCollection, myDoc);// add the BasicDBObject to the Tokens collection

		FormType = "PaymentRequest";// the Concur form type for Field Context
		fields[0] = "Custom16";// the Account context
		fields[1] = "Custom17";// the Activity context
		fields[2] = null;// the Phase context
		fields[3] = null;// the Task context
		fields[4] = null;// the IsBillable context
		out.println("----------------------");
		for (int i=0; i<fields.length; i++) {// iterate through each field context
			if (fields[i] != null) {// then there is a field mapped for this context
				context.setID(GUID.getGUID(2));
				context.setCompanyID(CompanyID);
				context.setFormType(FormType);
				context.setFieldID(fields[i]);
				context.setContext(contexts[i]);
				context.display();
				myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
				myMongoCompanyFunctions.addDoc(conCollection, myDoc);
			}
		}
		
		context = new FieldContext();
		context.setID(GUID.getGUID(2));
		context.setCompanyID(CompanyID);
		context.setContext("ConnectedList");
		context.setFormType("ListID");
		context.setFieldID("gWjJowLIZz20rT2XPcyOJX$sdhrEppChqjcw");
		context.display();
		myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
		myMongoCompanyFunctions.addDoc(conCollection, myDoc);
		
		context = new FieldContext();
		context.setID(GUID.getGUID(2));
		context.setCompanyID(CompanyID);
		context.setContext("ConnectedList");
		context.setFormType("Level1");
		context.setFieldID("Custom14");
		context.display();
		myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
		myMongoCompanyFunctions.addDoc(conCollection, myDoc);
		
		context = new FieldContext();
		context.setID(GUID.getGUID(2));
		context.setCompanyID(CompanyID);
		context.setContext("ConnectedList");
		context.setFormType("Level2");
		context.setFieldID("Custom15");
		context.display();
		myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
		myMongoCompanyFunctions.addDoc(conCollection, myDoc);
		
		context = new FieldContext();
		context.setID(GUID.getGUID(2));
		context.setCompanyID(CompanyID);
		context.setContext("ConnectedList");
		context.setFormType("Level3");
		context.setFieldID("Custom16");
		context.display();
		myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
		myMongoCompanyFunctions.addDoc(conCollection, myDoc);
		context = new FieldContext();
		context.setID(GUID.getGUID(2));
		context.setCompanyID(CompanyID);
		context.setContext("ConnectedList");
		context.setFormType("Level4");
		context.setFieldID("Custom17");
		context.display();
		myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
		myMongoCompanyFunctions.addDoc(conCollection, myDoc);
		
		//Batch Definition for Invoice Payment Type
		definition.setID(GUID.getGUID(2));
		definition.setCompanyID(CompanyID);
		definition.setPaymentTypeCode("INV");// Payment Type Code for Cash
		definition.setPaymentTypeName("Invoice");// Payment Type Code for Cash
		definition.setPaymentMethod("AP");// Payment Method for pay payee using Accounts Payable
		definition.setCountry(HQ.getCountryCode());
		definition.setCurrency(PostingCurrency);
		definition.setPayeeType("VEN");// Payee Type for a Vendor
		out.println("----------------------");
		definition.display();
		myDoc = definition.getDocument();
		myMongoCompanyFunctions.addDoc(batCollection, myDoc);
		/*
			
		// Create Orbimed
				LegalName = "Orbimed";
				Domain = "obrimed.com";
				Token = "0_TTdJ95hoq3kSx6RwSAqayIGHI=";
				Industry = "Consulting";
				city = "Bellevue";
				
				
				HQ = location.getCity(locCollection, city);
				if (HQ.getCountryCode().equals("CA")) {//then it's a Canadian company
					PostingCurrency = "CAD";
				} else if (HQ.getCountryCode().equals("GB")) {//then it's a United Kingdom company
					PostingCurrency = "GBP";
				} else if (HQ.getCountryCode().equals("AU")) {//then it's a Australian company
					PostingCurrency = "AUD";
				}else {//then its an American company
					PostingCurrency = "USD";
				}// if block
			
				DBAName = LegalName;
				company  = new Company();
				company.setID(GUID.getGUID(4));
				company.setDBAName(DBAName);
				company.setLegalName(LegalName);
				company.setDomain(Domain);
				company.setIndustry(Industry);
				company.setHQ(HQ);
				company.setPostingCurrency(PostingCurrency);
				coid = company.getID();
				out.println("----------------------");
				company.display();
				myDoc = company.getDocument();//convert the Company object into a BasicDBObject document
				myMongoCompanyFunctions.addDoc(comCollection, myDoc);
				
				employee = new Employee();
				login="Support@" + Domain;
				
				employee.setID(GUID.getGUID(4));
				employee.setDisplayName(firstname + " "+ lastname);
				employee.setEmployerCompanyID(coid);
				employee.setEmployerCompanyName(LegalName);
				employee.setHome(HQ);
				employee.setLastName(lastname);
				employee.setFirstName(firstname);
				employee.setLoginID(login);
				out.println("----------------------");
				employee.display();
				myDoc = employee.getDocument();// convert this Employee object into a BasicDBObject
				myMongoCompanyFunctions.addDoc(empCollection, myDoc);// add this BasicDBObject to the MongoDB
				

				EmployeeID = employee.getID();// assign the ID for this Employee
				CompanyID = employee.getEmployerCompanyID();// assign the Company ID for this employee

				
				token.setID(GUID.getGUID(4));
				token.setInstanceURL(Instance);
				token.setToken(Token);
				String strDate = "8/19/2017 04:58:55 AM";// assign the Expriration_Date to this "String date"
				Date ExpirationDate = sdf.parse(strDate);// parse the "String date" into a Date object using the SimpleDateFormat object sdf
				token.setExpirationDate(ExpirationDate);
				token.setEmployeeID(EmployeeID);
				token.setCompanyID(CompanyID);

				out.println("----------------------");
				token.display();
				myDoc = token.getDocument();// convert the OAuthToken object into a BasicDBObject document
				myMongoCompanyFunctions.addDoc(tokenCollection, myDoc);// add the BasicDBObject to the Tokens collection

				FormType = "InvoiceAllocation";// the Concur form type for Field Context
				fields[0] = "Custom1";// the Account context
				fields[1] = "Custom1";// the Activity context
				fields[2] = null;// the Phase context
				fields[3] = null;// the Task context
				fields[4] = null;// the IsBillable context
				out.println("----------------------");
				for (int i=0; i<fields.length; i++) {// iterate through each field context
					if (fields[i] != null) {// then there is a field mapped for this context
						context.setID(GUID.getGUID(2));
						context.setCompanyID(CompanyID);
						context.setFormType(FormType);
						context.setFieldID(fields[i]);
						context.setContext(contexts[i]);
						context.display();
						myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
						myMongoCompanyFunctions.addDoc(conCollection, myDoc);
					}
				}
				
				context = new FieldContext();
				context.setID(GUID.getGUID(2));
				context.setCompanyID(CompanyID);
				context.setContext("SimpleList");
				context.setFormType("gWgNcaQvnVDCsQfhnm2rwPxDYcZBOM7dQyw");
				context.setFieldID("Custom1");
				context.display();
				myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
				myMongoCompanyFunctions.addDoc(conCollection, myDoc);
				
		// Create Hines
		LegalName = "Hines";//"Pragmatic Works";//compose the company's name
		Domain = "hines.com";//"pragmaticworks.com";//compose the company's domain
		Token = "0_/1IiWv12yZM8QvNnt4HwmfOqI=";//Hines
		Industry = "Consulting";
		city = "Quebec City";
		
		
		DBAName = LegalName;
		
		
		HQ = location.getCity(locCollection, city);
		HQ.display();
		if (HQ.getCountryCode().equals("CA")) {//then it's a Canadian company
			PostingCurrency = "CAD";
		} else if (HQ.getCountryCode().equals("GB")) {//then it's a United Kingdom company
			PostingCurrency = "GBP";
		} else if (HQ.getCountryCode().equals("AU")) {//then it's a Australian company
			PostingCurrency = "AUD";
		}else {//then its an American company
			PostingCurrency = "USD";
		}// if block
	
		
		company.setID(GUID.getGUID(4));
		company.setDBAName(DBAName);
		company.setLegalName(LegalName);
		company.setDomain(Domain);
		company.setIndustry(Industry);
		company.setHQ(HQ);
		company.setPostingCurrency(PostingCurrency);

		
		coid = company.getID();
		out.println("----------------------");
		company.display();
		myDoc = company.getDocument();//convert the Company object into a BasicDBObject document
		myMongoCompanyFunctions.addDoc(comCollection, myDoc);
		
		login="Support@" + Domain;
		
		employee.setID(GUID.getGUID(4));
		employee.setDisplayName(firstname + " "+ lastname);
		employee.setEmployerCompanyID(coid);
		employee.setEmployerCompanyName(LegalName);
		employee.setHome(HQ);
		employee.setLastName(lastname);
		employee.setFirstName(firstname);
		employee.setLoginID(login);

		out.println("----------------------");
		employee.display();
		myDoc = employee.getDocument();// convert this Employee object into a BasicDBObject
		myMongoCompanyFunctions.addDoc(empCollection, myDoc);// add this BasicDBObject to the MongoDB
		
		String strDate = "11/21/2017 04:58:55 AM";// assign the Expriration_Date to this "String date"
		Date ExpirationDate = sdf.parse(strDate);// parse the "String date" into a Date object using the SimpleDateFormat object sdf
		

		EmployeeID = employee.getID();// assign the ID for this Employee
		CompanyID = employee.getEmployerCompanyID();// assign the Company ID for this employee
		
		token.setID(GUID.getGUID(4));
		token.setInstanceURL(Instance);
		token.setToken(Token);
		token.setExpirationDate(ExpirationDate);
		token.setEmployeeID(EmployeeID);
		token.setCompanyID(CompanyID);

		out.println("----------------------");
		token.display();
		myDoc = token.getDocument();// convert the OAuthToken object into a BasicDBObject document
		myMongoCompanyFunctions.addDoc(tokenCollection, myDoc);// add the BasicDBObject to the Tokens collection
		*/
		
								
				
	}

}

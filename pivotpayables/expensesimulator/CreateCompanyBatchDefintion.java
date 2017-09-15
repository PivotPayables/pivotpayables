package com.pivotpayables.expensesimulator;

import static java.lang.System.out;

import java.util.HashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.nexus.BatchDefinition;



public class CreateCompanyBatchDefintion {
	
	private static String host= "localhost";//the MongoDB server host
	private static int port = 27017;//the MongoDB server port
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection batCollection = myMongoCompanyFunctions.setCollection("BatchDefinitions");// get the BatchDefinitions collection
	private static BasicDBObject myDoc;//placeholder for a Basic DBObject document
	private static HashMap<String, String> map = new HashMap<String, String>();// placheholder for a HashMap object
	private static BatchDefinition definition = new BatchDefinition();// placeholder for a Batch Definition
	
	public static void CreateBatchDefintions(Company company){
		// Create the Batch Definitions
				definition.setID(GUID.getGUID(2));
				definition.setCompanyID(company.getID());
				definition.setPaymentTypeCode("CASH");// Payment Type Code for Cash
				definition.setPaymentTypeName("Cash");// Payment Type Code for Cash
				definition.setPaymentMethod("CNQRPAY");
				definition.setCountry("US");
				//definition.setCountry(company.getHQ().getCountryCode());
				definition.setCurrency(company.getPostingCurrency());
				definition.setPayeeType("EMP");
				
				
				//Set Group HashMap
				/*
				map = new HashMap<String, String>();
				map.put("ExpenseField", "Custom21");// set to use the LedgerName Expense field
				
				definition.setGroup(map);
				*/
				
				
				// Set Ledger ID HashMap
				map = new HashMap<String, String>();
				map.put("Constant", "ApexConsulting");// set to use the LedgerName Expense field
				definition.setLedgerID(map);
				
				// Set Invoice Number HashMap
				map = new HashMap<String, String>();
				map.put("ExpenseField", "ReportID");// set to use the ReportID Expense field
				definition.setInvoiceNumber(map);
				
				// Set Posting Date HashMap
				map = new HashMap<String, String>();
				map.put("Today", "");// set to use today's date
				definition.setInvoiceDate(map);
				
				// Set Invoice Date HashMap
				map = new HashMap<String, String>();
				map.put("ExpenseField", "PaidDate");// set to use the PaidDate Expense field
				definition.setInvoiceDate(map);
				
				// Set Liability Account HashMap
						map = new HashMap<String, String>();
						map.put("Constant", "Accounts Payable");// set to use the constant Accounts Payable
						definition.setLiabilityAccount(map);
				
				// Set Header Description HashMap
				map = new HashMap<String, String>();
				map.put("ExpenseField", "ReportName");// set to use the ReportName Expense field
				definition.setHeaderDescription(map);
				
				// Set AccountingObjectID HashMap
				map = new HashMap<String, String>();
				map.put("Lookup", "IDXKCM8848IWGC7818");// set to use the Accounting Object Lookup Table
				definition.setAccountingObjectID(map);
				
				// Set Detail Description HashMap
				map = new HashMap<String, String>();
				map.put("ItemField", "ExpenseTypeName");// set to use the Itemization Expense Type Name
				definition.setDetailDescription(map);;
				
				// Set Customer ID HashMap
				map = new HashMap<String, String>();
				map.put("AllocationField", "Custom2");// set to use the Allocation, Custom2
				definition.setCustomerID(map);
							
				// Set IsBillable HashMap
				map = new HashMap<String, String>();
				map.put("AllocationField", "Custom3");// set to use the Allocation, Custom3
				definition.setCustomerID(map);
				
				// Set IsBillable HashMap
				map = new HashMap<String, String>();
				map.put("AllocationField", "Custom3");// set to use the Allocation, Custom3
				definition.setBillable(map);
				
				definition.setCurrentSerialNumber(1);
					
											
				out.println("----------------------");
				definition.display();
				myDoc = definition.getDocument();
				myMongoCompanyFunctions.addDoc(batCollection, myDoc);
				
				//Batch Definition for Company Paid
				definition = new BatchDefinition();// initialize the BatchDefinition object
				definition.setID(GUID.getGUID(2));
				definition.setCompanyID(company.getID());
				definition.setPaymentTypeCode("COPD");// Payment Type Code for Cash
				definition.setPaymentTypeName("Company Paid");// Payment Type Code for Cash
				definition.setPaymentMethod("AP");
				definition.setCountry("US");
				//definition.setCountry(company.getHQ().getCountryCode());
				definition.setCurrency(company.getPostingCurrency());
				definition.setPayeeType("CARD");
				
				definition.setCardIssuerVendorID("Company Paid Credit Card Account");// set the Card Issuer Vendor ID
				
				// Set Ledger ID HashMap
				map = new HashMap<String, String>();
				map.put("Constant", "ApexConsulting");// set to use the LedgerName Expense field
				definition.setLedgerID(map);
				
				// Set Invoice Number HashMap
				map = new HashMap<String, String>();
				map.put("ExpenseField", "ReportID");// set to use the ReportID Expense field
				definition.setInvoiceNumber(map);
				
				// Set Posting Date HashMap
				map = new HashMap<String, String>();
				map.put("Today", "");// set to use today's date
				definition.setInvoiceDate(map);
				
				// Set Invoice Date HashMap
				map = new HashMap<String, String>();
				map.put("ExpenseField", "PaidDate");// set to use the PaidDate Expense field
				definition.setInvoiceDate(map);
				
				// Set Liability Account HashMap
						map = new HashMap<String, String>();
						map.put("Constant", "Accounts Payable");// set to use the constant Accounts Payable
						definition.setLiabilityAccount(map);
				
				// Set Header Description HashMap
				map = new HashMap<String, String>();
				map.put("ExpenseField", "ReportName");// set to use the ReportName Expense field
				definition.setHeaderDescription(map);
				
				// Set AccountingObjectID HashMap
				map = new HashMap<String, String>();
				map.put("Lookup", "IDXKCM8848IWGC7818");// set to use the Accounting Object Lookup Table
				definition.setAccountingObjectID(map);
				
				// Set Detail Description HashMap
				map = new HashMap<String, String>();
				map.put("ItemField", "ExpenseTypeName");// set to use the Itemization Expense Type Name
				definition.setDetailDescription(map);;
				
				// Set Customer ID HashMap
				map = new HashMap<String, String>();
				map.put("AllocationField", "Custom2");// set to use the Allocation, Custom2
				definition.setCustomerID(map);
							
				// Set IsBillable HashMap
				map = new HashMap<String, String>();
				map.put("AllocationField", "Custom3");// set to use the Allocation, Custom3
				definition.setCustomerID(map);
				
				// Set IsBillable HashMap
				map = new HashMap<String, String>();
				map.put("AllocationField", "Custom3");// set to use the Allocation, Custom3
				definition.setBillable(map);
				
				definition.setCurrentSerialNumber(1);
				
				out.println("----------------------");
				definition.display();
				myDoc = definition.getDocument();
				myMongoCompanyFunctions.addDoc(batCollection, myDoc);
	}
}

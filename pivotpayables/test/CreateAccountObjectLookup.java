package com.pivotpayables.test;
/**
 * @author John Toman
 * 7/1/2016
 * 
 * This class generates in the MongoDB Company_Data database an Accounting Object Lookup  list or lul.
 * 
 * It helps with testing CreateCanonicalTransactions because it can create a Lookup List to use for
 * calculating an Accounting Object ID.
 * 
 * 
 * 
 *
 */


import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import static java.lang.System.out;

import java.io.IOException;
import java.text.ParseException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.concurplatform.ExpenseGroupConfiguration;
import com.pivotpayables.concurplatform.ExpensePolicy;
import com.pivotpayables.concurplatform.ExpenseReports;
import com.pivotpayables.concurplatform.ExpenseType;
import com.pivotpayables.concurplatform.FindConcurCompany;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;
import com.pivotpayables.nexus.*;


public class CreateAccountObjectLookup {
	
	private static String host= "localhost";//the MongoDB server host
	private static int port = 27017;//the MongoDB server port
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection lulCollection = myMongoCompanyFunctions.setCollection("LookupLists");// get the Accounting Object Lookup collection
	private static DBCollection lutCollection = myMongoCompanyFunctions.setCollection("LookupTables");// get the Accounting Object Lookup collection
	
	

	
	public static void create(String key, String loginID) throws ParseException, JsonParseException, JsonMappingException, JSONException, IOException  {
		BasicDBObject myDoc;//placeholder for a Basic DBObject document
		ArrayList<ExpenseType> expensetypes = new ArrayList<ExpenseType>();// an ArrayList that holds the expense types names for a particular CompanyID
		ArrayList<ExpensePolicy> policies = new ArrayList<ExpensePolicy>();
		ArrayList<String> names = new ArrayList<String>();
		String name;
		boolean match=false;
		ExpenseReports r = new ExpenseReports();// initiate an ExpenseReports object
		
		
		
		AccountingObjectLookup lookup;// placeholder for AccountingObjectLookup object
		AccountingObjectTable table;// placeholder for AccountingObjectTable object
		HashMap<String, String> leveldefs;// HashMap with key-value pairs for the level definitions: key = form type, value = field name
		
		// Begin by determining the company for the provided OAuth token
		String CompanyID = FindConcurCompany.getCompanyID(key);//
		
		// Next, create an Accounting Object Table
		table = new AccountingObjectTable();// initiate an AccountObjectTable object for this company
		table.setID(GUID.getGUID(4));
		table.setCompanyID(CompanyID);// set the CompanyID to current company
		
		leveldefs = new HashMap<String, String>();// initialize the Level Definitions HashMap
		// set the Level 1 definition to the form type and field ID
		//leveldefs.put("Expense", "Custom1");
		table.setLeveldefs(leveldefs);
		myDoc = table.getDocument();
		myMongoCompanyFunctions.addDoc(lutCollection, myDoc);
		
		
		// Build the list of expense types names across all expense groups and all expense policies
		HashMap<String, String> queryparameters = new HashMap<String, String>();// initialize the queryparameters HashMap
		if (loginID != null){// the a user Login ID was provided
			queryparameters.put("user", loginID);
		}
		queryparameters.put("limit", "25");
		
		ArrayList<ExpenseGroupConfiguration> configs = r.getConfigs(key, queryparameters);
		

		if (configs != null){
			for (ExpenseGroupConfiguration config:configs){
				policies = config.getPolicies();
				for (ExpensePolicy policy:policies){
					expensetypes = policy.getExpenseTypes();
					for (ExpenseType type:expensetypes){
						name = type.getName();
						for (String typename:names){
							if (name.equals(typename)){
								match = true;// this expense type is already in the names ArrayList
								break;// so break this for block
							} else {
								match = false;// this expens type name doesn't match this iteration's name, so keep looking
							}
						}
						if (!match) {// then this expense type name isn't in the names ArrayList, so add it
							names.add(name);
						}
					}
					
				}
			}
		}
		// for this lookup table, there are no levels.  This means there is one accounting object per expense type
		for (String itname:names) {// iterate through each expense type name for this Company
			
			lookup = new AccountingObjectLookup();
			lookup.setID(GUID.getGUID(4));
			lookup.setAccountingTableID(table.getID());
			lookup.setCompanyID(CompanyID);
			lookup.setExpenseTypeName(itname);
			//lookup.setLevel1("Y");
			lookup.setAccountingObjectID(GUID.getGUID(1));// generate a random, four-character Accounting Object ID
			lookup.display();
			myDoc = lookup.getDocument();
			myMongoCompanyFunctions.addDoc(lulCollection, myDoc);
		}

	}
	

}

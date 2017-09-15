/**
 * 
 */
package com.pivotpayables.nexus;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Expense;
import com.pivotpayables.concurplatform.Itemization;
import com.pivotpayables.concurplatform.JournalParentLookup;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

/**
 * @author John
 * This class returns the Accounting Object ID for the provided Itemization object and Accounting Object Lookup Table
 *
 */
public class AccountingLookup {
	static final String host = "localhost";
	static final int port = 27017;
	
	// Connect to the Company_Data database, and the get the necessary collections
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection lutCollection = myMongoCompanyFunctions.setCollection("LookupTables");// get the Accounting Object Lookup Table collection
	private static DBCollection lulCollection = myMongoCompanyFunctions.setCollection("LookupLists");// get the Accounting Object Lookup collection
	
	@SuppressWarnings("rawtypes")
	public static String lookupAccountingObjectID (Expense expense, String journalID, String id) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		/* 
		 * Some Chart of Accounts have a complex way to settle the natural account code for an expense.  In addition to Expense Type, there are one or more criteria that determine
		 * the account code.
		 * 
		 * The idea of a "lookup table" is its a way to represent the Chart of Accounts as multiple level list, where each level in the list represents a criterion.  Concur
		 * calls this idea the "Account Code Hierarchy".  We call the the Accounting Object Table.
		 * 
		 * 
		 * This method searches the MongoDB for an item in a specified Accounting Object Table or item that for the current Itemization (i.e., the global variable item)
		 * that matches the Expense Type Name and the values for the Levels, as defined in the Accounting Object Table
		 * the current batch definition.
		 * 
		 */
		String accoutingobjectID=null;
		String level = null;
		HashMap<String, String> criteria = new HashMap<String, String>();// HashMap with key-value pairs that are the criteria to find the matching Accounting Object ID
		AccountingObjectTable lookuptable = new AccountingObjectTable();
		

		int levelcount=0;
		AccountingObjectLookup lookup = new AccountingObjectLookup();
		HashMap<String, String> levelsmap = new HashMap<String, String>();// HashMap with key-value pairs that are level definitions for the Lookup Table
		Iterator it=null;// placeholder for an iterator
		Map.Entry pair;// placeholder for a map entry
		DBObject doc;// placeholder for a MongoDB document
		
		

		// get the Lookup Table for the specified AccountingOjbectLookupTableID
		
		doc = myMongoCompanyFunctions.getDoc(lutCollection, id);// get from the MongoDB the Lookup Table document for the provided AccountingOjbectTableID
		lookuptable = lookuptable.doctoAccountingObjectTable(doc);// convert the Lookup Table document into a Lookup Table object
		Itemization item = JournalParentLookup.getItemization(expense, journalID);
		criteria.put("CompanyID", lookuptable.getCompanyID());// add the Company ID associated with the specified Accounting Object Table
		criteria.put("ExpenseTypeName", item.getExpenseTypeName());// add the current Itemization, Expense Type Name to the criteria
		
    	levelsmap = lookuptable.getLeveldefs();// get the HashMap for the Level definitions
    	if (levelsmap != null){
    		it = levelsmap.entrySet().iterator();// create an Iterator for this map
    		while (it.hasNext()){// iterate through each map entry
    			pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
    			level = CreateFieldValue.createText(pair, expense, journalID,"");// get the Level value for this iteration
    			criteria.put("Level"+(levelcount+1), level);// add the Level value to the criteria
    			levelcount++;
    			it.remove(); // remove this map entry from the iteration
    		}
    	}
		
    	ArrayList<DBObject> lolDocs = myMongoCompanyFunctions.getDocsByCriteria(lulCollection, criteria);// search the MongoDB for the Accounting Object that match the criteria
		
		 
		if (lolDocs.size()>0){// then it found at least one match
			doc = lolDocs.get(0);// get the first matching Accounting Object
			lookup = lookup.doctoAccountingObjectLookup(doc);// convert document into an AccountingObjectLookup object
			return accoutingobjectID = lookup.getAccountingObjectID();// return the Accounting Object ID
		} else {
			return accoutingobjectID;// return a null AccountingObject
		}
	}
}

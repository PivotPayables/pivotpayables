package com.pivotpayables.nexus;
/*
*
**
* @author John Toman
* 6/30/2017
* 
* This is the base class for a PivotNexus Accounting Object Table
* 
* The AccountingObjectTable object defines the data structure for an Accounting Object Table.  The AccountingLookup class uses it to determine how to calculate the value 
* for each level for the specified, Accounting Object Lookup list.
* 
* The AccountingObjectTable object consists of metadata. 
* It includes a HashMap that includes a key-value pair for how to determine the value for each Level. 
* 
* There are three types of key-value pairs.
* 
* 1.	Form Type/Field Name - is a key-value pair where the key is the object name or "form type" and the value is the field's name.  The object name can be one of the levels in an Expense object.
* 2.	Constant/Value - is a key-value pair where the key is the word, Constant, and the value is the value of this constant.
* 3.	Calculation – is a key-value pair where the key is the word, Calculation, and the value is the unique identifier for the Calculation object (not implemented yet).  A Calculation object includes metadata for how to calculate a value.
* 
* For a given Expense object that PivotNexus is processing into its associated canonical transaction, the CreateCanonicalTransactions class uses this HashMap 
* to gather the necessary data for the specified levels and then find the matching row in the table.  It then determines the
* AccountingObjectID value for this row.
* 
*/



import static java.lang.System.out;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class AccountingObjectTable {
	private String ID;// the unique identifier for this Accounting Object Table
	private String CompanyID;// the Company associated with this Accouting Object Lookup Table
	private HashMap<String, String> leveldefs;// HashMap with key-value pairs for the level definitions. One key-value pair for each level in the table.  There can be zero to five levels.
	
	
	
	protected  static Iterator<Entry<String, String>> it;// placeholder for an Iterator object
	protected  static BasicDBObject Doc;// placeholder for a BasicDBObject object
	protected  static AccountingObjectLookup lookup = new AccountingObjectLookup();// placeholder for an AccountingObjectLookup object
	
	public AccountingObjectTable(){}// no args constructor
	
	public void display(){
		if (ID != null) {
			out.println("ID: " + ID);
		}
		if (CompanyID != null) {
			out.println("Company ID: " + CompanyID);
		}
		if (leveldefs != null){// then display the Level Definitions
			out.println("LEVEL DEFINITIONS");
			it = leveldefs.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			int level = 1;// the level counter
			while (it.hasNext()) {// iterate for each entry in the Map
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        out.println("Level " + level);
		        out.println("-----------------------");
		        out.println("Form Type: " + (String) pair.getKey());
		        out.println("Field Name: " + (String) pair.getValue());
		        out.println();
		        level = level++;// increment the Level counter
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
		}
		
	}
	public BasicDBObject getDocument () {
		BasicDBObject myDoc = new BasicDBObject("ID",this.getID());
		if(CompanyID != null) {
			myDoc.append("CompanyID", CompanyID);
		}
		if (leveldefs != null){
			BasicDBList definitions = new BasicDBList();
			String formtype =  null;
			String fieldname = null;
			Iterator<Entry<String, String>> it = leveldefs.entrySet().iterator();// create an Iterator to iterate the entries in the Map
			while (it.hasNext()) {// iterate for each entry in the Map
				Doc = new BasicDBObject();
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();// get the Map entry (key, value) for this iteration
		        formtype = (String) pair.getKey();// get the key for the Map entry, which is what field to find the form type
		        fieldname =  (String) pair.getValue();// get the value for the Map entry, which is the field name
				Doc.append("FormType",formtype);
				Doc.append("FieldName",fieldname);
				definitions.add(Doc);
		        it.remove(); // avoids a ConcurrentModificationException, remove the entry from the iteration
		    }
			myDoc.append("LevelDefs", definitions);

		}

		return myDoc;
	}
	
	public AccountingObjectTable doctoAccountingObjectTable (DBObject doc) {
		String ID= doc.get("ID").toString();
		String CompanyID= doc.get("CompanyID").toString();
		
		
		// create the Level Definitions
		BasicDBList leveldefs = new BasicDBList();
		DBObject Doc;
		leveldefs = (BasicDBList) doc.get("LevelDefs");
		HashMap<String, String> definitions = new HashMap<String, String>();
		
		int levelcount = leveldefs.size();//the number of Level Definition documents
		if (levelcount > 0) {//then there are level definition documents 
			for (int i=0; i<levelcount; i++) {//iterate for each level definition key-value pair
				Doc = (DBObject) leveldefs.get(i);// get the level definition document for this iteration
				definitions.put(Doc.get("FormType").toString(), Doc.get("FieldName").toString());// get the FormType and FieldName values from the document, and then add to HashMap
			}// i loop
		}// if (levelcount > 0)

		
		AccountingObjectTable myAccountingObjectTable = new AccountingObjectTable();
		myAccountingObjectTable.setID(ID);
		myAccountingObjectTable.setCompanyID(CompanyID);
		myAccountingObjectTable.setLeveldefs(definitions);
		
		return myAccountingObjectTable;
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

	public HashMap<String, String> getLeveldefs() {
		return leveldefs;
	}

	public void setLeveldefs(HashMap<String, String> leveldefs) {
		this.leveldefs = leveldefs;
	}

}

package com.pivotpayables.nexus;
/*
*
**
* @author John Toman
* 3/31/2017
* 
* This is the base class for a PivotNexus Accounting Object Lookup
* 
* The CreateCanonicalTransactions class uses the AccountingObjectLookup, along with an AccountingObjectTable, to calculate 
* the AccountingObjectID for use in a canonical transaction. Its purpose is to provide the metadata necessary to lookup the Accounting Object ID from a 
* specified Accounting Object Table using the combination of an expense type name, and zero to five attributes called, “levels”.
* 
* An Accounting Object Table is a way to implement a rule-based calculation, that uses if/then logic, capable of handling any Chart of Accounts, 
* regardless of how complex it is.
* 
* Here is a summary of this logic,
*      If (condition 1 && condition 2 && condition 3 &&… condition 6) then the Accounting Object ID is this value.
*      
* Here, condition 1 is an expense type name that equals some specified value, and conditions 2 through 6 are attributes of an Expense object, 
* a constant, or a calculated value that equal some specified values.  Conditions 2 through 6 are called, “levels”.  
* 
* There can be zero to five levels in an Accounting Object Table.
* 
* An Accounting Object Table has rows for each unique combination of an expense type name, and the value for its levels.  
* 
* The table has,
*   •	a column for Expense Type Name.
*   •	columns for each level; there can be zero to five levels, and
*   •	a column for the AccountObjectID.
* 
* The way a table lookup works is for a specified Expense Type Name, it finds a row with values for each level that match the specified level values 
* it is looking up.  The purpose of the AccountingObjectLookup object is to provide the values for the expense type name, and the values for the levels to use in this lookup.  
* It also identifies what Accounting Object Table to use.
* 
*/
import static java.lang.System.out;

import com.mongodb.BasicDBObject;/*
*
**

* 6/17/2016
* 
* This is the base class for a PivotNexus Accounting Object Lookup.
* Its purpose is lookup the "Accounting Object" assigned to an Expense Type Name combined with values for one or more "levels" (field values).
* An "Accounting Object" is an accounting code used in posting transactions to an accounting application
*/
import com.mongodb.DBObject;

public class AccountingObjectLookup {
	
	private String ID;// the unique identifier for this Accounting Object Lookup 
	private String AccountingTableID;// the unique identifier for the Accounting Object Table this lookup uses (foreign key)
	private String CompanyID;// the Company associated with this Accounting Object Lookup 
	private String ExpenseTypeName;
	private String Level1;
	private String Level2;
	private String Level3;
	private String Level4;
	private String Level5;
	private String AccountingObjectID;

	
	public AccountingObjectLookup(){}// no args construction
	
	
	
	public void display(){
		if (ID != null) {
			out.println("ID: " + ID);
		}
		if (AccountingTableID != null) {
			out.println("Lookup Table ID" + AccountingTableID);
		}
		if (CompanyID != null) {
			out.println("Company ID: " + CompanyID);
		}
		if (ExpenseTypeName != null) {
			out.println("Expense Type Name: " + ExpenseTypeName);
		}
		if (Level1 != null) {
			out.println("Level 1: " + Level1);
		}
		if (Level2 != null) {
			out.println("Level 2: " + Level2);
		}
		if (Level3 != null) {
			out.println("Level 3: " + Level3);
		}
		if (Level4 != null) {
			out.println("Level 4: " + Level4);
		}
		if (Level5 != null) {
			out.println("Level 5: " + Level5);
		}
		if (AccountingObjectID != null) {
			out.println("Accounting Object ID: " + AccountingObjectID);
		}
	}
	public BasicDBObject getDocument () {
		BasicDBObject myDoc = new BasicDBObject("ID",this.getID());
		myDoc.append("AccountingTableID",this.getAccountingTableID());
		if(CompanyID != null) {
			myDoc.append("CompanyID", CompanyID);
		}
		myDoc.append("ExpenseTypeName",this.getExpenseTypeName());
		myDoc.append("Level1",this.getLevel1());
		if(Level2 != null) {
			myDoc.append("Level2",this.getLevel2());
		}
		if(Level3 != null) {
			myDoc.append("Level3",this.getLevel3());
		}
		if(Level4 != null) {
			myDoc.append("Level4",this.getLevel4());
		}
		if(Level5 != null) {
			myDoc.append("Level5",this.getLevel5());
		}
		myDoc.append("AccountingObjectID",this.AccountingObjectID);

		return myDoc;
	}

	public AccountingObjectLookup doctoAccountingObjectLookup (DBObject doc) {
		String ID= doc.get("ID").toString();
		String AccountingTableID= null;
		String CompanyID= doc.get("CompanyID").toString();
		String ExpenseTypeName=null;
		String Level1=null;
		String Level2=null;
		String Level3=null;
		String Level4=null;
		String Level5=null;
		String AccountingObjectID=null;
		if (doc.get("AccountingTableID") != null){
			AccountingTableID= doc.get("AccountingTableID").toString();
		}
		
		if (doc.get("ExpenseTypeName") != null){
			ExpenseTypeName = doc.get("ExpenseTypeName").toString();
		}
		if (doc.get("Level1") != null){
			Level1 = doc.get("Level1").toString();
		}
		if (doc.get("Level2") != null){
			Level2 = doc.get("Level2").toString();
		}
		if (doc.get("Level3") != null){
			Level3 = doc.get("Level3").toString();
		}
		if (doc.get("Level4") != null){
			Level4 = doc.get("Level4").toString();
		}
		if (doc.get("Level5") != null){
			Level5 = doc.get("Level5").toString();
		}
		if (doc.get("AccountingObjectID") != null){
			AccountingObjectID = doc.get("AccountingObjectID").toString();
		}


		AccountingObjectLookup myAccountingObjectLookup = new AccountingObjectLookup();
		myAccountingObjectLookup.setID(ID);
		myAccountingObjectLookup.setAccountingTableID(AccountingTableID);
		myAccountingObjectLookup.setCompanyID(CompanyID);
		myAccountingObjectLookup.setExpenseTypeName(ExpenseTypeName);
		myAccountingObjectLookup.setLevel1(Level1);
		myAccountingObjectLookup.setLevel2(Level2);
		myAccountingObjectLookup.setLevel3(Level3);
		myAccountingObjectLookup.setLevel4(Level4);
		myAccountingObjectLookup.setLevel5(Level5);
		myAccountingObjectLookup.setAccountingObjectID(AccountingObjectID);
		
		return myAccountingObjectLookup;
	}
	
	public String getID() {
		return ID;
	}



	public void setID(String iD) {
		ID = iD;
	}



	public String getAccountingTableID() {
		return AccountingTableID;
	}



	public void setAccountingTableID(String lookupTableID) {
		AccountingTableID = lookupTableID;
	}



	public String getCompanyID() {
		return CompanyID;
	}



	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}



	public String getExpenseTypeName() {
		return ExpenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		ExpenseTypeName = expenseTypeName;
	}

	public String getLevel1() {
		return Level1;
	}

	public void setLevel1(String level1) {
		Level1 = level1;
	}

	public String getLevel2() {
		return Level2;
	}

	public void setLevel2(String level2) {
		Level2 = level2;
	}

	public String getLevel3() {
		return Level3;
	}

	public void setLevel3(String level3) {
		Level3 = level3;
	}

	public String getLevel4() {
		return Level4;
	}

	public void setLevel4(String level4) {
		Level4 = level4;
	}

	public String getLevel5() {
		return Level5;
	}

	public void setLevel5(String level5) {
		Level5 = level5;
	}

	public String getAccountingObjectID() {
		return AccountingObjectID;
	}

	public void setAccountingObjectID(String accountingObjectID) {
		AccountingObjectID = accountingObjectID;
	}
	

}

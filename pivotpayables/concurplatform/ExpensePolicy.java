/**
 * 
 */
package com.pivotpayables.concurplatform;

/**
 * @author John
 *
 */
import static java.lang.System.out;

import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpensePolicy {
	@JsonProperty
    private String ID;
	@JsonProperty
	private String Name;
	@JsonProperty
	private ArrayList<ExpenseType> ExpenseTypes;



public ExpensePolicy() {};// no args constructor required by Jackson

public void display () {
	if (ID != null) {
		out.println("ID: " + ID);
	}
	if (Name != null) {
		out.println("Name: " + Name);
	}
	if(ExpenseTypes != null) {
		out.println("Expense Types");
		int count = ExpenseTypes.size();
		ExpenseType type = new ExpenseType();
		if (count >0 ) {
		 for (int index=0 ; index<count; index++) {
			type = ExpenseTypes.get(index);
			int itereation = index + 1;
			out.println("Expense Type: " + itereation);
			out.println("-----------------------");
			type.display();
		 }
			out.println("End Expense Types___________________________________________");
			
		}
	}
}
public BasicDBObject getDocument () {//this method returns the journey as a BasicDBObject that can be added to a MongoDB
	BasicDBObject myDoc = new BasicDBObject("ID",this.ID);
	BasicDBObject Doc = null;
	myDoc.append("Name",this.Name);	
	if(ExpenseTypes != null) {
		int count = ExpenseTypes.size();
		ExpenseType type = null;
		if (count >0 ) {
			BasicDBList types = new BasicDBList();
			
		 for (int index=0 ; index<count; index++) {
			type = ExpenseTypes.get(index);

			Doc = new BasicDBObject();
			Doc = type.getDocument();
			types.add(Doc);
		 }
		 myDoc.append("ExpenseTypes", types);

		}
	}
	return myDoc;
}
public ExpensePolicy doctoExpensePolicy (DBObject doc) {
	String ID = null;
	String Name = null;
	ExpensePolicy myExpensePolicy = new ExpensePolicy();
	
	if (doc.get("ID") != null){
		ID= doc.get("ID").toString();
		myExpensePolicy.setID(ID);
	}
	if (doc.get("Name") != null){
		Name = doc.get("Name").toString();
		myExpensePolicy.setName(Name);
	}
	// Create Expense Types
	BasicDBList types = new BasicDBList();
	DBObject Doc;
	types = (BasicDBList) doc.get("ExpenseTypes");
	ArrayList<ExpenseType> expensetypes = new ArrayList<ExpenseType>();// initialize an ArrayList of ExpenseType object elements
	ExpenseType type = new ExpenseType();// placeholder for an ExpenseType object
	
	int typecount = types.size();//the number of ExpenseType documents for this Expense
	if (typecount > 0) {//then there are ExpenseType documents for this expense
		for (int i=0; i<typecount; i++) {//iterate for each Expense Type document
			Doc = (DBObject) types.get(i);
			type = type.doctoExpenseType(Doc);//get the type	
			expensetypes.add(type);//add the Expense Type to the types ArrayList 
		}//i loop
		myExpensePolicy.setExpenseTypes(expensetypes);;// add the Expense Types
	}//if (typecount > 0) block
	return myExpensePolicy;
}

public String getID() {
	return ID;
}

public void setID(String iD) {
	ID = iD;
}

public String getName() {
	return Name;
}

public void setName(String name) {
	Name = name;
}

public ArrayList<ExpenseType> getExpenseTypes() {
	return ExpenseTypes;
}

public void setExpenseTypes(ArrayList<ExpenseType> expenseTypes) {
	ExpenseTypes = expenseTypes;
}


}

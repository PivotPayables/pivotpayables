/**
 * 
 */
package com.pivotpayables.concurplatform;

/**
 * @author John
 *
 */
import static java.lang.System.out;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseType {
	@JsonProperty
    private String Code;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String ExpenseCode;

public ExpenseType() {};// no args constructor required by Jackson

public void display () {
	if (Code != null) {
		out.println("Code: " + Code);
	}
	if (Name != null) {
		out.println("Name: " + Name);
	}
	if (ExpenseCode != null) {
		out.println("Expense Code: " + ExpenseCode);
	}
}
public BasicDBObject getDocument () {//this method returns the journey as a BasicDBObject that can be added to a MongoDB
	BasicDBObject myDoc = new BasicDBObject("Code",this.Code);
	myDoc.append("ExpenseCode",this.ExpenseCode);	
	myDoc.append("ExpenseCode",this.ExpenseCode);		
	return myDoc;
}
public ExpenseType doctoExpenseType (DBObject doc) {
	String Code = null;
	String Name = null;
	String ExpenseCode = null;
	ExpenseType myType = new ExpenseType();
	
	if (doc.get("Code") != null){
		Code= doc.get("Code").toString();
		myType.setCode(Code);
	}
	if (doc.get("Name") != null){
		Name = doc.get("Name").toString();
		myType.setName(Name);
	}
	if (doc.get("ExpenseCode") != null){
		ExpenseCode = doc.get("ExpenseCode").toString();
		myType.setExpenseCode(ExpenseCode);
	}
	return myType;
}



public String getCode() {
	return Code;
}

public void setCode(String code) {
	Code = code;
}

public String getName() {
	return Name;
}

public void setName(String name) {
	Name = name;
}

public String getExpenseCode() {
	return ExpenseCode;
}

public void setExpenseCode(String expenseCode) {
	ExpenseCode = expenseCode;
}


}

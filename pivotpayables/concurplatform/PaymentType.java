package com.pivotpayables.concurplatform;

/**
 * @author John
 * Base clase for a Payment Type object
 *
 */
import static java.lang.System.out;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentType {
	@JsonProperty
    private String ID;
	@JsonProperty
	private String Name;



public PaymentType() {};// no args constructor required by Jackson

public void display () {
	if (ID != null) {
		out.println("ID: " + ID);
	}
	if (Name != null) {
		out.println("Name: " + Name);
	}
}
public BasicDBObject getDocument () {//this method returns the journey as a BasicDBObject that can be added to a MongoDB
	BasicDBObject myDoc = new BasicDBObject("ID",this.ID);
	myDoc.append("Name",this.Name);		
	return myDoc;
}
public PaymentType doctoPaymentType (DBObject doc) {
	String ID = null;
	String Name = null;
	PaymentType myType = new PaymentType();
	
	if (doc.get("ID") != null){
		ID= doc.get("ID").toString();
		myType.setID(ID);
	}
	if (doc.get("Name") != null){
		Name = doc.get("Name").toString();
		myType.setName(Name);
	}
	return myType;
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

}

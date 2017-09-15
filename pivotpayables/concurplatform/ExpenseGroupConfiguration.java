/**
 * 
 */
package com.pivotpayables.concurplatform;

/**
 * @author John
 *
 */
import static java.lang.System.out;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseGroupConfiguration {

	@JsonProperty   
    private String ID;
    
	@JsonProperty
    private String Name;
  
	@JsonProperty
    private ArrayList<PaymentType> PaymentTypes;
	
	@JsonProperty
	private ArrayList<ExpensePolicy> Policies;


	public ExpenseGroupConfiguration() {};// no args constructor required by Jackson

	public void display () {
		if (ID != null) {
			out.println("ID: " + ID);
		}
		if (Name != null) {
			out.println("Name: " + Name);
		}
		if(PaymentTypes != null) {
			out.println("Payment Types");
			out.println();
			int count = PaymentTypes.size();
			PaymentType type = new PaymentType();
			if (count >0 ) {
			 for (int index=0 ; index<count; index++) {
				type = PaymentTypes.get(index);
				int itereation = index + 1;
				out.println("Payment Type: " + itereation);
				out.println("-----------------------");
				type.display();
			 }
				out.println("End Payment Types___________________________________________");
				
			}
		}
		if(Policies != null) {
			out.println("Expense Policies");
			int count = Policies.size();
			ExpensePolicy policy = new ExpensePolicy();
			if (count >0 ) {
			 for (int index=0 ; index<count; index++) {
				policy = Policies.get(index);
				int itereation = index + 1;
				out.println("Expense Policy: " + itereation);
				out.println("-----------------------");
				policy.display();
			 }
				out.println("End Expense Policies___________________________________________");
				
			}
		}
	}
	public BasicDBObject getDocument () {//this method returns the journey as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myDoc = new BasicDBObject("ID",this.ID);
		BasicDBObject Doc = null;
		myDoc.append("Name",this.Name);	
		if(PaymentTypes != null) {
			int count = PaymentTypes.size();
			PaymentType type = null;
			if (count >0 ) {
				BasicDBList types = new BasicDBList();
				
			 for (int index=0 ; index<count; index++) {
				type = PaymentTypes.get(index);

				Doc = new BasicDBObject();
				Doc = type.getDocument();
				types.add(Doc);
			 }
			 myDoc.append("PaymentTypes", types);

			}
		}
		if(Policies != null) {
			int count = Policies.size();
			ExpensePolicy policy = null;
			if (count >0 ) {
				BasicDBList policies = new BasicDBList();
				
			 for (int index=0 ; index<count; index++) {
				policy = Policies.get(index);

				Doc = new BasicDBObject();
				Doc = policy.getDocument();
				policies.add(Doc);
			 }
			 myDoc.append("Policies", policies);

			}
		}
		return myDoc;
	}
	public ExpenseGroupConfiguration doctoExpenseGroupConfiguration (DBObject doc) {
		String ID = null;
		String Name = null;
		ExpenseGroupConfiguration myExpenseGroupConfiguration = new ExpenseGroupConfiguration();
		
		if (doc.get("ID") != null){
			ID= doc.get("ID").toString();
			myExpenseGroupConfiguration.setID(ID);
		}
		if (doc.get("Name") != null){
			Name = doc.get("Name").toString();
			myExpenseGroupConfiguration.setName(Name);
		}
		// Create Expense Types
		BasicDBList types = new BasicDBList();
		DBObject Doc;
		types = (BasicDBList) doc.get("PaymentTypes");
		ArrayList<PaymentType> expensetypes = new ArrayList<PaymentType>();// initialize an ArrayList of PaymentType object elements
		PaymentType type = new PaymentType();// placeholder for an PaymentType object
		
		int typecount = types.size();//the number of PaymentType documents for this Expense
		if (typecount > 0) {//then there are PaymentType documents for this expense
			for (int i=0; i<typecount; i++) {//iterate for each Expense Type document
				Doc = (DBObject) types.get(i);
				type = type.doctoPaymentType(Doc);//get the type	
				expensetypes.add(type);//add the Expense Type to the types ArrayList 
			}//i loop
			myExpenseGroupConfiguration.setPaymentTypes(expensetypes);;// add the Expense Types
		}//if (typecount > 0) block
		
		// Create Expense Policies
		BasicDBList policies = new BasicDBList();
		policies = (BasicDBList) doc.get("Policies");
		ArrayList<ExpensePolicy> expensepolicies = new ArrayList<ExpensePolicy>();// initialize an ArrayList of ExpensePolicy object elements
		ExpensePolicy policy = new ExpensePolicy();// placeholder for an ExpensePolicy object
		
		int policycount = policies.size();//the number of ExpensePolicy documents for this Expense
		if (policycount > 0) {//then there are ExpensePolicy documents for this expense
			for (int i=0; i<policycount; i++) {//iterate for each Expense Policy document
				Doc = (DBObject) policies.get(i);
				policy = policy.doctoExpensePolicy(Doc);//get the policy	
				expensepolicies.add(policy);//add the Expense Policy to the policies ArrayList 
			}//i loop
			myExpenseGroupConfiguration.setPolicies(expensepolicies);// add the Expense Policies
		}//if (policycount > 0) block
		return myExpenseGroupConfiguration;
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

	public ArrayList<PaymentType> getPaymentTypes() {
		return PaymentTypes;
	}

	public void setPaymentTypes(ArrayList<PaymentType> paymentTypes) {
		PaymentTypes = paymentTypes;
	}

	public ArrayList<ExpensePolicy> getPolicies() {
		return Policies;
	}

	public void setPolicies(ArrayList<ExpensePolicy> policies) {
		Policies = policies;
	}

	


}

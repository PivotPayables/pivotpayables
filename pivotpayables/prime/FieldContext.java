
package com.pivotpayables.prime;

/**
 * @author John Toman
 * 6/21/2016
 * 
 * This is the base class for a Concur Form Field Context.
 * 
 * Its purpose is to establish the context of an OrgUnit or CustomField Concur form field.  It is a mapping between a specified form fields and a context.
 * This is necessary to translates a given Concur customer's Concur Expense configuration to one of the standard contexts the Pivot Payables application service uses
 *
 * Add doctoFieldContext method
 * Convert properties into fields using private
 */
import static java.lang.System.out;



import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class FieldContext {
	private String ID;
	private String CompanyID;// the Company this context mapping belongs
	private String FormType;// the Concur form type
	private String FieldID;// the name of the field
	private String Context;// its context

	public FieldContext () {};// no args constructor

	public void display () {
		if (ID != null) {
			out.println("ID: " + ID);
		}
		if (CompanyID != null) {
			out.println("Company ID: " + CompanyID);
		}
		if (FormType != null) {
			out.println("Form Type: " + FormType);
		}
		if (FieldID != null) {
			out.println("Field ID: " + FieldID);
		}
		if (Context != null) {
			out.println("Context: " + Context);
		}
	}
	public BasicDBObject getDocument () {//this method returns the context as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myDoc = new BasicDBObject("ID",this.ID);		
		myDoc.append("CompanyID", this.CompanyID);
		myDoc.append("FormType",this.FormType);
		myDoc.append("FieldID",this.FieldID);
		myDoc.append("Context", this.Context);
		return myDoc;
	}
	public FieldContext doctoFieldContext (DBObject doc) {
		String ID= doc.get("ID").toString();
		String CompanyID = doc.get("CompanyID").toString();
		String FormType = doc.get("FormType").toString();
		String FieldID = doc.get("FieldID").toString();
		String Context = doc.get("Context").toString();



		FieldContext myFieldContext = new FieldContext();
		myFieldContext.setID(ID);
		myFieldContext.setCompanyID(CompanyID);
		myFieldContext.setFormType(FormType);
		myFieldContext.setFieldID(FieldID);
		myFieldContext.setContext(Context);

		return myFieldContext;
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
	public String getFormType() {
		return FormType;
	}
	public void setFormType(String formType) {
		FormType = formType;
	}
	public String getFieldID() {
		return FieldID;
	}
	public void setFieldID(String fieldID) {
		FieldID = fieldID;
	}
	public String getContext() {
		return Context;
	}
	public void setContext(String context) {
		Context = context;
	}
}

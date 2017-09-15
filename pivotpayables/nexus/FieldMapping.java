package com.pivotpayables.nexus;
/*
*
**
* @author John Toman
* 6/8/2016
* 
* This is the base class for a PivotNexus Field Mapping.
* Its purpose is to define how to map a field from a Source Application's data entry form to a field from a Target Application's data entry form.
* 
* There are two types of mappings.
* 1. Source form field to target form field.  This is when there is a form field in the source that holds the value to map to the target.
* 2. Constant to target form field.  This is when there is a constant value to map to the target.
* 

*/



import static java.lang.System.out;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;



public class FieldMapping {

	 private String SourceFormName;// the name of the data entry form or "object"; Constant means instead of using a form field, use a constant value. The value is the SourceFieldName.
	 private String SourceFieldName;// the name of the field on this data entry form or object, or the value of the constant
	 private String TargetFormName;// the name of the data entry form or "object" in the target application
	 private String TargetFieldName;// the name of the field on this data entry form or object

	public FieldMapping() {}// no args constructor
	
	public void display(){
		if (TargetFormName != null) {
			out.println("Target Form Name: " + TargetFormName);
		}
		if (TargetFieldName != null) {
			out.println("Target Field Name: " + TargetFieldName);
		}
		if (SourceFormName != null) {
			out.println("Source Form Name: " + SourceFormName);
		}
		if (SourceFieldName != null) {
			out.println("Source Field Name: " + SourceFieldName);
		}
	}
	public BasicDBObject getDocument () {//this method returns the field mapping object as a BasicDBObject that can be added to a MongoDB
		BasicDBObject myDoc = new BasicDBObject("TargetFormName",this.getTargetFormName());
		myDoc.append("TargetFieldName",this.getTargetFieldName());
		myDoc.append("SourceFormName",this.getSourceFormName());
		myDoc.append("SourceFieldName",this.getSourceFieldName());

		return myDoc;
	}
	public FieldMapping doctoFieldMapping(DBObject doc){
		FieldMapping mapping = new FieldMapping();
		if (doc.get("TargetFormName") != null){
			TargetFormName= doc.get("TargetFormName").toString();
			mapping.setTargetFormName(TargetFormName);
		}
		if (doc.get("TargetFieldName") != null){
			TargetFieldName= doc.get("TargetFieldName").toString();
			mapping.setTargetFieldName(TargetFieldName);
		}
		if (doc.get("SourceFormName") != null){
			SourceFormName= doc.get("SourceFormName").toString();
			mapping.setSourceFormName(SourceFormName);
		}
		if (doc.get("SourceFieldName") != null){
			SourceFieldName= doc.get("SourceFieldName").toString();
			mapping.setSourceFieldName(SourceFieldName);
		}
		return mapping;
	}
	public String getTargetFormName() {
		return TargetFormName;
	}

	public void setTargetFormName(String targetFormName) {
		TargetFormName = targetFormName;
	}

	public String getTargetFieldName() {
		return TargetFieldName;
	}

	public void setTargetFieldName(String targetFieldName) {
		TargetFieldName = targetFieldName;
	}

	public String getSourceFormName() {
		return SourceFormName;
	}

	public void setSourceFormName(String sourceFormName) {
		SourceFormName = sourceFormName;
	}

	public String getSourceFieldName() {
		return SourceFieldName;
	}

	public void setSourceFieldName(String sourceFieldName) {
		SourceFieldName = sourceFieldName;
	}
	
	

}

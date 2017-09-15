package com.pivotpayables.concurplatform;
/**
 * 
 */


/**
 * @author John Toman
 *
 */


import static java.lang.System.out;

import javax.xml.bind.annotation.*;

import org.codehaus.jackson.annotate.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomField {
	@XmlElement
	@JsonProperty
	String Type;// 	The custom field type. Will be one of the following: Amount, Boolean, ConnectedList, Date, Integer, List, Number, Text.
	@XmlElement
	@JsonProperty
	String Value;// The value in the org unit or custom field. For list fields this is the name of the list item. Maximum 48 characters.
	@XmlElement
	@JsonProperty
	String Code;// For list fields this is the list item code.
	@XmlElement
	@JsonProperty
	String ListItemID;// For list fields, this is the list item ID.

	public  CustomField () {}// no arg constructor required by Jackson
	public  CustomField (String type, String value) { // constructor for types other than list
		Type = type;
		Value = value;
	}
	public  CustomField (String type, String value, String code, String itemid) { // constructor for list types
		Type = type;
		Value = value;
		Code = code;
		ListItemID = itemid;
	}
	
	

	public void display () {//method to display the custom field in the console
		if (Type!=null) {
			out.println("Type: " + Type);
			if (Type.equals("List") || Type.equals("ConnectedList")) {
				out.println("ListItemID: " + ListItemID);
				out.println("Code: " + Code);
			}
		}
		
		out.println("Value: " + Value);
		
		out.println("___________________________________________");
		out.println();
	}		
		
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getListItemID() {
		return ListItemID;
	}
	public void setListItemID(String listItemID) {
		ListItemID = listItemID;
	}


}


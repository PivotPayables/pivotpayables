/**
 * 
 */
package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 5/1/2015
 * Base class for the Form Data resource
 *
 */
import static java.lang.System.out;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FormData")
@XmlAccessorType(XmlAccessType.FIELD)
public class FormData {
	@XmlElement
	String Name;
	@XmlElement (name = "FormId")
	String FormID;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getFormID() {
		return FormID;
	}
	public void setFormID(String formID) {
		FormID = formID;
	}
	public void display() {
		out.println("Name: " + Name);
		out.println("Form ID" + FormID);
	}
}

/**
 * 
 */
package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 5/1/2015
 * Base class for Expense Form
 *
 */
import static java.lang.System.out;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FormType")
@XmlAccessorType(XmlAccessType.FIELD)
public class FormType {
	@XmlElement
	String Name;
	@XmlElement
	String FormCode;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getFormCode() {
		return FormCode;
	}
	public void setFormCode(String formCode) {
		FormCode = formCode;
	}
	public void display() {
		out.println("Name: " + Name);
		out.println("Form Code: " + FormCode);
	}

}

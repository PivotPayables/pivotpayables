/**
 * 
 */
package com.pivotpayables.intacctplatform;

import static java.lang.System.out;

/**
 * @author John
 *
 */
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="billto")
@XmlAccessorType (XmlAccessType.FIELD)
public class BillTo {
	@XmlElement(name = "contactname")
	String ContactName;
	
	public void display() {

		out.println("Bill To: " + ContactName);

	}

	public String getContactname() {
		return ContactName;
	}

	public void setContactname(String contactname) {
		this.ContactName = contactname;
	}

}

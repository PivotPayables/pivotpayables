/**
 * 
 */
package com.pivotpayables.intacctplatform;

import static java.lang.System.out;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author John
 *
 */
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="shipto")
@XmlAccessorType (XmlAccessType.FIELD)
public class ShipTo {
	@XmlElement(name = "contactname")
	String ContactName;
	
	public void display() {

		out.println("Ship To: " + ContactName);

	}

	public String getContactname() {
		return ContactName;
	}

	public void setContactname(String contactname) {
		this.ContactName = contactname;
	}

}

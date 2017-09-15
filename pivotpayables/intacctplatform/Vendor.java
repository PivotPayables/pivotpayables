/**
 * 
 */
package com.pivotpayables.intacctplatform;

import static java.lang.System.out;

/**
 * @author John
 * This is the base class for an Intacct Vendor object
 *
 */
import javax.xml.bind.annotation.XmlElement;

public class Vendor {
	@XmlElement(name = "RECORDNO")
	private String Record;
	
	@XmlElement(name = "VENDORID")
	private String VendorID;
	
	@XmlElement(name = "NAME")
	private String Name;
	
	public void display() {
		out.println("Vendor Name: " + Name);
		out.println("Record " + Record);
		out.println("Vendor ID " + VendorID);
	}



	public String getRecord() {
		return Record;
	}



	public void setRecord(String record) {
		Record = record;
	}



	public String getVendorID() {
		return VendorID;
	}

	public void setVendorID(String vendorID) {
		VendorID = vendorID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}

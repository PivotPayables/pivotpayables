/**
 * 
 */
package com.pivotpayables.intacctplatform;

/**
 * @author John
 *
 */
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="data")
@XmlAccessorType (XmlAccessType.FIELD)
public class VendorsList {
	@XmlElement(name = "vendor")
    private List<Vendor> vendors = null;

	public List<Vendor> getVendors() {
		return vendors;
	}

	public void setVendors(List<Vendor> vendors) {
		this.vendors = vendors;
	}
	
	
	
}

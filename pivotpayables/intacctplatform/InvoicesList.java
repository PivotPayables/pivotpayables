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
public class InvoicesList {
	@XmlElement(name = "arinvoice")
    private List<Invoice> invoices = null;

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

}

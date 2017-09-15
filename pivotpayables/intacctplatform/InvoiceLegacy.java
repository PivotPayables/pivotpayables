/**
 * 
 */
package com.pivotpayables.intacctplatform;


import java.util.ArrayList;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * @author John
 * This is the base class for the "legacy", Invoice object used for creating and updating AR invoices.
 *
 */

@XmlRootElement(name="create_invoice")
@XmlAccessorType (XmlAccessType.FIELD)
public class InvoiceLegacy {
	
	@XmlElement(name = "shipto")
	ShipTo ShipTo;
	
	@XmlElement(name = "billto")
	ShipTo BillTo;
	
	@XmlElement(name = "customerid")
	private String CustomerID;

	
	@XmlElement(name = "datecreated")
	DateCreated DateCreated;
	
	@XmlElement(name = "datedue")
	DateDue DateDue;
	

	
	@XmlElementWrapper(name = "invoiceitems")
	@XmlElement(name = "lineitem")
	ArrayList<LineItem> Items;
	
	public InvoiceLegacy () {} // no args constructor
	
	public String getCustomerID() {
		return CustomerID;
	}


	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public DateCreated getDateCreated() {
		return DateCreated;
	}

	public void setDateCreated(DateCreated dateCreated) {
		DateCreated = dateCreated;
	}

	public DateDue getDateDue() {
		return DateDue;
	}

	public void setDateDue(DateDue dateDue) {
		DateDue = dateDue;
	}

	public ShipTo getShipTo() {
		return ShipTo;
	}

	public void setShipTo(ShipTo shipTo) {
		ShipTo = shipTo;
	}

	public ShipTo getBillTo() {
		return BillTo;
	}

	public void setBillTo(ShipTo billTo) {
		BillTo = billTo;
	}

	public ArrayList<LineItem> getItems() {
		return Items;
	}

	public void setItems(ArrayList<LineItem> items) {
		Items = items;
	}
}

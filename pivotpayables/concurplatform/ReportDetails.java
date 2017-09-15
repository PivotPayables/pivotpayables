/**
 * 
 */
package com.pivotpayables.concurplatform;

/**
 * @author John
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "ReportDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportDetails {
	


	@XmlElementWrapper(name = "ExpenseEntriesList")
	@XmlElement(name = "ExpenseEntry")
	//List<ExpenseEntry> expenses;
	ArrayList<ExpenseEntry> expenses;

	
	
	public ReportDetails () {
		// no args constructor
	}

	//@XmlElement
	public ArrayList<ExpenseEntry> getExpenses() {
		return expenses;
	}
	//@XmlElement
	public void setExpenses(ArrayList<ExpenseEntry> expenses) {
		this.expenses = expenses;
	}


}

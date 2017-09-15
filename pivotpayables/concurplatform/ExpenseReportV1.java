/**
 * 
 */
package com.pivotpayables.concurplatform;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import static java.lang.System.out;

/**
 * @author TranseoTech
 *
 */
@XmlRootElement(name = "ReportsList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExpenseReportV1 {
	//@XmlElement
	String ReportName;
	//@XmlElement(name = "Report-Details-Url")
	String ReportDetailsUrl;
	
	public ExpenseReportV1 () {
		// no args constructor
	}
	public void display() {
		out.println("Report Name: " + ReportName);
		out.println("Report Details URL " + ReportDetailsUrl);
	}
}

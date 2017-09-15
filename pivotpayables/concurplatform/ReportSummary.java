package com.pivotpayables.concurplatform;

import static java.lang.System.out;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement (name="ReportSummary")
@XmlAccessorType (XmlAccessType.FIELD)
public class ReportSummary {
	@XmlElement(name="ReportName")
	String ReportName;
	@XmlElement(name = "Report-Details-Url")
	String ReportDetailsUrl;
	@XmlElement(name = "ExpenseUserXUserID")
	String OwnerLoginID;	
	@XmlElement(name = "ReportId")
	String ReportID;

	public String getReportName() {
		return ReportName;
	}
	public void setReportName(String reportName) {
		ReportName = reportName;
	}
	public String getReportDetailsUrl() {
		return ReportDetailsUrl;
	}
	public void setReportDetailsUrl(String reportDetailsUrl) {
		ReportDetailsUrl = reportDetailsUrl;
	}
	public String getOwnerLoginID() {
		return OwnerLoginID;
	}
	public void setOwnerLoginID(String ownerLoginID) {
		OwnerLoginID = ownerLoginID;
	}
	public String getProtectedReportID() {
		String reportid = this.getReportDetailsUrl();

		String[] parts = reportid.split("v1.1/report/");
		reportid = parts[1];	
		return reportid;
	}
	public String getReportID() {
		return ReportID;
	}
	public void setReportID(String reportID) {
		ReportID = reportID;
	}
	public void display() {
		out.println("Report Name: " + ReportName);
		out.println("ReportID " + ReportID);
		out.println("Protected ID " + getProtectedReportID());
		out.println("Owner " + OwnerLoginID);

	}

	

}

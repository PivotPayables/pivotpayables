package com.pivotpayables.nexus;

import java.util.ArrayList;
import java.util.Collections;

public class ReportPayeeSorter {
	ArrayList<ReportPayee> reportPayees = new ArrayList<ReportPayee>();// a placeholder Array List of ReportPayee objects       
	  
	
	public ReportPayeeSorter(ArrayList<ReportPayee> reportpayees) {// constructor         
	    this.reportPayees = reportpayees;     
	}       

	
	public  ArrayList<ReportPayee> getSortedReportPayeeByBatchDefinitionID() {

	    Collections.sort(reportPayees, ReportPayee.batchdefinitionIDComparator);         
	    return reportPayees;     
	} 

}

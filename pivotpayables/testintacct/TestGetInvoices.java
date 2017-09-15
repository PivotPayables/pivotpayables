package com.pivotpayables.testintacct;

import java.util.List;

import com.pivotpayables.intacctplatform.ARFunctions;
import com.pivotpayables.intacctplatform.GetAPISession;
import com.pivotpayables.intacctplatform.GetAuthString;
import com.pivotpayables.intacctplatform.GetControlString;
import com.pivotpayables.intacctplatform.Invoice;


public class TestGetInvoices {
	public static void main(String[] args) {
		String controlstring = GetControlString.create("Pivot Payables", "841!#tGB8b");
		String authstring = GetAuthString.createSession("Guest", "Pivot Payables-DEV", "Workflow1!");
	
		String sessionid = GetAPISession.getSession(controlstring, authstring);
		String query = "RECORDID = 'INV-000051'";
	
		List<Invoice> invoices= ARFunctions.getInvoices(controlstring, sessionid, query);
	
		for (Invoice invoice:invoices){
			invoice.display();
		}
	}

}

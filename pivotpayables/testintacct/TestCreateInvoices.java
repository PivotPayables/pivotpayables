package com.pivotpayables.testintacct;
import java.util.ArrayList;
import java.util.List;

import com.pivotpayables.intacctplatform.GetAPISession;
import com.pivotpayables.intacctplatform.GetAuthString;
import com.pivotpayables.intacctplatform.GetControlString;
import com.pivotpayables.intacctplatform.ARFunctions;
import com.pivotpayables.intacctplatform.BillTo;
import com.pivotpayables.intacctplatform.DateCreated;
import com.pivotpayables.intacctplatform.DateDue;
import com.pivotpayables.intacctplatform.Invoice;
import com.pivotpayables.intacctplatform.LineItem;
import com.pivotpayables.intacctplatform.ShipTo;

import static java.lang.System.out;

public class TestCreateInvoices {
	public static void main(String[] args) {
		String controlstring = GetControlString.create("Pivot Payables", "841!#tGB8b");
		String authstring = GetAuthString.createSession("Guest", "Pivot Payables-DEV", "Workflow1!");

		String sessionid = GetAPISession.getSession(controlstring, authstring);
		
		Invoice invoice = new Invoice();

		invoice.setCustomerID("CUST-00112");
		
		DateCreated created = new DateCreated();
		created.setDay("7");
		created.setMonth("9");
		created.setYear("2017");
		invoice.setDateCreated(created);
		
		DateDue due = new DateDue();
		due.setDay("7");
		due.setMonth("9");
		due.setYear("2017");
		invoice.setDateDue(due);
		

		
		ArrayList<LineItem> Items = new ArrayList<LineItem>();
		LineItem item = new LineItem();
		item.setAccountNumber("4000");
		item.setAmount(1234.56);
		item.setLocationID("100");
		Items.add(item);
		invoice.setItems(Items);
		
		
		String response = ARFunctions.CreateInvoice(controlstring, sessionid, invoice);
		out.println("Response:\n" + response);
		
		/*
		List<Invoice> invoices= ARFunctions.getInvoices(controlstring, sessionid);

		for (Invoice invoice:invoices){
			invoice.display();
		}
		*/

		
		

	}

}

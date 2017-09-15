package com.pivotpayables.testintacct;
import java.util.List;

import com.pivotpayables.intacctplatform.GetAPISession;
import com.pivotpayables.intacctplatform.GetAuthString;
import com.pivotpayables.intacctplatform.GetControlString;
import com.pivotpayables.intacctplatform.GetVendors;
import com.pivotpayables.intacctplatform.Vendor;


public class TestGetVendors {
	public static void main(String[] args) {
		String controlstring = GetControlString.create("Pivot Payables", "841!#tGB8b");
		String authstring = GetAuthString.createSession("Guest", "Pivot Payables-DEV", "Workflow1!");

		String sessionid = GetAPISession.getSession(controlstring, authstring);

		List<Vendor> vendors= GetVendors.getAll(controlstring, sessionid);

		for (Vendor vendor:vendors){
			vendor.display();
		}

		
		

	}
}

package com.pivotpayables.testintacct;

import com.pivotpayables.intacctplatform.GetAPISession;
import com.pivotpayables.intacctplatform.GetAuthString;
import com.pivotpayables.intacctplatform.GetControlString;

public class TestGetAPISession {

	public static void main(String[] args) {
		String sessionid=null;

		String controlstring = GetControlString.create("Pivot Payables", "841!#tGB8b");
		String authstring = GetAuthString.createSession("Guest", "Pivot Payables-DEV", "Workflow1!");

		sessionid = GetAPISession.getSession(controlstring, authstring);
	
		System.out.println(sessionid);

	}

}

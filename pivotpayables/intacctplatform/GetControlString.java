/**
 * 
 */
package com.pivotpayables.intacctplatform;



import com.pivotpayables.expensesimulator.GUID;

/**
 * @author John
 * This class creates a control string for a given Sender ID and Password
 *
 */
public class GetControlString {

	/**
	 * @param args
	 */
	public static String create(String senderid, String password) {
		
		String controlid1 = GUID.getGUID(1);
		String controlstring = "<control><senderid>" + senderid+ "</senderid><password>" + password + "</password><controlid>";
		controlstring = controlstring + controlid1;
		controlstring = controlstring + "</controlid><uniqueid>false</uniqueid><dtdversion>3.0</dtdversion></control>";
		return controlstring;
	}

}

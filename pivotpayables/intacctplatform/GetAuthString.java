/**
 * 
 */
package com.pivotpayables.intacctplatform;

import java.util.UUID;

import com.pivotpayables.expensesimulator.GUID;

/**
 * @author John
 *
 */
public class GetAuthString {
	public static String createSession(String userid, String companyid, String password) {
		String authstring = "<authentication><login><userid>"+ userid + "</userid><companyid>" + companyid +"</companyid><password>"+ password + "</password></login></authentication>";
		return authstring;
	}
}


package com.pivotpayables.concurplatform;

import java.io.IOException;
import java.net.URLEncoder;


import com.pivotpayables.expensesimulator.Employee;
import com.pivotpayables.expensesimulator.GUID;


/**
 * @author John Toman
 * 6/23/15
 * 
 * This class creates an Employee object for the specified Concur login ID.  It uses the Concur Get User API to get the Concur user information to create the Employee.
 *
 * Modified to use updated Employee and Location classes
 */
public class GetConcurUser {

	static final String host = "localhost";
	static final int port = 27017;
	
	public static Employee getEmployee (String user, String key) throws IOException {

		ExpenseReports r = new ExpenseReports();
		String loginid = URLEncoder.encode(user, "UTF-8");// URL Encode it
        ConcurUser cu;
        Employee employee = new Employee();
        cu = r.getUser(loginid, key);
        if (cu != null) {// then there is a Concur User to process
	        employee.setFirstName(cu.getFirstName());
	        employee.setLastName(cu.getLastName());
	        if (!(cu.getMi().isEmpty())) {
	        	employee.setMiddleInitial(cu.getMi());
	        }
	        employee.setDisplayName(employee.getFirstName() + " "+ employee.getLastName());
	        employee.setFullName(employee.getFirstName() + " "+ employee.getMiddleInitial() + " " + employee.getLastName());
	        employee.setLoginID(user);
	        employee.setID(GUID.getGUID(4));
	
	        Location home = new Location();
	        home.setID(GUID.getGUID(2));
	        home.setDisplayName("Unknown");
	        home.setCity("Unknown");
	        home.setCountryCode(cu.getCtryCode());
	        home.setState(cu.getCtrySubCode());
	        employee.setHome(home);
        }// if cu != null block
        
        return employee;

	}

}

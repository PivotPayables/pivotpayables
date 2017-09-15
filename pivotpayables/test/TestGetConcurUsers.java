/**
 * 
 */
package com.pivotpayables.test;

/**
 * @author John Toman
 * 4/28/15
 *
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.concurplatform.ConcurUser;
import com.pivotpayables.concurplatform.ExpenseReports;
import com.pivotpayables.concurplatform.ReportSummary;

import static java.lang.System.out;
public class TestGetConcurUsers {

	static final String key = "iKYcEPLgIZuQid+5987q2bQs3Fk=";
	protected static final String user = null;//"william.never@pivotpayables.com";
	protected static ExpenseReports r = new ExpenseReports();
	ArrayList<ConcurUser> users = new ArrayList<ConcurUser>();
	private static ConcurUser cu = null; //placeholder for a ConcuUser
	
	
	public static void main(String[] args) throws IOException {
		cu = r.getUser(user, key);
		cu.display();

	}

}

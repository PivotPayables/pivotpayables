package com.pivotpayables.test;
import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.concurplatform.ExpenseReport;
import com.pivotpayables.concurplatform.ExpenseReports;



/**
 * 
 */

/**
 * @author TranseoTech
 *
 */
public class TestPostReport {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void main(String[] args) throws JsonParseException, JsonMappingException, JSONException, IOException {
		String name;
		ExpenseReports r = new ExpenseReports();
		for (int i=0; i <51; i++) {
			name = "Test Report " + i;
			ExpenseReport report = new ExpenseReport(name); // create a test, expense report
			r.addExpenseReport(report,  "iKYcEPLgIZuQid+5987q2bQs3Fk=");
		}





	}

}

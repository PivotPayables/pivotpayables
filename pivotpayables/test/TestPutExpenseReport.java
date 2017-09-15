package com.pivotpayables.test;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.concurplatform.ExpenseReport;
import com.pivotpayables.concurplatform.ExpenseReports;

public class TestPutExpenseReport {
	static final String key = "iKYcEPLgIZuQid+5987q2bQs3Fk=";
	public static void main(String[] args) throws JsonParseException, JsonMappingException, JSONException, IOException {

		ExpenseReports r = new ExpenseReports();
		String reportid = "E58AD2725546418C968A";
		ExpenseReport report = r.getReport(key, reportid);
		if (report != null) {
			//String name=report.getName();
			report.setName("Update Trial 9");
			report.setPurpose("Purpose Test");
			r.updateExpenseReport(report, key);
			r.getReport(key, reportid);
			report.display();

		}


	}

}

package com.pivotpayables.test;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.concurplatform.ExpenseReports;
import com.pivotpayables.concurplatform.ReportSummary;

import static java.lang.System.out;

public class TestSubmitReport {
	static final String key = "iKYcEPLgIZuQid+5987q2bQs3Fk=";
	static final String keyowner = "wsadmin@@Connect-PivotPayables.com";
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, JSONException, IOException, JAXBException {

		ExpenseReports r = new ExpenseReports();
		List<ReportSummary> reportSummaries;
		ReportSummary summary = null;
		String reportid= "gWnxRWWiyd6QgXm0uOW4cl5uCGY8Trh1mQw";


		
		reportSummaries = r.getReportsList(key);

		if (reportSummaries != null) {
			for (int i = 0; i <reportSummaries.size(); i++) {
				summary = reportSummaries.get(i);
				summary.display();
				out.println(summary.getProtectedReportID());
			}
		} else {
			out.println("Empty");
		}
		

		
		out.println(r.submitExpenseReport(reportid, key));
						

	}
}

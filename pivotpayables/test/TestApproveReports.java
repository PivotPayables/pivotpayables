package com.pivotpayables.test;

import static java.lang.System.out;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.JsonParseException;

import com.pivotpayables.concurplatform.ExpenseReport;
import com.pivotpayables.concurplatform.ExpenseReports;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;


public class TestApproveReports {
	protected static final String adminkey = "lyJkLrmhtXS1Ou4bEBMYnQLfllQ=";// key for wsadmin
	protected static final String processorkey = "E5BdG6Y9UILVLMESAVZgbNIWTV0=";// key for US Processor
	protected static final String resourceurl = "https://www.concursolutions.com/api/expense/expensereport/v1.1/report/" ;
	
	public static void main(String[] args) throws IOException, JAXBException, JSONException {


		Map queryparameters = new HashMap();
		ArrayList<ExpenseReport> reports = new ArrayList<ExpenseReport>();
		ExpenseReport report; // placeholder for an ExpenseReport object
		ExpenseReports r = new ExpenseReports();
		queryparameters.put("user", "ALL");
		//queryparameters.put("approvalStatusCode", "A_PEND");
		queryparameters.put("approvalStatusCode", "A_ACCO");
		reports = r.getReports(adminkey, queryparameters);// get list of reports pending approval

		
		if (reports.size() > 0) {
		    for(int i=0; i<reports.size(); i++){
		        report = reports.get(i);// get ExpenseReport for this iteration
		        String status = report.getApprovalStatusCode();
		        
			       out.println(report.getID());
			       out.println(report.getName());
			       out.println(report.getApprovalStatusCode());
			       out.println(r.approveExpenseReport(report, status, processorkey));
		    }// for i loop 
		}// if block

	}// main


}
	

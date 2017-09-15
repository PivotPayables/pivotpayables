package com.pivotpayables.test;
/*
**
* @author John Toman
*5/18/2016
* This tests the getReportDetails method of the Expenses class
*
*/



import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import java.io.IOException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.concurplatform.Allocation;
import com.pivotpayables.concurplatform.Expense;
import com.pivotpayables.concurplatform.ExpenseEntry;
import com.pivotpayables.concurplatform.ExpenseReport;
import com.pivotpayables.concurplatform.ExpenseReports;
import com.pivotpayables.concurplatform.Expenses;
import com.pivotpayables.concurplatform.GetReports;
import com.pivotpayables.concurplatform.Itemization;
import com.pivotpayables.concurplatform.JournalEntry;
import com.pivotpayables.concurplatform.Journey;
import com.pivotpayables.concurplatform.ReportDetails;
import com.pivotpayables.concurplatform.VATData;
import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

import static java.lang.System.out;


public class TestGetReportDetails {
	private static Map<String, String> queryparameters = new HashMap<String, String>();// a HashMap to hold key-value pairs for a query parameter
	private static ExpenseReport report; // placeholder for an ExpenseReport object
	private static ArrayList<ExpenseReport> reports = new ArrayList<ExpenseReport>();
	private static ArrayList<Expense> expenses;
	private static ArrayList <ExpenseEntry> xpenses;
	private static ExpenseEntry entry;
	private static Expense expense;
	private static ReportDetails details;
	private static Expenses e = new Expenses();// Expenses functions
	private static ExpenseReports r = new ExpenseReports();// ExpenseReports functions
	private static String reportID;
	private static ArrayList<Itemization> items;
	private static Itemization item;
	private static ArrayList<Allocation> allocations;
	private static Allocation allocation;
	private static ArrayList<JournalEntry> journals;
	private static JournalEntry journal;
	private static ArrayList<VATData> vatdetails;
	private static VATData vatdetail;
	private static BasicDBObject Doc;
	private static DBObject doc;
	
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, JSONException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String key = "0_2n15wT8FUUsTzLk2Y/dHakpbA=";//apex //"0_H8UDN5wGoLs/KEid6HkF7EB4I=";//nesoi solutions//"0_H8UDN5wGoLs/KEid6HkF7EB4I=";//nesoi solutions//"0_TTdJ95hoq3kSx6RwSAqayIGHI=";//OrbiMed//"VOKbC9xM2kftRcgSEwV2AAw+yLY=";//Coffman Engineers//"0_TH3kHDP+XJ3eJGh5qZignLpnc=";//HFZ//"0_v9841SkONKrlZPFDPcQMV1La8=";//Renoir Group//"0_ZKoC9r4R83iEXvHqit9oJlzvA=";//West Yost//"swcvpgXlclo7BzaISyA5uUYfhD4=";//LifeWay//"e15mCgSh7e0KrKZ4w363v1rSlek=";//K & L Beverage//"myL0YTKnKt9Bu57bxxNWgIB/LdA=";//Chopin Vodka//"gEDKnWeGyPDdaTBBXTQX8YEWodE=";//Voicebrook//"iohyl8b1gbjyvMK3iH++bwkMybY=";// assurance resources;//"e15mCgSh7e0KrKZ4w363v1rSlek=";//K&L //
		String lastsuccess = "2016-09-01";
		String status;
		
		queryparameters = new HashMap<String, String>();// initialize the queryparameters HashMap
		String user = "ALL";// search for results for All report owners; not just reports from the user the key is associated
		queryparameters.put("user", user);
		//queryparameters.put("paymentStatusCode", "P_PAID");// search for reports with a Paid Date after this date
		queryparameters.put("paidDateAfter", lastsuccess);// search for reports with a Paid Date after this date
		//queryparameters.put("submitDateAfter", lastsuccess);// search for reports with a Paid Date after this date
		
		reports = r.getReports(key, queryparameters);
		
		if (reports.size() > 0) { // then there is at least one report to process
			for(int i=0; i<reports.size(); i++){// iterate for each report 

		        report = reports.get(i);// get ExpenseReport for this iteration
		        reportID= report.getID();
		        details = e.getReportDetails(reportID, key);
		        
		        if (details != null){
					xpenses = details.getExpenses();// get the list of Expense Entry objects for this report
					
					for (int j=0; j< xpenses.size(); j++) { // Iterate for each Expense Entry object in the report
						
						entry = xpenses.get(j);// get the Expense Entry object for this iteration
					
						expense = e.getExpense(report, entry.getReportEntryID(), key);// get the Expense object for this Expense Entry
						expense.setID(GUID.getGUID(4));// assign a Pivot Payables GUID to this expense
						expense.setAmountDueCompanyCard(report.getAmountDueCompanyCard());
						expense.setAmountDueEmployee(report.getAmountDueEmployee());
						expense.setReportName(report.getName());
						expense.setReportPurpose(report.getPurpose());
						expense.setReportTotal(report.getTotal());
						expense.setPolicyID(report.getPolicyID());
						expense.setLedgerName(report.getLedgerName());
						expense.setEmployeeCountry(report.getCountry());
						expense.setEmployeeState(report.getCountrySubdivision());
						expense.setPersonalExpense(report.getPersonalAmount());
						expense.setTotalApprovedAmount(report.getTotalApprovedAmount());
					

						items = entry.getItems();// get the list of Itemization objects for the Expense Entry
						for (int k=0; k<items.size(); k++){// iterate for each Itemization object for this Expense Entry
							item = items.get(k);//  get the Itemization object for this iteration
							allocations = item.getAllocations();// get the list of Allocation objects for this Itemization
							for (int l =0; l<allocations.size(); l++){// Iterate for each Allocation object for this Itemization
								allocation = allocations.get(l);// get the Allocation object for this iteration
								allocation.display();
								journals = allocation.getJournals();// get the list of Journal Entry objects for this Allocation
								for (int m=0; m<journals.size(); m++){// iterate for each Journal Entry object
									journal = journals.get(m);// get the Journal Entry object for this iteration
									journal.setID(GUID.getGUID(4));//assign a Pivot Payables GUID for this Journal Entry
									journal.setPostedCurrency(expense.getPostedCurrency());
									journals.set(m, journal);
								}
								vatdetails = allocation.getVatdetails();// get the list of VAT Data objects for this Allocation
								for (int m=0; m<vatdetails.size(); m++){// iterate for each VAT Data object
									//out.println("Checkpoint: VAT Detail");
									vatdetail = vatdetails.get(m);// get the VAT Data object for this iteration
									vatdetail.setID(GUID.getGUID(4));//assign a Pivot Payables GUID for this VAT Data
									vatdetail.setOriginalCurrency(expense.getOriginalCurrency());
									vatdetail.setPostedCurrency(expense.getPostedCurrency());
									vatdetails.set(m, vatdetail);
								}
								allocation.setID(GUID.getGUID(4));//assign a Pivot Payables GUID for this Allocation
								allocation.setJournals(journals);// add the Journal Entries to the Allocation
								allocation.setVatdetails(vatdetails);// add the VAT Data to the Allocation


								allocations.set(l, allocation);
							}
							item.setID(GUID.getGUID(4));// assign a Pivot Payables GUID to this itemization
							item.setEntryID(expense.getID());// copy the ID for the parent, Expense
							item.setEntry_ID(expense.getEntry_ID());
							item.setTransactionCurrencyCode(expense.getOriginalCurrency());// copy the Expense's original currency to the itemization
							item.setPostedCurrency(expense.getPostedCurrency());// copy the Expense's posted currency to the itemization
							item.setAllocations(allocations);
							items.set(k, item);
						}
						expense.setItems(items);// add the Itemizations to the Expense
						/*
						myMongoExpenseFunctions.addDoc(expCollection, Doc);
						doc = myMongoExpenseFunctions.getDoc(expCollection, Doc.getString("ID").toString());
						expense = new Expense();
						expense = myMongoExpenseFunctions.doctoExpense(Doc);
						
						
						
							Doc = expense.getDocument();
							expense = new Expense();
							expense = myMongoExpenseFunctions.doctoExpense(Doc);

							expense.display();
						*/
		        	}
		        }
		        
			}
		}//(reports.size() > 0)

	}

}

package com.pivotpayables.concurplatform;


/**
 * @author John Toman
 * 8/2/15
 * This class gets Expense reports the specified query parameters and creates a CSV for each expense it gets.
 * This is a way to capture expense data from Concur to use for demonstrations and testing.
 *
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import com.pivotpayables.expensesimulator.GUID;




import static java.lang.System.out;

public class GetReports  {

	
	private static ArrayList<ExpenseReport> reports = new ArrayList<ExpenseReport>();
	private static ArrayList<Expense> expenses = new ArrayList<Expense>();
	private static ArrayList<Itemization> items = new ArrayList<Itemization>();
	private static ArrayList<Allocation> allocations = new ArrayList<Allocation>();

	


	
	
	private static Expenses expensefunctions = new Expenses();// Expenses functions
	private static ExpenseReports r = new ExpenseReports();// ExpenseReports functions
	private static String vendor=null;

	private static Path currentRelativePath = Paths.get("");// find the current relative path

	private static String CSVfilepath = (currentRelativePath.toAbsolutePath().toString()) + "/CSVFiles/";// construct the absolute path to the ImageFiles directoryprivate static String filename;// placeholder for image file name
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String FILE_HEADER = "Employee Name, Expense Type, Vendor Name, Date, Amount, Currency, Posted Amount, Posted Currency, Custom1, Custom2, Custom3, Custom4, Custom5 , Custom6, Custom7, "
			+ "Custom8, Custom9, Custom10, Custom11, Custom12, Custom13, Custom14, Custom15 , Custom16, Custom17, "
			+ "Custom18, Custom19, Custom20,Custom21, Custom22, Custom23, Custom24, Custom25 , Custom26, Custom27, "
			+ "Custom28, Custom29, Custom30,Custom31, Custom32, Custom33, Custom34, Custom35 , Custom36, Custom37, "
			+ "Custom38, Custom39, Custom40,OrgUnit1, OrgUnit2, OrgUnit3, OrgUnit4, OrgUnit5, OrgUnit6, Report ID, Entry ID, Approval Status, Payment Status, Paid Date";
	
	public static String getExpenses (String company, String key, Map<String,String> queryparameters) throws ParseException, JsonParseException, JsonMappingException, JSONException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String status = "failed";// set status to default of failed
		ArrayList<ExpenseReport> reports = new ArrayList<ExpenseReport>();
		ArrayList <ExpenseEntry> xpenses;// ArrayList of ExpenseEntry object
		ReportDetails details;// placeholder for a ReportDetails object
		ArrayList<Itemization> items;// ArrayList of Itemization objects
		ArrayList<Allocation> allocations;// ArrayList of Allocation objects
		ArrayList<JournalEntry> journals;// ArrayList of JournalEntry objects
		ArrayList<VATData> vatdetails;// ArrayList of VATData objects
		Expense expense = new Expense();
		int j, k, l, m;
		CustomField custom;
			
		
		String filename = "ExpensesPulledfromConcur for " + company + ".CSV";// set the filename	
		File file = new File(CSVfilepath + filename);
		FileWriter fileWriter = new FileWriter(file);
		 
		try {
			fileWriter.append(FILE_HEADER.toString());//Write the CSV file header
			fileWriter.append(NEW_LINE_SEPARATOR);//Add a new line separator after the header
	
	
			reports = r.getReports(key, queryparameters);// pull the reports from Concur Expense that match query parameters
				
			if (reports.size() > 0) { // then there is at least one report to process
				
				out.println("Reports: " + reports.size());
				for(ExpenseReport report:reports){// iterate for each report 
			           
		            String reportID = report.getID();// get the Report ID for this expense report
		            out.println("Report ID is " + report.getID());
		            details = expensefunctions.getReportDetails(reportID, key);// get the ReportDetails for this report
			        
			        if (details != null){// then there is a ReportDetails object for this report
						xpenses = details.getExpenses();// get the list of Expense Entry objects for this report
						
						for (ExpenseEntry entry:xpenses) { // Iterate for each Expense Entry object in the report
							
							expense = expensefunctions.getExpense(report, entry.getReportEntryID(), key);// get the Expense object for this Expense Entry
							expense.setID(GUID.getGUID(4));// assign a Pivot Payables GUID to this expense
							expense.setPaymentTypeCode(entry.getPaymentTypeCode());
							expense.setPostedCurrency(report.getCurrencyCode());
							
							
							//out.println("Expense Type Name is " + expense.getExpenseTypeName());
							//out.println("Expense Amount is " + expense.getPostedAmount());
							items = entry.getItems();// get the list of Itemization objects for the Expense Entry
							j=0;// initialize the index for itemizations
							for (Itemization item:items){// iterate for each Itemization object for this Expense Entry
								
								allocations = item.getAllocations();// get the list of Allocation objects for this Itemization
								k=0;// initialize the index for allocations
								for (Allocation allocation:allocations){// Iterate for each Allocation object for this Itemization
									if (reportID == "25901FE8105946D6A8B6"){
										allocation.display();
										/*
										custom = allocation.getCustom10();// get the Ledger field context
										if (custom != null){
											if (custom.Value != null){
												allocation.display();
												//out.println("Custom 10 is " + custom.Value);
												//out.println("Item Expense Type Name is " + item.getExpenseTypeName());
												//out.println("Item Amount is " + item.getPostedAmount());
											}
										} else {
											out.println("Custom 10 is null");
											out.println("Entry ID is " + expense.getEntry_ID());
											out.println("Expense Type Name is " + expense.getExpenseTypeName());
											out.println("Amount is " + expense.getPostedAmount());
										}
										*/
									}

								}// for (Allocation allocation:allocations)
							
								item.setID(GUID.getGUID(4));// assign a Pivot Payables GUID to this itemization
								item.setEntryID(expense.getID());// copy the ID for the parent, Expense
								item.setEntry_ID(expense.getEntry_ID());
								item.setTransactionCurrencyCode(expense.getOriginalCurrency());// copy the Expense's original currency to the itemization
								item.setPostedCurrency(expense.getPostedCurrency());// copy the Expense's posted currency to the itemization
								item.setAllocations(allocations);
								items.set(j, item);// add the item at the index
								j++;// increment the index
							}// for (Itemization item:items)
							
							expense.setItems(items);// add the Itemizations to the Expense
							
							 
							expense.setApprovalStatus(report.getApprovalStatusCode());// the approval status of the expense report that contains this expense
							expense.setPaymentStatus(report.getPaymentStatusCode());// the payment status of the expense report that contains this expense. Is "Paid" when Concur Expense extracts the expense report.
							expense.setPaidDate(report.getPaidDate());// when the expense report this expense entry is a member was paid
							
							expense.setAmountDueCompanyCard(report.getAmountDueCompanyCard());
							expense.setAmountDueEmployee(report.getAmountDueEmployee());
							expense.setReportName(report.getName());
							expense.setReportPurpose(report.getPurpose());
							expense.setReportTotal(report.getTotal());
							expense.setPolicyID(report.getPolicyID());
							expense.setLedgerName(report.getLedgerName());
							expense.setEmployeeCountry(report.getCountry());
							expense.setEmployeeState(report.getCountrySubdivision());
							expense.TaxNexus();// set the Tax Nexus for this expense
							expense.setPersonalExpense(report.getPersonalAmount());
							expense.setTotalApprovedAmount(report.getTotalApprovedAmount());
							// initialize the status for PivotPrime and PivotNexus to Not Processed
							expense.setPrimeStatus("Not Processed");
							expense.setNexusStatus("Not Processed");
							expense.setEmployeeDisplayName(report.getOwnerName());// the employee who incurred the expense
							
							fileWriter.append(expense.getEmployeeDisplayName());

							fileWriter.append(COMMA_DELIMITER);
							fileWriter.append(expense.getExpenseTypeName());
							fileWriter.append(COMMA_DELIMITER);
							vendor =expense.getMerchantName();
							if ((vendor !=null) && (vendor.contains(COMMA_DELIMITER))) {
								vendor =expense.getMerchantName().replace(",",";");
							}
							fileWriter.append(vendor);
							fileWriter.append(COMMA_DELIMITER);
							fileWriter.append(String.valueOf(expense.getTransactionDate()));
							fileWriter.append(COMMA_DELIMITER);
							fileWriter.append(String.valueOf(expense.getOriginalAmount()));
							fileWriter.append(COMMA_DELIMITER);
							fileWriter.append(expense.getOriginalCurrency());
							fileWriter.append(COMMA_DELIMITER);
							fileWriter.append(String.valueOf(expense.getPostedAmount()));
							fileWriter.append(COMMA_DELIMITER);
							fileWriter.append(expense.getPostedCurrency());
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom1() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom1().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom2() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom2().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom3() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom3().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom4() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom4().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom5() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom5().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom6() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom6().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom7() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom7().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom8() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom8().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom9() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom9().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom10() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom10().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom11() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom11().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom12() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom12().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom13() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom13().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom14() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom14().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom15() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom15().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom16() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom16().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom17() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom17().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom18() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom18().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom19() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom19().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom20() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom20().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom21() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom21().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom22() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom22().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom23() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom23().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom24() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom24().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom25() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom25().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom26() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom26().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom27() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom27().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom28() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom28().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom29() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom29().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom30() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom30().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom31() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom31().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom32() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom32().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom33() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom33().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom34() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom34().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom35() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom35().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom36() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom36().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom37() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom37().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom38() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom38().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom39() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom39().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getCustom40() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getCustom40().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getOrgUnit1() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getOrgUnit1().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getOrgUnit2() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getOrgUnit2().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getOrgUnit3() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getOrgUnit3().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getOrgUnit4() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getOrgUnit4().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getOrgUnit5() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getOrgUnit5().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							if (expense.getOrgUnit6() == null){
								fileWriter.append("null");
							} else {
								fileWriter.append(expense.getOrgUnit6().getValue());
							}
							fileWriter.append(COMMA_DELIMITER);
							fileWriter.append(reportID);
							fileWriter.append(COMMA_DELIMITER);
							fileWriter.append(expense.getEntry_ID());
							fileWriter.append(COMMA_DELIMITER);
							fileWriter.append(expense.getApprovalStatus());
							fileWriter.append(COMMA_DELIMITER);
							fileWriter.append(expense.getPaymentStatus());
							fileWriter.append(COMMA_DELIMITER);
							fileWriter.append(String.valueOf(expense.getPaidDate()));
							fileWriter.append(COMMA_DELIMITER);
							fileWriter.append(NEW_LINE_SEPARATOR);	

							
							
					}// for (ExpenseEntry entry:xpenses)
					
		        	
			      }// if (details != null)
			       

				}// for(ExpenseReport report:reports)
			}// if reports.size > 0
			status = "success";
	
	        } catch (Exception e) {
	        	System.out.println("Error in CsvFileWriter !!!");
	            e.printStackTrace();
	            status = "failed";
	            
	        } finally {
	
	            try {
	
	                fileWriter.flush();
	                fileWriter.close();
	            } catch (IOException e) {
	                System.out.println("Error while flushing/closing fileWriter !!!");
	                e.printStackTrace();
	            }
	        }
		    
		return status;
	    
	}// getExpenses
	
	
}


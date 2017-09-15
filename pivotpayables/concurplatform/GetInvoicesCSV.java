package com.pivotpayables.concurplatform;

/*
**
* @author John Toman
* 4/14/2016
* This class gets payment requests from Concur and creates a CSV file
*
*/



import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;


















import java.io.IOException;
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












import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.Company;

import static java.lang.System.out;


public class GetInvoicesCSV {
	/**
	 * @param args
	 */

	private static Map<String, String> queryparameters = new HashMap<String, String>();// a HashMap to hold key-value pairs for a query parameter
	private static Invoices p = new Invoices();// Invoice functions
	private static Invoice invoice = new Invoice();// placeholder for an Invoice object
	private static ArrayList<PaymentRequest> requests = new ArrayList<PaymentRequest>();//
	// the line items for this payment request.  There is at least one line item for every payment request
	private static LineItem line;// placeholder for a Line Item object
	private static ArrayList<LineItem> items = new ArrayList<LineItem>();
	
	
	
	
	// for converting string dates into Date objects
	
	private static SimpleDateFormat expsdf = new SimpleDateFormat("yyyy-MM-dd");
	private static Date date = null;// placeholder for a Date object
	private static String submitted;
	
	private static String vendor=null;

	
	private static Path currentRelativePath = Paths.get("");// find the current relative path
	private static String CSVfilepath = (currentRelativePath.toAbsolutePath().toString()) + "/CSVFiles/";// construct the absolute path to the ImageFiles directoryprivate static String filename;// placeholder for image file name
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String FILE_HEADER = "Employee Name, Expense Type, Vendor Name, Date, Amount, Currency, Posted Amount, Posted Currency, Custom1, Custom2, Custom3, Custom4, Custom5 , Custom6, Custom7, "
			+ "Custom9, Custom10, Custom11, Custom12, Custom13, Custom14, Custom15 , Custom16, Custom17, "
			+ "Custom19, Custom20,Request ID, Approval Status, Payment Status";
	
	
	public static void CreateCSVFile(String key, String lastsuccess, String company) throws JsonParseException, JsonMappingException, ParseException, JSONException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String filename = "InvoicesPulledfromConcur for " + company + ".CSV";// set the filename	
		File file = new File(CSVfilepath + filename);
		FileWriter fileWriter = new FileWriter(file);
		
		
		//calculate the lastSubmitDate to be 30 days before today
		Calendar today = Calendar.getInstance();// initialize a Calendar object
		today.set(Calendar.HOUR_OF_DAY, 0); // set the date to today
		today.add(Calendar.DATE, -30);// set the date to 30 days before today
		date = today.getTime();// set the date to this date
		submitted = expsdf.format(date);// convert date into a String date
				
		try {
			fileWriter.append(FILE_HEADER.toString());//Write the CSV file header
			fileWriter.append(NEW_LINE_SEPARATOR);//Add a new line separator after the header
			
			if (lastsuccess.equals("ALL")) {
				queryparameters.put("paymentStatus", "R_PAID");// search for invoices with a Payment Status of Extracted
				requests = p.getPaymentRequests(key, queryparameters);// pull the invoices from Concur Expense
				out.println("Checkpoint: Requests Digest Count "+ requests.size());
				
			} else {
				queryparameters.put("submitDateAfter", submitted);// search for invoices with a Submit Date after this date
				requests = p.getPaymentRequests(key, queryparameters);// pull the invoices from Concur Expense
			}
			int count=0;
			if (requests.size() > 0) { // then there is at least one Payment Request Digest to process
			    for(PaymentRequest request:requests){// iterate for each Payment Request Digest 
			        String RequestID = request.getID();// get the Payment Request ID for this invoice
					count++;
					invoice = p.getInvoice(RequestID, key);// get the Invoice object for this Payment Request ID
					out.println("Invoice Number: " + count);
					invoice.display();
					items= invoice.getLineItems();
					line = items.get(0);
					
					// write this as a row in the CSV file
					fileWriter.append(invoice.getEmployeeDisplayName());
	
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(invoice.getName());// Assign the Request Name as the Expense Type Name
					fileWriter.append(COMMA_DELIMITER);
					vendor =invoice.getVendorDescription();
					if ((vendor !=null) && (vendor.contains(COMMA_DELIMITER))) {
						vendor =invoice.getVendorDescription().replace(",",";");
					}
					fileWriter.append(vendor);
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(invoice.getInvoiceDate()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(invoice.getInvoiceAmount()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(invoice.getCurrencyCode());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(invoice.getCalculatedAmount()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(invoice.getCurrencyCode());
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom1() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom1());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom2() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom2());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom3() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom3());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom4() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom4());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom5() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom5());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom6() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom6());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom7() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom7());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom8() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom8());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom9() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom9());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom10() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom10());
					}
					fileWriter.append(COMMA_DELIMITER);
					
					
					if (line.getCustom11() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom11());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom12() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom12());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom13() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom13());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom14() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom14());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom15() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom15());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom16() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom16());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom17() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom17());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom18() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom18());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom19() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom19());
					}
					fileWriter.append(COMMA_DELIMITER);
					if (line.getCustom20() == null){
						fileWriter.append("");
					} else {
						fileWriter.append(line.getCustom20());
					}
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(RequestID);
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(invoice.getApprovalStatus());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(invoice.getPaymentStatus());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(NEW_LINE_SEPARATOR);
	
		    	}// for i loop 
			}// if invoices.size > 0
				    
		 } catch (Exception e) {
	        	System.out.println("Error in CsvFileWriter !!!");
	            e.printStackTrace();
	            
	        } finally {
	
	            try {
	
	                fileWriter.flush();
	                fileWriter.close();
	            } catch (IOException e) {
	                System.out.println("Error while flushing/closing fileWriter !!!");
	                e.printStackTrace();
	            }
	        }
		
		
	}

}

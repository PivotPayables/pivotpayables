
package com.pivotpayables.prime;

/**
 * @author John Toman
 * 5/12/16
 * 
 * This class creates a Zip formatted file that includes an XLSX, spreadsheet file with the expense details for a billing statement
 * and a PDF formatted file for each receipt image for this billing statement
 *
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.text.NumberFormat;
import java.text.DecimalFormat;

//import static java.lang.System.out;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Expense;
import com.pivotpayables.concurplatform.Journey;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

import java.util.zip.*;

public class GetBillingStatementZip {
	private static Path currentRelativePath = Paths.get("");// find the current relative path
	private static String filespath = (currentRelativePath.toAbsolutePath().toString()) + "\\FilestoZIP\\";// construct the absolute path to the directory that holds the files to be Zipped
	private static String ZIPpath = (currentRelativePath.toAbsolutePath().toString()) + "\\ZIPFile\\";// construct the absolute path to the directory that holds the ZIP file
	private static String XLSXname;// the spreadsheet file name
	private static String ZIPname;// the ZIP file name
	
	private static AccountActivityCharge charge;// placeholder for an AccountActivityCharge object
	private static String currententry=null;// placeholder for the EntryID for the current billable expense

	
	static final String host = "localhost";
	static final int port = 27017;
	
	// Connect to  the Expense_Data database, and then get the Expenses and Journeys collections
	private static MongoDBFunctions myMongoExpenseFunctions = new MongoDBFunctions (host, port, "Expense_Data");// create a MongoDB Client for the specified host and port, and get the Expense_Data database
	private static DBCollection expCollection = myMongoExpenseFunctions.setCollection("Expenses");// get the Expenses collection

	
	private static DBObject doc;// placeholder for a DBObject document
	private static ArrayList<DBObject> docs;// placeholder for an ArrayList of DBObject documents
	private static Expense expense;// placeholder for an Expense object
	private static Journey journey;// placeholder for a Journey object
	private static Cell cell;

	private static double billedamount;
	private static int rowcount;


	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void createFile(ArrayList<AccountActivityCharge> charges) throws IOException {
		

		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";


		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		
		// create the name for the spreadsheet file
		charge = charges.get(0);// get the first charge
		String accountname = cleanName(charge.getAccountName());// remove invalid characters
		String activityname = cleanName(charge.getActivityName());// remove invalid characters
		XLSXname =  accountname + "-" + activityname  + ".xlsx";// set the file name to the Account Name-Activity Name.XLSX
		ZIPname =  accountname + "-" + activityname  + ".zip";// set the file name to the Account Name-Activity Name.XLSX
		
		Workbook wb = new XSSFWorkbook();// initiate a new XSSF Workbook
		CreationHelper createHelper = wb.getCreationHelper();// initiate a cell Helper
		CellStyle cellStyle = wb.createCellStyle();// create a Cell Style
	    cellStyle.setDataFormat(
	        createHelper.createDataFormat().getFormat("MM-dd-yyyy"));// set format for Date fields
		Sheet sheet1 = wb.createSheet("Expense Details");// name worksheet 1 Expense Details
		

		rowcount=0;//initialize the row number
		currententry="";// initialize the current entry to empty
	    Row row = sheet1.createRow((short)0);// Create the header row
	    
	    // Create a cell and put a value in it.
	    row.createCell(0).setCellValue("Employee");
	    row.createCell(1).setCellValue("Type");
	    row.createCell(2).setCellValue("Date");
	    row.createCell(3).setCellValue("Original Amount");
	    row.createCell(4).setCellValue("Converted Amount");
	    row.createCell(5).setCellValue("Billed Amount");
	    row.createCell(6).setCellValue("Vendor");
	    row.createCell(7).setCellValue("Location");
	    row.createCell(8).setCellValue("Journey Distance");
	    row.createCell(9).setCellValue("Journey From|To");
	    row.createCell(10).setCellValue("Description");
	    row.createCell(11).setCellValue("Entry ID");


	    
		for (int i=0; i<charges.size(); i++) {// iterate for each AccountActivityCharge
			charge= charges.get(i);// get the Charge for this iteration

			if (charge.getEntryID().equals(currententry)) {// then this Charge is for the current, expense entry
				billedamount = billedamount + charge.getChargeAmount();// increment Billed Amount by the Charge Amount for this charge
			    cell.setCellValue(decimalFormat.format(billedamount) + " " + expense.getPostedCurrency());
			} else {// then this is a different, billable expense entry
				
				rowcount = rowcount+1;// increment the row count so that we are on a new row in the worksheet
				row = sheet1.createRow((short)rowcount);// Create the row in the worksheet for this billable expense
				currententry = charge.getEntryID();// set the current entry to this expense entry's ID
				myMongoExpenseFunctions.getImage(filespath, currententry);// get the receipt image for this Entry ID and put into the Files to Zip folder
			
				billedamount = charge.getChargeAmount();// initialize Billed Amount to the Charge Amount for this charge
				
				// get the DBObject document for this expense
				docs = myMongoExpenseFunctions.getDocsByField(expCollection, "Entry_ID",currententry);
				doc = docs.get(0);// there should be only one Expense document for this Entry_ID
				
				expense = myMongoExpenseFunctions.doctoExpense(doc);// convert this Expense document into an Expense object
				
				// set the cell values for the cells that have the same value for each charge for this expense entry
			    row.createCell(0).setCellValue(expense.getEmployeeDisplayName());// set the Employee Display Name to column A
			    row.createCell(1).setCellValue(expense.getExpenseTypeName());// set the Expense Type Name to column B
			    cell = row.createCell(2);
				if (expense.getTransactionDate() != null) {
				    cell.setCellValue(expense.getTransactionDate());// set the Transaction Date to column C
				    cell.setCellStyle(cellStyle);// format the cell to use the Date field style
				}// if (expense.getTransactionDate() != null)
			    row.createCell(3).setCellValue(decimalFormat.format(expense.getOriginalAmount()) + " " + expense.getOriginalCurrency());// set the Original Amount and Currency to column D
			    row.createCell(4).setCellValue(decimalFormat.format(expense.getPostedAmount()) + " " + expense.getPostedCurrency());// set the Posted Amount and Currency to column E
			    cell = row.createCell(5);
			    cell.setCellValue(decimalFormat.format(billedamount) + " " + expense.getPostedCurrency());// set the Billed Amount and Currency to column F
			    row.createCell(6).setCellValue(expense.getMerchantName());// set the Merchant Name to column G
			    row.createCell(7).setCellValue(expense.getLocationDisplayName());// set the Location Display Name to column H
			    row.createCell(10).setCellValue(expense.getDescription());// set the Description to column I
			    row.createCell(11).setCellValue(currententry);// set the Entry ID to column J
			    
			    if (expense.getJourney() != null) {// then this is a mileage expense
			    	journey = expense.getJourney();// get the Journey object associated to the Expense object
				    row.createCell(8).setCellValue(journey.getBusinessDistance());// set the Business Distance to column K
				    row.createCell(9).setCellValue(journey.getStartLocation() + " | " + journey.getEndLocation());// set the From To Waypoints to column L
			   }// if (expense.getJourney() != null)
			}//if (charge.getEntryID().equals(currententry))

		}// for i block
	    FileOutputStream fileOut = new FileOutputStream(filespath+XLSXname);// create the XLSX file and put into the Files to Zip folder
	    wb.write(fileOut);// write the file
	    fileOut.close();// close the file
	    wb.close();// close the Workbook object
	    Zip();// create the Zip file and put into the ZIP File folder

	}//createFile
	
	private static String cleanName (String name){
		char c;
		String front;
		String back;
		for (int i=0; i<name.length(); i++) {// iterate through each character in the name
			c = name.charAt(i);// get the character at the current index
			
			if (c=='\\' || c=='/' ||  c=='(' || c==')' || c=='*' || c=='+' || c==',' || c=='=' || c=='|' || c==';' ||c=='.' || 
			c==':' || c=='[' || c==']' || c=='{' || c=='}') {// then this character can't be used in the file name
				if (i==0){// then this is the first character in the name
					name= "-" + name.substring(1, name.length());//so, replace it with a hyphen
				} else {
					front = name.substring(0, (i-1));// the front string is the first character through the previous character to the index
					back = name.substring((i+1), name.length());// the back string is the character after the index through the last character
					name = front + "-" + back;// set the name so that it replaces the invalid character with a hyphen
				}// if (i==0) block
			}// if (c=='\\'
			
		}// i loop
		return name;
	}//cleanName
	private static void Zip (){
		
		// get a list of files from the ZIP files folder
		
        File file = new File(filespath);// load all the files in the filespath folder into a File object
        if (file.exists()) {// then there is at least one file to ZIP
	         
        	File currentfile = null;// placeholder for current file
        	File[] files= file.listFiles();// create an Array of File objects for each file in the filespath folder


		   try {// try to create the ZIP file

		        byte[] buffer = new byte[1024];

		        // create object of FileOutputStream for the ZIP file
		        FileOutputStream fileout = new FileOutputStream(ZIPpath + ZIPname);

		        // create object of ZipOutputStream from FileOutputStream
		        ZipOutputStream zipout = new ZipOutputStream(fileout);

		        for (int i=0; i<files.length; i++) {// iterate through each file to be zipped
		            currentfile = files[i];// get the file for this iteration

		            // create object of FileInputStream for the current file
		            FileInputStream filein = new FileInputStream(currentfile);

		            // add files to ZIP
		            ZipEntry zipentry = new ZipEntry(currentfile.getName());// create a ZIPEntry object for the current file name
		            zipout.putNextEntry(new ZipEntry(zipentry));// add this ZIPEntry object to the ZIP output stream

		            // write file content
		            int length;

		            while ((length = filein.read(buffer)) > 0) {// read one buffer of the File Input Stream; keep writing until the entire File Input Stream is read
		                zipout.write(buffer, 0, length);// write the portion just read 
		            }
		            // close the ZIPEntry object
		            zipout.closeEntry();

		            // close the File Input Stream
		            filein.close();
		            
		            // now delete the file from the ZIP files folder
		            currentfile.delete();
		        }

		        // close the ZipOutputStream
		        zipout.flush();
		        zipout.finish();
		        zipout.close();
		    } catch (IOException ioe) {
		        System.out.println("IOException :" + ioe);
		    }
        }// if (file.exists())
		   
	}

}
	




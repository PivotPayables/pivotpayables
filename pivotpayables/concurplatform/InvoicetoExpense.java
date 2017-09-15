package com.pivotpayables.concurplatform;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pivotpayables.expensesimulator.GUID;
import com.pivotpayables.prime.FieldContext;


public class InvoicetoExpense {
	private static String key;// Oauth token
	private static ConcurFunctions concurfunctions = new ConcurFunctions();
	private static String listtype = null;
	private static String longcode = null;
	

	public static Expense getExpense (Invoice invoice, ArrayList<FieldContext> contexts, String Key) throws ParseException, JsonParseException, JsonMappingException, JSONException, IOException {
		//this method returns this invoice as an Expense object that can be used exactly as any other Expense object such one created using the GET Expense Entry v3 API
		//The idea is to abstract from PivotPrime, PivotNexus, and other products the source of Expense data.  These products should neither no or care whether the 
		//source for an Expense object was data gathered using the GET Expense Entry, GET Payment Request, or some other API
		Date date = null;// placeholder for a Date object
		SimpleDateFormat invsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");// date format the Invoice APIs use
		
		String connectedlistid = null;
		String listid1=null;
		String listid2=null;
		String listid3=null;
		String listid4=null;
		String listid5=null;
		String listid6=null;
		String listid7=null;
		String listid8=null;
		String listid9=null;
		String listid10=null;
		String listid11=null;
		String listid12=null;
		String listid13=null;
		String listid14=null;
		String listid15=null;
		String listid16=null;
		String listid17=null;
		String listid18=null;
		String listid19=null;
		String listid20=null;
		
		String Level1="";
		String Level2="";
		String Level3="";
		String Level4="";
		String Level5="";
		
		String expensetypename;
		String expensetypecode;
		Itemization item;// placeholder for an Itemization object
		ArrayList<Itemization> items = new ArrayList<Itemization>();
		double amount=0;// placeholder for an amount
		ArrayList<LineItem> lines;
		ArrayList<InvoiceAllocation> invallocations;
		ArrayList<Allocation> allocations;
		Allocation allocation;
		ArrayList<JournalEntry> journals;
		JournalEntry journal;
		CustomField custom = null;
		ArrayList<VATData> vatdetails;
		Boolean usesconnected = false;
		
		
		key = Key;
		
		
		// the itemizations for this expense.  There is at least one itemization for every expense

				
		for (FieldContext itcontext:contexts) {// iterate for each field context
	        String context = itcontext.getContext();
	        String formtype = itcontext.getFormType();
	        String fieldid = itcontext.getFieldID();
	        
	        switch (context){
		        case "ConnectedList":
		        	usesconnected = true;
		        	switch (formtype){
		        		case "ListID":
		        			connectedlistid = fieldid;
		        			break;
		        		case "Level1":
		        			Level1 = fieldid;
		        			break;
		        		case "Level2":
		        			Level2 = fieldid;
		        			break;
		        		case "Level3":
		        			Level3 = fieldid;
		        			break;	
		        		case "Level4":
		        			Level4 = fieldid;
		        			break;
		        		case "Level5":
		        			Level5 = fieldid;
		        			break;
		        	}
		        	break;
		        case "SimpleList":
		        	switch (fieldid){
		        		case "Custom1":
		        			listid1 = formtype;
		        			break;
		        		case "Custom2":
		        			listid2 = formtype;
		        			break;
		        		case "Custom3":
		        			listid3 = formtype;
		        			break;
		        		case "Custom4":
		        			listid4 = formtype;
		        			break;
		        		case "Custom5":
		        			listid5 = formtype;
		        			break;
		        		case "Custom6":
		        			listid6 = formtype;
		        			break;
		        		case "Custom7":
		        			listid7 = formtype;
		        			break;
		        		case "Custom8":
		        			listid8 = formtype;
		        			break;
		        		case "Custom9":
		        			listid9 = formtype;
		        			break;
		        		case "Custom10":
		        			listid10 = formtype;
		        			break;
		        		case "Custom11":
		        			listid11 = formtype;
		        			break;
		        		case "Custom12":
		        			listid12 = formtype;
		        			break;
		        		case "Custom13":
		        			listid13 = formtype;
		        			break;
		        		case "Custom14":
		        			listid14 = formtype;
		        			break;
		        		case "Custom15":
		        			listid15 = formtype;
		        			break;
		        		case "Custom16":
		        			listid16 = formtype;
		        			break;
		        		case "Custom17":
		        			listid17 = formtype;
		        			break;
		        		case "Custom18":
		        			listid18 = formtype;
		        			break;
		        		case "Custom19":
		        			listid19 = formtype;
		        			break;
		        		case "Custom20":
		        			listid20 = formtype;
		        	}    
	        }// switch
	       
	    }// for (FieldContext itcontext:contexts)

		
		//Begin creating the Expense object
		Expense myExpense = new Expense();//initiate an Expense object
		myExpense.setID(GUID.getGUID(4));// assign a Pivot Payables unique ID for this expense
		myExpense.setEntry_ID(invoice.getID());// assign the Payment Request ID as the unique identifier that Concur Invoice assigned to this payment request	
		
		// For consistency there needs to be a ReportID for an Expense object created from a payment request.  So, assign the Payment Request ID as the Report ID.
		myExpense.setReportID(invoice.getID());// with an invoice (payment request) there isn't an "expense report" that the payment request is a member, such as the case an expense report, expense entry.
		myExpense.setReportName(invoice.getName());
		myExpense.setReportPurpose(invoice.getDescription());// assign the Invoice description to the Report Purpose
		myExpense.setReportTotal(invoice.getCalculatedAmount());
		myExpense.setTotalApprovedAmount(invoice.getTotalApprovedAmount());
		myExpense.setLedgerName(invoice.getLedgerCode());
		myExpense.setEmployeeCountry(invoice.getCountryCode());// assign the Invoice Country to the Employee Country
		myExpense.setExpenseTypeName(invoice.getName());// assign the Payment Request Name to the Expense Type Name
		myExpense.setPaymentTypeName("Invoice");
		myExpense.setPaymentTypeCode("INV");
		myExpense.setDescription(invoice.getDescription());	
		myExpense.setHasItemizations(true);// by definition, all payment requests have at least one line item	
		
		if (invoice.getVendorRemitAddress() != null){// then there is a vendor remit address 
			myExpense.setLocationDisplayName(invoice.getVendorRemitAddress().getCity() + ", " + invoice.getVendorRemitAddress().getState());
			myExpense.setLocationCity(invoice.getVendorRemitAddress().getCity());	
			myExpense.setLocationState(invoice.getVendorRemitAddress().getState());
			myExpense.setLocationCountry(invoice.getVendorRemitAddress().getCountryCode());
		}// if (invoice.getVendorRemitAddress() != null)

		myExpense.TaxNexus();// set the Tax Nexus based upon the Employee Country (Invoice Country) and Vendor Country
		
		if (invoice.getInvoiceDate() != null) {// then there is an Invoice Date to assign
			date = invsdf.parse(invoice.getInvoiceDate());// convert InvoiceDate from a String date to a Date object
			myExpense.setTransactionDate(date);// assign the Invoice Date to the Transaction Date
		}// if (invoice.getInvoiceDate() != null)
	
		myExpense.setOriginalAmount(invoice.getInvoiceAmount());// assign the Invoice Amount as the Original Amount
		myExpense.setExchangeRate(1.0);// set the Exchange Rate to 1.0 because with Invoice the Original and Posted currencies are always the same
		
		// Note with Concur Invoice that there is only one currency associated with a payment request.  
		// In contrast with Concur Expense there can be an Original Currency and a Posted Currency with an expense entry.
		// This explains why there is no Exchange Rate in a payment request.
		myExpense.setOriginalCurrency(invoice.getCurrencyCode()); // assign the Currency Code for the invoice as the Original Currency
		myExpense.setPostedCurrency(invoice.getCurrencyCode());  //  assign the Currency Code for the invoice as the Posted Currency
		
		myExpense.setApprovedAmount(invoice.getTotalApprovedAmount());	// assign the Total Approved Amount to Approved Amount
		myExpense.setPostedAmount(invoice.getCalculatedAmount());
		
		myExpense.setApprovalStatus(invoice.getApprovalStatus());
		myExpense.setPaymentStatus(invoice.getPaymentStatus());
		
		if (invoice.getExtractDate() != null) {// then there is an Extract Date to assign
			date = invsdf.parse(invoice.getExtractDate());// convert PaidDate from a String date to a Date object
			myExpense.setPaidDate(date);;// assign the Extract Date as the Paid Date
		}//
		
		// create the Itemizations for this expense from the payment request's line items
		lines = invoice.getLineItems();
		for (LineItem line:lines) {// iterate for each Line Item
			item = new Itemization();// initialize the Itemization
			item.setID(GUID.getGUID(4));
			item.setEntryID(myExpense.getID());// assign the Pivot Payables EntryID
			item.setEntry_ID(invoice.getID());	// assign the Payment Request ID as the Parent Entry ID
			expensetypecode=line.getExpenseTypeCode();
			expensetypename = getExpenseTypeName(expensetypecode);// convert this code into its name
			item.setExpenseTypeName(expensetypename);// assign the Expense Type Name for this itemization	
			item.setDate(myExpense.getTransactionDate());// assign the Expense's Transaction Date as the Itemization date
			item.setTransactionAmount(line.getTotalPrice());// assign the Line Item Total Amount to the Transaction Amount
			item.setTransactionCurrencyCode(myExpense.getPostedCurrency());	
			item.setPostedAmount(line.getTotalPrice());// assign the Line Item Total Amount to the Posted Amount
			item.setApprovedAmount(line.getApprovedLineItemAmount());// assign the Line Item Approved Amount to the Approved Amount
			item.setPostedCurrency(myExpense.getPostedCurrency());// assign the Expense Post Currency as the Posted Currency
			
			// Process custom fields that are connected 
			
			if (usesconnected){// then this Company uses a Connected List for Account/Activity/Phase/Task
				// So, process the connected list
				listtype = "ConnectedList";
				switch (Level1) {
					case "Custom1":
						if (line.getCustom1() != null) {
							longcode = line.getCustom1();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom1());
							item.setCustom1(custom);
						}
						break;
					case "Custom2":
						if (line.getCustom2() != null) {
							longcode = line.getCustom2();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom2());
							item.setCustom2(custom);
						}
						break;
					case "Custom3":
						if (line.getCustom3() != null) {
							longcode = line.getCustom3();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom3());
							item.setCustom3(custom);
						}
						break;
					case "Custom4":
						if (line.getCustom4() != null) {
							longcode = line.getCustom4();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom4());
							item.setCustom4(custom);
						}
						break;
					case "Custom5":
						if (line.getCustom5() != null) {
							longcode = line.getCustom5();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom5());
							item.setCustom5(custom);
						}
						break;
					case "Custom6":
						if (line.getCustom6() != null) {
							longcode = line.getCustom6();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom6());
							item.setCustom6(custom);
						}
						break;	
					case "Custom7":
						if (line.getCustom7() != null) {
							longcode = line.getCustom7();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom7());
							item.setCustom7(custom);
						}
						break;	
					case "Custom8":
						if (line.getCustom8() != null) {
							longcode = line.getCustom8();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom8());
							item.setCustom8(custom);
						}
						break;	
					case "Custom9":
						if (line.getCustom9() != null) {
							longcode = line.getCustom9();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom9());
							item.setCustom9(custom);
						}
						break;	
					case "Custom10":
						if (line.getCustom10() != null) {
							longcode = line.getCustom10();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom10());
							item.setCustom10(custom);
						}
						break;	
					case "Custom11":
						if (line.getCustom11() != null) {
							longcode = line.getCustom11();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom11());
							item.setCustom11(custom);
						}
						break;
					case "Custom12":
						if (line.getCustom12() != null) {
							longcode = line.getCustom12();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom12());
							item.setCustom12(custom);
						}
						break;
					case "Custom13":
						longcode = line.getCustom13();// save the item code as the first segment of the long code
						if (line.getCustom13() != null) {
							longcode = line.getCustom13();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom13());
							item.setCustom13(custom);
						}
						break;
					case "Custom14":
						if (line.getCustom14() != null) {
							longcode = line.getCustom14();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom14());
							item.setCustom14(custom);
						}
						break;
					case "Custom15":
						if (line.getCustom15() != null) {
							longcode = line.getCustom15();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom15());
							item.setCustom15(custom);
						}
						break;
					case "Custom16":
						if (line.getCustom16() != null) {
							longcode = line.getCustom16();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom16());
							item.setCustom16(custom);
						}
						break;	
					case "Custom17":
						if (line.getCustom17() != null) {
							longcode = line.getCustom17();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom17());
							item.setCustom17(custom);
						}
						break;	
					case "Custom18":
						if (line.getCustom18() != null) {
							longcode = line.getCustom18();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom18());
							item.setCustom18(custom);
						}
						break;	
					case "Custom19":
						if (line.getCustom19() != null) {
							longcode = line.getCustom19();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom19());
							item.setCustom19(custom);
						}
						break;	
					case "Custom20":// save the item code as the second segment of the long codem20":
						if (line.getCustom20() != null) {
							longcode = line.getCustom20();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom20());
							item.setCustom20(custom);
						}
				
			}
			switch (Level2) {
			case "Custom1":
				if (line.getCustom1() != null) {
					longcode = longcode + "-" + line.getCustom1();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom1());
					item.setCustom1(custom);
				}
				break;
			case "Custom2":
				if (line.getCustom2() != null) {
					longcode = longcode + "-" + line.getCustom2();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom2());
					item.setCustom2(custom);
				}
				break;
			case "Custom3":
				if (line.getCustom3() != null) {
					longcode = longcode + "-" + line.getCustom3();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom3());
					item.setCustom3(custom);
				}
				break;
			case "Custom4":
				if (line.getCustom4() != null) {
					longcode = longcode + "-" + line.getCustom4();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom4());
					item.setCustom4(custom);
				}
				break;
			case "Custom5":
				if (line.getCustom5() != null) {
					longcode = longcode + "-" + line.getCustom5();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom5());
					item.setCustom5(custom);
				}
				break;
			case "Custom6":
				if (line.getCustom6() != null) {
					longcode = longcode + "-" + line.getCustom6();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom6());
					item.setCustom6(custom);
				}
				break;	
			case "Custom7":
				if (line.getCustom7() != null) {
					longcode = longcode + "-" + line.getCustom7();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom7());
					item.setCustom7(custom);
				}
				break;	
			case "Custom8":
				if (line.getCustom8() != null) {
					longcode = longcode + "-" + line.getCustom8();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom8());
					item.setCustom8(custom);
				}
				break;	
			case "Custom9":
				if (line.getCustom9() != null) {
					longcode = longcode + "-" + line.getCustom9();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom9());
					item.setCustom9(custom);
				}
				break;	
			case "Custom10":
				if (line.getCustom10() != null) {
					longcode = longcode + "-" + line.getCustom10();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom10());
					item.setCustom10(custom);
				}
				break;	
			case "Custom11":
				if (line.getCustom11() != null) {
					longcode = longcode + "-" + line.getCustom11();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom11());
					item.setCustom11(custom);
				}
				break;
			case "Custom12":
				if (line.getCustom12() != null) {
					longcode = longcode + "-" + line.getCustom12();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom12());
					item.setCustom12(custom);
				}
				break;
			case "Custom13":
				longcode = longcode + "-" + line.getCustom13();// save the item code as the first segment of the long code
				if (line.getCustom13() != null) {
					longcode = longcode + "-" + line.getCustom13();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom13());
					item.setCustom13(custom);
				}
				break;
			case "Custom14":
				if (line.getCustom14() != null) {
					longcode = longcode + "-" + line.getCustom14();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom14());
					item.setCustom14(custom);
				}
				break;
			case "Custom15":
				if (line.getCustom15() != null) {
					longcode = longcode + "-" + line.getCustom15();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom15());
					item.setCustom15(custom);
				}
				break;
			case "Custom16":
				if (line.getCustom16() != null) {
					longcode = longcode + "-" + line.getCustom16();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom16());
					item.setCustom16(custom);
				}
				break;	
			case "Custom17":
				if (line.getCustom17() != null) {
					longcode = longcode + "-" + line.getCustom17();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom17());
					item.setCustom17(custom);
				}
				break;	
			case "Custom18":
				if (line.getCustom18() != null) {
					longcode = longcode + "-" + line.getCustom18();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom18());
					item.setCustom18(custom);
				}
				break;	
			case "Custom19":
				if (line.getCustom19() != null) {
					longcode = longcode + "-" + line.getCustom19();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom19());
					item.setCustom19(custom);
				}
				break;	
			case "Custom20":// save the item code as the second segment of the long codem20":
				if (line.getCustom20() != null) {
					longcode = longcode + "-" + line.getCustom20();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(line.getCustom20());
					item.setCustom20(custom);
				}
			
			}
		if (Level3 != null){
			switch (Level3) {
				case "Custom1":
					if (line.getCustom1() != null) {
						longcode = longcode + "-" + line.getCustom1();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom1());
						item.setCustom1(custom);
					}
					break;
				case "Custom2":
					if (line.getCustom2() != null) {
						longcode = longcode + "-" + line.getCustom2();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom2());
						item.setCustom2(custom);
					}
					break;
				case "Custom3":
					if (line.getCustom3() != null) {
						longcode = longcode + "-" + line.getCustom3();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom3());
						item.setCustom3(custom);
					}
					break;
				case "Custom4":
					if (line.getCustom4() != null) {
						longcode = longcode + "-" + line.getCustom4();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom4());
						item.setCustom4(custom);
					}
					break;
				case "Custom5":
					if (line.getCustom5() != null) {
						longcode = longcode + "-" + line.getCustom5();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom5());
						item.setCustom5(custom);
					}
					break;
				case "Custom6":
					if (line.getCustom6() != null) {
						longcode = longcode + "-" + line.getCustom6();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom6());
						item.setCustom6(custom);
					}
					break;	
				case "Custom7":
					if (line.getCustom7() != null) {
						longcode = longcode + "-" + line.getCustom7();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom7());
						item.setCustom7(custom);
					}
					break;	
				case "Custom8":
					if (line.getCustom8() != null) {
						longcode = longcode + "-" + line.getCustom8();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom8());
						item.setCustom8(custom);
					}
					break;	
				case "Custom9":
					if (line.getCustom9() != null) {
						longcode = longcode + "-" + line.getCustom9();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom9());
						item.setCustom9(custom);
					}
					break;	
				case "Custom10":
					if (line.getCustom10() != null) {
						longcode = longcode + "-" + line.getCustom10();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom10());
						item.setCustom10(custom);
					}
					break;	
				case "Custom11":
					if (line.getCustom11() != null) {
						longcode = longcode + "-" + line.getCustom11();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom11());
						item.setCustom11(custom);
					}
					break;
				case "Custom12":
					if (line.getCustom12() != null) {
						longcode = longcode + "-" + line.getCustom12();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom12());
						item.setCustom12(custom);
					}
					break;
				case "Custom13":
					longcode = longcode + "-" + line.getCustom13();// save the item code as the first segment of the long code
					if (line.getCustom13() != null) {
						longcode = longcode + "-" + line.getCustom13();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom13());
						item.setCustom13(custom);
					}
					break;
				case "Custom14":
					if (line.getCustom14() != null) {
						longcode = longcode + "-" + line.getCustom14();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom14());
						item.setCustom14(custom);
					}
					break;
				case "Custom15":
					if (line.getCustom15() != null) {
						longcode = longcode + "-" + line.getCustom15();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom15());
						item.setCustom15(custom);
					}
					break;
				case "Custom16":
					if (line.getCustom16() != null) {
						longcode = longcode + "-" + line.getCustom16();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom16());
						item.setCustom16(custom);
					}
					break;	
				case "Custom17":
					if (line.getCustom17() != null) {
						longcode = longcode + "-" + line.getCustom17();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom17());
						item.setCustom17(custom);
					}
					break;	
				case "Custom18":
					if (line.getCustom18() != null) {
						longcode = longcode + "-" + line.getCustom18();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom18());
						item.setCustom18(custom);
					}
					break;	
				case "Custom19":
					if (line.getCustom19() != null) {
						longcode = longcode + "-" + line.getCustom19();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom19());
						item.setCustom19(custom);
					}
					break;	
				case "Custom20":// save the item code as the second segment of the long codem20":
					if (line.getCustom20() != null) {
						longcode = longcode + "-" + line.getCustom20();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(line.getCustom20());
						item.setCustom20(custom);
					}
		
			}
		}
			if (Level4 != null){
				switch (Level4) {
					case "Custom1":
						if (line.getCustom1() != null) {
							longcode = longcode + "-" + line.getCustom1();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom1());
							item.setCustom1(custom);
						}
						break;
					case "Custom2":
						if (line.getCustom2() != null) {
							longcode = longcode + "-" + line.getCustom2();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom2());
							item.setCustom2(custom);
						}
						break;
					case "Custom3":
						if (line.getCustom3() != null) {
							longcode = longcode + "-" + line.getCustom3();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom3());
							item.setCustom3(custom);
						}
						break;
					case "Custom4":
						if (line.getCustom4() != null) {
							longcode = longcode + "-" + line.getCustom4();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom4());
							item.setCustom4(custom);
						}
						break;
					case "Custom5":
						if (line.getCustom5() != null) {
							longcode = longcode + "-" + line.getCustom5();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom5());
							item.setCustom5(custom);
						}
						break;
					case "Custom6":
						if (line.getCustom6() != null) {
							longcode = longcode + "-" + line.getCustom6();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom6());
							item.setCustom6(custom);
						}
						break;	
					case "Custom7":
						if (line.getCustom7() != null) {
							longcode = longcode + "-" + line.getCustom7();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom7());
							item.setCustom7(custom);
						}
						break;	
					case "Custom8":
						if (line.getCustom8() != null) {
							longcode = longcode + "-" + line.getCustom8();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom8());
							item.setCustom8(custom);
						}
						break;	
					case "Custom9":
						if (line.getCustom9() != null) {
							longcode = longcode + "-" + line.getCustom9();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom9());
							item.setCustom9(custom);
						}
						break;	
					case "Custom10":
						if (line.getCustom10() != null) {
							longcode = longcode + "-" + line.getCustom10();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom10());
							item.setCustom10(custom);
						}
						break;	
					case "Custom11":
						if (line.getCustom11() != null) {
							longcode = longcode + "-" + line.getCustom11();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom11());
							item.setCustom11(custom);
						}
						break;
					case "Custom12":
						if (line.getCustom12() != null) {
							longcode = longcode + "-" + line.getCustom12();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom12());
							item.setCustom12(custom);
						}
						break;
					case "Custom13":
						longcode = longcode + "-" + line.getCustom13();// save the item code as the first segment of the long code
						if (line.getCustom13() != null) {
							longcode = longcode + "-" + line.getCustom13();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom13());
							item.setCustom13(custom);
						}
						break;
					case "Custom14":
						if (line.getCustom14() != null) {
							longcode = longcode + "-" + line.getCustom14();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom14());
							item.setCustom14(custom);
						}
						break;
					case "Custom15":
						if (line.getCustom15() != null) {
							longcode = longcode + "-" + line.getCustom15();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom15());
							item.setCustom15(custom);
						}
						break;
					case "Custom16":
						if (line.getCustom16() != null) {
							longcode = longcode + "-" + line.getCustom16();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom16());
							item.setCustom16(custom);
						}
						break;	
					case "Custom17":
						if (line.getCustom17() != null) {
							longcode = longcode + "-" + line.getCustom17();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom17());
							item.setCustom17(custom);
						}
						break;	
					case "Custom18":
						if (line.getCustom18() != null) {
							longcode = longcode + "-" + line.getCustom18();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom18());
							item.setCustom18(custom);
						}
						break;	
					case "Custom19":
						if (line.getCustom19() != null) {
							longcode = longcode + "-" + line.getCustom19();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom19());
							item.setCustom19(custom);
						}
						break;	
					case "Custom20":// save the item code as the second segment of the long codem20":
						if (line.getCustom20() != null) {
							longcode = longcode + "-" + line.getCustom20();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom20());
							item.setCustom20(custom);
						}
			
				}
			}
			if (Level5 != null){
				switch (Level5) {
					case "Custom1":
						if (line.getCustom1() != null) {
							longcode = longcode + "-" + line.getCustom1();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom1());
							item.setCustom1(custom);
						}
						break;
					case "Custom2":
						if (line.getCustom2() != null) {
							longcode = longcode + "-" + line.getCustom2();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom2());
							item.setCustom2(custom);
						}
						break;
					case "Custom3":
						if (line.getCustom3() != null) {
							longcode = longcode + "-" + line.getCustom3();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom3());
							item.setCustom3(custom);
						}
						break;
					case "Custom4":
						if (line.getCustom4() != null) {
							longcode = longcode + "-" + line.getCustom4();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom4());
							item.setCustom4(custom);
						}
						break;
					case "Custom5":
						if (line.getCustom5() != null) {
							longcode = longcode + "-" + line.getCustom5();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom5());
							item.setCustom5(custom);
						}
						break;
					case "Custom6":
						if (line.getCustom6() != null) {
							longcode = longcode + "-" + line.getCustom6();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom6());
							item.setCustom6(custom);
						}
						break;	
					case "Custom7":
						if (line.getCustom7() != null) {
							longcode = longcode + "-" + line.getCustom7();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom7());
							item.setCustom7(custom);
						}
						break;	
					case "Custom8":
						if (line.getCustom8() != null) {
							longcode = longcode + "-" + line.getCustom8();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom8());
							item.setCustom8(custom);
						}
						break;	
					case "Custom9":
						if (line.getCustom9() != null) {
							longcode = longcode + "-" + line.getCustom9();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom9());
							item.setCustom9(custom);
						}
						break;	
					case "Custom10":
						if (line.getCustom10() != null) {
							longcode = longcode + "-" + line.getCustom10();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom10());
							item.setCustom10(custom);
						}
						break;	
					case "Custom11":
						if (line.getCustom11() != null) {
							longcode = longcode + "-" + line.getCustom11();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom11());
							item.setCustom11(custom);
						}
						break;
					case "Custom12":
						if (line.getCustom12() != null) {
							longcode = longcode + "-" + line.getCustom12();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom12());
							item.setCustom12(custom);
						}
						break;
					case "Custom13":
						longcode = longcode + "-" + line.getCustom13();// save the item code as the first segment of the long code
						if (line.getCustom13() != null) {
							longcode = longcode + "-" + line.getCustom13();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom13());
							item.setCustom13(custom);
						}
						break;
					case "Custom14":
						if (line.getCustom14() != null) {
							longcode = longcode + "-" + line.getCustom14();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom14());
							item.setCustom14(custom);
						}
						break;
					case "Custom15":
						if (line.getCustom15() != null) {
							longcode = longcode + "-" + line.getCustom15();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom15());
							item.setCustom15(custom);
						}
						break;
					case "Custom16":
						if (line.getCustom16() != null) {
							longcode = longcode + "-" + line.getCustom16();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom16());
							item.setCustom16(custom);
						}
						break;	
					case "Custom17":
						if (line.getCustom17() != null) {
							longcode = longcode + "-" + line.getCustom17();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom17());
							item.setCustom17(custom);
						}
						break;	
					case "Custom18":
						if (line.getCustom18() != null) {
							longcode = longcode + "-" + line.getCustom18();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom18());
							item.setCustom18(custom);
						}
						break;	
					case "Custom19":
						if (line.getCustom19() != null) {
							longcode = longcode + "-" + line.getCustom19();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom19());
							item.setCustom19(custom);
						}
						break;	
					case "Custom20":// save the item code as the second segment of the long codem20":
						if (line.getCustom20() != null) {
							longcode = longcode + "-" + line.getCustom20();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(line.getCustom20());
							item.setCustom20(custom);
						}
				}
			}
		}// if (usesconnected)
		
		// Next, process custom fields that are either a simple list or a test field type
		if (line.getCustom1() != null) {
			if (listid1 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom1();
				custom = longCodetoCustom(listid1);// convert the long code into a Custom Field
				custom.setCode(line.getCustom1());
				item.setCustom1(custom);
			} else if (!((Level1.equals("Custom1")) || (Level2.equals("Custom1")) || (Level3.equals("Custom1")) || (Level4.equals("Custom1")) || (Level5.equals("Custom1")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom1());
				custom.setValue(line.getCustom1());
				item.setCustom1(custom);
			}
			
		}
		if (line.getCustom2() != null) {
			
			if (listid2 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom2();
				custom = longCodetoCustom(listid2);// convert the long code into a Custom Field
				custom.setCode(line.getCustom2());
				item.setCustom2(custom);
			} else if (!((Level1.equals("Custom2")) || (Level2.equals("Custom2")) || (Level3.equals("Custom2")) || (Level4.equals("Custom2")) || (Level5.equals("Custom2")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom2());
				custom.setValue(line.getCustom2());
				item.setCustom2(custom);
			}
			
		}
		if (line.getCustom3() != null) {
			if (listid3 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom3();
				custom = longCodetoCustom(listid3);// convert the long code into a Custom Field
				custom.setCode(line.getCustom3());
				item.setCustom3(custom);
			} else if (!((Level1.equals("Custom3")) || (Level2.equals("Custom3")) || (Level3.equals("Custom3")) || (Level4.equals("Custom3")) || (Level5.equals("Custom3")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom3());
				custom.setValue(line.getCustom3());
				item.setCustom3(custom);
			}
			
		}
		if (line.getCustom4() != null) {
			if (listid4 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom4();
				custom = longCodetoCustom(listid4);// convert the long code into a Custom Field
				custom.setCode(line.getCustom4());
				item.setCustom4(custom);
			} else if (!((Level1.equals("Custom4")) || (Level2.equals("Custom4")) || (Level3.equals("Custom4")) || (Level4.equals("Custom4")) || (Level5.equals("Custom4")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom4());
				custom.setValue(line.getCustom4());
				item.setCustom4(custom);
			}
			
		}
		if (line.getCustom5() != null) {
			if (listid5 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom5();
				custom = longCodetoCustom(listid5);// convert the long code into a Custom Field
				custom.setCode(line.getCustom5());
				item.setCustom5(custom);
			} else if (!((Level1.equals("Custom5")) || (Level2.equals("Custom5")) || (Level3.equals("Custom5")) || (Level4.equals("Custom5")) || (Level5.equals("Custom5")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom5());
				custom.setValue(line.getCustom5());
				item.setCustom5(custom);
			}
			
		}
		if (line.getCustom6() != null) {
			if (listid6 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom6();
				custom = longCodetoCustom(listid6);// convert the long code into a Custom Field
				custom.setCode(line.getCustom6());
				item.setCustom6(custom);
			} else if (!((Level1.equals("Custom6")) || (Level2.equals("Custom6")) || (Level3.equals("Custom6")) || (Level4.equals("Custom6")) || (Level5.equals("Custom6")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom6());
				custom.setValue(line.getCustom6());
				item.setCustom6(custom);
			}
			
		}
		if (line.getCustom7() != null) {
			if (listid7 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom7();
				custom = longCodetoCustom(listid7);// convert the long code into a Custom Field
				custom.setCode(line.getCustom7());
				item.setCustom7(custom);
			} else if (!((Level1.equals("Custom7")) || (Level2.equals("Custom7")) || (Level3.equals("Custom7")) || (Level4.equals("Custom7")) || (Level5.equals("Custom7")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom7());
				custom.setValue(line.getCustom7());
				item.setCustom7(custom);
			}
			
		}
		if (line.getCustom8() != null) {
			if (listid8 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom8();
				custom = longCodetoCustom(listid8);// convert the long code into a Custom Field
				custom.setCode(line.getCustom8());
				item.setCustom8(custom);
			} else if (!((Level1.equals("Custom8")) || (Level2.equals("Custom8")) || (Level3.equals("Custom8")) || (Level4.equals("Custom8")) || (Level5.equals("Custom8")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom8());
				custom.setValue(line.getCustom8());
				item.setCustom8(custom);
			}
			
		}
		if (line.getCustom9() != null) {
			if (listid9 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom9();
				custom = longCodetoCustom(listid9);// convert the long code into a Custom Field
				custom.setCode(line.getCustom9());
				item.setCustom9(custom);
			} else if (!((Level1.equals("Custom9")) || (Level2.equals("Custom9")) || (Level3.equals("Custom9")) || (Level4.equals("Custom9")) || (Level5.equals("Custom9")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setCode(line.getCustom9());
				custom.setValue(line.getCustom9());
				item.setCustom9(custom);
			}
			
		}
		if (line.getCustom10() != null) {
			if (listid10 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom10();
				custom = longCodetoCustom(listid10);// convert the long code into a Custom Field
				custom.setCode(line.getCustom10());
				item.setCustom10(custom);
			} else if (!((Level1.equals("Custom10")) || (Level2.equals("Custom10")) || (Level3.equals("Custom10")) || (Level4.equals("Custom10")) || (Level5.equals("Custom10")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom10());
				custom.setValue(line.getCustom10());
				item.setCustom10(custom);
			}
			
			
		}
		if (line.getCustom11() != null) {
			if (listid11 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom11();
				custom = longCodetoCustom(listid11);// convert the long code into a Custom Field
				custom.setCode(line.getCustom11());
				item.setCustom11(custom);
			} else if (!((Level1.equals("Custom11")) || (Level2.equals("Custom11")) || (Level3.equals("Custom11")) || (Level4.equals("Custom11")) || (Level5.equals("Custom11")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom11());
				custom.setValue(line.getCustom11());
				item.setCustom11(custom);
			}
			
		}
		if (line.getCustom12() != null) {
			if (listid12 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom12();
				custom = longCodetoCustom(listid12);// convert the long code into a Custom Field
				custom.setCode(line.getCustom12());
				item.setCustom12(custom);
			} else if (!((Level1.equals("Custom12")) || (Level2.equals("Custom12")) || (Level3.equals("Custom12")) || (Level4.equals("Custom12")) || (Level5.equals("Custom12")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom12());
				custom.setValue(line.getCustom12());
				item.setCustom12(custom);
			}
			
		}
		if (line.getCustom13() != null) {
			if (listid13 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom13();
				custom = longCodetoCustom(listid13);// convert the long code into a Custom Field
				custom.setCode(line.getCustom13());
				item.setCustom13(custom);
			} else if (!((Level1.equals("Custom13")) || (Level2.equals("Custom13")) || (Level3.equals("Custom13")) || (Level4.equals("Custom13")) || (Level5.equals("Custom13")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom13());
				custom.setValue(line.getCustom13());
				item.setCustom13(custom);
			}
			
		}
		if (line.getCustom14() != null) {
			if (listid14 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom14();
				custom = longCodetoCustom(listid14);// convert the long code into a Custom Field
				custom.setCode(line.getCustom14());
				item.setCustom14(custom);
			} else if (!((Level1.equals("Custom14")) || (Level2.equals("Custom14")) || (Level3.equals("Custom14")) || (Level4.equals("Custom14")) || (Level5.equals("Custom14")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom14());
				custom.setValue(line.getCustom14());
				item.setCustom14(custom);
			}
			
		}
		if (line.getCustom15() != null) {
			if (listid15 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom15();
				custom = longCodetoCustom(listid15);// convert the long code into a Custom Field
				custom.setCode(line.getCustom15());
				item.setCustom15(custom);
			} else if (!((Level1.equals("Custom15")) || (Level2.equals("Custom15")) || (Level3.equals("Custom15")) || (Level4.equals("Custom15")) || (Level5.equals("Custom15")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom15());
				custom.setValue(line.getCustom15());
				item.setCustom15(custom);
			}
			
		}
		if (line.getCustom16() != null) {
			if (listid16 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom16();
				custom = longCodetoCustom(listid16);// convert the long code into a Custom Field
				custom.setCode(line.getCustom16());
				item.setCustom16(custom);
			} else if (!((Level1.equals("Custom16")) || (Level2.equals("Custom16")) ||(Level3.equals("Custom16")) || (Level4.equals("Custom16")) || (Level5.equals("Custom16"))) ) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom16());
				custom.setValue(line.getCustom16());
				item.setCustom16(custom);
			}
			
		}
		if (line.getCustom17() != null) {
			if (listid17 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom17();
				custom = longCodetoCustom(listid17);// convert the long code into a Custom Field
				custom.setCode(line.getCustom17());
				item.setCustom17(custom);
			} else if (!((Level1.equals("Custom17")) || (Level2.equals("Custom17")) || (Level3.equals("Custom17")) || (Level4.equals("Custom17")) || (Level5.equals("Custom17")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom17());
				custom.setValue(line.getCustom17());
				item.setCustom17(custom);
			}
			
		}
		if (line.getCustom18() != null) {
			if (listid18 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom18();
				custom = longCodetoCustom(listid18);// convert the long code into a Custom Field
				custom.setCode(line.getCustom18());
				item.setCustom18(custom);
			} else if (!((Level1.equals("Custom18")) || (Level2.equals("Custom18")) || (Level3.equals("Custom18")) || (Level4.equals("Custom18")) || (Level5.equals("Custom18")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom18());
				custom.setValue(line.getCustom18());
				item.setCustom18(custom);
			}
			
		}
		if (line.getCustom19() != null) {
			if (listid19 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom19();
				custom = longCodetoCustom(listid19);// convert the long code into a Custom Field
				custom.setCode(line.getCustom19());
				item.setCustom19(custom);
			} else if (!((Level1.equals("Custom19")) || (Level2.equals("Custom19")) || (Level3.equals("Custom19")) || (Level4.equals("Custom19")) || (Level5.equals("Custom19")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom19());
				custom.setValue(line.getCustom19());
				item.setCustom19(custom);
			}
			
		}
		if (line.getCustom20() != null) {
			if (listid20 != null){// then this is a List custom field type
				listtype = "List";
				longcode = line.getCustom20();
				custom = longCodetoCustom(listid20);// convert the long code into a Custom Field
				custom.setCode(line.getCustom20());
				item.setCustom20(custom);
			} else if (!((Level1.equals("Custom20")) || (Level2.equals("Custom20")) || (Level3.equals("Custom20")) || (Level4.equals("Custom20")) || (Level5.equals("Custom20")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(line.getCustom20());
				custom.setValue(line.getCustom20());
				item.setCustom20(custom);
			}
		}
				
		// add the allocations and journal entries for this Line Item
		allocations = new ArrayList<Allocation>();// initialize the ArrayList of Allocation objects
		invallocations = line.getAllocations();// get the list of InvoiceAllocation objects for this line item
		for (InvoiceAllocation invallocation:invallocations){// Iterate for each InvoiceAllocation object for this Itemization
			allocation = new Allocation();// initialize a new Allocation object
			allocation.setID(GUID.getGUID(4));//assign a Pivot Payables GUID for this Allocation
			allocation.setAllocationAccountCode(invallocation.getAllocationAccountCode());
			allocation.setPercentage(invallocation.getPercentage());
			
			// Process custom fields that are connected 
			if (usesconnected){// then this Company uses a Connected List for Account/Activity/Phase/Task
				// So, process the connected list
				listtype = "ConnectedList";
				switch (Level1) {
					case "Custom1":
						if (invallocation.getCustom1() != null) {
							longcode = invallocation.getCustom1();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom1());
							allocation.setCustom1(custom);
						}
						break;
					case "Custom2":
						if (invallocation.getCustom2() != null) {
							longcode = invallocation.getCustom2();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom2());
							allocation.setCustom2(custom);
						}
						break;
					case "Custom3":
						if (invallocation.getCustom3() != null) {
							longcode = invallocation.getCustom3();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom3());
							allocation.setCustom3(custom);
						}
						break;
					case "Custom4":
						if (invallocation.getCustom4() != null) {
							longcode = invallocation.getCustom4();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom4());
							allocation.setCustom4(custom);
						}
						break;
					case "Custom5":
						if (invallocation.getCustom5() != null) {
							longcode = invallocation.getCustom5();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom5());
							allocation.setCustom5(custom);
						}
						break;
					case "Custom6":
						if (invallocation.getCustom6() != null) {
							longcode = invallocation.getCustom6();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom6());
							allocation.setCustom6(custom);
						}
						break;	
					case "Custom7":
						if (invallocation.getCustom7() != null) {
							longcode = invallocation.getCustom7();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom7());
							allocation.setCustom7(custom);
						}
						break;	
					case "Custom8":
						if (invallocation.getCustom8() != null) {
							longcode = invallocation.getCustom8();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom8());
							allocation.setCustom8(custom);
						}
						break;	
					case "Custom9":
						if (invallocation.getCustom9() != null) {
							longcode = invallocation.getCustom9();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom9());
							allocation.setCustom9(custom);
						}
						break;	
					case "Custom10":
						if (invallocation.getCustom10() != null) {
							longcode = invallocation.getCustom10();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom10());
							allocation.setCustom10(custom);
						}
						break;	
					case "Custom11":
						if (invallocation.getCustom11() != null) {
							longcode = invallocation.getCustom11();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom11());
							allocation.setCustom11(custom);
						}
						break;
					case "Custom12":
						if (invallocation.getCustom12() != null) {
							longcode = invallocation.getCustom12();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom12());
							allocation.setCustom12(custom);
						}
						break;
					case "Custom13":
						longcode = invallocation.getCustom13();// save the item code as the first segment of the long code
						if (invallocation.getCustom13() != null) {
							longcode = invallocation.getCustom13();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom13());
							allocation.setCustom13(custom);
						}
						break;
					case "Custom14":
						if (invallocation.getCustom14() != null) {
							longcode = invallocation.getCustom14();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom14());
							allocation.setCustom14(custom);
						}
						break;
					case "Custom15":
						if (invallocation.getCustom15() != null) {
							longcode = invallocation.getCustom15();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom15());
							allocation.setCustom15(custom);
						}
						break;
					case "Custom16":
						if (invallocation.getCustom16() != null) {
							longcode = invallocation.getCustom16();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom16());
							allocation.setCustom16(custom);
						}
						break;	
					case "Custom17":
						if (invallocation.getCustom17() != null) {
							longcode = invallocation.getCustom17();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom17());
							allocation.setCustom17(custom);
						}
						break;	
					case "Custom18":
						if (invallocation.getCustom18() != null) {
							longcode = invallocation.getCustom18();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom18());
							allocation.setCustom18(custom);
						}
						break;	
					case "Custom19":
						if (invallocation.getCustom19() != null) {
							longcode = invallocation.getCustom19();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom19());
							allocation.setCustom19(custom);
						}
						break;	
					case "Custom20":// save the item code as the second segment of the long codem20":
						if (invallocation.getCustom20() != null) {
							longcode = invallocation.getCustom20();
							custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
							custom.setCode(invallocation.getCustom20());
							allocation.setCustom20(custom);
						}
				
				
			}
			switch (Level2) {
				case "Custom1":
					if (invallocation.getCustom1() != null) {
						longcode = longcode + "-" + invallocation.getCustom1();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom1());
						allocation.setCustom1(custom);
					}
					break;
				case "Custom2":
					if (invallocation.getCustom2() != null) {
						longcode = longcode + "-" + invallocation.getCustom2();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom2());
						allocation.setCustom2(custom);
					}
					break;
				case "Custom3":
					if (invallocation.getCustom3() != null) {
						longcode = longcode + "-" + invallocation.getCustom3();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom3());
						allocation.setCustom3(custom);
					}
					break;
				case "Custom4":
					if (invallocation.getCustom4() != null) {
						longcode = longcode + "-" + invallocation.getCustom4();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom4());
						allocation.setCustom4(custom);
					}
					break;
				case "Custom5":
					if (invallocation.getCustom5() != null) {
						longcode = longcode + "-" + invallocation.getCustom5();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom5());
						allocation.setCustom5(custom);
					}
					break;
				case "Custom6":
					if (invallocation.getCustom6() != null) {
						longcode = longcode + "-" + invallocation.getCustom6();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom6());
						allocation.setCustom6(custom);
					}
					break;	
				case "Custom7":
					if (invallocation.getCustom7() != null) {
						longcode = longcode + "-" + invallocation.getCustom7();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom7());
						allocation.setCustom7(custom);
					}
					break;	
				case "Custom8":
					if (invallocation.getCustom8() != null) {
						longcode = longcode + "-" + invallocation.getCustom8();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom8());
						allocation.setCustom8(custom);
					}
					break;	
				case "Custom9":
					if (invallocation.getCustom9() != null) {
						longcode = longcode + "-" + invallocation.getCustom9();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom9());
						allocation.setCustom9(custom);
					}
					break;	
				case "Custom10":
					if (invallocation.getCustom10() != null) {
						longcode = longcode + "-" + invallocation.getCustom10();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom10());
						allocation.setCustom10(custom);
					}
					break;	
				case "Custom11":
					if (invallocation.getCustom11() != null) {
						longcode = longcode + "-" + invallocation.getCustom11();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom11());
						allocation.setCustom11(custom);
					}
					break;
				case "Custom12":
					if (invallocation.getCustom12() != null) {
						longcode = longcode + "-" + invallocation.getCustom12();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom12());
						allocation.setCustom12(custom);
					}
					break;
				case "Custom13":
					longcode = longcode + "-" + invallocation.getCustom13();// save the item code as the first segment of the long code
					if (invallocation.getCustom13() != null) {
						longcode = longcode + "-" + invallocation.getCustom13();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom13());
						allocation.setCustom13(custom);
					}
					break;
				case "Custom14":
					if (invallocation.getCustom14() != null) {
						longcode = longcode + "-" + invallocation.getCustom14();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom14());
						allocation.setCustom14(custom);
					}
					break;
				case "Custom15":
					if (invallocation.getCustom15() != null) {
						longcode = longcode + "-" + invallocation.getCustom15();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom15());
						allocation.setCustom15(custom);
					}
					break;
				case "Custom16":
					if (invallocation.getCustom16() != null) {
						longcode = longcode + "-" + invallocation.getCustom16();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom16());
						allocation.setCustom16(custom);
					}
					break;	
				case "Custom17":
					if (invallocation.getCustom17() != null) {
						longcode = longcode + "-" + invallocation.getCustom17();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom17());
						allocation.setCustom17(custom);
					}
					break;	
				case "Custom18":
					if (invallocation.getCustom18() != null) {
						longcode = longcode + "-" + invallocation.getCustom18();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom18());
						allocation.setCustom18(custom);
					}
					break;	
				case "Custom19":
					if (invallocation.getCustom19() != null) {
						longcode = longcode + "-" + invallocation.getCustom19();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom19());
						allocation.setCustom19(custom);
					}
					break;	
				case "Custom20":// save the item code as the second segment of the long codem20":
					if (invallocation.getCustom20() != null) {
						longcode = longcode + "-" + invallocation.getCustom20();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom20());
						allocation.setCustom20(custom);
					}
			}
			if (Level3 != null){
				switch (Level3) {
				case "Custom1":
					if (invallocation.getCustom1() != null) {
						longcode = longcode + "-" + invallocation.getCustom1();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom1());
						allocation.setCustom1(custom);
					}
					break;
				case "Custom2":
					if (invallocation.getCustom2() != null) {
						longcode = longcode + "-" + invallocation.getCustom2();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom2());
						allocation.setCustom2(custom);
					}
					break;
				case "Custom3":
					if (invallocation.getCustom3() != null) {
						longcode = longcode + "-" + invallocation.getCustom3();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom3());
						allocation.setCustom3(custom);
					}
					break;
				case "Custom4":
					if (invallocation.getCustom4() != null) {
						longcode = longcode + "-" + invallocation.getCustom4();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom4());
						allocation.setCustom4(custom);
					}
					break;
				case "Custom5":
					if (invallocation.getCustom5() != null) {
						longcode = longcode + "-" + invallocation.getCustom5();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom5());
						allocation.setCustom5(custom);
					}
					break;
				case "Custom6":
					if (invallocation.getCustom6() != null) {
						longcode = longcode + "-" + invallocation.getCustom6();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom6());
						allocation.setCustom6(custom);
					}
					break;	
				case "Custom7":
					if (invallocation.getCustom7() != null) {
						longcode = longcode + "-" + invallocation.getCustom7();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom7());
						allocation.setCustom7(custom);
					}
					break;	
				case "Custom8":
					if (invallocation.getCustom8() != null) {
						longcode = longcode + "-" + invallocation.getCustom8();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom8());
						allocation.setCustom8(custom);
					}
					break;	
				case "Custom9":
					if (invallocation.getCustom9() != null) {
						longcode = longcode + "-" + invallocation.getCustom9();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom9());
						allocation.setCustom9(custom);
					}
					break;	
				case "Custom10":
					if (invallocation.getCustom10() != null) {
						longcode = longcode + "-" + invallocation.getCustom10();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom10());
						allocation.setCustom10(custom);
					}
					break;	
				case "Custom11":
					if (invallocation.getCustom11() != null) {
						longcode = longcode + "-" + invallocation.getCustom11();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom11());
						allocation.setCustom11(custom);
					}
					break;
				case "Custom12":
					if (invallocation.getCustom12() != null) {
						longcode = longcode + "-" + invallocation.getCustom12();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom12());
						allocation.setCustom12(custom);
					}
					break;
				case "Custom13":
					longcode = longcode + "-" + invallocation.getCustom13();// save the item code as the first segment of the long code
					if (invallocation.getCustom13() != null) {
						longcode = longcode + "-" + invallocation.getCustom13();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom13());
						allocation.setCustom13(custom);
					}
					break;
				case "Custom14":
					if (invallocation.getCustom14() != null) {
						longcode = longcode + "-" + invallocation.getCustom14();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom14());
						allocation.setCustom14(custom);
					}
					break;
				case "Custom15":
					if (invallocation.getCustom15() != null) {
						longcode = longcode + "-" + invallocation.getCustom15();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom15());
						allocation.setCustom15(custom);
					}
					break;
				case "Custom16":
					if (invallocation.getCustom16() != null) {
						longcode = longcode + "-" + invallocation.getCustom16();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom16());
						allocation.setCustom16(custom);
					}
					break;	
				case "Custom17":
					if (invallocation.getCustom17() != null) {
						longcode = longcode + "-" + invallocation.getCustom17();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom17());
						allocation.setCustom17(custom);
					}
					break;	
				case "Custom18":
					if (invallocation.getCustom18() != null) {
						longcode = longcode + "-" + invallocation.getCustom18();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom18());
						allocation.setCustom18(custom);
					}
					break;	
				case "Custom19":
					if (invallocation.getCustom19() != null) {
						longcode = longcode + "-" + invallocation.getCustom19();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom19());
						allocation.setCustom19(custom);
					}
					break;	
				case "Custom20":// save the item code as the second segment of the long codem20":
					if (invallocation.getCustom20() != null) {
						longcode = longcode + "-" + invallocation.getCustom20();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom20());
						allocation.setCustom20(custom);
					}
			
				}
		}
		if (Level4 != null){
			switch (Level4) {
			case "Custom1":
				if (invallocation.getCustom1() != null) {
					longcode = longcode + "-" + invallocation.getCustom1();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom1());
					allocation.setCustom1(custom);
				}
				break;
			case "Custom2":
				if (invallocation.getCustom2() != null) {
					longcode = longcode + "-" + invallocation.getCustom2();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom2());
					allocation.setCustom2(custom);
				}
				break;
			case "Custom3":
				if (invallocation.getCustom3() != null) {
					longcode = longcode + "-" + invallocation.getCustom3();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom3());
					allocation.setCustom3(custom);
				}
				break;
			case "Custom4":
				if (invallocation.getCustom4() != null) {
					longcode = longcode + "-" + invallocation.getCustom4();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom4());
					allocation.setCustom4(custom);
				}
				break;
			case "Custom5":
				if (invallocation.getCustom5() != null) {
					longcode = longcode + "-" + invallocation.getCustom5();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom5());
					allocation.setCustom5(custom);
				}
				break;
			case "Custom6":
				if (invallocation.getCustom6() != null) {
					longcode = longcode + "-" + invallocation.getCustom6();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom6());
					allocation.setCustom6(custom);
				}
				break;	
			case "Custom7":
				if (invallocation.getCustom7() != null) {
					longcode = longcode + "-" + invallocation.getCustom7();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom7());
					allocation.setCustom7(custom);
				}
				break;	
			case "Custom8":
				if (invallocation.getCustom8() != null) {
					longcode = longcode + "-" + invallocation.getCustom8();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom8());
					allocation.setCustom8(custom);
				}
				break;	
			case "Custom9":
				if (invallocation.getCustom9() != null) {
					longcode = longcode + "-" + invallocation.getCustom9();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom9());
					allocation.setCustom9(custom);
				}
				break;	
			case "Custom10":
				if (invallocation.getCustom10() != null) {
					longcode = longcode + "-" + invallocation.getCustom10();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom10());
					allocation.setCustom10(custom);
				}
				break;	
			case "Custom11":
				if (invallocation.getCustom11() != null) {
					longcode = longcode + "-" + invallocation.getCustom11();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom11());
					allocation.setCustom11(custom);
				}
				break;
			case "Custom12":
				if (invallocation.getCustom12() != null) {
					longcode = longcode + "-" + invallocation.getCustom12();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom12());
					allocation.setCustom12(custom);
				}
				break;
			case "Custom13":
				longcode = longcode + "-" + invallocation.getCustom13();// save the item code as the first segment of the long code
				if (invallocation.getCustom13() != null) {
					longcode = longcode + "-" + invallocation.getCustom13();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom13());
					allocation.setCustom13(custom);
				}
				break;
			case "Custom14":
				if (invallocation.getCustom14() != null) {
					longcode = longcode + "-" + invallocation.getCustom14();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom14());
					allocation.setCustom14(custom);
				}
				break;
			case "Custom15":
				if (invallocation.getCustom15() != null) {
					longcode = longcode + "-" + invallocation.getCustom15();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom15());
					allocation.setCustom15(custom);
				}
				break;
			case "Custom16":
				if (invallocation.getCustom16() != null) {
					longcode = longcode + "-" + invallocation.getCustom16();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom16());
					allocation.setCustom16(custom);
				}
				break;	
			case "Custom17":
				if (invallocation.getCustom17() != null) {
					longcode = longcode + "-" + invallocation.getCustom17();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom17());
					allocation.setCustom17(custom);
				}
				break;	
			case "Custom18":
				if (invallocation.getCustom18() != null) {
					longcode = longcode + "-" + invallocation.getCustom18();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom18());
					allocation.setCustom18(custom);
				}
				break;	
			case "Custom19":
				if (invallocation.getCustom19() != null) {
					longcode = longcode + "-" + invallocation.getCustom19();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom19());
					allocation.setCustom19(custom);
				}
				break;	
			case "Custom20":// save the item code as the second segment of the long codem20":
				if (invallocation.getCustom20() != null) {
					longcode = longcode + "-" + invallocation.getCustom20();
					custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
					custom.setCode(invallocation.getCustom20());
					allocation.setCustom20(custom);
				}
			}
		}
		if (Level5 != null){
			switch (Level5) {
				case "Custom1":
					if (invallocation.getCustom1() != null) {
						longcode = longcode + "-" + invallocation.getCustom1();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom1());
						allocation.setCustom1(custom);
					}
					break;
				case "Custom2":
					if (invallocation.getCustom2() != null) {
						longcode = longcode + "-" + invallocation.getCustom2();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom2());
						allocation.setCustom2(custom);
					}
					break;
				case "Custom3":
					if (invallocation.getCustom3() != null) {
						longcode = longcode + "-" + invallocation.getCustom3();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom3());
						allocation.setCustom3(custom);
					}
					break;
				case "Custom4":
					if (invallocation.getCustom4() != null) {
						longcode = longcode + "-" + invallocation.getCustom4();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom4());
						allocation.setCustom4(custom);
					}
					break;
				case "Custom5":
					if (invallocation.getCustom5() != null) {
						longcode = longcode + "-" + invallocation.getCustom5();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom5());
						allocation.setCustom5(custom);
					}
					break;
				case "Custom6":
					if (invallocation.getCustom6() != null) {
						longcode = longcode + "-" + invallocation.getCustom6();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom6());
						allocation.setCustom6(custom);
					}
					break;	
				case "Custom7":
					if (invallocation.getCustom7() != null) {
						longcode = longcode + "-" + invallocation.getCustom7();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom7());
						allocation.setCustom7(custom);
					}
					break;	
				case "Custom8":
					if (invallocation.getCustom8() != null) {
						longcode = longcode + "-" + invallocation.getCustom8();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom8());
						allocation.setCustom8(custom);
					}
					break;	
				case "Custom9":
					if (invallocation.getCustom9() != null) {
						longcode = longcode + "-" + invallocation.getCustom9();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom9());
						allocation.setCustom9(custom);
					}
					break;	
				case "Custom10":
					if (invallocation.getCustom10() != null) {
						longcode = longcode + "-" + invallocation.getCustom10();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom10());
						allocation.setCustom10(custom);
					}
					break;	
				case "Custom11":
					if (invallocation.getCustom11() != null) {
						longcode = longcode + "-" + invallocation.getCustom11();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom11());
						allocation.setCustom11(custom);
					}
					break;
				case "Custom12":
					if (invallocation.getCustom12() != null) {
						longcode = longcode + "-" + invallocation.getCustom12();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom12());
						allocation.setCustom12(custom);
					}
					break;
				case "Custom13":
					longcode = longcode + "-" + invallocation.getCustom13();// save the item code as the first segment of the long code
					if (invallocation.getCustom13() != null) {
						longcode = longcode + "-" + invallocation.getCustom13();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom13());
						allocation.setCustom13(custom);
					}
					break;
				case "Custom14":
					if (invallocation.getCustom14() != null) {
						longcode = longcode + "-" + invallocation.getCustom14();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom14());
						allocation.setCustom14(custom);
					}
					break;
				case "Custom15":
					if (invallocation.getCustom15() != null) {
						longcode = longcode + "-" + invallocation.getCustom15();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom15());
						allocation.setCustom15(custom);
					}
					break;
				case "Custom16":
					if (invallocation.getCustom16() != null) {
						longcode = longcode + "-" + invallocation.getCustom16();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom16());
						allocation.setCustom16(custom);
					}
					break;	
				case "Custom17":
					if (invallocation.getCustom17() != null) {
						longcode = longcode + "-" + invallocation.getCustom17();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom17());
						allocation.setCustom17(custom);
					}
					break;	
				case "Custom18":
					if (invallocation.getCustom18() != null) {
						longcode = longcode + "-" + invallocation.getCustom18();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom18());
						allocation.setCustom18(custom);
					}
					break;	
				case "Custom19":
					if (invallocation.getCustom19() != null) {
						longcode = longcode + "-" + invallocation.getCustom19();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom19());
						allocation.setCustom19(custom);
					}
					break;	
				case "Custom20":// save the item code as the second segment of the long codem20":
					if (invallocation.getCustom20() != null) {
						longcode = longcode + "-" + invallocation.getCustom20();
						custom = longCodetoCustom(connectedlistid);// convert the long code into a Custom Field
						custom.setCode(invallocation.getCustom20());
						allocation.setCustom20(custom);
					}
				}
			}
		}// if (usesconnected)
		// Next, process custom fields that are either a simple list or a text field type
		if (invallocation.getCustom1() != null) {
			if (listid1 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom1();
				custom = longCodetoCustom(listid1);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom1());
				allocation.setCustom1(custom);
			} else if (!((Level1.equals("Custom1")) || (Level2.equals("Custom1")) || (Level3.equals("Custom1")) || (Level4.equals("Custom1")) || (Level5.equals("Custom1")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom1());
				custom.setValue(invallocation.getCustom1());
				allocation.setCustom1(custom);
			}
			
		}
		if (invallocation.getCustom2() != null) {
			if (listid2 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom2();
				custom = longCodetoCustom(listid2);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom2());
				allocation.setCustom2(custom);
			} else if (!((Level1.equals("Custom2")) || (Level2.equals("Custom2")) || (Level3.equals("Custom2")) || (Level4.equals("Custom2")) || (Level5.equals("Custom2")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom2());
				custom.setValue(invallocation.getCustom2());
			}
			
		}
		if (invallocation.getCustom3() != null) {
			if (listid3 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom3();
				custom = longCodetoCustom(listid3);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom3());
				allocation.setCustom3(custom);
			} else if (!((Level1.equals("Custom3")) || (Level2.equals("Custom3")) || (Level3.equals("Custom3")) || (Level4.equals("Custom3")) || (Level5.equals("Custom3")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom3());
				custom.setValue(invallocation.getCustom3());
				allocation.setCustom3(custom);
			}
			
		}
		if (invallocation.getCustom4() != null) {
			if (listid4 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom4();
				custom = longCodetoCustom(listid4);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom4());
				allocation.setCustom4(custom);
			} else if (!((Level1.equals("Custom4")) || (Level2.equals("Custom4")) || (Level3.equals("Custom4")) || (Level4.equals("Custom4")) || (Level5.equals("Custom4")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom4());
				custom.setValue(invallocation.getCustom4());
				allocation.setCustom4(custom);
			}
			
		}
		if (invallocation.getCustom5() != null) {
			if (listid5 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom5();
				custom = longCodetoCustom(listid5);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom5());
				allocation.setCustom5(custom);
			} else if (!((Level1.equals("Custom5")) || (Level2.equals("Custom5")) || (Level3.equals("Custom5")) || (Level4.equals("Custom5")) || (Level5.equals("Custom5")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom5());
				custom.setValue(invallocation.getCustom5());
				allocation.setCustom5(custom);
			}
			
		}
		if (invallocation.getCustom6() != null) {
			if (listid6 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom6();
				custom = longCodetoCustom(listid6);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom6());
				allocation.setCustom6(custom);
			} else if (!((Level1.equals("Custom6")) || (Level2.equals("Custom6")) || (Level3.equals("Custom6")) || (Level4.equals("Custom6")) || (Level5.equals("Custom6")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom6());
				custom.setValue(invallocation.getCustom6());
				allocation.setCustom6(custom);
			}
			
		}
		if (invallocation.getCustom7() != null) {
			if (listid7 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom7();
				custom = longCodetoCustom(listid7);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom7());
				allocation.setCustom7(custom);
			} else if (!((Level1.equals("Custom7")) || (Level2.equals("Custom7")) || (Level3.equals("Custom7")) || (Level4.equals("Custom7")) || (Level5.equals("Custom7")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom7());
				custom.setValue(invallocation.getCustom7());
				allocation.setCustom7(custom);
			}
			
		}
		if (invallocation.getCustom8() != null) {
			if (listid8 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom8();
				custom = longCodetoCustom(listid8);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom8());
				allocation.setCustom8(custom);
			} else if (!((Level1.equals("Custom8")) || (Level2.equals("Custom8")) || (Level3.equals("Custom8")) || (Level4.equals("Custom8")) || (Level5.equals("Custom8")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom8());
				custom.setValue(invallocation.getCustom8());
				allocation.setCustom8(custom);
			}
			
		}
		if (invallocation.getCustom9() != null) {
			if (listid9 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom9();
				custom = longCodetoCustom(listid9);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom9());
				allocation.setCustom9(custom);
			} else if (!((Level1.equals("Custom9")) || (Level2.equals("Custom9")) || (Level3.equals("Custom9")) || (Level4.equals("Custom9")) || (Level5.equals("Custom9")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom9());
				custom.setValue(invallocation.getCustom9());
				allocation.setCustom9(custom);
			}
			
		}
		if (invallocation.getCustom10() != null) {
			if (listid10 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom10();
				custom = longCodetoCustom(listid10);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom10());
				allocation.setCustom10(custom);
			} else if (!((Level1.equals("Custom10")) || (Level2.equals("Custom10")) || (Level3.equals("Custom10")) || (Level4.equals("Custom10")) || (Level5.equals("Custom10")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom10());
				custom.setValue(invallocation.getCustom10());
				allocation.setCustom10(custom);
			}
			
			
		}
		if (invallocation.getCustom11() != null) {
			if (listid11 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom11();
				custom = longCodetoCustom(listid11);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom11());
				allocation.setCustom11(custom);
			} else if (!((Level1.equals("Custom11")) || (Level2.equals("Custom11")) || (Level3.equals("Custom11")) || (Level4.equals("Custom11")) || (Level5.equals("Custom11")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom11());
				custom.setValue(invallocation.getCustom11());
				allocation.setCustom11(custom);
			}
			
		}
		if (invallocation.getCustom12() != null) {
			if (listid12 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom12();
				custom = longCodetoCustom(listid12);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom12());
				allocation.setCustom12(custom);
			} else if (!((Level1.equals("Custom12")) || (Level2.equals("Custom12")) || (Level3.equals("Custom12")) || (Level4.equals("Custom12")) || (Level5.equals("Custom12")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom12());
				custom.setValue(invallocation.getCustom12());
				allocation.setCustom12(custom);
			}
			
		}
		if (invallocation.getCustom13() != null) {
			if (listid13 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom13();
				custom = longCodetoCustom(listid13);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom13());
				allocation.setCustom13(custom);
			} else if (!((Level1.equals("Custom13")) || (Level2.equals("Custom13")) || (Level3.equals("Custom13")) || (Level4.equals("Custom13")) || (Level5.equals("Custom13")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setCode(invallocation.getCustom13());
				custom.setValue(invallocation.getCustom13());
				allocation.setCustom13(custom);
			}
			
		}
		if (invallocation.getCustom14() != null) {
			if (listid14 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom14();
				custom = longCodetoCustom(listid14);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom14());
				allocation.setCustom14(custom);
			} else if (!((Level1.equals("Custom14")) || (Level2.equals("Custom14")) || (Level3.equals("Custom14")) || (Level4.equals("Custom14")) || (Level5.equals("Custom14")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom14());
				custom.setValue(invallocation.getCustom14());
				allocation.setCustom14(custom);
			}
			
		}
		if (invallocation.getCustom15() != null) {
			if (listid15 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom15();
				custom = longCodetoCustom(listid15);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom15());
				allocation.setCustom15(custom);
			} else if (!((Level1.equals("Custom15")) || (Level2.equals("Custom15")) || (Level3.equals("Custom15")) || (Level4.equals("Custom15")) || (Level5.equals("Custom15")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom15());
				custom.setValue(invallocation.getCustom15());
				allocation.setCustom15(custom);
			}
			
		}
		if (invallocation.getCustom16() != null) {
			if (listid16 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom16();
				custom = longCodetoCustom(listid16);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom16());
				allocation.setCustom16(custom);
			} else if (!((Level1.equals("Custom16")) || (Level2.equals("Custom16")) || (Level3.equals("Custom16")) || (Level4.equals("Custom16")) || (Level5.equals("Custom16")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom16());
				custom.setValue(invallocation.getCustom16());
				allocation.setCustom16(custom);
			}
			
		}
		if (invallocation.getCustom17() != null) {
			if (listid17 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom17();
				custom = longCodetoCustom(listid17);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom17());
				allocation.setCustom17(custom);
			} else if (!((Level1.equals("Custom17")) || (Level2.equals("Custom17")) || (Level3.equals("Custom17")) || (Level4.equals("Custom17")) || (Level5.equals("Custom17")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setCode(invallocation.getCustom17());
				custom.setValue(invallocation.getCustom17());
				allocation.setCustom17(custom);
			}
			
		}
		if (invallocation.getCustom18() != null) {
			if (listid18 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom18();
				custom = longCodetoCustom(listid18);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom18());
				allocation.setCustom18(custom);
			} else if (!((Level1.equals("Custom18")) || (Level2.equals("Custom18")) || (Level3.equals("Custom18")) || (Level4.equals("Custom18")) || (Level5.equals("Custom18")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom18());
				custom.setValue(invallocation.getCustom18());
				allocation.setCustom18(custom);
			}
			
		}
		if (invallocation.getCustom19() != null) {
			if (listid19 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom19();
				custom = longCodetoCustom(listid19);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom19());
				allocation.setCustom19(custom);
			} else if (!((Level1.equals("Custom19")) || (Level2.equals("Custom19")) || (Level3.equals("Custom19")) || (Level4.equals("Custom19")) || (Level5.equals("Custom19")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setType("Text");
				custom.setCode(invallocation.getCustom19());
				custom.setValue(invallocation.getCustom19());
				allocation.setCustom19(custom);
			}
			
		}
		if (invallocation.getCustom20() != null) {
			if (listid20 != null){// then this is a List custom field type
				listtype = "List";
				longcode = invallocation.getCustom20();
				custom = longCodetoCustom(listid20);// convert the long code into a Custom Field
				custom.setCode(invallocation.getCustom20());
				allocation.setCustom20(custom);
			} else if (!((Level1.equals("Custom20")) || (Level2.equals("Custom20")) || (Level3.equals("Custom20")) || (Level4.equals("Custom20")) || (Level5.equals("Custom20")))) {// then this isn't a connected list field, so it's text custom field type
				custom = new CustomField();
				custom.setCode(invallocation.getCustom20());
				custom.setValue(invallocation.getCustom20());
				allocation.setCustom20(custom);
			}
			
			
		}
					
			journals = new ArrayList<JournalEntry>();// initialize the ArrayList of JournalEntry objects for this Allocation
			journal = new JournalEntry();// initialize a JournalEntry object
			journal.setID(GUID.getGUID(4));//assign a Pivot Payables GUID for this Journal Entry
			journal.setAccountCode(invallocation.getAllocationAccountCode());// set the Journal Account Code to the Allocation Account Code
			// calculate the Journal Amount by multiplying the Allocation Percentage (must first convert string into a double) by the Line Item Total Price
			amount = (Double.valueOf(invallocation.getPercentage())/100)*line.getTotalPrice(); // the Journal Amount
			journal.setAmount(amount);
			if (amount >= 0){
				journal.setDebitOrCredit("CR");
			} else {
				journal.setDebitOrCredit("DB");
			}
			journal.setPayerPaymentTypeName("Company");// This indicates that the company owes the vendor the Journal Amount
			journal.setPayeePaymentTypeName("AP");// this means the AP application will pay the vendor;
			journal.setPayeePaymentTypeCode("AP");
			
			//TODO - handle vendors paid by Invoice Pay
			
			/* We need to create a Vendor Lookup Table that determines whether the vendor gets paid by Invoice Pay
			 * If so, the Payee Payment Type may be CHECK or ACH indicating whether Invoice Pays using a check or ACH
			 */
			
			journal.setPostedCurrency(invoice.getCurrencyCode());
			journals.add(journal);// there is only one journal entry per allocation, so add this element 0 of the ArrayList
			allocation.setJournals(journals);// add the Journal Entry to the Allocation
			
			//TODO - handle VAT
			/* Need to better understand how Invoice does VAT.  It has two VAT amounts:
			 * VAT One and VAT Two.
			 * 
			 */
			
			vatdetails = new ArrayList<VATData>();
			allocation.setVatdetails(vatdetails);
	
			allocations.add(allocation);
				
			}// for (InvoiceAllocation invallocation:invallocations)
			
			item.setAllocations(allocations);// add the ArrayList of Allocations to this Itemization

			
			items.add(item);// add the Itemization to the ArrayList of Itemizations
		
		}// for (LineItem line:invoice.getLineItems())
		myExpense.setItems(items);// assign the Items ArrayList to the Expense's Items ArrayList
		return myExpense;
	}
	private static CustomField longCodetoCustom (String listid) throws JsonParseException, JsonMappingException, JSONException, IOException{
		
		CustomField custom = new CustomField();// initiate the Custom Field object
		
			custom.setType(listtype);
			ListItem listitem = concurfunctions.getListItemByLongCode(key, listid, longcode);// get the Item Name by calling getListItembyLongCode
			if (listitem.getListID() !=null){// then found a list item for this longcode
				custom.setValue(listitem.getName());// set the CustomField, Value to the list item's name
				custom.setListItemID(listitem.getID());// set the CustomField, List Item ID to the list item's ID
			} else {// there isn't a list item that matches the long code
				custom.setValue("Not Available");// set the CustomField, Value to the list item's name
				custom.setListItemID("Not Available");// set the CustomField, List Item ID to the list item's ID
			}

		return custom;
		
	}
	private static String getExpenseTypeName (String expensetypecode) throws JsonParseException, JsonMappingException, JSONException, IOException{
		String name="Not Available";// initialize the expense type name
		String itemcode;
		/*
		 * This method return the expense type name for a specified expense type code and language code
		 * Language code for now is set to en for English.
		 * 
		 */

		String languagecode = "en";// the language code for English
		Map<String, String> queryparameters = new HashMap<String, String>();// a HashMap to hold key-value pairs for a query parameter
		queryparameters.put("type", "expenseType");
		queryparameters.put("langCode", languagecode);
		
		ArrayList<LocalizedData> localizeddata =concurfunctions.getLocalizedData(key, queryparameters);
		
		for (LocalizedData dataitem:localizeddata){
			itemcode=dataitem.getCode().replaceAll("\\s","");// strip white space from data item code so that it can be compared
			if (itemcode.equals(expensetypecode)){// then this localized data item is the one for the expense type code
				return name = dataitem.getLocalizedValue();// get the localized value, which is the expense type name
			}
		}
		return name;
	}
	
}

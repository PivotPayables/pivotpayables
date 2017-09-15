package com.pivotpayables.concurplatform;
/*  An expense is a purchase of a good or service by an employee of a company.
 * 
 * 	The employee pays for the purchase either with the employee's cash or personal credit card, a company paid credit card, or using a purchase order where the merchant invoices
 *  the employee's company (q.k.q., "Company Paid").  This is refered to as the "payment type".  It refers to how the MERCHANT was paid.
 *  
 *  The company reimburses the employee for the approved amount of purchases paid with the employee's cash or personal credit card.
 *  The company pays the card issuer of the company credit card for the approved amount of purchased paid with a company credit card.
 *  The company pays the merchant for purchases paid with by purchase order/invoice.
 */

/**
 * @author John Toman
 * 8/15/2016
 * Base class for an expense entry.
 * 
 * Add doctoExpense method
 * 
 * Add PivotNexus fields
 *
 */
import static java.lang.System.out;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.*;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {
	@XmlElement
	@JsonProperty("ID")
	private String Entry_ID;// the GUID for the Concur Expense, expense entry
	@XmlElement
	@JsonProperty
	private String ReportID;// the GUID for the Concur Expense, expense report this expense entry is a member
	private String CompanyID;// the GUID for the employee's company
	@XmlElement
	@JsonProperty
	private String ReportOwnerID;// The login ID for the report owner
	@XmlElement
	@JsonProperty
	String ExpenseTypeName;
	@XmlElement
	@JsonProperty
	String PaymentTypeName;// the name of the type of payment the employee used to make the purchase
	@XmlElement
	@JsonProperty
	String Description;// The description of the expense
	@XmlElement
	@JsonProperty ("IsBillable")
	Boolean HasBillableItems;// whether any of the itemizations are billable
	@XmlElement
	@JsonProperty
	Boolean HasItemizations;// whether there are itemizations
	@XmlElement
	@JsonProperty
	Boolean IsPersonal;// whether this expense is non-reimbursable or personal
	@XmlElement
	@JsonProperty
	Boolean IsPersonalCardCharge;// whether this expense was imported from a personal card charge downloaded using the Yodlee API
	@XmlElement
	@JsonProperty
	Boolean HasVAT;// whether this expense has VAT
	String LocationDisplayName;// where the purchase happened 
	@XmlElement
	@JsonProperty("LocationName")
	private String LocationCity;
	@XmlElement
	@JsonProperty("LocationSubdivision")
	private String LocationState;
	@XmlElement
	@JsonProperty
	private String LocationCountry;
	@XmlElement
	@JsonProperty
	Date TransactionDate;// when the purchase happened
	@XmlElement
	@JsonProperty ("TransactionCurrencyCode")
	String OriginalCurrency;// the currency paid to merchant, and that appears on the receipt
	@XmlElement
	@JsonProperty("TransactionAmount")
	double OriginalAmount;// the amount of the purchase in original currency, and that appears on the receipt
	@XmlElement
	@JsonProperty
	double PostedAmount;// the original amount converted into posted currency
	@XmlElement
	@JsonProperty
	double ApprovedAmount;// the portion of the posted amount approved for reimbursement/payment to the employee/card issuer
	@XmlElement
	@JsonProperty
	Boolean HasImage;// the entry has a receipt image
	@XmlElement
	@JsonProperty
	Boolean IsPaidByExpensePay;// the entry was paid by Expense Pay
	@XmlElement
	@JsonProperty
	Date LastModified;// when the purchase happened
	@XmlElement
	@JsonProperty ("VendorDescription")
	String MerchantName;// the name of the merchant the employee paid
	@XmlElement
	@JsonProperty
	String ElectronicReceiptID;// When there is an eReceipt associated to this expense this is its unique identifier. 
	@XmlElement
	@JsonProperty
	double ExchangeRate;// The currency conversion rate that converts the Transaction Amount that is in Transaction Currency into the Posted Amount that is in Report Currency. 
	@XmlElement
	@JsonProperty
	String TripID;// When there is a trip in the Itinerary Service that includes the travel booking associated to this expense this is the trip's unique identifier.
	@XmlElement
	@JsonProperty
	Journey Journey;// the Journey object for mileage expenses.  Contains data about the journey.
	@XmlElement
	@JsonProperty
	private CustomField	Custom1;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom2;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom3;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom4;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom5;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom6;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom7;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom8;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom9;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom10;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom11;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom12;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom13;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom14;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom15;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom16;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom17;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom18;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom19;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom20;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom21;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom22;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom23;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom24;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom25;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom26;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom27;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom28;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom29;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom30;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom31;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom32;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom33;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom34;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom35;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom36;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom37;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom38;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom39;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	Custom40;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	OrgUnit1;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	OrgUnit2;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	OrgUnit3;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	OrgUnit4;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	OrgUnit5;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private CustomField	OrgUnit6;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	
	// these aren't in the ExpenseEntry V3.0 Model, but are in the Expense document
	// This means these need to be calculated to determine their values.
	String ID;
	String EmployeeID;// the GUID for the employee who incurred the expense by purchasing a good or service for business use
	String EmployeeDisplayName;// the employee who incurred the expense
	String EmployeeFirstName;
	String EmployeeMiddleInitial;
	private String EmployeeLastName;
	Byte[] ReceiptImage;// Byte array for holding the PDF file of the expense's receipt image
	String PostedCurrency;// the currency of the company's AP system
	String ReportId;// the Report ID of the expense report that contains this expense
	String ReportName;// the name of the expense report that contains this expense
	String ReportPurpose;// the Business Purpose of the expense report that contains this expense
	String LedgerName;// the Ledger Name  of the expense report that contains this expense
	String PolicyID;// the unique identifier for the Expense Policy  of the expense report that contains this expense
	String EmployeeCountry;// the employee's country
	String EmployeeState;// the employee's State or sub-country
	String TaxNexus;// this is whether the expense is Domestic or Foreign
	double ReportTotal;// the total amount in Posted Currency of the expense report that contains this expense
	double PersonalExpense;// the total amount in Posted Currency of personal expenses of the expense report that contains this expense
	double AmountDueEmployee;// the total amount in Posted Currency due to the Report Owner of the expense report that contains this expense
	double AmountDueCompanyCard;// the total amount due in Posted Currency to the card issuer(s) of the expense report that contains this expense
	double TotalApprovedAmount;// the total amount in Posted Currency of approved expenses of the expense report that contains this expense
	String ApprovalStatus;// the approval status of the expense report that contains this expense
	String PaymentStatus;// the payment status of the expense report that contains this expense. Is "Paid" when Concur Expense extracts the expense report.
	Date PaidDate;// when the expense report this expense entry is a member was paid
	String CanonicalTransactionID;// the unique identifier for the Canonical Transaction this Expense is associated
	String NexusStatus;// whether the the Canonical Transaction Job processed this Expense into a Canonical Transaction
	String PayeeVendorID;// the unique identifier from the Vendor Master List for the payee, which can be an employee, a card issuer, or a vendor
	String PayeeAddressID;// the unique identifier from the Vendor Master List for the payee's remittance address
	String PayeePaymentMethod;// the method the employee is reimbursed or the card issuer or vendor is paid
	String PaymentTypeCode;// the code for the Payment Type
	String ReportPayeeID;// the unique identifier for the Report Payee this expense is associated
	String PrimeStatus;// whether the Create Charge job processed this expense
	
	// Itemizations aren't in the Expense V3.0 model.  Instead they are in the Itemization document and the Itemizations V3.0 model
	ArrayList<Itemization> Items;// the itemizations for this expense.  There is at least one itemization for every expense
	
	public Expense () {}// no args constructor

	public void display () {//method to display the expense in the console
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";
		String strDate = null;

		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		


		out.println("EXPENSE REPORT");
		out.println("ReportID: " + getReportID());
		out.println("ReportName: " + getReportName());
		if (ReportPurpose != null) {
			out.println("Report Purpose: " + ReportPurpose.toString());
		}
		out.println("Report Total: " + decimalFormat.format(ReportTotal) + " " + PostedCurrency);
		out.println("TotalApprovedAmount: " + decimalFormat.format(TotalApprovedAmount) + " " + PostedCurrency);
		out.println("Personal Expense: " + decimalFormat.format(PersonalExpense) + " " + PostedCurrency);
		out.println("Amount Due Employee: " + decimalFormat.format(AmountDueEmployee) + " " + PostedCurrency);
		out.println("Amount Due Company Card: " + decimalFormat.format(AmountDueCompanyCard) + " " + PostedCurrency);
		out.println("LedgerName: " + getLedgerName());
		out.println("PolicyID: " + getPolicyID());
		out.println("End Report___________________________________________");
		out.println("ID: " + ID);
		out.println("Entry_ID: " + getEntry_ID());

		out.println("Trip ID: " + TripID);
		out.println("Electronic Receipt ID: " + ElectronicReceiptID);
		out.println("Company ID: " + getCompanyID());
		out.println("Report Owner ID: " + ReportOwnerID);
		out.println("Employee ID: " + EmployeeID);
		
		if (getEmployeeDisplayName() != null){
			out.println("Employee: " + getEmployeeDisplayName());
		}
		if (getEmployeeFirstName() != null){
			out.println("First Name: " + getEmployeeFirstName());
		}
		if (getEmployeeMiddleInitial() == null){
			out.println("Middle Initial: " + getEmployeeMiddleInitial());
		}
		if (getEmployeeLastName() != null){
			out.println("Last Name: " + getEmployeeLastName());
		}
		out.println("Expense Type: " + ExpenseTypeName);
		out.println("Payment Type Name: " + PaymentTypeName);
		out.println("Payment Type Code: " + PaymentTypeCode);
		out.println("Description: " + Description);
		if (TransactionDate != null) {
			strDate = sdf.format(TransactionDate);	 
			out.println("Date: " + strDate);
		}
		out.println("Amount: " + decimalFormat.format(OriginalAmount) + " " + OriginalCurrency);
		out.println("Merchant: " + MerchantName);
		out.println("Location: " + LocationDisplayName);
		out.println("City: " + getLocationCity());
		out.println("State: " + getLocationState());
		out.println("Country: " + getLocationCountry());
		out.println("EmployeeCountry: " + getEmployeeCountry());
		out.println("EmployeeState: " + getEmployeeState());
		out.println("TaxNexus: " + getTaxNexus());
		out.println("Exchange Rate: " + ExchangeRate);
		out.println("Posted Amount: " + decimalFormat.format(PostedAmount) + " " + PostedCurrency);
		out.println("Approved Amount: " + decimalFormat.format(ApprovedAmount) + " " + PostedCurrency);
		if (HasBillableItems != null) {
			out.println("Has Billable Items: " + HasBillableItems.toString());
		}
		if (HasItemizations != null) {
			out.println("Has Itemizations: " + HasItemizations.toString());
		}
		if (IsPersonal != null) {
			out.println("Is Personal: " + IsPersonal.toString());
		}
		if (IsPersonalCardCharge != null) {
			out.println("Is Personal Card Charge: " + IsPersonalCardCharge.toString());
		}
		if (HasVAT != null) {
			out.println("Has VAT: " + HasVAT.toString());
		}
		if (HasImage != null) {
			out.println("Has Image: " + HasImage.toString());
		}
		if (IsPaidByExpensePay != null) {
			out.println("Is Paid By ExpensePay: " + IsPaidByExpensePay.toString());
		}
		out.println("Approval Status: " + ApprovalStatus);
		out.println("Payment Status: " + PaymentStatus);

		if (PaidDate != null) {
			strDate = sdf.format(PaidDate);	 
			out.println("Paid: " + strDate);
		}
		if (LastModified != null) {
			strDate = sdf.format(LastModified);	 
			out.println("Last Modified: " + strDate);
		}

		out.println("CUSTOM FIELDS");
		if (Custom1 != null) {
			out.println("Custom 1----------------------------");
			Custom1.display();;
			out.println();
		}
		if (Custom2 != null) {
			out.println("Custom 2----------------------------");
			 Custom2.display();
			out.println();
		}
		if (Custom3 != null) {
			out.println("Custom 3----------------------------");
			 Custom3.display();
		}
		if (Custom4 != null) {
			out.println("Custom 4----------------------------");
			 Custom4.display();
		}
		if (Custom5 != null) {
			 Custom5.display();
		}
		if (Custom6 != null) {
			 Custom6.display();
		}
		if (Custom7 != null) {
			 Custom7.display();
		}
		if (Custom8 != null) {
			 Custom8.display();
		}
		if (Custom9 != null) {
			 Custom9.display();
		}
		if (Custom10 != null) {
			 Custom10.display();
		}
		if (Custom11 != null) {
			 Custom11.display();
		}
		if (Custom12 != null) {
			 Custom12.display();
		}
		if (Custom13 != null) {
			 Custom13.display();
		}
		if (Custom14 != null) {
			 Custom14.display();
		}
		if (Custom15 != null) {
			 Custom15.display();
		}
		if (Custom16 != null) {
			 Custom16.display();
		}
		if (Custom17 != null) {
			 Custom17.display();
		}
		if (Custom18 != null) {
			 Custom18.display();
		}
		if (Custom19 != null) {
			 Custom19.display();
		}
		if (Custom20 != null) {
			 Custom20.display();
		}
		if (Custom21 != null) {
			 Custom21.display();
		}
		if (Custom22 != null) {
			 Custom22.display();
		}
		if (Custom23 != null) {
			 Custom23.display();
		}
		if (Custom24 != null) {
			 Custom24.display();
		}
		if (Custom25 != null) {
			 Custom25.display();
		}
		if (Custom26 != null) {
			 Custom26.display();
		}
		if (Custom27 != null) {
			 Custom27.display();
		}
		if (Custom28 != null) {
			 Custom28.display();
		}
		if (Custom29 != null) {
			 Custom29.display();
		}
		if (Custom30 != null) {
			 Custom30.display();
		}
		if (Custom31 != null) {
			 Custom31.display();
		}
		if (Custom32 != null) {
			 Custom32.display();
		}
		if (Custom33 != null) {
			 Custom33.display();
		}
		if (Custom34 != null) {
			 Custom34.display();
		}
		if (Custom35 != null) {
			 Custom35.display();
		}
		if (Custom36 != null) {
			 Custom36.display();
		}
		if (Custom37 != null) {
			 Custom37.display();
		}
		if (Custom38 != null) {
			 Custom38.display();
		}
		if (Custom39 != null) {
			 Custom39.display();
		}
		if (Custom40 != null) {
			 Custom40.display();
		}
		out.println("___________________________________________");
		out.println();
		out.println("ORG UNIT FIELDS");
		if (OrgUnit1 != null) {
			out.println("Org Unit 1----------------------------");
			 OrgUnit1.display();
		}
		if (OrgUnit2 != null) {
			out.println("Org Unit 2----------------------------");
			 OrgUnit2.display();
		}
		if (OrgUnit3 != null) {
			out.println("Org Unit 3----------------------------");
			 OrgUnit3.display();
		}
		if (OrgUnit4 != null) {
			out.println("Org Unit 4----------------------------");
			 OrgUnit4.display();
		}
		if (OrgUnit5 != null) {
			out.println("Org Unit 5----------------------------");
			 OrgUnit5.display();
		}
		if (OrgUnit6 != null) {
			out.println("Org Unit 6----------------------------");
			 OrgUnit6.display();//	The details from the Org Unit fields. These may not have data, depending on configuration.
		}
		
		
		
		out.println("End Entry___________________________________________");
		out.println();
		if (Journey != null) {
			out.println("JOURNEY: ");
			Journey.display();
			out.println("End Journey___________________________________________");
			out.println();
		}
	
		if(Items != null) {
			out.println("ITEMIZATIONS");
			int count = Items.size();
			Itemization item = null;
			if (count >0 ) {
			 for (int index=0 ; index<count; index++) {
				item = Items.get(index);
				int itereation = index + 1;
				out.println("Line Item: " + itereation);
				out.println("-----------------------");
				item.display();
			 }
				//out.println("End Itemizations___________________________________________");
				out.println();
			}
		}
		
		if (NexusStatus != null){
			out.println("PIVOTNEXUS");
			out.println("PivotNexus Status: " + NexusStatus);
			if (CanonicalTransactionID != null){
				out.println("Canonical Transaction ID: " + CanonicalTransactionID);
			}
			if (ReportPayeeID != null){
				out.println("Report Payee ID: " + ReportPayeeID);
			}
			if (PayeeVendorID != null){
				out.println("Payee Vendor ID: " + PayeeVendorID);
			}
			if (PayeeAddressID != null){
				out.println("Payee Address ID: " + PayeeAddressID);
			}
			if (PayeePaymentMethod != null){
				out.println("Payee Payment Method: " + PayeePaymentMethod);
			}
			out.println("End PivotNexus");
			out.println();
		}
		if (PrimeStatus != null){
			out.println("PIVOTPRIME");
			out.println("PivotPrime Status: " + PrimeStatus);
			out.println("End PivotPrime");
			out.println();
		}
		out.println();
		out.println("END EXPENSE DISPLAY");
		out.println("______________________________________________________________________________________________________________");
		out.println();
		out.println();
		out.println();
	}
	
	public BasicDBObject getDocument () {//this method returns the expense as a BasicDBObject that can be added to a MongoDB
		BasicDBObject Doc = new BasicDBObject();
		BasicDBObject myExpense = new BasicDBObject("ID",this.ID);
		myExpense.append("Entry_ID",this.getEntry_ID());		
		myExpense.append("ReportID",this.getReportID());
		myExpense.append("ReportName",this.getReportName());
		myExpense.append("ReportPurpose",this.getReportPurpose());
		myExpense.append("LedgerName",this.getLedgerName());
		myExpense.append("PolicyID",this.getPolicyID());
		myExpense.append("ReportTotal",this.getReportTotal());
		myExpense.append("PersonalExpense",this.getPersonalExpense());
		myExpense.append("AmountDueEmployee",this.getAmountDueEmployee());
		myExpense.append("AmountDueCompanyCard",this.getAmountDueCompanyCard());
		myExpense.append("TotalApprovedAmount",this.getTotalApprovedAmount());
		myExpense.append("TripID",this.TripID);
		myExpense.append("ElectronicReceiptID",this.ElectronicReceiptID);
		myExpense.append("CompanyID",this.getCompanyID());
		myExpense.append("ReportOwnerID",this.ReportOwnerID);
		myExpense.append("EmployeeID",this.EmployeeID);
		myExpense.append("EmployeeDisplayName",this.getEmployeeDisplayName());
		myExpense.append("EmployeeFirstName",this.getEmployeeFirstName());
		myExpense.append("EmployeeMiddleInitial",this.getEmployeeMiddleInitial());
		myExpense.append("EmployeeLastName",this.getEmployeeLastName());
		myExpense.append("ExpenseTypeName", this.ExpenseTypeName);
		myExpense.append("PaymentTypeName", this.PaymentTypeName);
		myExpense.append("PaymentTypeCode", this.PaymentTypeCode);
		myExpense.append("Description", this.Description);
		myExpense.append("HasBillableItems",this.HasBillableItems);
		myExpense.append("HasItemizations",this.HasItemizations);
		myExpense.append("IsPersonal",this.IsPersonal);
		myExpense.append("IsPersonalCardCharge",this.IsPersonalCardCharge);
		myExpense.append("HasVAT",this.HasVAT);
		myExpense.append("HasImage",this.HasImage);
		myExpense.append("IsPaidByExpensePay",this.IsPaidByExpensePay);
		myExpense.append("LocationDisplayName", this.LocationDisplayName);
		myExpense.append("LocationCity", this.getLocationCity());
		myExpense.append("LocationState", this.getLocationState());
		myExpense.append("LocationCountry", this.getLocationCountry());
		myExpense.append("EmployeeCountry", this.getEmployeeCountry());
		myExpense.append("EmployeeState", this.getEmployeeState());
		myExpense.append("TaxNexus", this.getTaxNexus());
		myExpense.append("TransactionDate",this.TransactionDate);
		myExpense.append("OriginalAmount", this.OriginalAmount);
		myExpense.append("OriginalCurrency",this.OriginalCurrency);
		myExpense.append("ExchangeRate", this.ExchangeRate);
		myExpense.append("PostedAmount", this.PostedAmount);
		myExpense.append("PostedCurrency",this.PostedCurrency);
		myExpense.append("ApprovedAmount",this.ApprovedAmount);
		myExpense.append("MerchantName", this.MerchantName);
		myExpense.append("ApprovalStatus",this.ApprovalStatus);
		myExpense.append("PaymentStatus",this.PaymentStatus);
		if (PrimeStatus != null){
			myExpense.append("PrimeStatus",this.PrimeStatus);
		}
		if (CanonicalTransactionID != null){
			myExpense.append("CanonicalTransactionID",this.CanonicalTransactionID.toString());
		}
		if (NexusStatus != null){
			myExpense.append("NexusStatus",this.NexusStatus);
		}
		
		if (ReportPayeeID != null){
			myExpense.append("ReportPayeeID",this.ReportPayeeID);;
		}
		if (PayeeVendorID != null){
			myExpense.append("PayeeVendorID",this.PayeeVendorID);
		}
		if (PayeeAddressID != null){
			myExpense.append("PayeeAddressID",this.PayeeAddressID);
		}
		if (PayeePaymentMethod != null){
			myExpense.append("PayeePaymentMethod",this.PayeePaymentMethod);
		}
		myExpense.append("PaidDate",this.PaidDate);
		myExpense.append("LastModified",this.LastModified);
		
		
		if (Custom1 != null) {
			//Custom1.display();
			myExpense.append("Custom1Type",this.Custom1.Type);
			myExpense.append("Custom1Value",this.Custom1.Value);
			myExpense.append("Custom1Code",this.Custom1.Code);
			myExpense.append("Custom1ID",this.Custom1.ListItemID);
			
		}
		if (Custom2 != null) {
			//Custom2.display();
			myExpense.append("Custom2Type",this.Custom2.Type);
			myExpense.append("Custom2Value",this.Custom2.Value);
			myExpense.append("Custom2Code",this.Custom2.Code);
			myExpense.append("Custom2ID",this.Custom2.ListItemID);
			
		}
		if (Custom3 != null) {
			myExpense.append("Custom3Type",this.Custom3.Type);
			myExpense.append("Custom3Value",this.Custom3.Value);
			myExpense.append("Custom3Code",this.Custom3.Code);
			myExpense.append("Custom3ID",this.Custom3.ListItemID);
		}
		if (Custom4 != null) {
			myExpense.append("Custom4Type",this.Custom4.Type);
			myExpense.append("Custom4Value",this.Custom4.Value);
			myExpense.append("Custom4Code",this.Custom4.Code);
			myExpense.append("Custom4ID",this.Custom4.ListItemID);
		}
		if (Custom5 != null) {
			myExpense.append("Custom5Type",this.Custom5.Type);
			myExpense.append("Custom5Value",this.Custom5.Value);
			myExpense.append("Custom5Code",this.Custom5.Code);
			myExpense.append("Custom5ID",this.Custom5.ListItemID);
		}
		if (Custom6 != null) {
			myExpense.append("Custom6Type",this.Custom6.Type);
			myExpense.append("Custom6Value",this.Custom6.Value);
			myExpense.append("Custom6Code",this.Custom6.Code);
			myExpense.append("Custom6ID",this.Custom6.ListItemID);
		}
		if (Custom7 != null) {
			myExpense.append("Custom7Type",this.Custom7.Type);
			myExpense.append("Custom7Value",this.Custom7.Value);
			myExpense.append("Custom7Code",this.Custom7.Code);
			myExpense.append("Custom7ID",this.Custom7.ListItemID);
		}
		if (Custom8 != null) {
			myExpense.append("Custom8Type",this.Custom8.Type);
			myExpense.append("Custom8Value",this.Custom8.Value);
			myExpense.append("Custom8Code",this.Custom8.Code);
			myExpense.append("Custom8ID",this.Custom8.ListItemID);
		}
		if (Custom9 != null) {
			myExpense.append("Custom9Type",this.Custom9.Type);
			myExpense.append("Custom9Value",this.Custom9.Value);
			myExpense.append("Custom9Code",this.Custom9.Code);
			myExpense.append("Custom9ID",this.Custom9.ListItemID);
		}
		if (Custom10 != null) {
			myExpense.append("Custom10Type",this.Custom10.Type);
			myExpense.append("Custom10Value",this.Custom10.Value);
			myExpense.append("Custom10Code",this.Custom10.Code);
			myExpense.append("Custom10ID",this.Custom10.ListItemID);
		}
		if (Custom11 != null) {
			myExpense.append("Custom11Type",this.Custom11.Type);
			myExpense.append("Custom11Value",this.Custom11.Value);
			myExpense.append("Custom11Code",this.Custom11.Code);
			myExpense.append("Custom11ID",this.Custom11.ListItemID);
		}
		if (Custom12 != null) {
			myExpense.append("Custom12Type",this.Custom12.Type);
			myExpense.append("Custom12Value",this.Custom12.Value);
			myExpense.append("Custom12Code",this.Custom12.Code);
			myExpense.append("Custom12ID",this.Custom12.ListItemID);
		}
		if (Custom13 != null) {
			myExpense.append("Custom13Type",this.Custom13.Type);
			myExpense.append("Custom13Value",this.Custom13.Value);
			myExpense.append("Custom13Code",this.Custom13.Code);
			myExpense.append("Custom13ID",this.Custom13.ListItemID);
		}
		if (Custom14 != null) {
			myExpense.append("Custom14Type",this.Custom14.Type);
			myExpense.append("Custom14Value",this.Custom14.Value);
			myExpense.append("Custom14Code",this.Custom14.Code);
			myExpense.append("Custom14ID",this.Custom14.ListItemID);
		}
		if (Custom15 != null) {
			myExpense.append("Custom15Type",this.Custom15.Type);
			myExpense.append("Custom15Value",this.Custom15.Value);
			myExpense.append("Custom15Code",this.Custom15.Code);
			myExpense.append("Custom15ID",this.Custom15.ListItemID);
		}
		if (Custom16 != null) {
			myExpense.append("Custom16Type",this.Custom16.Type);
			myExpense.append("Custom16Value",this.Custom16.Value);
			myExpense.append("Custom16Code",this.Custom16.Code);
			myExpense.append("Custom16ID",this.Custom16.ListItemID);
		}
		if (Custom17 != null) {
			myExpense.append("Custom17Type",this.Custom17.Type);
			myExpense.append("Custom17Value",this.Custom17.Value);
			myExpense.append("Custom17Code",this.Custom17.Code);
			myExpense.append("Custom17ID",this.Custom17.ListItemID);
		}
		if (Custom18 != null) {
			myExpense.append("Custom18Type",this.Custom18.Type);
			myExpense.append("Custom18Value",this.Custom18.Value);
			myExpense.append("Custom18Code",this.Custom18.Code);
			myExpense.append("Custom18ID",this.Custom18.ListItemID);
		}
		if (Custom19 != null) {
			myExpense.append("Custom19Type",this.Custom19.Type);
			myExpense.append("Custom19Value",this.Custom19.Value);
			myExpense.append("Custom19Code",this.Custom19.Code);
			myExpense.append("Custom19ID",this.Custom19.ListItemID);
		}
		if (Custom10 != null) {
			myExpense.append("Custom10Type",this.Custom10.Type);
			myExpense.append("Custom10Value",this.Custom10.Value);
			myExpense.append("Custom10Code",this.Custom10.Code);
			myExpense.append("Custom10ID",this.Custom10.ListItemID);
		}
		if (Custom20 != null) {
			myExpense.append("Custom20Type",this.Custom20.Type);
			myExpense.append("Custom20Value",this.Custom20.Value);
			myExpense.append("Custom20Code",this.Custom20.Code);
			myExpense.append("Custom20ID",this.Custom20.ListItemID);
		}
		if (Custom21 != null) {
			myExpense.append("Custom21Type",this.Custom21.Type);
			myExpense.append("Custom21Value",this.Custom21.Value);
			myExpense.append("Custom21Code",this.Custom21.Code);
			myExpense.append("Custom21ID",this.Custom21.ListItemID);
		}
		if (Custom22 != null) {
			myExpense.append("Custom22Type",this.Custom22.Type);
			myExpense.append("Custom22Value",this.Custom22.Value);
			myExpense.append("Custom22Code",this.Custom22.Code);
			myExpense.append("Custom22ID",this.Custom22.ListItemID);
		}
		if (Custom23 != null) {
			myExpense.append("Custom23Type",this.Custom23.Type);
			myExpense.append("Custom23Value",this.Custom23.Value);
			myExpense.append("Custom23Code",this.Custom23.Code);
			myExpense.append("Custom23ID",this.Custom23.ListItemID);
		}
		if (Custom24 != null) {
			myExpense.append("Custom24Type",this.Custom24.Type);
			myExpense.append("Custom24Value",this.Custom24.Value);
			myExpense.append("Custom24Code",this.Custom24.Code);
			myExpense.append("Custom24ID",this.Custom24.ListItemID);
		}
		if (Custom25 != null) {
			myExpense.append("Custom25Type",this.Custom25.Type);
			myExpense.append("Custom25Value",this.Custom25.Value);
			myExpense.append("Custom25Code",this.Custom25.Code);
			myExpense.append("Custom25ID",this.Custom25.ListItemID);
		}
		if (Custom26 != null) {
			myExpense.append("Custom26Type",this.Custom26.Type);
			myExpense.append("Custom26Value",this.Custom26.Value);
			myExpense.append("Custom26Code",this.Custom26.Code);
			myExpense.append("Custom26ID",this.Custom26.ListItemID);
		}
		if (Custom27 != null) {
			myExpense.append("Custom27Type",this.Custom27.Type);
			myExpense.append("Custom27Value",this.Custom27.Value);
			myExpense.append("Custom27Code",this.Custom27.Code);
			myExpense.append("Custom27ID",this.Custom27.ListItemID);
		}
		if (Custom28 != null) {
			myExpense.append("Custom28Type",this.Custom28.Type);
			myExpense.append("Custom28Value",this.Custom28.Value);
			myExpense.append("Custom28Code",this.Custom28.Code);
			myExpense.append("Custom28ID",this.Custom28.ListItemID);
		}
		if (Custom29 != null) {
			myExpense.append("Custom29Type",this.Custom29.Type);
			myExpense.append("Custom29Value",this.Custom29.Value);
			myExpense.append("Custom29Code",this.Custom29.Code);
			myExpense.append("Custom29ID",this.Custom29.ListItemID);
		}
		if (Custom30 != null) {
			myExpense.append("Custom30Type",this.Custom30.Type);
			myExpense.append("Custom30Value",this.Custom30.Value);
			myExpense.append("Custom30Code",this.Custom30.Code);
			myExpense.append("Custom30ID",this.Custom30.ListItemID);
		}
		if (Custom31 != null) {
			myExpense.append("Custom31Type",this.Custom31.Type);
			myExpense.append("Custom31Value",this.Custom31.Value);
			myExpense.append("Custom31Code",this.Custom31.Code);
			myExpense.append("Custom31ID",this.Custom31.ListItemID);
		}
		if (Custom32 != null) {
			myExpense.append("Custom32Type",this.Custom32.Type);
			myExpense.append("Custom32Value",this.Custom32.Value);
			myExpense.append("Custom32Code",this.Custom32.Code);
			myExpense.append("Custom32ID",this.Custom32.ListItemID);
		}
		if (Custom33 != null) {
			myExpense.append("Custom33Type",this.Custom33.Type);
			myExpense.append("Custom33Value",this.Custom33.Value);
			myExpense.append("Custom33Code",this.Custom33.Code);
			myExpense.append("Custom33ID",this.Custom33.ListItemID);
		}
		if (Custom34 != null) {
			myExpense.append("Custom34Type",this.Custom34.Type);
			myExpense.append("Custom34Value",this.Custom34.Value);
			myExpense.append("Custom34Code",this.Custom34.Code);
			myExpense.append("Custom34ID",this.Custom34.ListItemID);
		}
		if (Custom35 != null) {
			myExpense.append("Custom35Type",this.Custom35.Type);
			myExpense.append("Custom35Value",this.Custom35.Value);
			myExpense.append("Custom35Code",this.Custom35.Code);
			myExpense.append("Custom35ID",this.Custom35.ListItemID);
		}
		if (Custom36 != null) {
			myExpense.append("Custom36Type",this.Custom36.Type);
			myExpense.append("Custom36Value",this.Custom36.Value);
			myExpense.append("Custom36Code",this.Custom36.Code);
			myExpense.append("Custom36ID",this.Custom36.ListItemID);
		}
		if (Custom37 != null) {
			myExpense.append("Custom37Type",this.Custom37.Type);
			myExpense.append("Custom37Value",this.Custom37.Value);
			myExpense.append("Custom37Code",this.Custom37.Code);
			myExpense.append("Custom37ID",this.Custom37.ListItemID);
		}
		if (Custom38 != null) {
			myExpense.append("Custom38Type",this.Custom38.Type);
			myExpense.append("Custom38Value",this.Custom38.Value);
			myExpense.append("Custom38Code",this.Custom38.Code);
			myExpense.append("Custom38ID",this.Custom38.ListItemID);
		}
		if (Custom39 != null) {
			myExpense.append("Custom39Type",this.Custom39.Type);
			myExpense.append("Custom39Value",this.Custom39.Value);
			myExpense.append("Custom39Code",this.Custom39.Code);
			myExpense.append("Custom39ID",this.Custom39.ListItemID);
		}
		if (Custom40 != null) {
			myExpense.append("Custom40Type",this.Custom40.Type);
			myExpense.append("Custom40Value",this.Custom40.Value);
			myExpense.append("Custom40Code",this.Custom40.Code);
			myExpense.append("Custom40ID",this.Custom40.ListItemID);
		}
		if (OrgUnit1 != null) {
			myExpense.append("OrgUnit1Type",this.OrgUnit1.Type);
			myExpense.append("OrgUnit1Value",this.OrgUnit1.Value);
			myExpense.append("OrgUnit1Code",this.OrgUnit1.Code);
			myExpense.append("OrgUnit1ID",this.OrgUnit1.ListItemID);
		}
		if (OrgUnit2 != null) {
			myExpense.append("OrgUnit2Type",this.OrgUnit2.Type);
			myExpense.append("OrgUnit2Value",this.OrgUnit2.Value);
			myExpense.append("OrgUnit2Code",this.OrgUnit2.Code);
			myExpense.append("OrgUnit2ID",this.OrgUnit2.ListItemID);
		}
		if (OrgUnit3!= null) {
			myExpense.append("OrgUnit3Type",this.OrgUnit3.Type);
			myExpense.append("OrgUnit3Value",this.OrgUnit3.Value);
			myExpense.append("OrgUnit3Code",this.OrgUnit3.Code);
			myExpense.append("OrgUnit3ID",this.OrgUnit3.ListItemID);
		}
		if (OrgUnit4 != null) {
			myExpense.append("OrgUnit4Type",this.OrgUnit4.Type);
			myExpense.append("OrgUnit4Value",this.OrgUnit4.Value);
			myExpense.append("OrgUnit4Code",this.OrgUnit4.Code);
			myExpense.append("OrgUnit4ID",this.OrgUnit4.ListItemID);
		}
		if (OrgUnit5 != null) {
			myExpense.append("OrgUnit5Type",this.OrgUnit5.Type);
			myExpense.append("OrgUnit5Value",this.OrgUnit5.Value);
			myExpense.append("OrgUnit5Code",this.OrgUnit5.Code);
			myExpense.append("OrgUnit5ID",this.OrgUnit5.ListItemID);
		}
		if (OrgUnit6 != null) {
			myExpense.append("OrgUnit6Type",this.OrgUnit6.Type);
			myExpense.append("OrgUnit6Value",this.OrgUnit6.Value);
			myExpense.append("OrgUnit6Code",this.OrgUnit6.Code);
			myExpense.append("OrgUnit6ID",this.OrgUnit6.ListItemID);
		}
		
		if(Items != null) {
			int count = Items.size();
			Itemization item = null;
			if (count >0 ) {
				BasicDBList itemizations = new BasicDBList();
				
			 for (int index=0 ; index<count; index++) {
				item = Items.get(index);
				item.setEntryID(this.getID());
				item.setTransactionCurrencyCode(this.getOriginalCurrency());
				item.setPostedCurrency(this.getPostedCurrency());
				Doc = new BasicDBObject();
				Doc = item.getDocument();
				itemizations.add(Doc);
			 }
			 myExpense.append("Itemizations", itemizations);

			}
		}
		
		if(Journey != null) {
			Doc = Journey.getDocument();
			myExpense.append("Journey", Doc);
		}

		return myExpense;
	}
	
	public Expense doctoExpense (DBObject doc) {
		// converts an Expense document into an Expense object
		// It requires the expense's itemizations to be stored as Itemization documents in the MongoDB
		Expense myExpense = new Expense();
		String amt;//placeholder for an amount represented as a String
		
		// These are system required fields in Concur Expense.  A valid Expense document must NOT be null for these fields.
		// If any of these fields are null, it will cause an exception that will be caught

		try {
			String ID = doc.get("ID").toString();
			myExpense.setID(ID);
			String ExpenseTypeName = doc.get("ExpenseTypeName").toString();
			myExpense.setExpenseTypeName(ExpenseTypeName);
			String PaymentTypeName = doc.get("PaymentTypeName").toString();
			myExpense.setPaymentTypeName(PaymentTypeName);
			String PaymentTypeCode = doc.get("PaymentTypeCode").toString();
			myExpense.setPaymentTypeCode(PaymentTypeCode);
			Date TransactionDate = (Date)doc.get("TransactionDate");
			myExpense.setTransactionDate(TransactionDate);
			amt = doc.get("OriginalAmount").toString();
			double OriginalAmount = Double.parseDouble(amt);
			myExpense.setOriginalAmount(OriginalAmount);
			String OriginalCurrency = doc.get("OriginalCurrency").toString();
			myExpense.setOriginalCurrency(OriginalCurrency);
			amt = doc.get("PostedAmount").toString();
			double PostedAmount = Double.parseDouble(amt);
			myExpense.setPostedAmount(PostedAmount);
			String PostedCurrency = doc.get("PostedCurrency").toString();
			myExpense.setPostedCurrency(PostedCurrency);
			String ApprovalStatus = doc.get("ApprovalStatus").toString();
			myExpense.setApprovalStatus(ApprovalStatus);
			String PaymentStatus = doc.get("PaymentStatus").toString();
			myExpense.setPaymentStatus(PaymentStatus);
		
		// These are fields are NOT system required fields in Concur Expense that the Expense constructor requires to create a new Expense object.
		// When these fields are null a default value will be used
			String CompanyID ="";
			if (doc.get("CompanyID")!=null) {
				CompanyID = doc.get("CompanyID").toString();
				myExpense.setCompanyID(CompanyID);
			}

			String EmployeeID = "";
			if (doc.get("EmployeeID") !=null) {
				EmployeeID = doc.get("EmployeeID").toString();
				myExpense.setEmployeeID(EmployeeID);
			}
			
			double ApprovedAmount = 0.0;// initialize default approved amount
			if (doc.get("ApprovedAmount")!=null) {
				amt= doc.get("ApprovedAmount").toString();
				ApprovedAmount = Double.parseDouble(amt);
				myExpense.setApprovedAmount(ApprovedAmount);
			}
			Boolean HasBillableItems = false;// initialize default HasBillableItems
			if (doc.get("HasBillableItems")!=null) {
				HasBillableItems = (Boolean)(doc.get("HasBillableItems"));
				myExpense.setHasBillableItems(HasBillableItems);
			}
			String LocationDisplayName = "Not specified";// initialize default location display name
			if (doc.get("LocationDisplayName")!=null) {
				LocationDisplayName = doc.get("LocationDisplayName").toString();
				myExpense.setLocationDisplayName(LocationDisplayName);
			}
			String MerchantName = "Not specified";// initialize default merchant name
			if (doc.get("MerchantName")!=null) {
				MerchantName = doc.get("MerchantName").toString();
				myExpense.setMerchantName(MerchantName);
			}
			Date PaidDate = null;// initialize the default paid date
			if (doc.get("MerchantName")!=null) {
				PaidDate = (Date)doc.get("PaidDate");
				myExpense.setPaidDate(PaidDate);
			}

			} catch (Exception e) {// then it was unable to create an expense
				out.println("Exception: " + e.getMessage());
				return myExpense;
			}// try block
		
		if (doc.get("ReportName")!=null) {
			String ReportName = doc.get("ReportName").toString();
			myExpense.setReportName(ReportName);
		}
		if (doc.get("ReportPurpose") !=null) {
			String ReportPurpose = doc.get("ReportPurpose").toString();
			myExpense.setReportPurpose(ReportPurpose);
		}
		if (doc.get("LedgerName")!=null) {
			String LedgerName= doc.get("LedgerName").toString();
			myExpense.setLedgerName(LedgerName);
		}
		if (doc.get("PolicyID")!=null) {
			String PolicyID= doc.get("PolicyID").toString();
			myExpense.setPolicyID(PolicyID);
		}
		if (doc.get("EmployeeCountry")!=null) {
			String EmployeeCountry= doc.get("EmployeeCountry").toString();
			myExpense.setEmployeeCountry(EmployeeCountry);
		}
		if (doc.get("EmployeeState")!=null) {
			String EmployeeState= doc.get("EmployeeState").toString();
			myExpense.setEmployeeState(EmployeeState);
		}
		if (doc.get("TaxNexus")!=null) {
			String TaxNexus= doc.get("TaxNexus").toString();
			myExpense.setTaxNexus(TaxNexus);
		}
		if (doc.get("ReportTotal")!=null) {
			amt = doc.get("ReportTotal").toString();
			double ReportTotal= Double.parseDouble(amt);
			myExpense.setReportTotal(ReportTotal);
		}
		if (doc.get("PersonalExpense")!=null) {
			amt = doc.get("PersonalExpense").toString();
			double PersonalExpense= Double.parseDouble(amt);
			myExpense.setPersonalExpense(PersonalExpense);
		}
		if (doc.get("AmountDueEmployee")!=null) {
			amt = doc.get("AmountDueEmployee").toString();
			double AmountDueEmployee= Double.parseDouble(amt);
			myExpense.setAmountDueEmployee(AmountDueEmployee);
		}
		if (doc.get("AmountDueCompanyCard")!=null) {
			amt = doc.get("AmountDueCompanyCard").toString();
			double AmountDueCompanyCard= Double.parseDouble(amt);
			myExpense.setAmountDueCompanyCard(AmountDueCompanyCard);
		}
		if (doc.get("TotalApprovedAmount")!=null) {
			amt = doc.get("TotalApprovedAmount").toString();
			double TotalApprovedAmount= Double.parseDouble(amt);
			myExpense.setTotalApprovedAmount(TotalApprovedAmount);
		}
		
		// These are non-system required fields in Concur Expense that aren't required by the Expense object constructor.
		double ExchangeRate = 1.0;// initialize default exchange rate
		if (doc.get("ExchangeRate")!=null) {
			amt = doc.get("ExchangeRate").toString();
			ExchangeRate = Double.parseDouble(amt);
			myExpense.setExchangeRate(ExchangeRate);
		}
		if (doc.get("EntryID")!=null) {
			String EntryID = doc.get("EntryID").toString();
			myExpense.setEntry_ID(EntryID);
		}
		if (doc.get("Entry_ID")!=null) {
			String Entry_ID = doc.get("Entry_ID").toString();
			myExpense.setEntry_ID(Entry_ID);
		}
		if (doc.get("ReportID")!=null) {
			String ReportID = doc.get("ReportID").toString();
			myExpense.setReportID(ReportID);
		}
		if (doc.get("TripID")!=null) {
			String TripID = doc.get("TripID").toString();
			myExpense.setTripID(TripID);
		}
		if (doc.get("ElectronicReceiptID")!=null) {
			String ElectronicReceiptID = doc.get("ElectronicReceiptID").toString();
			myExpense.setElectronicReceiptID(ElectronicReceiptID);
		}
		if (doc.get("ReportOwnerID")!=null) {
			String ReportOwnerID = doc.get("ReportOwnerID").toString();
			myExpense.setReportOwnerID(ReportOwnerID);
		}
		if (doc.get("EmployeeDisplayName")!=null) {
			String EmployeeDisplayName = doc.get("EmployeeDisplayName").toString();
			myExpense.setEmployeeDisplayName(EmployeeDisplayName);
		}
		if (doc.get("EmployeeFirstName")!=null) {
			String EmployeeFirstName = doc.get("EmployeeFirstName").toString();
			myExpense.setEmployeeFirstName(EmployeeFirstName);
		}
		if (doc.get("EmployeeMiddleInitial")!=null) {
			String middle = doc.get("EmployeeMiddleInitial").toString();
			myExpense.setEmployeeMiddleInitial(middle);
		}
		if (doc.get("EmployeeLastName")!=null) {
			String EmployeeLastName = doc.get("EmployeeLastName").toString();
			myExpense.setEmployeeLastName(EmployeeLastName);
		}
		if (doc.get("LocationCity")!=null) {
			String LocationCity = doc.get("LocationCity").toString();
			myExpense.setLocationCity(LocationCity);
		}
		if (doc.get("LocationState")!=null) {
			String LocationState = doc.get("LocationState").toString();
			myExpense.setLocationState(LocationState);
		}
		if (doc.get("LocationCountry")!=null) {
			String LocationCountry = doc.get("LocationCountry").toString();
			myExpense.setLocationCountry(LocationCountry);
		}
		if (doc.get("Description")!=null) {
			String Description = doc.get("Description").toString();
			myExpense.setDescription(Description);
		}

		if (doc.get("HasItemizations")!=null) {
			Boolean HasItemizations = (Boolean)(doc.get("HasItemizations"));
			myExpense.setHasItemizations(HasItemizations);
		}
		if (doc.get("IsPersonal")!=null) {
			Boolean IsPersonal = (Boolean)(doc.get("IsPersonal"));
			myExpense.setIsPersonal(IsPersonal);

		}
		if (doc.get("IsPersonalCardCharge")!=null) {
			Boolean IsPersonalCardCharge = (Boolean)(doc.get("IsPersonalCardCharge"));
			myExpense.setIsPersonalCardCharge(IsPersonalCardCharge);
		}
		if (doc.get("HasVAT")!=null) {
			Boolean HasVAT = (Boolean)(doc.get("HasVAT"));
			myExpense.setHasVAT(HasVAT);
		}
		if (doc.get("HasImage")!=null) {
			Boolean HasImage = (Boolean)(doc.get("HasImage"));
			myExpense.setHasImage(HasImage);
		}

		if (doc.get("LastModified")!=null) {
			Date LastModified = (Date)doc.get("LastModified");
			myExpense.setLastModified(LastModified);
		}

		if (doc.get("Custom1Type")!=null) {
			CustomField Custom1 = new CustomField(doc.get("Custom1Type").toString(), doc.get("Custom1Value").toString());
			if ( (doc.get("Custom1Type").toString().equals("List")) || (doc.get("Custom1Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom1.setCode(doc.get("Custom1Code").toString());
				Custom1.setListItemID(doc.get("Custom1ID").toString());
			}
			myExpense.setCustom1(Custom1);
		}
		if (doc.get("Custom2Type")!=null) {
			CustomField Custom2 = new CustomField(doc.get("Custom2Type").toString(), doc.get("Custom2Value").toString());
			if ( (doc.get("Custom2Type").toString().equals("List")) || (doc.get("Custom2Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom2.setCode(doc.get("Custom2Code").toString());
				Custom2.setListItemID(doc.get("Custom2ID").toString());
			}
			myExpense.setCustom2(Custom2);
		}
		if (doc.get("Custom3Type")!=null) {
			CustomField Custom3 = new CustomField(doc.get("Custom3Type").toString(), doc.get("Custom3Value").toString());
			if ( (doc.get("Custom3Type").toString().equals("List")) || (doc.get("Custom3Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom3.setCode(doc.get("Custom3Code").toString());
				Custom3.setListItemID(doc.get("Custom3ID").toString());
			}
			myExpense.setCustom3(Custom3);
		}
		if (doc.get("Custom4Type")!=null) {
			CustomField Custom4 = new CustomField(doc.get("Custom4Type").toString(), doc.get("Custom4Value").toString());
			if ( (doc.get("Custom4Type").toString().equals("List")) || (doc.get("Custom4Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom4.setCode(doc.get("Custom4Code").toString());
				Custom4.setListItemID(doc.get("Custom4ID").toString());
			}
			myExpense.setCustom4(Custom4);
		}
		
		
		if (doc.get("Custom5Type")!=null) {
			CustomField Custom5 = new CustomField(doc.get("Custom5Type").toString(), doc.get("Custom5Value").toString());
			if ( (doc.get("Custom5Type").toString().equals("List")) || (doc.get("Custom5Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom5.setCode(doc.get("Custom5Code").toString());
				Custom5.setListItemID(doc.get("Custom5ID").toString());
			}
			myExpense.setCustom5(Custom5);
		}

		if (doc.get("Custom6Type")!=null) {
			CustomField Custom6 = new CustomField(doc.get("Custom6Type").toString(), doc.get("Custom6Value").toString());
			if ( (doc.get("Custom6Type").toString().equals("List")) || (doc.get("Custom6Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom6.setCode(doc.get("Custom6Code").toString());
				Custom6.setListItemID(doc.get("Custom6ID").toString());
			}
			myExpense.setCustom6(Custom6);
		}

		if (doc.get("Custom7Type")!=null) {
			CustomField Custom7 = new CustomField(doc.get("Custom7Type").toString(), doc.get("Custom7Value").toString());
			if ( (doc.get("Custom7Type").toString().equals("List")) || (doc.get("Custom7Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom7.setCode(doc.get("Custom7Code").toString());
				Custom7.setListItemID(doc.get("Custom7ID").toString());
			}
			myExpense.setCustom7(Custom7);
		}

		if (doc.get("Custom8Type")!=null) {
			CustomField Custom8 = new CustomField(doc.get("Custom8Type").toString(), doc.get("Custom8Value").toString());
			if ( (doc.get("Custom8Type").toString().equals("List")) || (doc.get("Custom8Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom8.setCode(doc.get("Custom8Code").toString());
				Custom8.setListItemID(doc.get("Custom8ID").toString());
			}
			myExpense.setCustom8(Custom8);
		}

		if (doc.get("Custom9Type")!=null) {
			CustomField Custom9 = new CustomField(doc.get("Custom9Type").toString(), doc.get("Custom9Value").toString());
			if ( (doc.get("Custom9Type").toString().equals("List")) || (doc.get("Custom9Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom9.setCode(doc.get("Custom9Code").toString());
				Custom9.setListItemID(doc.get("Custom9ID").toString());
			}
			myExpense.setCustom9(Custom9);
		}

		if (doc.get("Custom10Type")!=null) {
			CustomField Custom10 = new CustomField(doc.get("Custom10Type").toString(), doc.get("Custom10Value").toString());
			if ( (doc.get("Custom10Type").toString().equals("List")) || (doc.get("Custom10Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom10.setCode(doc.get("Custom10Code").toString());
				Custom10.setListItemID(doc.get("Custom10ID").toString());
			}
			myExpense.setCustom10(Custom10);
		}

		if (doc.get("Custom11Type")!=null) {
			CustomField Custom11 = new CustomField(doc.get("Custom11Type").toString(), doc.get("Custom11Value").toString());
			if ( (doc.get("Custom11Type").toString().equals("List")) || (doc.get("Custom11Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom11.setCode(doc.get("Custom11Code").toString());
				Custom11.setListItemID(doc.get("Custom11ID").toString());
				myExpense.setCustom11(Custom11);
			}
		}
			
		if (doc.get("Custom12Type")!=null) {
			CustomField Custom12 = new CustomField(doc.get("Custom12Type").toString(), doc.get("Custom12Value").toString());
			if ( (doc.get("Custom12Type").toString().equals("List")) || (doc.get("Custom12Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom12.setCode(doc.get("Custom12Code").toString());
				Custom12.setListItemID(doc.get("Custom12ID").toString());
			}
			myExpense.setCustom12(Custom12);
		}

		if (doc.get("Custom13Type")!=null) {
			CustomField Custom13 = new CustomField(doc.get("Custom13Type").toString(), doc.get("Custom13Value").toString());
			if ( (doc.get("Custom13Type").toString().equals("List")) || (doc.get("Custom13Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom13.setCode(doc.get("Custom13Code").toString());
				Custom13.setListItemID(doc.get("Custom13ID").toString());
			}
			myExpense.setCustom13(Custom13);
		}

		if (doc.get("Custom14Type")!=null) {
			CustomField Custom14 = new CustomField(doc.get("Custom14Type").toString(), doc.get("Custom14Value").toString());
			if ( (doc.get("Custom14Type").toString().equals("List")) || (doc.get("Custom14Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom14.setCode(doc.get("Custom14Code").toString());
				Custom14.setListItemID(doc.get("Custom14ID").toString());
			}
			myExpense.setCustom14(Custom14);
		}

		if (doc.get("Custom15Type")!=null) {
			CustomField Custom15 = new CustomField(doc.get("Custom15Type").toString(), doc.get("Custom15Value").toString());
			if ( (doc.get("Custom15Type").toString().equals("List")) || (doc.get("Custom15Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom15.setCode(doc.get("Custom15Code").toString());
				Custom15.setListItemID(doc.get("Custom15ID").toString());
			}
			myExpense.setCustom15(Custom15);
		}

		if (doc.get("Custom16Type")!=null) {
			CustomField Custom16 = new CustomField(doc.get("Custom16Type").toString(), doc.get("Custom16Value").toString());
			if ( (doc.get("Custom16Type").toString().equals("List")) || (doc.get("Custom16Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom16.setCode(doc.get("Custom16Code").toString());
				Custom16.setListItemID(doc.get("Custom16ID").toString());
			}
			myExpense.setCustom16(Custom16);
		}

		if (doc.get("Custom17Type")!=null) {
			CustomField Custom17 = new CustomField(doc.get("Custom17Type").toString(), doc.get("Custom17Value").toString());
			if ( (doc.get("Custom17Type").toString().equals("List")) || (doc.get("Custom17Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom17.setCode(doc.get("Custom17Code").toString());
				Custom17.setListItemID(doc.get("Custom17ID").toString());
			}
			myExpense.setCustom17(Custom17);
		}

		if (doc.get("Custom18Type")!=null) {
			CustomField Custom18 = new CustomField(doc.get("Custom18Type").toString(), doc.get("Custom18Value").toString());
			if ( (doc.get("Custom18Type").toString().equals("List")) || (doc.get("Custom18Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom18.setCode(doc.get("Custom18Code").toString());
				Custom18.setListItemID(doc.get("Custom18ID").toString());
			}
			myExpense.setCustom18(Custom18);
		}

		if (doc.get("Custom19Type")!=null) {
			CustomField Custom19 = new CustomField(doc.get("Custom19Type").toString(), doc.get("Custom19Value").toString());
			if ( (doc.get("Custom19Type").toString().equals("List")) || (doc.get("Custom19Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom19.setCode(doc.get("Custom19Code").toString());
				Custom19.setListItemID(doc.get("Custom19ID").toString());
			}
			myExpense.setCustom19(Custom19);
		}

		if (doc.get("Custom20Type")!=null) {
			CustomField Custom20 = new CustomField(doc.get("Custom20Type").toString(), doc.get("Custom20Value").toString());
			if ( (doc.get("Custom20Type").toString().equals("List")) || (doc.get("Custom20Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom20.setCode(doc.get("Custom20Code").toString());
				Custom20.setListItemID(doc.get("Custom20ID").toString());
			}
		}

		if (doc.get("Custom21Type")!=null) {
			CustomField Custom21 = new CustomField(doc.get("Custom21Type").toString(), doc.get("Custom21Value").toString());
			if ( (doc.get("Custom21Type").toString().equals("List")) || (doc.get("Custom21Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom21.setCode(doc.get("Custom21Code").toString());
				Custom21.setListItemID(doc.get("Custom21ID").toString());
			}
		}

		if (doc.get("Custom22Type")!=null) {
			CustomField Custom22 = new CustomField(doc.get("Custom22Type").toString(), doc.get("Custom22Value").toString());
			if ( (doc.get("Custom22Type").toString().equals("List")) || (doc.get("Custom22Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom22.setCode(doc.get("Custom22Code").toString());
				Custom22.setListItemID(doc.get("Custom22ID").toString());
			}
			myExpense.setCustom22(Custom22);
		}

		if (doc.get("Custom23Type")!=null) {
			CustomField Custom23 = new CustomField(doc.get("Custom23Type").toString(), doc.get("Custom23Value").toString());
			if ( (doc.get("Custom23Type").toString().equals("List")) || (doc.get("Custom23Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom23.setCode(doc.get("Custom23Code").toString());
				Custom23.setListItemID(doc.get("Custom23ID").toString());
			}
			myExpense.setCustom23(Custom23);
		}

		if (doc.get("Custom24Type")!=null) {
			CustomField Custom24 = new CustomField(doc.get("Custom24Type").toString(), doc.get("Custom24Value").toString());
			if ( (doc.get("Custom24Type").toString().equals("List")) || (doc.get("Custom24Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom24.setCode(doc.get("Custom24Code").toString());
				Custom24.setListItemID(doc.get("Custom24ID").toString());
			}
			myExpense.setCustom24(Custom24);
		}

		if (doc.get("Custom25Type")!=null) {
			CustomField Custom25 = new CustomField(doc.get("Custom25Type").toString(), doc.get("Custom25Value").toString());
			if ( (doc.get("Custom25Type").toString().equals("List")) || (doc.get("Custom25Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom25.setCode(doc.get("Custom25Code").toString());
				Custom25.setListItemID(doc.get("Custom25ID").toString());
			}
			myExpense.setCustom25(Custom25);
		}

		if (doc.get("Custom26Type")!=null) {
			CustomField Custom26 = new CustomField(doc.get("Custom26Type").toString(), doc.get("Custom26Value").toString());
			if ( (doc.get("Custom26Type").toString().equals("List")) || (doc.get("Custom26Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom26.setCode(doc.get("Custom26Code").toString());
				Custom26.setListItemID(doc.get("Custom26ID").toString());
			}
			myExpense.setCustom26(Custom26);
		}

		if (doc.get("Custom27Type")!=null) {
			CustomField Custom27 = new CustomField(doc.get("Custom27Type").toString(), doc.get("Custom27Value").toString());
			if ( (doc.get("Custom27Type").toString().equals("List")) || (doc.get("Custom27Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom27.setCode(doc.get("Custom27Code").toString());
				Custom27.setListItemID(doc.get("Custom27ID").toString());
			}
			myExpense.setCustom27(Custom27);
		}

		if (doc.get("Custom28Type")!=null) {
			CustomField Custom28 = new CustomField(doc.get("Custom28Type").toString(), doc.get("Custom28Value").toString());
			if ( (doc.get("Custom28Type").toString().equals("List")) || (doc.get("Custom28Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom28.setCode(doc.get("Custom28Code").toString());
				Custom28.setListItemID(doc.get("Custom28ID").toString());
			}
			myExpense.setCustom28(Custom28);
		}

		if (doc.get("Custom29Type")!=null) {
			CustomField Custom29 = new CustomField(doc.get("Custom29Type").toString(), doc.get("Custom29Value").toString());
			if ( (doc.get("Custom29Type").toString().equals("List")) || (doc.get("Custom29Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom29.setCode(doc.get("Custom29Code").toString());
				Custom29.setListItemID(doc.get("Custom29ID").toString());
			}
			myExpense.setCustom29(Custom29);
		}

		if (doc.get("Custom30Type")!=null) {
			CustomField Custom30 = new CustomField(doc.get("Custom30Type").toString(), doc.get("Custom30Value").toString());
			if ( (doc.get("Custom30Type").toString().equals("List")) || (doc.get("Custom30Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom30.setCode(doc.get("Custom30Code").toString());
				Custom30.setListItemID(doc.get("Custom30ID").toString());
			}
			myExpense.setCustom30(Custom30);
		}

		if (doc.get("Custom31Type")!=null) {
			CustomField Custom31 = new CustomField(doc.get("Custom31Type").toString(), doc.get("Custom31Value").toString());
			if ( (doc.get("Custom31Type").toString().equals("List")) || (doc.get("Custom31Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom31.setCode(doc.get("Custom31Code").toString());
				Custom31.setListItemID(doc.get("Custom31ID").toString());
			}
			myExpense.setCustom31(Custom31);
		}

		if (doc.get("Custom32Type")!=null) {
			CustomField Custom32 = new CustomField(doc.get("Custom32Type").toString(), doc.get("Custom32Value").toString());
			if ( (doc.get("Custom32Type").toString().equals("List")) || (doc.get("Custom32Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom32.setCode(doc.get("Custom32Code").toString());
				Custom32.setListItemID(doc.get("Custom32ID").toString());
			}
			myExpense.setCustom32(Custom32);
		}

		if (doc.get("Custom33Type")!=null) {
			CustomField Custom33 = new CustomField(doc.get("Custom33Type").toString(), doc.get("Custom33Value").toString());
			if ( (doc.get("Custom33Type").toString().equals("List")) || (doc.get("Custom33Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom33.setCode(doc.get("Custom33Code").toString());
				Custom33.setListItemID(doc.get("Custom33ID").toString());
			}
			myExpense.setCustom33(Custom33);
		}

		if (doc.get("Custom34Type")!=null) {
			CustomField Custom34 = new CustomField(doc.get("Custom34Type").toString(), doc.get("Custom34Value").toString());
			if ( (doc.get("Custom34Type").toString().equals("List")) || (doc.get("Custom34Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom34.setCode(doc.get("Custom34Code").toString());
				Custom34.setListItemID(doc.get("Custom34ID").toString());
			}
			myExpense.setCustom34(Custom34);
		}

		if (doc.get("Custom35Type")!=null) {
			CustomField Custom35 = new CustomField(doc.get("Custom35Type").toString(), doc.get("Custom35Value").toString());
			if ( (doc.get("Custom35Type").toString().equals("List")) || (doc.get("Custom35Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom35.setCode(doc.get("Custom35Code").toString());
				Custom35.setListItemID(doc.get("Custom35ID").toString());
			}
			myExpense.setCustom35(Custom35);
		}

		if (doc.get("Custom36Type")!=null) {
			CustomField Custom36 = new CustomField(doc.get("Custom36Type").toString(), doc.get("Custom36Value").toString());
			if ( (doc.get("Custom36Type").toString().equals("List")) || (doc.get("Custom36Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom36.setCode(doc.get("Custom36Code").toString());
				Custom36.setListItemID(doc.get("Custom36ID").toString());
			}
			myExpense.setCustom36(Custom36);
		}

		if (doc.get("Custom37Type")!=null) {
			CustomField Custom37 = new CustomField(doc.get("Custom37Type").toString(), doc.get("Custom37Value").toString());
			if ( (doc.get("Custom37Type").toString().equals("List")) || (doc.get("Custom37Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom37.setCode(doc.get("Custom37Code").toString());
				Custom37.setListItemID(doc.get("Custom37ID").toString());
			}
			myExpense.setCustom37(Custom37);
		}

		if (doc.get("Custom38Type")!=null) {
			CustomField Custom38 = new CustomField(doc.get("Custom38Type").toString(), doc.get("Custom38Value").toString());
			if ( (doc.get("Custom38Type").toString().equals("List")) || (doc.get("Custom38Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom38.setCode(doc.get("Custom38Code").toString());
				Custom38.setListItemID(doc.get("Custom38ID").toString());
			}
			myExpense.setCustom38(Custom38);
		}

		if (doc.get("Custom39Type")!=null) {
			CustomField Custom39 = new CustomField(doc.get("Custom39Type").toString(), doc.get("Custom39Value").toString());
			if ( (doc.get("Custom39Type").toString().equals("List")) || (doc.get("Custom39Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom39.setCode(doc.get("Custom39Code").toString());
				Custom39.setListItemID(doc.get("Custom39ID").toString());
			}
			myExpense.setCustom39(Custom39);
		}

		if (doc.get("Custom40Type")!=null) {
			CustomField Custom40 = new CustomField(doc.get("Custom40Type").toString(), doc.get("Custom40Value").toString());
			if ( (doc.get("Custom40Type").toString().equals("List")) || (doc.get("Custom40Type").toString().equals("ConnectedList")) ) {// it's a list type
				Custom40.setCode(doc.get("Custom40Code").toString());
				Custom40.setListItemID(doc.get("Custom40ID").toString());
			}
			myExpense.setCustom40(Custom40);
		}

		
		if (doc.get("OrgUnit1Type")!=null) {
			CustomField OrgUnit1 = new CustomField(doc.get("OrgUnit1Type").toString(), doc.get("OrgUnit1Value").toString());
			if ( (doc.get("OrgUnit1Type").toString().equals("List")) || (doc.get("OrgUnit1Type").toString().equals("ConnectedList")) ) {// it's a list type
				OrgUnit1.setCode(doc.get("OrgUnit1Code").toString());
				OrgUnit1.setListItemID(doc.get("OrgUnit1ID").toString());
			}
			myExpense.setOrgUnit1(OrgUnit1);
		}
		if (doc.get("OrgUnit2Type")!=null) {
			CustomField OrgUnit2 = new CustomField(doc.get("OrgUnit2Type").toString(), doc.get("OrgUnit2Value").toString());
			if ( (doc.get("OrgUnit2Type").toString().equals("List")) || (doc.get("OrgUnit2Type").toString().equals("ConnectedList")) ) {// it's a list type
				OrgUnit2.setCode(doc.get("OrgUnit2Code").toString());
				OrgUnit2.setListItemID(doc.get("OrgUnit2ID").toString());
			}
			myExpense.setOrgUnit2(OrgUnit2);
		}
		if (doc.get("OrgUnit3Type")!=null) {
			CustomField OrgUnit3 = new CustomField(doc.get("OrgUnit3Type").toString(), doc.get("OrgUnit3Value").toString());
			if ( (doc.get("OrgUnit3Type").toString().equals("List")) || (doc.get("OrgUnit3Type").toString().equals("ConnectedList")) ) {// it's a list type
				OrgUnit3.setCode(doc.get("OrgUnit3Code").toString());
				OrgUnit3.setListItemID(doc.get("OrgUnit3ID").toString());
			}
			myExpense.setOrgUnit3(OrgUnit3);
		}
		if (doc.get("OrgUnit4Type")!=null) {
			CustomField OrgUnit4 = new CustomField(doc.get("OrgUnit4Type").toString(), doc.get("OrgUnit4Value").toString());
			if ( (doc.get("OrgUnit4Type").toString().equals("List")) || (doc.get("OrgUnit4Type").toString().equals("ConnectedList")) ) {// it's a list type
				OrgUnit4.setCode(doc.get("OrgUnit4Code").toString());
				OrgUnit4.setListItemID(doc.get("OrgUnit4ID").toString());
			}
			myExpense.setOrgUnit4(OrgUnit4);
		}
		if (doc.get("OrgUnit5Type")!=null) {
			CustomField OrgUnit5 = new CustomField(doc.get("OrgUnit5Type").toString(), doc.get("OrgUnit5Value").toString());
			if ( (doc.get("OrgUnit5Type").toString().equals("List")) || (doc.get("OrgUnit5Type").toString().equals("ConnectedList")) ) {// it's a list type
				OrgUnit5.setCode(doc.get("OrgUnit5Code").toString());
				OrgUnit5.setListItemID(doc.get("OrgUnit5ID").toString());
			}
			myExpense.setOrgUnit5(OrgUnit5);
		}
		if (doc.get("OrgUnit6Type")!=null) {
			CustomField OrgUnit6 = new CustomField(doc.get("OrgUnit6Type").toString(), doc.get("OrgUnit6Value").toString());
			if ( (doc.get("OrgUnit6Type").toString().equals("List")) || (doc.get("OrgUnit6Type").toString().equals("ConnectedList")) ) {// it's a list type
				OrgUnit6.setCode(doc.get("OrgUnit6Code").toString());
				OrgUnit6.setListItemID(doc.get("OrgUnit6ID").toString());
			}
			myExpense.setOrgUnit6(OrgUnit6);
		}
		
		// create the Itemizations for this expense
		BasicDBList itemizations = new BasicDBList();
		DBObject Doc, JournalDoc, VATDoc;
		itemizations = (BasicDBList) doc.get("Itemizations");
		ArrayList<Itemization> items = new ArrayList<Itemization>();// initialize an ArrayList of Itemization object elements
		Itemization item = new Itemization();// placeholder for an Itemization object
		
		int itemcount = itemizations.size();//the number of Itemization documents for this Expense
		if (itemcount > 0) {//then there are itemization documents for this expense
			for (int i=0; i<itemcount; i++) {//iterate for each itemization
				Doc = (DBObject) itemizations.get(i);
				item = item.doctoItemization(Doc);//get the item

				//create the Allocations for this item
				BasicDBList allocations = new BasicDBList();
				allocations = (BasicDBList) Doc.get("Allocations");
				
				if (allocations != null) {//then there are allocation documents for this itemization
					ArrayList<Allocation> allocs = new ArrayList<Allocation>();// initialize an ArrayList of Allocation object elements
					Allocation allocation= new Allocation();// placeholder for an Allocation object
					int alloccount = allocations.size();//the number of Allocation documents for this Itemization

					for (int j=0; j<alloccount; j++) {//iterate for each Allocation document
						Doc = (DBObject) allocations.get(j);// get the Allocation document for this iteration
						allocation = allocation.doctoAllocation(Doc);//get the Allocation object for this Allocation document
						
						//create the Journal Entries for this allocation
						BasicDBList journals = new BasicDBList();
						journals = (BasicDBList) Doc.get("JournalEntries");
						if (journals != null) {//then there are journal entry documents for this allocation
							ArrayList<JournalEntry> entries = new ArrayList<JournalEntry>();// initialize an ArrayList of Journal Entry object elements
							JournalEntry journal= new JournalEntry();// placeholder for an Journal Entry object
							
							int journalcount = journals.size();//the number of Jouranl Entry documents for this Allocation
							for (int k=0; k<journalcount; k++) {//iterate for each Journal Entry document
								JournalDoc = (DBObject) journals.get(k);// get the Journal Entry document for this iteration
								journal = journal.doctoJournalEntry(JournalDoc);//get the Journal Entry object for this Journal Entry document
								entries.add(journal);
							}// k loop
							allocation.setJournals(entries);
						}// if (journals != null)
						
						//create the VAT Details for this allocation

						BasicDBList vatdata = new BasicDBList();
						vatdata = (BasicDBList) Doc.get("VATData");
						if (vatdata != null) {//then there are VAT Data documents for this allocation
							ArrayList<VATData> vatdetails = new ArrayList<VATData>();// initialize an ArrayList of VAT Data object elements
							VATData vatdetail= new VATData();// placeholder for a VAT Data object
							
							int vatcount = vatdata.size();//the number of VAT Data documents for this Allocation
							for (int k=0; k<vatcount; k++) {//iterate for each VAT Data document
								VATDoc = (DBObject) vatdata.get(k);// get the VAT Data document for this iteration
								vatdetail = vatdetail.doctoVATData(VATDoc);//get the VAT Data object for this Journal Entry document
								vatdetails.add(vatdetail);
							}// k loop
							allocation.setVatdetails(vatdetails);
						}// if (vatdata != null)

						allocs.add(allocation);//add the Allocation to the allocations ArrayList 
					}//j loop
					item.setAllocations(allocs);

				}// if (allocations != null)
				
				items.add(item);//add the item to the items ArrayList 
			}//i loop
			myExpense.setItems(items);// add the Itemizations
		}//if (itemcount > 0) block
		Journey journey = new Journey();
		if (doc.get("Journey")!=null) {// then this is Milage expense, get its Journey object
			Doc = (DBObject) doc.get("Journey");// get the Journey document for this expense
			journey = journey.doctoJourney(Doc);// convert the Journey document into a Journey object
			myExpense.setJourney(journey);// add the Journey object to the Expense object
		}// if journey document
		if (doc.get("PrimeStatus")!=null) {
			String PrimeStatus = doc.get("PrimeStatus").toString();
			myExpense.setPrimeStatus(PrimeStatus);
		}
		if (doc.get("CanonicalTransactionID")!=null) {
			String CanonicalTransactionID = doc.get("CanonicalTransactionID").toString();
			myExpense.setCanonicalTransactionID(CanonicalTransactionID);
		}
		if (doc.get("NexusStatus")!=null) {
			String NexusStatus = doc.get("NexusStatus").toString();
			myExpense.setNexusStatus(NexusStatus);
		}
		if (doc.get("ReportPayeeID")!=null) {
			String ReportPayeeID = doc.get("ReportPayeeID").toString();
			myExpense.setReportPayeeID(ReportPayeeID);
		}
		if (doc.get("PayeeVendorID")!=null) {
			String PayeeVendorID = doc.get("PayeeVendorID").toString();
			myExpense.setPayeeVendorID(PayeeVendorID);
		}
		if (doc.get("PayeeAddressID")!=null) {
			String PayeeAddressID = doc.get("PayeeAddressID").toString();
			myExpense.setPayeeAddressID(PayeeAddressID);
		}
		if (doc.get("PayeePaymentMethod")!=null) {
			String PayeePaymentMethod = doc.get("PayeePaymentMethod").toString();
			myExpense.setPayeePaymentMethod(PayeePaymentMethod);
		}

		return myExpense;
}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	public String getReportOwnerID() {
		return ReportOwnerID;
	}

	public void setReportOwnerID(String reportOwnerID) {
		ReportOwnerID = reportOwnerID;
	}

	public String getEmployeeID() {
		return EmployeeID;
	}
	public void setEmployeeID(String employeeID) {
		EmployeeID = employeeID;
	}
	public String getExpenseTypeName() {
		return ExpenseTypeName;
	}
	public void setExpenseTypeName(String expenseTypeName) {
		ExpenseTypeName = expenseTypeName;
	}
	public String getPaymentTypeName() {
		return PaymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		PaymentTypeName = paymentTypeName;
	}
	
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}

	public Boolean getHasBillableItems() {
		return HasBillableItems;
	}
	public void setHasBillableItems(Boolean hasBillableItems) {
		HasBillableItems = hasBillableItems;
	}
	public String getLocationDisplayName() {
		return LocationDisplayName;
	}
	public void setLocationDisplayName(String locationDisplayName) {
		LocationDisplayName = locationDisplayName;
	}
	public Date getTransactionDate() {
		return TransactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		TransactionDate = transactionDate;
	}
	public String getOriginalCurrency() {
		return OriginalCurrency;
	}
	public void setOriginalCurrency(String originalCurrency) {
		OriginalCurrency = originalCurrency;
	}
	public double getOriginalAmount() {
		return OriginalAmount;
	}
	public void setOriginalAmount(double originalAmount) {
		OriginalAmount = originalAmount;
	}
	public String getPostedCurrency() {
		return PostedCurrency;
	}
	public void setPostedCurrency(String postedCurrency) {
		PostedCurrency = postedCurrency;
	}
	public double getPostedAmount() {
		return PostedAmount;
	}
	public void setPostedAmount(double postedAmount) {
		PostedAmount = postedAmount;
	}
	public double getApprovedAmount() {
		return ApprovedAmount;
	}
	public void setApprovedAmount(double approvedAmount) {
		ApprovedAmount = approvedAmount;
	}
	public String getMerchantName() {
		return MerchantName;
	}
	public void setMerchantName(String merchantName) {
		MerchantName = merchantName;
	}
	
	public String getElectronicReceiptID() {
		return ElectronicReceiptID;
	}

	public void setElectronicReceiptID(String electronicReceiptID) {
		ElectronicReceiptID = electronicReceiptID;
	}

	public double getExchangeRate() {
		return ExchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		ExchangeRate = exchangeRate;
	}

	public String getTripID() {
		return TripID;
	}

	public void setTripID(String tripID) {
		TripID = tripID;
	}

	public Byte[] getReceiptImage() {
		return ReceiptImage;
	}
	public void setReceiptImage(Byte[] receiptImage) {
		ReceiptImage = receiptImage;
	}
	public String getApprovalStatus() {
		return ApprovalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		ApprovalStatus = approvalStatus;
	}
	public String getPaymentStatus() {
		return PaymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		PaymentStatus = paymentStatus;
	}
	public Date getPaidDate() {
		return PaidDate;
	}
	public void setPaidDate(Date paidDate) {
		PaidDate = paidDate;
	}
	public ArrayList<Itemization> getItems() {
		return Items;
	}
	public void setItems(ArrayList<Itemization> items) {
		Items = items;
	}
	public Journey getJourney() {
		return Journey;
	}
	public void setJourney(Journey journey) {
		this.Journey = journey;
	}
	public String getCompanyID() {
		return CompanyID;
	}
	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}
	public String getEmployeeDisplayName() {
		return EmployeeDisplayName;
	}
	public void setEmployeeDisplayName(String employeeDisplayName) {
		EmployeeDisplayName = employeeDisplayName;
	}
	public String getEntry_ID() {
		return Entry_ID;
	}
	public void setEntry_ID(String entry_ID) {
		Entry_ID = entry_ID;
	}
	public String getReportID() {
		return ReportID;
	}
	public void setReportID(String reportID) {
		ReportID = reportID;
	}
	public String getEmployeeFirstName() {
		return EmployeeFirstName;
	}
	public void setEmployeeFirstName(String employeeFirstName) {
		EmployeeFirstName = employeeFirstName;
	}
	public String getEmployeeMiddleInitial() {
		return EmployeeMiddleInitial;
	}
	public void setEmployeeMiddleInitial(String employeeMiddleInitial) {
		EmployeeMiddleInitial = employeeMiddleInitial;
	}
	public String getEmployeeLastName() {
		return EmployeeLastName;
	}
	public void setEmployeeLastName(String employeeLastName) {
		EmployeeLastName = employeeLastName;
	}
	public String getLocationCity() {
		return LocationCity;
	}
	public void setLocationCity(String locationCity) {
		LocationCity = locationCity;
	}
	public String getLocationState() {
		return LocationState;
	}
	public void setLocationState(String locationState) {
		LocationState = locationState;
	}
	public String getLocationCountry() {
		return LocationCountry;
	}
	public void setLocationCountry(String locationCountry) {
		LocationCountry = locationCountry;
	}
	
	public Boolean getHasItemizations() {
		return HasItemizations;
	}

	public void setHasItemizations(Boolean hasItemizations) {
		HasItemizations = hasItemizations;
	}

	public Boolean getIsPersonal() {
		return IsPersonal;
	}

	public void setIsPersonal(Boolean isPersonal) {
		IsPersonal = isPersonal;
	}

	public Boolean getIsPersonalCardCharge() {
		return IsPersonalCardCharge;
	}

	public void setIsPersonalCardCharge(Boolean isPersonalCardCharge) {
		IsPersonalCardCharge = isPersonalCardCharge;
	}

	public Boolean getHasVAT() {
		return HasVAT;
	}

	public void setHasVAT(Boolean hasVAT) {
		HasVAT = hasVAT;
	}

	public Boolean getHasImage() {
		return HasImage;
	}
	public void setHasImage(Boolean hasImage) {
		HasImage = hasImage;
	}
	public Boolean getIsPaidByExpensePay() {
		return IsPaidByExpensePay;
	}
	public void setIsPaidByExpensePay(Boolean isPaidByExpensePay) {
		IsPaidByExpensePay = isPaidByExpensePay;
	}
	public Date getLastModified() {
		return LastModified;
	}
	public void setLastModified(Date lastModified) {
		LastModified = lastModified;
	}
	public CustomField getCustom1() {
		return Custom1;
	}
	public void setCustom1(CustomField custom1) {
		Custom1 = custom1;
	}
	public CustomField getCustom2() {
		return Custom2;
	}
	public void setCustom2(CustomField custom2) {
		Custom2 = custom2;
	}
	public CustomField getCustom3() {
		return Custom3;
	}
	public void setCustom3(CustomField custom3) {
		Custom3 = custom3;
	}
	public CustomField getCustom4() {
		return Custom4;
	}
	public void setCustom4(CustomField custom4) {
		Custom4 = custom4;
	}
	public CustomField getCustom5() {
		return Custom5;
	}
	public void setCustom5(CustomField custom5) {
		Custom5 = custom5;
	}
	public CustomField getCustom6() {
		return Custom6;
	}
	public void setCustom6(CustomField custom6) {
		Custom6 = custom6;
	}
	public CustomField getCustom7() {
		return Custom7;
	}
	public void setCustom7(CustomField custom7) {
		Custom7 = custom7;
	}
	public CustomField getCustom8() {
		return Custom8;
	}
	public void setCustom8(CustomField custom8) {
		Custom8 = custom8;
	}
	public CustomField getCustom9() {
		return Custom9;
	}
	public void setCustom9(CustomField custom9) {
		Custom9 = custom9;
	}
	public CustomField getCustom10() {
		return Custom10;
	}
	public void setCustom10(CustomField custom10) {
		Custom10 = custom10;
	}
	public CustomField getCustom11() {
		return Custom11;
	}
	public void setCustom11(CustomField custom11) {
		Custom11 = custom11;
	}
	public CustomField getCustom12() {
		return Custom12;
	}
	public void setCustom12(CustomField custom12) {
		Custom12 = custom12;
	}
	public CustomField getCustom13() {
		return Custom13;
	}
	public void setCustom13(CustomField custom13) {
		Custom13 = custom13;
	}
	public CustomField getCustom14() {
		return Custom14;
	}
	public void setCustom14(CustomField custom14) {
		Custom14 = custom14;
	}
	public CustomField getCustom15() {
		return Custom15;
	}
	public void setCustom15(CustomField custom15) {
		Custom15 = custom15;
	}
	public CustomField getCustom16() {
		return Custom16;
	}
	public void setCustom16(CustomField custom16) {
		Custom16 = custom16;
	}
	public CustomField getCustom17() {
		return Custom17;
	}
	public void setCustom17(CustomField custom17) {
		Custom17 = custom17;
	}
	public CustomField getCustom18() {
		return Custom18;
	}
	public void setCustom18(CustomField custom18) {
		Custom18 = custom18;
	}
	public CustomField getCustom19() {
		return Custom19;
	}
	public void setCustom19(CustomField custom19) {
		Custom19 = custom19;
	}
	public CustomField getCustom20() {
		return Custom20;
	}
	public void setCustom20(CustomField custom20) {
		Custom20 = custom20;
	}
	public CustomField getCustom21() {
		return Custom21;
	}
	public void setCustom21(CustomField custom21) {
		Custom21 = custom21;
	}
	
	public CustomField getCustom22() {
		return Custom22;
	}
	public void setCustom22(CustomField custom22) {
		Custom22 = custom22;
	}
	public CustomField getCustom23() {
		return Custom23;
	}
	public void setCustom23(CustomField custom23) {
		Custom23 = custom23;
	}
	public CustomField getCustom24() {
		return Custom24;
	}
	public void setCustom24(CustomField custom24) {
		Custom24 = custom24;
	}
	public CustomField getCustom25() {
		return Custom25;
	}
	public void setCustom25(CustomField custom25) {
		Custom25 = custom25;
	}
	public CustomField getCustom26() {
		return Custom26;
	}
	public void setCustom26(CustomField custom26) {
		Custom26 = custom26;
	}
	public CustomField getCustom27() {
		return Custom27;
	}
	public void setCustom27(CustomField custom27) {
		Custom27 = custom27;
	}
	public CustomField getCustom28() {
		return Custom28;
	}
	public void setCustom28(CustomField custom28) {
		Custom28 = custom28;
	}
	public CustomField getCustom29() {
		return Custom29;
	}
	public void setCustom29(CustomField custom29) {
		Custom29 = custom29;
	}
	public CustomField getCustom30() {
		return Custom30;
	}
	public void setCustom30(CustomField custom30) {
		Custom30 = custom30;
	}
	public CustomField getCustom31() {
		return Custom31;
	}
	public void setCustom31(CustomField custom31) {
		Custom31 = custom31;
	}
	public CustomField getCustom32() {
		return Custom32;
	}
	public void setCustom32(CustomField custom32) {
		Custom32 = custom32;
	}
	public CustomField getCustom33() {
		return Custom33;
	}
	public void setCustom33(CustomField custom33) {
		Custom33 = custom33;
	}
	public CustomField getCustom34() {
		return Custom34;
	}
	public void setCustom34(CustomField custom34) {
		Custom34 = custom34;
	}
	public CustomField getCustom35() {
		return Custom35;
	}
	public void setCustom35(CustomField custom35) {
		Custom35 = custom35;
	}
	public CustomField getCustom36() {
		return Custom36;
	}
	public void setCustom36(CustomField custom36) {
		Custom36 = custom36;
	}
	public CustomField getCustom37() {
		return Custom37;
	}
	public void setCustom37(CustomField custom37) {
		Custom37 = custom37;
	}
	public CustomField getCustom38() {
		return Custom38;
	}
	public void setCustom38(CustomField custom38) {
		Custom38 = custom38;
	}
	public CustomField getCustom39() {
		return Custom39;
	}
	public void setCustom39(CustomField custom39) {
		Custom39 = custom39;
	}
	public CustomField getCustom40() {
		return Custom40;
	}
	public void setCustom40(CustomField custom40) {
		Custom40 = custom40;
	}
	public CustomField getOrgUnit1() {
		return OrgUnit1;
	}
	public void setOrgUnit1(CustomField orgUnit1) {
		OrgUnit1 = orgUnit1;
	}
	public CustomField getOrgUnit2() {
		return OrgUnit2;
	}
	public void setOrgUnit2(CustomField orgUnit2) {
		OrgUnit2 = orgUnit2;
	}
	public CustomField getOrgUnit3() {
		return OrgUnit3;
	}
	public void setOrgUnit3(CustomField orgUnit3) {
		OrgUnit3 = orgUnit3;
	}
	public CustomField getOrgUnit4() {
		return OrgUnit4;
	}
	public void setOrgUnit4(CustomField orgUnit4) {
		OrgUnit4 = orgUnit4;
	}
	public CustomField getOrgUnit5() {
		return OrgUnit5;
	}
	public void setOrgUnit5(CustomField orgUnit5) {
		OrgUnit5 = orgUnit5;
	}
	public CustomField getOrgUnit6() {
		return OrgUnit6;
	}
	public void setOrgUnit6(CustomField orgUnit6) {
		OrgUnit6 = orgUnit6;
	}

	public String getReportId() {
		return ReportId;
	}

	public void setReportId(String reportId) {
		ReportId = reportId;
	}

	public String getReportName() {
		return ReportName;
	}

	public void setReportName(String reportName) {
		ReportName = reportName;
	}

	public String getReportPurpose() {
		return ReportPurpose;
	}

	public void setReportPurpose(String reportPurpose) {
		ReportPurpose = reportPurpose;
	}

	public String getLedgerName() {
		return LedgerName;
	}

	public void setLedgerName(String ledgerName) {
		LedgerName = ledgerName;
	}

	public String getPolicyID() {
		return PolicyID;
	}

	public void setPolicyID(String policyID) {
		PolicyID = policyID;
	}

	public double getReportTotal() {
		return ReportTotal;
	}

	public void setReportTotal(double reportTotal) {
		ReportTotal = reportTotal;
	}

	public double getPersonalExpense() {
		return PersonalExpense;
	}

	public void setPersonalExpense(double personalExpense) {
		PersonalExpense = personalExpense;
	}

	public double getAmountDueEmployee() {
		return AmountDueEmployee;
	}

	public void setAmountDueEmployee(double amountDueEmployee) {
		AmountDueEmployee = amountDueEmployee;
	}

	public double getAmountDueCompanyCard() {
		return AmountDueCompanyCard;
	}

	public void setAmountDueCompanyCard(double amountDueCompanyCard) {
		AmountDueCompanyCard = amountDueCompanyCard;
	}

	public double getTotalApprovedAmount() {
		return TotalApprovedAmount;
	}

	public void setTotalApprovedAmount(double totalApprovedAmount) {
		TotalApprovedAmount = totalApprovedAmount;
	}

	public String getEmployeeCountry() {
		return EmployeeCountry;
	}

	public void setEmployeeCountry(String employeeCountry) {
		EmployeeCountry = employeeCountry;
	}

	public String getEmployeeState() {
		return EmployeeState;
	}

	public void setEmployeeState(String employeeState) {
		EmployeeState = employeeState;
	}

	public String getTaxNexus() {
		return TaxNexus;
	}

	public void setTaxNexus(String nexus) {
		TaxNexus = nexus;
	}
	
	
	
	public String getCanonicalTransactionID() {
		return CanonicalTransactionID;
	}

	public void setCanonicalTransactionID(String canonicalTransactionID) {
		CanonicalTransactionID = canonicalTransactionID;
	}

	public String getNexusStatus() {
		return NexusStatus;
	}

	public void setNexusStatus(String nexusStatus) {
		NexusStatus = nexusStatus;
	}
	public String getPayeeVendorID() {
		return PayeeVendorID;
	}

	public void setPayeeVendorID(String payeeVendorID) {
		PayeeVendorID = payeeVendorID;
	}

	public String getPayeeAddressID() {
		return PayeeAddressID;
	}

	public void setPayeeAddressID(String payeeAddressID) {
		PayeeAddressID = payeeAddressID;
	}

	public String getPaymentTypeCode() {
		return PaymentTypeCode;
	}

	public void setPaymentTypeCode(String paymentTypeCode) {
		PaymentTypeCode = paymentTypeCode;
	}
	

	public String getPayeePaymentMethod() {
		return PayeePaymentMethod;
	}

	public void setPayeePaymentMethod(String payeePaymentMethod) {
		PayeePaymentMethod = payeePaymentMethod;
	}

	public String getReportPayeeID() {
		return ReportPayeeID;
	}

	public void setReportPayeeID(String reportPayeeID) {
		ReportPayeeID = reportPayeeID;
	}

	public String getPrimeStatus() {
		return PrimeStatus;
	}

	public void setPrimeStatus(String primeStatus) {
		PrimeStatus = primeStatus;
	}

	public void TaxNexus(){
		if (this.getEmployeeCountry() != null && this.getLocationCountry() != null){// then can calculate Tax Nexus for the Tax
			if (this.getEmployeeCountry().equals(this.getLocationCountry())) {// the Employee's Country and Location Country are the same
				this.setTaxNexus("Domestic");// so, set Tax Nexus to Domestic
			} else {// the Employee's Country and Location Country are different
				this.setTaxNexus("Foreign");// so, set Tax Nexus to Foreign
			}
		}
		
	}

}

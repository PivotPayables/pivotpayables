package com.pivotpayables.concurplatform;



/**
 * @author John Toman
 * 5/232015
 * Base class for a Concur expense entry or Entry resource
 *
 */
import static java.lang.System.out;

import java.util.Date;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.annotate.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense_Entry {

	/*  An expense is a purchase of a good or service by an employee of a company.
	 * 
	 * 	The employee pays for the purchase either with the employee's cash or personal credit card, a company paid credit card, or using a purchase order where the merchant invoices
	 *  the employee's company (q.k.q., "Company Paid").  This is refered to as the "payment type".  It refers to how the MERCHANT was paid.
	 *  
	 *  The company reimburses the employee for the approved amount of purchases paid with the employee's cash or personal credit card.
	 *  The company pays the card issuer of the company credit card for the approved amount of purchased paid with a company credit card.
	 *  The company pays the merchant for purchases paid with by purchase order/invoice.
	 */

// Required elements for a POST
	@JsonProperty
	private String ReportID;// the GUID for the Concur Expense, expense report this expense entry is a member
	
	@JsonProperty
	private String PaymentTypeID;// the unique identifier of the type of payment the employee used to make the purchase
	
	@JsonProperty
	private String ExpenseTypeCode;// the unique identifier for the expense type
	
	@JsonProperty
	private String TransactionDate;// when the purchase happened
	
	@JsonProperty
	private double TransactionAmount;// the amount of the purchase in original currency, and that appears on the receipt
	
	@JsonProperty
	private String TransactionCurrencyCode;// the currency paid to merchant, and that appears on the receipt
	
// Optional elements for a POST
	@JsonProperty
	private double ExchangeRate;// The currency conversion rate that converts the Transaction Amount that is in Transaction Currency into the Posted Amount that is in Report Currency. 
	
	@JsonProperty
	private String LocationID;
	
	@JsonProperty
	private String Description;// The description of the expense
	
	@JsonProperty ("IsBillable")
	private Boolean HasBillableItems;// whether any of the itemizations are billable
	
	@JsonProperty
	private Boolean HasItemizations;// whether there are itemizations
	
	@JsonProperty
	private Boolean IsPersonal;// whether this expense is non-reimbursable or personal
	
	@JsonProperty
	private Boolean IsPersonalCardCharge;// whether this expense was imported from a personal card charge downloaded using the Yodlee API
	
	@JsonProperty
	private Boolean HasVAT;// whether this expense has VAT
	
	@JsonProperty
	private String VendorDescription;// the name of the merchant the employee paid
	
	@JsonProperty
	private Journey Journey;// the Journey object for mileage expenses.  Contains data about the journey.
	
	@JsonProperty
	private CustomField	Custom1;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom2;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom3;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom4;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom5;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom6;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom7;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom8;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom9;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom10;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom11;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom12;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom13;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom14;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom15;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom16;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom17;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom18;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom19;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom20;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom21;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom22;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom23;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom24;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom25;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom26;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom27;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom28;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom29;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom30;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom31;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom32;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom33;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom34;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom35;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom36;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom37;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom38;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom39;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	Custom40;//	The details from the Custom fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	OrgUnit1;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	OrgUnit2;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	OrgUnit3;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	OrgUnit4;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	OrgUnit5;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	
	@JsonProperty
	private CustomField	OrgUnit6;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	
	
// Elements only in the GET
	@JsonProperty
	private String ID;// the GUID for the Concur Expense, expense entry
	
	@JsonProperty
	private String ExpenseTypeName;// the name of the expense type that appears in the user interface
	
	@JsonProperty
	private String PaymentTypeName;// the name of the type of payment the employee used to make the purchase
	
	@JsonProperty
	private String ReportOwnerID;// The login ID for the report owner
	
	@JsonProperty("LocationName")
	private String LocationCity;
	
	@JsonProperty("LocationSubdivision")
	private String LocationState;
	
	@JsonProperty
	private String LocationCountry;
	
	@JsonProperty
	private double PostedAmount;// the original amount converted into posted currency
	
	@JsonProperty
	private double ApprovedAmount;// the portion of the posted amount approved for reimbursement/payment to the employee/card issuer
	
	@JsonProperty
	private Boolean HasImage;// the entry has a receipt image
	
	@JsonProperty
	private Boolean IsPaidByExpensePay;// the entry was paid by Expense Pay
	
	@JsonProperty
	private Date LastModified;// when the purchase happened
	
	
	@JsonProperty
	private String ElectronicReceiptID;// When there is an eReceipt associated to this expense this is its unique identifier. 
	
	@JsonProperty
	private String TripID;// When there is a trip in the Itinerary Service that includes the travel booking associated to this expense this is the trip's unique identifier.
		
	public ExpenseEntry () {}// no args constructor



	public void display () {//method to display the expense in the console
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";
		String strDate = null;

		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
     
		
		out.println("ID: " + ID);
		out.println("ReportID: " + getReportID());
		if (TripID != null) {
			out.println("Trip ID: " + TripID);
		}
		if (ElectronicReceiptID != null) {
			out.println("Electronic Receipt ID: " + ElectronicReceiptID);
		}
		out.println("Report Owner ID: " + ReportOwnerID);
		out.println("Expense Type: " + ExpenseTypeName);
		if (ExpenseTypeCode != null) {
			out.println("Expense Type Code: " + ExpenseTypeCode);
		}
		out.println("Payment Type: " + PaymentTypeName);
		if (PaymentTypeID != null) {
			out.println("Payment Type ID: " + PaymentTypeID);
		}
		if (Description != null) {
			out.println("Description: " + Description);
		}
		out.println("Date: " + TransactionDate);
		out.println("Amount: " + decimalFormat.format(TransactionAmount) + " " + TransactionCurrencyCode);
		out.println("Merchant: " + VendorDescription);
		if (LocationID != null) {
			out.println("Location ID: " + LocationID);
			out.println("City: " + getLocationCity());
			out.println("State: " + getLocationState());
			out.println("Country: " + getLocationCountry());
		}
		out.println("Exchange Rate: " + ExchangeRate);
		out.println("Posted Amount: " + decimalFormat.format(PostedAmount));
		out.println("Approved Amount: " + decimalFormat.format(ApprovedAmount));
		
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
		out.println("___________________________________________");
		out.println();
		if (Journey != null) {
			out.println("Journey: ");
			out.println("-----------------------");
			Journey.display();
		}
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
	public String getExpenseTypeName() {
		return ExpenseTypeName;
	}
	public void setExpenseTypeName(String expenseTypeName) {
		ExpenseTypeName = expenseTypeName;
	}
	
	public String getExpenseTypeCode() {
		return ExpenseTypeCode;
	}

	public void setExpenseTypeCode(String expenseTypeCode) {
		ExpenseTypeCode = expenseTypeCode;
	}

	public String getPaymentTypeName() {
		return PaymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		PaymentTypeName = paymentTypeName;
	}
	
	public String getPaymentTypeID() {
		return PaymentTypeID;
	}

	public void setPaymentTypeID(String paymentTypeID) {
		PaymentTypeID = paymentTypeID;
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
	public String getTransactionDate() {
		return TransactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		TransactionDate = transactionDate;
	}
	public String getTransactionCurrencyCode() {
		return TransactionCurrencyCode;
	}
	public void setTransactionCurrencyCode(String originalCurrency) {
		TransactionCurrencyCode = originalCurrency;
	}
	public double getTransactionAmount() {
		return TransactionAmount;
	}
	public void setTransactionAmount(double originalAmount) {
		TransactionAmount = originalAmount;
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
	public String getVendorDescription() {
		return VendorDescription;
	}
	public void setVendorDescription(String merchantName) {
		VendorDescription = merchantName;
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

	public Journey getJourney() {
		return Journey;
	}
	public void setJourney(Journey journey) {
		this.Journey = journey;
	}
	public String getReportID() {
		return ReportID;
	}
	public void setReportID(String rid) {
		ReportID = rid;
	}
	
	public String getLocationID() {
		return LocationID;
	}

	public void setLocationID(String locationID) {
		LocationID = locationID;
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
	
	/*
	
	//@JsonIgnoreProperties(ignoreUnknown = true) 
	private static class CustomField {
		@JsonProperty
		public String Type;// 	The custom field type. Will be one of the following: Amount, Boolean, ConnectedList, Date, Integer, List, Number, Text.
		@JsonProperty
		public String Value;// The value in the org unit or custom field. For list fields this is the name of the list item. Maximum 48 characters.
		@JsonProperty
		public String Code;// For list fields this is the list item code.
		@JsonProperty
		public String ListItemID;// For list fields, this is the list item ID.
		
		public CustomField () {}// no arg constructor

		public String getType() {
			return Type;
		}

		public void setType(String type) {
			Type = type;
		}

		public String getValue() {
			return Value;
		}

		public void setValue(String value) {
			Value = value;
		}

		public String getCode() {
			return Code;
		}

		public void setCode(String code) {
			Code = code;
		}

		public String getListItemID() {
			return ListItemID;
		}

		public void setListItemID(String listItemID) {
			ListItemID = listItemID;
		}
		
	}
	private void displayCustom (CustomField f) {
		out.println("Type: " + f.Type);

		if (f.Code != null) {
			out.println("Item Value: " + f.Value);
			out.println("Item Code: " + f.Code);
		} else {
			out.println("Value: " + f.Value);
		}
		if (f.ListItemID != null) {
			out.println("Item ID: " + f.ListItemID);
		}
	}
	
	*/


}

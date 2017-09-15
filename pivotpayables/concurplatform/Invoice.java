package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 2/23/2016
 * Base class for a Concur payment request (b.k.a., an invoice)
 *
 */
import static java.lang.System.out;

import java.util.ArrayList;


import java.util.Locale;
import java.text.DecimalFormat;
import java.text.NumberFormat;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.*;




@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice {


	/*  An invoice is a purchase of a good or service by an employee of a company, who is called the "buyer" where the seller, who is called a "vendor" provides 
	 *  the buyer credit at the time of the purchase. It's called a "payment request" because an invoice is a request to the buyer to pay the vendor for these goods or services.
	 *  
	 * 
	 * 	In Concur a payment request is similar to an payment request entry for an payment request report.  A payment request and an payment request entry both represent a purchase from a vendor (seller) by an employee (vendor).  
	 *
	 */


	@XmlElement
	@JsonProperty
	String ID;// the GUID for the Concur Invoice, payment request

	@JsonProperty
	String ApprovalStatus;// the approval status of the payment request that contains this payment request
	@JsonProperty
	String BuyerCostCenter;// the cost center the buyer assigned this payment request
	@XmlElement
	@JsonProperty
	double CalculatedAmount;// the amount calculated for this payment request@XmlElement
	@XmlElement
	@JsonProperty
	private String CountryCode;
	@XmlElement
	@JsonProperty
	String CurrencyCode;// the currency for the payment request
	@XmlElement
	@JsonProperty
	private String	Custom1;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom2;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom3;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom4;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom5;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom6;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom7;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom8;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom9;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom10;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom11;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom12;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom13;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom14;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom15;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom16;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom17;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom18;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom19;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom20;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom21;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom22;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom23;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	Custom24;//	The details from the Custom fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	String Description;// The description of the payment request
	@XmlElement
	@JsonProperty
	String CreatedByUsername;// The login ID for the payment request owner
	@XmlElement
	@JsonProperty
	String ExtractDate;// the employee who incurred the payment request
	@XmlElement
	@JsonProperty
	String EmployeeName;// the employee who incurred the payment request
	@XmlElement
	@JsonProperty
	double InvoiceAmount;// the amount of the purchase in original currency, and that appears on the vendor's invoice
	@XmlElement
	@JsonProperty
	String InvoiceDate;// when the purchase happened
	@XmlElement
	@JsonProperty
	String InvoiceNumber;// When there is an eReceipt associated to this payment request this is its unique identifier. 
	@XmlElement
	@JsonProperty
	Boolean IsPaymentRequestDeleted;// this payment request was deleted
	@XmlElement
	@JsonProperty
	Boolean IsTestTransaction;// this is a test transaction
	@XmlElement
	@JsonProperty
	String LedgerCode;
	@XmlElement
	@JsonProperty
	double LineItemTotalAmount;// the payment request amount in posting currency, which is the sum of line item
	@XmlElement
	@JsonProperty
	ArrayList<LineItem> LineItems;
	@XmlElement
	@JsonProperty
	String Name;// the name of the payment request 	
	@XmlElement
	@JsonProperty
	String NotestoVendor;// the note to the vendor
	@XmlElement
	@JsonProperty
	private String	OrgUnit1;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	OrgUnit2;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	OrgUnit3;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	OrgUnit4;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	OrgUnit5;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	private String	OrgUnit6;//	The details from the Org Unit fields. These may not have data, depending on configuration.
	@XmlElement
	@JsonProperty
	String PaidDate;// when the payment request this payment request is a member was paid
	@XmlElement
	@JsonProperty
	String PaymentMethod;// the method to pay this payment request
	@XmlElement
	@JsonProperty
	String PaymentStatus;// the payment status of the payment request.
	@XmlElement
	@JsonProperty
	String PurchaseOrderNumber;// the payment status of the payment request.
	@XmlElement
	@JsonProperty
	double ShippingAmount;// the amount for shipping for this payment request
	@XmlElement
	@JsonProperty
	double TotalApprovedAmount;// the total amount approved for this payment request
	@XmlElement
	@JsonProperty
	String VendorDescription;// the name of the merchant the employee paid
	@XmlElement
	@JsonProperty
	Address VendorRemitAddress;// the Vendor's address
	

	// These are elements not in the ExpenseEntry V3.0 Model, but are in the Expense object and document
	// This means these need to be calculated to determine their values.
	String CompanyID;// the GUID in the MongoDB for the employee's company
	String EmployeeID;// the GUID in the MongoDB for the employee who is the invoice owner
	String EmployeeDisplayName;// the employee who incurred the expense
	String EmployeeFirstName;
	String EmployeeMiddleInitial;
	String EmployeeLastName;
	Byte[] ReceiptImage;// Byte array for holding the PDF file of the payment request's image





	
	public Invoice () {}// no args constructor
	public Invoice(String json) {}
	

	public void display () {//method to display the payment request in the console
		
		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";
		

		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		

		
		out.println("ID: " + ID);
		out.println("Invoice Number: " + InvoiceNumber);

		out.println("Invoice Owner ID: " + CreatedByUsername);
		out.println("Invoice Display Name: " + EmployeeName);
		out.println("Ledger Code: " + LedgerCode);
		out.println("Description: " + Description);
		if (InvoiceDate != null) { 
			out.println("Date: " + InvoiceDate);
		}
		out.println("Invoice Amount: " + decimalFormat.format(InvoiceAmount) + " " + CurrencyCode);

		if (VendorRemitAddress != null ) {
			VendorRemitAddress.display();
		}
     

		out.println("Line Item Total Amount: " + decimalFormat.format(LineItemTotalAmount) + " " + CurrencyCode);
		out.println("Approved Amount: " + decimalFormat.format(TotalApprovedAmount) + " " + CurrencyCode);
		out.println("Shipping Amount: " + decimalFormat.format(ShippingAmount) + " " + CurrencyCode);

		if (IsPaymentRequestDeleted != null) {
			out.println("Is Payment Request Deleted: " + IsPaymentRequestDeleted.toString());
		}
		if (IsTestTransaction != null) {
			out.println("Is Test Transaction: " + IsTestTransaction.toString());
		}
		out.println("Approval Status: " + ApprovalStatus);
		out.println("Payment Status: " + PaymentStatus);
		if (PaidDate != null) { 
			out.println("Paid: " + PaidDate);
		}


		out.println("CUSTOM FIELDS");
		if (Custom1 != null) {
			out.println("Custom 1----------------------------");
			out.println(Custom1);
		}
		if (Custom2 != null) {
			out.println("Custom 2----------------------------");
			out.println(Custom2);
		}
		if (Custom3 != null) {
			out.println("Custom 3----------------------------");
			out.println(Custom3);
		}
		if (Custom4 != null) {
			out.println("Custom 4----------------------------");
			out.println(Custom4);
		}
		if (Custom5 != null) {
			out.println("Custom 5----------------------------");
			out.println(Custom5);
		}
		if (Custom6 != null) {
			out.println("Custom 6----------------------------");
			out.println(Custom6);
		}
		if (Custom7 != null) {
			out.println("Custom 7----------------------------");
			out.println(Custom7);
		}
		if (Custom8 != null) {
			out.println("Custom 8----------------------------");
			out.println(Custom8);
		}
		if (Custom9 != null) {
			out.println("Custom 9----------------------------");
			out.println(Custom9);
		}
		if (Custom10 != null) {
			out.println("Custom 10----------------------------");
			out.println(Custom10);
		}
		if (Custom11 != null) {
			out.println("Custom 11----------------------------");
			out.println(Custom11);
		}
		if (Custom12 != null) {
			out.println(Custom12);
		}
		if (Custom13 != null) {
			out.println(Custom13);
		}
		if (Custom14 != null) {
			out.println(Custom14);
		}
		if (Custom15 != null) {
			out.println(Custom15);
		}
		if (Custom16 != null) {
			out.println(Custom16);
		}
		if (Custom17 != null) {
			out.println(Custom17);
		}
		if (Custom18 != null) {
			out.println(Custom18);
		}
		if (Custom19 != null) {
			out.println(Custom19);
		}
		if (Custom20 != null) {
			out.println(Custom20);
		}
		if (Custom21 != null) {
			out.println(Custom21);
		}
		if (Custom22 != null) {
			out.println(Custom22);
		}
		if (Custom23 != null) {
			out.println(Custom23);
		}
		if (Custom24 != null) {
			out.println(Custom24);
		}
		
		out.println("___________________________________________");
		out.println();
		


		if(LineItems != null) {
			out.println("Line Items");
			int count = LineItems.size();
			LineItem item = new LineItem();
			if (count >0 ) {
			 for (int index=0 ; index<count; index++) {
				item = LineItems.get(index);
				int itereation = index + 1;
				out.println("Line Item: " + itereation);
				out.println("-----------------------");
				item.display();
			 }
				out.println("End Line Items___________________________________________");
				
			}
		}
		
	}



	public String getID() {
		return ID;
	}



	public void setID(String iD) {
		ID = iD;
	}



	public String getApprovalStatus() {
		return ApprovalStatus;
	}



	public void setApprovalStatus(String approvalStatus) {
		ApprovalStatus = approvalStatus;
	}



	public String getBuyerCostCenter() {
		return BuyerCostCenter;
	}
	public void setBuyerCostCenter(String buyerCostCenter) {
		BuyerCostCenter = buyerCostCenter;
	}
	public double getCalculatedAmount() {
		return CalculatedAmount;
	}



	public void setCalculatedAmount(double calculatedAmount) {
		CalculatedAmount = calculatedAmount;
	}

	
	public String getCompanyID() {
		return CompanyID;
	}
	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}
	
	public String getCountryCode() {
		return CountryCode;
	}



	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}



	public String getCurrencyCode() {
		return CurrencyCode;
	}



	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}

	public String getCustom1() {
		return Custom1;
	}
	public void setCustom1(String custom1) {
		Custom1 = custom1;
	}
	public String getCustom2() {
		return Custom2;
	}
	public void setCustom2(String custom2) {
		Custom2 = custom2;
	}
	public String getCustom3() {
		return Custom3;
	}
	public void setCustom3(String custom3) {
		Custom3 = custom3;
	}
	public String getCustom4() {
		return Custom4;
	}
	public void setCustom4(String custom4) {
		Custom4 = custom4;
	}
	public String getCustom5() {
		return Custom5;
	}
	public void setCustom5(String custom5) {
		Custom5 = custom5;
	}
	public String getCustom6() {
		return Custom6;
	}
	public void setCustom6(String custom6) {
		Custom6 = custom6;
	}
	public String getCustom7() {
		return Custom7;
	}
	public void setCustom7(String custom7) {
		Custom7 = custom7;
	}
	public String getCustom8() {
		return Custom8;
	}
	public void setCustom8(String custom8) {
		Custom8 = custom8;
	}
	public String getCustom9() {
		return Custom9;
	}
	public void setCustom9(String custom9) {
		Custom9 = custom9;
	}
	public String getCustom10() {
		return Custom10;
	}
	public void setCustom10(String custom10) {
		Custom10 = custom10;
	}
	public String getCustom11() {
		return Custom11;
	}
	public void setCustom11(String custom11) {
		Custom11 = custom11;
	}
	public String getCustom12() {
		return Custom12;
	}
	public void setCustom12(String custom12) {
		Custom12 = custom12;
	}
	public String getCustom13() {
		return Custom13;
	}
	public void setCustom13(String custom13) {
		Custom13 = custom13;
	}
	public String getCustom14() {
		return Custom14;
	}
	public void setCustom14(String custom14) {
		Custom14 = custom14;
	}
	public String getCustom15() {
		return Custom15;
	}
	public void setCustom15(String custom15) {
		Custom15 = custom15;
	}
	public String getCustom16() {
		return Custom16;
	}
	public void setCustom16(String custom16) {
		Custom16 = custom16;
	}
	public String getCustom17() {
		return Custom17;
	}
	public void setCustom17(String custom17) {
		Custom17 = custom17;
	}
	public String getCustom18() {
		return Custom18;
	}
	public void setCustom18(String custom18) {
		Custom18 = custom18;
	}
	public String getCustom19() {
		return Custom19;
	}
	public void setCustom19(String custom19) {
		Custom19 = custom19;
	}
	public String getCustom20() {
		return Custom20;
	}
	public void setCustom20(String custom20) {
		Custom20 = custom20;
	}
	public String getCustom21() {
		return Custom21;
	}
	public void setCustom21(String custom21) {
		Custom21 = custom21;
	}
	public String getCustom22() {
		return Custom22;
	}
	public void setCustom22(String custom22) {
		Custom22 = custom22;
	}
	public String getCustom23() {
		return Custom23;
	}
	public void setCustom23(String custom23) {
		Custom23 = custom23;
	}
	public String getCustom24() {
		return Custom24;
	}
	public void setCustom24(String custom24) {
		Custom24 = custom24;
	}
	public void setOrgUnit1(String orgUnit1) {
		OrgUnit1 = orgUnit1;
	}
	public void setOrgUnit2(String orgUnit2) {
		OrgUnit2 = orgUnit2;
	}
	public void setOrgUnit3(String orgUnit3) {
		OrgUnit3 = orgUnit3;
	}
	public void setOrgUnit4(String orgUnit4) {
		OrgUnit4 = orgUnit4;
	}
	public void setOrgUnit5(String orgUnit5) {
		OrgUnit5 = orgUnit5;
	}
	public void setOrgUnit6(String orgUnit6) {
		OrgUnit6 = orgUnit6;
	}
	public String getDescription() {
		return Description;
	}
	
	public void setDescription(String description) {
		Description = description;
	}

	public String getCreatedByUsername() {
		return CreatedByUsername;
	}
	public void setCreatedByUsername(String createdByUsername) {
		CreatedByUsername = createdByUsername;
	}
	
	public String getExtractDate() {
		return ExtractDate;
	}
	public void setExtractDate(String extractDate) {
		ExtractDate = extractDate;
	}
	public String getEmployeeName() {
		return EmployeeName;
	}
	public void setEmployeeName(String employeeName) {
		EmployeeName = employeeName;
	}
	public String getEmployeeID() {
		return EmployeeID;
	}
	public void setEmployeeID(String employeeID) {
		EmployeeID = employeeID;
	}
	public String getEmployeeDisplayName() {
		return EmployeeDisplayName;
	}
	public void setEmployeeDisplayName(String employeeDisplayName) {
		EmployeeDisplayName = employeeDisplayName;
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
	public double getInvoiceAmount() {
		return InvoiceAmount;
	}



	public void setInvoiceAmount(double invoiceAmount) {
		InvoiceAmount = invoiceAmount;
	}



	public String getInvoiceDate() {
		return InvoiceDate;
	}



	public void setInvoiceDate(String invoiceDate) {
		InvoiceDate = invoiceDate;
	}



	public String getInvoiceNumber() {
		return InvoiceNumber;
	}



	public void setInvoiceNumber(String invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}



	public Boolean getIsPaymentRequestDeleted() {
		return IsPaymentRequestDeleted;
	}



	public void setIsPaymentRequestDeleted(Boolean isPaymentRequestDeleted) {
		IsPaymentRequestDeleted = isPaymentRequestDeleted;
	}



	public Boolean getIsTestTransaction() {
		return IsTestTransaction;
	}



	public void setIsTestTransaction(Boolean isTestTransaction) {
		IsTestTransaction = isTestTransaction;
	}



	public String getLedgerCode() {
		return LedgerCode;
	}



	public void setLedgerCode(String ledgerCode) {
		LedgerCode = ledgerCode;
	}



	public ArrayList<LineItem> getLineItems() {
		return LineItems;
	}
	public void setLineItems(ArrayList<LineItem> lineItems) {
		LineItems = lineItems;
	}
	
	public double getLineItemTotalAmount() {
		return LineItemTotalAmount;
	}



	public void setLineItemTotalAmount(double lineItemTotalAmount) {
		LineItemTotalAmount = lineItemTotalAmount;
	}



	public String getName() {
		return Name;
	}



	public void setName(String name) {
		Name = name;
	}



	public String getNotestoVendor() {
		return NotestoVendor;
	}



	public void setNotestoVendor(String notestoVendor) {
		NotestoVendor = notestoVendor;
	}



	public String getPaidDate() {
		return PaidDate;
	}



	public void setPaidDate(String paidDate) {
		PaidDate = paidDate;
	}



	public String getPaymentMethod() {
		return PaymentMethod;
	}



	public void setPaymentMethod(String paymentMethod) {
		PaymentMethod = paymentMethod;
	}



	public String getPaymentStatus() {
		return PaymentStatus;
	}



	public void setPaymentStatus(String paymentStatus) {
		PaymentStatus = paymentStatus;
	}



	public String getPurchaseOrderNumber() {
		return PurchaseOrderNumber;
	}



	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		PurchaseOrderNumber = purchaseOrderNumber;
	}



	public Byte[] getReceiptImage() {
		return ReceiptImage;
	}
	public void setReceiptImage(Byte[] receiptImage) {
		ReceiptImage = receiptImage;
	}
	public double getShippingAmount() {
		return ShippingAmount;
	}



	public void setShippingAmount(double shippingAmount) {
		ShippingAmount = shippingAmount;
	}



	public double getTotalApprovedAmount() {
		return TotalApprovedAmount;
	}



	public void setTotalApprovedAmount(double totalApprovedAmount) {
		TotalApprovedAmount = totalApprovedAmount;
	}



	public String getVendorDescription() {
		return VendorDescription;
	}



	public void setVendorDescription(String vendorDescription) {
		VendorDescription = vendorDescription;
	}
	public Address getVendorRemitAddress() {
		return VendorRemitAddress;
	}
	public void setVendorRemitAddress(Address vendorRemitAddress) {
		VendorRemitAddress = vendorRemitAddress;
	}
	public String getOrgUnit1() {
		return OrgUnit1;
	}
	public String getOrgUnit2() {
		return OrgUnit2;
	}
	public String getOrgUnit3() {
		return OrgUnit3;
	}
	public String getOrgUnit4() {
		return OrgUnit4;
	}
	public String getOrgUnit5() {
		return OrgUnit5;
	}
	public String getOrgUnit6() {
		return OrgUnit6;
	}


}


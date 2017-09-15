/**
 * 
 */
package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * base class for the ExpenseEntry object
 * 
 * The ExpenseEntry object is a child element of the ReportDetails object
 *
 */

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ExpenseEntry {
	
	String ReportEntryID;

	String ExpenseTypeID;

	String ExpenseTypeName;

	String SpendCategory;

	String PaymentTypeCode;

	String PaymentTypeName;

	Date TransactionDate;

	String TransactionCurrencyName;
	
	String ExchangeRate;
	
	double TransactionAmount;
	
	double PostedAmount;
	
	double ApprovedAmount;
	
	String BusinessPurpose;
	
	String VendorDescription;
	
	String LocationName;
	
	String LocationSubdivision;
	
	String LocationCountry;
	
	CustomField OrgUnit1;
	
	CustomField OrgUnit2;
	
	CustomField OrgUnit3;
	
	CustomField OrgUnit4;
	
	CustomField OrgUnit5;
	
	CustomField OrgUnit6;
	
	CustomField Custom1;
	
	CustomField Custom2;
	
	CustomField Custom3;
	
	CustomField Custom4;
	
	CustomField Custom5;
	
	CustomField Custom6;
	
	CustomField Custom7;
	
	CustomField Custom8;
	
	CustomField Custom9;
	
	CustomField Custom10;
	
	CustomField Custom11;
	
	CustomField Custom12;
	
	CustomField Custom13;
	
	CustomField Custom14;
	
	CustomField Custom15;
	
	CustomField Custom16;
	
	CustomField Custom17;
	
	CustomField Custom18;
	
	CustomField Custom19;
	
	CustomField Custom20;
	
	CustomField Custom21;
	
	CustomField Custom22;
	CustomField Custom23;
	CustomField Custom24;
	CustomField Custom25;
	CustomField Custom26;
	CustomField Custom27;
	CustomField Custom28;
	CustomField Custom29;
	CustomField Custom30;
	
	CustomField Custom31;
	
	CustomField Custom32;
	
	CustomField Custom33;
	
	CustomField Custom34;
	
	CustomField Custom35;
	
	CustomField Custom36;
	
	CustomField Custom37;
	
	CustomField Custom38;
	
	CustomField Custom39;
	
	CustomField Custom40;
	
	String FormID;
	
	String EntryImageID;
	
	String HasVat;
	
	String HasComments;
	
	String CommentCount;
	
	String IsItemized;
	
	String HasExceptions;
	
	String IsPersonal;
	
	String HasAttendees;
	
	String HasAllocation;
	
	String IsCreditCardCharge;
	
	String IsPersonalCardCharge;
	
	String ExpensePay;
	
	String ReceiptRequired;
	
	String ImageRequired;

	String E_ReceiptID;
	
	Date LastModified;
	
	@XmlElementWrapper(name = "ItemizationsList")
	@XmlElement(name = "Itemization")
	ArrayList<Itemization> items;
	
	
	
	public ExpenseEntry () {
		// no args constructor
	}
	
	public Expense entrytoExpense (){
		Expense expense = new Expense();
		
		
		expense.setEntry_ID(this.getReportEntryID());
		expense.setExpenseTypeName(this.getExpenseTypeName());
		expense.setPaymentTypeName(this.getPaymentTypeName());
		expense.setTransactionDate(this.getTransactionDate());
		expense.setOriginalAmount(this.getTransactionAmount());
		expense.setOriginalCurrency(this.getTransactionCurrencyName());
		expense.setApprovedAmount(this.getApprovedAmount());
		expense.setPostedAmount(this.getPostedAmount());
		expense.setMerchantName(this.getVendorDescription());
		expense.setLocationDisplayName(this.getLocationName());
		expense.setCustom1(this.getCustom1());
		expense.setCustom2(this.getCustom2());
		expense.setCustom3(this.getCustom2());
		expense.setCustom4(this.getCustom4());
		expense.setCustom5(this.getCustom5());
		expense.setCustom6(this.getCustom6());
		expense.setCustom7(this.getCustom7());
		expense.setCustom7(this.getCustom7());
		expense.setCustom8(this.getCustom8());
		expense.setCustom9(this.getCustom9());
		expense.setItems(this.getItems());
		
		return expense;
	}
	@XmlElement
	public String getReportEntryID() {
		return ReportEntryID;
	}
	public void setReportEntryID(String reportEntryID) {
		ReportEntryID = reportEntryID;
	}
	public String getExpenseTypeID() {
		return ExpenseTypeID;
	}
	@XmlElement
	public void setExpenseTypeID(String expenseTypeID) {
		ExpenseTypeID = expenseTypeID;
	}
	@XmlElement
	public String getExpenseTypeName() {
		return ExpenseTypeName;
	}
	public void setExpenseTypeName(String expenseTypeName) {
		ExpenseTypeName = expenseTypeName;
	}
	public String getSpendCategory() {
		return SpendCategory;
	}
	@XmlElement
	public void setSpendCategory(String spendCategory) {
		SpendCategory = spendCategory;
	}
	public String getPaymentTypeCode() {
		return PaymentTypeCode;
	}
	@XmlElement
	public void setPaymentTypeCode(String paymentTypeCode) {
		PaymentTypeCode = paymentTypeCode;
	}
	@XmlElement
	public String getPaymentTypeName() {
		return PaymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		PaymentTypeName = paymentTypeName;
	}
	public Date getTransactionDate() {
		return TransactionDate;
	}
	@XmlElement
	public void setTransactionDate(Date transactionDate) {
		TransactionDate = transactionDate;
	}
	public String getTransactionCurrencyName() {
		return TransactionCurrencyName;
	}
	@XmlElement
	public void setTransactionCurrencyName(String transactionCurrencyName) {
		TransactionCurrencyName = transactionCurrencyName;
	}
	public String getExchangeRate() {
		return ExchangeRate;
	}
	@XmlElement
	public void setExchangeRate(String exchangeRate) {
		ExchangeRate = exchangeRate;
	}
	public double getTransactionAmount() {
		return TransactionAmount;
	}
	@XmlElement
	public void setTransactionAmount(double transactionAmount) {
		TransactionAmount = transactionAmount;
	}
	public double getPostedAmount() {
		return PostedAmount;
	}
	@XmlElement
	public void setPostedAmount(double postedAmount) {
		PostedAmount = postedAmount;
	}
	public double getApprovedAmount() {
		return ApprovedAmount;
	}
	@XmlElement
	public void setApprovedAmount(double approvedAmount) {
		ApprovedAmount = approvedAmount;
	}
	public String getBusinessPurpose() {
		return BusinessPurpose;
	}
	@XmlElement
	public void setBusinessPurpose(String businessPurpose) {
		BusinessPurpose = businessPurpose;
	}
	public String getVendorDescription() {
		return VendorDescription;
	}
	@XmlElement
	public void setVendorDescription(String vendorDescription) {
		VendorDescription = vendorDescription;
	}
	public String getLocationName() {
		return LocationName;
	}
	@XmlElement
	public void setLocationName(String locationName) {
		LocationName = locationName;
	}
	public String getLocationSubdivision() {
		return LocationSubdivision;
	}
	@XmlElement
	public void setLocationSubdivision(String locationSubdivision) {
		LocationSubdivision = locationSubdivision;
	}
	public String getLocationCountry() {
		return LocationCountry;
	}
	@XmlElement
	public void setLocationCountry(String locationCountry) {
		LocationCountry = locationCountry;
	}
	public CustomField getOrgUnit1() {
		return OrgUnit1;
	}
	@XmlElement
	public void setOrgUnit1(CustomField orgUnit1) {
		OrgUnit1 = orgUnit1;
	}
	public CustomField getOrgUnit2() {
		return OrgUnit2;
	}
	@XmlElement
	public void setOrgUnit2(CustomField orgUnit2) {
		OrgUnit2 = orgUnit2;
	}
	public CustomField getOrgUnit3() {
		return OrgUnit3;
	}
	@XmlElement
	public void setOrgUnit3(CustomField orgUnit3) {
		OrgUnit3 = orgUnit3;
	}
	public CustomField getOrgUnit4() {
		return OrgUnit4;
	}
	@XmlElement
	public void setOrgUnit4(CustomField orgUnit4) {
		OrgUnit4 = orgUnit4;
	}
	public CustomField getOrgUnit5() {
		return OrgUnit5;
	}
	@XmlElement
	public void setOrgUnit5(CustomField orgUnit5) {
		OrgUnit5 = orgUnit5;
	}
	public CustomField getOrgUnit6() {
		return OrgUnit6;
	}
	@XmlElement
	public void setOrgUnit6(CustomField orgUnit6) {
		OrgUnit6 = orgUnit6;
	}
	public CustomField getCustom1() {
		return Custom1;
	}
	@XmlElement
	public void setCustom1(CustomField custom1) {
		Custom1 = custom1;
	}
	public CustomField getCustom2() {
		return Custom2;
	}
	@XmlElement
	public void setCustom2(CustomField custom2) {
		Custom2 = custom2;
	}
	public CustomField getCustom3() {
		return Custom3;
	}
	@XmlElement
	public void setCustom3(CustomField custom3) {
		Custom3 = custom3;
	}
	public CustomField getCustom4() {
		return Custom4;
	}
	@XmlElement
	public void setCustom4(CustomField custom4) {
		Custom4 = custom4;
	}
	public CustomField getCustom5() {
		return Custom5;
	}
	@XmlElement
	public void setCustom5(CustomField custom5) {
		Custom5 = custom5;
	}
	
	public CustomField getCustom6() {
		return Custom6;
	}
	@XmlElement
	public void setCustom6(CustomField custom6) {
		Custom6 = custom6;
	}

	public CustomField getCustom7() {
		return Custom7;
	}
	@XmlElement
	public void setCustom7(CustomField custom7) {
		Custom7 = custom7;
	}

	public CustomField getCustom8() {
		return Custom8;
	}
	@XmlElement
	public void setCustom8(CustomField custom8) {
		Custom8 = custom8;
	}
	public CustomField getCustom9() {
		return Custom9;
	}
	@XmlElement
	public void setCustom9(CustomField custom9) {
		Custom9 = custom9;
	}
	public CustomField getCustom10() {
		return Custom10;
	}
	@XmlElement
	public void setCustom10(CustomField custom10) {
		Custom10 = custom10;
	}

	public CustomField getCustom11() {
		return Custom11;
	}
	@XmlElement
	public void setCustom11(CustomField custom11) {
		Custom11 = custom11;
	}
	
	public CustomField getCustom12() {
		return Custom12;
	}
	@XmlElement
	public void setCustom12(CustomField custom12) {
		Custom12 = custom12;
	}
	public CustomField getCustom13() {
		return Custom13;
	}
	@XmlElement
	public void setCustom13(CustomField custom13) {
		Custom13 = custom13;
	}
	public CustomField getCustom14() {
		return Custom14;
	}
	@XmlElement
	public void setCustom14(CustomField custom14) {
		Custom14 = custom14;
	}
	public CustomField getCustom15() {
		return Custom15;
	}
	@XmlElement
	public void setCustom15(CustomField custom15) {
		Custom15 = custom15;
	}
	public CustomField getCustom16() {
		return Custom16;
	}
	@XmlElement
	public void setCustom16(CustomField custom16) {
		Custom16 = custom16;
	}
	public CustomField getCustom17() {
		return Custom17;
	}
	@XmlElement
	public void setCustom17(CustomField custom17) {
		Custom17 = custom17;
	}
	public CustomField getCustom18() {
		return Custom18;
	}
	@XmlElement
	public void setCustom18(CustomField custom18) {
		Custom18 = custom18;
	}
	public CustomField getCustom19() {
		return Custom19;
	}
	@XmlElement
	public void setCustom19(CustomField custom19) {
		Custom19 = custom19;
	}
	public CustomField getCustom20() {
		return Custom20;
	}
	@XmlElement
	public void setCustom20(CustomField custom20) {
		Custom20 = custom20;
	}
	public CustomField getCustom21() {
		return Custom21;
	}
	@XmlElement
	public void setCustom21(CustomField custom21) {
		Custom21 = custom21;
	}
	public CustomField getCustom22() {
		return Custom22;
	}
	@XmlElement
	public void setCustom22(CustomField custom22) {
		Custom22 = custom22;
	}
	public CustomField getCustom23() {
		return Custom23;
	}
	@XmlElement
	public void setCustom23(CustomField custom23) {
		Custom23 = custom23;
	}
	public CustomField getCustom24() {
		return Custom24;
	}
	@XmlElement
	public void setCustom24(CustomField custom24) {
		Custom24 = custom24;
	}
	public CustomField getCustom25() {
		return Custom25;
	}
	@XmlElement
	public void setCustom25(CustomField custom25) {
		Custom25 = custom25;
	}
	public CustomField getCustom26() {
		return Custom26;
	}
	@XmlElement
	public void setCustom26(CustomField custom26) {
		Custom26 = custom26;
	}
	public CustomField getCustom27() {
		return Custom27;
	}
	@XmlElement
	public void setCustom27(CustomField custom27) {
		Custom27 = custom27;
	}
	public CustomField getCustom28() {
		return Custom28;
	}
	@XmlElement
	public void setCustom28(CustomField custom28) {
		Custom28 = custom28;
	}
	public CustomField getCustom29() {
		return Custom29;
	}
	@XmlElement
	public void setCustom29(CustomField custom29) {
		Custom29 = custom29;
	}
	public CustomField getCustom30() {
		return Custom30;
	}
	@XmlElement
	public void setCustom30(CustomField custom30) {
		Custom30 = custom30;
	}
	public CustomField getCustom31() {
		return Custom31;
	}
	@XmlElement
	public void setCustom31(CustomField custom31) {
		Custom31 = custom31;
	}
	public CustomField getCustom32() {
		return Custom32;
	}
	@XmlElement
	public void setCustom32(CustomField custom32) {
		Custom32 = custom32;
	}
	public CustomField getCustom33() {
		return Custom33;
	}
	@XmlElement
	public void setCustom33(CustomField custom33) {
		Custom33 = custom33;
	}
	public CustomField getCustom34() {
		return Custom34;
	}
	@XmlElement
	public void setCustom34(CustomField custom34) {
		Custom34 = custom34;
	}
	public CustomField getCustom35() {
		return Custom35;
	}
	@XmlElement
	public void setCustom35(CustomField custom35) {
		Custom35 = custom35;
	}
	public CustomField getCustom36() {
		return Custom36;
	}
	@XmlElement
	public void setCustom36(CustomField custom36) {
		Custom36 = custom36;
	}
	public CustomField getCustom37() {
		return Custom37;
	}
	@XmlElement
	public void setCustom37(CustomField custom37) {
		Custom37 = custom37;
	}
	public CustomField getCustom38() {
		return Custom38;
	}
	@XmlElement
	public void setCustom38(CustomField custom38) {
		Custom38 = custom38;
	}
	public CustomField getCustom39() {
		return Custom39;
	}
	@XmlElement
	public void setCustom39(CustomField custom39) {
		Custom39 = custom39;
	}
	public CustomField getCustom40() {
		return Custom40;
	}
	@XmlElement
	public void setCustom40(CustomField custom40) {
		Custom40 = custom40;
	}
	public String getFormID() {
		return FormID;
	}
	@XmlElement
	public void setFormID(String formID) {
		FormID = formID;
	}
	public String getEntryImageID() {
		return EntryImageID;
	}
	@XmlElement
	public void setEntryImageID(String entryImageID) {
		EntryImageID = entryImageID;
	}
	public String getHasVat() {
		return HasVat;
	}
	@XmlElement
	public void setHasVat(String hasVat) {
		HasVat = hasVat;
	}
	public String getHasComments() {
		return HasComments;
	}
	@XmlElement
	public void setHasComments(String hasComments) {
		HasComments = hasComments;
	}
	public String getCommentCount() {
		return CommentCount;
	}
	@XmlElement
	public void setCommentCount(String commentCount) {
		CommentCount = commentCount;
	}
	public String getIsItemized() {
		return IsItemized;
	}
	@XmlElement
	public void setIsItemized(String isItemized) {
		IsItemized = isItemized;
	}
	public String getHasExceptions() {
		return HasExceptions;
	}
	@XmlElement
	public void setHasExceptions(String hasExceptions) {
		HasExceptions = hasExceptions;
	}
	public String getIsPersonal() {
		return IsPersonal;
	}
	@XmlElement
	public void setIsPersonal(String isPersonal) {
		IsPersonal = isPersonal;
	}
	public String getHasAttendees() {
		return HasAttendees;
	}
	@XmlElement
	public void setHasAttendees(String hasAttendees) {
		HasAttendees = hasAttendees;
	}
	public String getHasAllocation() {
		return HasAllocation;
	}
	@XmlElement
	public void setHasAllocation(String hasAllocation) {
		HasAllocation = hasAllocation;
	}
	public String getIsCreditCardCharge() {
		return IsCreditCardCharge;
	}
	@XmlElement
	public void setIsCreditCardCharge(String isCreditCardCharge) {
		IsCreditCardCharge = isCreditCardCharge;
	}
	public String getIsPersonalCardCharge() {
		return IsPersonalCardCharge;
	}
	@XmlElement
	public void setIsPersonalCardCharge(String isPersonalCardCharge) {
		IsPersonalCardCharge = isPersonalCardCharge;
	}
	public String getReceiptRequired() {
		return ReceiptRequired;
	}
	@XmlElement
	public void setReceiptRequired(String receiptRequired) {
		ReceiptRequired = receiptRequired;
	}
	public String getImageRequired() {
		return ImageRequired;
	}
	@XmlElement
	public void setImageRequired(String imageRequired) {
		ImageRequired = imageRequired;
	}
	public String getE_ReceiptID() {
		return E_ReceiptID;
	}
	@XmlElement
	public void setE_ReceiptID(String e_ReceiptID) {
		E_ReceiptID = e_ReceiptID;
	}
	public Date getLastModified() {
		return LastModified;
	}
	@XmlElement
	public void setLastModified(Date lastModified) {
		LastModified = lastModified;
	}

	public ArrayList<Itemization> getItems() {
		return items;
	}

	public void setItems(ArrayList<Itemization> items) {
		this.items = items;
	}
	public String getExpensePay() {
		return ExpensePay;
	}
	@XmlElement
	public void setExpensePay(String expensePay) {
		ExpensePay = expensePay;
	}


}

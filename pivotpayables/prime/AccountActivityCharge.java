package com.pivotpayables.prime;
/**
 * 
 */

/**
 * @author John Toman
 * 6/21/2016
 * Base class for Account Activity Charges
 * Includes addition for VAT field
 * 
 * Moved doctoAccountActivityCharge
 *
 */
import static java.lang.System.out;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.GUID;

public class AccountActivityCharge {// the AccountActivityCharge class includes all the elements that appear in the Statement of Reimbursable Expenses
	// There is an AccountActivityCharge for every Itemization (or, Allocation for organziations that use Allocations) to record amounts charged (billed) to Account/Activity/Phase/Task
	// 
	private String ID;
	private String ReportID;
	private String EntryID;
	private String AccountID;
	private String AccountName;// the account being billed (charged)
	private String ActivityID;
	private String ActivityName;// the activity associated to this charge
	private String PhaseID;
	private String PhaseName;// the phase associated to this charge
	private String TaskID;
	private String TaskName;// the task associated to this charge
	private String EmployeeDisplayName;// the employee who incurred the expense
	private String ExpenseID;// the GUID for the parent, expense entry this charge's Itemization is associated
	private String ExpenseTypeName;// the expense type name for the parent, expense entry
	private Date ExpenseDate;// the date for the parent, expense entry
	private String OriginalCurrency;// the currency paid to merchant, and that appears on the receipt
	private double ExpenseAmount;// the amount of the expense in original currency
	private String PostedCurrency;// the currency of the expense in posted currency
	private String BillingCurrency;// the currency the account is billed (not necessarily the PostedCurrency...instead it is the currency of the account's AP system)
	double ExpenseBilledAmount;//the original amount converted into the currency the account is billed (not necessarily the ApprovedAmount)
	private String ItemizationID;// the GUID for the Itemization assoicated to this charge
	private String ItemTypeName;// the item's expense type name
	private Date ItemDate;// the item's date
	private double ItemAmount;// the item's amount in original currency
	private double ItemPostedAmount;// the item's amount in posted currency
	private double TaxAmount;// the item's amount in tax original currency
	private double ReclaimAmount;// the item's tax reclaim amount in original currency
	private double TaxPostedAmount;// the item's amount in tax posted currency
	private double ReclaimPostedAmount;// the item's tax reclaim amount in posted currency
	private double ChargeAmount;// the amount of the charge in billing currency
	private Date PaidDate;// when the expense report that contains the parent expense entry was paid


	public AccountActivityCharge () {};// no args constructor
	
	public AccountActivityCharge(String accountid, String accountname, String activityid, String activityname, 
			String empname, String expenseid, String expensetypename, Date expensedate, double expenseamount, String ocur, double billedamount, String bcur, 
			String itemizationid, String itemtype, Date itemdate, double itemamount, double chargeamount, Date paiddate){//constructor
		ID = GUID.getGUID(5);// create the GUID for the charge
		AccountID = accountid;
		AccountName = accountname;
		ActivityID = activityid;
		ActivityName = activityname;
		EmployeeDisplayName = empname;
		ExpenseID = expenseid;
		ExpenseTypeName = expensetypename;
		ExpenseDate = expensedate;
		ExpenseAmount = expenseamount;
		OriginalCurrency = ocur;
		ExpenseBilledAmount = billedamount;
		BillingCurrency = bcur;
		ItemizationID = itemizationid;
		ItemTypeName = itemtype;
		ItemDate = itemdate;
		ItemAmount = itemamount;
		ChargeAmount = chargeamount;
		PaidDate = paiddate;


	}
	public AccountActivityCharge(String accountid, String accountname, String activityid, String activityname, 
			String empname, String expenseid, String expensetypename, Date expensedate, double expenseamount, String ocur, double billedamount, String bcur, 
			String itemizationid, String itemtype, Date itemdate, double itemamount, double chargeamount, Date paiddate, String id){ // constructor, use when converting document to object
		
		this(accountid, accountname, activityid, activityname, empname, expenseid, expensetypename, expensedate, expenseamount,ocur, billedamount, bcur, 
			itemizationid, itemtype, itemdate, itemamount, chargeamount, paiddate);
		ID = id;

	}
	public void display () {//method to display the company in the console
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Locale locale  = new Locale("en", "US");
		String pattern = "###.##";

		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		out.println("ID: " + ID);
		out.println("Report ID: " + ReportID);
		out.println("Entry ID: " + EntryID);
		out.println("Account ID: " + AccountID);
		out.println("Account Name: " + AccountName);
		out.println("Activity ID: " + ActivityID);
		out.println("Activity Name: " + ActivityName);
		out.println("Phase ID: " + PhaseID);
		out.println("Phase Name: " + PhaseName);
		out.println("Task ID: " + TaskID);
		out.println("Task Name: " + TaskName);
		out.println("Employee Display Name: " + EmployeeDisplayName);
		out.println("Expense ID: " + ExpenseID);
		out.println("Expense: Expense Type Name: " + ExpenseTypeName);
		String strDate = sdf.format(ExpenseDate);
		out.println("Expense: Date: " + strDate);
		out.println("Expense: Amount: " + decimalFormat.format(ExpenseAmount) + " "+ OriginalCurrency);
		out.println("Expense: Billed Amount: " + decimalFormat.format(ExpenseBilledAmount) + " "+ BillingCurrency);
		out.println("Itemization ID: " + ItemizationID);
		out.println("Item: Expense Type Name: " + ItemTypeName);
		strDate = sdf.format(ItemDate);
		out.println("Date: " + strDate);
		out.println("Amount: " + decimalFormat.format(ItemAmount) + " "+ OriginalCurrency);
		out.println("Posted Amount: " + decimalFormat.format(ItemPostedAmount) + " "+ PostedCurrency);
		out.println("Charge Amount: " + decimalFormat.format(ChargeAmount) + " "+ BillingCurrency);
		out.println("Tax Amount: " + decimalFormat.format(TaxAmount) + " "+ OriginalCurrency);
		out.println("Reclaim Amount: " + decimalFormat.format(ReclaimAmount) + " "+ OriginalCurrency);
		out.println("Tax Posted Amount: " + decimalFormat.format(TaxPostedAmount) + " "+ PostedCurrency);
		out.println("Reclaim Posted Amount: " + decimalFormat.format(ReclaimPostedAmount) + " "+ PostedCurrency);
		if (PaidDate != null) {
			strDate = sdf.format(PaidDate);
			out.println("Paid Date: " + strDate);
		} else {
			out.println("Paid Date: ");
		}// if block
	}

	public BasicDBObject getDocument () {// covert an AccountActivityCharge object into an AccountActivityCharge document
		BasicDBObject myCharge = new BasicDBObject("ID",this.ID);	
		myCharge.append("ReportID", this.ReportID);
		myCharge.append("EntryID", this.EntryID);
		myCharge.append("AccountID", this.AccountID);
		myCharge.append("AccountName", this.AccountName);
		myCharge.append("ActivityID", this.ActivityID);
		myCharge.append("ActivityName", this.ActivityName);
		myCharge.append("PhaseID", this.PhaseID);
		myCharge.append("PhaseName", this.PhaseName);
		myCharge.append("TaskID", this.TaskID);
		myCharge.append("TaskName", this.TaskName);
		myCharge.append("EmployeeDisplayName", this.EmployeeDisplayName);
		myCharge.append("ExpenseID",this.ExpenseID);
		myCharge.append("ExpenseTypeName",this.ExpenseTypeName);
		myCharge.append("ExpenseDate",this.ExpenseDate);
		myCharge.append("ExpenseAmount",this.ExpenseAmount);
		myCharge.append("OriginalCurrency",this.OriginalCurrency);
		myCharge.append("PostedCurrency",this.PostedCurrency);
		myCharge.append("ExpenseBilledAmount", this.ExpenseBilledAmount);
		myCharge.append("BillingCurrency",this.BillingCurrency);
		myCharge.append("ItemizationID",this.ItemizationID);
		myCharge.append("ItemTypeName",this.ItemTypeName);
		myCharge.append("ItemDate",this.ItemDate);
		myCharge.append("ItemAmount",this.ItemAmount);
		myCharge.append("ItemPostedAmount",this.ItemPostedAmount);
		myCharge.append("ChargeAmount",this.ChargeAmount);
		myCharge.append("TaxAmount",this.TaxAmount);
		myCharge.append("ReclaimAmount",this.ReclaimAmount);
		myCharge.append("TaxPostedAmount",this.TaxPostedAmount);
		myCharge.append("ReclaimPostedAmount",this.ReclaimPostedAmount);
		myCharge.append("PaidDate",this.PaidDate);
		return myCharge;
	}
	public AccountActivityCharge doctoCharge (DBObject doc) {// convert an AccountActivityCharge document into an AccountActivityCharge object
		String ID= doc.get("ID").toString();
		String AccountID = "";
		String AccountName="";
		if (doc.get("AccountID") != null) {
			AccountID = doc.get("AccountID").toString();
			AccountName = doc.get("AccountName").toString();
		}	
		String ActivityID ="";
		String ActivityName = "";
		String PhaseName = "";
		String PhaseID="";
		String TaskID = "";;
		String TaskName = "";
		if (doc.get("ActivityID") != null) {
			ActivityID = doc.get("ActivityID").toString();
			ActivityName = doc.get("ActivityName").toString();
		}		
		if (doc.get("PhaseName") != null){
			PhaseName = doc.get("PhaseName").toString();
			PhaseID = doc.get("PhaseID").toString();
		}
		if (doc.get("TaskID") != null) {
			TaskID = doc.get("TaskID").toString();
			TaskName = doc.get("TaskName").toString();
		}
		String EmployeeDisplayName = doc.get("EmployeeDisplayName").toString();
		String ExpenseID = doc.get("ExpenseID").toString();
		String ExpenseTypeName = doc.get("ExpenseTypeName").toString();
		Date ExpenseDate = (Date)doc.get("ExpenseDate");
		double ExpenseAmount = Double.parseDouble(doc.get("ExpenseAmount").toString());
		String OriginalCurrency = doc.get("OriginalCurrency").toString();
		double ExpenseBilledAmount = Double.parseDouble(doc.get("ExpenseBilledAmount").toString());
		String BillingCurrency = doc.get("BillingCurrency").toString();
		String ItemTypeName = doc.get("ItemTypeName").toString();
		Date ItemDate = (Date)doc.get("ItemDate");
		double ItemAmount = Double.parseDouble(doc.get("ItemAmount").toString());
		double ChargeAmount = Double.parseDouble(doc.get("ChargeAmount").toString());
		Date PaidDate = (Date)doc.get("PaidDate");
		
		AccountActivityCharge myCharge = new AccountActivityCharge(AccountID, AccountName, ActivityID, ActivityName, 
				EmployeeDisplayName, ExpenseID, ExpenseTypeName, ExpenseDate, ExpenseAmount, OriginalCurrency, ExpenseBilledAmount, BillingCurrency, 
				ItemizationID, ItemTypeName, ItemDate, ItemAmount, ChargeAmount, PaidDate, ID);
		myCharge.setReportID(doc.get("ReportID").toString());
		myCharge.setEntryID(doc.get("EntryID").toString());
		myCharge.setPostedCurrency(doc.get("PostedCurrency").toString());
		myCharge.setItemPostedAmount(Double.parseDouble(doc.get("ItemPostedAmount").toString()));
		myCharge.setPhaseID(PhaseID);
		myCharge.setPhaseName(PhaseName);
		myCharge.setTaskID(TaskID);
		myCharge.setTaskName(TaskName);
		
		return myCharge;
		

	}
	public String getID() {
		return ID;
	}
	

	public String getReportID() {
		return ReportID;
	}

	public void setReportID(String reportID) {
		ReportID = reportID;
	}
	public String getEntryID() {
		return EntryID;
	}

	public void setEntryID(String entryID) {
		EntryID = entryID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getAccountID() {
		return AccountID;
	}

	public void setAccountID(String accountID) {
		AccountID = accountID;
	}

	public String getAccountName() {
		return AccountName;
	}

	public void setAccountName(String accountName) {
		AccountName = accountName;
	}

	public String getActivityID() {
		return ActivityID;
	}

	public void setActivityID(String activityID) {
		ActivityID = activityID;
	}

	public String getActivityName() {
		return ActivityName;
	}

	public void setActivityName(String activityName) {
		ActivityName = activityName;
	}

	public String getEmployeeDisplayName() {
		return EmployeeDisplayName;
	}

	public void setEmployeeDisplayName(String employeeDisplayName) {
		EmployeeDisplayName = employeeDisplayName;
	}

	public String getExpenseID() {
		return ExpenseID;
	}

	public void setExpenseID(String expenseID) {
		ExpenseID = expenseID;
	}

	public String getExpenseTypeName() {
		return ExpenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		ExpenseTypeName = expenseTypeName;
	}

	public Date getExpenseDate() {
		return ExpenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		ExpenseDate = expenseDate;
	}

	public String getOriginalCurrency() {
		return OriginalCurrency;
	}

	public void setOriginalCurrency(String originalCurrency) {
		OriginalCurrency = originalCurrency;
	}

	public double getExpenseAmount() {
		return ExpenseAmount;
	}

	public void setExpenseAmount(double expenseAmount) {
		ExpenseAmount = expenseAmount;
	}

	public String getBillingCurrency() {
		return BillingCurrency;
	}

	public void setBillingCurrency(String billingCurrency) {
		BillingCurrency = billingCurrency;
	}

	public double getExpenseBilledAmount() {
		return ExpenseBilledAmount;
	}

	public void setExpenseBilledAmount(double expenseBilledAmount) {
		ExpenseBilledAmount = expenseBilledAmount;
	}

	public String getItemizationID() {
		return ItemizationID;
	}

	public void setItemizationID(String itemizationID) {
		ItemizationID = itemizationID;
	}

	public String getItemTypeName() {
		return ItemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		ItemTypeName = itemTypeName;
	}

	public Date getItemDate() {
		return ItemDate;
	}

	public void setItemDate(Date itemDate) {
		ItemDate = itemDate;
	}

	public double getItemAmount() {
		return ItemAmount;
	}

	public void setItemAmount(double itemAmount) {
		ItemAmount = itemAmount;
	}

	public double getChargeAmount() {
		return ChargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		ChargeAmount = chargeAmount;
	}

	public Date getPaidDate() {
		return PaidDate;
	}

	public void setPaidDate(Date paidDate) {
		PaidDate = paidDate;
	}

	public String getPhaseID() {
		return PhaseID;
	}

	public void setPhaseID(String phaseID) {
		PhaseID = phaseID;
	}

	public String getPhaseName() {
		return PhaseName;
	}

	public void setPhaseName(String phaseName) {
		PhaseName = phaseName;
	}

	public String getTaskID() {
		return TaskID;
	}

	public void setTaskID(String taskID) {
		TaskID = taskID;
	}

	public String getTaskName() {
		return TaskName;
	}

	public void setTaskName(String taskName) {
		TaskName = taskName;
	}

	public double getItemPostedAmount() {
		return ItemPostedAmount;
	}

	

	public double getTaxPostedAmount() {
		return TaxPostedAmount;
	}

	public void setItemPostedAmount(double itemPostedAmount) {
		ItemPostedAmount = itemPostedAmount;
	}

	public void setTaxPostedAmount(double itemTaxPostedAmount) {
		TaxPostedAmount = itemTaxPostedAmount;
	}

	public double getReclaimPostedAmount() {
		return ReclaimPostedAmount;
	}

	public void setReclaimPostedAmount(double itemReclaimPostedAmount) {
		ReclaimPostedAmount = itemReclaimPostedAmount;
	}

	public double getTaxAmount() {
		return TaxAmount;
	}

	public void setTaxAmount(double itemTaxAmount) {
		TaxAmount = itemTaxAmount;
	}

	public double getReclaimAmount() {
		return ReclaimAmount;
	}

	public void setReclaimAmount(double itemReclaimAmount) {
		ReclaimAmount = itemReclaimAmount;
	}

	public String getPostedCurrency() {
		return PostedCurrency;
	}

	public void setPostedCurrency(String postedCurrency) {
		PostedCurrency = postedCurrency;
	}
	
	
	
}

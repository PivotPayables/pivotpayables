package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 4/30/2015
 * Base class for a ConcurUser
 *
 */
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


import static java.lang.System.out;
@XmlRootElement(name = "UserProfile")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConcurUser {
	//@XmlElement
    String FirstName;
	//@XmlElement
    String LastName;
	//@XmlElement
    String Mi;
	//@XmlElement
	String LoginId;
	//@XmlElement
	String Password; 
	//@XmlElement
    String EmailAddress;
	//@XmlElement
    String EmpId;
	//@XmlElement
	String LocaleName; 
	//@XmlElement
	String LedgerKey; 
	//@XmlElement
    String CtryCode;
	//@XmlElement
    String CrnKey;
	//@XmlElement
    String CtrySubCode;
	//@XmlElement
	String ExpenseUser; 
	//@XmlElement
	String ExpenseApprover; 
	//@XmlElement
	String TripUser; 
	//@XmlElement
	String InvoiceUser; 
	//@XmlElement
	String InvoiceApprover;
	//@XmlElement
	String ExpenseApproverEmployeeID;
	//@XmlElement
    String IsTestEmp;
	//@XmlElement
	String Active;
	//@XmlElement
	String Custom21;


	public ConcurUser(){}// no args constructor
	public void display() {
		if (LoginId != null) {
			out.println("Login ID: " + LoginId);
		}
		if (Password != null) {
			out.println("Password: " + Password);
		}
		if (FirstName != null) {
			out.println("First Name: " + FirstName);
		}
		if (LastName != null) {
			out.println("LastName: " + LastName);
		}
		if (Mi != null) {
			out.println("Middle Initial: " + Mi);
		}
		if (EmailAddress != null) {
			out.println("Email: " + EmailAddress);
		}
		if (EmpId != null) {
			out.println("Employee ID: " + EmpId);
		}
		if (CtryCode != null) {
			out.println("Country: " + CtryCode);
		}
		if (CtrySubCode != null) {
			out.println("State: " + CtrySubCode);
		}
		if (CrnKey != null) {
			out.println("Reimbursement Currency: " + CrnKey);
		}
		if (IsTestEmp != null) {
			out.println("Is Test User: " + IsTestEmp);
		}
		if (LocaleName != null) {
			out.println("Locale Name: " + LocaleName);
		}
		if (LedgerKey != null) {
			out.println("Ledger Key: " + LedgerKey);
		}
		if (Custom21 != null) {
			out.println("Custom 21: " + Custom21);
		}
		if (ExpenseUser != null) {
			out.println("Is Expense User: " + ExpenseUser);
		}
		if (ExpenseApprover != null) {
			out.println("Is Expense Approver: " + ExpenseApprover);
		}
		if (TripUser != null) {
			out.println("Is Trip User: " + TripUser);
		}
		if (InvoiceUser != null) {
			out.println("Is Invoice User: " + InvoiceUser);
		}
		if (InvoiceApprover != null) {
			out.println("Is Invoice Approver: " + InvoiceApprover);
		}
		if (ExpenseApproverEmployeeID != null) {
			out.println("ExpenseApproverEmployeeID: " + ExpenseApproverEmployeeID);
		}
		if (Active != null) {
			out.println("Is Active: " + Active);
		}

	}
	public String getLoginId() {
		return LoginId;
	}
	public void setLoginId(String loginId) {
		LoginId = loginId;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getMi() {
		return Mi;
	}
	public void setMi(String mi) {
		Mi = mi;
	}
	public String getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}
	public String getEmpId() {
		return EmpId;
	}
	public void setEmpId(String empId) {
		EmpId = empId;
	}
	public String getCtryCode() {
		return CtryCode;
	}
	public void setCtryCode(String ctryCode) {
		CtryCode = ctryCode;
	}
	public String getCrnKey() {
		return CrnKey;
	}
	public void setCrnKey(String crnKey) {
		CrnKey = crnKey;
	}
	public String getCtrySubCode() {
		return CtrySubCode;
	}
	public void setCtrySubCode(String ctrySubCode) {
		CtrySubCode = ctrySubCode;
	}
	public String getIsTestEmp() {
		return IsTestEmp;
	}
	public void setIsTestEmp(String isTestEmp) {
		IsTestEmp = isTestEmp;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		this.Password = password;
	}
	public String getLocaleName() {
		return LocaleName;
	}
	public void setLocaleName(String localeName) {
		LocaleName = localeName;
	}
	public String getLedgerKey() {
		return LedgerKey;
	}
	public void setLedgerKey(String ledgerKey) {
		LedgerKey = ledgerKey;
	}
	public String getExpenseUser() {
		return ExpenseUser;
	}
	public void setExpenseUser(String expenseUser) {
		ExpenseUser = expenseUser;
	}
	public String getExpenseApprover() {
		return ExpenseApprover;
	}
	public void setExpenseApprover(String expenseApprover) {
		ExpenseApprover = expenseApprover;
	}
	public String getTripUser() {
		return TripUser;
	}
	public void setTripUser(String tripUser) {
		TripUser = tripUser;
	}
	public String getInvoiceUser() {
		return InvoiceUser;
	}
	public void setInvoiceUser(String invoiceUser) {
		InvoiceUser = invoiceUser;
	}
	public String getInvoiceApprover() {
		return InvoiceApprover;
	}
	public void setInvoiceApprover(String invoiceApprover) {
		InvoiceApprover = invoiceApprover;
	}
	public String getExpenseApproverEmployeeID() {
		return ExpenseApproverEmployeeID;
	}
	public void setExpenseApproverEmployeeID(String expenseApproverEmployeeID) {
		ExpenseApproverEmployeeID = expenseApproverEmployeeID;
	}
	public String getActive() {
		return Active;
	}
	public void setActive(String active) {
		Active = active;
	}
	public String getCustom21() {
		return Custom21;
	}
	public void setCustom21(String custom21) {
		Custom21 = custom21;
	}
	
	

}
	

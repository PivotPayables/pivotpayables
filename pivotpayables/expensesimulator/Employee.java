package com.pivotpayables.expensesimulator;
/**
 * 
 */

/**
 * @author John Toman
 * 6/21/2015
 * This is the base class for an Employee.
 * It includes constructors for Basic, Simple, and Complete employees.
 * It generates a GUID, ID.  This is a system assigned GUID. 
 * In contrast the EmployeeID is a unique identifier the employer assigns to the employee.
 * The Employee class assumes an Employee object is associated with only one employer.
 * 
 * Added doctoEmployee class
 * 
 * Converted all properties to fields by making them private.
 *
 */
import static java.lang.System.out;








import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.pivotpayables.concurplatform.Location;



public class Employee {
//Public fields
	private String FirstName;
	private String LastName; 
	private String MiddleInitial;
	private String DisplayName;
	private String FullName;
	private String LoginID;// Concur Login ID
	private String Employee_ID;//the unique identifier for this employee in the company's HR system.
	private String EmployerCompanyID;
	private String EmployerCompanyName;
	private String Email;
	private Location Home;//where the employee's home is
	private String ID;
	private static BasicDBObject Doc = new BasicDBObject();


	public Employee () {};// no arg constructor
	public Employee(String first, String last){
		Employee employee = new Employee();
		employee.setFirstName(first);
		employee.setLastName(last);
	}
	
	public void display () {//method to display the employee in the console
		out.println("ID: " + getID());
		out.println("First Name: " + FirstName);
		out.println("Middle Initial: " + MiddleInitial);
		out.println("Last Name: " + LastName);
		out.println("Display Name: " + DisplayName);
		out.println("Login ID: " + LoginID);
		out.println("Employee ID: " + Employee_ID);
		out.println("Employeer Company ID: " + EmployerCompanyID);
		out.println("Employeer Company Name: " + EmployerCompanyName);
		
		if (Home != null) {
			out.println("Employee's Home Location:");
			Home.display();
		}
	}
	
	public BasicDBObject getDocument () {
		
		BasicDBObject myEmployee = new BasicDBObject("FirstName",this.FirstName);
		myEmployee.append("LastName",this.LastName);
		myEmployee.append("MiddleInitial", this.MiddleInitial);
		myEmployee.append("FullName",this.FullName);
		myEmployee.append("DisplayName",this.DisplayName);
		myEmployee.append("LoginID", this.LoginID);
		myEmployee.append("Email", this.Email);
		myEmployee.append("Employee_ID",this.Employee_ID);
		myEmployee.append("EmployerCompanyID",this.EmployerCompanyID);
		myEmployee.append("EmployerCompanyName",this.EmployerCompanyName);
		if (this.Home != null) {
			Doc = this.Home.getDocument();
			myEmployee.append("Home", Doc);
		}
		myEmployee.append("ID", this.getID());
		return myEmployee;
	}

	public Employee doctoEmployee (DBObject doc) {
		Employee myEmployee = new Employee();
		if (doc.get("ID")!= null) {
			String ID = doc.get("ID").toString();
			myEmployee.setID(ID);
		}
		if (doc.get("FirstName")!= null) {
			String FirstName = doc.get("FirstName").toString();
			myEmployee.setFirstName(FirstName);
		}
		if (doc.get("MiddleInitial")!= null) {
			String middle = doc.get("MiddleInitial").toString();
			myEmployee.setMiddleInitial(middle);	
		}
		if (doc.get("LastName")!= null) {
			String LastName = doc.get("LastName").toString();
			myEmployee.setLastName(LastName);
		}
		if (doc.get("DisplayName")!= null) {
			String DisplayName = doc.get("DisplayName").toString();
			myEmployee.setDisplayName(DisplayName);
		}
		if (doc.get("LoginID")!= null) {
			String LoginID = doc.get("LoginID").toString();
			myEmployee.setLoginID(LoginID);
		}
		if (doc.get("Employee_ID")!= null) {
			String Employee_ID = doc.get("Employee_ID").toString();
			myEmployee.setEmployee_ID(Employee_ID);
		}
		if (doc.get("EmployerCompanyID")!= null) {
			String EmployerCompanyID = doc.get("EmployerCompanyID").toString();
			myEmployee.setEmployerCompanyID(EmployerCompanyID);
		}
		if (doc.get("EmployerCompanyName")!= null) {
			String EmployerCompanyName = doc.get("EmployerCompanyName").toString();
			myEmployee.setEmployerCompanyName(EmployerCompanyName);
		}
		if (doc.get("Home")!= null) {
			//get the Home location for this employee
			Doc = (BasicDBObject)(doc.get("Home"));
			Location Home = new Location();
			Home = Home.doctoLocation(Doc);
			myEmployee.setHome(Home);
		}
		
		
		return myEmployee;
	}
	public int getEmployeesCount(DBCollection collection, String coid){//returns documents for employees at specified Employer Company ID
	    DBObject doc;
	    Long total = collection.count();
	    int count = total.intValue();
	    DBObject docs[]= new DBObject[count];
	    int Index=0;
	    BasicDBObject query= new BasicDBObject("ID", coid);
	    DBCursor cursor = collection.find(query);
	    while (cursor.hasNext()) {
	    	doc = cursor.next();
	    	docs[Index] = doc;
	    	Index = Index++;
	    }

	    return Index;
	  }
	public DBObject[] getEmployees(DBCollection collection, String coid){//returns documents for employees at specified Employer Company ID

	    DBObject doc;
	    Long total = collection.count();
	    int count = total.intValue();
	    DBObject docs[]= new DBObject[count];
	    int Index=0;
	    BasicDBObject query= new BasicDBObject("EmployerCompanyID", coid);
	    DBCursor cursor = collection.find(query);
	    while (cursor.hasNext()) {
	    	doc = cursor.next();
	    	docs[Index] = doc;
	    	Index = Index++;
	    }

	    return docs;
	  }
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
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
	public String getMiddleInitial() {
		return MiddleInitial;
	}
	public void setMiddleInitial(String middleInitial) {
		MiddleInitial = middleInitial;
	}
	public String getDisplayName() {
		return DisplayName;
	}
	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}
	public String getFullName() {
		return FullName;
	}
	public void setFullName(String fullName) {
		FullName = fullName;
	}
	public String getLoginID() {
		return LoginID;
	}
	public void setLoginID(String loginID) {
		LoginID = loginID;
	}
	public String getEmployee_ID() {
		return Employee_ID;
	}
	public void setEmployee_ID(String employee_ID) {
		Employee_ID = employee_ID;
	}
	public String getEmployerCompanyID() {
		return EmployerCompanyID;
	}
	public void setEmployerCompanyID(String employerCompanyID) {
		EmployerCompanyID = employerCompanyID;
	}
	public String getEmployerCompanyName() {
		return EmployerCompanyName;
	}
	public void setEmployerCompanyName(String employerCompanyName) {
		EmployerCompanyName = employerCompanyName;
	}
	public Location getHome() {
		return Home;
	}

	public void setHome(Location home) {
		Home = home;
	}

	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
}

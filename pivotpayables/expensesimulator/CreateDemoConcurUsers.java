package com.pivotpayables.expensesimulator;
/**
 * 
 */

/**
 * @author John Toman
 * 4/28/15
 * This program generates a specified number of employees for a specified, Concur company.
 * 
 * To create employee names, it uses two text files, FirstNames.txt and LastNames.txt, that contain a
 * collection of seed names that are used to create an employee name.
 * 
 * 
 */
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.pivotpayables.concurplatform.ConcurUser;
import com.pivotpayables.concurplatform.ExpenseReports;
import com.pivotpayables.concurplatform.Location;




public class CreateDemoConcurUsers {

	/**
	 * @param args
	 * arg[0] is how  many employees to create.
	 * arg[1] the key for the WS Admin for the company to create the employees
	 * args[2] the domain for the company
	 * 
	 */
	private static ArrayList<String> First = new ArrayList<String>();//an ArrayList of String elements for holding the seed names for employee first name.
	private static ArrayList<String> Last = new ArrayList<String>();//an ArrayList of String elements for holding the seed names for employee last name.
	private static ExpenseReports r = new ExpenseReports();
	private static String key= "YcvD9V0pD8Tm1sLqTURELsfad1s=";//"iKYcEPLgIZuQid+5987q2bQs3Fk=";//
	private static String domain=  "apexconsultinggroup.com";//"pivotpayables.com";//;
	private static int employeecount=10;
	private static ConcurUser user;
	
	public static void main(String[] args) throws IOException, JAXBException {

		
		try {	
			if (args.length == 2) {//then there are parameters for EmployeeCount and key.  
				try {
					employeecount = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					employeecount = 1;
				}	
				key = args[1];
				domain = args[2];

			}// if args.length == 2
		} catch (Exception e) {
    		out.println(e);
		}// try block

	

		// get the seed files from disk
		Scanner FirstNames = new Scanner(new File("FirstNames.txt"));//open the file of seed names for employee first names.
		Scanner LastNames = new Scanner(new File("LastNames.txt"));//open the file of seed names for employee last names.	
		
		//load FirstName string array with first names from the FirstNames.txt file
		int i=0;  
		while (FirstNames.hasNext()==true) {//iterate through the seed names in the file
			First.add(i, FirstNames.next());//store the name in the array
			++i;
		}

		FirstNames.close();

		
		//load LastName string array with first names from the LastNames.txt file
		i=0;
		while (LastNames.hasNext()==true) {//iterate through the seed names in the file
			Last.add(i,LastNames.next());//store the name in the array
			++i;
		}
		LastNames.close();
		CreateEmps();
		
}//end main method
private static void CreateEmps () throws IOException, JAXBException {


	String first, last;
	String login;
	String employeeid ="";
	char middle;
	String mi;
	int FirstIndex, LastIndex;

	Random RandomIndex = new Random();
	String result=null;

	
	for (int j=0; j < employeecount; j++) {
		
		FirstIndex = RandomIndex.nextInt(First.size());//select a random first name
		first = First.get(FirstIndex);//assign it to first
		LastIndex = RandomIndex.nextInt (Last.size());//select a random last name
		last = Last.get(LastIndex);//assign it to last
		middle = (char) (RandomIndex.nextInt(26) + 'A');//select a random middle initial
		mi = String.valueOf(middle);
		login = first +"."+ last + "@"+ domain;//compose the Login ID
	
		// generate a random, 10 digit number for Employee_ID
		employeeid ="";
		for (int k=0; k< 10; k++) {
				employeeid = employeeid + (char) (RandomIndex.nextInt(10) + '0');
		}
		
		user = new ConcurUser();
		user.setEmpId(employeeid);
		user.setLoginId(login);
		user.setPassword("initial1");
		user.setFirstName(first);
		user.setLastName(last);
		user.setMi(mi);
		user.setEmailAddress("john.toman@pivotpayables.com");
		user.setLocaleName("en_US");
		user.setCtryCode("US");
		user.setCrnKey("USD");
		user.setLedgerKey("DEFAULT");
		user.setCustom21("US");
		user.setExpenseUser("Y");
		user.setActive("Y");
		user.setIsTestEmp("N");
		result = r.addConcurUser(user, key);
		out.println(result);
		//user = r.getUser(login, key);
		//user.display();
		
	}//end for j loop
}//end CreateEmps method

}

		

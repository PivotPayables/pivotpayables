package com.pivotpayables.expensesimulator;
/**
 * @author John Toman
 * 4/2/16
 * 
 * This class creates the field context mappings for a specified Company ID
 *
 */
import static java.lang.System.out;





import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.pivotpayables.prime.FieldContext;

public class CreateDemoFieldContexts {
	
	private final static String host= "localhost";//the MongoDB server host
	private final static int port = 27017;//the MongoDB server port
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection conCollection = myMongoCompanyFunctions.setCollection("FieldContexts");// get the Companies collection
	
	public static void main(String[] args) {

		String CompanyID = "KQAR7139VZHR6777ZOLE";// Apex//"OAZT5067BTQL0845LFBR";//Nesoi Solutions//"HQHY4447XLVD5303FFLG";//Cardno//"YASE8992KPDY5506EOBZ";//Coffman Engineers//"BMBV7890ZRLQ9107RYBE";// Yost West
		String FormType = "ExpenseEntry";// the Concur form type
		String[] contexts = new String[5];
		String[] fields = new String[5];
		FieldContext context = new FieldContext();

		contexts[0] = "Account";
		contexts[1] = "Activity";
		contexts[2] = "Phase";
		contexts[3] = "Task";
		contexts[4] = "IsBillable";
		/*
		//Nesoi Solutions
		fields[0] = "Custom3";
		fields[1] = "Custom4";
		fields[2] = null;
		fields[3] = null;
		fields[4] = null;
		*/
		
		// Apex Correct
		fields[0] = "Custom2";
		fields[1] = "Custom1";
		fields[2] = null;
		fields[3] = null;
		fields[4] = "Custom3";
		

		/*
		// Cardno Incorrect
		fields[0] = "Custom3";
		fields[1] = "Custom3";
		fields[2] = "Custom4";
		fields[3] = "Custom5";
		fields[4] = null;
		
		
		// Cardno Correct
		fields[0] = "Custom4";
		fields[1] = "Custom4";
		fields[2] = "Custom5";
		fields[3] = "Custom6";
		fields[4] = null;
		/*
		fields[4] = null;
		
		// Coffman Correct
		fields[0] = "OrgUnit2";
		fields[1] = "OrgUnit2";
		fields[2] = "OrgUnit3";
		fields[3] = "OrgUnit4";

		// Coffman Incorrect
		fields[0] = "OrgUnit3";
		fields[1] = "OrgUnit3";
		fields[2] = "OrgUnit4";
		fields[3] = "OrgUnit5";
		fields[4] = null;
				*/


		
			
		for (int i=0; i<fields.length; i++) {// iterate through each field context
			if (fields[i] != null) {// then there is a field mapped for this context
				context.setID(GUID.getGUID(2));
				context.setCompanyID(CompanyID);
				context.setFormType(FormType);
				context.setFieldID(fields[i]);
				context.setContext(contexts[i]);
				context.display();
				BasicDBObject myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
				myMongoCompanyFunctions.addDoc(conCollection, myDoc);
			}
		}
		context = new FieldContext();
		context.setID(GUID.getGUID(2));
		context.setCompanyID(CompanyID);
		context.setContext("VAT");
		context.setFormType("Domestic");
		context.setFieldID("All");
		context.display();
		BasicDBObject myDoc = context.getDocument();//convert the FieldContext object into a MongoDB document
		myMongoCompanyFunctions.addDoc(conCollection, myDoc);
	}
		
		

}


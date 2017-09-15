/**
 * 
 */
package com.pivotpayables.nexus;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.pivotpayables.expensesimulator.MongoDBFunctions;

/**
 * @author John
 * This class returns the current serial number for the specified Batch Definition.  It updates the Batch Defintion
 * to the new serial number.
 *
 */
public class ManageSerialNumber {

	static final String host = "localhost";
	static final int port = 27017;
	
	// Connect to the Company_Data database, and the get the necessary collections
	private static MongoDBFunctions myMongoCompanyFunctions = new MongoDBFunctions (host, port, "Company_Data");// create a MongoDB Client for the specified host and port, and get the Company_Data database 
	private static DBCollection batCollection = myMongoCompanyFunctions.setCollection("BatchDefinitions");// get the BatchDefintions collection
	
	public static String getNumber(String batchdefinitionID) {
		BatchDefinition definition = new BatchDefinition();
		DBObject doc = myMongoCompanyFunctions.getDoc(batCollection, batchdefinitionID);// get the Batch Definition document for the specified Batch Definition ID
		definition = definition.doctoBatchDefinition(doc);// convert the Batch Definition document into a Batch Definition object
		int current = definition.getCurrentSerialNumber();// get the current serial number stored in the batch definition
		current = current++;// increment the serial number
		definition.setCurrentSerialNumber(current);// update the current serial number for the batch defintion
		myMongoCompanyFunctions.updateDocByField(batCollection, "ID", definition.getID().toString(), "CurrentSerialNumber", Integer.toString(definition.getCurrentSerialNumber()));
		String text = Integer.toString(current);// convert current to a String
		return text;
	}

}

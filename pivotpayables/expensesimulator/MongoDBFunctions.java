package com.pivotpayables.expensesimulator;
/**
 * @author John Toman
 * 6/21/2016
 * This provides functions for working with MongoDB databases.
 * 
 * Moved all methods related to specific objects to their assoicated class so that
 * MongoDBFunctions includes only methods that are generic
 * 
 *
 */
import static java.lang.System.out;


// MongoDB Java driver 
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.gridfs.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;



public class MongoDBFunctions {
	protected MongoClient mongoClient;
	protected DB db;
	protected DBCollection myCollection;
	protected static String host = "localhost";
	protected static int port = 27017;
	protected static CustomFunctions myFunctions = new CustomFunctions();
	protected DBObject doc;
	protected BasicDBObject query;
	
	

	
	public MongoDBFunctions (String host, int port) {//constructor that establishes MongoDB Client at the specified MongoDB server.

	    try {
	        MongoClient mongoClient = new MongoClient(host, port);
	        mongoClient.setWriteConcern(WriteConcern.JOURNAL_SAFE);

	      } catch (Exception e) {
	        out.println(e);
	      }
	}
	    
	    public MongoDBFunctions (String host, int port, String dbname) {//constructor that establishes MongoDB Client at the specified MongoDB server and opens the specified database.

	    try {
	        MongoClient mongoClient = new MongoClient(host, port);
	        mongoClient.setWriteConcern(WriteConcern.JOURNAL_SAFE);
	        db = mongoClient.getDB(dbname);
	      } catch (Exception e) {
	        out.println(e);
	      }
	}
	    public MongoDBFunctions (String host, int port, String dbname, String collname) {//constructor that establishes MongoDB Client at the specified MongoDB server and opens the specified database and collection.

		    try {
		        MongoClient mongoClient = new MongoClient(host, port);
		        mongoClient.setWriteConcern(WriteConcern.JOURNAL_SAFE);
		        db = mongoClient.getDB(dbname);
		        myCollection = db.getCollection(collname);
		      } catch (Exception e) {
		        System.out.println(e);
		      }
		}
		public DB getDB () {//gets the MongoDB database
			return db;
		}
		public DB setDatabase (String dbname) {//sets the MongoDB database for the current MongoDB client
			try {
				db = mongoClient.getDB(dbname);
		      } catch (Exception e) {
			        System.out.println(e);
			  }
			return db;
		}
		public DBCollection getCollection () {// gets the collection for the current MongoDB client
			return myCollection;
		}
		public DBCollection setCollection (String name) {//sets the specified collection for the current MongoDB client
			if (db.collectionExists(name)) {
				myCollection = db.getCollection(name);
			} else {
				myCollection = null;
			}
			return myCollection;
		}  	
		public GridFS getFS() {
			db = mongoClient.getDB("Expense_Data");
			GridFS myFS = new GridFS(db);
			return myFS;
		}
		public String addDoc(DBCollection collection, BasicDBObject doc){//adds a document to the MongoDB in the specified collection
			
			String status;
			try {
				collection.insert(WriteConcern.SAFE,doc);
				status = "success";
			} catch (MongoException ex) {
				Logger logger = Logger.getGlobal();
				logger.log(Level.SEVERE, ex.getMessage());//  Log a severe error to the Logger
				status = "failed";
			}
		    return status;
		
		  }
		public String updateDocByField(DBCollection collection, String queryfield, String queryvalue, String updatefield, String updatevalue){
			//updates the specified update field and value to the first matching document to the specified query field and value
			String status;
		    BasicDBObject query= new BasicDBObject(queryfield, queryvalue);
		    BasicDBObject update = new BasicDBObject();
		    try {
			    update.append("$set", new BasicDBObject (updatefield, updatevalue));
			    collection.update(query, update, false, false);
				status = "success";
			} catch (MongoException ex) {
				Logger logger = Logger.getGlobal();
				logger.log(Level.SEVERE, ex.getMessage());//  Log a severe error to the Logger
				status = "failed";
			}
		    return status;
		  }
		public String deleteDocByDoc(DBCollection collection, DBObject doc){//deletes a document in the MongoDB in the specified collection
			
			String status;
			try {
				collection.remove(doc, WriteConcern.SAFE);
				status = "success";
			} catch (MongoException ex) {
				Logger logger = Logger.getGlobal();
				logger.log(Level.SEVERE, ex.getMessage());//  Log a severe error to the Logger
				status = "failed";
			}
		    return status;
		
		  }
		public String deleteDoc(DBCollection collection, String queryfield, String queryvalue){//deletes a document from the MongoDB in the specified collection with the specified field value

			String status;
			BasicDBObject query = new BasicDBObject(queryfield, queryvalue);
			try {
				collection.remove(query, WriteConcern.SAFE);
				status = "success";
			} catch (MongoException ex) {
				Logger logger = Logger.getGlobal();
				logger.log(Level.SEVERE, ex.getMessage());//  Log a severe error to the Logger
				status = "failed";
			}
		    return status;

		  }

		
		public Long docCount (DBCollection collection) {//counts the number of documents in a collection.  
	        
			return collection.count();
	  	}
		public ArrayList<DBObject> getDocs(DBCollection collection){
			//create an ArrayList of DBObject elements.
		    ArrayList<DBObject> Docs = new ArrayList<DBObject>();
		    DBCursor cursor = collection.find();

		    while(cursor.hasNext()) {
		      DBObject doc = cursor.next();
		      Docs.add(doc);
		    }
		    return Docs;
		  }
		public DBObject getDoc(DBCollection collection, String id){// find the document with the specified ID

		    BasicDBObject query= new BasicDBObject("ID", id);
		    DBCursor cursor = collection.find(query);
		    if (cursor.hasNext()) {
		    	doc = cursor.next();
		    }

		    return doc;
		  }
		public int getDocumentCount(DBCollection collection, String field, String value){//returns the count of documents that match the specified field value

		    BasicDBObject query= new BasicDBObject(field, value);
		    DBCursor cursor = collection.find(query);
		    int count = cursor.count();



		    return count;
		  }
		public ArrayList<DBObject> getDocsByField(DBCollection collection, String field, String value){//returns the documents that match the specified field and its value
		    DBObject doc;
			ArrayList<DBObject> Docs = new ArrayList<DBObject>();
		    BasicDBObject query= new BasicDBObject(field, value);
		    DBCursor cursor = collection.find(query);
		    int count = cursor.count();//the number of documents with the specified field value

		    for (int i=0; i<count; i++){//iterate for each document in the cursor
		    	doc = cursor.next();//get the document for this iteration
		    	Docs.add(doc);//add it to the ArrayList
		    }
		    return Docs;
		  }
		@SuppressWarnings("rawtypes")
		public ArrayList<DBObject> getDocsByCriteria(DBCollection collection, HashMap criteria){//returns the documents that match the specified critieria
			/* criteria is a HashMap of key-value pairs
			 * where the key is the search field name and the value is the search field value
			 */
		    DBObject doc;
			ArrayList<DBObject> Docs = new ArrayList<DBObject>();
			BasicDBObject query= new BasicDBObject();
			
			Iterator it = criteria.entrySet().iterator();// create an Iterator to iterate the HashMap
			while(it.hasNext()){// iterate for each criterion in the criteria HashMap
				Map.Entry criterion = (Map.Entry)it.next();// get the criterion for this iteration
				query.append(criterion.getKey().toString(), criterion.getValue().toString());// add the criterion for this iteration
			}
			
		    DBCursor cursor = collection.find(query);
		    int count = cursor.count();//the number of documents with the specified field value

		    for (int i=0; i<count; i++){//iterate for each document in the cursor
		    	doc = cursor.next();//get the document for this iteration
		    	Docs.add(doc);//add it to the ArrayList
		    }
		    return Docs;
		  }
		
		public void putImage (String filepath, String filename) throws IOException {
			// this reads an image file from the file system puts the image file with the specified path and name into the MongoDB
			File file = new File(filepath + filename);// read the file from the file system
			GridFS myFS = new GridFS(db);
			GridFSInputFile gridFile = myFS.createFile(file);
			gridFile.save();
		}
		public void getImage (String filepath, String filename) throws IOException {
			// this gets an image file from the MongoDB, and writes it to the file system as a file
			GridFS myFS = new GridFS(db);
			GridFSDBFile imagefile = myFS.findOne(filename);
			if (imagefile != null) {// then it found the file
				File file = new File(filepath + filename);
				imagefile.writeTo(file);
			}

		}
}


/**
 * 
 */
package com.pivotpayables.intacctplatform;

/**
 * @author John
 * 
 * This is the base class for an Intacct API Session object
 *
 */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name ="api")
public class APISession {
	
	@XmlElement (name ="sessionid")
	String sessionid;
	
	@XmlElement (name ="endpoint")
	String endpoint;

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	

}

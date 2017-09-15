/**
 * 
 */
package com.pivotpayables.intacctplatform;

import static java.lang.System.out;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author John
 * Base class for an Intacct Date Created object
 *
 */

@XmlRootElement(name="datecreated")
@XmlAccessorType (XmlAccessType.FIELD)
public class DateCreated {
	@XmlElement(name = "year")
	String year;
    
	@XmlElement(name = "month")
    String month;
    
	@XmlElement(name = "day")
    String day;
	
	public DateCreated () {} // no args constructor
	
	
	
	
	public void display() {	
		out.println("Created: " + day +"-" + month + "-" + year);

	}




	public String getYear() {
		return year;
	}




	public void setYear(String year) {
		this.year = year;
	}




	public String getMonth() {
		return month;
	}




	public void setMonth(String month) {
		this.month = month;
	}




	public String getDay() {
		return day;
	}




	public void setDay(String day) {
		this.day = day;
	}
	
}

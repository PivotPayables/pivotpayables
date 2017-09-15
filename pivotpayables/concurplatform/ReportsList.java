/**
 * 
 */
package com.pivotpayables.concurplatform;

/**
 * @author TranseoTech
 *
 */

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

import static java.lang.System.out;

@XmlRootElement(name="ReportsList")
@XmlAccessorType (XmlAccessType.FIELD)
public class ReportsList {
	@XmlElement(name = "ReportSummary")
    private List<ReportSummary> summaries = null;
 
    public List<ReportSummary> getSummaries() {
        return summaries;
    }
 
    public void setSummaries(List<ReportSummary> summaries) {
        this.summaries = summaries;
    }
    public void display() {

    	
    }


}
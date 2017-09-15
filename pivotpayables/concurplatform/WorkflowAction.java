/**
 * 
 */
package com.pivotpayables.concurplatform;

/**
 * @author TranseoTech
 *
 */
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name ="WorkflowAction")
public class WorkflowAction {
	
	String Action;
	String Comment;
	@XmlElement (name ="Action")
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	public String getComment() {
		return Comment;
	}
	@XmlElement (name ="Comment")
	public void setComment(String comment) {
		Comment = comment;
	}

}

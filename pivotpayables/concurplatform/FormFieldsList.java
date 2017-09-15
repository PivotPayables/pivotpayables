/**
 * 
 */
package com.pivotpayables.concurplatform;

/**
 * @author John Toman
 * 5/1/2015
 * Base class for the Form Fields List resource
 *
 */
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="FormFieldsList")
@XmlAccessorType (XmlAccessType.FIELD)
public class FormFieldsList {
	@XmlElement(name = "FormField")
    private List<FormField> fields = null;

	public List<FormField> getFields() {
		return fields;
	}

	public void setFields(List<FormField> fields) {
		this.fields = fields;
	}
	
}

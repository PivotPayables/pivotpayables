/**
 * 
 */
package com.pivotpayables.concurplatform;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author John Toman
 * 05/1/2015
 * Base class for Form Field resource
 *
 */
import static java.lang.System.out;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FormField")
@XmlAccessorType(XmlAccessType.FIELD)
public class FormField {
	@XmlElement (name = "Id")
	String ID;
	@XmlElement
	String Label;
	@XmlElement
	String ControlType;
	@XmlElement
	String DataType;
	@XmlElement
	String MaxLength;
	@XmlElement
	String Required;
	@XmlElement
	String Cols;
	@XmlElement
	String Access;
	@XmlElement
	String Width;
	@XmlElement
	String Custom;
	@XmlElement
	String Sequence;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public String getControlType() {
		return ControlType;
	}
	public void setControlType(String controlType) {
		ControlType = controlType;
	}
	public String getDataType() {
		return DataType;
	}
	public void setDataType(String dataType) {
		DataType = dataType;
	}
	public String getMaxLength() {
		return MaxLength;
	}
	public void setMaxLength(String maxLength) {
		MaxLength = maxLength;
	}
	public String getRequired() {
		return Required;
	}
	public void setRequired(String required) {
		Required = required;
	}
	public String getCols() {
		return Cols;
	}
	public void setCols(String cols) {
		Cols = cols;
	}
	public String getAccess() {
		return Access;
	}
	public void setAccess(String access) {
		Access = access;
	}
	public String getWidth() {
		return Width;
	}
	public void setWidth(String width) {
		Width = width;
	}
	public String getCustom() {
		return Custom;
	}
	public void setCustom(String custom) {
		Custom = custom;
	}
	public String getSequence() {
		return Sequence;
	}
	public void setSequence(String sequence) {
		Sequence = sequence;
	}
	public void display() {
		out.println("--------------------------");
		out.println("ID: " + ID);
		out.println("Label: " + Label);
		out.println("Control Type: " + ControlType);
		out.println("DataType: " + DataType);
		out.println("Max Length: " + MaxLength);
		out.println("Required: " + Required);
		out.println("Cols: " + Cols);
		out.println("Access: " + Access);
		out.println("Width: " + Width);
		out.println("Custom: " + Custom);
		out.println("Sequence: " + Sequence);

	}
}


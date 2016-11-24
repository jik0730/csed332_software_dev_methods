package edu.ncsu.csc.itrust.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * A bean for storing data about OrthopedicSurgery.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters� 
 * to create these easily)
 */
public class OrthopedicSurgeryRecordBean {
	
	/**The mid of the user.*/
	private long mid;
	/**The Orthopedic office visit id.*/
	private long oid;
	/**The date of the Orthopedic office visit.*/
	private String visitDate;
	/**The last name of the orthopedic surgeon.*/
	private String docLastName;
	/**The first name of the orthopedic surgeon.*/
	private String docFirstName;
	/**Surgery*/
	private String surgery;
	/**Surgery notes*/
	private String surgeryNotes;
	
	/**
	 * Getter for the mid value.
	 * @return the mid.
	 */
	public long getMid() {
		return mid;
	}
	
	/**
	 * Set the mid value.
	 * @param mid the new value.
	 */
	public void setMid(long mid) {
		this.mid = mid;
	}
	
	/**
	 * Getter for the oid value.
	 * @return the oid.
	 */
	public long getOid() {
		return oid;
	}
	
	/**
	 * Set the oid value.
	 * @param oid the new value.
	 */
	public void setOid(long oid) {
		this.oid = oid;
	}
	
	/**
	 * Getter for the visitDate as a Date object.
	 * @return the visit date.
	 */
	public Date getVisitDate(){
		if (visitDate == null)
			return null;
		Date date = null; 
		try {
			date = new SimpleDateFormat("MM/dd/yyyy").parse(visitDate);
		} catch (ParseException e) {
			//If it can't be parsed, return null.
			return null;
		}
		return date;
	}
	
	/**
	 * Getter for the visitDate as a string.
	 * @return the visitDate.
	 */
	public String getVisitDateString() {
		return visitDate;
	}
	
	/**
	 * Set the visitDate value
	 * @param visitDate the new value.
	 */
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	
	/**
	 * Getter for the last name of the optometrist.
	 * @return the optometrist last name.
	 */
	public String getLastName(){
		return this.docLastName;
	}
	
	/**
	 * Set the last name of the optometrist.
	 * @param docLastName the new value.
	 */
	public void setLastName(String docLastName){
		this.docLastName = docLastName;
	}

	/**
	 * Getter for the first name of the optometrist.
	 * @return the doctors first name.
	 */
	public String getFirstName(){
		return this.docFirstName;
	}
	
	/**
	 * Set the first name of the optometrist.
	 * @param docFirstName the new value.
	 */
	public void setFirstName(String docFirstName){
		this.docFirstName = docFirstName;
	}
	

	/**
	 * @return the surgery
	 */
	public String getSurgery() {
		return surgery;
	}

	/**
	 * @param surgery the surgery to set
	 */
	public void setSurgery(String surgery) {
		this.surgery = surgery;
	}

	/**
	 * @return the surgeryNotes
	 */
	public String getSurgeryNotes() {
		return surgeryNotes;
	}

	/**
	 * @param surgeryNotes the surgeryNotes to set
	 */
	public void setSurgeryNotes(String surgeryNotes) {
		this.surgeryNotes = surgeryNotes;
	}

	/**
	 * Method used to compute the hashcode for a OrthopedicSurgeryRecordBean. 
	 * @return true if the OrthopedicSurgeryRecordBeans are equal, false otherwise.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((docFirstName == null) ? 0 : docFirstName.hashCode());
		result = prime * result
				+ ((docLastName == null) ? 0 : docLastName.hashCode());
		result = prime * result + (int) (mid ^ (mid >>> 32));
		result = prime * result + (int) (oid ^ (oid >>> 32));
		result = prime * result
				+ ((visitDate == null) ? 0 : visitDate.hashCode());
		result = prime * result
				+ ((surgery == null) ? 0 : surgery.hashCode());
		result = prime * result
				+ ((surgeryNotes == null) ? 0 : surgeryNotes.hashCode());
		return result;
	}

	/**
	 * Method used to determine if OrthopedicSurgeryRecordBeans are equal. 
	 * @return true if the OrthopedicSurgeryRecordBeans are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrthopedicSurgeryRecordBean other = (OrthopedicSurgeryRecordBean) obj;
		if (docFirstName == null) {
			if (other.docFirstName != null)
				return false;
		} else if (!docFirstName.equals(other.docFirstName))
			return false;
		if (docLastName == null) {
			if (other.docLastName != null)
				return false;
		} else if (!docLastName.equals(other.docLastName))
			return false;
		if (mid != other.mid)
			return false;
		if (oid != other.oid)
			return false;
		if (visitDate == null) {
			if (other.visitDate != null)
				return false;
		} else if (!visitDate.equals(other.visitDate))
			return false;
		if (surgery == null) {
			if (other.surgery != null)
				return false;
		} else if (!surgery.equals(other.surgery))
			return false;
		if (surgeryNotes == null) {
			if (other.surgeryNotes != null)
				return false;
		} else if (!surgeryNotes.equals(other.surgeryNotes))
			return false;
		return true;
	}

	/**
	 * Creates a string representation of this object.
	 * @return The string representation.
	 */
	@Override
	public String toString() {
		return "OrthopedicSurgeryRecordBean [mid=" + mid + ", oid=" + oid
				+ ", visitDate=" + visitDate + ", docLastName=" + docLastName
				+ ", docFirstName=" + docFirstName + ", surgery=" + surgery + ",surgeryNotes=" 
				+ surgeryNotes + "]";
	}
}
	
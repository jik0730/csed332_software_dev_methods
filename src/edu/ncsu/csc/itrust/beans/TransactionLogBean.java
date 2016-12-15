package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;
import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * A bean for storing data about a transaction that occurred within iTrust.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters
 * to create these easily)
 */
public class TransactionLogBean {
	private long transactionID;
	private String loggedInRole;
	private String secondaryRole;
	private TransactionType transactionType;
	private Timestamp timeLogged;
	private String addedInfo;

	public TransactionLogBean() {
	}

	public String getAddedInfo() {
		return addedInfo;
	}

	public void setAddedInfo(String addedInfo) {
		this.addedInfo = addedInfo;
	}

	public String getLoggedInRole() {
		return loggedInRole;
	}

	public void setLoggedInRole(String loggedInRole) {
		this.loggedInRole = loggedInRole;
	}

	public String getSecondaryRole() {
		return secondaryRole;
	}

	public void setSecondaryRole(String secondaryRole) {
		this.secondaryRole = secondaryRole;
	}

	public Timestamp getTimeLogged() {
		return (Timestamp) timeLogged.clone();
	}

	public void setTimeLogged(Timestamp timeLogged) {
		this.timeLogged = (Timestamp) timeLogged.clone();
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType tranactionType) {
		this.transactionType = tranactionType;
	}

	public long getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(long transactionID) {
		this.transactionID = transactionID;
	}
}

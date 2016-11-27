package edu.ncsu.csc.itrust.beans;

public class OrderBean {

	int orderID;
	int visitID;
	long orderHCPID;
	long orderedHCPID;
	long patientID;
	boolean completed;
	
	public OrderBean() {};
	
	public OrderBean(int orderID,
			int visitID,
			long orderHCPID,
			long orderedHCPID,
			long patientID,
			boolean completed) {
		this.orderID = orderID;
		this.orderHCPID = orderHCPID;
		this.orderedHCPID = orderedHCPID;
		this.patientID = patientID;
		this.completed = completed;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getVisitID() {
		return visitID;
	}

	public void setVisitID(int visitID) {
		this.visitID = visitID;
	}

	public long getOrderHCPID() {
		return orderHCPID;
	}

	public void setOrderHCPID(long orderHCPID) {
		this.orderHCPID = orderHCPID;
	}

	public long getOrderedHCPID() {
		return orderedHCPID;
	}

	public void setOrderedHCPID(long orderedHCPID) {
		this.orderedHCPID = orderedHCPID;
	}

	public long getPatientID() {
		return patientID;
	}

	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}

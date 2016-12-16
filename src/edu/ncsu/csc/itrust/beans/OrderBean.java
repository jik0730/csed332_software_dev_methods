package edu.ncsu.csc.itrust.beans;

public class OrderBean {

	int orderID;
	int visitID;
	long orderHCPID;
	long orderedHCPID;
	long patientID;
	boolean completed;

	public OrderBean() {
	};

	/**
	 * initialize order bean
	 * 
	 * @param orderID
	 * @param visitID
	 * @param orderHCPID
	 * @param orderedHCPID
	 * @param patientID
	 * @param completed
	 */
	public OrderBean(int orderID, int visitID, long orderHCPID, long orderedHCPID, long patientID, boolean completed) {
		this.orderID = orderID;
		this.orderHCPID = orderHCPID;
		this.orderedHCPID = orderedHCPID;
		this.patientID = patientID;
		this.completed = completed;
	}

	/**
	 * get orderID
	 * 
	 * @return orderID
	 */
	public int getOrderID() {
		return orderID;
	}

	/**
	 * set OrderID
	 * 
	 * @param orderID
	 */
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	/**
	 * get VisitID
	 * 
	 * @return visitID
	 */
	public int getVisitID() {
		return visitID;
	}

	/**
	 * set VisitID
	 * 
	 * @param visitID
	 */
	public void setVisitID(int visitID) {
		this.visitID = visitID;
	}

	/**
	 * get orderHCP
	 * 
	 * @return orderHCP
	 */
	public long getOrderHCPID() {
		return orderHCPID;
	}

	/**
	 * set OrderHCPID
	 * 
	 * @param orderHCPID
	 */
	public void setOrderHCPID(long orderHCPID) {
		this.orderHCPID = orderHCPID;
	}

	/**
	 * get orderedHCPID
	 * 
	 * @return orderedHCPID
	 */
	public long getOrderedHCPID() {
		return orderedHCPID;
	}

	/**
	 * setOrderedHCPID
	 * 
	 * @param orderedHCPID
	 */
	public void setOrderedHCPID(long orderedHCPID) {
		this.orderedHCPID = orderedHCPID;
	}

	/**
	 * getPatientID
	 * 
	 * @return patientID
	 */
	public long getPatientID() {
		return patientID;
	}

	/**
	 * setPatientID
	 * 
	 * @param patientID
	 */
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	/**
	 * check to Completed
	 * 
	 * @return completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * setCompleted
	 * 
	 * @param completed
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * override method to check equals' orderbean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderBean other = (OrderBean) obj;
		if (this.orderID == other.orderID && this.visitID == other.visitID && this.orderHCPID == other.orderHCPID
				&& this.orderedHCPID == other.orderedHCPID && this.patientID == other.patientID
				&& this.completed == other.completed)
			return true;
		else
			return false;
	}

	/**
	 * override method to get orderbean's hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 269;
		int result = 1;
		result = prime * result + (int) (orderID ^ (orderID >>> 16));
		result = prime * result + (int) (visitID ^ (visitID >>> 16));
		result = prime * result + (int) (orderHCPID ^ (orderHCPID >>> 32));
		result = prime * result + (int) (orderedHCPID ^ (orderedHCPID >>> 32));
		result = prime * result + (int) (patientID ^ (patientID >>> 32));
		if (completed)
			result = prime * result + 10;
		else
			result = prime * result + 20;
		return result;
	}

	/**
	 * override method to get string in orderbean
	 */
	@Override
	public String toString() {
		return "OrderBean [orderID=" + orderID + ", visitID=" + visitID + ", orderHCPID=" + orderHCPID
				+ ", orderedHCPID=" + orderedHCPID + ", patientID=" + patientID + ", completed=" + completed;
	}

}

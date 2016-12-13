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
		this.visitID = visitID;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderBean other = (OrderBean) obj;
		if(this.orderID == other.orderID &&
			this.visitID == other.visitID &&
			this.orderHCPID == other.orderHCPID &&
			this.orderedHCPID == other.orderedHCPID &&
			this.patientID == other.patientID &&
			this.completed == other.completed)
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 269;
		int result = 1;
		result = prime * result + (int) (orderID ^ (orderID >>> 16));
		result = prime * result + (int) (visitID ^ (visitID >>> 16));
		result = prime * result + (int) (orderHCPID ^ (orderHCPID >>> 32));
		result = prime * result + (int) (orderedHCPID ^ (orderedHCPID >>> 32));
		result = prime * result + (int) (patientID ^ (patientID >>> 32));
		if(completed)
			result = prime * result + 10;
		else
			result = prime * result + 20;
		return result;
	}
	
	@Override
	public String toString() {
		return "OrderBean [orderID=" + orderID + ", visitID=" + visitID
				+ ", orderHCPID=" + orderHCPID + ", orderedHCPID=" + orderedHCPID
				+ ", patientID=" + patientID + ", completed=" + completed;
	}
	
}

package edu.ncsu.csc.itrust.beans;

/**
 * A bean for storing data about a WardRoom.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to
 * be added to a bean (with the exception of minor formatting such as
 * concatenating phone numbers together). A bean must only have Getters and
 * Setters (Eclipse Hint: Use Source > Generate Getters and Setters. to create
 * these easily)
 */
public class WardRoomBean {

	long roomID = 0;
	Long occupiedBy = null;
	long inWard = 0;
	String roomName = "";
	String status = "Clean";
	Boolean state = Boolean.valueOf(true);
	Long waiting = null;
	int price = 50;
	int story = 4;

	public WardRoomBean(long roomID, long occupiedBy, long inWard, String roomName, String status, Boolean state,
			long waiting, int price, int story) {
		this.roomID = roomID;
		this.occupiedBy = occupiedBy;
		this.inWard = inWard;
		this.roomName = roomName;
		this.status = status;
		this.state = state;
		this.waiting = waiting;
		this.price = price;
		this.story = story;
	}

	/**
	 * isAccepted
	 * 
	 * @return status
	 */
	public boolean getState() {
		return this.state;
	}

	/**
	 * setPending
	 * 
	 * @param pending
	 *            pending
	 */
	public void setPending(boolean pending) {
		this.state = Boolean.valueOf(false);
	}

	/**
	 * If setPending(false) has not been called before using this method, this
	 * method will have no effect.
	 * 
	 * @param accepted
	 *            accepted
	 */
	public void setAccepted(boolean accepted) {
		this.state = Boolean.valueOf(accepted);
	}

	/**
	 * getWaiting
	 * 
	 * @return waiting
	 */
	public Long getWaiting() {
		return waiting;
	}

	/**
	 * setWaiting
	 * 
	 * @param waiting
	 */
	public void setWaiting(Long waiting) {
		this.waiting = waiting;
	}

	/**
	 * getRoomID
	 * 
	 * @return roomID
	 */
	public long getRoomID() {
		return roomID;
	}

	/**
	 * getRoomID
	 * 
	 * @param roomID
	 */
	public void setRoomID(long roomID) {
		this.roomID = roomID;
	}

	/**
	 * getOccupiedBy
	 * 
	 * @return occupiedBy
	 */
	public Long getOccupiedBy() {
		return occupiedBy;
	}

	/**
	 * setOccupiedBy
	 * 
	 * @param occupiedBy
	 */
	public void setOccupiedBy(Long occupiedBy) {
		this.occupiedBy = occupiedBy;
	}

	/**
	 * getInWard
	 * 
	 * @return inWard
	 */
	public long getInWard() {
		return inWard;
	}

	/**
	 * setInWard
	 * 
	 * @param inWard
	 */
	public void setInWard(long inWard) {
		this.inWard = inWard;
	}

	/**
	 * getRoomName
	 * 
	 * @return roomName
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * setRoomName
	 * 
	 * @param roomName
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * getStatus
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * setStatus
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * getPrice
	 * 
	 * @return price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * setPrice
	 * 
	 * @param price
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * getStory
	 * 
	 * @return story
	 */
	public int getStory() {
		return story;
	}

	/**
	 * setStory
	 * 
	 * @param story
	 */
	public void setStory(int story) {
		this.story = story;
	}

	/**
	 * Overrided equals function
	 */
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass().equals(this.getClass()) && this.equals((WardRoomBean) obj);
	}

	/**
	 * Overrided hashCode function
	 */
	@Override
	public int hashCode() {
		return 42; // any arbitrary constant will do
	}

	/**
	 * equal function
	 * 
	 * @param other
	 * @return isEqual
	 */
	private boolean equals(WardRoomBean other) {
		return roomID == other.roomID && roomName.equals(other.roomName);
	}
}

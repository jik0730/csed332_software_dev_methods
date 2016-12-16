package edu.ncsu.csc.itrust.beans;

public class ApptTypeBean {

	private String name;
	private int duration;
	private int price; // added for UC60

	/**
	 * ApptTypeBean Constructure
	 */
	public ApptTypeBean() {
		this.name = null;
		this.duration = 0;
	}

	/**
	 * 
	 * @param name
	 * @param duration
	 */
	public ApptTypeBean(String name, int duration) {
		this.name = name;
		this.duration = duration;
	}

	/**
	 * return getName
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * setting name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get duration
	 * @return duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * set duration.
	 * @param duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * get price
	 * @return price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * set price
	 * @param price
	 */
	public void setPrice(int price) {
		this.price = price;
	}
}

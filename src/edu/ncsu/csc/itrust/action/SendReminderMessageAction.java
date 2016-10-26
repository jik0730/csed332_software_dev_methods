package edu.ncsu.csc.itrust.action;

import org.apache.commons.lang.NotImplementedException;

import edu.ncsu.csc.itrust.dao.DAOFactory;

/**
 * SendReminderMessageAction
 */
public class SendReminderMessageAction extends ApptAction {

	/**
	 * SendReminderMessageAction
	 */
	public SendReminderMessageAction(DAOFactory factory) {
		super(factory);
		//TODO: Implement Constructor
		throw new NotImplementedException("Stub");
	}
	
	/**
	 * Send reminder Messages to patients who have appointments within given days
	 * 
	 * @param apptDaysLeftUpperBound Days left until an appointment
	 */
	public void sendReminderMessage(int apptDaysLeftUpperBound) {
		//TODO: Implement sending reminder messages
		throw new NotImplementedException("Stub");
	}
}
package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.MessageBean;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.FakeEmailDAO;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;

import edu.ncsu.csc.itrust.enums.TransactionType;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * SendReminderMessageAction
 */
public class SendReminderMessageAction extends ApptAction {
	ApptDAO apptDAO;
	PatientDAO patientDAO;
	PersonnelDAO personnelDAO;
	FakeEmailDAO fakeEmailDAO;
	MessageDAO messageDAO;
	TransactionDAO transactionDAO;
	AuthDAO authDAO;
	long loggedInMID;
	
	// Constructor
	/** 
	 * @param factory
	 * @param loggedInMID
	 * @throws ITrustException
	 */
	public SendReminderMessageAction(DAOFactory factory, long loggedInMID) throws ITrustException {
		super(factory);
		apptDAO = factory.getApptDAO();
		patientDAO = factory.getPatientDAO();
		personnelDAO = factory.getPersonnelDAO();
		fakeEmailDAO = factory.getFakeEmailDAO();
		messageDAO = factory.getMessageDAO();
		transactionDAO = factory.getTransactionDAO();
		authDAO = factory.getAuthDAO();
		
		try {
			if (authDAO.getUserRole(loggedInMID).getUserRolesString() != "admin")
				throw new ITrustException("Only admin can use this feature.");
		} catch (ITrustException e) {
			throw e;
		}
			
		this.loggedInMID = loggedInMID;
	}
	
	// Send reminder Messages to patients who have appointments within given days
	/**
	 *
	 * @param apptDaysLeftUpperBound
	 * @throws SQLException
	 * @throws DBException
	 * @throws ITrustException
	 */
	public void sendReminderMessage(long apptDaysLeftUpperBound) throws SQLException, DBException, ITrustException {
		List<ApptBean> appts = apptDAO.getApptsIn(apptDaysLeftUpperBound);
		
		for (ApptBean appt: appts) {
			sendReminders(appt);
		}
		
		transactionDAO.logTransaction(TransactionType.SYSTEM_REMINDERS_VIEW, loggedInMID, 0, "");
	}
	
	private void sendReminders(ApptBean appt) throws SQLException, DBException, ITrustException {
		long from = 9000000009L; //System Reminder
		long to = appt.getPatient();
		long daysLeft = appt.getDate().getTime()/(24 * 1000 * 60 * 60) - new Date().getTime() / (24 * 60 * 60 * 1000);
		String doctorLastName = personnelDAO.getName(appt.getHcp()).split("\\s")[0];
		String body = genBody(appt.getDate(), doctorLastName);
		String subject = genSubject(daysLeft);
	
		sendMessage(from, to, subject, body);
		sendEmail(from, to, subject, body);
	}
	
	private void sendMessage(long from, long to, String subject, String body) throws SQLException, DBException {
		MessageBean message = new MessageBean();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setBody(body);
		
		messageDAO.addMessage(message);
	}

	private void sendEmail(long from, long to, String subject, String body) throws ITrustException{
		Email email = new Email();
		email.setFrom("System Reminder");
		List<String> toList = Arrays.asList(patientDAO.getEmailFor(to));
		email.setToList(toList);
		email.setSubject(subject);
		email.setBody(body);
		fakeEmailDAO.sendEmailRecord(email);
	}

	private String genSubject(long daysLeft) {
		return "Reminder: upcoming appointment in " + daysLeft + " day(s)";
	}

	private String genBody(Timestamp date, String doctorName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return "You have an appointment on " + sdf.format(date) + ", with Dr. " + doctorName;
	}
}
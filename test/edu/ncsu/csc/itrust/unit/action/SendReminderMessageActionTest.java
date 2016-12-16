package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FakeEmailDAO;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.action.SendReminderMessageAction;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.beans.TransactionBean;

public class SendReminderMessageActionTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private SendReminderMessageAction action;
	private MessageDAO messageDAO = factory.getMessageDAO();
	private FakeEmailDAO fakeEmailDAO = factory.getFakeEmailDAO();
	private TransactionDAO transactionDAO = factory.getTransactionDAO();
	
	private final String REMINDER_SUBJECT_LT = "Reminder: upcoming appointment in 6 day(s)";
	private final String REMINDER_SUBJECT_EQ = "Reminder: upcoming appointment in 7 day(s)";
	private final String REMINDER_SUBJECT_GT = "Reminder: upcoming appointment in 8 day(s)";

	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private final long oneSecond = 1000;
	private final long oneMinute = 60 * oneSecond;
	private final long oneDay = 24 * 60 * oneMinute;
	private final Date curDate = new Date();
	private final Date ltDate = new Date(curDate.getTime() + oneDay * 6);
	private final Date eqDate = new Date(curDate.getTime() + oneDay * 7);
	
	private final long adminMID = 9000000001L;

	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.uc41_add_appointments();
		action = new SendReminderMessageAction(factory, adminMID);
	}

	@Test
	public void testSendReminderMessageAction() {
		try {
			SendReminderMessageAction actionWithInvalidRole = new SendReminderMessageAction(factory, 9000000000L);
		} catch (ITrustException e) {
		}
		
		assertTrue(false);
	}
	
	@Test
	public void testSendReminderMessage() throws DBException, SQLException, ITrustException {
		action.sendReminderMessage(7);
		checkMessages();
		checkEmails();
		checkLogs();
	}
	
	private void checkMessages() throws DBException, SQLException {
		List<MessageBean> messages = messageDAO.getMessagesFor(100);
		MessageBean ltMsg = null, eqMsg = null, gtMsg = null;
		
		for(MessageBean msg: messages) {
			if(REMINDER_SUBJECT_LT.equals(msg.getSubject())) ltMsg = msg;
			if(REMINDER_SUBJECT_EQ.equals(msg.getSubject())) eqMsg = msg;
			if(REMINDER_SUBJECT_GT.equals(msg.getSubject())) gtMsg = msg;
		}
	
		assertTrue(ltMsg != null);
		assertTrue(eqMsg != null);
		assertTrue(gtMsg == null);
		assertTrue(getReminderBody(ltDate).equals(ltMsg.getBody()));
		assertTrue(getReminderBody(eqDate).equals(eqMsg.getBody()));
		assertTrue(ltMsg.getFrom() == 9000000009L);
		assertTrue(eqMsg.getFrom() == 9000000009L);
	}
	
	private void checkEmails() throws DBException, SQLException {
		Email ltMail = null, eqMail = null, gtMail = null;
		List<Email> mails = fakeEmailDAO.getEmailWithBody("You have an appointment on");
		for(Email mail: mails) {
			if(REMINDER_SUBJECT_LT.equals(mail.getSubject())) ltMail = mail;
			if(REMINDER_SUBJECT_EQ.equals(mail.getSubject())) eqMail = mail;
			if(REMINDER_SUBJECT_GT.equals(mail.getSubject())) gtMail = mail;
		}
		
		assertTrue(ltMail != null);
		assertTrue(eqMail != null);
		assertTrue(gtMail == null);
		assertTrue(getReminderBody(ltDate).equals(ltMail.getBody()));
		assertTrue(getReminderBody(eqDate).equals(eqMail.getBody()));
		assertTrue("System Reminder".equals(ltMail.getFrom()));
		assertTrue("System Reminder".equals(eqMail.getFrom()));
	}
	
	private void checkLogs() throws DBException, SQLException {
		List<TransactionBean> transactions = transactionDAO.getTransactionList(adminMID, 0, new Date(curDate.getTime() - oneSecond), new Date(curDate.getTime() + oneMinute), 4100);
		assertTrue(transactions.size() == 1);
		
		TransactionBean log = transactions.get(0);
		assertTrue(log.getTransactionType() == TransactionType.SYSTEM_REMINDERS_VIEW);
	}
	
	private String getReminderBody(Date date) {
		return "You have an appointment on " + format.format(date) + ", with Dr. Kelly";
	}
}
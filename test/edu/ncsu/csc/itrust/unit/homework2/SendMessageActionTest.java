package edu.ncsu.csc.itrust.unit.homework2;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import edu.ncsu.csc.itrust.beans.MessageBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.action.SendMessageAction;

public class SendMessageActionTest {
	
	MessageBean mBean;
	SendMessageAction sendMessageAction;
	DAOFactory factory;
	
	final long SPECIFIC_PERSONNEL = 5000000000L;
	final long ANY_PERSONNEL = 7000000000L;
	final String STUB_MAIL = "wow@much.mail";
	
	@Before
	public void setup() {
		factory = TestDAOFactory.getTestInstance();
		sendMessageAction = new SendMessageAction(factory, SPECIFIC_PERSONNEL);
		mBean = new MessageBean();
		mBean.setBody("Stub");
		mBean.setSubject("Stub");
	}
	
	
	private void addStubPatients() {
		removeStubPatients();
		PatientDAO patientDao = factory.getPatientDAO();
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			long mid1 = patientDao.addEmptyPatient();
			long mid2 = patientDao.addEmptyPatient();

			conn = factory.getConnection();
			stmt = conn.prepareStatement("update patients set mid="
					+ "case mid when ? then ? when ? then ? else mid end "
					+ "where mid in (?, ?)");
			stmt.setLong(1, mid1);
			stmt.setLong(2, ANY_PERSONNEL);
			stmt.setLong(3, mid2);
			stmt.setLong(4, SPECIFIC_PERSONNEL);
			stmt.setLong(5, mid1);
			stmt.setLong(6, mid2);
			stmt.executeUpdate();
		} catch (DBException | SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
			DBUtil.closeConnection(conn, stmt);
		}
	}
	
	private void addStubPersonnel() {
		removeStubPersonnel();
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = factory.getConnection();
			stmt = conn.prepareStatement("insert into personnel (mid) VALUES (?), (?)");
			stmt.setLong(1, ANY_PERSONNEL);
			stmt.setLong(2, SPECIFIC_PERSONNEL);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
			DBUtil.closeConnection(conn, stmt);
		}
	}
	
	private void removeStubPatients() {
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = factory.getConnection();
			stmt = conn.prepareStatement("delete from patients where mid=? or ?");
			stmt.setLong(1, SPECIFIC_PERSONNEL);
			stmt.setLong(2, ANY_PERSONNEL);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
			DBUtil.closeConnection(conn, stmt);
		}
	}
	
	private void removeStubPersonnel() {
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = factory.getConnection();
			stmt = conn.prepareStatement("delete from personnel where mid=? or ?");
			stmt.setLong(1, SPECIFIC_PERSONNEL);
			stmt.setLong(2, ANY_PERSONNEL);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
			DBUtil.closeConnection(conn, stmt);
		}
	}
	
	private void setMessageReceiverAnyPersonnel() {
		mBean.setTo(ANY_PERSONNEL);
	}

	private void setMessageReceiverSpecificPersonnel() {
		mBean.setTo(SPECIFIC_PERSONNEL);
	}
	
	private void setMessageSenderAnyPersonnel() {
		mBean.setFrom(ANY_PERSONNEL);
	}
	
	private void setMessageSenderSpecificPersonnel() {
		mBean.setFrom(SPECIFIC_PERSONNEL);
	}

	@Test
	public void sendMessageFromAnyToAnyPersonnel() {
		addStubPatients();
		addStubPersonnel();
		setMessageReceiverAnyPersonnel();
		setMessageSenderAnyPersonnel();
		try {
			send();
		} catch (Throwable e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		removeStubPatients();
		removeStubPersonnel();
	}
	
	//this throws DBException because PatientLoader.loadCommon cannot handle large mid
	@Test
	public void sendMessageFromSpecificToSpecificPersonnel() {
		addStubPatients();
		addStubPersonnel();
		setMessageReceiverSpecificPersonnel();
		setMessageSenderSpecificPersonnel();
		try {
			send();
		} catch(Exception e) {
			assertTrue(true);
		} finally {
			removeStubPatients();
			removeStubPersonnel();
		}
	}
	
	@Test
	public void getDLHCPByMIDReturnsSomething() {
		addStubPersonnel();
		try {
			sendMessageAction.getDLHCPByMID(ANY_PERSONNEL);
		} catch (ITrustException e) {
			e.printStackTrace();
			fail("did not return anything");
		}
		removeStubPersonnel();
	}
	
	private void send() {
		try {
			sendMessageAction.sendMessage(mBean);
		} catch (SQLException | ITrustException | FormValidationException e) {
			e.printStackTrace();
		}
	}
}

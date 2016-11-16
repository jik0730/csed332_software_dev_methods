package edu.ncsu.csc.itrust.uc92;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.AddApptRequestAction;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.ApptRequestBean;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class UC92AppointmentRequestTest{
	private AddApptRequestAction action;
	private TestDataGenerator gen = new TestDataGenerator();
	
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.uc92();
		gen.hcp921();
		gen.hcp922();
		gen.apptRequestConflicts();
		action = new AddApptRequestAction(TestDAOFactory.getTestInstance());
	}
	
	@Test
	public void patientRequestforPT() throws Exception {
		ApptBean b = new ApptBean();
		b.setApptType("Physical Therapy");
		b.setHcp(9210000000L); // Physical Therapiest's MID
		b.setPatient(2L);
		b.setComment("Test for UC92");
		
		String time = "11:00 AM";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat fo = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 7);
		time = fo.format(cal.getTime()) + " " + time;
		fo = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		Date d = fo.parse(time);
		
		b.setDate(new Timestamp(d.getTime()));
		
		ApptRequestBean req = new ApptRequestBean();
		String expected = "Your appointment request has been saved and is pending.";
		req.setRequestedAppt(b);
		assertEquals(expected, action.addApptRequest(req));
	}
	
	
}

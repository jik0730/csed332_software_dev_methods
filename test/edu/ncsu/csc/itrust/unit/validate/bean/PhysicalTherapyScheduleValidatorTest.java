package edu.ncsu.csc.itrust.unit.validate.bean;

import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyScheduleOVRecordBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.PhysicalTherapyScheduleOVValidator;

public class PhysicalTherapyScheduleValidatorTest {

	PhysicalTherapyScheduleOVValidator v;

	/**
	 * setup for test environment
	 */
	@Before
	public void setUp() {
		v = new PhysicalTherapyScheduleOVValidator();
	}

	/**
	 * Test validating to null bean
	 */
	@Test
	public void testValidateAllNull() {
		PhysicalTherapyScheduleOVRecordBean badbean = new PhysicalTherapyScheduleOVRecordBean();
		try {
			v.validate(badbean);
			fail();
		} catch (FormValidationException e) {
			// do nothing, we made it
		}
	}

	/**
	 * Test validating to no doctor name bean
	 */
	@Test
	public void testNoDoctorName() {
		PhysicalTherapyScheduleOVRecordBean badbean = new PhysicalTherapyScheduleOVRecordBean();
		badbean.setComment("Comment");
		badbean.setDoctormid(9210000000L);
		badbean.setPatientmid(102);
		badbean.setPending(true);
		SimpleDateFormat frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Date d;
		try {
			d = frmt.parse("20/20/1994 10:22 PM");
			badbean.setDate(new Timestamp(d.getTime()));
		} catch (ParseException e1) {
		}
		try {
			v.validate(badbean);
			fail();
		} catch (FormValidationException e) {
		}
	}

	/**
	 * Test for validating to no date bean
	 */
	@Test
	public void testNoDate() {
		PhysicalTherapyScheduleOVRecordBean badbean = new PhysicalTherapyScheduleOVRecordBean();
		badbean.setComment("Comment");
		badbean.setDoctormid(9210000000L);
		badbean.setPatientmid(102);
		badbean.setPending(true);
		badbean.setDocFirstName("Taylor");
		badbean.setDocLastName("Physical Therapist");
		try {
			v.validate(badbean);
			fail();
		} catch (FormValidationException e) {
			// do nothing, we made it
		}
	}
}
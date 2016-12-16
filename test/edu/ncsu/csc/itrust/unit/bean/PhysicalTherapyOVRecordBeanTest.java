package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean;

/**
 * Test for physical therapy office visit bean worked well
 *
 */
public class PhysicalTherapyOVRecordBeanTest {
	/**
	 * Test for setter and getter of bean
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testGetSet() throws ParseException {
		PhysicalTherapyOVRecordBean bean1 = new PhysicalTherapyOVRecordBean();
		bean1.setMid(1L);
		bean1.setOid(2L);
		bean1.setFirstName("Kelly");
		bean1.setLastName("Doctor");
		bean1.setVisitDate("01/22/2015");
		String survey = "100,0,0,0,0,0,0,0,0,0";
		bean1.setWellnessSurveyResults(survey);
		bean1.setWellnessSurveyScore(10L);
		String exercise = "false,true,false,false,true,false,false,true,false,true";
		bean1.setExercise(exercise);

		assertEquals(bean1.getMid(), 1L);
		assertEquals(bean1.getOid(), 2L);
		assertEquals(bean1.getLastName(), "Doctor");
		assertEquals(bean1.getFirstName(), "Kelly");
		assertEquals(bean1.getVisitDateString(), "01/22/2015");
		assertEquals(bean1.getVisitDate(), new SimpleDateFormat("MM/dd/yyyy").parse("01/22/2015"));
		assertEquals(bean1.getExercise(), "false,true,false,false,true,false,false,true,false,true");

		String[] splitted = exercise.split(",");
		String[] exercises = bean1.getExerciseInArray();
		for (int i = 0; i < 10; i++) {
			assertEquals(exercises[i], splitted[i]);
		}
		assertEquals(bean1.getWellnessSurveyResults(), survey);

		splitted = survey.split(",");
		String[] surveys = bean1.getWellnessSurveyResultsInArray();
		for (int i = 0; i < 10; i++) {
			assertEquals(surveys[i], splitted[i]);
		}
		assertEquals(bean1.getWellnessSurveyScore(), 10L);
	}

	/**
	 * Test for invalid dates inputs
	 */
	@Test
	public void testInvalidDates() {
		// Make a bean with an invalid date
		PhysicalTherapyOVRecordBean bean1 = new PhysicalTherapyOVRecordBean();
		bean1.setVisitDate("Not even a date");
		assertNull(bean1.getVisitDate());

		bean1.setVisitDate("2015-12-12");
		assertNull(bean1.getVisitDate());

		bean1.setVisitDate(null);
		assertNull(bean1.getVisitDate());
	}

	/**
	 * Test for equals of bean
	 */
	@Test
	public void testEquals() {
		PhysicalTherapyOVRecordBean bean1 = new PhysicalTherapyOVRecordBean();
		PhysicalTherapyOVRecordBean bean2 = new PhysicalTherapyOVRecordBean();

		assertTrue(bean1.equals(bean1));
		assertTrue(!bean1.equals(null));

		bean1.setMid(0);
		bean2.setMid(1);

		assertTrue(!bean1.equals(bean2));

		bean2.setMid(0);
		bean1.setOid(0);
		bean2.setOid(1);

		assertTrue(!bean1.equals(bean2));

		bean2.setOid(0);
		bean1.setVisitDate("2015-12-12");
		bean2.setVisitDate("2015-12-13");

		assertTrue(!bean1.equals(bean2));

		bean2.setVisitDate("2015-12-12");
		bean1.setLastName("Doctor");
		bean2.setLastName("Fisher");

		assertTrue(!bean1.equals(bean2));

		bean2.setLastName("Doctor");
		bean1.setFirstName("Kelly");
		bean2.setFirstName("Celly");

		assertTrue(!bean1.equals(bean2));

		bean2.setFirstName("Kelly");
		bean1.setWellnessSurveyResults("100,0,0,0,0,0,0,0,0,0");
		bean2.setWellnessSurveyResults("100,0,0,0,0,0,0,0,0,100");

		assertTrue(!bean1.equals(bean2));

		bean2.setWellnessSurveyResults("100,0,0,0,0,0,0,0,0,0");
		bean1.setWellnessSurveyScore(10);
		bean2.setWellnessSurveyScore(100);

		assertTrue(!bean1.equals(bean2));

		bean2.setWellnessSurveyScore(10);
		bean1.setExercise("false,true,false,false,true,false,false,true,false,true");
		bean2.setExercise("false,true,false,false,true,false,false,true,false,false");

		assertTrue(!bean1.equals(bean2));
	}

	/**
	 * Test for equality of two beans
	 */
	@Test
	public void testEquality() {
		PhysicalTherapyOVRecordBean bean1 = new PhysicalTherapyOVRecordBean();

		bean1.setMid(92210000000L);
		bean1.setOid(14753L);
		bean1.setFirstName("Talyor");
		bean1.setLastName("Physical therapist");
		bean1.setVisitDate("2016-12-17");
		bean1.setWellnessSurveyResults("100,0,0,0,0,0,0,0,0,0");
		bean1.setWellnessSurveyScore(10);
		bean1.setExercise("false,true,false,false,true,false,false,true,false,true");

		PhysicalTherapyOVRecordBean bean2 = new PhysicalTherapyOVRecordBean();

		bean2.setMid(92210000000L);
		bean2.setOid(14753L);
		bean2.setFirstName("Talyor");
		bean2.setLastName("Physical therapist");
		bean2.setVisitDate("2016-12-17");
		bean2.setWellnessSurveyResults("100,0,0,0,0,0,0,0,0,0");
		bean2.setWellnessSurveyScore(10);
		bean2.setExercise("false,true,false,false,true,false,false,true,false,true");

		assertEquals(bean1, bean2);
		assertEquals(bean1.toString(), bean2.toString());
		assertEquals(bean1.hashCode(), bean2.hashCode());

		bean1.setMid(9220000000L);
		assertFalse(bean1.equals(bean2));

	}

}
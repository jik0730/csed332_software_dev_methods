package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean;

public class PhysicalTherapyOVRecordBeanTest {
	/**Allowed value for the difference of two 'equivalent' doubles.*/
	private static final double ERROR = 0.000001;

	@Test
	public void testGetSet() throws ParseException{
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
		assertEquals(bean1.getExercise(),"false,true,false,false,true,false,false,true,false,true");
		
		String[] splitted = exercise.split(",");
		String[] exercises = bean1.getExerciseInArray();
		for(int i=0; i<10; i++){
			assertEquals(exercises[i], splitted[i]);
		}
		assertEquals(bean1.getWellnessSurveyResults(), survey);
		
		splitted = survey.split(",");
		String[] surveys = bean1.getWellnessSurveyResultsInArray();
		for(int i=0; i<10; i++){
			assertEquals(surveys[i], splitted[i]);
		}
		assertEquals(bean1.getWellnessSurveyScore(), 10L);
	}
	
	@Test
	public void testInvalidDates(){
		// Make a bean with an invalid date
		PhysicalTherapyOVRecordBean bean1 = new PhysicalTherapyOVRecordBean();
		bean1.setVisitDate("Not even a date");
		assertNull(bean1.getVisitDate());
		
		bean1.setVisitDate("2015-12-12");
		assertNull(bean1.getVisitDate());
		
		bean1.setVisitDate(null);
		assertNull(bean1.getVisitDate());
	}
	 
	@Test
	public void testEquals() {
		PhysicalTherapyOVRecordBean bean1 = new PhysicalTherapyOVRecordBean();
		PhysicalTherapyOVRecordBean bean2 = new PhysicalTherapyOVRecordBean();
		Integer tmpInteger = new Integer(0);
		
		assertTrue(bean1.equals(bean1));
		assertTrue(!bean1.equals(null));
		assertTrue(!bean1.equals(tmpInteger));
		
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
}

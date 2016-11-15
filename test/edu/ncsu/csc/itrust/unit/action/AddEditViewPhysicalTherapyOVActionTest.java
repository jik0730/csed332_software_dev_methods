package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.action.AddPhysicalTherapyOVAction;
import edu.ncsu.csc.itrust.action.ViewPhysicalTherapyOVAction;
import edu.ncsu.csc.itrust.action.EditPhysicalTherapyOVAction;

public class AddEditViewPhysicalTherapyOVActionTest {
	private final long LOGGED_IN_MID = 9220000000L;
	private final long PATIENT_MID = 407L;
	private final long DEPENDENT_MID = 409L;
	
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}
	
	@Test
	public void testAddPhysicalTherapyOV() throws FormValidationException, ITrustException{
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		
		AddPhysicalTherapyOVAction addAction = new AddPhysicalTherapyOVAction(prodDAO, LOGGED_IN_MID);
		
		//Create a valid bean
		PhysicalTherapyOVRecordBean bean1 = new PhysicalTherapyOVRecordBean();
		bean1.setMid(407L);
		bean1.setOid(1L);
		bean1.setFirstName("Kelly");
		bean1.setLastName("Doctor");
		bean1.setVisitDate("2015-12-12");
		String survey = "100,0,0,0,0,0,0,0,0,0";
		bean1.setWellnessSurveyResults(survey);
		bean1.setWellnessSurveyScore(10L);
		String exercise = "false,true,false,false,true,false,false,true,false,true";
		bean1.setExercise(exercise);
		
		
		//Create another valid bean
		PhysicalTherapyOVRecordBean bean2 = new PhysicalTherapyOVRecordBean();
		bean2.setMid(407L);
		bean2.setOid(2L);
		bean2.setFirstName("Kelly");
		bean2.setLastName("Doctor");
		bean2.setVisitDate("2015-12-13");
		survey = "100,0,0,0,0,0,0,0,0,0";
		bean2.setWellnessSurveyResults(survey);
		bean2.setWellnessSurveyScore(10L);
		exercise = "false,true,false,false,true,false,false,true,false,true";
		bean2.setExercise(exercise);
		
		
		//Create another valid bean
		PhysicalTherapyOVRecordBean bean3 = new PhysicalTherapyOVRecordBean();
		bean3.setMid(407L);
		bean3.setOid(3L);
		bean3.setFirstName("Kelly");
		bean3.setLastName("Doctor");
		bean3.setVisitDate("2015-12-14");
		survey = "100,0,0,0,0,0,0,0,0,0";
		bean3.setWellnessSurveyResults(survey);
		bean3.setWellnessSurveyScore(10L);
		exercise = "false,true,false,false,true,false,false,true,false,true";
		bean3.setExercise(exercise);
		
		
		//Create another valid bean
		PhysicalTherapyOVRecordBean bean4 = new PhysicalTherapyOVRecordBean();
		bean4.setMid(407L);
		bean4.setOid(4L);
		bean4.setFirstName("Kelly");
		bean4.setLastName("Doctor");
		bean4.setVisitDate("2015-12-15");
		survey = "100,0,0,0,0,0,0,0,0,0";
		bean4.setWellnessSurveyResults(survey);
		bean4.setWellnessSurveyScore(10L);
		exercise = "false,true,false,false,true,false,false,true,false,true";
		bean4.setExercise(exercise);
		
		PhysicalTherapyOVRecordBean bean5 = new PhysicalTherapyOVRecordBean();
		bean5.setMid(407L);
		bean5.setOid(5L);
		bean5.setFirstName("Kelly");
		bean5.setLastName("Doctor");
		bean5.setVisitDate("2015-12-16");
		survey = "100,0,0,0,0,0,0,0,0,0";
		bean5.setWellnessSurveyResults(survey);
		bean5.setWellnessSurveyScore(10L);
		exercise = "false,true,false,false,true,false,false,true,false,true";
		bean5.setExercise(exercise);
	
		

		
		
		//Add the beans
		addAction.addPhysicalTherapyOV(bean1);
		addAction.addPhysicalTherapyOV(bean2);
		addAction.addPhysicalTherapyOV(bean3);
		addAction.addPhysicalTherapyOV(bean4);
		addAction.addPhysicalTherapyOV(bean5);
		
		//Now test the view
		ViewPhysicalTherapyOVAction viewAction = new ViewPhysicalTherapyOVAction(prodDAO, LOGGED_IN_MID);
		
		
		List<PhysicalTherapyOVRecordBean> beans = viewAction.getPhysicalTherapyOVByMID(PATIENT_MID);
		assertEquals(beans.get(0), bean5);
		assertEquals(beans.get(1), bean4);
		assertEquals(beans.get(2), bean3);
		assertEquals(beans.get(3), bean2);
		assertEquals(beans.get(4), bean1);
		
		EditPhysicalTherapyOVAction editAction = new EditPhysicalTherapyOVAction(prodDAO, LOGGED_IN_MID);
		editAction.editPhysicalTherapyOV(3L, bean3);
		bean3.setOid(3L);
		assertEquals(viewAction.getPhysicalTherapyOVForHCP(3L), bean3);
	}
	
	@Test
	public void testErrors() throws FormValidationException{
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		AddPhysicalTherapyOVAction addAction = new AddPhysicalTherapyOVAction(prodDAO, 401L);
		EditPhysicalTherapyOVAction editAction = new EditPhysicalTherapyOVAction(prodDAO, 401L);
		
		PhysicalTherapyOVRecordBean nullBean = null;
		
		try{
			addAction.addPhysicalTherapyOV(nullBean);
			fail();
		}catch(ITrustException e){
			assertNull(nullBean);
		}
		
		try{
			editAction.editPhysicalTherapyOV(1, nullBean);
			fail();
		}catch(ITrustException e){
			assertNull(nullBean);
		}
	}
	
}

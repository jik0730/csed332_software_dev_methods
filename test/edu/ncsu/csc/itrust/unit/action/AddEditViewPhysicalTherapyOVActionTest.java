package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean;
import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrderDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.action.AddPhysicalTherapyOVAction;
import edu.ncsu.csc.itrust.action.EditPhysicalTherapyOVAction;
import edu.ncsu.csc.itrust.action.ViewPhysicalTherapyOVAction;

/**
 * test for action of add, edit, view physical therapy OV
 *
 */
public class AddEditViewPhysicalTherapyOVActionTest {
	private final long LOGGED_IN_MID = 9220000000L;
	private final long PATIENT_MID = 407L;
	private final long DEPENDENT_MID = 409L;

	private DAOFactory prodDAO = TestDAOFactory.getTestInstance();
	private AddPhysicalTherapyOVAction addAction = new AddPhysicalTherapyOVAction(prodDAO, LOGGED_IN_MID);

	private PhysicalTherapyOVRecordBean[] patientBeans = new PhysicalTherapyOVRecordBean[5];
	private PhysicalTherapyOVRecordBean dependentBean = new PhysicalTherapyOVRecordBean();

	private String survey = "100,0,0,0,0,0,0,0,0,0";
	private String exercise = "false,true,false,false,true,false,false,true,false,true";
	private String docFirstName = "Kelly";
	private String docLastName = "Doctor";

	private TransactionDAO transactionDAO = prodDAO.getTransactionDAO();

	private final long oneDay = 1000 * 60 * 60 * 24;
	private final Date curDate = new Date();

	/**
	 * Set up before test
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.uc89Order();

		// for patient
		for (int i = 0; i < 5; i++) {
			patientBeans[i] = new PhysicalTherapyOVRecordBean();
			patientBeans[i].setMid(PATIENT_MID);
			patientBeans[i].setVisitDate("12/1" + String.valueOf(2 + i) + "/2015");
			patientBeans[i].setFirstName(docFirstName);
			patientBeans[i].setLastName(docLastName);
			patientBeans[i].setWellnessSurveyResults(survey);
			patientBeans[i].setWellnessSurveyScore(10L);
			patientBeans[i].setExercise(exercise);

			addAction.addPhysicalTherapyOV(patientBeans[i]);
		}

		// for dependent
		dependentBean.setMid(DEPENDENT_MID);
		dependentBean.setVisitDate("12/17/2015");
		dependentBean.setFirstName(docFirstName);
		dependentBean.setLastName(docLastName);
		dependentBean.setWellnessSurveyResults(survey);
		dependentBean.setWellnessSurveyScore(10L);
		dependentBean.setExercise(exercise);

		addAction.addPhysicalTherapyOV(dependentBean);
	}

	/**
	 * Test for add physical therapy office visit well
	 * 
	 * @throws FormValidationException
	 * @throws ITrustException
	 */
	@Test
	public void testAddPhysicalTherapyOV1() throws FormValidationException, ITrustException {
		// Now test the view
		ViewPhysicalTherapyOVAction viewAction = new ViewPhysicalTherapyOVAction(prodDAO, LOGGED_IN_MID);
		List<PhysicalTherapyOVRecordBean> beans = viewAction.getPhysicalTherapyOVByMID(PATIENT_MID);
		assertEquals(beans.get(0), patientBeans[4]);
		assertEquals(beans.get(1), patientBeans[3]);
		assertEquals(beans.get(2), patientBeans[2]);
		assertEquals(beans.get(3), patientBeans[1]);
		assertEquals(beans.get(4), patientBeans[0]);

		EditPhysicalTherapyOVAction editAction = new EditPhysicalTherapyOVAction(prodDAO, LOGGED_IN_MID);
		editAction.editPhysicalTherapyOV(3L, patientBeans[2]);
		patientBeans[2].setOid(3L);
		assertEquals(viewAction.getPhysicalTherapyOVForHCP(3L), patientBeans[2]);
	}

	/**
	 * Test for fail to add physical therapy ov
	 * 
	 * @throws FormValidationException
	 * @throws ITrustException
	 */
	@Test
	public void testAddPhysicalTherapyOV2() throws FormValidationException, ITrustException {
		PhysicalTherapyOVRecordBean trollBean = new PhysicalTherapyOVRecordBean();
		trollBean.setMid(0);
		trollBean.setVisitDate("12/17/2015");
		trollBean.setFirstName(docFirstName);
		trollBean.setLastName(docLastName);
		trollBean.setWellnessSurveyResults(survey);
		trollBean.setWellnessSurveyScore(10L);
		trollBean.setExercise(exercise);

		try {
			addAction.addPhysicalTherapyOV(trollBean);
			assertTrue(false);
		} catch (ITrustException e) {
		}

	}

	/**
	 * Test for get physical thearpy OV by pid
	 * 
	 * @throws ITrustException
	 */
	@Test
	public void testGetPhysicalTherapyOVForPatient() throws ITrustException {
		ViewPhysicalTherapyOVAction viewAction = new ViewPhysicalTherapyOVAction(prodDAO, PATIENT_MID);
		long oid = 1;
		PhysicalTherapyOVRecordBean bean = viewAction.getPhysicalTherapyOVForPatient(oid);

		assertEquals(bean.getOid(), oid);

		List<TransactionBean> transactions = transactionDAO.getTransactionList(PATIENT_MID, PATIENT_MID,
				new Date(curDate.getTime() - oneDay), curDate, 9100);

		assertEquals(1, transactions.size());
	}

	/**
	 * Test view action of dependent
	 * 
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	@Test
	public void testGetPhysicalTherapyOVForDependent() throws ITrustException, FormValidationException {
		ViewPhysicalTherapyOVAction viewAction = new ViewPhysicalTherapyOVAction(prodDAO, PATIENT_MID);
		long oid = 6;
		PhysicalTherapyOVRecordBean bean = viewAction.getPhysicalTherapyOVForDependent(oid);

		assertEquals(bean.getOid(), oid);

		List<TransactionBean> transactions = transactionDAO.getTransactionList(PATIENT_MID, DEPENDENT_MID,
				new Date(curDate.getTime() - oneDay), curDate, 9101);

		assertEquals(1, transactions.size());
	}

	/**
	 * Test get dependents
	 */
	@Test
	public void testGetDependents() {
		ViewPhysicalTherapyOVAction viewAction = new ViewPhysicalTherapyOVAction(prodDAO, LOGGED_IN_MID);
		List<PatientBean> dependents = viewAction.getDependents(PATIENT_MID);

		assertEquals(2, dependents.size());
		assertEquals(DEPENDENT_MID, dependents.get(0).getMID());
		assertEquals(410, dependents.get(1).getMID());
	}

	/**
	 * Test error checking well for action
	 * 
	 * @throws FormValidationException
	 */
	@Test
	public void testErrors() throws FormValidationException {
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		AddPhysicalTherapyOVAction addAction = new AddPhysicalTherapyOVAction(prodDAO, 401L);
		EditPhysicalTherapyOVAction editAction = new EditPhysicalTherapyOVAction(prodDAO, 401L);

		PhysicalTherapyOVRecordBean nullBean = null;

		try {
			addAction.addPhysicalTherapyOV(nullBean);
			fail();
		} catch (ITrustException e) {
			assertNull(nullBean);
		}

		try {
			editAction.editPhysicalTherapyOV(1, nullBean);
			fail();
		} catch (ITrustException e) {
			assertNull(nullBean);
		}
	}

}

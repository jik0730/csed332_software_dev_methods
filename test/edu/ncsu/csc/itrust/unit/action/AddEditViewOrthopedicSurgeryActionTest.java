package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddOrthopedicSurgeryAction;
import edu.ncsu.csc.itrust.action.EditOrthopedicSurgeryAction;
import edu.ncsu.csc.itrust.action.ViewOrthopedicSurgeryAction;
import edu.ncsu.csc.itrust.beans.OrthopedicSurgeryRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class AddEditViewOrthopedicSurgeryActionTest {
	private final long LOGGED_IN_MID = 9000000086L;
	private final long PATIENT_MID = 407L;
	private final long DEPENDENT_MID = 409L;
	
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.uc90Order();
	}
	
	@Test
	public void testAddOrthopedicSurgery() throws FormValidationException, ITrustException{
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		
		AddOrthopedicSurgeryAction addAction = new AddOrthopedicSurgeryAction(prodDAO, LOGGED_IN_MID);
		
		//Create a valid bean
		OrthopedicSurgeryRecordBean bean1 = new OrthopedicSurgeryRecordBean();
		bean1.setMid(PATIENT_MID);
		bean1.setVisitDate("01/22/2015");
		bean1.setLastName("Bridges");
		bean1.setFirstName("Lamar");
		bean1.setSurgery("Total knee replacement");
		bean1.setSurgeryNotes("Surgery successful.");
		
		//Create another valid bean
		OrthopedicSurgeryRecordBean bean2 = new OrthopedicSurgeryRecordBean();
		bean2.setMid(PATIENT_MID);
		bean2.setVisitDate("01/23/2015");
		bean2.setLastName("Bridges");
		bean2.setFirstName("Lamar");
		bean2.setSurgery("Total knee replacement");
		bean2.setSurgeryNotes("Surgery was boring.");
		
		//Create another valid bean
		OrthopedicSurgeryRecordBean bean3 = new OrthopedicSurgeryRecordBean();
		bean3.setMid(PATIENT_MID);
		bean3.setVisitDate("01/24/2015");
		bean3.setLastName("Tran");
		bean3.setFirstName("Brooke");
		bean3.setSurgery("ACL reconstruction");
		bean3.setSurgeryNotes("Yet another surgery.");
		
		//Create another valid bean
		OrthopedicSurgeryRecordBean bean4 = new OrthopedicSurgeryRecordBean();
		bean4.setMid(PATIENT_MID);
		bean4.setVisitDate("01/26/2015");
		bean4.setLastName("Tran");
		bean4.setFirstName("Brooke");
		// Surgery and notes aren't required
		
		//Create another valid bean
		OrthopedicSurgeryRecordBean bean5 = new OrthopedicSurgeryRecordBean();
		bean5.setMid(DEPENDENT_MID);
		bean5.setVisitDate("01/26/2015");
		bean5.setLastName("Tran");
		bean5.setFirstName("Brooke");
		bean5.setSurgeryNotes("Surgery rescheduled");
		
		//Create unvalid bean
		OrthopedicSurgeryRecordBean bean6 = new OrthopedicSurgeryRecordBean();
		bean6.setMid(1L);
		bean6.setVisitDate("01/26/2015");
		bean6.setLastName("Tran");
		bean6.setFirstName("Brooke");
		bean6.setSurgeryNotes("Surgery rescheduled");
		
		//Add the beans
		addAction.addOrthopedicSurgery(bean1);
		addAction.addOrthopedicSurgery(bean2);
		addAction.addOrthopedicSurgery(bean3);
		addAction.addOrthopedicSurgery(bean4);
		addAction.addOrthopedicSurgery(bean5);
		
			
		
		//Now test the view
		ViewOrthopedicSurgeryAction viewAction = new ViewOrthopedicSurgeryAction(prodDAO, LOGGED_IN_MID);
		
		OrthopedicSurgeryRecordBean retBean = viewAction.getOrthopedicSurgeryForHCP(1L);
		assertEquals(bean1, retBean);
		
		List<OrthopedicSurgeryRecordBean> beans = viewAction.getOrthopedicSurgeryByMID(PATIENT_MID);
		assertEquals(beans.get(0), bean4);
		assertEquals(beans.get(1), bean3);
		assertEquals(beans.get(2), bean2);
		assertEquals(beans.get(3), bean1);
		
		EditOrthopedicSurgeryAction editAction = new EditOrthopedicSurgeryAction(prodDAO, LOGGED_IN_MID);
		editAction.editOrthopedicSurgery(2L, bean3);
		bean3.setOid(2L);
		assertEquals(viewAction.getOrthopedicSurgeryForHCP(2L), bean3);
		
		try {
			addAction.addOrthopedicSurgery(bean6);
		}catch(ITrustException e){
			assertEquals(e.getMessage(), "You can't make surgery without order.");
		}
		// Patients don't have to be able to view their surgical records, per documentation
	}
	
	@Test
	public void testErrors() throws FormValidationException{
		DAOFactory prodDAO = TestDAOFactory.getTestInstance();
		AddOrthopedicSurgeryAction addAction = new AddOrthopedicSurgeryAction(prodDAO, 401L);
		EditOrthopedicSurgeryAction editAction = new EditOrthopedicSurgeryAction(prodDAO, 401L);
		
		OrthopedicSurgeryRecordBean nullBean = null;
		
		try{
			addAction.addOrthopedicSurgery(nullBean);
			fail();
		}catch(ITrustException e){
			assertNull(nullBean);
		}
		
		try{
			editAction.editOrthopedicSurgery(1, nullBean);
			fail();
		}catch(ITrustException e){
			assertNull(nullBean);
		}
	}
	
}

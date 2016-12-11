package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.OrthopedicDiagnosisBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.validate.OrthopedicDiagnosisBeanValidator;
import edu.ncsu.csc.itrust.action.EditORDiagnosesAction;
import junit.framework.TestCase;

public class EditORDiagnosesTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditORDiagnosesAction action;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	//Test actions related to getting Diagnoses
	public void testGetDiagnoses() throws Exception {
		testAddDiagnosis();
		action = new EditORDiagnosesAction(factory, "1");
		List<OrthopedicDiagnosisBean> list = action.getDiagnoses();
		assertEquals(1, list.size());
		assertEquals("13.40", list.get(0).getICDCode());


		// An EditDiagnosesAction without an ovID returns an empty list.
		action = new EditORDiagnosesAction(factory, "105");
		assertEquals(0, action.getDiagnoses().size());
	}

	//test adding various sorts of diagnosis.
	public void testAddDiagnosis() throws Exception {
		action = new EditORDiagnosesAction(factory, "1");
		assertEquals(0, action.getDiagnoses().size());
		OrthopedicDiagnosisBean bean = new OrthopedicDiagnosisBean();
		bean.setICDCode("13.40");
		bean.setVisitID(1);
		bean.setDescription("Whiplash injury");
		action.addDiagnosis(bean);
		assertEquals(1, action.getDiagnoses().size());
		assertEquals("13.40", action.getDiagnoses().get(0).getICDCode());
		assertEquals("Whiplash injury", action.getDiagnoses().get(0).getDescription());
		assertEquals("no",action.getDiagnoses().get(0).getClassification());
	}

	public void testEditDiagnosis() throws Exception {
		testAddDiagnosis();
		action = new EditORDiagnosesAction(factory, "1");
		OrthopedicDiagnosisBean bean = action.getDiagnoses().get(0);
		assertEquals("13.40", bean.getICDCode());
		bean.setICDCode("66.70");
		bean.setDescription("Congenital pes cavus");
		bean.setURL("");
		OrthopedicDiagnosisBeanValidator validator = new OrthopedicDiagnosisBeanValidator();
		validator.validate(bean);
		action.editDiagnosis(bean);
		bean = action.getDiagnoses().get(0);
		assertEquals("66.70", bean.getICDCode());
	}

	public void testDeleteDiagnosis() throws Exception {
		testAddDiagnosis();
		action = new EditORDiagnosesAction(factory, "1");
		assertEquals(1, action.getDiagnoses().size());
		action.deleteDiagnosis(action.getDiagnoses().get(0));
		assertEquals(0, action.getDiagnoses().size());
	}
	

	public void testGetDiagnosisCodes() throws Exception {
		action = new EditORDiagnosesAction(factory, "1");
		List<OrthopedicDiagnosisBean> list = action.getDiagnosisCodes();
		assertEquals(6, list.size());
		
		// It can also be retrieved for an undefined office visit
		action = new EditORDiagnosesAction(factory, "0");
		list = action.getDiagnosisCodes();
		System.out.println(list.size());
		assertEquals(6, list.size());
	}
	
	public void testBadFactory() throws Exception {
		testAddDiagnosis();
		EvilDAOFactory badFact = new EvilDAOFactory(1);
		action = new EditORDiagnosesAction(badFact,"1");
		boolean except=false;
		try{
		OrthopedicDiagnosisBean bean =action.getDiagnoses().get(0);
		action.deleteDiagnosis(bean);
		}
		catch(DBException e){
			except=true;
		}
		
		assertTrue(except);
		
	}

}

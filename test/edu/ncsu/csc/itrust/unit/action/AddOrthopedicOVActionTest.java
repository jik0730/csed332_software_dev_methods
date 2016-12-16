package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddOrthopedicOVAction;
import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicOVRecordDAO;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/** AddOrthopedicOVActionTest */
public class AddOrthopedicOVActionTest {

	DAOFactory factory = TestDAOFactory.getTestInstance();
	OrthopedicOVRecordDAO dao = factory.getOrthopedicOVRecordDAO();
	TestDataGenerator gen = new TestDataGenerator();

	/**
	 * setup
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * test addOrthopedicOV when given null
	 * 
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	@Test(expected=ITrustException.class)
	public void testNull() throws ITrustException, FormValidationException {
		AddOrthopedicOVAction action = new AddOrthopedicOVAction(factory, 9220000000L);
		action.addOrthopedicOV(null);
	}

	/**
	 * test addOrthopedicOV when given a correct bean
	 * 
	 * @throws ITrustException
	 * @throws FormValidationException
	 */
	@Test
	public void testAdd() throws ITrustException, FormValidationException {
		AddOrthopedicOVAction action = new AddOrthopedicOVAction(factory, 9220000000L);
		OrthopedicOVRecordBean p = new OrthopedicOVRecordBean();
		p.setHid(9220000000L);
		p.setInjured("testinjured");
		p.setMriReport("coolandgood");
		p.setVisitDate("11/12/2015");
		p.setPid(1L);
		action.addOrthopedicOV(p);
		
		List<OrthopedicOVRecordBean> beans = dao.getOrthopedicOVRecordsByMID(1L);
		assertEquals(1, beans.size());
		assertEquals("coolandgood", beans.get(0).getMriReport());
	}

}

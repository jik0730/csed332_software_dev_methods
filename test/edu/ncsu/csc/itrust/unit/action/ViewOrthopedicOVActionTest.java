package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.ViewOrthopedicOVAction;
import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicOVRecordDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/** ViewOrthopedicOVActionTest */
public class ViewOrthopedicOVActionTest {

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
		gen.uc88();
	}

	/**
	 * Test if it returns proper bean when given an mid
	 */
	@Test
	public void testMID() throws ITrustException {
		ViewOrthopedicOVAction action = new ViewOrthopedicOVAction(factory, 9220000000L);
		List<OrthopedicOVRecordBean> beans = action.getOrthopedicOVByMID(1L);
		assertEquals(1, beans.size());
		assertEquals("UC88 Injured", beans.get(0).getInjured());
	}

	/**
	 * Test if it returns proper bean when given an oid
	 */
	@Test
	public void testForHCP() throws ITrustException {
		ViewOrthopedicOVAction action = new ViewOrthopedicOVAction(factory, 9220000000L);
		OrthopedicOVRecordBean p = action.getOrthopedicOVForHCP(1);
		assertEquals("UC88 Injured", p.getInjured());
	}

}

package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.ViewPersonnelBySpecialityAction;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/** ViewPersonnelBySpecialtyAction */
public class ViewPersonnelBySpecialtyAction {
	DAOFactory factory = TestDAOFactory.getTestInstance();
	TestDataGenerator gen = new TestDataGenerator();

	/**
	 * Setup
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
	}

	/**
	 * Test Get
	 * 
	 * @throws ITrustException
	 */
	@Test
	public void testGet() throws ITrustException {
		ViewPersonnelBySpecialityAction action = new ViewPersonnelBySpecialityAction(factory, 9220000000L);
		List<PersonnelBean> men = action.getPersonnel("orthopedic");
		assertEquals(1, men.size());
	}

}

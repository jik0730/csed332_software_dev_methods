package edu.ncsu.csc.itrust.UC39.unit;

import java.util.List;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class GetTransactionGroupByTest extends TestCase {
	private TransactionDAO tranDAO = TestDAOFactory.getTestInstance().getTransactionDAO();
	private TransactionDAO evilDAO = EvilDAOFactory.getEvilInstance().getTransactionDAO();
	
	private TestDataGenerator gen;
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.transactionLog();
	}
	
	public void testGetTransactionGroupByLoggedInMID() throws Exception {
		List<TransactionBean> list = tranDAO.getTransactionGroupBy(0);

		assertEquals(4, list.size());
		assertEquals(9000000000L, list.get(0).getLoggedInMID());
	}
	
	public void testGetTransactionGroupBySecondaryMID() throws Exception {
		List<TransactionBean> list = tranDAO.getTransactionGroupBy(1);

		assertEquals(2, list.size());
		assertEquals(2L, list.get(0).getSecondaryMID());
	}
	
	public void testGetTransactionGroupByTransactionCode() throws Exception {
		List<TransactionBean> list = tranDAO.getTransactionGroupBy(2);

		assertEquals(3, list.size());
		assertEquals(1900, list.get(0).getTransactionType().getCode());
	}
	
	public void testInputErrorException() throws Exception {
		try {
			List<TransactionBean> list = evilDAO.getTransactionGroupBy(2);
			fail("DBException should have been thrown");
			list.get(0);
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testInputErrorException2() throws Exception {
		try {
			List<TransactionBean> list = tranDAO.getTransactionGroupBy(3);
			fail("iTrustException should have been thrown");
			list.get(0);
		} catch (ITrustException e) {
			assertEquals("Wrong input type!", e.getMessage());
		}
	}
}

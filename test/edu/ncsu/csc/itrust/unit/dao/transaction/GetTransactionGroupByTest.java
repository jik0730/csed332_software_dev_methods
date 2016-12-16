package edu.ncsu.csc.itrust.unit.dao.transaction;

import java.util.List;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionLogColumnType;
import junit.framework.TestCase;

/**
 * Test getTransaction function of transactionDAO
 * 
 */
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

	/**
	 * test getTransactionGroupByLoggedInMID function of transactionDAO
	 * @throws Exception
	 */
	public void testGetTransactionGroupByLoggedInMID() throws Exception {
		List<TransactionBean> list = tranDAO.getTransactionGroupBy(TransactionLogColumnType.parse(1));

		assertEquals(4, list.size());
		assertEquals(9000000000L, list.get(0).getLoggedInMID());
	}

	/**
	 * test getTransactionGroupBySecondaryMID function of transactionDAO
	 * @throws Exception
	 */
	public void testGetTransactionGroupBySecondaryMID() throws Exception {
		List<TransactionBean> list = tranDAO.getTransactionGroupBy(TransactionLogColumnType.parse(2));

		assertEquals(2, list.size());
		assertEquals(2L, list.get(0).getSecondaryMID());
	}

	/**
	 * test getTransactionGroupByTransactionCode function of transactionDAO
	 * @throws Exception
	 */
	public void testGetTransactionGroupByTransactionCode() throws Exception {
		List<TransactionBean> list = tranDAO.getTransactionGroupBy(TransactionLogColumnType.parse(3));

		assertEquals(3, list.size());
		assertEquals(1900, list.get(0).getTransactionType().getCode());
	}

	/**
	 * Test for DBException
	 * 
	 * @throws Exception
	 */
	public void testInputErrorException() throws Exception {
		try {
			List<TransactionBean> list = evilDAO.getTransactionGroupBy(TransactionLogColumnType.parse(3));
			fail("DBException should have been thrown");
			list.get(0);
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	/**
	 * Test for iTrustException
	 * 
	 * @throws Exception
	 */
	public void testInputErrorException2() throws Exception {
		try {
			List<TransactionBean> list = tranDAO.getTransactionGroupBy(TransactionLogColumnType.parse(4));
			fail("iTrustException should have been thrown");
			list.get(0);
		} catch (ITrustException e) {
			assertEquals("Wrong input type!", e.getMessage());
		}
	}
}

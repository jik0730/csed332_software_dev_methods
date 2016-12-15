package edu.ncsu.csc.itrust.unit.dao.transactionLog;

import java.util.List;

import edu.ncsu.csc.itrust.beans.TransactionLogBean;
import edu.ncsu.csc.itrust.dao.mysql.TransactionLogDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionLogColumnType;
import junit.framework.TestCase;

public class GetTransactionGroupByTest extends TestCase {
	private TransactionLogDAO tranDAO = TestDAOFactory.getTestInstance().getTransactionLogDAO();
	private TransactionLogDAO evilDAO = EvilDAOFactory.getEvilInstance().getTransactionLogDAO();
	
	private TestDataGenerator gen;
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.transactionLog();
	}
	
	public void testGetTransactionLogGroupByLoggedInMID() throws Exception {
		List<TransactionLogBean> list = tranDAO.getTransactionGroupBy(TransactionLogColumnType.parse(1));
		assertEquals(5, list.size());
		
		assertEquals("er", list.get(0).getLoggedInRole());
	}
	
	public void testGetTransactionLogGroupBySecondaryMID() throws Exception {
		List<TransactionLogBean> list = tranDAO.getTransactionGroupBy(TransactionLogColumnType.parse(2));
		assertEquals(1, list.size());
		assertEquals("patient", list.get(0).getSecondaryRole());
	}
	
	public void testGetTransactionLogGroupByTransactionLogCode() throws Exception {
		List<TransactionLogBean> list = tranDAO.getTransactionGroupBy(TransactionLogColumnType.parse(3));
		assertEquals(7, list.size());
		assertEquals(3400, list.get(0).getTransactionType().getCode());
	}
	
	public void testInputErrorException() throws Exception {
		try {
			List<TransactionLogBean> list = evilDAO.getTransactionGroupBy(TransactionLogColumnType.parse(3));
			fail("DBException should have been thrown");
			list.get(0);
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testInputErrorException2() throws Exception {
		try {
			List<TransactionLogBean> list = tranDAO.getTransactionGroupBy(TransactionLogColumnType.parse(4));
			fail("iTrustException should have been thrown");
			list.get(0);
		} catch (ITrustException e) {
			assertEquals("Wrong input type!", e.getMessage());
		}
	}
}

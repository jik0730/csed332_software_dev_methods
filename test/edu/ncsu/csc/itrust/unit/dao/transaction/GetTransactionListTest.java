package edu.ncsu.csc.itrust.unit.dao.transaction;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

import junit.framework.TestCase;


public class GetTransactionListTest extends TestCase {
	private TransactionDAO tranDAO = TestDAOFactory.getTestInstance().getTransactionDAO();
	private TransactionDAO evilDAO = EvilDAOFactory.getEvilInstance().getTransactionDAO();
	
	private TestDataGenerator gen;
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.transactionLog();
	}
	
	public void testGetTransactionList1() throws Exception {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date start = fmt.parse("2007-06-21");
		Date end = fmt.parse("2007-06-25");
		List<TransactionBean> list = tranDAO.getTransactionList(1L, 0L, start, end, 410);
		
		assertEquals(1, list.size());
		assertEquals(1L, list.get(0).getLoggedInMID());
	}
	
	public void testGetTransactionList2() throws Exception {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date start = fmt.parse("2007-06-21");
		Date end = fmt.parse("2007-06-25");
		List<TransactionBean> list = tranDAO.getTransactionList(-1, -1, start, end, -1);
		
		assertEquals(8, list.size());
		assertEquals(9000000000L, list.get(0).getLoggedInMID());
	}
	
	public void testInputErrorException() throws Exception {
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			Date start = fmt.parse("2007-06-21");
			Date end = fmt.parse("2007-06-25");
			List<TransactionBean> list = evilDAO.getTransactionList(1L, 0L, start, end, 410);
			fail("Exception should have been thrown");
			list.get(0);
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}

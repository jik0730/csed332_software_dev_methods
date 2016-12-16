package edu.ncsu.csc.itrust.unit.dao.transactionLog;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncsu.csc.itrust.dao.mysql.TransactionLogDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.beans.TransactionLogBean;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

import junit.framework.TestCase;

/**
 * Test case for function getTransactionList of TransactionLogDAO
 *
 */
public class GetTransactionListTest extends TestCase {
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

	/**
	 * To check function getTransactionList() in specific conditions pass when
	 * the list of data 's criteria are "hcp", "patient" and the date of it is
	 * in specific range
	 * 
	 * @throws Exception
	 */
	public void testGetTransactionList1() throws Exception {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date start = fmt.parse("2007-06-21");
		Date end = fmt.parse("2007-06-25");
		Date check = fmt.parse("2007-06-26");
		List<TransactionLogBean> list = tranDAO.getTransactionList("hcp", "patient", start, end, 410);
		assertEquals(8, list.size());
		for (int i = 0; i < list.size(); i++) {
			assertEquals("hcp", list.get(i).getLoggedInRole());
			assertEquals("patient", list.get(i).getSecondaryRole());
			System.out.println(list.get(i).getTimeLogged().toString());
			if ((list.get(i).getTimeLogged().getTime() >= start.getTime())
					&& (list.get(i).getTimeLogged().getTime() < check.getTime())) {
			} else {
				fail();
			}
		}
	}

	/**
	 * This is function to check bad case of getTransactionList
	 * 
	 * @throws Exception
	 */
	public void testGetTransactionList2() throws Exception {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date start = fmt.parse("2007-06-21");
		Date end = fmt.parse("2007-06-25");
		List<TransactionLogBean> list = tranDAO.getTransactionList("", "", start, end, -1);
		assertEquals(0, list.size());
	}

	/**
	 * This is function to check getTransactionList when role criteria is "all
	 * roles" pass when the timelogged is in specific range
	 * 
	 * @throws Exception
	 */
	public void testGetTransactionList3() throws Exception {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date start = fmt.parse("2007-06-21");
		Date end = fmt.parse("2007-06-25");
		Date check = fmt.parse("2007-06-26");
		List<TransactionLogBean> list = tranDAO.getTransactionList("all roles", "all roles", start, end, 410);
		assertEquals(8, list.size());
		for (int i = 0; i < list.size(); i++) {
			if ((list.get(i).getTimeLogged().getTime() >= start.getTime())
					&& (list.get(i).getTimeLogged().getTime() < check.getTime())) {
			} else {
				fail();
			}
		}
	}

	/**
	 * Test for DBException
	 * 
	 * @throws Exception
	 */
	public void testInputErrorException() throws Exception {
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			Date start = fmt.parse("2007-06-21");
			Date end = fmt.parse("2007-06-25");
			List<TransactionLogBean> list = evilDAO.getTransactionList("hcp", "patient", start, end, 410);
			fail("Exception should have been thrown");
			list.get(0);
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}

package edu.ncsu.csc.itrust.unit.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.TransactionLogBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.action.ViewTransactionLogAction;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionLogColumnType;
import junit.framework.TestCase;

/**
 * Test case for ViewTransactionLogAction
 *
 */
public class ViewTransactiongLogActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ViewTransactionLogAction tranAction = new ViewTransactionLogAction(factory);

	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.transactionLog();
	}

	/**
	 * To check function getTransactionGroupBy of ViewTransactionAction when the
	 * records were group by "loggedInRole" pass when the list size is 5 in data
	 * 
	 * @throws Exception
	 */
	public void testGetTransactionLogGroupByLoggedInRole() throws Exception {
		List<TransactionLogBean> list = tranAction.getTransactionGroupBy(TransactionLogColumnType.parse(1));
		assertEquals(5, list.size());
	}

	/**
	 * To check function getTransactionGroupBy of ViewTransactionAction when the
	 * records were group by "loggedInRole" pass when the list size is 1 in data
	 * 
	 * @throws Exception
	 */
	public void testGetTransactionLogGroupBySecondaryRole() throws Exception {
		List<TransactionLogBean> list = tranAction.getTransactionGroupBy(TransactionLogColumnType.parse(2));
		assertEquals(1, list.size());
	}

	/**
	 * To check function getTransactionGroupBy of ViewTransactionAction when the
	 * records were group by "loggedInRole" pass when the list size is 7 in data
	 * 
	 * @throws Exception
	 */
	public void testGetTransactionLogGroupByTransactionLogCode() throws Exception {
		List<TransactionLogBean> list = tranAction.getTransactionGroupBy(TransactionLogColumnType.parse(3));
		assertEquals(7, list.size());
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
		List<TransactionLogBean> list = tranAction.getTransactionList("hcp", "patient", start, end, 410);
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
		List<TransactionLogBean> list = tranAction.getTransactionList("", "", start, end, -1);
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
		List<TransactionLogBean> list = tranAction.getTransactionList("all roles", "all roles", start, end, 410);
		assertEquals(8, list.size());
		for (int i = 0; i < list.size(); i++) {
			if ((list.get(i).getTimeLogged().getTime() >= start.getTime())
					&& (list.get(i).getTimeLogged().getTime() < check.getTime())) {
			} else {
				fail();
			}
		}
	}
}

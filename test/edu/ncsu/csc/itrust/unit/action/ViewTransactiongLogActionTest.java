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

public class ViewTransactiongLogActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ViewTransactionLogAction tranAction = new ViewTransactionLogAction (factory);
	
	private TestDataGenerator gen;
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.transactionLog();
	}
	
	public void testGetTransactionLogGroupByLoggedInMID() throws Exception {
		List<TransactionLogBean> list = tranAction.getTransactionGroupBy(TransactionLogColumnType.parse(1));
		assertEquals(5, list.size());
		
		assertEquals("er", list.get(0).getLoggedInRole());
	}
	
	
	public void testGetTransactionLogGroupBySecondaryMID() throws Exception {
		List<TransactionLogBean> list = tranAction.getTransactionGroupBy(TransactionLogColumnType.parse(2));
		assertEquals(1, list.size());
		assertEquals("patient", list.get(0).getSecondaryRole());
	}
	
	public void testGetTransactionLogGroupByTransactionLogCode() throws Exception {
		List<TransactionLogBean> list = tranAction.getTransactionGroupBy(TransactionLogColumnType.parse(3));
		assertEquals(7, list.size());
		assertEquals(3400, list.get(0).getTransactionType().getCode());
	}
	public void testGetTransactionList1() throws Exception {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date start = fmt.parse("2007-06-21");
		Date end = fmt.parse("2007-06-25");
		List<TransactionLogBean> list = tranAction.getTransactionList("hcp", "patient", start, end, 410);

		assertEquals(8, list.size());
		assertEquals("hcp", list.get(0).getLoggedInRole());
		assertEquals("patient", list.get(0).getSecondaryRole());
	}
	
	public void testGetTransactionList2() throws Exception {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date start = fmt.parse("2007-06-21");
		Date end = fmt.parse("2007-06-25");
		List<TransactionLogBean> list = tranAction.getTransactionList("", "", start, end, -1);
		assertEquals(0, list.size());
	}

	public void testGetTransactionList3() throws Exception {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date start = fmt.parse("2007-06-21");
		Date end = fmt.parse("2007-06-25");
		List<TransactionLogBean> list = tranAction.getTransactionList("all roles", "all roles", start, end, 410);
		System.out.println(list.size());
		assertEquals(8, list.size());
		assertEquals("hcp", list.get(0).getLoggedInRole());
		assertEquals("patient", list.get(0).getSecondaryRole());
	}
}

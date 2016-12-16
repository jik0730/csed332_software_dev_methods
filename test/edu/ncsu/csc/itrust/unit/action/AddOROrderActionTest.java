package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddOROrderAction;
import edu.ncsu.csc.itrust.beans.OrderBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrderDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/** AddOROrderActionTest */
public class AddOROrderActionTest {

	DAOFactory factory = TestDAOFactory.getTestInstance();
	TestDataGenerator gen = new TestDataGenerator();
	OrderDAO dao = factory.getOrderDAO();

	/**
	 * Setup
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.orderTest();
	}

	/**
	 * Test order list get
	 * 
	 * @throws ITrustException
	 */
	@Test
	public void testGet() throws ITrustException {
		AddOROrderAction action = new AddOROrderAction(factory, "456456456");
		List<OrderBean> orders = action.getOrders();
		assertEquals(1, orders.size());
	}
	
	/**
	 * Test order Add
	 * 
	 * @throws ITrustException
	 */
	@Test
	public void testAdd() throws ITrustException {
		AddOROrderAction action = new AddOROrderAction(factory, "456456456");
		OrderBean bean =  new OrderBean();
		bean.setOrderedHCPID(123456L);
		bean.setOrderHCPID(9220000000L);
		bean.setPatientID(1L);
		action.addOrder(bean);
		
		List<OrderBean> result = dao.getOrderByOrderedHCPID(123456L);
		assertEquals(1, result.size());
		assertEquals(123456L, result.get(0).getOrderedHCPID());
	}
}

package edu.ncsu.csc.itrust.unit.dao.order;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OrderBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrderDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class OrderDAOTest {

	DAOFactory factory = TestDAOFactory.getTestInstance();
	OrderDAO dao = new OrderDAO(factory);
	TestDataGenerator gen = new TestDataGenerator();

	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.orderTest();
	}
	
	@After
	public void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	@Test
	public void testAdd() throws Exception {
		OrderBean bean = new OrderBean();
		bean.setVisitID(456456456);
		bean.setOrderHCPID(9220000000L);
		bean.setOrderedHCPID(9220000000L);
		bean.setPatientID(1L);
		bean.setCompleted(false);
		dao.add(bean);
		
		List<OrderBean> orders = dao.getOrderByOrderedHCPID(9220000000L);
		assertEquals(1, orders.size());
	}

	@Test
	public void testGetOrderByVisitID() {
		
	}

	@Test
	public void testGetOrderByOrderedHCPID() {
		
	}

	@Test
	public void testGetUncompletedOrderForPair() {
		
	}
}

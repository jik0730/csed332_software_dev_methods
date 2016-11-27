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
	public void testAddAndGetByOrderedHCPID() throws Exception {
		OrderBean bean = new OrderBean();
		bean.setVisitID(456456456);
		bean.setOrderHCPID(9220000000L);
		bean.setOrderedHCPID(9220000000L);
		bean.setPatientID(1L);
		bean.setCompleted(false);
		dao.add(bean);
		
		List<OrderBean> orders = dao.getOrderByOrderedHCPID(9220000000L);
		assertEquals(1, orders.size());
		bean.setOrderID(orders.get(0).getOrderID());
		assertEquals(bean, orders.get(0));
	}

	@Test
	public void testGetOrderByVisitID() throws Exception {
		OrderBean bean = new OrderBean();
		bean.setVisitID(456456456);
		bean.setOrderHCPID(9220000000L);
		bean.setOrderedHCPID(9220000000L);
		bean.setPatientID(1L);
		bean.setCompleted(false);
		dao.add(bean);
		
		List<OrderBean> orders = dao.getOrderByVisitID(456456456);
		boolean preparedBeanExists = false;
		for(OrderBean b: orders) {
			if(b.getOrderedHCPID() == 9220000000L) preparedBeanExists = true;
		}

		assertEquals(2, orders.size());
		assertTrue(preparedBeanExists);
	}

	@Test
	public void testGetUncompletedOrderForPair() throws Exception {
		List<OrderBean> orders = dao.getUncompletedOrderForPair(9210000000L, 1L);
		
		assertEquals(1, orders.size());
		assertEquals(9210000000L, orders.get(0).getOrderedHCPID());
		assertEquals(1L, orders.get(0).getPatientID());
	}
}

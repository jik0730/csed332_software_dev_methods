package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OrderBean;

public class OrderBeanTest {

	@Test
	public void testGetSet() {
		OrderBean bean = new OrderBean();
		bean.setOrderID(1);
		bean.setVisitID(456456);
		bean.setOrderHCPID(9220000000L);
		bean.setOrderedHCPID(9210000000L);
		bean.setPatientID(1L);
		bean.setCompleted(false);
		
		assertEquals(1, bean.getOrderID());
		assertEquals(456456, bean.getVisitID());
		assertEquals(9220000000L, bean.getOrderHCPID());
		assertEquals(9210000000L, bean.getOrderedHCPID());
		assertEquals(1L, bean.getPatientID());
		assertEquals(false, bean.isCompleted());
		
		OrderBean bean2 = new OrderBean(1, 456456, 9220000000L, 9210000000L, 1L, false);
		assertEquals(1, bean2.getOrderID());
		assertEquals(456456, bean2.getVisitID());
		assertEquals(9220000000L, bean2.getOrderHCPID());
		assertEquals(9210000000L, bean2.getOrderedHCPID());
		assertEquals(1L, bean2.getPatientID());
		assertEquals(false, bean2.isCompleted());	
	}
	
	@Test
	public void testEquality() {
		OrderBean bean1 = new OrderBean();
		OrderBean bean2 = new OrderBean();
		
		bean1.setOrderID(1);
		bean1.setVisitID(456456);
		bean1.setOrderHCPID(9220000000L);
		bean1.setOrderedHCPID(9210000000L);
		bean1.setPatientID(1L);
		bean1.setCompleted(false);
		
		bean2.setOrderID(1);
		bean2.setVisitID(456456);
		bean2.setOrderHCPID(9220000000L);
		bean2.setOrderedHCPID(9210000000L);
		bean2.setPatientID(1L);
		bean2.setCompleted(false);

		assertEquals(bean1, bean2);
		assertEquals(bean1.toString(), bean2.toString());
		assertEquals(bean1.hashCode(), bean2.hashCode());
	}
	
	@Test
	public void testEqualityManyCase() {
		OrderBean bean1 = new OrderBean();
		OrderBean bean2 = new OrderBean();
		
		bean1.setOrderID(1);
		bean1.setVisitID(456456);
		bean1.setOrderHCPID(9220000000L);
		bean1.setOrderedHCPID(9210000000L);
		bean1.setPatientID(1L);
		bean1.setCompleted(false);
		
		bean2.setOrderID(1);
		bean2.setVisitID(456456);
		bean2.setOrderHCPID(9220000000L);
		bean2.setOrderedHCPID(9210000000L);
		bean2.setPatientID(1L);
		bean2.setCompleted(false);
		
		assertEquals (bean1.equals(bean1), true);
		assertEquals (bean2.equals(bean1), true);
		assertEquals (bean1.equals(null), false);
		assertEquals (bean1.equals("hi"), false);
		
		bean2.setOrderID(0);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setVisitID(456);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setOrderHCPID(1L);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setOrderedHCPID(1L);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setPatientID(2L);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setCompleted(true);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setOrderID(1);
		bean2.setVisitID(456456);
		bean2.setOrderHCPID(9220000000L);
		bean2.setOrderedHCPID(9210000000L);
		bean2.setPatientID(1L);
		
		bean2.setVisitID(456);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setOrderHCPID(1L);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setOrderedHCPID(1L);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setPatientID(2L);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setCompleted(true);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setOrderID(1);
		bean2.setVisitID(456456);
		bean2.setOrderHCPID(9220000000L);
		bean2.setOrderedHCPID(9210000000L);
		bean2.setPatientID(1L);
		
		bean2.setOrderHCPID(1L);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setOrderedHCPID(1L);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setPatientID(2L);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setCompleted(true);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setOrderID(1);
		bean2.setVisitID(456456);
		bean2.setOrderHCPID(9220000000L);
		bean2.setOrderedHCPID(9210000000L);
		bean2.setPatientID(1L);
		
		bean2.setOrderedHCPID(1L);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setPatientID(2L);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setCompleted(true);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setOrderID(1);
		bean2.setVisitID(456456);
		bean2.setOrderHCPID(9220000000L);
		bean2.setOrderedHCPID(9210000000L);
		bean2.setPatientID(1L);
		
		bean2.setPatientID(2L);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setCompleted(true);
		assertEquals (bean2.equals(bean1), false);
		
		bean2.setOrderID(1);
		bean2.setVisitID(456456);
		bean2.setOrderHCPID(9220000000L);
		bean2.setOrderedHCPID(9210000000L);
		bean2.setPatientID(1L);
		
		bean2.setCompleted(true);
		assertEquals (bean2.equals(bean1), false);
		
		assertNotEquals(bean1.hashCode(), bean2.hashCode());
		
		
	}
}

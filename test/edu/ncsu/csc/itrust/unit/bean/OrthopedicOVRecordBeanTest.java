package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;

public class OrthopedicOVRecordBeanTest {
	/**Allowed value for the difference of two 'equivalent' doubles.*/
	private static final double ERROR = 0.000001;
	
	private final byte[] TEST_XRAY = {
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10	
	};
	private final byte[] TEST_MRI = {
		11, 12, 13, 14, 15, 16, 17, 18, 19, 20	
	};

	@Test
	public void testGetSet() throws ParseException{
		OrthopedicOVRecordBean bean1 = new OrthopedicOVRecordBean();
		OrthopedicOVRecordBean bean2 = new OrthopedicOVRecordBean(1, 401, 9000000, "XRAY", "MRI",
                "TestBean MriReport", "TestBean Injured", "01/22/2015");
		
		bean1.setPid(401L);
		bean1.setOid(1);
		bean1.setHid(9000000000L);
		bean1.setVisitDate("01/22/2015");
		bean1.setInjured("TestBean Injured");
		bean1.setMriReport("TestBean MriReport");
		bean1.setXray(TEST_XRAY);
		bean1.setMri(TEST_MRI);
		
		assertEquals(bean1.getPid(), 401L, ERROR);
		assertEquals(bean1.getOid(), 1, ERROR);
		assertEquals(bean1.getVisitDateString(), "01/22/2015");
		assertEquals(bean1.getVisitDate(), new SimpleDateFormat("MM/dd/yyyy").parse("01/22/2015"));
		assertEquals(bean1.getInjured(), "TestBean Injured");
		assertEquals(bean1.getMriReport(), "TestBean MriReport");
		assertTrue(Arrays.equals(bean1.getXray(), TEST_XRAY));
		assertTrue(Arrays.equals(bean1.getMri(), TEST_MRI));
	}
	
	@Test
	public void testEquality(){
		OrthopedicOVRecordBean bean1 = new OrthopedicOVRecordBean();
		bean1.setPid(401L);
		bean1.setOid(1);
		bean1.setHid(9000000000L);
		bean1.setVisitDate("01/22/2015");
		bean1.setInjured("TestBean Injured");
		bean1.setMriReport("TestBean MriReport");
		bean1.setXray(TEST_XRAY);
		bean1.setMri(TEST_MRI);
		
		OrthopedicOVRecordBean bean2 = new OrthopedicOVRecordBean();
		bean2.setPid(401L);
		bean2.setOid(1);
		bean2.setHid(9000000000L);
		bean2.setVisitDate("01/22/2015");
		bean2.setInjured("TestBean Injured");
		bean2.setMriReport("TestBean MriReport");
		bean2.setXray(TEST_XRAY);
		bean2.setMri(TEST_MRI);
		
		
		assertEquals(bean1, bean2);
		assertEquals(bean1.toString(), bean2.toString());
		assertEquals(bean1.hashCode(), bean2.hashCode());
		
		
		bean1.setVisitDate(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setVisitDate("01/22/2015");
		
		bean1.setInjured(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setInjured("TestBean Injured");
		
		bean1.setMriReport(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setMriReport("TestBean MriReport");
		
		bean1.setXray(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setXray(TEST_XRAY);
		
		bean1.setMri(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setMri(TEST_MRI);
	}
	
	@Test
	public void testHashCodeManyCase() {
		OrthopedicOVRecordBean bean1 = new OrthopedicOVRecordBean();
		bean1.setPid(401L);
		bean1.setOid(1);
		bean1.setHid(9000000000L);
		bean1.setVisitDate("01/22/2015");
		bean1.setInjured("");
		bean1.setMriReport("");
		bean1.setXray(TEST_XRAY);
		bean1.setMri(TEST_MRI);
		bean1.hashCode();
		
		bean1.setInjured(null);
		bean1.setMriReport(null);
		bean1.setVisitDate(null);
		bean1.hashCode();
		
	}
	
	@Test
	public void testEqualsManyCase() {
		OrthopedicOVRecordBean bean1 = new OrthopedicOVRecordBean();
		bean1.setPid(401L);
		bean1.setOid(1);
		bean1.setHid(9000000000L);
		bean1.setVisitDate("01/22/2015");
		bean1.setInjured("TestBean Injured");
		bean1.setMriReport("TestBean MriReport");
		bean1.setXray(TEST_XRAY);
		bean1.setMri(TEST_MRI);
		
		OrthopedicOVRecordBean bean2 = new OrthopedicOVRecordBean();
		bean2.setPid(401L);
		bean2.setOid(1);
		bean2.setHid(9000000000L);
		bean2.setVisitDate("01/22/2015");
		bean2.setInjured("TestBean Injured");
		bean2.setMriReport("TestBean MriReport");
		bean2.setXray(TEST_XRAY);
		bean2.setMri(TEST_MRI);
		
		assertEquals(bean1.equals(null), false);
		assertEquals(bean1.equals("hi"), false);
		assertEquals(bean1.equals(bean1), true);
		assertEquals(bean1.equals(bean2), true);
		assertEquals(bean2.equals(bean1), true);
		
		bean2.setPid(400L);
		assertEquals(bean1.equals(bean2), false);
		assertEquals(bean2.equals(bean1), false);
		bean2.setPid(401L);
		bean2.setOid(2);
		assertEquals(bean1.equals(bean2), false);
		assertEquals(bean2.equals(bean1), false);
		bean2.setOid(1);
		bean2.setHid(9000000001L);
		assertEquals(bean1.equals(bean2), false);
		assertEquals(bean2.equals(bean1), false);
		bean2.setHid(9000000000L);
		
		bean2.setMriReport(null);
		assertEquals(bean1.equals(bean2), false);
		bean1.setMriReport(null);
		assertEquals(bean2.equals(bean1), true);
		bean1.setMriReport("TestBean MriReport");
		bean2.setMriReport("TestBean MriReport");

		bean2.setVisitDate(null);
		assertEquals(bean1.equals(bean2), false);
		bean1.setVisitDate(null);
		assertEquals(bean2.equals(bean1), true);
		bean1.setVisitDate("01/22/2015");
		bean2.setVisitDate("01/22/2015");
		
		bean2.setInjured(null);
		assertEquals(bean1.equals(bean2), false);
		bean1.setInjured(null);
		assertEquals(bean2.equals(bean1), true);
		bean1.setInjured("TestBean Injured");
		bean2.setInjured("TestBean Injured");
		
		bean2.setMri(null);
		assertEquals(bean1.equals(bean2), false);
		bean1.setMri(null);
		bean1.hashCode();
		assertEquals(bean2.equals(bean1), true);
		bean1.setMri(TEST_MRI);
		bean2.setMri(TEST_MRI);
		
		bean2.setXray(null);
		assertEquals(bean1.equals(bean2), false);
		bean1.setXray(null);
		bean1.hashCode();
		assertEquals(bean2.equals(bean1), true);
		bean1.setXray(TEST_XRAY);
		bean2.setXray(TEST_XRAY);
	}
	@Test
	public void testInvalidDates(){
		// Make a bean with an invalid date
		OrthopedicOVRecordBean bean1 = new OrthopedicOVRecordBean();
		bean1.setVisitDate("Not even a date");
		assertNull(bean1.getVisitDate());
		
		bean1.setVisitDate("2015-12-12");
		assertNull(bean1.getVisitDate());
		
		bean1.setVisitDate(null);
		assertNull(bean1.getVisitDate());
	}
	
}

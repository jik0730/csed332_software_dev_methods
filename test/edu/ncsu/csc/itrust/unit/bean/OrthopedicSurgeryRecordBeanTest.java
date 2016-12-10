package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OrthopedicSurgeryRecordBean;
import edu.ncsu.csc.itrust.beans.OphthalmologySurgeryRecordBean;

public class OrthopedicSurgeryRecordBeanTest {
	/**Allowed value for the difference of two 'equivalent' doubles.*/
	private static final double ERROR = 0.000001;

	@Test
	public void testGetSet() throws ParseException{
		OrthopedicSurgeryRecordBean bean1 = new OrthopedicSurgeryRecordBean();
		bean1.setMid(401L);
		bean1.setOid(1L);
		bean1.setLastName("Bridges");
		bean1.setFirstName("Lamar");
		bean1.setVisitDate("01/22/2015");
		bean1.setSurgery("Total knee replacement");
		bean1.setSurgeryNotes("Went well.");
		
		assertEquals(bean1.getMid(), 401L, ERROR);
		assertEquals(bean1.getOid(), 1L, ERROR);
		assertEquals(bean1.getLastName(), "Bridges");
		assertEquals(bean1.getFirstName(), "Lamar");
		assertEquals(bean1.getVisitDateString(), "01/22/2015");
		assertEquals(bean1.getVisitDate(), new SimpleDateFormat("MM/dd/yyyy").parse("01/22/2015"));
		assertEquals(bean1.getSurgery(), "Total knee replacement");
		assertEquals(bean1.getSurgeryNotes(), "Went well.");
	}
	
	@Test
	public void testEquality(){
		OrthopedicSurgeryRecordBean bean1 = new OrthopedicSurgeryRecordBean();
		bean1.setMid(401L);
		bean1.setOid(1L);
		bean1.setLastName("Bridges");
		bean1.setFirstName("Lamar");
		bean1.setVisitDate("01/22/2015");
		bean1.setSurgery("Total knee replacement");
		bean1.setSurgeryNotes("Went well.");
		
		OrthopedicSurgeryRecordBean bean2 = new OrthopedicSurgeryRecordBean();
		bean2.setMid(401L);
		bean2.setOid(1L);
		bean2.setLastName("Bridges");
		bean2.setFirstName("Lamar");
		bean2.setVisitDate("01/22/2015");
		bean2.setSurgery("Total knee replacement");
		bean2.setSurgeryNotes("Went well.");
		
		
		assertEquals(bean1, bean2);
		assertEquals(bean1.toString(), bean2.toString());
		assertEquals(bean1.hashCode(), bean2.hashCode());
		
		
		bean1.setVisitDate(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setVisitDate("01/22/2015");
		
		bean1.setSurgery(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setSurgery("Total knee replacement");

		bean1.setSurgeryNotes(null);
		assertFalse(bean1.equals(bean2));
		//assertFalse(bean1.hashCode() == (bean2.hashCode()));
		bean1.setSurgeryNotes("Went well.");
		
		
	}
	
	@Test
	public void testHashCode() {
		OrthopedicSurgeryRecordBean bean1 = new OrthopedicSurgeryRecordBean();
		bean1.setFirstName(null);
		bean1.setLastName(null);
		bean1.setMid(0);
		bean1.setOid(0);
		bean1.setSurgery(null);
		bean1.setSurgeryNotes(null);
		bean1.setVisitDate(null);
		assertEquals(bean1.hashCode(), bean1.hashCode());
	}
	
	@Test
	public void testManyEquals() {
		OrthopedicSurgeryRecordBean bean1 = new OrthopedicSurgeryRecordBean();
		OrthopedicSurgeryRecordBean bean2 = new OrthopedicSurgeryRecordBean();
		OphthalmologySurgeryRecordBean bean3 = new OphthalmologySurgeryRecordBean();
		OrthopedicSurgeryRecordBean bean4 = new OrthopedicSurgeryRecordBean();
		OrthopedicSurgeryRecordBean bean5 = new OrthopedicSurgeryRecordBean();
		
		bean1.setMid(401L);
		bean1.setOid(1L);
		bean1.setLastName("Bridges");
		bean1.setFirstName("Lamar");
		bean1.setVisitDate("01/22/2015");
		bean1.setSurgery("Total knee replacement");
		bean1.setSurgeryNotes("Went well.");
		assertEquals (bean1.equals(bean1), true);
		
		
		bean2.setMid(401L);
		bean2.setOid(1L);
		bean2.setLastName("Bridges");
		bean2.setFirstName("Lamar");
		bean2.setVisitDate("01/22/2015");
		bean2.setSurgery("Total knee replacement");
		bean2.setSurgeryNotes("Went well.");
		
		assertEquals (bean1.equals(bean2), true);
		assertEquals (bean1.equals(null), false);
		assertEquals (bean1.equals(bean3), false);
		
		bean4.setMid(401L);
		bean4.setOid(1L);
		bean4.setLastName("Bridges");
		bean4.setFirstName("Lamar");
		bean4.setVisitDate("01/22/2015");
		bean4.setSurgery("Total knee replacement");
		bean4.setSurgeryNotes("Went well.");
		
		bean5.setMid(0L);
		bean5.setOid(1L);
		bean5.setLastName("Bridges");
		bean5.setFirstName("Lamar");
		bean5.setVisitDate("01/22/2015");
		bean5.setSurgery("Total knee replacement");
		bean5.setSurgeryNotes("Went well.");
		assertEquals (bean4.equals(bean5), false);
		assertEquals (bean5.equals(bean4), false);
		
		bean5.setMid(401L);
		bean5.setOid(0L);
		bean5.setLastName("Bridges");
		bean5.setFirstName("Lamar");
		bean5.setVisitDate("01/22/2015");
		bean5.setSurgery("Total knee replacement");
		bean5.setSurgeryNotes("Went well.");
		assertEquals (bean4.equals(bean5), false);
		assertEquals (bean5.equals(bean4), false);
		
		bean5.setMid(401L);
		bean5.setOid(1L);
		bean5.setLastName(null);
		bean5.setFirstName("Lamar");
		bean5.setVisitDate("01/22/2015");
		bean5.setSurgery("Total knee replacement");
		bean5.setSurgeryNotes("Went well.");
		assertEquals (bean4.equals(bean5), false);
		assertEquals (bean5.equals(bean4), false);
		
		bean5.setMid(401L);
		bean5.setOid(1L);
		bean5.setLastName("Bridges");
		bean5.setFirstName(null);
		bean5.setVisitDate("01/22/2015");
		bean5.setSurgery("Total knee replacement");
		bean5.setSurgeryNotes("Went well.");
		assertEquals (bean4.equals(bean5), false);
		assertEquals (bean5.equals(bean4), false);

		bean5.setMid(401L);
		bean5.setOid(1L);
		bean5.setLastName("Bridges");
		bean5.setFirstName("Lamar");
		bean5.setVisitDate(null);
		bean5.setSurgery("Total knee replacement");
		bean5.setSurgeryNotes("Went well.");
		assertEquals (bean4.equals(bean5), false);
		
		bean5.setMid(401L);
		bean5.setOid(1L);
		bean5.setLastName("Bridges");
		bean5.setFirstName("Lamar");
		bean5.setVisitDate("01/22/2015");
		bean5.setSurgery(null);
		bean5.setSurgeryNotes("Went well.");
		assertEquals (bean4.equals(bean5), false);
		assertEquals (bean5.equals(bean4), false);
		
		bean5.setMid(401L);
		bean5.setOid(1L);
		bean5.setLastName("Bridges");
		bean5.setFirstName("Lamar");
		bean5.setVisitDate("01/22/2015");
		bean5.setSurgery("Total knee replacement");
		bean5.setSurgeryNotes(null);
		assertEquals (bean4.equals(bean5), false);
		assertEquals (bean5.equals(bean4), false);
		
		bean5.setMid(401L);
		bean5.setOid(1L);
		bean5.setLastName("Bridges");
		bean5.setFirstName("Lamar");
		bean5.setVisitDate("01/22/2015");
		bean5.setSurgery("Total knee replacement");
		bean5.setSurgeryNotes("Went well.");
		assertEquals (bean4.equals(bean5), true);
		assertEquals (bean5.equals(bean4), true);
		
		bean4.setMid(401L);
		bean4.setOid(1L);
		bean4.setLastName(null);
		bean4.setFirstName("Lamar");
		bean4.setVisitDate("01/22/2015");
		bean4.setSurgery("Total knee replacement");
		bean4.setSurgeryNotes("Went well.");
		bean5.setMid(401L);
		bean5.setOid(1L);
		bean5.setLastName(null);
		bean5.setFirstName("Lamar");
		bean5.setVisitDate("01/22/2015");
		bean5.setSurgery("Total knee replacement");
		bean5.setSurgeryNotes("Went well.");
		assertEquals (bean4.equals(bean5), true);
		assertEquals (bean5.equals(bean4), true);
		
		bean4.setMid(401L);
		bean4.setOid(1L);
		bean4.setLastName("Bridges");
		bean4.setFirstName(null);
		bean4.setVisitDate(null);
		bean4.setSurgery(null);
		bean4.setSurgeryNotes(null);
		bean5.setMid(401L);
		bean5.setOid(1L);
		bean5.setLastName("Bridges");
		bean5.setFirstName(null);
		bean5.setVisitDate(null);
		bean5.setSurgery(null);
		bean5.setSurgeryNotes(null);
		assertEquals (bean4.equals(bean5), true);
		assertEquals (bean5.equals(bean4), true);
	}
	
	@Test
	public void testInvalidDates(){
		// Make a bean with an invalid date
		OrthopedicSurgeryRecordBean bean1 = new OrthopedicSurgeryRecordBean();
		bean1.setVisitDate("Not even a date");
		assertNull(bean1.getVisitDate());
		
		bean1.setVisitDate("2015-12-12");
		assertNull(bean1.getVisitDate());
		
		bean1.setVisitDate(null);
		assertNull(bean1.getVisitDate());
	}
	
}

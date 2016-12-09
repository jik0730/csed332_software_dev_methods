package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OrthopedicDiagnosisBean;

public class OrthopedicDiagnosisBeanTest {
	/**Allowed value for the difference of two 'equivalent' doubles.*/
	private static final double ERROR = 0.000001;

	@Test
	public void testGetSet() throws ParseException{
		OrthopedicDiagnosisBean bean1 = new OrthopedicDiagnosisBean();
		OrthopedicDiagnosisBean bean2 = new OrthopedicDiagnosisBean("000", "hello", "yes", "www.naver.com");
		OrthopedicDiagnosisBean bean3 = new OrthopedicDiagnosisBean("000", "hello", "no", "www.naver.com");
		OrthopedicDiagnosisBean bean4 = new OrthopedicDiagnosisBean("000", "hello", null, "www.naver.com");
		bean1.setOrDiagnosisID(1L);
		bean1.setICDCode("000");
		bean1.setDescription("hello");
		bean1.setURL("www.naver.com");
		bean1.setVisitID(1L);
		bean1.getFormattedDescription();
		assertEquals (bean1.getOrDiagnosisID(), 1L);
		assertEquals (bean1.getICDCode(), "000");
		assertEquals (bean1.getDescription(), "hello");
		assertEquals (bean1.getURL(), "www.naver.com");
		assertEquals (bean1.getVisitID(), 1L);
		assertEquals (bean2.getClassification(), "yes");
		assertEquals (bean3.getClassification(), "no");
	}
	
}

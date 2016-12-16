package edu.ncsu.csc.itrust.unit.validate.bean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OrthopedicSurgeryRecordBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.OrthopedicSurgeryValidator;

public class OrthopedicSurgeryValidatorTest {

	OrthopedicSurgeryValidator v;
	
	@Before
	public void setUp(){
		v = new OrthopedicSurgeryValidator();
	}
	
	@Test
	public void testNullBean() {
		OrthopedicSurgeryRecordBean badbean = null;
		try {
			v.validate(badbean);
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		badbean = new OrthopedicSurgeryRecordBean();
		badbean.setVisitDate(null);
		try {
			v.validate(badbean);
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		badbean.setVisitDate("");
		try {
			v.validate(badbean);
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		badbean.setVisitDate("11/11/2014");
		try {
			v.validate(badbean);
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		badbean.setFirstName("hi");
		badbean.setLastName("doctor");
		try {
			v.validate(badbean);
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testValidateAllNull(){
		OrthopedicSurgeryRecordBean badbean = new OrthopedicSurgeryRecordBean();
		try{
			v.validate(badbean);
			fail();
		}catch(FormValidationException e){
			//do nothing, we made it
		}	
	}
	
}
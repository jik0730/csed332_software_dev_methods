package edu.ncsu.csc.itrust.unit.validate.bean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.OrthopedicOVValidator;

public class OrthopedicOVValidatorTest {

	OrthopedicOVValidator v;
	
	@Before
	public void setUp(){
		v = new OrthopedicOVValidator();
	}
	
	@Test
	public void testNullBean() {
		OrthopedicOVRecordBean badbean = null;
		try {
			v.validate(badbean);
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		badbean = new OrthopedicOVRecordBean();
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
		
		badbean.setHid(1L);
		badbean.setPid(2L);
		try {
			v.validate(badbean);
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testValidateAllNull(){
		OrthopedicOVRecordBean badbean = new OrthopedicOVRecordBean();
		try{
			v.validate(badbean);
			fail();
		}catch(FormValidationException e){
			//do nothing, we made it
		}	
	}
	
}
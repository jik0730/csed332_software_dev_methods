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
	public void testValidateAllNull(){
		OrthopedicSurgeryRecordBean badbean = new OrthopedicSurgeryRecordBean();
		try{
			v.validate(badbean);
			fail();
		}catch(FormValidationException e){
			//do nothing, we made it
		}	
	}
	
	@Test
	public void testValidateAxisNoCylinder(){
		OrthopedicSurgeryRecordBean goodbean = new OrthopedicSurgeryRecordBean();
		try{
			goodbean.setVisitDate("10/14/2015");
			goodbean.setLastName("Tran");
			goodbean.setFirstName("Brooke");
			v.validate(goodbean);
		}catch(FormValidationException e){
			fail(); //this is a valid bean
		}	
	}
	
	@Test
	public void testValidateCylinderNoAxis(){
		OrthopedicSurgeryRecordBean badbean = new OrthopedicSurgeryRecordBean();
		try{
			badbean.setVisitDate("10/14/2015");
			badbean.setLastName("Tran");
			badbean.setFirstName("Brooke");
			v.validate(badbean);
			fail(); //this is an invalid bean
		}catch(FormValidationException e){
			//do nothing, this bean is supposed to be invalid
		}	
	}
	
	@Test
	public void testValidateLowBoundaries(){
		OrthopedicSurgeryRecordBean goodbean = new OrthopedicSurgeryRecordBean();
		try{
			goodbean.setVisitDate("10/14/2015");
			goodbean.setLastName("Tran");
			goodbean.setFirstName("Brooke");
			v.validate(goodbean);
		}catch(FormValidationException e){
			fail(); //this is a valid bean
		}	
	}
	
	@Test
	public void testValidateHighBoundaries(){
		OrthopedicSurgeryRecordBean goodbean = new OrthopedicSurgeryRecordBean();
		try{
			goodbean.setVisitDate("10/14/2015");
			goodbean.setLastName("Tran");
			goodbean.setFirstName("Brooke");
			v.validate(goodbean);
		}catch(FormValidationException e){
			fail(); //this is a valid bean
		}	
	}
	
	@Test
	public void testValidateNotRounded(){
		OrthopedicSurgeryRecordBean badbean = new OrthopedicSurgeryRecordBean();
		try{
			badbean.setVisitDate("10/14/2015");
			badbean.setLastName("Tran");
			badbean.setFirstName("Brooke");
			v.validate(badbean);
			fail();
		}catch(FormValidationException e){
			 //do nothing, this bean should fail
		}	
	}
}
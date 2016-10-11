package edu.ncsu.csc.itrust.unit.homework2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.forms.EditPrescriptionsForm;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.EditPrescriptionsValidator;

public class EditPrescriptionsValidatorTest {
	
	EditPrescriptionsValidator validator;
	EditPrescriptionsForm form;
	
	@Before
	public void setup() {
		validator = new EditPrescriptionsValidator("Stub");
		form = new EditPrescriptionsForm();
	}
	
	@After
	public void tearDown() {
		form = null;
	}
	
	public void fillInFormWithDefaultInstruction() {
		form.setInstructions("Stub");
	}
	
	public void fillInFormWithNonDefaultInstruction() {
		form.setInstructions("DifferentStub");
	}
	
	public void fillInFormWithMalFormedDate() {
		form.setStartDate("Prepare to spit out errors");
		form.setEndDate("OK.");
	}
	
	public void fillInFormWithWellFormedDate() {
		form.setStartDate("09/14/1995");
		form.setEndDate("09/14/1995");
	}
	
	public void fillInFormWithReverseDate() {
		form.setStartDate("09/14/2000");
		form.setEndDate("09/14/1995");
	}
	
	@Test(expected=FormValidationException.class)
	public void validateFormWithDefaultInstruction() throws FormValidationException {
		fillInFormWithDefaultInstruction();
		validator.validate(form);
	}

	@Test(expected=FormValidationException.class)
	public void validateFormWithNonDefaultInstruction() throws FormValidationException {
		fillInFormWithNonDefaultInstruction();
		validator.validate(form);
	}
	
	@Test(expected=FormValidationException.class)
	public void validateFormWithMalFormedDate() throws FormValidationException {
		fillInFormWithDefaultInstruction();
		fillInFormWithMalFormedDate();
		validator.validate(form);
	}

	@Test(expected=FormValidationException.class)
	public void validateFormWithWellFormedDate() throws FormValidationException {
		fillInFormWithDefaultInstruction();
		fillInFormWithWellFormedDate();
		validator.validate(form);
	}

	@Test(expected=FormValidationException.class)
	public void validateFormWithReverseDate() throws FormValidationException {
		fillInFormWithDefaultInstruction();
		fillInFormWithReverseDate();
		validator.validate(form);
	}
}

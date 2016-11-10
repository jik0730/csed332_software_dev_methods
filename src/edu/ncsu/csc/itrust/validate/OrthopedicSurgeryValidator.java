package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.OrthopedicSurgeryRecordBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ErrorList;

 /**
 * Validator class for OrthopedicSurgeryActionBean. Used in order to verify that a beans
 * contents are consistent with the data format section of UC83.
 */
public class OrthopedicSurgeryValidator extends BeanValidator<OrthopedicSurgeryRecordBean> {

	/** Validates an Orthopedic bean passed to it.
	 * @param bean The bean to be validated.
	 * @throws FormValidationException throws an exception as its way of saying that the bean passed is invalid.
	 */
	@Override
	public void validate(OrthopedicSurgeryRecordBean bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		if (bean != null) {
			//at this point, we can assume that all numerical fields have made it into the bean successfully,
			//we still need to check to make sure they are in the right ranges.
			
			if(bean.getVisitDateString() == null || bean.getVisitDateString().equals("")){
				errorList.addIfNotNull("Date is a required field");
			}
			errorList.addIfNotNull(checkFormat("Visit Date:", bean.getVisitDateString(), ValidationFormat.DATE, true));
			
			errorList.addIfNotNull(checkFormat("Last Name:", bean.getLastName(), ValidationFormat.NAME, false));
			errorList.addIfNotNull(checkFormat("First Name:", bean.getFirstName(), ValidationFormat.NAME, false));
			
			
			

		} else {
			errorList.addIfNotNull("Bean is null.");
		}
		
		if (errorList.hasErrors()){
			throw new FormValidationException(errorList);
		}
	}
}
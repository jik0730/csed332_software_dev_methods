package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.OphthalmologyOVRecordBean;
import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

public class OrthopedicOVValidator extends BeanValidator<OrthopedicOVRecordBean> {


	@Override
	public void validate(OrthopedicOVRecordBean bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		if (bean != null) {
			//at this point, we can assume that all numerical fields have made it into the bean successfully,
			//we still need to check to make sure they are in the right ranges.
			
			errorList.addIfNotNull(checkFormat("Patient ID:", bean.getPid(), ValidationFormat.MID, false));
			errorList.addIfNotNull(checkFormat("HCP ID:", bean.getHid(), ValidationFormat.MID, false));
			
			if(bean.getVisitDateString() == null || bean.getVisitDateString().equals("")){
				errorList.addIfNotNull("Date is a required field");
			}
			errorList.addIfNotNull(checkFormat("Visit Date:", bean.getVisitDateString(), ValidationFormat.DATE, true));
			
			if(bean.getDiagnosis() == null) {
				errorList.addIfNotNull("Diagnosis is a required field");
			}
			for(String s : bean.getDiagnosis()) {
				errorList.addIfNotNull(checkFormat("Diagnosis:", s, ValidationFormat.ORTHOPEDIC_DIAGNOSIS, false));
			}

		} else {
			errorList.addIfNotNull("Bean is null.");
		}
		
		if (errorList.hasErrors()){
			throw new FormValidationException(errorList);
		}
		
	}
	
}

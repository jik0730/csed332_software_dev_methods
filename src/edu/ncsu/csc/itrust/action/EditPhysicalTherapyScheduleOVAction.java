package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyScheduleOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PhysicalTherapyScheduleOVDAO;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.PhysicalTherapyScheduleOVValidator;

/**
 * Used for editing physicalTherapy office visit requests. 
 */
public class EditPhysicalTherapyScheduleOVAction {
	/**physicalTherapyOVDAO is the DAO that retrieves the physicalTherapy office
	 *  visit records from the database*/
	private PhysicalTherapyScheduleOVDAO physicalTherapyOVDAO;
	
	
	public EditPhysicalTherapyScheduleOVAction(DAOFactory factory, long loggedInMID) {
		this.physicalTherapyOVDAO = factory.getPhysicalTherapyScheduleOVDAO();
	}
	
	
	public void editPhysicalTherapyScheduleOV(long oid, PhysicalTherapyScheduleOVRecordBean p)
    		throws FormValidationException, ITrustException {
		if(p != null){
			new PhysicalTherapyScheduleOVValidator().validate(p);
			
			physicalTherapyOVDAO.editPhysicalTherapyScheduledOVRecordsRecord(oid, p);
		} else {
			throw new ITrustException("Cannot edit a null Obstetrics Record.");
		}
	}
}

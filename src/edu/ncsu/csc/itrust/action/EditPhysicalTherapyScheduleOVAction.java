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
	
	/**
     * EditPhysicalTherapyScheduleOVAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public EditPhysicalTherapyScheduleOVAction(DAOFactory factory, long loggedInMID) {
		this.physicalTherapyOVDAO = factory.getPhysicalTherapyScheduleOVDAO();
	}
	
	/**
	 * Edits an existing physicalTherapy scheduled office visit record.
	 * @param oid The oid of the physicalTherapy scheduled office visit.
	 * @param p PhysicalTherapyScheduleOVRecordBean containing the info for the record to be updated.
	 * @throws FormValidationException if the patient is not successfully validated.
	 * @throws ITrustException thrown if the database encounters an issue.
	 */
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

package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OrthopedicScheduleOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicScheduleOVDAO;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.OrthopedicScheduleOVValidator;

/**
 * Used for editing orthopedic office visit requests. 
 */
public class EditOrthopedicScheduleOVAction {
	/**orthopedicOVDAO is the DAO that retrieves the orthopedic office
	 *  visit records from the database*/
	private OrthopedicScheduleOVDAO orthopedicOVDAO;
	
	/**
     * EditOrthopedicScheduleOVAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public EditOrthopedicScheduleOVAction(DAOFactory factory, long loggedInMID) {
		this.orthopedicOVDAO = factory.getOrthopedicScheduleOVDAO();
	}
	
	/**
	 * Edits an existing orthopedic scheduled office visit record.
	 * @param oid The oid of the orthopedic scheduled office visit.
	 * @param p OrthopedicScheduleOVRecordBean containing the info for the record to be updated.
	 * @throws FormValidationException if the patient is not successfully validated.
	 * @throws ITrustException thrown if the database encounters an issue.
	 */
	public void editOrthopedicScheduleOV(long oid, OrthopedicScheduleOVRecordBean p)
    		throws FormValidationException, ITrustException {
		if(p != null){
			new OrthopedicScheduleOVValidator().validate(p);
			
			orthopedicOVDAO.editOrthopedicScheduledOVRecordsRecord(oid, p);
		} else {
			throw new ITrustException("Cannot edit a null Obstetrics Record.");
		}
	}
}

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
	
	
	public EditOrthopedicScheduleOVAction(DAOFactory factory, long loggedInMID) {
		this.orthopedicOVDAO = factory.getOrthopedicScheduleOVDAO();
	}
	
	
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

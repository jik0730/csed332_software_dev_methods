package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicOVRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.OrthopedicOVValidator;

/**
 * Used for edit orthopedic office visit page (editOrthopedicOVRecord.jsp). 
 * 
 * Very similar to {@link EditObstetricsAction}
 */
public class EditOrthopedicOVAction {
	/**orthopedicOVDAO is the DAO that retrieves the orthopedic office
	 *  visit records from the database*/
	private OrthopedicOVRecordDAO orthopedicOVDAO;
	/**loggedInMID is the HCP that is logged in.*/
    private long loggedInMID;
    /**loggingAction is used to write to the log.*/
    private EventLoggingAction loggingAction;

    /**
     * EditOrthopedicOVAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public EditOrthopedicOVAction(DAOFactory factory, long loggedInMID) {
		this.orthopedicOVDAO = factory.getOrthopedicOVRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
	}
	
	/**
	 * Edits an existing orthopedic office visit record.
	 * @param oid The oid of the orthopedic office visit.
	 * @param p OrthopedicOVRecordBean containing the info for the record to be updated.
	 * @throws FormValidationException if the patient is not successfully validated.
	 * @throws ITrustException thrown if the database encounters an issue.
	 */
	public void editOrthopedicOV(long oid, OrthopedicOVRecordBean p)
    		throws FormValidationException, ITrustException {
		if(p != null){
			new OrthopedicOVValidator().validate(p);
			
			orthopedicOVDAO.editOrthopedicOVRecordsRecord(oid, p);
			
			loggingAction.logEvent(TransactionType.parse(8802), loggedInMID,
					p.getPid(), "Orthopedic Office Visit " +  p.getOid() + " edited by " + loggedInMID);
		} else {
			throw new ITrustException("Cannot edit a null Orthopedic Record.");
		}
	}
}

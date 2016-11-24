package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OrthopedicSurgeryRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicSurgeryRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.OrthopedicSurgeryValidator;

/**
 * Used for edit Orthopedic office visit page (editOphalmologySurgeryRecord.jsp). 
 * 
 * Very similar to {@link EditObstetricsAction}
 */
public class EditOrthopedicSurgeryAction {
	/**OrthopedicSurgeryDAO is the DAO that retrieves the Orthopedic office
	 *  visit records from the database*/
	private OrthopedicSurgeryRecordDAO OrthopedicSurgeryDAO;
	/**loggedInMID is the HCP that is logged in.*/
    private long loggedInMID;
    /**loggingAction is used to write to the log.*/
    private EventLoggingAction loggingAction;

    /**
     * EditOrthopedicSurgeryAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public EditOrthopedicSurgeryAction(DAOFactory factory, long loggedInMID) {
		this.OrthopedicSurgeryDAO = factory.getOrthopedicSurgeryRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
	}
	
	/**
	 * Edits an existing Orthopedic office visit record.
	 * @param oid The oid of the Orthopedic office visit.
	 * @param p OrthopedicSurgeryRecordBean containing the info for the record to be updated.
	 * @throws FormValidationException if the patient is not successfully validated.
	 * @throws ITrustException thrown if the database encounters an issue.
	 */
	public void editOrthopedicSurgery(long oid, OrthopedicSurgeryRecordBean p)
    		throws FormValidationException, ITrustException {
		if(p != null){
			new OrthopedicSurgeryValidator().validate(p);
			
			OrthopedicSurgeryDAO.editOrthopedicSurgeryRecordsRecord(oid, p);
			
			loggingAction.logEvent(TransactionType.parse(9002), loggedInMID, 
					p.getMid(), "Surgical Orthopedic Office Visit " +  p.getOid() + " edited by " + loggedInMID);
		} else {
			throw new ITrustException("Cannot edit a null Surgical Orthopedic Record.");
		}
	}
}

package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OrthopedicSurgeryRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicSurgeryRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.OrthopedicSurgeryValidator;

/**
 * Used for add Orthopedic office visit page (addOphalmologySurgeryRecord.jsp). 
 * 
 * Very similar to {@link AddObstetricsAction}
 */
public class AddOrthopedicSurgeryAction {
	/**OrthopedicSurgeryDAO is the DAO that retrieves the surgical Orthopedic 
	 * office visit records from the database*/
	private OrthopedicSurgeryRecordDAO OrthopedicSurgeryDAO;
	/**loggedInMID is the HCP that is logged in.*/
    private long loggedInMID;
    /**loggingAction is used to write to the log.*/
    private EventLoggingAction loggingAction;

    /**
     * AddOrthopedicSurgeryAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the OrthopedicSurgeryRecordDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public AddOrthopedicSurgeryAction(DAOFactory factory, long loggedInMID) {
		this.OrthopedicSurgeryDAO = factory.getOrthopedicSurgeryRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
	}
	
	/**
	 * Adds a new Orthopedic office visit record.
	 * @param p OrthopedicSurgeryRecordBean containing the info for the record to be created.
	 * @throws FormValidationException if the patient is not successfully validated.
	 * @throws ITrustException thrown if the database encounters an issue.
	 */
	public void addOrthopedicSurgery(OrthopedicSurgeryRecordBean p) 
			throws FormValidationException, ITrustException {
		if(p != null){
			//Validate the bean
			new OrthopedicSurgeryValidator().validate(p);
			
			//Add the Orthopedic office visit record to the database
			OrthopedicSurgeryDAO.addOrthopedicSurgeryRecord(p);
			
			//Log the transaction
			loggingAction.logEvent(TransactionType.parse(9000), loggedInMID, 
					p.getMid(), "Surgical Orthopedic Office Visit " +  p.getOid() + " added");
		} else {
			throw new ITrustException("Cannot add a null Surgical Orthopedic Record.");
		}
	}
}

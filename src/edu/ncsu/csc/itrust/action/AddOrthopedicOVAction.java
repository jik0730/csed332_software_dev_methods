package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicOVRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.OrthopedicOVValidator;

/**
 * Used for add orthopedic office visit page (addOphalmologyOVRecord.jsp). 
 * 
 * Very similar to {@link AddObstetricsAction}
 */
public class AddOrthopedicOVAction {
	/**orthopedicOVDAO is the DAO that retrieves the orthopedic office
	 *  visit records from the database*/
	private OrthopedicOVRecordDAO orthopedicOVDAO;
	/**loggedInMID is the HCP that is logged in.*/
    private long loggedInMID;
    /**loggingAction is used to write to the log.*/
    private EventLoggingAction loggingAction;

    /**
     * AddOrthopedicOVAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public AddOrthopedicOVAction(DAOFactory factory, long loggedInMID) {
		this.orthopedicOVDAO = factory.getOrthopedicOVRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
	}
	
	/**
	 * Adds a new orthopedic office visit record.
	 * @param p OrthopedicOVRecordBean containing the info for the record to be created.
	 * @throws FormValidationException if the patient is not successfully validated.
	 * @throws ITrustException thrown if the database encounters an issue.
	 */
	public void addOrthopedicOV(OrthopedicOVRecordBean p) 
			throws FormValidationException, ITrustException {
		if(p != null){
			//Validate the bean
			new OrthopedicOVValidator().validate(p);
			
			//Add the orthopedic office visit record to the database
			orthopedicOVDAO.addOrthopedicOVRecord(p);
			
			//Log the transaction
			loggingAction.logEvent(TransactionType.parse(8800), loggedInMID, 
					p.getPid(), "Orthopedic Office Visit " +  p.getOid() + " added");
		} else {
			throw new ITrustException("Cannot add a null Orthopedic Office Visit.");
		}
	}
}

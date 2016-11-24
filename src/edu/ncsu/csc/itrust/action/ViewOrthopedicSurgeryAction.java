package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.OrthopedicSurgeryRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicSurgeryRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Used for view Orthopedic office visit page (viewOrthopedicSurgeryRecord.jsp). 
 * 
 * Very similar to {@link ViewObstetricsAction}
 */
public class ViewOrthopedicSurgeryAction {
	/**OrthopedicSurgeryDAO is the DAO that retrieves the Orthopedic office
	 *  visit records from the database*/
	private OrthopedicSurgeryRecordDAO OrthopedicSurgeryDAO;
	/**loggedInMID is the User that is logged in.*/
    private long loggedInMID;
    /**loggingAction is used to write to the log.*/
    private EventLoggingAction loggingAction;

    /**
     * ViewOrthopedicSurgeryAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public ViewOrthopedicSurgeryAction(DAOFactory factory, long loggedInMID) {
		this.OrthopedicSurgeryDAO = factory.getOrthopedicSurgeryRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
	}
	
    /**
     * getOrthopedicSurgeryByMID returns a list of Orthopedic office visits record beans for past Orthopedic care.
     * @param mid the mid of the patient.
     * @return The list of Orthopedic office visit records.
     * @throws ITrustException When there is a bad user.
     */
	public List<OrthopedicSurgeryRecordBean> getOrthopedicSurgeryByMID(long mid) throws ITrustException{
		return OrthopedicSurgeryDAO.getOrthopedicSurgeryRecordsByMID(mid);	
	}
	
	/**
	 * Retrieves an OrthopedicSurgeryRecordBean for an HCP.
	 * @param oid The oid of the Orthopedic office visit
	 * @return A bean containing the Orthopedic office visit.
	 * @throws ITrustException When there is a bad oid passed in.
	 */
	public OrthopedicSurgeryRecordBean getOrthopedicSurgeryForHCP(long oid) throws ITrustException{
		OrthopedicSurgeryRecordBean record = OrthopedicSurgeryDAO.getOrthopedicSurgeryRecord(oid);
    	loggingAction.logEvent(TransactionType.parse(9001), loggedInMID, 
				record.getMid(), "Surgical Orthopedic Office Visit " +  oid + " viewed by " + loggedInMID);
    	return record;
	}
	
	
}

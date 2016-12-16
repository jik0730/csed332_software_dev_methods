package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;
import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicOVRecordDAO;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicOVRecordDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class ViewOrthopedicOVAction {
	/**
	 * orthopedicOVDAO is the DAO that retrieves the orthopedic office visit
	 * records from the database
	 */
	private OrthopedicOVRecordDAO orthopedicOVDAO;
	/** loggedInMID is the User that is logged in. */
	private long loggedInMID;
	/** loggingAction is used to write to the log. */
	private EventLoggingAction loggingAction;
	/** PatientDAO for working with patient objects in the database */
	private PatientDAO patDAO;

	public ViewOrthopedicOVAction(DAOFactory factory, long loggedInMID) {
		this.orthopedicOVDAO = factory.getOrthopedicOVRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
		this.patDAO = new PatientDAO(factory);
	}

	/**
	 * getOrthopedicOVByMID returns a list of orthopedic office visits record
	 * beans for past orthopedic care.
	 * 
	 * @param mid
	 *            the mid of the patient.
	 * @return The list of orthopedic office visit records.
	 * @throws ITrustException
	 *             When there is a bad user.
	 */
	public List<OrthopedicOVRecordBean> getOrthopedicOVByMID(long mid) throws ITrustException {
		return orthopedicOVDAO.getOrthopedicOVRecordsByMID(mid);
	}

	/**
	 * Retrieves an OrthopedicOVRecordBean for an HCP.
	 * 
	 * @param oid
	 *            The oid of the orthopedic office visit
	 * @return A bean containing the orthopedic office visit.
	 * @throws ITrustException
	 *             When there is a bad oid passed in.
	 */
	public OrthopedicOVRecordBean getOrthopedicOVForHCP(long oid) throws ITrustException {
		OrthopedicOVRecordBean record = orthopedicOVDAO.getOrthopedicOVRecord(oid);
		loggingAction.logEvent(TransactionType.parse(8801), loggedInMID, record.getPid(),
				"Orthopedic Office Visit " + oid + " viewed by " + loggedInMID);
		return record;
	}
	
	public OrthopedicOVRecordBean getOrthopedicOVForPatient(long oid) throws ITrustException{
		OrthopedicOVRecordBean record = orthopedicOVDAO.getOrthopedicOVRecord(oid);
    	loggingAction.logEvent(TransactionType.parse(9100), loggedInMID, 
				record.getPid(), "Orthopedic Office Visit " +  oid + " viewed by " + loggedInMID);
    	return record;
	}
	
	public OrthopedicOVRecordBean getOrthopedicOVForDependent(long oid) throws ITrustException{
		OrthopedicOVRecordBean record = orthopedicOVDAO.getOrthopedicOVRecord(oid);
    	loggingAction.logEvent(TransactionType.parse(9101), loggedInMID, 
				record.getPid(), "Orthopedic Office Visit " +  oid + " viewed by " + loggedInMID);
    	return record;
	}
	
	public List<PatientBean> getDependents(long mid) {
		List<PatientBean> dependents = new ArrayList<PatientBean>();
		try {
			dependents = patDAO.getDependents(mid);
		} catch (DBException e) {
			//If a DBException occurs print a stack trace and return an empty list
			e.printStackTrace();
		}
		
		return dependents;
	}
}

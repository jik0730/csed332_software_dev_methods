package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.OrthopedicScheduleOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicScheduleOVDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Used for viewing orthopedic office visit requests.
 */
public class ViewOrthopedicScheduleOVAction {
	
	/**orthopedicOVDAO is the DAO that retrieves the orthopedic office
	 *  visit records from the database*/
	private OrthopedicScheduleOVDAO orthopedicOVDAO;
    
    /**
     * ViewOrthopedicOVAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public ViewOrthopedicScheduleOVAction(DAOFactory factory, long loggedInMID) {
		this.orthopedicOVDAO = factory.getOrthopedicScheduleOVDAO();
	}
    
    /**
     * getOrthopedicScheduleOVByMID returns a list of orthopedic scheduled office visits record beans for the patient.
     * @param mid the mid of the patient.
     * @return The list of orthopedic office visit records.
     * @throws ITrustException When there is a bad user.
     */
	public List<OrthopedicScheduleOVRecordBean> getOrthopedicScheduleOVByPATIENTMID(long mid) throws ITrustException{
		return orthopedicOVDAO.getOrthopedicScheduleOVRecordsByPATIENTMID(mid);	
	}
	
	/*
     * getOrthopedicScheduleOVByMID returns a list of orthopedic scheduled office visits record beans for the doctor.
     * @param mid the mid of the patient.
     * @return The list of orthopedic office visit records.
     * @throws ITrustException When there is a bad user.
     */
	public List<OrthopedicScheduleOVRecordBean> getOrthopedicScheduleOVByDOCTORMID(long mid) throws ITrustException{
		return orthopedicOVDAO.getOrthopedicScheduleOVRecordsByDOCTORMID(mid);	
	}
	
	
	public OrthopedicScheduleOVRecordBean getOrthopedicScheduleOVForPatient(long oid) throws ITrustException{
		OrthopedicScheduleOVRecordBean record = orthopedicOVDAO.getOrthopedicScheduleOVRecord(oid);
    	return record;
	}
	

	public OrthopedicScheduleOVRecordBean getOrthopedicScheduleOVForHCP(long oid) throws ITrustException{
		OrthopedicScheduleOVRecordBean record = orthopedicOVDAO.getOrthopedicScheduleOVRecord(oid);
    	return record;
	}
}

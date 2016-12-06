package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyScheduleOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PhysicalTherapyScheduleOVDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Used for viewing physicalTherapy office visit requests.
 */
public class ViewPhysicalTherapyScheduleOVAction {
	
	/**physicalTherapyOVDAO is the DAO that retrieves the physicalTherapy office
	 *  visit records from the database*/
	private PhysicalTherapyScheduleOVDAO physicalTherapyOVDAO;
    
    /**
     * ViewPhysicalTherapyOVAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public ViewPhysicalTherapyScheduleOVAction(DAOFactory factory, long loggedInMID) {
		this.physicalTherapyOVDAO = factory.getPhysicalTherapyScheduleOVDAO();
	}
    
    /**
     * getPhysicalTherapyScheduleOVByMID returns a list of physicalTherapy scheduled office visits record beans for the patient.
     * @param mid the mid of the patient.
     * @return The list of physicalTherapy office visit records.
     * @throws ITrustException When there is a bad user.
     */
	public List<PhysicalTherapyScheduleOVRecordBean> getPhysicalTherapyScheduleOVByPATIENTMID(long mid) throws ITrustException{
		return physicalTherapyOVDAO.getPhysicalTherapyScheduleOVRecordsByPATIENTMID(mid);	
	}
	
	/**
     * getPhysicalTherapyScheduleOVByMID returns a list of physicalTherapy scheduled office visits record beans for the doctor.
     * @param mid the mid of the patient.
     * @return The list of physicalTherapy office visit records.
     * @throws ITrustException When there is a bad user.
     */
	public List<PhysicalTherapyScheduleOVRecordBean> getPhysicalTherapyScheduleOVByDOCTORMID(long mid) throws ITrustException{
		return physicalTherapyOVDAO.getPhysicalTherapyScheduleOVRecordsByDOCTORMID(mid);	
	}
	
	/**
	 * Retrieves an PhysicalTherapyScheduleOVRecordBean for a Patient.
	 * @param oid The oid of the physicalTherapy scheduled office visit
	 * @return A bean containing the physicalTherapy scheduled office visit.
	 * @throws ITrustException When there is a bad oid passed in.
	 */
	public PhysicalTherapyScheduleOVRecordBean getPhysicalTherapyScheduleOVForPatient(long oid) throws ITrustException{
		PhysicalTherapyScheduleOVRecordBean record = physicalTherapyOVDAO.getPhysicalTherapyScheduleOVRecord(oid);
    	return record;
	}
	
	/**
	 * Retrieves an PhysicalTherapyScheduleOVRecordBean for a HCP.
	 * @param oid The oid of the physicalTherapy scheduled office visit
	 * @return A bean containing the physicalTherapy scheduled office visit.
	 * @throws ITrustException When there is a bad oid passed in.
	 */
	public PhysicalTherapyScheduleOVRecordBean getPhysicalTherapyScheduleOVForHCP(long oid) throws ITrustException{
		PhysicalTherapyScheduleOVRecordBean record = physicalTherapyOVDAO.getPhysicalTherapyScheduleOVRecord(oid);
    	return record;
	}
}

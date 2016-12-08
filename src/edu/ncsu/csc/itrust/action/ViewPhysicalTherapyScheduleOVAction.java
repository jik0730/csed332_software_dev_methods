package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyScheduleOVRecordBean;
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
    
   
	public List<PhysicalTherapyScheduleOVRecordBean> getPhysicalTherapyScheduleOVByPATIENTMID(long mid) throws ITrustException{
		return physicalTherapyOVDAO.getPhysicalTherapyScheduleOVRecordsByPATIENTMID(mid);	
	}
	
	
	public List<PhysicalTherapyScheduleOVRecordBean> getPhysicalTherapyScheduleOVByDOCTORMID(long mid) throws ITrustException{
		return physicalTherapyOVDAO.getPhysicalTherapyScheduleOVRecordsByDOCTORMID(mid);	
	}
	
	public PhysicalTherapyScheduleOVRecordBean getPhysicalTherapyScheduleOVForPatient(long oid) throws ITrustException{
		PhysicalTherapyScheduleOVRecordBean record = physicalTherapyOVDAO.getPhysicalTherapyScheduleOVRecord(oid);
    	return record;
	}
	
	
	public PhysicalTherapyScheduleOVRecordBean getPhysicalTherapyScheduleOVForHCP(long oid) throws ITrustException{
		PhysicalTherapyScheduleOVRecordBean record = physicalTherapyOVDAO.getPhysicalTherapyScheduleOVRecord(oid);
    	return record;
	}
}

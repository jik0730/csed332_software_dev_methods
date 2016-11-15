package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PhysicalTherapyOVRecordDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class ViewPhysicalTherapyOVAction {
	private PhysicalTherapyOVRecordDAO physicalTherapyOVDAO;
    private long loggedInMID;
    private EventLoggingAction loggingAction;
	private PatientDAO patDAO;

	public ViewPhysicalTherapyOVAction(DAOFactory factory, long loggedInMID) {
		this.physicalTherapyOVDAO = factory.getPhysicalTherapyOVRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
		this.patDAO = new PatientDAO(factory);
	}
	
    public List<PhysicalTherapyOVRecordBean> getPhysicalTherapyOVByMID(long mid) throws ITrustException{
		return physicalTherapyOVDAO.getPhysicalTherapyOVRecordsByMID(mid);	
	}
	
	public PhysicalTherapyOVRecordBean getPhysicalTherapyOVForHCP(long oid) throws ITrustException{
		PhysicalTherapyOVRecordBean record = physicalTherapyOVDAO.getPhysicalTherapyOVRecord(oid);
    	loggingAction.logEvent(TransactionType.parse(8301), loggedInMID, 
				record.getMid(), "PhysicalTherapy Office Visit " +  oid + " viewed by " + loggedInMID);
    	return record;
	}
	
	public PhysicalTherapyOVRecordBean getPhysicalTherapyOVForPatient(long oid) throws ITrustException{
		PhysicalTherapyOVRecordBean record = physicalTherapyOVDAO.getPhysicalTherapyOVRecord(oid);
    	loggingAction.logEvent(TransactionType.parse(8400), loggedInMID, 
				record.getMid(), "PhysicalTherapy Office Visit " +  oid + " viewed by " + loggedInMID);
    	return record;
	}
	
	public PhysicalTherapyOVRecordBean getPhysicalTherapyOVForDependent(long oid) throws ITrustException{
		PhysicalTherapyOVRecordBean record = physicalTherapyOVDAO.getPhysicalTherapyOVRecord(oid);
    	loggingAction.logEvent(TransactionType.parse(8901), loggedInMID, 
				record.getMid(), "PhysicalTherapy Office Visit " +  oid + " viewed by " + loggedInMID);
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

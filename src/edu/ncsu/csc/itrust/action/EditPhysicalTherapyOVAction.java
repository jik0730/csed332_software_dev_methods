package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PhysicalTherapyOVRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class EditPhysicalTherapyOVAction {
	
	private PhysicalTherapyOVRecordDAO physicalTherapyOVDAO;
	/**loggedInMID is the HCP that is logged in.*/
    private long loggedInMID;
    /**loggingAction is used to write to the log.*/
    private EventLoggingAction loggingAction;

	public EditPhysicalTherapyOVAction(DAOFactory factory, long loggedInMID) {
		this.physicalTherapyOVDAO = factory.getPhysicalTherapyOVRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
	}
	
	public void editPhysicalTherapyOV(long oid, PhysicalTherapyOVRecordBean p)
    		throws FormValidationException, ITrustException {
		if(p != null){
			
			physicalTherapyOVDAO.editPhysicalTherapyOVRecordsRecord(oid, p);
			
			loggingAction.logEvent(TransactionType.parse(8902), loggedInMID, 
					p.getMid(), "PhysicalTherapy Office Visit " +  p.getOid() + " edited by " + loggedInMID);
		} else {
			throw new ITrustException("Cannot edit a null Obstetrics Record.");
		}
	}
}
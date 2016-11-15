package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PhysicalTherapyOVRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class AddPhysicalTherapyOVAction {
	private PhysicalTherapyOVRecordDAO physicalTherapyOVDAO;
    private long loggedInMID;
    private EventLoggingAction loggingAction;

   public AddPhysicalTherapyOVAction(DAOFactory factory, long loggedInMID) {
		this.physicalTherapyOVDAO = factory.getPhysicalTherapyOVRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
	}
	
	public void addPhysicalTherapyOV(PhysicalTherapyOVRecordBean p) 
			throws FormValidationException, ITrustException {
		if(p != null){
			physicalTherapyOVDAO.addPhysicalTherapyOVRecord(p);
			loggingAction.logEvent(TransactionType.parse(8900), loggedInMID, 
					p.getMid(), "PhysicalTherapy Office Visit " +  p.getOid() + " added");
		} else {
			throw new ITrustException("Cannot add a null PhysicalTherapy Office Visit.");
		}
	}
}

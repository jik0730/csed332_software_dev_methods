package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PhysicalTherapyOVRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Used for edit physical therapy office visit page
 * (editPhysicalTherapyOVRecord.jsp).
 * 
 * Very similar to {@link EditObstetricsAction}
 */
public class EditPhysicalTherapyOVAction {

	private PhysicalTherapyOVRecordDAO physicalTherapyOVDAO;
	/** loggedInMID is the HCP that is logged in. */
	private long loggedInMID;
	/** loggingAction is used to write to the log. */
	private EventLoggingAction loggingAction;

	/**
	 * EditOphthalmologyOVAction is the constructor for this action class. It
	 * simply initializes the instance variables.
	 * 
	 * @param factory
	 *            The factory used to get the physicalTherapyOVDAO.
	 * @param loggedInMID
	 *            The MID of the logged in user.
	 */
	public EditPhysicalTherapyOVAction(DAOFactory factory, long loggedInMID) {
		this.physicalTherapyOVDAO = factory.getPhysicalTherapyOVRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
	}

	/**
	 * Edits an existing physical therapy office visit record.
	 * 
	 * @param oid
	 *            The oid of the physical therapy office visit.
	 * @param p
	 *            PhysicalTherapyOVRecordBean containing the info for the record
	 *            to be updated.
	 * @throws FormValidationException
	 *             if the patient is not successfully validated.
	 * @throws ITrustException
	 *             thrown if the database encounters an issue.
	 */
	public void editPhysicalTherapyOV(long oid, PhysicalTherapyOVRecordBean p)
			throws FormValidationException, ITrustException {
		if (p != null) {

			physicalTherapyOVDAO.editPhysicalTherapyOVRecordsRecord(oid, p);

			loggingAction.logEvent(TransactionType.parse(8902), loggedInMID, p.getMid(),
					"PhysicalTherapy Office Visit " + p.getOid() + " edited by " + loggedInMID);
		} else {
			throw new ITrustException("Cannot edit a null Obstetrics Record.");
		}
	}
}
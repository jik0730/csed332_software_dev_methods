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

	/**
	 * ViewPhysicalTherapyOVAction is the constructor for this action class. It
	 * simply initializes the instance variables.
	 * 
	 * @param factory
	 *            The factory used to get the obstetricsDAO.
	 * @param loggedInMID
	 *            The MID of the logged in user.
	 */
	public ViewPhysicalTherapyOVAction(DAOFactory factory, long loggedInMID) {
		this.physicalTherapyOVDAO = factory.getPhysicalTherapyOVRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
		this.patDAO = new PatientDAO(factory);
	}

	/**
	 * getPhysicalTherapyOVByMID returns a list of PhysicalTherapy office visits
	 * record beans for past PhysicalTherapy care.
	 * 
	 * @param mid
	 *            the mid of the patient.
	 * @return The list of PhysicalTherapy office visit records.
	 * @throws ITrustException
	 *             When there is a bad user.
	 */
	public List<PhysicalTherapyOVRecordBean> getPhysicalTherapyOVByMID(long mid) throws ITrustException {
		return physicalTherapyOVDAO.getPhysicalTherapyOVRecordsByMID(mid);
	}

	/**
	 * Retrieves an PhysicalTherapyOVRecordBean for an HCP.
	 * 
	 * @param oid
	 *            The oid of the PhysicalTherapy office visit
	 * @return A bean containing the PhysicalTherapy office visit.
	 * @throws ITrustException
	 *             When there is a bad oid passed in.
	 */
	public PhysicalTherapyOVRecordBean getPhysicalTherapyOVForHCP(long oid) throws ITrustException {
		PhysicalTherapyOVRecordBean record = physicalTherapyOVDAO.getPhysicalTherapyOVRecord(oid);
		loggingAction.logEvent(TransactionType.parse(8901), loggedInMID, record.getMid(),
				"PhysicalTherapy Office Visit " + oid + " viewed by " + loggedInMID);
		return record;
	}

	/**
	 * Retrieves an PhysicalTherapyOVRecordBean for a Patient.
	 * 
	 * @param oid
	 *            The oid of the PhysicalTherapy office visit
	 * @return A bean containing the PhysicalTherapy office visit.
	 * @throws ITrustException
	 *             When there is a bad oid passed in.
	 */
	public PhysicalTherapyOVRecordBean getPhysicalTherapyOVForPatient(long oid) throws ITrustException {
		PhysicalTherapyOVRecordBean record = physicalTherapyOVDAO.getPhysicalTherapyOVRecord(oid);
		loggingAction.logEvent(TransactionType.parse(9100), loggedInMID, record.getMid(),
				"PhysicalTherapy Office Visit " + oid + " viewed by " + loggedInMID);
		return record;
	}

	/**
	 * Retrieves an PhysicalTherapyOVRecordBean for Representee of the
	 * dependent.
	 * 
	 * @param oid
	 *            The oid of the PhysicalTherapy office visit
	 * @return A bean containing the PhysicalTherapy office visit.
	 * @throws ITrustException
	 *             When there is a bad oid passed in.
	 */
	public PhysicalTherapyOVRecordBean getPhysicalTherapyOVForDependent(long oid) throws ITrustException {
		PhysicalTherapyOVRecordBean record = physicalTherapyOVDAO.getPhysicalTherapyOVRecord(oid);
		loggingAction.logEvent(TransactionType.parse(9101), loggedInMID, record.getMid(),
				"PhysicalTherapy Office Visit " + oid + " viewed by " + loggedInMID);
		return record;
	}

	/**
	 * Returns a list of PatientBeans of all patients the currently logged in
	 * patient represents and are a dependent
	 * 
	 * @param mid
	 *            The mid of the patient that the dependents for are being
	 *            returned.
	 * @return a list of PatientBeans of all patients the currently logged in
	 *         patient represents and are a dependent
	 */
	public List<PatientBean> getDependents(long mid) {
		List<PatientBean> dependents = new ArrayList<PatientBean>();
		try {
			dependents = patDAO.getDependents(mid);
		} catch (DBException e) {
			// If a DBException occurs print a stack trace and return an empty
			// list
			e.printStackTrace();
		}

		return dependents;
	}

}

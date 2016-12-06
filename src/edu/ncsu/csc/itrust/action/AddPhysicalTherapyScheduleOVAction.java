package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyScheduleOVRecordBean;
import edu.ncsu.csc.itrust.beans.OrderBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PhysicalTherapyScheduleOVDAO;
import edu.ncsu.csc.itrust.dao.mysql.OrderDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.PhysicalTherapyScheduleOVValidator;

/**
 * Used for add physicalTherapy office visit requests. 
 */
public class AddPhysicalTherapyScheduleOVAction {
	/** physicalTherapyOVDAO is the DAO that adds the physicalTherapy scheduled office
	 *  visit records to the database*/
	private PhysicalTherapyScheduleOVDAO physicalTherapyOVDAO;
    /** PatientDAO for working with patient objects in the database*/
	private PersonnelDAO perDAO;
	private OrderDAO orderDAO;
    
    /**
     * AddPhysicalTherapyScheduleOVAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     */
	public AddPhysicalTherapyScheduleOVAction(DAOFactory factory, long loggedInMID) {
		this.physicalTherapyOVDAO = factory.getPhysicalTherapyScheduleOVDAO();
		this.perDAO = factory.getPersonnelDAO();
		this.orderDAO = factory.getOrderDAO();
	}
	
	/**
	 * Adds a new physicalTherapy scheduled office visit record.
	 * @param p PhysicalTherapyScheduleOVRecordBean containing the info for the record to be created.
	 * @throws FormValidationException if the patient is not successfully validated.
	 * @throws ITrustException thrown if the database encounters an issue.
	 */
	public void addPhysicalTherapyOV(PhysicalTherapyScheduleOVRecordBean p) 
			throws FormValidationException, ITrustException {
		if(p != null){
			new PhysicalTherapyScheduleOVValidator().validate(p);
			
			long pid = p.getPatientmid();
			long hid = p.getDoctormid();
			List<OrderBean> check = orderDAO.getUncompletedOrderForPair(hid, pid);
			if(check.size() ==0){
				throw new ITrustException("You can't make physical therapy appointment request without doctor's order");
			}
			
			//Add the physicalTherapy office visit record to the database
			physicalTherapyOVDAO.addPhysicalTherapyScheduleOVRecord(p);
		} else {
			throw new ITrustException("Cannot add a null PhysicalTherapy Scheduled Office Visit.");
		}
	}
	
	/**
	 * Returns a list of PersonnelBeans of all physicalTherapy personnel, ie doctors with the specialty of physicaltherapist
	 * 
	 * @return a list of PersonnelBeans of all physicalTherapy personnel
	 */
	public List<PersonnelBean> getAllPhysicalTherapyPersonnel(){
		List<PersonnelBean> personnel = new ArrayList<PersonnelBean>();
		try {
			personnel = perDAO.getAllPhysicalTherapyPersonnel();
		} catch (DBException e) {
			//If a DBException occurs print a stack trace and return an empty list
			e.printStackTrace();
		}
		
		return personnel;
	}
}

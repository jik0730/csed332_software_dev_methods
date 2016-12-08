package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.OrthopedicScheduleOVRecordBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicScheduleOVDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.OrthopedicScheduleOVValidator;

/**
 * Used for add orthopedic office visit requests. 
 */
public class AddOrthopedicScheduleOVAction {
	/** orthopedicOVDAO is the DAO that adds the orthopedic scheduled office
	 *  visit records to the database*/
	private OrthopedicScheduleOVDAO orthopedicOVDAO;
    /** PatientDAO for working with patient objects in the database*/
	private PersonnelDAO perDAO;
    
   
	public AddOrthopedicScheduleOVAction(DAOFactory factory, long loggedInMID) {
		this.orthopedicOVDAO = factory.getOrthopedicScheduleOVDAO();
		this.perDAO = factory.getPersonnelDAO();
	}
	
	
	public void addOrthopedicOV(OrthopedicScheduleOVRecordBean p) 
			throws FormValidationException, ITrustException {
		if(p != null){
			new OrthopedicScheduleOVValidator().validate(p);
			
			//Add the orthopedic office visit record to the database
			orthopedicOVDAO.addOrthopedicScheduleOVRecord(p);
		} else {
			throw new ITrustException("Cannot add a null Orthopedic Scheduled Office Visit.");
		}
	}
	
	/**
	 * Returns a list of PersonnelBeans of all orthopedic personnel, ie doctors with the specialty of orthopedic
	 * 
	 * @return a list of PersonnelBeans of all orthopedic personnel
	 */
	public List<PersonnelBean> getAllOrthopedicPersonnel(){
		List<PersonnelBean> personnel = new ArrayList<PersonnelBean>();
		try {
			personnel = perDAO.getAllOrthopedicPersonnel();
		} catch (DBException e) {
			//If a DBException occurs print a stack trace and return an empty list
			e.printStackTrace();
		}
		
		return personnel;
	}
}

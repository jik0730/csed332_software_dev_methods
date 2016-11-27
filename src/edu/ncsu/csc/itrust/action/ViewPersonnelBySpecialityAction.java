package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FakeEmailDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.Messages;

public class ViewPersonnelBySpecialityAction {
	private PersonnelDAO personnelDAO;
	private long loggedInMID;

	/**
	 * Set up defaults
	 * 
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 * @param loggedInMID The MID of the person retrieving personnel beans.
	 */
	public ViewPersonnelBySpecialityAction(DAOFactory factory, long loggedInMID) {
		this.personnelDAO = factory.getPersonnelDAO();
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Retrieves a PersonnelBeans for the speciality passed as a param
	 */
	public List<PersonnelBean> getPersonnel(String speciality) throws ITrustException {
		try {
			List<PersonnelBean> personnels = personnelDAO.getPersonnelBySpeciality(speciality);
			if (personnels != null) {
				return personnels;
			} else
				throw new ITrustException(Messages.getString("ViewPersonnelBySpecialityAction.1")); //$NON-NLS-1$
		} catch (NumberFormatException e) {
			
			throw new ITrustException(Messages.getString("ViewPersonnelBySpecialityAction.2")); //$NON-NLS-1$
		}
	}
}


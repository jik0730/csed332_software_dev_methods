/**
 * 
 */
package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.OrthopedicDiagnosisBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicDiagnosisDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * EditORDiagnosisAction
 */
public class EditORDiagnosesAction {

	private OrthopedicDiagnosisDAO diagnosesDAO;
	private String officeVisitID;

	/**
	 * EditORDiagnosesAction
	 * 
	 * @param factory
	 * @param ovIDString
	 * @throws ITrustException
	 */
	public EditORDiagnosesAction(DAOFactory factory, String ovIDString) throws ITrustException {
		diagnosesDAO = factory.getORDiagnosisDAO();
		officeVisitID = ovIDString;
	}

	/**
	 * Get all diagnoses
	 * 
	 * @return
	 * @throws DBException
	 */
	public List<OrthopedicDiagnosisBean> getDiagnoses() throws DBException {
		return diagnosesDAO.getList(Integer.parseInt(officeVisitID));
	}

	/**
	 * Add a diagnoses
	 * 
	 * @param bean
	 * @throws ITrustException
	 */
	public void addDiagnosis(OrthopedicDiagnosisBean bean) throws ITrustException {
		diagnosesDAO.add(bean);
	}

	/**
	 * Edit a diagnoses
	 * 
	 * @param bean
	 * @throws ITrustException
	 */
	public void editDiagnosis(OrthopedicDiagnosisBean bean) throws ITrustException {
		diagnosesDAO.edit(bean);

	}

	/**
	 * Delete a diagnoses
	 * 
	 * @param bean
	 * @throws ITrustException
	 */
	public void deleteDiagnosis(OrthopedicDiagnosisBean bean) throws ITrustException {
		diagnosesDAO.remove(bean.getOrDiagnosisID());

	}

	/**
	 * Get all diagnoses codes
	 * 
	 * @return
	 * @throws DBException
	 */
	public List<OrthopedicDiagnosisBean> getDiagnosisCodes() throws DBException {
		return diagnosesDAO.getOrICDCodes();
	}

}

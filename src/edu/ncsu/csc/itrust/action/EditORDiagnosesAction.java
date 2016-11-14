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
 * Handle patients Diagnosis
 * Edit Diagnosis
 * Add Diagnosis
 * Remove Diagnosis
 *  
 *  
 */
public class EditORDiagnosesAction {
	
	private OrthopedicDiagnosisDAO diagnosesDAO;
	private String officeVisitID;
	
	public EditORDiagnosesAction(DAOFactory factory, String ovIDString ) 
		throws ITrustException {
		diagnosesDAO = factory.getORDiagnosisDAO();
		officeVisitID=ovIDString;
		
	}
	
	public List<OrthopedicDiagnosisBean> getDiagnoses() throws DBException {
			return diagnosesDAO.getList(Integer.parseInt(officeVisitID));
	}
	
	public void addDiagnosis(OrthopedicDiagnosisBean bean) throws ITrustException {
		diagnosesDAO.add(bean);
	}
	
	public void editDiagnosis(OrthopedicDiagnosisBean bean) throws ITrustException {
		diagnosesDAO.edit(bean);
		
	}
	
	public void deleteDiagnosis(OrthopedicDiagnosisBean bean) throws ITrustException {
		diagnosesDAO.remove(bean.getOpDiagnosisID());
		
	}
	
	public List<OrthopedicDiagnosisBean> getDiagnosisCodes() throws DBException {
		return diagnosesDAO.getOpICDCodes();
	}
	
}

package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.beans.ApptRequestBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ApptRequestDAO;
import edu.ncsu.csc.itrust.exception.DBException;

public class ViewMyApptsActionUC92 extends ApptAction {
	private long loggedInMID;
	private ApptRequestDAO arDAO;

	public ViewMyApptsActionUC92(DAOFactory factory, long loggedInMID) {
		super(factory, loggedInMID);
		this.loggedInMID = loggedInMID;
		arDAO = factory.getApptRequestDAO();
		//

	}

	/**
	 * Gets a user's appointments
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<ApptRequestBean> getMyAppointments() throws SQLException, DBException {
		List<ApptRequestBean> reqs = arDAO.getApptRequestsForPatient(loggedInMID);
		return reqs;
	}

	/**
	 * Gets a user's appointments
	 * 
	 * @param mid
	 *            the MID of the user
	 * @return a list of the user's appointments
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<ApptRequestBean> getAppointments(long MID) throws SQLException, DBException {
		List<ApptRequestBean> reqs = arDAO.getApptRequestsForPatient(loggedInMID);
		return reqs;
	}
}

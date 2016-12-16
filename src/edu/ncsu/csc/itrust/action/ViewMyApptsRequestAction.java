package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class ViewMyApptsRequestAction extends ApptAction {
	private long loggedInMID;

	/**
	 * Action to View users' appointment Request
	 * 
	 * @param factory
	 * @param loggedInMID
	 */
	public ViewMyApptsRequestAction(DAOFactory factory, long loggedInMID) {
		super(factory, loggedInMID);
		this.loggedInMID = loggedInMID;
	}

	/**
	 * get users' Appointments
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<ApptBean> getMyAppointments() throws SQLException, DBException {
		return apptDAO.getApptsFor(loggedInMID);
	}

	/**
	 * getAll users' Appointments
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<ApptBean> getAllMyAppointments() throws SQLException, DBException {
		return apptDAO.getAllApptsFor(loggedInMID);
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
	public List<ApptBean> getAppointments(long MID) throws SQLException, DBException {
		return apptDAO.getApptsFor(MID);
	}
}

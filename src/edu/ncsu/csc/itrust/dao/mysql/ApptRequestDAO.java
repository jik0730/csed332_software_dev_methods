package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.ApptRequestBean;
import edu.ncsu.csc.itrust.beans.loaders.ApptRequestBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * 
 *
 */
@SuppressWarnings({})
public class ApptRequestDAO {

	private transient final DAOFactory factory;
	private transient final ApptRequestBeanLoader loader;

	public ApptRequestDAO(final DAOFactory factory) {
		this.factory = factory;
		loader = new ApptRequestBeanLoader();
	}

	/**
	 * 
	 * @param hcpid
	 * @return
	 * @throws DBException
	 */
	public List<ApptRequestBean> getApptRequestsFor(final long hcpid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = factory.getConnection();

			stmt = conn.prepareStatement("SELECT * FROM appointmentrequests WHERE doctor_id=? ORDER BY sched_date");

			stmt.setLong(1, hcpid);

			final ResultSet results = stmt.executeQuery();
			final List<ApptRequestBean> list = loader.loadList(results);
			results.close();
			stmt.close();
			return list;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, stmt);
		}

	}

	/**
	 * 
	 * @param pid
	 * @return
	 * @throws DBException
	 */
	public List<ApptRequestBean> getApptRequestsForPatient(final long pid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = factory.getConnection();

			stmt = conn.prepareStatement("SELECT * FROM appointmentrequests WHERE patient_id=? ORDER BY sched_date");

			stmt.setLong(1, pid);

			final ResultSet results = stmt.executeQuery();
			final List<ApptRequestBean> list = loader.loadList(results);
			results.close();
			stmt.close();
			return list;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, stmt);
		}

	}

	/**
	 * getAllConflicts
	 * 
	 * @param mid
	 * @return List<ApptRequestBean>
	 * @throws SQLException
	 * @throws DBException
	 */
	public List<ApptRequestBean> getAllConflicts(final long mid) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			conn = factory.getConnection();

			pstring = conn.prepareStatement("SELECT a1.* " + "FROM appointment a1, appointment a2, " + // all
																										// possible
																										// sets
																										// of
																										// 2
																										// appts
					"appointmenttype type1,appointmenttype type2 " + // and the
																		// corresponding
																		// types
					"WHERE a1.appt_id!=a2.appt_id AND " + // exclude itself
					"a1.appt_type=type1.appt_type AND a2.appt_type=type2.appt_type AND " + // match
																							// them
																							// with
																							// types
					"((DATE_ADD(a1.sched_date, INTERVAL type1.duration MINUTE)>a2.sched_date AND " + // a1
																										// ends
																										// after
																										// a2
																										// starts
																										// AND
					"a1.sched_date<=a2.sched_date) OR" + // a1 starts before a2
															// OR
					"(DATE_ADD(a2.sched_date, INTERVAL type2.duration MINUTE)>a1.sched_date AND " + // a2
																									// ends
																									// after
																									// a1
																									// starts
																									// AND
					"a2.sched_date<=a1.sched_date)) AND " + // a2 starts before
															// a1 starts
					"a1.patient_id=? AND a2.patient_id=?;");

			pstring.setLong(1, mid);
			pstring.setLong(2, mid);

			final ResultSet results = pstring.executeQuery();
			final List<ApptRequestBean> conflictList = loader.loadList(results);
			results.close();
			pstring.close();
			return conflictList;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}

	}

	/**
	 * add ApptRequest
	 * 
	 * @param req
	 * @throws SQLException
	 * @throws DBException
	 */
	public void addApptRequest(final ApptRequestBean req) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = factory.getConnection();

			stmt = conn.prepareStatement(
					"INSERT INTO appointmentrequests (appt_type, patient_id, doctor_id, sched_date, comment, pending, accepted) VALUES (?, ?, ?, ?, ?, ?, ?)");
			loader.loadParameters(stmt, req);

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, stmt);
		}

	}

	/**
	 * update ApptRequest
	 * 
	 * @param req
	 * @throws SQLException
	 * @throws DBException
	 */
	public void updateApptRequest(final ApptRequestBean req) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = factory.getConnection();

			stmt = conn.prepareStatement("UPDATE appointmentrequests SET pending=?, accepted=? WHERE appt_id=?");

			stmt.setBoolean(1, req.isPending());
			stmt.setBoolean(2, req.isAccepted());
			stmt.setInt(3, req.getRequestedAppt().getApptID());

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, stmt);
		}
	}

	/**
	 * getApptRequest
	 * 
	 * @param reqID
	 * @return ApptRequestBean
	 * @throws SQLException
	 * @throws DBException
	 */
	public ApptRequestBean getApptRequest(final int reqID) throws SQLException, DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ApptRequestBean bean;
		try {
			conn = factory.getConnection();

			stmt = conn.prepareStatement("SELECT * FROM appointmentrequests WHERE appt_id=?");

			stmt.setInt(1, reqID);
			final ResultSet results = stmt.executeQuery();
			if (results.next()) {
				bean = loader.loadSingle(results);
				results.close();
				stmt.close();
			} else {
				bean = null;
				results.close();
				stmt.close();
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, stmt);
		}
		return bean;
	}
}

package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.TransactionLogBean;
import edu.ncsu.csc.itrust.beans.loaders.TransactionLogBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.enums.TransactionLogColumnType;

/**
 * TransactionLogDAO is for retrieving transaction log, user role
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be
 * reflections of the database, that is, one DAO per table in the database (most
 * of the time). For more complex sets of queries, extra DAOs are added. DAOs
 * can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than
 * a factory. All DAOs should be accessed by DAOFactory (@see
 * {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 * 
 */
public class TransactionLogDAO {
	private DAOFactory factory;
	private TransactionLogBeanLoader loader = new TransactionLogBeanLoader();

	/**
	 * The typical constructor.
	 * 
	 * @param factory
	 *            The {@link DAOFactory} associated with this DAO, which is used
	 *            for obtaining SQL connections, etc.
	 */
	public TransactionLogDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * To get transaction log data grouped by type
	 * 
	 * @param type
	 *            : attributes(type 0 : loggedinRole, type 1 : secondaryRole,
	 *            type 2 : transactionType(Code)
	 * @return List<TransactionLogBean>
	 * @throws DBException
	 * @throws ITrustException
	 */
	public List<TransactionLogBean> getTransactionGroupBy(TransactionLogColumnType type)
			throws DBException, ITrustException {
		Connection conn = null;
		PreparedStatement ps = null;
		String s;
		try {
			conn = factory.getConnection();
			s = "SELECT u1.Role as loggedInRole, u2.Role as secondaryRole, t.transactionID as transactionID, "
					+ "t.transactionCode as transactionCode, t.timeLogged as timeLogged, " + "t.addedInfo as addedInfo "
					+ "FROM transactionlog t, users u1, users u2 "
					+ "WHERE t.loggedInMID = u1.MID AND t.secondaryMID = u2.MID ";
			switch (type.getCode()) {
			case 1:
				s += "GROUP BY loggedinRole ";
				break;
			case 2:
				s += "GROUP BY secondaryRole ";
				break;
			case 3:
				s += "GROUP BY transactionCode ";
				break;
			default:
				throw new ITrustException("Wrong input type!");
			}
			s += "ORDER BY timeLogged DESC";
			ps = conn.prepareStatement(s);
			ResultSet rs = ps.executeQuery();
			List<TransactionLogBean> tbList = loader.loadList(rs);
			rs.close();
			ps.close();
			return tbList;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Get transaction list which is satisfying some criteria(inputs).
	 * 
	 * @param loggedInRole
	 *            Role of the logged in user of log
	 * @param secondaryRole
	 *            Role of the logged in user of log
	 * @param start
	 *            Index to start pulling entries from
	 * @param end
	 *            Index to end pulling entries to
	 * @param trasaction
	 *            code
	 * @return List of <range> TransactionLogBeans satisfying some criteria
	 * @throws DBException
	 */
	public List<TransactionLogBean> getTransactionList(String loggedInRole, String secondaryRole, java.util.Date start,
			java.util.Date end, int transactionCode) throws DBException {

		Connection conn = null;
		PreparedStatement ps = null;
		String s;
		try {
			conn = factory.getConnection();
			s = "SELECT u1.Role as loggedInRole, u2.Role as secondaryRole, t.transactionID as transactionID, "
					+ "t.transactionCode as transactionCode, t.timeLogged as timeLogged, " + "t.addedInfo as addedInfo "
					+ "FROM transactionlog t, users u1, users u2 "
					+ "WHERE t.loggedInMID = u1.MID AND t.secondaryMID = u2.MID "
					+ "AND (t.timeLogged >= ? AND t.timeLogged <= ?)";
			if (!loggedInRole.equals("all roles")) {
				s += " AND (u1.Role = \"" + loggedInRole.toString() + "\")";
			}
			if (!secondaryRole.equals("all roles")) {
				s += " AND (u2.Role = \"" + secondaryRole.toString() + "\")";
			}
			if (transactionCode >= 0) {
				s += " AND (t.transactionCode = " + Integer.toString(transactionCode) + ")";
			}
			s += " ORDER BY timeLogged DESC";
			ps = conn.prepareStatement(s);
			ps.setTimestamp(1, new Timestamp(start.getTime()));
			// Adjustment of end date.
			end = new java.util.Date(end.getTime() + (1000 * 60 * 60 * 24));
			ps.setTimestamp(2, new Timestamp(end.getTime()));
			ResultSet rs = ps.executeQuery();
			List<TransactionLogBean> tbList = loader.loadList(rs);
			rs.close();
			ps.close();
			return tbList;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}

package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.OrthopedicSurgeryRecordBean;
import edu.ncsu.csc.itrust.beans.loaders.OrthopedicSurgeryRecordLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for managing all static information related to an Orthopedic office
 * visit record. For other information related to all aspects of patient care,
 * see the other DAOs.
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be 
 * reflections of the database, that is, one DAO per table in the database 
 * (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor
 * than a factory. All DAOs should be accessed by DAOFactory 
 * (@see {@link DAOFactory}) and every DAO should have a factory - for 
 * obtaining JDBC connections and/or accessing other DAOs.
 */
public class OrthopedicSurgeryRecordDAO {

	/** Used to get database connections*/
	private DAOFactory factory;
	/** Used to load data from ResultSets, and into PreparedStatements.*/
	private OrthopedicSurgeryRecordLoader OrthopedicLoader;
	
	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which
	 * is used for obtaining SQL connections, etc.
	 */
	public OrthopedicSurgeryRecordDAO(DAOFactory factory) {
		this.factory = factory;
		this.OrthopedicLoader = new OrthopedicSurgeryRecordLoader();
	}
	
	/**
	 * Adds an Orthopedic office visit record to the table.
	 * @param p The Orthopedic office visit record to be added to the database.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public void addOrthopedicSurgeryRecord(OrthopedicSurgeryRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			//first, insert the record
			ps = conn.prepareStatement("INSERT INTO OrthopedicSurgery (MID,dateVisit,"
					+ "docLastName,docFirstName,surgery,surgeryNotes)"
					+ " VALUES(?,?,?,?,?,?)");
			ps = OrthopedicLoader.loadParameters(ps, p);
			ps.executeUpdate(); //HERE
			ps.close();
			ps = conn.prepareStatement("SELECT * FROM OrthopedicSurgery ORDER BY oid DESC limit 1");
			//then, set the OID of the original bean to the one the database generates
			ResultSet rs = ps.executeQuery();
			rs.first();
			p.setOid(rs.getLong("oid"));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns an Orthopedic office visit record correlating to the given 
	 * OID.
	 * @param oid The Orthopedic office visit id.
	 * @return ObstetricsRecordBean containing the information from the 
	 * Orthopedic office visit record.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public OrthopedicSurgeryRecordBean getOrthopedicSurgeryRecord(long oid) 
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM OrthopedicSurgery WHERE OID" + " = ?");
			ps.setLong(1, oid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				OrthopedicSurgeryRecordBean pat = OrthopedicLoader.loadSingle(rs);
				rs.close();
				ps.close();
				return pat;
			} else{
				rs.close();
				ps.close();
				return null;
			}
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Returns a list of Orthopedic office visit records correlating to the
	 * given MID.
	 * @param mid the patient's id.
	 * @return a list of ObstetricsRecordBean's with the information from all
	 * the Orthopedic office visit records correlating to the given MID.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public List<OrthopedicSurgeryRecordBean> 
			getOrthopedicSurgeryRecordsByMID(long mid)throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM OrthopedicSurgery WHERE MID=? ORDER BY dateVisit DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<OrthopedicSurgeryRecordBean> list = OrthopedicLoader.loadList(rs);
			rs.close();
			ps.close();
			return list;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Updates an Orthopedic office visit record for the given OID.
	 * @param oid the Orthopedic office visit id.
	 * @param p The OrthopedicSurgeryRecordBean representing the new information for
	 * the Orthopedic office visit record.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public void editOrthopedicSurgeryRecordsRecord(long oid, OrthopedicSurgeryRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE OrthopedicSurgery SET MID=?,dateVisit=?,docLastName=?,docFirstName=?,"
					+ "surgery=?,surgeryNotes=? "
					+ "WHERE OID=?");
			OrthopedicLoader.loadParameters(ps, p);
			ps.setLong(7, oid);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}
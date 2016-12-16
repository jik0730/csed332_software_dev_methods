package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.OrthopedicScheduleOVRecordBean;
import edu.ncsu.csc.itrust.beans.loaders.OrthopedicScheduleOVRecordLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for managing all static information related to a scheduled orthopedic office
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
public class OrthopedicScheduleOVDAO {
	/** Used to get database connections*/
	private DAOFactory factory;
	/** Used to load data from ResultSets, and into PreparedStatements.*/
	private OrthopedicScheduleOVRecordLoader orthopedicScheduleOVLoader;
	
	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which
	 * is used for obtaining SQL connections, etc.
	 */
	public OrthopedicScheduleOVDAO(DAOFactory factory) {
		this.factory = factory;
		this.orthopedicScheduleOVLoader = new OrthopedicScheduleOVRecordLoader();
	}
	

	/**
	 * Adds a scheduled Orthopedic office visit record to the table.
	 * @param p The scheduled Orthopedic office visit record to be added to the database.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public void addOrthopedicScheduleOVRecord(OrthopedicScheduleOVRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			//first, insert the record
			ps = conn.prepareStatement("INSERT INTO orthopedicSchedule(PATIENTMID,DOCTORMID,"
					+ "dateTime,docLastName,docFirstName,comments,pending,accepted)"
					+ " VALUES(?,?,?,?,?,?,?,?)");
			ps = orthopedicScheduleOVLoader.loadParameters(ps, p);
			ps.executeUpdate();
			ps.close();
			
			//then, set the OID of the original bean to the one the database generates
			ps = conn.prepareStatement("SELECT * FROM orthopedicSchedule ORDER BY oid DESC limit 1");
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
	 * Returns a scheduled orthopedic office visit record correlating to the given 
	 * OID.
	 * @param oid The scheduled Orthopedic office visit id.
	 * @return OrthopedicScheduleOVRecordBean containing the information from the 
	 * scheduled Orthopedic office visit record.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public OrthopedicScheduleOVRecordBean getOrthopedicScheduleOVRecord(long oid) 
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM orthopedicSchedule WHERE OID" + " = ?");
			ps.setLong(1, oid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				OrthopedicScheduleOVRecordBean pat = orthopedicScheduleOVLoader.loadSingle(rs);
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
	 * Returns a list of scheduled orthopedic office visit records correlating to the
	 * given PATIENTMID.
	 * @param mid the patient's id.
	 * @return a list of OrthopedicScheduleOVRecordBean's with the information from all
	 * the scheduled orthopedic office visit records correlating to the given PATIENTMID.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public List<OrthopedicScheduleOVRecordBean> 
			getOrthopedicScheduleOVRecordsByPATIENTMID(long mid)throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM orthopedicSchedule WHERE PATIENTMID=? ORDER BY dateTime DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<OrthopedicScheduleOVRecordBean> list = orthopedicScheduleOVLoader.loadList(rs);
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
	 * Returns a list of scheduled orthopedic office visit records correlating to the
	 * given DOCTORMID.
	 * @param mid the patient's id.
	 * @return a list of OrthopedicScheduleOVRecordBean's with the information from all
	 * the scheduled orthopedic office visit records correlating to the given DOCTORMID.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public List<OrthopedicScheduleOVRecordBean> 
			getOrthopedicScheduleOVRecordsByDOCTORMID(long mid)throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM orthopedicSchedule WHERE DOCTORMID=? ORDER BY dateTime DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<OrthopedicScheduleOVRecordBean> list = orthopedicScheduleOVLoader.loadList(rs);
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
	 * Updates a scheduled orthopedic office visit record for the given OID.
	 * @param oid the scheduled orthopedic office visit id.
	 * @param p The OrthopedicScheduleOVRecordBean representing the new information for
	 * the scheduled orthopedic office visit record.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public void editOrthopedicScheduledOVRecordsRecord(long oid, OrthopedicScheduleOVRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE orthopedicSchedule SET PATIENTMID=?,DOCTORMID=?,dateTime=?,"
					+ "docLastName=?,docFirstName=?,comments=?,pending=?,accepted=? "
					+ "WHERE OID=?");
			orthopedicScheduleOVLoader.loadParameters(ps, p);
			ps.setLong(9, oid);
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

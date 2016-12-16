package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.PhysicalTherapyScheduleOVRecordBean;
import edu.ncsu.csc.itrust.beans.loaders.PhysicalTherapyScheduleOVRecordLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for managing all static information related to a scheduled physicalTherapy office
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
public class PhysicalTherapyScheduleOVDAO {
	/** Used to get database connections*/
	private DAOFactory factory;
	/** Used to load data from ResultSets, and into PreparedStatements.*/
	private PhysicalTherapyScheduleOVRecordLoader physicalTherapyScheduleOVLoader;
	
	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which
	 * is used for obtaining SQL connections, etc.
	 */
	public PhysicalTherapyScheduleOVDAO(DAOFactory factory) {
		this.factory = factory;
		this.physicalTherapyScheduleOVLoader = new PhysicalTherapyScheduleOVRecordLoader();
	}
	

	/**
	 * Adds a scheduled PhysicalTherapy office visit record to the table.
	 * @param p The scheduled PhysicalTherapy office visit record to be added to the database.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public void addPhysicalTherapyScheduleOVRecord(PhysicalTherapyScheduleOVRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			//first, insert the record
			ps = conn.prepareStatement("INSERT INTO physicalTherapySchedule(PATIENTMID,DOCTORMID,"
					+ "dateTime,docLastName,docFirstName,comments,pending,accepted)"
					+ " VALUES(?,?,?,?,?,?,?,?)");
			ps = physicalTherapyScheduleOVLoader.loadParameters(ps, p);
			ps.executeUpdate();
			ps.close();
			
			//then, set the OID of the original bean to the one the database generates
			ps = conn.prepareStatement("SELECT * FROM physicalTherapySchedule ORDER BY oid DESC limit 1");
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
	 * Returns a scheduled physicalTherapy office visit record correlating to the given 
	 * OID.
	 * @param oid The scheduled PhysicalTherapy office visit id.
	 * @return PhysicalTherapyScheduleOVRecordBean containing the information from the 
	 * scheduled PhysicalTherapy office visit record.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public PhysicalTherapyScheduleOVRecordBean getPhysicalTherapyScheduleOVRecord(long oid) 
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM physicalTherapySchedule WHERE OID" + " = ?");
			ps.setLong(1, oid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				PhysicalTherapyScheduleOVRecordBean pat = physicalTherapyScheduleOVLoader.loadSingle(rs);
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
	 * Returns a list of scheduled physicalTherapy office visit records correlating to the
	 * given PATIENTMID.
	 * @param mid the patient's id.
	 * @return a list of PhysicalTherapyScheduleOVRecordBean's with the information from all
	 * the scheduled physicalTherapy office visit records correlating to the given PATIENTMID.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public List<PhysicalTherapyScheduleOVRecordBean> 
			getPhysicalTherapyScheduleOVRecordsByPATIENTMID(long mid)throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM physicalTherapySchedule WHERE PATIENTMID=? ORDER BY dateTime DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<PhysicalTherapyScheduleOVRecordBean> list = physicalTherapyScheduleOVLoader.loadList(rs);
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
	 * Returns a list of scheduled physicalTherapy office visit records correlating to the
	 * given DOCTORMID.
	 * @param mid the patient's id.
	 * @return a list of PhysicalTherapyScheduleOVRecordBean's with the information from all
	 * the scheduled physicalTherapy office visit records correlating to the given DOCTORMID.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public List<PhysicalTherapyScheduleOVRecordBean> 
			getPhysicalTherapyScheduleOVRecordsByDOCTORMID(long mid)throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM physicalTherapySchedule WHERE DOCTORMID=? ORDER BY dateTime DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<PhysicalTherapyScheduleOVRecordBean> list = physicalTherapyScheduleOVLoader.loadList(rs);
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
	 * Updates a scheduled physicalTherapy office visit record for the given OID.
	 * @param oid the scheduled physicalTherapy office visit id.
	 * @param p The PhysicalTherapyScheduleOVRecordBean representing the new information for
	 * the scheduled physicalTherapy office visit record.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public void editPhysicalTherapyScheduledOVRecordsRecord(long oid, PhysicalTherapyScheduleOVRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE physicalTherapySchedule SET PATIENTMID=?,DOCTORMID=?,dateTime=?,"
					+ "docLastName=?,docFirstName=?,comments=?,pending=?,accepted=? "
					+ "WHERE OID=?");
			physicalTherapyScheduleOVLoader.loadParameters(ps, p);
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

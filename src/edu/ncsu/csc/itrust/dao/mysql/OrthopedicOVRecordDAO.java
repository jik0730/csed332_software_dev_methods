package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.OphthalmologyOVRecordBean;
import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;
import edu.ncsu.csc.itrust.beans.loaders.OrthopedicOVRecordLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class OrthopedicOVRecordDAO {
	private DAOFactory factory;
	private OrthopedicOVRecordLoader orthopedicLoader;
	
	public OrthopedicOVRecordDAO(DAOFactory factory) {
		this.factory = factory;
		this.orthopedicLoader = new OrthopedicOVRecordLoader();
	}
	
	/**
	 * Return list of records that match given mid
	 * @param pid is the patient's mid
	 */
	public List<OrthopedicOVRecordBean> getOrthopedicOVRecordsByMID(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"SELECT OrthopedicVisitID, PatientiD, HCPID, DateVisit, Injured, MRIReport "
					+ "FROM orthopedic WHERE PatientID=? ORDER BY DateVisit DESC"
				);
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			List<OrthopedicOVRecordBean> list = orthopedicLoader.loadList(rs);
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
	 * Returns an orthopedic office visit record correlating to the given 
	 * orthopedicVisitID.
	 * @param oid The orthopedic office visit id.
	 * @return OrthopedicOVRecordBean containing the information from the 
	 * Orthopedic office visit record.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public OrthopedicOVRecordBean getOrthopedicOVRecord(long oid) 
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM orthopedic WHERE OrthopedicVisitID = ?");
			ps.setLong(1, oid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				OrthopedicOVRecordBean pat = orthopedicLoader.loadSingle(rs);
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
	 * Add a record to DB.
	 * @param p
	 * @throws DBException
	 */
	public void addOrthopedicOVRecord(OrthopedicOVRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			//first, insert the record
			ps = conn.prepareStatement("INSERT INTO orthopedic(PatientID,"
					+ "HCPID, DateVisit, Injured, XRay, MRI, MRIReport)"
					+ " VALUES(?,?,?,?,?,?,?)");
			ps = orthopedicLoader.loadParameters(ps, p);
			ps.executeUpdate();
			ps.close();
			
			//then, set the OID of the original bean to the one the database generates
			ps = conn.prepareStatement("SELECT * FROM orthopedic ORDER BY OrthopedicVisitID DESC limit 1");
			ResultSet rs = ps.executeQuery();
			rs.first();
			p.setOid(rs.getInt("OrthopedicVisitID"));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Updates an orthopedic office visit record for the given OID.
	 * @param oid the orthopedic office visit id.
	 * @param p The OrthopedicOVRecordBean representing the new information for
	 * the orthopedic office visit record.
	 * @throws DBException thrown when the database throws a SQLException.
	 */
	public void editOrthopedicOVRecordsRecord(long oid, OrthopedicOVRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE orthopedic SET PatientID=?, HCPID=?, DateVisit=?, Injured=?, "
					+ "XRay=?, MRI=?, MRIReport=?"
					+ "WHERE OrthopedicVisitID=?");
			orthopedicLoader.loadParameters(ps, p);
			ps.setLong(8, oid);
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

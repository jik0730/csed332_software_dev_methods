package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean;
import edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean;
import edu.ncsu.csc.itrust.beans.loaders.PhysicalTherapyOVRecordLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class PhysicalTherapyOVRecordDAO {

	private DAOFactory factory;
	private PhysicalTherapyOVRecordLoader physicalTherapyLoader;

	/**
	 * The typical constructor.
	 * 
	 * @param factory
	 *            The {@link DAOFactory} associated with this DAO, which is used
	 *            for obtaining SQL connections, etc.
	 */
	public PhysicalTherapyOVRecordDAO(final DAOFactory factory) {
		this.factory = factory;
		physicalTherapyLoader = new PhysicalTherapyOVRecordLoader();
	}

	public PhysicalTherapyOVRecordBean getPhysicalTherapyOVRecord(long oid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM physicalTherapy WHERE OID" + " = ?");
			ps.setLong(1, oid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				PhysicalTherapyOVRecordBean pat = physicalTherapyLoader.loadSingle(rs);
				rs.close();
				ps.close();
				return pat;
			} else {
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

	public void addPhysicalTherapyOVRecord(PhysicalTherapyOVRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO physicalTherapy(MID,dateVisit,"
					+ "docLastName,docFirstName,WellnessSurveyResults,WellnessSurveyScore,Exercise)"
					+ " VALUES(?,?,?,?,?,?,?)");
			ps = physicalTherapyLoader.loadParameters(ps, p);
			ps.executeUpdate();
			ps.close();

			ps = conn.prepareStatement("SELECT * FROM physicalTherapy ORDER BY oid DESC limit 1");
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

	public List<PhysicalTherapyOVRecordBean> getPhysicalTherapyOVRecordsByMID(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM physicalTherapy WHERE MID=? ORDER BY dateVisit DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();

			List<PhysicalTherapyOVRecordBean> pat = physicalTherapyLoader.loadList(rs);
			rs.close();
			ps.close();
			
			return pat;
		} catch (SQLException e) {
			System.out.println(e);
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public void editPhysicalTherapyOVRecordsRecord(long oid, PhysicalTherapyOVRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE physicalTherapy SET MID=?,dateVisit=?,docLastName=?,docFirstName=?,"
					+ "WellnessSurveyResults=?, WellnessSurveyScore=? ,Exercise=? where OID=?"
					);
			ps = physicalTherapyLoader.loadParameters(ps, p);
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

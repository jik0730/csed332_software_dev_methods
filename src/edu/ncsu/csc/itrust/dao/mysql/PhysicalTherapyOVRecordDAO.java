package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
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

	/**
	 * get physical therapy record with oid
	 * 
	 * @param oid
	 *            office visit id of physical therapy OV
	 * @return pb physical therapy record bean containing data
	 * @throws DBException
	 *             for DB excepted case
	 */
	public PhysicalTherapyOVRecordBean getPhysicalTherapyOVRecord(long oid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM physicalTherapy WHERE OID" + " = ?");
			ps.setLong(1, oid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				PhysicalTherapyOVRecordBean pb = physicalTherapyLoader.loadSingle(rs);
				rs.close();
				ps.close();
				return pb;
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

	/**
	 * add new physical therapy office visit
	 * 
	 * @param p
	 *            bean containing data of new OV
	 * @throws DBException
	 *             for DB excepted case
	 */
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

	/**
	 * get list of physical therapy office visit by doctor's mid
	 * 
	 * @param mid
	 *            mid of doctor
	 * @return list of physicla therapy office visit bean
	 * @throws DBException
	 */
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

	/**
	 * edit physical therapy office visit with bean
	 * 
	 * @param oid
	 *            office visit id of physical therapy OV
	 * @param p
	 *            physical therapy bean containing data for editing
	 * @throws DBException
	 *             for DB excepted case
	 */
	public void editPhysicalTherapyOVRecordsRecord(long oid, PhysicalTherapyOVRecordBean p) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE physicalTherapy SET MID=?,dateVisit=?,docLastName=?,docFirstName=?,"
					+ "WellnessSurveyResults=?, WellnessSurveyScore=? ,Exercise=? where OID=?");
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

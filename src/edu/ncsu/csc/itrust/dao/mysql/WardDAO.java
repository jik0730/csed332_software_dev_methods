package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.WardBean;
import edu.ncsu.csc.itrust.beans.WardRoomBean;
import edu.ncsu.csc.itrust.beans.loaders.HospitalBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.PersonnelLoader;
import edu.ncsu.csc.itrust.beans.loaders.WardBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.WardRoomBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Used for managing Wards
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
 * 
 */
public class WardDAO {
	private DAOFactory factory;
	private WardBeanLoader wardLoader = new WardBeanLoader();
	private WardRoomBeanLoader wardRoomLoader = new WardRoomBeanLoader();
	private PersonnelLoader personnelLoader = new PersonnelLoader();
	private HospitalBeanLoader hospitalLoader = new HospitalBeanLoader();

	/**
	 * The typical constructor.
	 * 
	 * @param factory
	 *            The {@link DAOFactory} associated with this DAO, which is used
	 *            for obtaining SQL connections, etc.
	 */
	public WardDAO(DAOFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns a list of all wards under a hospital sorted alphabetically
	 * 
	 * @param id
	 *            The ID of the hospital to get wards from
	 * @return A java.util.List of WardBeans.
	 * @throws DBException
	 */
	public List<WardBean> getAllWardsByHospitalID(String id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM WARDS WHERE InHospital = ? ORDER BY RequiredSpecialty");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			List<WardBean> loadlist = wardLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Adds a Ward
	 * 
	 * @param ward
	 *            The WardBean object to insert.
	 * @return A boolean indicating whether the insertion was successful.
	 * @throws DBException
	 * @throws ITrustException
	 */
	public boolean addWard(WardBean ward) throws DBException, ITrustException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO Wards (RequiredSpecialty, InHospital) " + "VALUES (?,?)");
			ps.setString(1, ward.getRequiredSpecialty());
			ps.setLong(2, ward.getInHospital());
			boolean check = (1 == ps.executeUpdate());
			ps.close();
			return check;
		} catch (SQLException e) {

			if (1062 == e.getErrorCode())
				throw new ITrustException("Error: Ward already exists.");
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Updates a particular ward's information. Returns the number of rows
	 * affected (should be 1)
	 * 
	 * @param ward
	 *            The WardBean to update.
	 * @return An int indicating the number of affected rows.
	 * @throws DBException
	 */
	public int updateWard(WardBean ward) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE wards SET RequiredSpecialty=?, InHospital=? " + "WHERE WardID = ?");
			ps.setString(1, ward.getRequiredSpecialty());
			ps.setLong(2, ward.getInHospital());
			ps.setLong(3, ward.getWardID());
			int result = ps.executeUpdate();
			ps.close();
			return result;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Removes a ward from a hospital. Returns whether or not any changes were
	 * made
	 * 
	 * @param id
	 *            The WardId of the Ward to remove.
	 * @return A boolean indicating success.
	 * @throws DBException
	 */
	public boolean removeWard(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM Wards WHERE WardID = ?");
			ps.setLong(1, id);
			boolean check = (1 == ps.executeUpdate());
			ps.close();
			return check;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a list of all wardrooms under a ward sorted alphabetically
	 * 
	 * @param id
	 *            The id of the ward to get all rooms for
	 * @return A java.util.List of all WardRoomBeans in a ward.
	 * @throws DBException
	 */
	public List<WardRoomBean> getAllWardRoomsByWardID(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM WARDROOMS WHERE InWard = ? ORDER BY RoomName");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			List<WardRoomBean> loadlist = wardRoomLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Return list of wardrooms which are waiting
	 * 
	 * @param waiting
	 * @return A java.util.List of all WardRoomBeans waiting
	 * @throws DBException
	 */
	public List<WardRoomBean> getAllWardRoomsByWaiting(long waiting) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM WARDROOMS WHERE Waiting = ? ORDER BY RoomName");
			ps.setLong(1, waiting);
			ResultSet rs = ps.executeQuery();
			List<WardRoomBean> loadlist = wardRoomLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Adds a WardRoom
	 * 
	 * @param wardRoom
	 *            The WardRoomBean object to insert.
	 * @return A boolean indicating whether the insertion was successful.
	 * @throws DBException
	 * @throws ITrustException
	 */
	public boolean addWardRoom(WardRoomBean wardRoom) throws DBException, ITrustException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"INSERT INTO WardRooms (InWard, RoomName, Status, State, Price, Story) " + "VALUES (?,?,?,?,?,?)");
			ps.setLong(1, wardRoom.getInWard());
			ps.setString(2, wardRoom.getRoomName());
			ps.setString(3, wardRoom.getStatus());
			ps.setBoolean(4, wardRoom.getState());
			ps.setInt(5, wardRoom.getPrice());
			ps.setInt(6, wardRoom.getStory());
			boolean check = (1 == ps.executeUpdate());
			ps.close();
			return check;
		} catch (SQLException e) {

			if (1062 == e.getErrorCode())
				throw new ITrustException("Error: WardRoom already exists.");
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Updates a particular wardroom's information. Returns the number of rows
	 * affected (should be 1)
	 * 
	 * @param wardRoom
	 *            The WardRoomBean to update.
	 * @return An int indicating the number of affected rows.
	 * @throws DBException
	 */
	public int updateWardRoom(WardRoomBean wardRoom) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"UPDATE wardrooms SET InWard=?, RoomName=?, Status=?, State=?, Waiting=?, Price=?, Story=? "
							+ "WHERE RoomID = ?");
			ps.setLong(1, wardRoom.getInWard());
			ps.setString(2, wardRoom.getRoomName());
			ps.setString(3, wardRoom.getStatus());
			ps.setBoolean(4, wardRoom.getState());
			ps.setLong(5, wardRoom.getWaiting());
			ps.setInt(6, wardRoom.getPrice());
			ps.setInt(7, wardRoom.getStory());
			ps.setLong(8, wardRoom.getRoomID());
			int result = ps.executeUpdate();
			ps.close();
			return result;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Removes a room from a ward. Returns whether or not any changes were made
	 * 
	 * @param id
	 *            The RoomId of the Room to remove.
	 * @return A boolean indicating success.
	 * @throws DBException
	 */
	public boolean removeWardRoom(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM WardRooms WHERE RoomID = ?");
			ps.setLong(1, id);
			boolean check = (1 == ps.executeUpdate());
			ps.close();
			return check;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a list of all wards assigned to a HCP sorted alphabetically
	 * 
	 * @param id
	 *            The id of the HCP to get wards for
	 * @return A java.util.List of all WardBeans.
	 * @throws DBException
	 */
	public List<WardBean> getAllWardsByHCP(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"SELECT * FROM HCPAssignedToWard haw INNER JOIN Wards w WHERE HCP = ? AND haw.ward = w.wardid ORDER BY RequiredSpecialty");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();

			List<WardBean> loadlist = wardLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a list of all HCPs assigned to a ward sorted alphabetically
	 * 
	 * @param id
	 *            The id of the ward to get HCPs for
	 * @return A java.util.List of PersonnelBean that represent the HCPs
	 *         assigned to a ward.
	 * @throws DBException
	 */
	public List<PersonnelBean> getAllHCPsAssignedToWard(long id) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"SELECT * FROM HCPAssignedToWard haw INNER JOIN Personnel p WHERE haw.HCP = p.MID AND WARD = ? ORDER BY lastName");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			List<PersonnelBean> loadlist = personnelLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Assigns an HCP to a the specified ward.
	 * 
	 * @param hcpID
	 *            The id of the HCP to assign
	 * @param wardID
	 *            The ward to assign the HCP to.
	 * @return A boolean whether or not the insert worked correctly.
	 * @throws ITrustException
	 */
	public boolean assignHCPToWard(long hcpID, long wardID) throws ITrustException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO HCPAssignedToWard (HCP, WARD) Values(?,?)");
			ps.setLong(1, hcpID);
			ps.setLong(2, wardID);

			boolean check = (1 == ps.executeUpdate());
			ps.close();
			return check;
		} catch (SQLException e) {

			if (1062 == e.getErrorCode())
				throw new ITrustException("Error: HCP or WARD ID don't exist!");
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Removes a HCP and Ward association
	 * 
	 * @param wardID
	 *            The Ward ID of the Ward associated to the HCP.
	 * @param hcpID
	 *            The HCP ID of the HCP associated with the Ward.
	 * @return A boolean indicating success.
	 * @throws DBException
	 */
	public boolean removeWard(long hcpID, long wardID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM HCPAssignedToWard WHERE Ward = ? and hcp = ?");
			ps.setLong(1, wardID);
			ps.setLong(2, hcpID);
			boolean check = (1 == ps.executeUpdate());
			ps.close();
			return check;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Updates a particular wardroom's occupiedBy information. Returns the
	 * number of rows affected (should be 1)
	 * 
	 * @param wardRoom
	 *            The WardRoomBean to update.
	 * @return An int indicating the number of affected rows.
	 * @throws DBException
	 */
	public int updateWardRoomOccupant(WardRoomBean wardRoom) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE wardRooms SET OccupiedBy=? " + "WHERE RoomID = ?");
			if (wardRoom.getOccupiedBy() == null) {
				ps.setNull(1, java.sql.Types.BIGINT);
			} else {
				ps.setLong(1, wardRoom.getOccupiedBy());
			}
			ps.setLong(2, wardRoom.getRoomID());
			int result = ps.executeUpdate();
			ps.close();
			return result;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Update wardroom to waiting
	 * 
	 * @param wardRoom
	 * @return result
	 * @throws DBException
	 */
	public int updateWardRoomWaiting(WardRoomBean wardRoom) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE wardRooms SET Waiting=? " + "WHERE RoomID = ?");
			if (wardRoom.getWaiting() == null) {
				ps.setNull(1, java.sql.Types.BIGINT);
			} else {
				ps.setLong(1, wardRoom.getWaiting());
			}
			ps.setLong(2, wardRoom.getRoomID());
			int result = ps.executeUpdate();
			ps.close();
			return result;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Update ward room state
	 * 
	 * @param wardRoom
	 * @return result
	 * @throws DBException
	 */
	public int updateWardRoomState(WardRoomBean wardRoom) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE wardRooms SET State=? " + "WHERE RoomID = ?");
			ps.setBoolean(1, wardRoom.getState());
			ps.setLong(2, wardRoom.getRoomID());
			int result = ps.executeUpdate();
			ps.close();
			return result;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a list of all wards with the status specified that the hcp has
	 * access to
	 * 
	 * @param status
	 *            The Status to search on
	 * @param hcpID
	 *            The id of the HCP to get wards for
	 * @return A java.util.List of WardRoomBeans that the specified hcp has
	 *         access too.
	 * @throws DBException
	 */
	public List<WardRoomBean> getWardRoomsByState(Boolean State, Long hcpID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"SELECT * FROM wardrooms wr inner join hcpassignedtoward hw where wr.state = ? and wr.inward = hw.ward and hw.hcp = ?");
			ps.setBoolean(1, State);
			ps.setLong(2, hcpID);
			ResultSet rs = ps.executeQuery();
			List<WardRoomBean> loadlist = wardRoomLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a list of all wards with the status specified that the hcp has
	 * access to
	 * 
	 * @param status
	 *            The Status to search on
	 * @param hcpID
	 *            The id of the HCP to get wards for
	 * @return A java.util.List of WardRoomBeans that the specified hcp has
	 *         access too.
	 * @throws DBException
	 */
	public List<WardRoomBean> getWardRoomsByStatus(String status, Long hcpID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"SELECT * FROM wardrooms wr inner join hcpassignedtoward hw where wr.status = ? and wr.inward = hw.ward and hw.hcp = ?");
			ps.setString(1, status);
			ps.setLong(2, hcpID);
			ResultSet rs = ps.executeQuery();
			List<WardRoomBean> loadlist = wardRoomLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a WardRoom specified by the id
	 * 
	 * @param wardRoomID
	 *            The id of the ward room to get
	 * @return A WardRoomBean with the ID specified
	 * @throws DBException
	 */
	public WardRoomBean getWardRoom(String wardRoomID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM wardrooms where RoomID = ?");
			ps.setString(1, wardRoomID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				WardRoomBean loaded = wardRoomLoader.loadSingle(rs);
				rs.close();
				ps.close();
				return loaded;
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
	 * Get ward room by waiting specified
	 * 
	 * @param waitingID
	 * @return WardRoomBean
	 * @throws DBException
	 */
	public WardRoomBean getWardRoomByWaiting(long waitingID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM wardrooms where Waiting = ?");
			ps.setLong(1, waitingID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				WardRoomBean loaded = wardRoomLoader.loadSingle(rs);
				rs.close();
				ps.close();
				return loaded;
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
	 * Returns a Ward specified by the id
	 * 
	 * @param wardID
	 *            The id of the ward to get
	 * @return A WardBean with the ID specified
	 * @throws DBException
	 */
	public WardBean getWard(String wardID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM wards where wardid = ?");
			ps.setString(1, wardID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				WardBean loaded = wardLoader.loadSingle(rs);
				rs.close();
				ps.close();
				return loaded;
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
	 * Returns the hospital that the specified ward room is located in
	 * 
	 * @param wardRoomID
	 *            The id of the ward room to get the hospital for
	 * @return The HospitalBean that the specified ward room is located in.
	 * @throws DBException
	 */
	public HospitalBean getHospitalByWard(String wardRoomID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"SELECT * FROM hospitals h inner join wards ward inner join wardrooms room where room.RoomID = ? and room.inward = ward.wardid and ward.inhospital = h.hospitalid");
			ps.setString(1, wardRoomID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				HospitalBean loaded = hospitalLoader.loadSingle(rs);
				rs.close();
				ps.close();
				return loaded;
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
	 * Logs the checkout reason for a patient
	 * 
	 * @param mid
	 *            The mid of the Patient checking out
	 * @param reason
	 *            The reason the patient is being checked out.
	 * @return Whether 1 patient was inserted
	 * @throws ITrustException
	 */
	public boolean checkOutPatientReason(long mid, String reason) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO WardRoomCheckout (PID, Reason) Values(?,?)");
			ps.setLong(1, mid);
			ps.setString(2, reason);
			boolean check = (1 == ps.executeUpdate());
			ps.close();
			return check;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns the hospital that the specified user is located in
	 * 
	 * @param pid
	 *            The id of the user to get the hospital for
	 * @return The HospitalBean that the specified patient is located in.
	 * @throws DBException
	 */
	public HospitalBean getHospitalByPatientID(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"SELECT * FROM hospitals h inner join wards ward inner join wardrooms room where room.OccupiedBy = ? and room.inward = ward.wardid and ward.inhospital = h.hospitalid");
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				HospitalBean loaded = hospitalLoader.loadSingle(rs);
				rs.close();
				ps.close();
				return loaded;
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
	 * Returns a ward where a patient is in.
	 * 
	 * @param pid
	 *            which is patient's id.
	 * @return String Specialty
	 * @throws DBException
	 */
	public WardBean getWardByPid(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM wardrooms r where r.occupiedby = ?");
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				WardRoomBean wrb = wardRoomLoader.loadSingle(rs);
				rs.close();
				ps.close();
				return getWard(Long.toString(wrb.getInWard()));
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
	 * Returns a wardroom where a patient is in.
	 * 
	 * @param pid
	 *            which is patient's id.
	 * @return String Specialty
	 * @throws DBException
	 */
	public WardRoomBean getWardRoomByPid(long pid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM wardrooms r WHERE r.occupiedby = ?");
			ps.setLong(1, pid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				WardRoomBean wrb = wardRoomLoader.loadSingle(rs);
				rs.close();
				ps.close();
				return wrb;
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
	 * Returns a list of all wards
	 * 
	 * @param none
	 * @return A java.util.List of WardBeans.
	 * @throws DBException
	 */
	public List<WardBean> getAllWards() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM WARDS ORDER BY RequiredSpecialty");
			ResultSet rs = ps.executeQuery();
			List<WardBean> loadlist = wardLoader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Returns a list of all wardrooms by patient's specialty. If the wardroom
	 * the patient stays doesn't have a specialty, return all wardrooms.
	 * 
	 * @param pid
	 *            which is patient's id.
	 * @return A java.util.List of all WardRoomBeans corresponding some
	 *         restrictions.
	 * @throws DBException
	 */
	public List<WardRoomBean> getAllWardRoomsBySpecialty(long pid) throws DBException {
		HospitalBean hb = getHospitalByPatientID(pid);
		WardBean ward = getWardByPid(pid);
		WardRoomBean cur = getWardRoomByPid(pid);
		List<WardRoomBean> rooms = new ArrayList<WardRoomBean>();
		List<WardBean> lwb = null;

		if (hb == null) {
			lwb = getAllWards();
			for (WardBean w : lwb) {
				List<WardRoomBean> ww = getAllWardRoomsByWardID(w.getWardID());
				rooms.addAll(ww);
			}
			return rooms;
		}

		lwb = getAllWardsByHospitalID(hb.getHospitalID());
		if (ward.getRequiredSpecialty() == null || ward.getRequiredSpecialty().equals("")) {
			for (WardBean w : lwb) {
				List<WardRoomBean> ww = getAllWardRoomsByWardID(w.getWardID());
				rooms.addAll(ww);
			}
		} else {
			for (WardBean w : lwb) {
				if (w.getRequiredSpecialty().equals(ward.getRequiredSpecialty())) {
					List<WardRoomBean> ww = getAllWardRoomsByWardID(w.getWardID());
					rooms.addAll(ww);
				}
			}
		}

		List<WardRoomBean> toReturn = new ArrayList<WardRoomBean>();
		for (WardRoomBean w : rooms) {
			if (!cur.equals(w)) {
				toReturn.add(w);
			}
		}
		if (toReturn.isEmpty()) {
			toReturn = null;
		}

		return toReturn;
	}

	/**
	 * Returns a list of all wardrooms under the price range.
	 * 
	 * @param id
	 *            The id of the ward to get all rooms for
	 * @return A java.util.List of all WardRoomBeans in a ward.
	 * @throws DBException
	 */
	public List<WardRoomBean> getAllWardRoomsBySpecialtyByPrice(long pid, int l_price, int u_price) throws DBException {
		List<WardRoomBean> srooms = getAllWardRoomsBySpecialty(pid);
		List<WardRoomBean> rooms = new ArrayList<WardRoomBean>();

		for (WardRoomBean w : srooms) {
			if (l_price < w.getPrice() && w.getPrice() <= u_price) {
				rooms.add(w);
			}
		}
		if (rooms.isEmpty()) {
			rooms = null;
		}
		return rooms;
	}

	/**
	 * Returns a list of all wardrooms under the room story.
	 * 
	 * @param pid,
	 *            room story
	 * @return A java.util.List of all WardRoomBeans in a ward.
	 * @throws DBException
	 */
	public List<WardRoomBean> getAllWardRoomsBySpecialtyByStory(long pid, int story) throws DBException {
		List<WardRoomBean> srooms = getAllWardRoomsBySpecialty(pid);
		List<WardRoomBean> rooms = new ArrayList<WardRoomBean>();

		for (WardRoomBean w : srooms) {
			if (w.getStory() == story) {
				rooms.add(w);
			}
		}
		if (rooms.isEmpty()) {
			rooms = null;
		}
		return rooms;
	}

	/**
	 * Returns a list of all wardrooms under the room story and price
	 * 
	 * @param pid,
	 *            room story, price
	 * @return A java.util.List of all WardRoomBeans in a ward.
	 * @throws DBException
	 */
	public List<WardRoomBean> getAllWardRoomsBySpecialtyByStoryByPrice(long pid, int story, int l_price, int u_price)
			throws DBException {
		List<WardRoomBean> srooms = getAllWardRoomsBySpecialty(pid);
		List<WardRoomBean> rooms = new ArrayList<WardRoomBean>();

		for (WardRoomBean w : srooms) {
			if (w.getStory() == story && l_price < w.getPrice() && w.getPrice() <= u_price) {
				rooms.add(w);
			}
		}
		if (rooms.isEmpty()) {
			rooms = null;
		}
		return rooms;
	}

	/**
	 * Returns a list of all wardrooms that system recommends, which means the
	 * system recommends for a patient with affordable ward rooms.
	 * 
	 * @param pid,
	 *            price
	 * @return A java.util.List of all WardRoomBeans in a ward.
	 * @throws DBException
	 */
	public List<WardRoomBean> getAllWardRoomsBySystemRecommanded(long pid, int price) throws DBException {
		List<WardRoomBean> srooms = getAllWardRoomsBySpecialty(pid);
		List<WardRoomBean> rooms = new ArrayList<WardRoomBean>();

		for (WardRoomBean w : srooms) {
			if (price - 15 < w.getPrice() && w.getPrice() <= price + 15) {
				rooms.add(w);
			}
		}
		if (rooms.isEmpty()) {
			rooms = null;
		}
		return rooms;
	}

	/**
	 * Returns specialty of the ward specified by wardid.
	 * 
	 * @param ward
	 *            id
	 * @return the specialty
	 * @throws DBException
	 */
	public String getSpecialtyOfWard(String wardID) throws DBException {
		WardBean w = getWard(wardID);
		return w.getRequiredSpecialty();
	}
}

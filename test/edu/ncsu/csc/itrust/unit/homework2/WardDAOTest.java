package edu.ncsu.csc.itrust.unit.homework2;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.WardBean;
import edu.ncsu.csc.itrust.beans.WardRoomBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.WardDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class WardDAOTest {

	WardDAO wardDao;
	DAOFactory factory;
	final int HOSPITAL_ID = 10000;

	@Before
	public void setUp() {
		factory = TestDAOFactory.getTestInstance();
		wardDao = new WardDAO(factory);
	}

	@After
	public void tearDown() {
		wardDao = null;
		factory = null;
	}
	
	private void removeTestHospital() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM hospitals where HospitalID=?");
			ps.setLong(1, HOSPITAL_ID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Error("Removing hospital failed");
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	@Test
	public void removeWardAffectsZeroRow() {
		try {
			wardDao.removeWard(10000);
		} catch (DBException e) {
			fail("expected: 0 row affected. result: SQLException");
		}
	}

	@Test
	public void getWardRoomGotResult() {
		WardRoomBean stubWardRoom = new WardRoomBean(100, 100, 100, "Stub", "Stub",true, 0);
		WardRoomBean resultWardRoom = null;
		try {
			// this does not add a wardroom with Room ID 100, the room id of
			// wardroombean
			// is not applied.
			wardDao.addWardRoom(stubWardRoom);
			resultWardRoom = wardDao.getWardRoom("1");
		} catch (ITrustException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertTrue(resultWardRoom != null);
	}

	@Test
	public void getWardGotResult() {
		WardBean resultWard = prepareAndGetStubWardFromDB(1);

		assertTrue(resultWard != null);
	}

	@Test
	public void getWardGetZeroResults() {
		WardBean resultWard = null;

		try {
			resultWard = wardDao.getWard("10000000");
		} catch (ITrustException e) {
			e.printStackTrace();
			fail("sqlerror");
		}

		assertTrue(resultWard == null);
	}

	@Test
	public void getHospitalByWardGetResult() {
		try {
			WardRoomBean stubWardRoom = wardDao.getWardRoom("1");
			if (stubWardRoom == null) {
				prepareStubWardRoomInDBWithRoomIdAndInWard(1, HOSPITAL_ID);
			}
			prepareStubWardInDBWithInHospital(1);
			prepareStubHospitalInDBWithHospitalID(HOSPITAL_ID);
			assertTrue(wardDao.getHospitalByWard("1") != null);
			removeTestHospital();
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
	}
	
	@Test(expected=DBException.class)
	public void checkOutPatientReasonInvalidSql() throws DBException{
		wardDao.checkOutPatientReason(-1, "~~~");
	}

	private WardBean prepareAndGetStubWardFromDB(int inHospital) {
		WardBean resultWard = null;
		prepareStubWardInDBWithInHospital(inHospital);
		try {
			resultWard = wardDao.getWard("1");
		} catch (ITrustException e) {
			e.printStackTrace();
			fail("getting stub ward failed");
		}
		return resultWard;
	}

	private void prepareStubWardInDBWithInHospital(int inHospital) {
		WardBean stubWard = new WardBean(10000, "Stub", inHospital);
		try {
			// wardID is not 100, ignored. any value higher than 0
			wardDao.addWard(stubWard);
		} catch (ITrustException e) {
			e.printStackTrace();
			fail("inserting stub ward failed");
		}
	}

	private void prepareStubWardRoomInDBWithRoomIdAndInWard(int roomID, int inWard) {
		WardRoomBean stubWardRoom = new WardRoomBean(roomID, 1000, inWard, "Stub", "Stub",true, 0);
		try {
			wardDao.addWardRoom(stubWardRoom);
		} catch (ITrustException e) {
			e.printStackTrace();
			fail("inserting stub wardroom failed");
		}
	}

	private void prepareStubHospitalInDBWithHospitalID(int hospitalID) {
		HospitalsDAO hospitalDao = factory.getHospitalsDAO();
		HospitalBean hospitalBean = new HospitalBean(String.valueOf(hospitalID));
		try {
			hospitalDao.addHospital(hospitalBean);
		} catch (ITrustException e) {
			e.printStackTrace();
			fail("inserting stub hospital failed");
		}
	}
}

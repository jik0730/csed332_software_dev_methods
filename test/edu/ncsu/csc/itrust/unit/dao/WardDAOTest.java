package edu.ncsu.csc.itrust.unit.dao;

import static org.easymock.classextension.EasyMock.createControl;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.WardDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.WardBean;
import edu.ncsu.csc.itrust.beans.WardRoomBean;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import java.util.List;

import org.easymock.classextension.IMocksControl;

/**
 * WardDAOTest
 */
@SuppressWarnings("unused")
public class WardDAOTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory factory2;

	WardDAO wd1;
	WardDAO wd2;

	private IMocksControl ctrl;

	/**
	 * setUp
	 */
	public void setUp() {
		ctrl = createControl();
		factory2 = ctrl.createMock(DAOFactory.class);

		wd1 = new WardDAO(factory);

		wd2 = new WardDAO(factory2);
	}

	/**
	 * testgetAllWardsByHospitalID
	 */
	public void testgetAllWardsByHospitalID() {
		List<WardBean> list = new ArrayList<WardBean>();

		try {
			list = wd1.getAllWardsByHospitalID("1");
			assertNotNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getAllWardsByHospitalID("1");
			fail();
		} catch (Exception e) {
			// TODO
		}

	}

	/**
	 * testAddWard
	 */
	public void testAddWard() {
		WardBean wb = new WardBean(0L, "name", 0L);

		try {
			assertTrue(wd1.addWard(wb));
		} catch (DBException e) {
			// TODO
		} catch (ITrustException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.addWard(wb);
			fail();
		} catch (Exception e) {
			// TODO
		}

	}

	/**
	 * testUpdateWard
	 */
	public void testUpdateWard() {
		WardBean wb = new WardBean(0L, "name", 0L);

		try {
			assertEquals(0, wd1.updateWard(wb));
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.updateWard(wb);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testRemoveWard
	 */
	public void testRemoveWard() {

		try {
			wd1.removeWard(5L);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.removeWard(1L);
			fail();
		} catch (Exception e) {
			// TODO
		}

	}

	/**
	 * testgetAllWardRoomsByWardID
	 */
	public void testgetAllWardRoomsByWardID() {
		List<WardRoomBean> list = new ArrayList<WardRoomBean>();

		try {
			list = wd1.getAllWardRoomsByWardID(1L);
			assertNotNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getAllWardRoomsByWardID(1L);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testAddWardRoom
	 */
	public void testAddWardRoom() {
		WardRoomBean wb = new WardRoomBean(0, 0, 0, "name1", "status", true, 0, 50, 4);

		try {
			assertTrue(wd1.addWardRoom(wb));
		} catch (DBException e) {
			// TODO
		} catch (ITrustException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.addWardRoom(wb);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testUpdateWardRoom
	 */
	public void testUpdateWardRoom() {
		WardRoomBean wb = new WardRoomBean(0, 0, 0, "name1", "status", true, 0, 50, 4);
		try {
			assertEquals(0, wd1.updateWardRoom(wb));
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.updateWardRoom(wb);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testRemoveWardRoom
	 */
	public void testRemoveWardRoom() {
		try {
			wd1.removeWardRoom(8L);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.removeWardRoom(1L);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testgetAllWardsByHCP
	 */
	public void testgetAllWardsByHCP() {
		List<WardBean> list = new ArrayList<WardBean>();

		try {
			list = wd1.getAllWardsByHCP(1L);
			assertNotNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getAllWardsByHCP(1L);
			fail();
		} catch (Exception e) {
			// TODO
		}

	}

	/**
	 * testgetAllHCPsAssignedToWard
	 */
	public void testgetAllHCPsAssignedToWard() {
		List<PersonnelBean> list = new ArrayList<PersonnelBean>();

		try {
			list = wd1.getAllHCPsAssignedToWard(1L);
			assertNotNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getAllHCPsAssignedToWard(1L);
			fail();
		} catch (Exception e) {
			// TODO
		}

	}

	/**
	 * testAssignHCPToWard
	 */
	public void testAssignHCPToWard() {
		try {
			assertTrue(wd1.assignHCPToWard(1L, 1L));
		} catch (DBException e) {
			// TODO
		} catch (ITrustException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.assignHCPToWard(1L, 1L);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testRemoveWard2
	 */
	public void testRemoveWard2() {
		try {
			assertNotNull(wd1.removeWard(1L, 1L));
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.removeWard(1L, 1L);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testUpdateWardRoomOccupant
	 */
	public void testUpdateWardRoomOccupant() {

		WardRoomBean wrb = new WardRoomBean(0, 0, 0, "name", "status", true, 0, 50, 4);

		try {
			assertEquals(0, wd1.updateWardRoomOccupant(wrb));
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.updateWardRoomOccupant(wrb);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testGetWardRoomsByStatus
	 */
	public void testGetWardRoomsByStatus() {

		List<WardRoomBean> list = new ArrayList<WardRoomBean>();

		try {
			list = wd1.getWardRoomsByStatus("status", 1L);
			assertNotNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getWardRoomsByStatus("status", 1L);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testGetWardRoom
	 */
	public void testGetWardRoom() {
		WardRoomBean wrb = new WardRoomBean(0, 0, 0, "name", "status", true, 0, 50, 4);

		try {
			wrb = wd1.getWardRoom("0");
			assertNull(wrb);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getWardRoom("0");
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testGetHospitalByWard
	 */
	public void testGetHospitalByWard() {
		HospitalBean hb = new HospitalBean();

		try {
			hb = wd1.getHospitalByWard("name");
			assertNull(hb);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getHospitalByWard("name");
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testgetAllWards
	 */
	public void testgetAllWards() {
		List<WardBean> list = new ArrayList<WardBean>();

		try {
			list = wd1.getAllWards();
			assertNotNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getAllWards();
			fail();
		} catch (Exception e) {
			// TODO
		}

	}

	/**
	 * testgetAllWardRoomsBySpecialty
	 */
	public void testgetAllWardRoomsBySpecialty() {
		List<WardRoomBean> list = new ArrayList<WardRoomBean>();

		try {
			list = wd1.getAllWardRoomsBySpecialty(2L);
			assertEquals(7, list.size());
		} catch (DBException e) {
			// TODO
		}

		try {
			list = wd1.getAllWardRoomsBySpecialty(1L);
			assertEquals(3, list.size());
		} catch (DBException e) {
			// TODO
		}

		try {
			list = wd1.getAllWardRoomsBySpecialty(11L);
			assertNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getAllWardRoomsBySpecialty(1L);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testgetAllWardRoomsBySpecialtyByPrice
	 */
	public void testgetAllWardRoomsBySpecialtyByPrice() {
		List<WardRoomBean> list = new ArrayList<WardRoomBean>();

		try {
			list = wd1.getAllWardRoomsBySpecialtyByPrice(1L, 25, 50);
			assertEquals(2, list.size());
		} catch (DBException e) {
			// TODO
		}

		try {
			list = wd1.getAllWardRoomsBySpecialtyByPrice(1L, 0, 25);
			assertNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getAllWardRoomsBySpecialtyByPrice(1L, 25, 50);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testgetAllWardRoomsBySpecialtyByStory
	 */
	public void testgetAllWardRoomsBySpecialtyByStory() {
		List<WardRoomBean> list = new ArrayList<WardRoomBean>();

		try {
			list = wd1.getAllWardRoomsBySpecialtyByStory(1L, 3);
			assertEquals(2, list.size());
		} catch (DBException e) {
			// TODO
		}

		try {
			list = wd1.getAllWardRoomsBySpecialtyByStory(1L, 5);
			assertNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getAllWardRoomsBySpecialtyByStory(1L, 1);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testgetAllWardRoomsBySpecialtyByStoryByPrice
	 */
	public void testgetAllWardRoomsBySpecialtyByStoryByPrice() {
		List<WardRoomBean> list = new ArrayList<WardRoomBean>();

		try {
			list = wd1.getAllWardRoomsBySpecialtyByStoryByPrice(1L, 3, 25, 50);
			assertEquals(2, list.size());
		} catch (DBException e) {
			// TODO
		}

		try {
			list = wd1.getAllWardRoomsBySpecialtyByStoryByPrice(1L, 1, 0, 25);
			assertNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getAllWardRoomsBySpecialtyByStoryByPrice(1L, 3, 25, 50);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testgetAllWardRoomsBySystemRecommanded
	 */
	public void testgetAllWardRoomsBySystemRecommanded() {
		List<WardRoomBean> list = new ArrayList<WardRoomBean>();

		try {
			list = wd1.getAllWardRoomsBySystemRecommanded(1L, 50);
			assertEquals(2, list.size());
		} catch (DBException e) {
			// TODO
		}

		try {
			list = wd1.getAllWardRoomsBySystemRecommanded(1L, 0);
			assertNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getAllWardRoomsBySystemRecommanded(1L, 50);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testgetSpecialtyOfWard
	 */
	public void testgetSpecialtyOfWard() {
		String s;
		try {
			s = wd1.getSpecialtyOfWard("2");
			assertEquals(s, "Elderly");
		} catch (DBException e) {
			// TODO
		}
	}

	/**
	 * testUpdateWaitingWardRoom
	 */
	public void testUpdateWaitingWardRoom() {
		WardRoomBean wb = new WardRoomBean(0, 0, 0, "name1", "status", true, 0, 50, 4);
		try {
			assertEquals(0, wd1.updateWardRoomWaiting(wb));
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.updateWardRoomWaiting(wb);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testgetAllWardRoomsByWaiting
	 */
	public void testgetAllWardRoomsByWaiting() {
		List<WardRoomBean> list = new ArrayList<WardRoomBean>();

		try {
			list = wd1.getAllWardRoomsByWaiting(1L);
			assertNotNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getAllWardRoomsByWaiting(1L);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testgetAllWardRoomsByWaiting
	 */
	public void testgetWardRoomsByWaiting() {
		WardRoomBean list = null;

		try {
			list = wd1.getWardRoomByWaiting(1L);
			assertNotNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getWardRoomByWaiting(1L);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}

	/**
	 * testgetAllWardRoomsByState
	 */
	public void testgetWardRoomsByState() {
		List<WardRoomBean> list = new ArrayList<WardRoomBean>();

		try {
			list = wd1.getWardRoomsByState(false, 1L);
			assertNotNull(list);
		} catch (DBException e) {
			// TODO
		}

		try {
			expect(factory2.getConnection()).andThrow(new SQLException());
			ctrl.replay();
			wd2.getWardRoomsByState(false, 1L);
			fail();
		} catch (Exception e) {
			// TODO
		}
	}
	
	/**
	 * testwardRoomBean
	 */
	public void testwardRoomBean() {
		WardRoomBean wrb = new WardRoomBean(1L, 1L, 1L, "test", "test", true, 1L, 1, 1);
		wrb.setInWard(1L);
		wrb.setOccupiedBy(1L);
		wrb.setPrice(1);
		wrb.setRoomID(1L);
		wrb.setRoomName("1");
		wrb.setStatus("1");
		wrb.setStory(1);
		wrb.setWaiting(1L);
		wrb.setPending(true);
		wrb.setAccepted(true);
		return ;
	}
}

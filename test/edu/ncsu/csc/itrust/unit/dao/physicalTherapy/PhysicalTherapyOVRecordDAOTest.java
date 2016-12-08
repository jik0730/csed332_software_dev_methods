package edu.ncsu.csc.itrust.unit.dao.physicalTherapy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PhysicalTherapyOVRecordDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class PhysicalTherapyOVRecordDAOTest {
	DAOFactory factory = TestDAOFactory.getTestInstance();
	PhysicalTherapyOVRecordDAO dao = new PhysicalTherapyOVRecordDAO(factory);
	PhysicalTherapyOVRecordBean bean;
	
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		
		//Make the test bean
		bean = new PhysicalTherapyOVRecordBean();
		bean.setMid(401L);
		bean.setOid(2L);
		bean.setFirstName("Kelly");
		bean.setLastName("Doctor");
		bean.setVisitDate("12/12/2015");
		String survey = "100,0,0,0,0,0,0,0,0,0";
		bean.setWellnessSurveyResults(survey);
		bean.setWellnessSurveyScore(10L);
		String exercise = "false,true,false,false,true,false,false,true,false,true";
		bean.setExercise(exercise);
	}
	
	@Test
	public void testAddEditRecord() throws DBException{
		dao.addPhysicalTherapyOVRecord(bean);
		assertNull(dao.getPhysicalTherapyOVRecord(-1));
		assertEquals(dao.getPhysicalTherapyOVRecord(1), bean);
		List<PhysicalTherapyOVRecordBean> beans = dao.getPhysicalTherapyOVRecordsByMID(401);
		assertEquals(beans.get(0), bean);
		
		//edit the bean
		bean.setFirstName("Povis");
		dao.editPhysicalTherapyOVRecordsRecord(1, bean);
		assertEquals(dao.getPhysicalTherapyOVRecord(1), bean);
		beans = dao.getPhysicalTherapyOVRecordsByMID(401);
		assertEquals(beans.get(0), bean);
	}
	
	@Test
	public void testErrors(){
		DBBuilder builder = new DBBuilder();

		//get a single record
		try {
			builder.dropTables(); //drop all tables in the DB
			PhysicalTherapyOVRecordBean bean = null;
			try {
				bean = dao.getPhysicalTherapyOVRecord(1);
			} catch (DBException e) {
				assertNull(bean);
			}

			builder.createTables(); //now put them back so future tests aren't broken
		} catch(Exception e) {
			fail();
		}
		
		//get all physicalTherapy records
		try {
			builder.dropTables(); //drop all tables in the DB
			List<PhysicalTherapyOVRecordBean> beans = null;
			try {
				beans = dao.getPhysicalTherapyOVRecordsByMID(1);
			} catch (DBException e) {
				assertNull(beans);
			}

			builder.createTables(); //now put them back so future tests aren't broken
		} catch(Exception e) {
			fail();
		}
		
		//get all physicalTherapy records
		try {
			builder.dropTables(); //drop all tables in the DB
			List<PhysicalTherapyOVRecordBean> beans = null;
			try {
				dao.editPhysicalTherapyOVRecordsRecord(1, bean);
			} catch (DBException e) {
				assertNull(null);
			}

			builder.createTables(); //now put them back so future tests aren't broken
		} catch(Exception e) {
			fail();
		}
		
		//get all physicalTherapy records
		try {
			builder.dropTables(); //drop all tables in the DB
			List<PhysicalTherapyOVRecordBean> beans = null;
			try {
				dao.addPhysicalTherapyOVRecord(bean);
			} catch (DBException e) {
				assertNull(null);
			}

			builder.createTables(); //now put them back so future tests aren't broken
		} catch(Exception e) {
			fail();
		}
	}
}

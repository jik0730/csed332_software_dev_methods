package edu.ncsu.csc.itrust.unit.dao.orthopedicSurgery;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.beans.OrthopedicSurgeryRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicSurgeryRecordDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class OrthopedicSurgeryRecordDAOTest {
	DAOFactory factory = TestDAOFactory.getTestInstance();
	OrthopedicSurgeryRecordDAO dao = new OrthopedicSurgeryRecordDAO(factory);
	OrthopedicSurgeryRecordBean bean;
	
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		
		//Make the test bean
		bean = new OrthopedicSurgeryRecordBean();
		bean.setMid(401L);
		bean.setOid(1L);
		bean.setLastName("Lamar");
		bean.setFirstName("Bridges");
		bean.setVisitDate("01/22/2015");
		bean.setSurgery("Total knee replacement");
		bean.setSurgeryNotes("Surgery completed with no issues.");
	}
	
	@Test
	public void testAddEditRecord() throws DBException{
		dao.addOrthopedicSurgeryRecord(bean);
		assertEquals(dao.getOrthopedicSurgeryRecord(1), bean);
		List<OrthopedicSurgeryRecordBean> beans = dao.getOrthopedicSurgeryRecordsByMID(401);
		assertEquals(beans.get(0), bean);
		
		//edit the bean
		dao.editOrthopedicSurgeryRecordsRecord(1, bean);
		assertEquals(dao.getOrthopedicSurgeryRecord(1), bean);
		beans = dao.getOrthopedicSurgeryRecordsByMID(401);
		assertEquals(beans.get(0), bean);
	}
	
	@Test
	public void testErrors(){
		DBBuilder builder = new DBBuilder();

		//get a single record
		try {
			builder.dropTables(); //drop all tables in the DB
			OrthopedicSurgeryRecordBean bean = null;
			try {
				bean = dao.getOrthopedicSurgeryRecord(1);
			} catch (DBException e) {
				assertNull(bean);
			}

			builder.createTables(); //now put them back so future tests aren't broken
		} catch(Exception e) {
			fail();
		}
		
		//get all obstetrics records
		try {
			builder.dropTables(); //drop all tables in the DB
			List<OrthopedicSurgeryRecordBean> beans = null;
			try {
				beans = dao.getOrthopedicSurgeryRecordsByMID(1);
			} catch (DBException e) {
				assertNull(beans);
			}

			builder.createTables(); //now put them back so future tests aren't broken
		} catch(Exception e) {
			fail();
		}
	}
}

package edu.ncsu.csc.itrust.unit.dao.orthopedic;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;
import edu.ncsu.csc.itrust.beans.loaders.OrthopedicOVRecordLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicOVRecordDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class OrthopedicOVRecordDAOTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private OrthopedicOVRecordDAO dao = new OrthopedicOVRecordDAO(factory);
	private OrthopedicOVRecordBean bean;
	
	private final byte[] TEST_XRAY = {
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10	
	};
	private final byte[] TEST_MRI = {
		11, 12, 13, 14, 15, 16, 17, 18, 19, 20	
	};
	
	@Before
	public void setUp() throws IOException, SQLException {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		
		bean = new OrthopedicOVRecordBean();
		bean.setPid(1L);
		bean.setHid(9000000000L);
		bean.setVisitDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
		bean.setXray(TEST_XRAY);
		bean.setMri(TEST_MRI);
		bean.setInjured("test");
		bean.setMriReport("Wow, Fabulous.");
	}
	
	@Test
	public void testAddEditRecord() throws DBException {
		dao.addOrthopedicOVRecord(bean);
		assertEquals(dao.getOrthopedicOVRecord(1), bean);
		List<OrthopedicOVRecordBean> beans = dao.getOrthopedicOVRecordsByMID(1L);
		assertEquals(beans.get(0), bean);
		
		//edit the bean
		bean.setMriReport("Wow, Awesome.");
		dao.editOrthopedicOVRecordsRecord(1, bean);
		assertEquals(dao.getOrthopedicOVRecord(1), bean);
		beans = dao.getOrthopedicOVRecordsByMID(1L);
		assertEquals(beans.get(0), bean);
	}
	
	@Test
	public void testErrors(){
		DBBuilder builder = new DBBuilder();

		//get a single record
		try {
			builder.dropTables(); //drop all tables in the DB
			OrthopedicOVRecordBean bean = null;
			try {
				bean = dao.getOrthopedicOVRecord(1);
			} catch (DBException e) {
				assertNull(bean);
			}

			builder.createTables(); //now put them back so future tests aren't broken
		} catch(Exception e) {
			fail();
		}
		
		//get all orthopedic records
		try {
			builder.dropTables(); //drop all tables in the DB
			List<OrthopedicOVRecordBean> beans = null;
			try {
				beans = dao.getOrthopedicOVRecordsByMID(1);
			} catch (DBException e) {
				assertNull(beans);
			}

			builder.createTables(); //now put them back so future tests aren't broken
		} catch(Exception e) {
			fail();
		}
	}
}

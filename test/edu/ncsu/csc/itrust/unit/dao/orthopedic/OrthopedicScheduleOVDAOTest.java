package edu.ncsu.csc.itrust.unit.dao.orthopedic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.beans.OrthopedicScheduleOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrthopedicScheduleOVDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class OrthopedicScheduleOVDAOTest {
   DAOFactory factory = TestDAOFactory.getTestInstance();
   OrthopedicScheduleOVDAO dao = new OrthopedicScheduleOVDAO(factory);
   OrthopedicScheduleOVRecordBean bean;
   
   @Before
   public void setUp() throws Exception {
      TestDataGenerator gen = new TestDataGenerator();
      gen.clearAllTables();
      
      //Make the test bean
      bean = new OrthopedicScheduleOVRecordBean();
      bean.setComment("Comment");
      bean.setDoctormid(101);
      bean.setPatientmid(102);
      bean.setPending(true);
      bean.setDocFirstName("Momsen");
      bean.setDocLastName("doctor");
      SimpleDateFormat frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      Date d;
      try {
         d = frmt.parse("20/20/1994 10:22 PM");
         bean.setDate(new Timestamp(d.getTime()));
      } catch (ParseException e1) {
         //Won't happen
      }
   }
   
   @Test
   public void testAddEditRecord() throws DBException{
      dao.addOrthopedicScheduleOVRecord(bean);
      assertEquals(dao.getOrthopedicScheduleOVRecord(1), bean);
      List<OrthopedicScheduleOVRecordBean> beans = dao.getOrthopedicScheduleOVRecordsByPATIENTMID(102L);
      assertEquals(beans.get(0), bean);
      
      beans = dao.getOrthopedicScheduleOVRecordsByDOCTORMID(101L);
      assertEquals(beans.get(0), bean);
      
      //edit the bean
      bean.setPending(false);
      bean.setAccepted(true);
      dao.editOrthopedicScheduledOVRecordsRecord(1, bean);
      assertEquals(dao.getOrthopedicScheduleOVRecord(1), bean);
      beans = dao.getOrthopedicScheduleOVRecordsByPATIENTMID(102L);
      assertEquals(beans.get(0), bean);
      
      beans = dao.getOrthopedicScheduleOVRecordsByDOCTORMID(101L);
      assertEquals(beans.get(0), bean);
   }
   
   @Test
   public void testErrors(){
      DBBuilder builder = new DBBuilder();

      //get a single record
      try {
         builder.dropTables(); //drop all tables in the DB
         OrthopedicScheduleOVRecordBean bean = null;
         try {
            bean = dao.getOrthopedicScheduleOVRecord(1);
         } catch (DBException e) {
            assertNull(bean);
         }

         builder.createTables(); //now put them back so future tests aren't broken
      } catch(Exception e) {
         fail();
      }
      
      //get all Orthopedic records by doctor
      try {
         builder.dropTables(); //drop all tables in the DB
         List<OrthopedicScheduleOVRecordBean> beans = null;
         try {
            beans = dao.getOrthopedicScheduleOVRecordsByDOCTORMID(1);
         } catch (DBException e) {
            assertNull(beans);
         }

         builder.createTables(); //now put them back so future tests aren't broken
      } catch(Exception e) {
         fail();
      }
      
      //get all Orthopedic records by patient
            try {
               builder.dropTables(); //drop all tables in the DB
               List<OrthopedicScheduleOVRecordBean> beans = null;
               try {
                  beans = dao.getOrthopedicScheduleOVRecordsByPATIENTMID(1);
               } catch (DBException e) {
                  assertNull(beans);
               }

               builder.createTables(); //now put them back so future tests aren't broken
            } catch(Exception e) {
               fail();
            }
   }
}
package edu.ncsu.csc.itrust.unit.dao.physicalTherapy;

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
import edu.ncsu.csc.itrust.beans.PhysicalTherapyScheduleOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PhysicalTherapyScheduleOVDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class PhysicalTherapyScheduleOVDAOTest {
   DAOFactory factory = TestDAOFactory.getTestInstance();
   PhysicalTherapyScheduleOVDAO dao = new PhysicalTherapyScheduleOVDAO(factory);
   PhysicalTherapyScheduleOVRecordBean bean;
   
   @Before
   public void setUp() throws Exception {
      TestDataGenerator gen = new TestDataGenerator();
      gen.clearAllTables();
      
      //Make the test bean
      bean = new PhysicalTherapyScheduleOVRecordBean();
      bean.setComment("Comment");
      bean.setDoctormid(101);
      bean.setPatientmid(102);
      bean.setPending(true);
      bean.setDocFirstName("Taylor");
      bean.setDocLastName("Physical Therapist");
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
      dao.addPhysicalTherapyScheduleOVRecord(bean);
      assertEquals(dao.getPhysicalTherapyScheduleOVRecord(1), bean);
      List<PhysicalTherapyScheduleOVRecordBean> beans = dao.getPhysicalTherapyScheduleOVRecordsByPATIENTMID(102L);
      assertEquals(beans.get(0), bean);
      
      beans = dao.getPhysicalTherapyScheduleOVRecordsByDOCTORMID(101L);
      assertEquals(beans.get(0), bean);
      
      //edit the bean
      bean.setPending(false);
      bean.setAccepted(true);
      dao.editPhysicalTherapyScheduledOVRecordsRecord(1, bean);
      assertEquals(dao.getPhysicalTherapyScheduleOVRecord(1), bean);
      beans = dao.getPhysicalTherapyScheduleOVRecordsByPATIENTMID(102L);
      assertEquals(beans.get(0), bean);
      
      beans = dao.getPhysicalTherapyScheduleOVRecordsByDOCTORMID(101L);
      assertEquals(beans.get(0), bean);
   }
   
   @Test
   public void testErrors(){
      DBBuilder builder = new DBBuilder();

      //get a single record
      try {
         builder.dropTables(); //drop all tables in the DB
         PhysicalTherapyScheduleOVRecordBean bean = null;
         try {
            bean = dao.getPhysicalTherapyScheduleOVRecord(1);
         } catch (DBException e) {
            assertNull(bean);
         }

         builder.createTables(); //now put them back so future tests aren't broken
      } catch(Exception e) {
         fail();
      }
      
      //get all PhysicalTherapy records by doctor
      try {
         builder.dropTables(); //drop all tables in the DB
         List<PhysicalTherapyScheduleOVRecordBean> beans = null;
         try {
            beans = dao.getPhysicalTherapyScheduleOVRecordsByDOCTORMID(1);
         } catch (DBException e) {
            assertNull(beans);
         }

         builder.createTables(); //now put them back so future tests aren't broken
      } catch(Exception e) {
         fail();
      }
      
      //get all PhysicalTherapy records by patient
            try {
               builder.dropTables(); //drop all tables in the DB
               List<PhysicalTherapyScheduleOVRecordBean> beans = null;
               try {
                  beans = dao.getPhysicalTherapyScheduleOVRecordsByPATIENTMID(1);
               } catch (DBException e) {
                  assertNull(beans);
               }

               builder.createTables(); //now put them back so future tests aren't broken
            } catch(Exception e) {
               fail();
            }
   }
}
package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyScheduleOVRecordBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.action.AddPhysicalTherapyScheduleOVAction;
import edu.ncsu.csc.itrust.action.ViewPhysicalTherapyScheduleOVAction;
import edu.ncsu.csc.itrust.action.EditPhysicalTherapyScheduleOVAction;

public class AddEditViewPhysicalTherapyScheduleOVActionTest {
   private final long LOGGED_IN_MID = 9210000000L;
   private final long PATIENT_MID = 407L;
   
   @Before
   public void setUp() throws Exception {
      TestDataGenerator gen = new TestDataGenerator();
      gen.clearAllTables();
      gen.standardData();
      gen.orderTest();
   }
   
   @Test
   public void testAddPhysicalTherapyOV() throws FormValidationException, ITrustException{
      DAOFactory prodDAO = TestDAOFactory.getTestInstance();
      
      AddPhysicalTherapyScheduleOVAction addAction = new AddPhysicalTherapyScheduleOVAction(prodDAO, LOGGED_IN_MID);
      
      PhysicalTherapyScheduleOVRecordBean bean1 = new PhysicalTherapyScheduleOVRecordBean();
      bean1.setComment("Comment");
      bean1.setDoctormid(9210000000L);
      bean1.setPatientmid(1);
      bean1.setPending(true);
      bean1.setDocFirstName("Taylor");
      bean1.setDocLastName("Physical Therapist");
      SimpleDateFormat frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      Date d;
      try {
         d = frmt.parse("20/20/1994 10:22 PM");
         bean1.setDate(new Timestamp(d.getTime()));
      } catch (ParseException e1) {
         //Won't happen
      }
      
      PhysicalTherapyScheduleOVRecordBean bean2 = new PhysicalTherapyScheduleOVRecordBean();
      bean2.setComment("Comment");
      bean2.setDoctormid(9210000000L);
      bean2.setPatientmid(1);
      bean2.setPending(true);
      bean2.setDocFirstName("Taylor");
      bean2.setDocLastName("Physical Therapist");
      frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      try {
         d = frmt.parse("20/20/1994 10:23 PM");
         bean2.setDate(new Timestamp(d.getTime()));
      } catch (ParseException e1) {
         //Won't happen
      }
      
      PhysicalTherapyScheduleOVRecordBean bean3 = new PhysicalTherapyScheduleOVRecordBean();
      bean3.setComment("Comment");
      bean3.setDoctormid(9210000000L);
      bean3.setPatientmid(1);
      bean3.setPending(true);
      bean3.setDocFirstName("Taylor");
      bean3.setDocLastName("Physical Therapist");
      frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      try {
         d = frmt.parse("20/20/1994 10:24 PM");
         bean3.setDate(new Timestamp(d.getTime()));
      } catch (ParseException e1) {
         //Won't happen
      }
      
      PhysicalTherapyScheduleOVRecordBean bean4 = new PhysicalTherapyScheduleOVRecordBean();
      bean4.setComment("Comment");
      bean4.setDoctormid(9210000000L);
      bean4.setPatientmid(1);
      bean4.setPending(true);
      bean4.setDocFirstName("Taylor");
      bean4.setDocLastName("Physical Therapist");
      frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      try {
         d = frmt.parse("20/20/1994 10:25 PM");
         bean4.setDate(new Timestamp(d.getTime()));
      } catch (ParseException e1) {
         //Won't happen
      }
      
      PhysicalTherapyScheduleOVRecordBean bean5 = new PhysicalTherapyScheduleOVRecordBean();
      bean5.setComment("Comment");
      bean5.setDoctormid(9210000000L);
      bean5.setPatientmid(1);
      bean5.setPending(true);
      bean5.setDocFirstName("Taylor");
      bean5.setDocLastName("Physical Therapist");
      frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      try {
         d = frmt.parse("20/20/1994 10:26 PM");
         bean5.setDate(new Timestamp(d.getTime()));
      } catch (ParseException e1) {
         //Won't happen
      }
      
      
      
      //Add the beans
      addAction.addPhysicalTherapyOV(bean1);
      addAction.addPhysicalTherapyOV(bean2);
      addAction.addPhysicalTherapyOV(bean3);
      addAction.addPhysicalTherapyOV(bean4);
      addAction.addPhysicalTherapyOV(bean5);
      
      //Now test the view
      ViewPhysicalTherapyScheduleOVAction viewAction = new ViewPhysicalTherapyScheduleOVAction(prodDAO, LOGGED_IN_MID);
      
      PhysicalTherapyScheduleOVRecordBean retBean = viewAction.getPhysicalTherapyScheduleOVForHCP(1L);
      assertEquals(bean1, retBean);
      
      List<PhysicalTherapyScheduleOVRecordBean> beans = viewAction.getPhysicalTherapyScheduleOVByPATIENTMID(1L);
      assertEquals(beans.get(0), bean5);
      assertEquals(beans.get(1), bean4);
      assertEquals(beans.get(2), bean3);
      assertEquals(beans.get(3), bean2);
      
      EditPhysicalTherapyScheduleOVAction editAction = new EditPhysicalTherapyScheduleOVAction(prodDAO, LOGGED_IN_MID);
      editAction.editPhysicalTherapyScheduleOV(2L, bean3);
      bean3.setOid(2L);
      assertEquals(viewAction.getPhysicalTherapyScheduleOVForPatient(2L), bean3);
      
      //Now test the view for a Patient viewing their own records
      ViewPhysicalTherapyScheduleOVAction patientViewAction = new ViewPhysicalTherapyScheduleOVAction(prodDAO, PATIENT_MID);
      retBean = patientViewAction.getPhysicalTherapyScheduleOVForPatient(1L);
      assertEquals(bean1, retBean);
      
      //Now test the view for a Patient viewing a Dependent's records
      retBean = patientViewAction.getPhysicalTherapyScheduleOVForPatient(5L);
      assertEquals(bean5, retBean);
   }
   
   @Test
   public void testErrors() throws FormValidationException{
      DAOFactory prodDAO = TestDAOFactory.getTestInstance();
      AddPhysicalTherapyScheduleOVAction addAction = new AddPhysicalTherapyScheduleOVAction(prodDAO, 401L);
      EditPhysicalTherapyScheduleOVAction editAction = new EditPhysicalTherapyScheduleOVAction(prodDAO, 401L);
      
      PhysicalTherapyScheduleOVRecordBean nullBean = null;
      
      try{
         addAction.addPhysicalTherapyOV(nullBean);
         fail();
      }catch(ITrustException e){
         assertNull(nullBean);
      }
      
      try{
         editAction.editPhysicalTherapyScheduleOV(1, nullBean);
         fail();
      }catch(ITrustException e){
         assertNull(nullBean);
      }
   }
   
   @Test
   public void testGetPersonnel() throws DBException{
      DAOFactory prodDAO = TestDAOFactory.getTestInstance();
      AddPhysicalTherapyScheduleOVAction addAction = new AddPhysicalTherapyScheduleOVAction(prodDAO, 401L);
      PersonnelDAO perDAO = new PersonnelDAO(prodDAO);
      
      List<PersonnelBean> personnel = addAction.getAllPhysicalTherapyPersonnel();
      personnel.contains(perDAO.getPersonnel(9000000085L));
      personnel.contains(perDAO.getPersonnel(9000000086L));
   }
   
}
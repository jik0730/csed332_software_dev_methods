package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OrthopedicScheduleOVRecordBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.action.AddOrthopedicScheduleOVAction;
import edu.ncsu.csc.itrust.action.ViewOrthopedicScheduleOVAction;
import edu.ncsu.csc.itrust.action.EditOrthopedicScheduleOVAction;

public class AddEditViewOrthopedicScheduleOVActionTest {
   private final long LOGGED_IN_MID = 9220000000L;
   private final long PATIENT_MID = 407L;
   
   @Before
   public void setUp() throws Exception {
      TestDataGenerator gen = new TestDataGenerator();
      gen.clearAllTables();
      gen.standardData();
   }
   
   @Test
   public void testAddOrthopedicOV() throws FormValidationException, ITrustException{
      DAOFactory prodDAO = TestDAOFactory.getTestInstance();
      
      AddOrthopedicScheduleOVAction addAction = new AddOrthopedicScheduleOVAction(prodDAO, LOGGED_IN_MID);
      
      OrthopedicScheduleOVRecordBean bean1 = new OrthopedicScheduleOVRecordBean();
      bean1.setComment("Comment");
      bean1.setDoctormid(9220000000L);
      bean1.setPatientmid(102);
      bean1.setPending(true);
      bean1.setDocFirstName("Momsen");
      bean1.setDocLastName("doctor");
      SimpleDateFormat frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      Date d;
      try {
         d = frmt.parse("20/20/1994 10:22 PM");
         bean1.setDate(new Timestamp(d.getTime()));
      } catch (ParseException e1) {
         //Won't happen
      }
      
      OrthopedicScheduleOVRecordBean bean2 = new OrthopedicScheduleOVRecordBean();
      bean2.setComment("Comment");
      bean2.setDoctormid(9220000000L);
      bean2.setPatientmid(102);
      bean2.setPending(true);
      bean2.setDocFirstName("Momsen");
      bean2.setDocLastName("doctor");
      frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      try {
         d = frmt.parse("20/20/1994 10:23 PM");
         bean2.setDate(new Timestamp(d.getTime()));
      } catch (ParseException e1) {
         //Won't happen
      }
      
      OrthopedicScheduleOVRecordBean bean3 = new OrthopedicScheduleOVRecordBean();
      bean3.setComment("Comment");
      bean3.setDoctormid(9220000000L);
      bean3.setPatientmid(102);
      bean3.setPending(true);
      bean3.setDocFirstName("Momsen");
      bean3.setDocLastName("doctor");
      frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      try {
         d = frmt.parse("20/20/1994 10:24 PM");
         bean3.setDate(new Timestamp(d.getTime()));
      } catch (ParseException e1) {
         //Won't happen
      }
      
      OrthopedicScheduleOVRecordBean bean4 = new OrthopedicScheduleOVRecordBean();
      bean4.setComment("Comment");
      bean4.setDoctormid(9220000000L);
      bean4.setPatientmid(102);
      bean4.setPending(true);
      bean4.setDocFirstName("Momsen");
      bean4.setDocLastName("doctor");
      frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      try {
         d = frmt.parse("20/20/1994 10:25 PM");
         bean4.setDate(new Timestamp(d.getTime()));
      } catch (ParseException e1) {
         //Won't happen
      }
      
      OrthopedicScheduleOVRecordBean bean5 = new OrthopedicScheduleOVRecordBean();
      bean5.setComment("Comment");
      bean5.setDoctormid(9220000000L);
      bean5.setPatientmid(102);
      bean5.setPending(true);
      bean5.setDocFirstName("Momsen");
      bean5.setDocLastName("doctor");
      frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      try {
         d = frmt.parse("20/20/1994 10:26 PM");
         bean5.setDate(new Timestamp(d.getTime()));
      } catch (ParseException e1) {
         //Won't happen
      }
      
      
      
      //Add the beans
      addAction.addOrthopedicOV(bean1);
      addAction.addOrthopedicOV(bean2);
      addAction.addOrthopedicOV(bean3);
      addAction.addOrthopedicOV(bean4);
      addAction.addOrthopedicOV(bean5);
      
      //Now test the view
      ViewOrthopedicScheduleOVAction viewAction = new ViewOrthopedicScheduleOVAction(prodDAO, LOGGED_IN_MID);
      
      OrthopedicScheduleOVRecordBean retBean = viewAction.getOrthopedicScheduleOVForHCP(1L);
      assertEquals(bean1, retBean);
      
      List<OrthopedicScheduleOVRecordBean> beans = viewAction.getOrthopedicScheduleOVByPATIENTMID(102L);
      assertEquals(beans.get(0), bean5);
      assertEquals(beans.get(1), bean4);
      assertEquals(beans.get(2), bean3);
      assertEquals(beans.get(3), bean2);
      
      EditOrthopedicScheduleOVAction editAction = new EditOrthopedicScheduleOVAction(prodDAO, LOGGED_IN_MID);
      editAction.editOrthopedicScheduleOV(2L, bean3);
      bean3.setOid(2L);
      assertEquals(viewAction.getOrthopedicScheduleOVForPatient(2L), bean3);
      
      //Now test the view for a Patient viewing their own records
      ViewOrthopedicScheduleOVAction patientViewAction = new ViewOrthopedicScheduleOVAction(prodDAO, PATIENT_MID);
      retBean = patientViewAction.getOrthopedicScheduleOVForPatient(1L);
      assertEquals(bean1, retBean);
      
      //Now test the view for a Patient viewing a Dependent's records
      retBean = patientViewAction.getOrthopedicScheduleOVForPatient(5L);
      assertEquals(bean5, retBean);
   }
   
   @Test
   public void testErrors() throws FormValidationException{
      DAOFactory prodDAO = TestDAOFactory.getTestInstance();
      AddOrthopedicScheduleOVAction addAction = new AddOrthopedicScheduleOVAction(prodDAO, 401L);
      EditOrthopedicScheduleOVAction editAction = new EditOrthopedicScheduleOVAction(prodDAO, 401L);
      
      OrthopedicScheduleOVRecordBean nullBean = null;
      
      try{
         addAction.addOrthopedicOV(nullBean);
         fail();
      }catch(ITrustException e){
         assertNull(nullBean);
      }
      
      try{
         editAction.editOrthopedicScheduleOV(1, nullBean);
         fail();
      }catch(ITrustException e){
         assertNull(nullBean);
      }
   }
   
   @Test
   public void testGetPersonnel() throws DBException{
      DAOFactory prodDAO = TestDAOFactory.getTestInstance();
      AddOrthopedicScheduleOVAction addAction = new AddOrthopedicScheduleOVAction(prodDAO, 401L);
      PersonnelDAO perDAO = new PersonnelDAO(prodDAO);
      
      List<PersonnelBean> personnel = addAction.getAllOrthopedicPersonnel();
      personnel.contains(perDAO.getPersonnel(9220000000L));
      //personnel.contains(perDAO.getPersonnel(9000000086L));
   }
   
}
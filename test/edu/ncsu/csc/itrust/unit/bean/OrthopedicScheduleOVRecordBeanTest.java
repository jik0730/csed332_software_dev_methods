package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.OrthopedicScheduleOVRecordBean;

public class OrthopedicScheduleOVRecordBeanTest {
   
   @Test
   public void testGetSet() throws ParseException{
      OrthopedicScheduleOVRecordBean bean1 = new OrthopedicScheduleOVRecordBean();
      bean1.setComment("Comment");
      bean1.setDoctormid(101);
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
      
      assertEquals(bean1.getComment(), "Comment");
      assertEquals(bean1.getDoctormid(), 101);
      assertEquals(bean1.getPatientmid(), 102);
      assertEquals(bean1.isPending(), true);
      assertEquals(bean1.getDocFirstName(), "Momsen");
      assertEquals(bean1.getDocLastName(), "doctor");
      try {
         d = frmt.parse("20/20/1994 10:22 PM");
         assertEquals(bean1.getDate(), new Timestamp(d.getTime()));
      } catch (ParseException e1) {
         //Won't happen
      }
      
   }
   
   @Test
   public void testEquality(){
      OrthopedicScheduleOVRecordBean bean1 = new OrthopedicScheduleOVRecordBean();
      bean1.setComment("Comment");
      bean1.setDoctormid(101);
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
      bean2.setDoctormid(101);
      bean2.setPatientmid(102);
      bean2.setPending(true);
      bean2.setDocFirstName("Momsen");
      bean2.setDocLastName("doctor");
      try {
         d = frmt.parse("20/20/1994 10:22 PM");
         bean2.setDate(new Timestamp(d.getTime()));
      } catch (ParseException e1) {
         //Won't happen
      }
      
      
      assertEquals(bean1, bean2);
      assertEquals(bean1.toString(), bean2.toString());
      assertEquals(bean1.hashCode(), bean2.hashCode());
      
      
      bean1.setComment(null);
      assertFalse(bean1.equals(bean2));
      //assertFalse(bean1.hashCode() == (bean2.hashCode()));
      bean1.setComment("Comment");
      bean1.setDoctormid(101);
      bean1.setPatientmid(102);
      bean1.setPending(true);
      bean1.setDocFirstName("Momsen");
      bean1.setDocLastName("doctor");
      
      bean1.setDoctormid(103);
      assertFalse(bean1.equals(bean2));
      //assertFalse(bean1.hashCode() == (bean2.hashCode()));
      bean1.setDoctormid(101);
      
      bean1.setPatientmid(1022);
      assertFalse(bean1.equals(bean2));
      //assertFalse(bean1.hashCode() == (bean2.hashCode()));
      bean1.setPatientmid(102);
      
      bean1.setPending(false);
      assertFalse(bean1.equals(bean2));
      //assertFalse(bean1.hashCode() == (bean2.hashCode()));
      bean1.setPending(true);
      
      bean1.setDocFirstName("bildl");
      assertFalse(bean1.equals(bean2));
      //assertFalse(bean1.hashCode() == (bean2.hashCode()));
      bean1.setDocFirstName("Momsen");
      
      bean1.setDocLastName("doctord");
      assertFalse(bean1.equals(bean2));
      //assertFalse(bean1.hashCode() == (bean2.hashCode()));
      bean1.setDocLastName("doctor");
   }
}
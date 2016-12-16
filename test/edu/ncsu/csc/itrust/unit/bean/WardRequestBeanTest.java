package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.WardRequestBean;

public class WardRequestBeanTest {

	@Test
	public void testGetSet() throws ParseException{
		WardRequestBean wbBean = new WardRequestBean();
		
		wbBean.setPending(false);
		wbBean.setAccepted(true);
		wbBean.setRequestedWardRoom(null);
		
		assertEquals (wbBean.getRequestedWardRoom(), null);
		assertEquals (wbBean.isAccepted(), true);
		assertEquals (wbBean.isPending(), false);
		
		wbBean.setAccepted(false);
		assertEquals (wbBean.isAccepted(), false);
		
		wbBean.setPending(true);
		wbBean.setAccepted(false);
		
		assertEquals (wbBean.isPending(), true);
		assertEquals (wbBean.isAccepted(), false);
	}
}

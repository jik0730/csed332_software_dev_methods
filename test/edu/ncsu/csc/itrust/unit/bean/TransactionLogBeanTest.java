package edu.ncsu.csc.itrust.unit.bean;

import edu.ncsu.csc.itrust.beans.TransactionLogBean;
import edu.ncsu.csc.itrust.enums.TransactionType;
import java.sql.Timestamp;
import java.util.Date;

import junit.framework.TestCase;

/**
 * WardBeanTest
 */
public class TransactionLogBeanTest extends TestCase {
	TransactionLogBean testBean;
	Date dt;
	/**
	 * setUp
	 */
	public void setUp(){
		dt = new Date ();
	}
	
	/**
	 * testWardID
	 */
	public void testTransactionLogBean(){
		testBean = new TransactionLogBean ();
		assertNotNull (testBean);
		
		testBean.setTransactionType(TransactionType.OFFICE_VISIT_EDIT);
		testBean.setLoggedInRole("hcp");
		testBean.setSecondaryRole("patient");
		testBean.setTimeLogged(new Timestamp (dt.getTime()));
		testBean.setTransactionID(1L);
		testBean.setAddedInfo("hihi");
		
		assertEquals (testBean.getTransactionType(), TransactionType.OFFICE_VISIT_EDIT);
		assertEquals (testBean.getLoggedInRole(), "hcp");
		assertEquals (testBean.getSecondaryRole(), "patient");
		assertEquals (testBean.getTimeLogged().getTime(), dt.getTime());
		assertEquals (testBean.getTransactionID(), 1L);
		assertEquals (testBean.getAddedInfo(), "hihi");
	}

}

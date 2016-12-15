package edu.ncsu.csc.itrust.unit.enums;

import edu.ncsu.csc.itrust.enums.TransactionLogColumnType;
import junit.framework.TestCase;

public class TransactionLogColumnTypeTest extends TestCase {
	public void testParse() throws Exception {
		for(TransactionLogColumnType type : TransactionLogColumnType.values()){
			assertEquals(type, TransactionLogColumnType.parse(type.getCode()));
		}
	}
	
	public void testBadParse() throws Exception {
		try{
			TransactionLogColumnType.parse(37);
			TransactionLogColumnType.parse(99);
			fail("exception should have been thrown");
		} catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
			assertEquals("No transaction log column type exists for code 37", e.getMessage());
		}
	}
	
}

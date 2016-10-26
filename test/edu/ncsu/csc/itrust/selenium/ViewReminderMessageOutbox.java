package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ViewReminderMessageOutbox extends iTrustSeleniumTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	@Test
	public void test() {
		WebDriver driver = login("9000000001", "pw");
		WebElement element;
		
		element = driver.findElement(By.linkText("Reminder Message Outbox"));
		element.click();
	}

}

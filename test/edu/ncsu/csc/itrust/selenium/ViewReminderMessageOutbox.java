package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ViewReminderMessageOutbox extends iTrustSeleniumTest {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.uc41_add_appointments();
	}

	private WebDriver testSendReminder(String input) throws Exception {
		WebDriver driver = login("9000000001", "pw");
		driver.findElement(By.linkText("Send Reminder Message"));
		driver.findElement(By.name("apptDaysLeftUpperBound")).clear();
		driver.findElement(By.name("apptDaysLeftUpperBound")).sendKeys(input);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();	
		
		return driver;
		/*
		assertTrue(driver.getPageSource().contains(expected));

		driver.findElement(By.linkText("Reminder Message Outbox")).click();
		if (noMessagesAfterSubmit) {
			assertTrue(driver.getPageSource().contains("no messages"));
		}
		else { //check format

		}
		*/
	}
	
	@Test
	public void testWhenInputIsValid() throws Exception {
		WebDriver driver = testSendReminder("7");
		assertTrue(driver.getPageSource().contains("Succeeded"));
	}

	@Test
	public void testWhenInputIsInvalid() throws Exception {
		WebDriver driver = testSendReminder("troll");
		assertTrue(driver.getPageSource().contains("Failed"));
	}
	
	@Test //TODO: Not finished
	public void testCheckReminderMessageOutbox() throws Exception {
		WebDriver driver = testSendReminder("7");
		WebElement table = driver.findElement(By.id("mailbox"));
		List<WebElement> rows = table.findElements(By.xpath("id('mailbox')/tbody/tr"));
		for(WebElement row: rows) {
			List<WebElement> cols = row.findElements(By.xpath("td"));
		}
	}
}

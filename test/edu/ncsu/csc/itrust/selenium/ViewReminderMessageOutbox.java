package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import java.util.regex.*;
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
		driver.findElement(By.linkText("Send Reminder Message")).click();
		driver.findElement(By.name("apptDaysLeftUpperBound")).clear();
		driver.findElement(By.name("apptDaysLeftUpperBound")).sendKeys(input);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();	
		
		return driver;
	}

	Pattern subjectPattern = Pattern.compile("Reminder: upcoming appointment in (\\d) day[(]s[)]$");
	Pattern bodyPattern = Pattern.compile("You have an appointment on \\d{4}-\\d{2}-\\d{2}, with Dr[.] (\\w+)");
	
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
	
	@Test
	public void testCheckReminderMessageOutbox() throws Exception {
		WebDriver driver = testSendReminder("7");
		driver.findElement(By.linkText("Reminder Message Outbox")).click();
		WebElement table = driver.findElement(By.id("mailbox"));
		List<WebElement> rows = table.findElements(By.xpath("id('mailbox')/tbody/tr"));
		
		
		boolean contains6 = false, contains7 = false; 
		for(WebElement row: rows){
			List<WebElement> cols = row.findElements(By.xpath("td"));
			
			Matcher m = subjectPattern.matcher(cols.get(1).getText());
			m.find();
			int parsedDay = Integer.valueOf(m.group(1));
			if (parsedDay == 6) {
				if("Anakin Skywalker".equals(cols.get(0).getText()))
					contains6 = true;
			}
			else if(parsedDay == 7) {
				if("Anakin Skywalker".equals(cols.get(0).getText()))
				contains7 = true;
			}
		}
		assertTrue(contains6);
		assertTrue(contains7);
	}
	
	@Test
	public void testReadReminderMessageOutboxEntry() throws Exception {
		WebDriver driver = testSendReminder("7");
		driver.findElement(By.linkText("Reminder Message Outbox")).click();
		List<WebElement> readButtons = driver.findElements(By.linkText("Read"));
		int messageCount = readButtons.size();
		
		boolean contains6 = false , contains7 = false;
		for (int i=0; i<messageCount; i++) {
			driver.findElements(By.linkText("Read")).get(i).click();
			WebElement headTable = driver.findElement(By.xpath("id('iTrustContent')/div/table"));
			WebElement bodyTable = driver.findElement(By.xpath("id('iTrustContent')/table"));

			WebElement toField = headTable.findElements(By.xpath("tbody/tr/td")).get(0);
			WebElement subjectField = headTable.findElements(By.xpath("tbody/tr/td")).get(1);
			WebElement bodyField = bodyTable.findElements(By.xpath("tbody/tr/td")).get(1);
			
			Matcher subjectMatcher = subjectPattern.matcher(subjectField.getText());
			Matcher bodyMatcher = bodyPattern.matcher(bodyField.getText());
			subjectMatcher.find();
			bodyMatcher.find();
			
			if(toField.getText().contains("Anakin Skywalker")) {
				if (bodyMatcher.group(1).equals("Kelly")) {
					if (subjectMatcher.group(1).equals("6")) contains6 = true;
					if (subjectMatcher.group(1).equals("7")) contains7 = true;
				}
			}
			
			driver.findElement(By.linkText("Back")).click();
		}
		assertTrue(contains6);
		assertTrue(contains7);
	}
}

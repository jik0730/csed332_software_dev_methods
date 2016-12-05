package edu.ncsu.csc.itrust.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class OrthopedicOVTest extends iTrustSeleniumTest {

	private WebDriver driver;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.uc88();
	}
 	
	@After
	public void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	@Test
	public void testViewOVList() throws Exception {
		WebDriver driver = login("9220000000", "pw");
		
		driver.findElement(By.linkText("Orthopedic Home")).click();
		driver.findElement(By.id("searchBox")).sendKeys("1");
		driver.findElement(By.cssSelector("input[type=button][value=1]")).click();
		int size = driver.findElements(By.xpath("//*[contains(text(), '11/14/2016')]")).size();
		assertEquals(1, size);
	}
 	
	@Test
	public void testViewOVDetails() throws Exception {
		WebDriver driver = login("9000000000", "pw");
		
		driver.findElement(By.linkText("Orthopedic Home")).click();
		driver.findElement(By.id("searchBox")).sendKeys("1");
		driver.findElement(By.cssSelector("input[type=button][value=1]")).click();
		driver.findElement(By.xpath("//*[contains(text(), '11/14/2016')]")).click();
		
		WebElement table = driver.findElements(By.className("fTable")).get(0);
		List<WebElement> list = table.findElements(By.xpath("tbody/tr/td"));
		List<WebElement> values = new ArrayList<WebElement>();
		
		for(int i = 1; i < list.size(); i += 3) {
			values.add(list.get(i));
		}
		
		assertEquals("Random Person", values.get(0).getText());
		assertEquals("11/14/2016", values.get(1).getText());
		assertEquals("UC88 Injured", values.get(2).getText());
		assertEquals("UC88 MriReport", values.get(5).getText());
		
		WebElement table2 = driver.findElements(By.className("fTable")).get(1);
		List<WebElement> list2 = table2.findElements(By.xpath("tbody/tr/td"));
		List<WebElement> values2 = new ArrayList<WebElement>();
		
		for(int i = 0; i < list2.size(); i += 3) {
			values2.add(list2.get(i));
		}
		
		assertEquals("83.50", values.get(0).getText());
		assertEquals("83.20", values.get(1).getText());
	}
	
	@Test
	public void testAddOV() throws Exception {
		WebDriver driver = login("9220000000", "pw");
		
		driver.findElement(By.linkText("Add Orthopedic Office Visit")).click();
		driver.findElement(By.id("searchBox")).sendKeys("1");
		driver.findElement(By.cssSelector("input[type=\"button\"][value=\"1\"]")).click();
		
		WebElement table = driver.findElements(By.className("fTable")).get(0);
		List<WebElement> list = table.findElements(By.xpath("tbody/tr/td"));
		List<WebElement> inputs = new ArrayList<WebElement>();
		
		for(int i = 1; i < list.size(); i += 3) {
			inputs.add(list.get(i));
		}
		
		inputs.get(0).findElement(By.cssSelector("[type=\"text\"]")).sendKeys("11/13/2016");
		inputs.get(1).sendKeys("UC88 Selenium Injured");
		inputs.get(4).sendKeys("UC88 Selenium MriReport");
		
		new Select(driver.findElement(By.name("ICDCode"))).selectByVisibleText("83.20 - Meniscus tear");
		
		driver.findElement(By.id("submit")).click();
		int size = driver.findElements(By.xpath("//*[contains(text(), '11/13/2016')]")).size();
		
		assertEquals(1, size);
	}
	
	
	@Test
	public void testEditOV() throws Exception {
		WebDriver driver = login("9220000000", "pw");
		
		driver.findElement(By.linkText("Orthopedic Home")).click();
		driver.findElement(By.id("searchBox")).sendKeys("1");
		driver.findElement(By.cssSelector("input[type=\"button\"][value=\"1\"]")).click();
		driver.findElement(By.xpath("//*[contains(text(), '11/14/2016')]")).click();
		
		WebElement table = driver.findElements(By.className("fTable")).get(0);
		List<WebElement> list = table.findElements(By.xpath("tbody/tr/td"));
		List<WebElement> inputs = new ArrayList<WebElement>();
		
		for(int i = 1; i < list.size(); i += 3) {
			inputs.add(list.get(i));
		}
		
		inputs.get(0).findElement(By.cssSelector("[type=\"text\"]")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		inputs.get(0).findElement(By.cssSelector("[type=\"text\"]")).sendKeys(Keys.DELETE);
		inputs.get(0).findElement(By.cssSelector("[type=\"text\"]")).sendKeys("11/13/2016");
		inputs.get(1).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		inputs.get(1).sendKeys(Keys.DELETE);
		inputs.get(1).sendKeys("UC88 Selenium Injured");
		inputs.get(4).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		inputs.get(4).sendKeys(Keys.DELETE);
		inputs.get(4).sendKeys("UC88 Selenium MriReport");
		
		new Select(driver.findElement(By.name("ICDCode"))).selectByVisibleText("13.40 - Whiplash injury");
		driver.findElement(By.id("add_diagnosis")).click();
		
		driver.findElement(By.id("submit")).click();
		
		String s = driver.findElement(By.id("iTrustContent")).findElement(By.tagName("p")).getText();
		assertEquals("Orthopedic Office Visit successfully edited", s);
	}
}

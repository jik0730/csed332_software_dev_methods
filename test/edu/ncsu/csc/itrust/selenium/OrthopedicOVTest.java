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
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import com.gargoylesoftware.htmlunit.BrowserVersion;

/** OrthopedicOVTest */
public class OrthopedicOVTest extends iTrustSeleniumTest {

	private HtmlUnitDriver driver;
	private String baseUrl;
	
	/**
	 * set up
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.uc88();
		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
	    baseUrl = "http://localhost:8080/iTrust/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    driver.setJavascriptEnabled(true);
	}
 	
	/**
	 * Access orthopedic office visit list
	 * 
	 * @throws Exception
	 */
	@Test
	public void testViewOVList() throws Exception {
		driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9220000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[11]/div/h2")).click();
	    driver.findElement(By.linkText("View Orthopedic Records")).click();
	    driver.findElement(By.id("searchBox")).sendKeys("1");

		for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("".equals(driver.findElement(By.cssSelector("input[type=\"button\"][value=\"1\"]")).getText())) break; } catch (Exception e) {
	    		System.out.println("wow");
	    	}
	    	Thread.sleep(1000);
	    }	

		driver.findElement(By.cssSelector("input[type=\"button\"][value=\"1\"]")).click();
		int size = driver.findElements(By.xpath("//*[contains(text(), '11/14/2016')]")).size();
		assertEquals(1, size);
	}
 	
	/**
	 * View Orthopedic Office Visit
	 * 
	 * @throws Exception
	 */
	@Test
	public void testViewOVDetails() throws Exception {
		driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[11]/div/h2")).click();
	    driver.findElement(By.linkText("Orthopedic Home")).click();
	    driver.findElement(By.id("searchBox")).sendKeys("1");

		for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("".equals(driver.findElement(By.cssSelector("input[type=\"button\"][value=\"1\"]")).getText())) break; } catch (Exception e) {
	    		System.out.println("wow");
	    	}
	    	Thread.sleep(1000);
	    }

		driver.findElement(By.cssSelector("input[type=\"button\"][value=\"1\"]")).click();
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
		assertEquals("UC88 MRIReport", values.get(5).getText());
		
		WebElement table2 = driver.findElements(By.className("fTable")).get(2);
		List<WebElement> list2 = table2.findElements(By.xpath("tbody/tr/td"));
		List<WebElement> values2 = new ArrayList<WebElement>();
		
		for(int i = 0; i < list2.size(); i += 3) {
			values2.add(list2.get(i));
		}
		
		assertEquals("83.50", values2.get(0).getText());
		assertEquals("83.20", values2.get(1).getText());
	}

	/**
	 * Document Office Visit Information
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddOV() throws Exception {
		driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9220000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[11]/div/h2")).click();
	    driver.findElement(By.linkText("Add Orthopedic Office Visit")).click();
	    driver.findElement(By.id("searchBox")).sendKeys("1");

		for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("".equals(driver.findElement(By.cssSelector("input[type=\"button\"][value=\"1\"]")).getText())) break; } catch (Exception e) {
	    		System.out.println("wow");
	    	}
	    	Thread.sleep(1000);
	    }
		
		driver.findElement(By.cssSelector("input[type=\"button\"][value=\"1\"]")).click();
		
		WebElement table = driver.findElements(By.className("fTable")).get(0);
		List<WebElement> list = table.findElements(By.xpath("tbody/tr/td"));
		List<WebElement> inputs = new ArrayList<WebElement>();
		
		for(int i = 1; i < list.size(); i += 3) {
			inputs.add(list.get(i));
		}
		
		inputs.get(0).findElement(By.cssSelector("[type=\"text\"]")).sendKeys("11/13/2016");
		inputs.get(1).findElement(By.cssSelector("[type=\"text\"]")).sendKeys("UC88 Selenium Injured");
		inputs.get(4).findElement(By.cssSelector("[type=\"text\"]")).sendKeys("UC88 Selenium MriReport");
		
		new Select(driver.findElement(By.name("ICDCode"))).selectByVisibleText("83.20 - Meniscus tear");
		
		driver.findElement(By.id("submit")).click();
		System.out.println(driver.getPageSource());
		int size = driver.findElements(By.xpath("//*[contains(text(), '11/13/2016')]")).size();
		
		assertEquals(1, size);
	}
	
	/**
	 * Edit office visit
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEditOV() throws Exception {
		driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9220000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[11]/div/h2")).click();
	    driver.findElement(By.linkText("Orthopedic Home")).click();
	    driver.findElement(By.id("searchBox")).sendKeys("1");

		for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("".equals(driver.findElement(By.cssSelector("input[type=\"button\"][value=\"1\"]")).getText())) break; } catch (Exception e) {
	    		System.out.println("wow");
	    	}
	    	Thread.sleep(1000);
	    }
		
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
		inputs.get(1).findElement(By.cssSelector("[type=\"text\"]")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		inputs.get(1).findElement(By.cssSelector("[type=\"text\"]")).sendKeys(Keys.DELETE);
		inputs.get(1).findElement(By.cssSelector("[type=\"text\"]")).sendKeys("UC88 Selenium Injured");
		inputs.get(4).findElement(By.cssSelector("[type=\"text\"]")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		inputs.get(4).findElement(By.cssSelector("[type=\"text\"]")).sendKeys(Keys.DELETE);
		inputs.get(4).findElement(By.cssSelector("[type=\"text\"]")).sendKeys("UC88 Selenium MriReport");
		
		new Select(driver.findElement(By.name("ICDCode"))).selectByVisibleText("13.40 - Whiplash injury");
		driver.findElement(By.id("add_diagnosis")).click();
		
		driver.findElement(By.id("submit")).click();
		
		String s = driver.findElement(By.id("iTrustContent")).findElement(By.tagName("p")).getText();
		assertEquals("Orthopedic Office Visit successfully added", s);
	}
}

package edu.ncsu.csc.itrust.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Selenium test cases for the Orthopedic Surgery feature in UC90.
 */
public class UC91SeleniumTest extends iTrustSeleniumTest{

	/**
	 * Sets up the required base data needed for each test case. This includes two HCP's and two patients.
	 */
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
//		gen.uc88();
		gen.patient12();
		gen.patient2();
		gen.hcp922();
		gen.hcp921();
		gen.hcp0();
		gen.patient27();
		gen.patient91();
	}
	
	/**
	 * Clears the database after each test.
	 */
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	
	/**
	 * Selenium test for UC90 Acceptance Scenario 1.
	 * @throws Exception
	 */
	public void testCreateOrthopedic() throws Exception{
		WebDriver driver = login("9220000000", "pw");
		
		driver.findElement(By.linkText("Add Orthopedic Office Visit")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		
		WebElement table = driver.findElements(By.className("fTable")).get(0);
		List<WebElement> list = table.findElements(By.xpath("tbody/tr/td"));
		List<WebElement> inputs = new ArrayList<WebElement>();
		
		for(int i = 1; i < list.size(); i += 3) {
			inputs.add(list.get(i));
		}
		
		inputs.get(0).findElement(By.cssSelector("[type=\"text\"]")).sendKeys("11/13/2016");
		driver.findElement(By.name("Injured")).sendKeys("UC88 Selenium Injured");
		driver.findElement(By.name("mriReport")).sendKeys("UC88 Selenium MriReport");
		
		new Select(driver.findElement(By.name("ICDCode"))).selectByVisibleText("83.20 - Meniscus tear");
		
		driver.findElement(By.id("submit")).click();
	}
	
	public void testCreateDependentOrthopedic() throws Exception{
		WebDriver driver = login("9220000000", "pw");
		
		driver.findElement(By.linkText("Add Orthopedic Office Visit")).click();
		driver.findElement(By.id("searchBox")).sendKeys("91");
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("91");
		driver.findElement(By.xpath("//input[@value='91']")).submit();
		
		WebElement table = driver.findElements(By.className("fTable")).get(0);
		List<WebElement> list = table.findElements(By.xpath("tbody/tr/td"));
		List<WebElement> inputs = new ArrayList<WebElement>();
		
		for(int i = 1; i < list.size(); i += 3) {
			inputs.add(list.get(i));
		}
		
		inputs.get(0).findElement(By.cssSelector("[type=\"text\"]")).sendKeys("11/14/2016");
		driver.findElement(By.name("Injured")).sendKeys("UC88 Selenium Injured");
		driver.findElement(By.name("mriReport")).sendKeys("UC88 Selenium MriReport");
		
		new Select(driver.findElement(By.name("ICDCode"))).selectByVisibleText("83.20 - Meniscus tear");
		
		driver.findElement(By.id("submit")).click();
	}
	
	public void testCreatePhysicalTherapyOV() throws Exception{
		//Login as Physical Therapist
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9220000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9220000000L, 0L, "");
		
		//Click the Add Physical Therapy Office Visit link
		driver.findElement(By.linkText("Add PhysicalTherapy Office Visit")).click();
		
		//Select Brody Franco as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		String title = driver.getTitle();
		assertEquals("iTrust - Add PhysicalTherapy Office Visit", title);
		
		//Enable javascript
		driver.setJavascriptEnabled(true);
		
		//fill in form and create
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/14/2015");
		for(int i=0; i<10; i++){
			driver.findElement(By.name("wellnessSurveyResult"+String.valueOf(i))).sendKeys("25");
		}
		for(int i=0; i<10; i++){
			driver.findElement(By.name("exercise"+String.valueOf(i))).sendKeys("false");
		}
		
		driver.findElement(By.id("submit")).submit();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Verify that we are returned to the PhisycalTherapy Homepage
		title = driver.getTitle();
		assertEquals("iTrust - PhysicalTherapy", title);
		
		//Verify that the action was logged
		assertTrue(driver.getPageSource().contains("PhysicalTherapy Office Visit successfully added"));
		assertLogged(TransactionType.CREATE_PHYSICALTHERAPY_OV, 9220000000L, 2L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/14/2015"));
	}
	
	public void testCreateDependentPhysicalTherapyOV() throws Exception{
		//Login as Physical Therapist
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9220000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9220000000L, 0L, "");
		
		//Click the Add Physical Therapy Office Visit link
		driver.findElement(By.linkText("Add PhysicalTherapy Office Visit")).click();
		
		//Select Brody Franco as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("91");
		driver.findElement(By.xpath("//input[@value='91']")).submit();
		String title = driver.getTitle();
		assertEquals("iTrust - Add PhysicalTherapy Office Visit", title);
		
		//Enable javascript
		driver.setJavascriptEnabled(true);
		
		//fill in form and create
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/15/2015");
		for(int i=0; i<10; i++){
			driver.findElement(By.name("wellnessSurveyResult"+String.valueOf(i))).sendKeys("25");
		}
		for(int i=0; i<10; i++){
			driver.findElement(By.name("exercise"+String.valueOf(i))).sendKeys("false");
		}
		
		driver.findElement(By.id("submit")).submit();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Verify that we are returned to the Phi1sycalTherapy Homepage
		title = driver.getTitle();
		assertEquals("iTrust - PhysicalTherapy", title);
		
		//Verify that the action was logged
		assertTrue(driver.getPageSource().contains("PhysicalTherapy Office Visit successfully added"));
		assertLogged(TransactionType.CREATE_PHYSICALTHERAPY_OV, 9220000000L, 91L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/15/2015"));
	}
	
	public void testViewOrthopedicMyRecord() throws Exception {
		testCreateOrthopedic();
		WebDriver driver = login("2", "pw");
		driver.findElement(By.linkText("View Orthopedic Records")).click();
		
		driver.findElement(By.linkText("11/13/2016")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		wait.until(ExpectedConditions.titleIs("iTrust - View Orthopedic Record"));
		System.out.println(driver.findElement(By.name("hcpName")).getText());
		assertEquals("Momsen doctor", driver.findElement(By.name("hcpName")).getText());
		assertEquals("Andy Programmer", driver.findElement(By.name("patientName")).getText());
		assertEquals("11/13/2016", driver.findElement(By.name("visitDate")).getText());
		assertEquals("UC88 Selenium Injured", driver.findElement(By.name("injured")).getText());
		assertEquals("UC88 Selenium MriReport", driver.findElement(By.name("mriReport")).getText());
		assertEquals("83.20", driver.findElement(By.name("icdCode")).getText());
		
	}
	
	public void testViewOrthopedicDependentRecord() throws Exception {
		testCreateDependentOrthopedic();
		WebDriver driver = login("2", "pw");
		driver.findElement(By.linkText("View Dependent's Orthopedic Records")).click();
		
		
		Select select = new Select(driver.findElement(By.name("selectedDependent")));
		select.selectByVisibleText("UC 91");
		driver.findElement(By.id("submit")).click();
		driver.findElement(By.linkText("11/14/2016")).click();
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		wait.until(ExpectedConditions.titleIs("iTrust - View Orthopedic Record"));
		System.out.println(driver.findElement(By.name("hcpName")).getText());
		assertEquals("Momsen doctor", driver.findElement(By.name("hcpName")).getText());
		assertEquals("UC 91", driver.findElement(By.name("patientName")).getText());
		assertEquals("11/14/2016", driver.findElement(By.name("visitDate")).getText());
		assertEquals("UC88 Selenium Injured", driver.findElement(By.name("injured")).getText());
		assertEquals("UC88 Selenium MriReport", driver.findElement(By.name("mriReport")).getText());
		assertEquals("83.20", driver.findElement(By.name("icdCode")).getText());
		
	}
	
	
	public void testViewPhysicalTherapyOV() throws Exception {
		testCreatePhysicalTherapyOV();
		WebDriver driver = login("2", "pw");
		driver.findElement(By.linkText("View PhysicalTherapy Records")).click();
		
		driver.findElement(By.linkText("10/14/2015")).click();

		assertEquals("Andy Programmer", driver.findElement(By.name("patientName")).getText());
		assertEquals("10/14/2015", driver.findElement(By.name("visitDate")).getText());
		System.out.println(driver.findElement(By.name("wellnessScore")).getText());
		
	}
	
	public void testViewDependentPhysicalTherapyOV() throws Exception {
		testCreateDependentPhysicalTherapyOV();
		WebDriver driver = login("2", "pw");
		driver.findElement(By.linkText("View Dependent's PhysicalTherapy Records")).click();
		
		
		Select select = new Select(driver.findElement(By.name("selectedDependent")));
		select.selectByVisibleText("UC 91");
		driver.findElement(By.id("submit")).click();
		driver.findElement(By.linkText("10/15/2015")).click();

		assertEquals("UC 91", driver.findElement(By.name("patientName")).getText());
		assertEquals("10/15/2015", driver.findElement(By.name("visitDate")).getText());
		System.out.println(driver.findElement(By.name("wellnessScore")).getText());
		
	}
	/**
	 * Selenium test for UC90 Acceptance Scenario 4.
	 * @throws Exception
	 */


}
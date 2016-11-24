package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Selenium test cases for the Orthopedic Surgery feature in UC90.
 */
public class OrthopedicSurgeryTest extends iTrustSeleniumTest{

	/**
	 * Sets up the required base data needed for each test case. This includes two HCP's and two patients.
	 */
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.hcp0();
		gen.hcp11();
		gen.hcp12();
		gen.patient27();
		gen.patient28();
		gen.hcp922();
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
	public void testCreateOrthopedicSurgery() throws Exception{
		//Login as Lamar Bridges
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9220000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9220000000L, 0L, "");
		
		//Click the Add Surgical Orthopedic Office Visit link
		driver.findElement(By.linkText("Add Surgical Orthopedic Office Visit")).click();
		
		//Select Brody Franco as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("407");
		driver.findElement(By.xpath("//input[@value='407']")).submit();
		
		// Wait for the page change
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		wait.until(ExpectedConditions.titleIs("iTrust - Add Surgical Orthopedic Office Visit"));
		
		//Enable javascript
		driver.setJavascriptEnabled(true);
		
		//fill in form and create
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys("10/14/2015");
		Select select = new Select(driver.findElementByName("surgery"));
		select.selectByVisibleText("Total knee replacement");
		driver.findElement(By.id("surgeryNotes")).clear();
		driver.findElement(By.id("surgeryNotes")).sendKeys("Surgery completed with no issues.");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Verify that we are returned to the Orthopedic Homepage
		wait.until(ExpectedConditions.titleIs("iTrust - Surgical Orthopedic"));
		
		//Verify that the action was logged
		assertTrue(driver.getPageSource().contains("Surgical Orthopedic Office Visit successfully added"));
		assertLogged(TransactionType.CREATE_ORTHOPEDIC_SURGERY, 9220000000L, 407L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/14/2015"));
	}
	/**
	 * Selenium test for UC90 Acceptance Scenario 4.
	 * @throws Exception
	 */
	public void testEditOrthopedicSurgery() throws Exception{
		testCreateOrthopedicSurgery();
		
		//Login as Lamar Bridges
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9220000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9220000000L, 0L, "");

		//Click the Orthopedic Home link
		driver.findElement(By.linkText("Surgical Orthopedic Home")).click();

		//Select Freya Chandler as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("407");
		driver.findElement(By.xpath("//input[@value='407']")).submit();
		
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		wait.until(ExpectedConditions.titleIs("iTrust - Surgical Orthopedic"));
		
		WebElement myElement = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.linkText("10/14/2015")));
		myElement.click();
		
		//Fill in the correct values and submit the form
		driver.findElement(By.id("surgeryNotes")).clear();
		driver.findElement(By.id("surgeryNotes")).sendKeys("Surgery completed with no issues.");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Verify that we are returned to the Orthopedic Homepage
		wait.until(ExpectedConditions.titleIs("iTrust - Surgical Orthopedic"));
		assertTrue(driver.getPageSource().contains("Surgical Orthopedic Office Visit successfully edited"));

		//Verify that the action was logged
		assertLogged(TransactionType.EDIT_ORTHOPEDIC_SURGERY, 9220000000L, 407L, "");

		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/14/2015"));
	}
	
	/**
	 * Selenium test for UC90 Acceptance Scenario 4.
	 * @throws Exception
	 */
	public void testViewOrthopedicSurgery() throws Exception{
		testCreateOrthopedicSurgery();
		
		//Login as Lamar Bridges
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9220000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9220000000L, 0L, "");

		//Click the Orthopedic Home link
		driver.findElement(By.linkText("Surgical Orthopedic Home")).click();

		//Select Freya Chandler as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("407");
		driver.findElement(By.xpath("//input[@value='407']")).submit();
		
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		wait.until(ExpectedConditions.titleIs("iTrust - Surgical Orthopedic"));
		
		WebElement myElement = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.linkText("10/14/2015")));
		myElement.click();
		
		//Fill in the correct values and submit the form
		Select select = new Select(driver.findElementByName("surgery"));
		assertEquals(select.getFirstSelectedOption().getText().toString(), "Total knee replacement");
		System.out.println(driver.findElement(By.id("surgeryNotes")).getAttribute("value"));
		assertEquals(driver.findElement(By.id("surgeryNotes")).getAttribute("value"), "Surgery completed with no issues.");
	}
	/**
	 * Selenium test for UC90 Acceptance Scenario 3.
	 * @throws Exception
	 */
	public void testNotOrthopedicCreateOrthopedicSurgery() throws Exception{
		//Set up the outcome of Scenario 1
		
		//Login as Brooke Tran
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000085", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000085L, 0L, "");
		
		//Click the Add Orthopedic Office Visit link
		driver.findElement(By.linkText("Add Surgical Orthopedic Office Visit")).click();
		
		//Select Brody Franco as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("407");
		driver.findElement(By.xpath("//input[@value='407']")).submit();
		
		//Verify that we got redirected to the regular oph office visit page
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		wait.until(ExpectedConditions.titleIs("iTrust - Document Office Visit"));
	}
}
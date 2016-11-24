package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class PhysicalTherapyOfficeVisitTest extends iTrustSeleniumTest {

	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.hcp922();
		gen.hcp0();
		gen.patient27();
	}
	
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	public void testCreatePhysicalTherapyOfficeVisit() throws Exception{
		//Login as Physical Therapist
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9220000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9220000000L, 0L, "");
		
		//Click the Add Physical Therapy Office Visit link
		driver.findElement(By.linkText("Add PhysicalTherapy Office Visit")).click();
		
		//Select Brody Franco as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("407");
		driver.findElement(By.xpath("//input[@value='407']")).submit();
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
		assertLogged(TransactionType.CREATE_PHYSICALTHERAPY_OV, 9220000000L, 407L, "");
		
		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/14/2015"));
	}
	
	public void testGenericHCPCreatePhisycalTherapyOfficeVisit() throws Exception{
		//Set up the outcome of Scenario 1
		gen.physicalTherapyScenario1();
		
		//Login as Kelly Doctor
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Add PhysicalTherapy Office Visit")).click();
		
		//Select Brody Franco as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("407");
		driver.findElement(By.xpath("//input[@value='407']")).submit();
		
		//Verify that we got redirected to the regular office visit page
		String title = driver.getTitle();
		assertEquals("iTrust - Document Office Visit", title);

		//Click the PhisycalTherapy Home link
		driver.findElement(By.linkText("PhysicalTherapy Home")).click();
		
		//Verify that we are returned to the PhisycalTherapy Homepage
		title = driver.getTitle();
		assertEquals("iTrust - PhysicalTherapy", title);
		
		//Click the proper office visit link
		driver.findElement(By.linkText("10/15/2015")).click();
		
		//Verify that the action was logged
		assertLogged(TransactionType.VIEW_PHYSICALTHERAPY_OV, 9000000000L, 407L, "");
		
		//Verify that the Office Visit is shown
		assertTrue(driver.getPageSource().contains("10/15/2015"));
	}
	
	public void testEditPhisycalTherapyOfficeVisit() throws Exception{
		//Set up the outcome of Scenario
		gen.physicalTherapyScenario1();
		
		//Login as Brooke Tran
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9220000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9220000000L, 0L, "");

		//Click the PhisycalTherapy Home link
		driver.findElement(By.linkText("PhysicalTherapy Home")).click();

		//Select Freya Chandler as the patient
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("407");
		driver.findElement(By.xpath("//input[@value='407']")).submit();
		String title = driver.getTitle();
		assertEquals("iTrust - PhysicalTherapy", title);
		
		//Click the proper office visit link
		driver.findElement(By.linkText("10/15/2015")).click();
		
		//Fill in the correct values and submit the form
		driver.findElement(By.name("wellnessSurveyResult0")).clear();
		driver.findElement(By.name("wellnessSurveyResult0")).sendKeys("100");
		driver.findElement(By.id("submit")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Verify that we are returned to the PhisycalTherapy Homepage
		title = driver.getTitle();
		assertEquals("iTrust - PhysicalTherapy", title);
		assertTrue(driver.getPageSource().contains("PhysicalTherapy Office Visit successfully edited"));

		//Verify that the action was logged
		assertLogged(TransactionType.EDIT_PHYSICALTHERAPY_OV, 9220000000L, 407L, "");

		//Verify that the newly created office visit is present in the Prior Office Visits list
		assertTrue(driver.getPageSource().contains("10/15/2015"));
	}
	
	
}

package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Selenium test cases for the Orthopedic Office Visit Request feature in UC88.
 */
public class OrthopedicScheduleOVTest  extends iTrustSeleniumTest{

   /**
    * Sets up the required base data needed for each test case. This includes two HCP's and two patients.
    */
   protected void setUp() throws Exception{
      super.setUp();
      gen.clearAllTables();

      gen.patient27();
      gen.patient29();
      
   //   gen.hcp9();
      gen.uc92();
   //   gen.hcp921();
      gen.hcp922();
   }
   
   /**
    * Clears the database after each test.
    */
   protected void tearDown() throws Exception {
      gen.clearAllTables();
   }
   
   /**
    * Selenium test for UC88 Acceptance Scenario 1.
    * @throws Exception
    */
   public void testCreateOrthopedicOfficeVisitRequest() throws Exception{
      //Login as Brody Franco
      HtmlUnitDriver driver = (HtmlUnitDriver)login("407", "pw");
      assertLogged(TransactionType.HOME_VIEW, 407L, 0L, "");
      
      // Create the explicit wait
      WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
      
      //Click the Add Orthopedic Office Visit Request link
      driver.findElement(By.linkText("Add Orthopedic Office Visit Request")).click();
      wait.until(ExpectedConditions.titleIs("iTrust - Add Orthopedic Office Visit Request"));
      
      //fill form
      Select select;
      select = new Select (driver.findElement(By.name("time1")));
      select.selectByValue("03");
      select = new Select (driver.findElement(By.name("time2")));
      select.selectByValue("00");
      select = new Select (driver.findElement(By.name("time3")));
      select.selectByValue("PM");
      select = new Select (driver.findElement(By.name("lhcp")));
      select.selectByValue("9220000000");
      driver.findElement(By.name("startDate")).clear();
      driver.findElement(By.name("startDate")).sendKeys("12/20/2015");
      driver.findElement(By.name("comment")).clear();
      driver.findElement(By.name("comment")).sendKeys("My arms hurt");
      driver.findElement(By.name("startDate")).submit();
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      
      //Verify that the form was accepted
      wait.until(ExpectedConditions.titleIs("iTrust - Add Orthopedic Office Visit Request"));
      assertTrue(driver.getPageSource().contains("Your Orthopedic Office Visit Request has been saved and is pending."));
      
      //Click the View Scheduled Orthopedic Office Visits link
      driver.findElement(By.linkText("View Scheduled Orthopedic Office Visits")).click();
      wait.until(ExpectedConditions.titleIs("iTrust - View Scheduled Orthopedic Office Visits"));
      
      
      //Verify the info in the table
      assertTrue(driver.getPageSource().contains("Momsen doctor"));
      assertTrue(driver.getPageSource().contains("12/20/2015 03:00 PM"));
      assertTrue(driver.getPageSource().contains("Pending"));
      //Click the Read Comment link
      driver.findElement(By.linkText("Read Comment")).click();
      wait.until(ExpectedConditions.titleIs("iTrust - View Scheduled Orthopedic Office Visit"));
      //Verify the information is present
      assertTrue(driver.getPageSource().contains("Momsen doctor"));
      assertTrue(driver.getPageSource().contains("12/20/2015 03:00 PM"));
      assertTrue(driver.getPageSource().contains("Pending"));
      assertTrue(driver.getPageSource().contains("My arms hurt"));
   }
   
   /**
    * Selenium test for UC88 Acceptance Scenario 2.
    * @throws Exception
    */
   public void testApproveOrthopedicOfficeVisitRequest() throws Exception{
      //Set up the outcome of Scenario 1
      gen.OrthopedicScenario4();
      
      //Login as Momsen doctor
      HtmlUnitDriver driver = (HtmlUnitDriver)login("9220000000", "pw");
      assertLogged(TransactionType.HOME_VIEW, 9220000000L, 0L, "");
      
      // Create the explicit wait
      WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
      
      //Click the View Orthopedic Office Visit Requests link
      driver.findElement(By.linkText("View Orthopedic Office Visit Requests")).click();
      wait.until(ExpectedConditions.titleIs("iTrust - Scheduled Orthopedic Office Visit Requests"));
      
      //Verify the information is present
      assertTrue(driver.getPageSource().contains("Request from: Brody Franco"));
      assertTrue(driver.getPageSource().contains("12/20/2016 03:00 PM"));
      assertTrue(driver.getPageSource().contains("My arms hurt"));
      //Click the Accept link
      driver.findElement(By.linkText("Accept")).click();
      assertTrue(driver.getPageSource().contains("The Orthopedic Office Visit Request has been accepted."));
      assertTrue(driver.getPageSource().contains("Accepted"));
      
      //Click the View Scheduled Orthopedic Office Visits link
      driver.findElement(By.linkText("View Scheduled Orthopedic Office Visits")).click();
      wait.until(ExpectedConditions.titleIs("iTrust - View Scheduled Orthopedic Office Visits"));
      
      //Verify that the info is in the table
      assertTrue(driver.getPageSource().contains("Brody Franco"));
      assertTrue(driver.getPageSource().contains("12/20/2016 03:00 PM"));
      assertTrue(driver.getPageSource().contains("Accepted"));
      //Click the Read Comment link
      driver.findElement(By.linkText("Read Comment")).click();
      wait.until(ExpectedConditions.titleIs("iTrust - View Scheduled Orthopedic Office Visit"));
      //Verify the information is present
      assertTrue(driver.getPageSource().contains("Brody Franco"));
      assertTrue(driver.getPageSource().contains("12/20/2016 03:00 PM"));
      assertTrue(driver.getPageSource().contains("Accepted"));
      assertTrue(driver.getPageSource().contains("My arms hurt"));
      
      //Then logout
      driver.get("http://localhost:8080/iTrust/logout.jsp");
      assertEquals("iTrust - Login", driver.getTitle());
      
      //Login as Brody Franco
      driver = (HtmlUnitDriver)login("407", "pw");
      assertLogged(TransactionType.HOME_VIEW, 407L, 0L, "");
      wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
      wait.until(ExpectedConditions.titleIs("iTrust - Patient Home"));
      
      //Click the View Scheduled Orthopedic Office Visits link
      driver.findElement(By.linkText("View Scheduled Orthopedic Office Visits")).click();
      wait.until(ExpectedConditions.titleIs("iTrust - View Scheduled Orthopedic Office Visits"));
      
      //Verify the info in the table
      assertTrue(driver.getPageSource().contains("Momsen doctor"));
      assertTrue(driver.getPageSource().contains("12/20/2016 03:00 PM"));
      assertTrue(driver.getPageSource().contains("Accepted"));
      //Click the Read Comment link
      driver.findElement(By.linkText("Read Comment")).click();
      wait.until(ExpectedConditions.titleIs("iTrust - View Scheduled Orthopedic Office Visit"));
      //Verify the information is present
      assertTrue(driver.getPageSource().contains("Momsen doctor"));
      assertTrue(driver.getPageSource().contains("12/20/2016 03:00 PM"));
      assertTrue(driver.getPageSource().contains("Accepted"));
      assertTrue(driver.getPageSource().contains("My arms hurt"));
      
   }
      
   /**
    * Selenium test for UC88 Acceptance Scenario 3.
    * @throws Exception
    */
   public void testRejectOrthopedicOfficeVisitRequest() throws Exception{
      //Set up the outcome of Scenario 1
      gen.OrthopedicScenario4();
      
      //Login as Momsen doctor
      HtmlUnitDriver driver = (HtmlUnitDriver)login("9220000000", "pw");
      assertLogged(TransactionType.HOME_VIEW, 9220000000L, 0L, "");
      
      // Create the explicit wait
      WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
      
      //Click the View Orthopedic Office Visit Requests link
      driver.findElement(By.linkText("View Orthopedic Office Visit Requests")).click();
      wait.until(ExpectedConditions.titleIs("iTrust - Scheduled Orthopedic Office Visit Requests"));
      
      //Verify the information is present
      assertTrue(driver.getPageSource().contains("Request from: Brody Franco"));
      assertTrue(driver.getPageSource().contains("12/20/2016 03:00 PM"));
      assertTrue(driver.getPageSource().contains("My arms hurt"));
      //Click the Reject link
      driver.findElement(By.linkText("Reject")).click();
      assertTrue(driver.getPageSource().contains("The Orthopedic Office Visit Request has been rejected."));
      assertTrue(driver.getPageSource().contains("Rejected"));
      
      //Click the View Scheduled Orthopedic Office Visits link
      driver.findElement(By.linkText("View Scheduled Orthopedic Office Visits")).click();
      wait.until(ExpectedConditions.titleIs("iTrust - View Scheduled Orthopedic Office Visits"));
      
      //Verify that the info is in the table
      assertTrue(driver.getPageSource().contains("Brody Franco"));
      assertTrue(driver.getPageSource().contains("12/20/2016 03:00 PM"));
      assertTrue(driver.getPageSource().contains("Rejected"));
      //Click the Read Comment link
      driver.findElement(By.linkText("Read Comment")).click();
      wait.until(ExpectedConditions.titleIs("iTrust - View Scheduled Orthopedic Office Visit"));
      //Verify the information is present
      assertTrue(driver.getPageSource().contains("Brody Franco"));
      assertTrue(driver.getPageSource().contains("12/20/2016 03:00 PM"));
      assertTrue(driver.getPageSource().contains("Rejected"));
      assertTrue(driver.getPageSource().contains("My arms hurt"));
      
      //Then logout
      driver.get("http://localhost:8080/iTrust/logout.jsp");
      assertEquals("iTrust - Login", driver.getTitle());
      
      //Login as Brody Franco
      driver = (HtmlUnitDriver)login("407", "pw");
      assertLogged(TransactionType.HOME_VIEW, 407L, 0L, "");
      wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
      wait.until(ExpectedConditions.titleIs("iTrust - Patient Home"));
      
      //Click the View Scheduled Orthopedic Office Visits link
      driver.findElement(By.linkText("View Scheduled Orthopedic Office Visits")).click();
      wait.until(ExpectedConditions.titleIs("iTrust - View Scheduled Orthopedic Office Visits"));
      
      //Verify the info in the table
      assertTrue(driver.getPageSource().contains("Momsen doctor"));
      assertTrue(driver.getPageSource().contains("12/20/2016 03:00 PM"));
      assertTrue(driver.getPageSource().contains("Rejected"));
      //Click the Read Comment link
      driver.findElement(By.linkText("Read Comment")).click();
      wait.until(ExpectedConditions.titleIs("iTrust - View Scheduled Orthopedic Office Visit"));
      //Verify the information is present
      assertTrue(driver.getPageSource().contains("Momsen doctor"));
      assertTrue(driver.getPageSource().contains("12/20/2016 03:00 PM"));
      assertTrue(driver.getPageSource().contains("Rejected"));
      assertTrue(driver.getPageSource().contains("My arms hurt"));
      
   }
   
}
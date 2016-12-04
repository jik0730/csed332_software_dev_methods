package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;

public class UC93SeleniumTest  extends iTrustSeleniumTest{

   WebDriver driver;
   WebElement element;
   
   @Override
   protected void setUp() throws Exception {
      super.setUp(); 
      gen.clearAllTables();
      gen.standardData();  
      gen.wardhcp923();
      gen.wardtestdata();
   }
   
   public void testRequestAndAccept() throws Exception {
      // login as "Random Person"
      driver = login("2", "pw"); 
      assertEquals("iTrust - Patient Home", driver.getTitle());
       
      // find element of ward room change request
      element = driver.findElement(By.linkText("Ward Room Change Request"));
      element.click();
      assertEquals("iTrust - Room Change Request", driver.getTitle());
      
      
      element = driver.findElements(By.name("requestRoomChange")).get(0);
      
      element.click();
      
      //log-out and log-in as doctor
      driver.close();
      driver = login("9230000000", "pw");
      assertEquals("iTrust - HCP Home", driver.getTitle());
    
      //find element appointment request
      element = driver.findElement(By.linkText("View Ward Room Change Requests"));
      element.click();
      assertEquals("iTrust - View Wards Change Request", driver.getTitle());
    
      
      element = driver.findElement(By.name("assignPatient"));
      element.click();
/*
      element = driver.findElement(By.name("removePatient"));
      element.click();
  */    
      //logout and login as patient
      driver.close();
      driver = login("2", "pw"); 
      assertEquals("iTrust - Patient Home", driver.getTitle());
      
      element = driver.findElement(By.linkText("Ward Room Change Request"));
      element.click();
      
      
      
      WebElement table = driver.findElements(By.className("fTable")).get(1);
      List<WebElement> list = table.findElements(By.xpath("tbody/tr"));
      
      
      assertEquals("WardTest22ElderlyClean402", list.get(2).getText());

      
   }
   
   public void testRequestAndReject() throws Exception {
 
      driver = login("2", "pw"); 
      assertEquals("iTrust - Patient Home", driver.getTitle());
      
      // find element of ward room change request
      element = driver.findElement(By.linkText("Ward Room Change Request"));
      element.click();
      assertEquals("iTrust - Room Change Request", driver.getTitle());
      

      element = driver.findElements(By.name("requestRoomChange")).get(0);
      
      element.click();;
   
      
      //log-out and log-in as physical therapist
      driver.close();
      driver = login("9230000000", "pw");
      assertEquals("iTrust - HCP Home", driver.getTitle());
      
      //find element appointment request
      element = driver.findElement(By.linkText("View Ward Room Change Requests"));
      element.click();
      assertEquals("iTrust - View Wards Change Request", driver.getTitle());
      
      element = driver.findElement(By.name("removePatient"));
      element.click();
      
      //logout and login as patient
      driver.close();
      driver = login("2", "pw"); 
      assertEquals("iTrust - Patient Home", driver.getTitle());
      
      element = driver.findElement(By.linkText("Ward Room Change Request"));
      element.click();
      
      
      WebElement table = driver.findElements(By.className("fTable")).get(1);
      List<WebElement> list = table.findElements(By.xpath("tbody/tr"));
      
      
      assertEquals("WardTest12ElderlyClean501", list.get(2).getText());
      
 
   }
   
  
}
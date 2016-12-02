package edu.ncsu.csc.itrust.selenium;

import java.util.ArrayList;
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

   }
   
   public void testRequestAndAccept() throws Exception {
      // login as "Random Person"
      driver = login("1", "pw"); 
      assertEquals("iTrust - Patient Home", driver.getTitle());
      
      // find element of ward room change request
      element = driver.findElement(By.linkText("Ward Room Change Request"));
      element.click();
      assertEquals("iTrust - Room Change Request", driver.getTitle());
      

      WebElement table = driver.findElements(By.className("fTable")).get(2);
      List<WebElement> list = table.findElements(By.xpath("tbody/tr/td"));
      List<WebElement> values = new ArrayList<WebElement>();   
      
      for(int i = 1; i < list.size(); i += 1) {
         values.add(list.get(i));
      }
      
      element = values.get(0).findElement(By.linkText("Request"));
   
      
      //log-out and log-in as physical therapist
      driver.close();
      driver = login("9000000000", "pw");
      assertEquals("iTrust - HCP Home", driver.getTitle());
      
      //find element appointment request
      element = driver.findElement(By.linkText("View Ward Room Change Requests"));
      element.click();
      assertEquals("iTrust - View Wards Change Request", driver.getTitle());
      
      table = driver.findElements(By.className("fTable")).get(0);
      list = table.findElements(By.xpath("tbody/tr/td"));
      values = new ArrayList<WebElement>();   
      
      for(int i = 1; i < list.size(); i += 1) {
         values.add(list.get(i));
      }
      
      element = values.get(0).findElement(By.linkText("Accept"));      
      element.click();
      element = values.get(1).findElement(By.linkText("Reject"));      
      element.click();
      element = values.get(2).findElement(By.linkText("Reject"));      
      element.click();
      
      //logout and login as patient
      driver.close();
      driver = login("1", "pw"); 
      assertEquals("iTrust - Patient Home", driver.getTitle());
      
      element = driver.findElement(By.linkText("Ward Room Change Request"));
      element.click();
      
      table = driver.findElements(By.className("fTable")).get(1);
      list = table.findElements(By.xpath("tbody/tr/td"));
      values = new ArrayList<WebElement>();   
      
      for(int i = 1; i < list.size(); i += 1) {
         values.add(list.get(i));
      }
      
      element = values.get(0).findElement(By.linkText("TEST"));
      
      assertEquals("TEST", element.getText());
      

   }
   
   public void testRequestAndReject() throws Exception {
      // login as "Random Person"
      driver = login("1", "pw"); 
      assertEquals("iTrust - Patient Home", driver.getTitle());
      
      // find element of ward room change request
      element = driver.findElement(By.linkText("Ward Room Change Request"));
      element.click();
      assertEquals("iTrust - Room Change Request", driver.getTitle());
      

      WebElement table = driver.findElements(By.className("fTable")).get(2);
      List<WebElement> list = table.findElements(By.xpath("tbody/tr/td"));
      List<WebElement> values = new ArrayList<WebElement>();   
      
      for(int i = 1; i < list.size(); i += 1) {
         values.add(list.get(i));
      }
      
      element = values.get(0).findElement(By.linkText("Request"));
   
      
      //log-out and log-in as physical therapist
      driver.close();
      driver = login("9000000000", "pw");
      assertEquals("iTrust - HCP Home", driver.getTitle());
      
      //find element appointment request
      element = driver.findElement(By.linkText("View Ward Room Change Requests"));
      element.click();
      assertEquals("iTrust - View Wards Change Request", driver.getTitle());
      
      table = driver.findElements(By.className("fTable")).get(0);
      list = table.findElements(By.xpath("tbody/tr/td"));
      values = new ArrayList<WebElement>();   
      
      for(int i = 1; i < list.size(); i += 1) {
         values.add(list.get(i));
      }
      
      element = values.get(0).findElement(By.linkText("Reject"));      
      element.click();
      element = values.get(1).findElement(By.linkText("Reject"));      
      element.click();
      element = values.get(2).findElement(By.linkText("Reject"));      
      element.click();
      
      //logout and login as patient
      driver.close();
      driver = login("1", "pw"); 
      assertEquals("iTrust - Patient Home", driver.getTitle());
      
      element = driver.findElement(By.linkText("Ward Room Change Request"));
      element.click();
      
      table = driver.findElements(By.className("fTable")).get(1);
      list = table.findElements(By.xpath("tbody/tr/td"));
      values = new ArrayList<WebElement>();   
      
      for(int i = 1; i < list.size(); i += 1) {
         values.add(list.get(i));
      }
      
      element = values.get(0).findElement(By.linkText("Lolita"));
      
      assertEquals("Lolita", element.getText());
      

   }
   
   
}

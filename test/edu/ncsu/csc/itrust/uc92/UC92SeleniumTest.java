package edu.ncsu.csc.itrust.uc92;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;

public class UC92SeleniumTest  extends iTrustSeleniumTest{

	WebDriver driver;
	WebElement element;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp(); 
		gen.clearAllTables();
		gen.standardData();
		gen.hcp9();
		gen.apptRequestConflicts();
	}
	
	public void testRequestAndAccept() throws Exception {
		// login as "Random Person"
		driver = login("1", "pw"); 
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		// find element of Appointment Requests
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		
		// fill form
		Select select;
		select = new Select (driver.findElement(By.name("apptType")));
		select.selectByValue("Orthopedic");
		select = new Select (driver.findElement(By.name("time1")));
		select.selectByValue("11");
		select = new Select (driver.findElement(By.name("time2")));
		select.selectByValue("00");
		select = new Select (driver.findElement(By.name("time3")));
		select.selectByValue("AM");
		select = new Select (driver.findElement(By.name("lhcp")));
		select.selectByValue("9220000000");
		element = driver.findElement(By.name("startDate"));
		element.clear();
		element.sendKeys("1/12/2016");
		element.submit();
		
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Your appointment request has been saved and is pending."));
		
		//find element of View My Requests
		element = driver.findElement(By.linkText("View My Requests"));
		element.click();
		assertEquals("iTrust - View My Requests", driver.getTitle());
		//find there exists new request with status is pending
			//Not Implemented of Interface of View My Requests
		
		//log-out and log-in as physical therapist
		driver.close();
		driver = login("9220000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		//find element appointment request
		element = driver.findElement(By.linkText("Appointment Requests"));
		element.click();
		assertEquals("iTrust - View My Appointment Requests", driver.getTitle());
		
		//check there exists request from random person
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Request from: Random Person"));
		
		//Accept request
		driver.findElement(By.linkText("Approve")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains
				("The appointment request you selected has been accepted and scheduled."));
		
		//Check appointment
		element = driver.findElement(By.linkText("View My Appointments"));
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Random Person"));
		
		//logout and login as patient
		driver.close();
		driver = login("1", "pw"); 
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//check request result from appointment page
		element = driver.findElement(By.linkText("View My Appointments"));
		element.click();
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Momsen doctor"));
		
		//check request result from request view page
		element = driver.findElement(By.linkText("View My Requests"));
			//Not yet implemented
		
	}
	
	public void testReqeustAndReject() throws Exception {
		// login as "Random Person"
				driver = login("26", "pw"); 
				assertEquals("iTrust - Patient Home", driver.getTitle());
				
				// find element of Appointment Requests
				element = driver.findElement(By.linkText("Appointment Requests"));
				element.click();
				assertEquals("iTrust - Appointment Requests", driver.getTitle());
				
				// fill form
				Select select;
				select = new Select (driver.findElement(By.name("apptType")));
				select.selectByValue("Orthopedic");
				select = new Select (driver.findElement(By.name("time1")));
				select.selectByValue("1");
				select = new Select (driver.findElement(By.name("time2")));
				select.selectByValue("00");
				select = new Select (driver.findElement(By.name("time3")));
				select.selectByValue("PM");
				select = new Select (driver.findElement(By.name("lhcp")));
				select.selectByValue("9220000000");
				element = driver.findElement(By.name("startDate"));
				element.clear();
				element.sendKeys("1/12/2016");
				element.submit();
				
				assertEquals("iTrust - Appointment Requests", driver.getTitle());
				assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Your appointment request has been saved and is pending."));
				
				//find element of View My Requests
				element = driver.findElement(By.linkText("View My Requests"));
				element.click();
				assertEquals("iTrust - View My Requests", driver.getTitle());
				//find there exists new request with status is pending
					//Not Implemented of Interface of View My Requests
				
				//log-out and log-in as physical therapist
				driver.close();
				driver = login("9220000000", "pw");
				assertEquals("iTrust - HCP Home", driver.getTitle());
				
				//find element appointment request
				element = driver.findElement(By.linkText("Appointment Requests"));
				element.click();
				assertEquals("iTrust - View My Appointment Requests", driver.getTitle());
				
				//check there exists request from random person
				assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains("Request from: Random Person"));
				
				//Accept request
				driver.findElement(By.linkText("Reject")).click();
				assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']")).getText().contains
						("The appointment request you selected has been rejected."));
				
				//logout and login as patient
				driver.close();
				driver = login("26", "pw"); 
				assertEquals("iTrust - Patient Home", driver.getTitle());
				
				//check request result from request view page
				element = driver.findElement(By.linkText("View My Requests"));
					//Not yet implemented
	}
	
}
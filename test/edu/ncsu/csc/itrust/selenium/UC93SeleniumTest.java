package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;

public class UC93SeleniumTest extends iTrustSeleniumTest {

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

	/**
	 * testRequestAndReceive AND testAcceptRequest combined
	 * 
	 * @throws Exception
	 */
	public void testRequestAndReceiveAndtestAcceptRequest() throws Exception {
		// login as "Random Person"
		driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());

		// find element of ward room change request
		element = driver.findElement(By.linkText("Ward Room Change Request"));
		element.click();
		assertEquals("iTrust - Room Change Request", driver.getTitle());

		element = driver.findElements(By.name("requestRoomChange")).get(0);

		element.click();

		// log-out and log-in as doctor
		driver.close();
		driver = login("9230000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());

		// find element appointment request
		element = driver.findElement(By.linkText("View Ward Room Change Requests"));
		element.click();
		assertEquals("iTrust - View Wards Change Request", driver.getTitle());

		element = driver.findElement(By.name("assignPatient"));
		element.click();

		// logout and login as patient
		driver.close();
		driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());

		element = driver.findElement(By.linkText("Ward Room Change Request"));
		element.click();

		WebElement table = driver.findElements(By.className("fTable")).get(1);
		List<WebElement> list = table.findElements(By.xpath("tbody/tr"));

		assertEquals("WardTest22ElderlyClean402", list.get(2).getText());

	}

	/**
	 * testRequestAndReceive AND testRejectRequest combined
	 * 
	 * @throws Exception
	 */
	public void testRequestAndReceiveAndtestRejectRequest() throws Exception {
		driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());

		// find element of ward room change request
		element = driver.findElement(By.linkText("Ward Room Change Request"));
		element.click();
		assertEquals("iTrust - Room Change Request", driver.getTitle());

		element = driver.findElements(By.name("requestRoomChange")).get(0);
		element.click();

		// log-out and log-in as physical therapist
		driver.close();
		driver = login("9230000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());

		// find element appointment request
		element = driver.findElement(By.linkText("View Ward Room Change Requests"));
		element.click();
		assertEquals("iTrust - View Wards Change Request", driver.getTitle());

		element = driver.findElement(By.name("removePatient"));
		element.click();

		// logout and login as patient
		driver.close();
		driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());

		element = driver.findElement(By.linkText("Ward Room Change Request"));
		element.click();

		WebElement table = driver.findElements(By.className("fTable")).get(1);
		List<WebElement> list = table.findElements(By.xpath("tbody/tr"));

		assertEquals("WardTest12ElderlyClean501", list.get(2).getText());

	}

	/**
	 * testViewSystemRecommendedRooms
	 * 
	 * @throws Exception
	 */
	public void testViewSystemRecommendedRooms() throws Exception {
		// login as "Random Person"
		driver = login("1", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());

		// find element of ward room change request
		element = driver.findElement(By.linkText("Ward Room Change Request"));
		element.click();
		assertEquals("iTrust - Room Change Request", driver.getTitle());

		// find system recommended table
		WebElement table = driver.findElements(By.className("fTable")).get(2);
		List<WebElement> list = table.findElements(By.xpath("tbody/tr"));
		assertEquals(4, list.size());
	}

	/**
	 * testViewPatientChoosenRooms
	 * 
	 * @throws Exception
	 */
	public void testViewPatientChoosenRooms() throws Exception {
		// login as "Random Person"
		driver = login("1", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());

		// find element of ward room change request
		element = driver.findElement(By.linkText("Ward Room Change Request"));
		element.click();
		assertEquals("iTrust - Room Change Request", driver.getTitle());

		// select price
		WebElement tableSelect = driver.findElements(By.className("fTable")).get(0);
		List<WebElement> selects = tableSelect.findElements(By.xpath("tbody/tr/td/select"));
		selects.get(0).click();
		selects.get(0).findElements(By.xpath("option")).get(2).click();

		// select size
		selects.get(1).click();
		selects.get(1).findElements(By.xpath("option")).get(3).click();

		// search
		element = driver.findElement(By.name("searchRooms"));
		element.click();

		// find results
		WebElement table = driver.findElements(By.className("fTable")).get(2);
		List<WebElement> list = table.findElements(By.xpath("tbody/tr"));
		assertEquals(4, list.size());
	}

	/**
	 * testViewAllRequestsByPatient
	 * 
	 * @throws Exception
	 */
	public void testViewAllRequestsByPatient() throws Exception {
		// login as "HCP"
		driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());

		// find element of ward room change requests
		element = driver.findElement(By.linkText("View Ward Room Change Requests"));
		element.click();
		assertEquals("iTrust - View Wards Change Request", driver.getTitle());

		// find system recommended table
		WebElement table = driver.findElements(By.className("fTable")).get(0);
		List<WebElement> list = table.findElements(By.xpath("tbody/tr"));
		assertEquals(5, list.size());
	}

	/**
	 * testViewAlreadyAssignedRoom
	 * 
	 * @throws Exception
	 */
	public void testViewAlreadyAssignedRoom() throws Exception {
		// login as "Random Person"
		driver = login("1", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());

		// find element of ward room change request
		element = driver.findElement(By.linkText("Ward Room Change Request"));
		element.click();
		assertEquals("iTrust - Room Change Request", driver.getTitle());

		// find system recommended table
		WebElement table = driver.findElements(By.className("fTable")).get(1);
		List<WebElement> list = table.findElements(By.xpath("tbody/tr"));
		assertEquals(3, list.size());

		// its room name
		WebElement roomName = table.findElements(By.xpath("tbody/tr/td")).get(6);
		assertEquals("Lolita", roomName.getText());
	}
}
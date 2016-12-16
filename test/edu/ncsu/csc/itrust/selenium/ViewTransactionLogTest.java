package edu.ncsu.csc.itrust.selenium;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.meterware.httpunit.HttpUnitOptions;

import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

/**
 * Test class for the viewTransactionLogs.jsp
 */
public class ViewTransactionLogTest extends iTrustSeleniumTest {

	private HtmlUnitDriver driver;
	Date start;
	Date end;
	Date check;
	SimpleDateFormat fmt;

	/**
	 * set up testViewTransactionLog This is for setting up this test
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		HttpUnitOptions.setScriptingEnabled(false);
		fmt = new SimpleDateFormat("yyyy-MM-dd");
		start = fmt.parse("2007-06-22");
		end = fmt.parse("2007-06-22");
		check = fmt.parse("2007-06-23");
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.transactionLog();
	}

	public void test1() throws Exception {

	}

	/**
	 * testViewTransactionLog1 This is for checking list of Admin
	 */
	public void testViewTransactionLogAdminList1() throws Exception {
		driver = (HtmlUnitDriver) login("9000000001", "pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("View Transaction Logs")).click();
		System.out.println(driver.getTitle());
		assertEquals("iTrust - View Transaction Logs", driver.getTitle());
		driver.findElement(By.xpath("//input[@name='ViewButton']")).click();
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(dt).toString().substring(0, 10);
		TableElement table = new TableElement(driver.findElement(By.id("list_table")));
		int i;
		int size = table.getRowSize();
		String s;
		for (i = 1; i < size - 1; i++) {
			s = table.getCellAsText(i, 1).substring(0, 10);
			assertEquals(today, s);
		}
	}

	/**
	 * testViewTransactionLog1 This is for checking list of Admin
	 */
	public void testViewTransactionLogAdminList2() throws Exception {
		driver = (HtmlUnitDriver) login("9000000001", "pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("View Transaction Logs")).click();
		System.out.println(driver.getTitle());
		assertEquals("iTrust - View Transaction Logs", driver.getTitle());
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/22/2007");
		driver.findElement(By.name("endDate")).sendKeys("06/22/2007");
		driver.findElement(By.name("ViewButton")).submit();
		TableElement table = new TableElement(driver.findElement(By.id("list_table")));
		int i;
		int size = table.getRowSize();
		String s;
		for (i = 1; i < size - 1; i++) {
			s = table.getCellAsText(i, 1).substring(0, 10);
			if(fmt.parse(s).getTime() >= start.getTime() && fmt.parse(s).getTime() < check.getTime()){
			} else {
				fail();
			}
		}
	}

	/**
	 * testViewTransactionLogAdminGraph this is for checking graph of Admin
	 */
	public void testViewTransactionLogAdminGraph() throws Exception {
		driver = (HtmlUnitDriver) login("9000000001", "pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("View Transaction Logs")).click();
		assertEquals("iTrust - View Transaction Logs", driver.getTitle());
		driver.findElement(By.xpath("//input[@name='Graph']")).click();
		assertNotNull(driver.findElement(By.id("chart_div")));
		assertNotNull(driver.findElement(By.id("chart_div2")));
		assertNotNull(driver.findElement(By.id("chart_div3")));
		assertNotNull(driver.findElement(By.id("chart_div4")));
	}

	/**
	 * testViewTransactionLogTesterList1 This is for checking list of Tester
	 */
	public void testViewTransactionLogTesterList1() throws Exception {
		driver = (HtmlUnitDriver) login("9999999999", "pw");
		driver.findElement(By.id("view-menu")).click();
		driver.findElement(By.linkText("View Transaction Logs")).click();
		assertEquals("iTrust - View Transaction Logs", driver.getTitle());
		driver.findElement(By.xpath("//input[@name='view']")).click();
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(dt).toString().substring(0, 10);
		TableElement table = new TableElement(driver.findElement(By.id("list_table")));
		int i;
		int size = table.getRowSize();
		String s;
		for (i = 1; i < size - 1; i++) {
			s = table.getCellAsText(i, 1).substring(0, 10);
			assertEquals(today, s);
		}
	}

	/**
	 * testViewTransactionLogTesterList2 This is for checking list of Tester
	 */
	public void testViewTransactionLogTesterList2() throws Exception {
		driver = (HtmlUnitDriver) login("9999999999", "pw");
		driver.findElement(By.id("view-menu")).click();
		driver.findElement(By.linkText("View Transaction Logs")).click();
		assertEquals("iTrust - View Transaction Logs", driver.getTitle());
		driver.findElement(By.xpath("//input[@name='view']")).click();
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/22/2007");
		driver.findElement(By.name("endDate")).sendKeys("06/22/2007");
		driver.findElement(By.name("view")).click();
		TableElement table = new TableElement(driver.findElement(By.id("list_table")));
		int i;
		int size = table.getRowSize();
		String s;
		for (i = 1; i < size - 1; i++) {
			s = table.getCellAsText(i, 1).substring(0, 10);
			if(fmt.parse(s).getTime() >= start.getTime() && fmt.parse(s).getTime() < check.getTime()){
			} else {
				fail();
			}
		}
	}

	/**
	 * testViewTransactionLogTesterGraph this is for checking graph of Tester
	 */
	public void testViewTransactionLogTesterGraph() throws Exception {
		driver = (HtmlUnitDriver) login("9999999999", "pw");
		driver.findElement(By.id("view-menu")).click();
		driver.findElement(By.linkText("View Transaction Logs")).click();
		assertEquals("iTrust - View Transaction Logs", driver.getTitle());
		driver.findElement(By.xpath("//input[@name='graph']")).click();
		assertNotNull(driver.findElement(By.id("chart_div")));
		assertNotNull(driver.findElement(By.id("chart_div2")));
		assertNotNull(driver.findElement(By.id("chart_div3")));
		assertNotNull(driver.findElement(By.id("chart_div4")));
	}

	/**
	 * TableElement a helper class for Selenium test htmlunitdriver retrieving
	 * data from tables.
	 */
	private class TableElement {
		WebElement tableElement;
		List<List<WebElement>> table;

		/**
		 * Constructor. This object will help user to get data from each cell of
		 * the table.
		 * 
		 * @param tableElement
		 *            The table WebElement.
		 */
		public TableElement(WebElement tableElement) {
			this.tableElement = tableElement;
			table = new ArrayList<List<WebElement>>();
			List<WebElement> trCollection = tableElement.findElements(By.xpath("tbody/tr"));
			for (WebElement trElement : trCollection) {
				List<WebElement> tdCollection = trElement.findElements(By.xpath("td"));
				table.add(tdCollection);
			}

		}

		/**
		 * Get data from given row and column cell.
		 * 
		 * @param row
		 *            (start from 0)
		 * @param column(start
		 *            from 0)
		 * @return The WebElement in that given cell.
		 */
		public String getCellAsText(int row, int column) {
			return table.get(row).get(column).getText();
		}

		public int getRowSize() {
			return tableElement.findElements(By.xpath("tbody/tr")).size();
		}

	}

}
package edu.ncsu.csc.itrust.unit.serverutils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.server.ImageDataServlet;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

/**
 * ImageDataServletTest
 */
public class ImageDataServletTest {

	TestDataGenerator gen = new TestDataGenerator();

	/**
	 * Setup
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.uc88();
	}

	@Test
	public void test() {
		ImageDataServlet servlet = new ImageDataServlet();
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getParameter("table")).thenReturn("orthopedic");
		when(req.getParameter("col")).thenReturn("XRay");
		when(req.getParameter("pri")).thenReturn("OrthopedicVisitID");
		when(req.getParameter("key")).thenReturn("1");
	}

}

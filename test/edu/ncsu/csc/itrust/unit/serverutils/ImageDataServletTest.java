package edu.ncsu.csc.itrust.unit.serverutils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.ncsu.csc.itrust.server.ImageDataServlet;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

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
		gen.imageTest();
	}

	/**
	 * Test Not Found
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test(expected=NullPointerException.class)
	public void testNotFound() throws IOException, ServletException {
	    Delegator test = new Delegator();
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse r = mock(HttpServletResponse.class);
		when(req.getParameter("table")).thenReturn("orthopedic");
		when(req.getParameter("col")).thenReturn("XRay");
		when(req.getParameter("pri")).thenReturn("OrthopedicVisitID");
		when(req.getParameter("key")).thenReturn("1");
		test.testDoGet(r, req);
	}
	
	/**
	 * Test Null Input
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testNull() throws IOException, ServletException {
	    Delegator test = new Delegator();
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse r = mock(HttpServletResponse.class);
		when(req.getParameter("table")).thenReturn(null);
		when(req.getParameter("col")).thenReturn("XRay");
		when(req.getParameter("pri")).thenReturn("OrthopedicVisitID");
		when(req.getParameter("key")).thenReturn("1");
		test.testDoGet(r, req);
		
	}
	
	/**
	 * Test Input
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testInput() throws IOException, ServletException {
	    Delegator test = new Delegator();
	    ServletOutputStream out = mock(ServletOutputStream.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse r = mock(HttpServletResponse.class);
		when(r.getOutputStream()).thenReturn(out);
		when(req.getParameter("table")).thenReturn("orthopedic");
		when(req.getParameter("col")).thenReturn("XRay");
		when(req.getParameter("pri")).thenReturn("OrthopedicVisitID");
		when(req.getParameter("key")).thenReturn("2");
		test.testDoGet(r, req);
		
	}

	private class Delegator extends ImageDataServlet {
		private static final long serialVersionUID = 1L;
        
		public Delegator(){
			super(TestDAOFactory.getTestInstance());
		}
		
		public void testDoGet(HttpServletResponse r, HttpServletRequest req) throws ServletException, IOException{
			super.doGet(req, r);
		}
	}
}

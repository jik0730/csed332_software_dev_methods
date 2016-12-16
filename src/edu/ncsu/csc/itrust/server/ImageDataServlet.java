package edu.ncsu.csc.itrust.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.dao.DAOFactory;

/** 
 * ImageDataServlet
 * 
 * Provides BLOB Images in mysql database 
 */
public class ImageDataServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ?table=orthopedic&col=MRI&pri=oid&key=3
		InputStream in = null;
		OutputStream out = null;
		try {
			String tableName = req.getParameter("table");
			String colName = req.getParameter("col");
			String idName = req.getParameter("pri");
			String primaryKey = req.getParameter("key");

			if(tableName == null || colName == null || idName == null | primaryKey == null)
				throw new NoSuchElementException();
			
			in = getImageStream(tableName, colName, idName, primaryKey);
			
			resp.setContentType("image");
			out = resp.getOutputStream();
			byte[] buf = new byte[512];
			int read = 0;
			while((read = in.read(buf))>0) {
				out.write(buf, 0, read);;
			}
		} catch (IOException | NoSuchElementException e) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			e.printStackTrace();
			return;
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}
	
	private InputStream getImageStream(String tableName, String colName, String idName, String primaryKey) {
		Connection conn = null;
		PreparedStatement ps = null;
		DAOFactory factory = DAOFactory.getProductionInstance();
		try {
			conn = factory.getConnection();
			/** TODO: Serious Security Problem: SQL Injection */
			ps = conn.prepareStatement("SELECT "+ colName +" FROM "+ tableName +" WHERE "+ idName +"=?");
			ps.setInt(1, Integer.valueOf(primaryKey));
			ResultSet rs = ps.executeQuery();
			rs.next();

			InputStream in = rs.getBinaryStream(colName);
			rs.close();
			ps.close();

			return in;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NoSuchElementException();
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}

package edu.ncsu.csc.itrust.server;

import java.io.IOException;
import java.io.InputStream;
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

/** Provides BLOB Images in mysql database */
public class ImageDataServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// /Orthopedic/MRI/wow/oid/3
		List<String> paths = Arrays.asList(req.getPathInfo().split("/"));
		try {
			if (paths.size() != 3) throw new NoSuchElementException();
			String tableName = paths.get(0);
			String colName = paths.get(1);
			String idName = paths.get(2);
			String primaryKey = paths.get(3);

			InputStream in = getImageStream(tableName, colName, idName, primaryKey);
			
			resp.setContentType("image");
			//TODO: Write Stream To Response
		} catch (NoSuchElementException e) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
	}
	
	private InputStream getImageStream(String tableName, String colName, String idName, String primaryKey) {
		Connection conn = null;
		PreparedStatement ps = null;
		DAOFactory factory = DAOFactory.getProductionInstance();
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT ? FROM ? WHERE ?=?");
			ps.setString(1, colName);
			ps.setString(2, tableName);
			ps.setString(3, idName);
			ps.setString(4, primaryKey);
			ResultSet rs = ps.executeQuery();

			InputStream in = rs.getBinaryStream(colName);
			rs.close();
			ps.close();

			return in;
		} catch (SQLException e) {
			throw new NoSuchElementException();
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}

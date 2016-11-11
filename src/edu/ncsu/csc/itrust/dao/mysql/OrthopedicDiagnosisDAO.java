package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.OrthopedicDiagnosisBean;
import edu.ncsu.csc.itrust.beans.loaders.OrthopedicDiagnosisBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Used for managing diagnoses given during a particular office visit.
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be reflections of the database, that is,
 * one DAO per table in the database (most of the time). For more complex sets of queries, extra DAOs are
 * added. DAOs can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than a factory. All DAOs should be
 * accessed by DAOFactory (@see {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 * 
 *   
 *  
 *  
 */

public class OrthopedicDiagnosisDAO {
	private DAOFactory factory;
	private OrthopedicDiagnosisBeanLoader loader = new OrthopedicDiagnosisBeanLoader(true);
	
	/**
	 * @param factory
	 */
	public OrthopedicDiagnosisDAO(DAOFactory factory) {
		this.factory = factory;
	}
	
	
	/**
	 * @param visitID
	 * @return
	 * @throws DBException
	 */
	public List<OrthopedicDiagnosisBean> getList(long visitID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("Select * From icdcodes,ordiagnosis Where ordiagnosis.VisitID = ? "
					+ "AND icdcodes.Code=ordiagnosis.ICDCode");
			ps.setLong(1, visitID);
			ResultSet rs = ps.executeQuery();
			List<OrthopedicDiagnosisBean> loadlist = loader.loadList(rs);
			rs.close();
			ps.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Adds a diagnosis bean to the database.
	 * @param pres The prescription bean to be added.
	 * @return The unique ID of the newly added bean.
	 * @throws DBException
	 */
	public long add(OrthopedicDiagnosisBean bean) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			String statement = "INSERT INTO ordiagnosis " +
				"(VisitID,ICDCode) VALUES (?,?)";
			ps = conn.prepareStatement(statement);
			ps.setLong(1, bean.getVisitID());
			ps.setString(2, bean.getICDCode());
			ps.executeUpdate();
			ps.close();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	
	/**
	 * Edits an existing prescription bean.
	 * 
	 * @param pres The newly updated prescription bean.
	 * @return A long indicating the ID of the newly updated prescription bean.
	 * @throws DBException
	 */
	public long edit(OrthopedicDiagnosisBean bean) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			String statement = "UPDATE ordiagnosis " +
					"SET VisitID=?, ICDCode=? " +
					"WHERE ID = ?";
			ps = conn.prepareStatement(statement);
			ps.setLong(1, bean.getVisitID());
			ps.setString(2, bean.getICDCode());
			ps.setLong(3, bean.getOrDiagnosisID());
			ps.executeUpdate();
			ps.close();
			return DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * Removes the given diagnosis from its office visit
	 * 
	 * @param ovMedicationID The unique ID of the medication to be removed.
	 * @throws DBException
	 */
	public void remove(long ovDiagnosisID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM ordiagnosis WHERE ID=? ");
			ps.setLong(1, ovDiagnosisID);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	
	/**
	 * Returns all non-Orthopedic ICD9CM codes sorted by code
	 * 
	 * @return java.util.List of DiagnosisBeans
	 * @throws DBException
	 */
	public List<OrthopedicDiagnosisBean> getOrICDCodes() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		OrthopedicDiagnosisBeanLoader diagnosisLoader = new OrthopedicDiagnosisBeanLoader();
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM icdcodes where Orthopedic='yes' ORDER BY CODE");
			ResultSet rs = ps.executeQuery();
			List<OrthopedicDiagnosisBean> loadlist = diagnosisLoader.loadList(rs);
			rs.close();
			return loadlist;
		} catch (SQLException e) {
			
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}



}

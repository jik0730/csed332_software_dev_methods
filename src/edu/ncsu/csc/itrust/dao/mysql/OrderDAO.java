package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.OrderBean;
import edu.ncsu.csc.itrust.beans.loaders.OrderBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class OrderDAO {
	private DAOFactory factory;
	private OrderBeanLoader orderLoader;

	/**
	 * The typical constructor.
	 * 
	 * @param factory
	 *            The {@link DAOFactory} associated with this DAO, which is used
	 *            for obtaining SQL connections, etc.
	 */
	public OrderDAO(DAOFactory factory) {
		this.factory = factory;
		orderLoader = new OrderBeanLoader();
	}

	/**
	 * add OrderBean
	 * 
	 * @param bean
	 * @return
	 * @throws DBException
	 */
	public int add(OrderBean bean) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			String statement = "INSERT INTO orderTable (VisitID, OrderHCPID,"
					+ " OrderedHCPID, PatientID, Completed) VALUES (?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(statement);
			orderLoader.loadParameters(ps, bean);
			ps.executeUpdate();
			ps.close();
			return (int) (DBUtil.getLastInsert(conn));
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * getOrderByVisitID
	 * 
	 * @param visitID
	 * @return List<OrderBean>
	 * @throws DBException
	 */
	public List<OrderBean> getOrderByVisitID(int visitID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<OrderBean> orders = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM orderTable where VisitID=?");
			ps.setInt(1, visitID);
			ResultSet rs = ps.executeQuery();
			orders = orderLoader.loadList(rs);
			rs.close();
			return orders;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * getOrderByOrdered by HCPID
	 * 
	 * @param orderedHCPID
	 * @return List<OrderBean>
	 * @throws DBException
	 */
	public List<OrderBean> getOrderByOrderedHCPID(long orderedHCPID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<OrderBean> orders = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM orderTable where OrderedHCPID=?");
			ps.setLong(1, orderedHCPID);
			ResultSet rs = ps.executeQuery();
			orders = orderLoader.loadList(rs);
			rs.close();
			return orders;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * getUncompletedOrderForPair
	 * 
	 * @param orderedHCPID
	 * @param patientID
	 * @return List<OrderBean>
	 * @throws DBException
	 */
	public List<OrderBean> getUncompletedOrderForPair(long orderedHCPID, long patientID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<OrderBean> orders = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement(
					"SELECT * FROM orderTable " + "where OrderedHCPID=? AND PatientID=? AND Completed=false");
			ps.setLong(1, orderedHCPID);
			ps.setLong(2, patientID);
			ResultSet rs = ps.executeQuery();
			orders = orderLoader.loadList(rs);
			rs.close();
			return orders;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

	/**
	 * completeOrder
	 * 
	 * @param orderID
	 * @throws DBException
	 */
	public void completeOrder(int orderID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE orderTable SET Completed = true where OrderID = ?");
			ps.setInt(1, orderID);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
}

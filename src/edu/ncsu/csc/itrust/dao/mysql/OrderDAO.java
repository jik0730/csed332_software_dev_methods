package edu.ncsu.csc.itrust.dao.mysql;

import edu.ncsu.csc.itrust.beans.loaders.OrderBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;

public class OrderDAO {
	private DAOFactory factory;
	private OrderBeanLoader orderLoader;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public OrderDAO(DAOFactory factory) {
		this.factory = factory;
		orderLoader = new OrderBeanLoader();
	}
}

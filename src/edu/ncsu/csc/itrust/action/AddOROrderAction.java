package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.OrderBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OrderDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class AddOROrderAction {
	private OrderDAO orderDAO;
	private String officeVisitID;
	
	public AddOROrderAction(DAOFactory factory, String ovIDString ) 
		throws ITrustException {
		orderDAO = factory.getOrderDAO();
		officeVisitID=ovIDString;
	}
	
	public List<OrderBean> getOrders() throws DBException {
		return orderDAO.getOrderByVisitID(Integer.valueOf(officeVisitID));
	}	

	public void addOrder(OrderBean bean) throws ITrustException {
		List<OrderBean> duplicates = 
				orderDAO.getUncompletedOrderForPair(bean.getOrderedHCPID(), bean.getPatientID());
		if (duplicates.size() > 0) throw new ITrustException("Duplicate Order");
		orderDAO.add(bean);
	}
}

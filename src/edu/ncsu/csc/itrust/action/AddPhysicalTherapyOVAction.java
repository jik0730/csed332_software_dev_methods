package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.OrderBean;
import edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PhysicalTherapyOVRecordDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.dao.mysql.OrderDAO;

public class AddPhysicalTherapyOVAction {
	private PhysicalTherapyOVRecordDAO physicalTherapyOVDAO;
	private long loggedInMID;
	private EventLoggingAction loggingAction;
	private OrderDAO orderDAO;

	/**
	 * AddPhysicalTherapyOVAction is the constructor for this action class. It
	 * simply initializes the instance variables.
	 * 
	 * @param factory
	 * @param loggedInMID
	 */
	public AddPhysicalTherapyOVAction(DAOFactory factory, long loggedInMID) {
		this.physicalTherapyOVDAO = factory.getPhysicalTherapyOVRecordDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
		this.orderDAO = factory.getOrderDAO();
	}

	/**
	 * Adds a new PhysicalTherapy office visit record.
	 * 
	 * @param p
	 * @throws FormValidationException
	 * @throws ITrustException
	 */
	public void addPhysicalTherapyOV(PhysicalTherapyOVRecordBean p) throws FormValidationException, ITrustException {
		if (p != null) {
			List<OrderBean> order = orderDAO.getUncompletedOrderForPair(loggedInMID, p.getMid());

			if (order.size() != 0) {
				// Add the Orthopedic office visit record to the database
				physicalTherapyOVDAO.addPhysicalTherapyOVRecord(p);
				orderDAO.completeOrder(order.get(0).getOrderID());
			} else {
				throw new ITrustException("You can't make physical therapy without order.");
			}

			loggingAction.logEvent(TransactionType.parse(8900), loggedInMID, p.getMid(),
					"PhysicalTherapy Office Visit " + p.getOid() + " added");
		} else {
			throw new ITrustException("Cannot add a null PhysicalTherapy Office Visit.");
		}
	}
}

package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.TransactionLogBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.TransactionLogDAO;
import edu.ncsu.csc.itrust.enums.TransactionLogColumnType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * ViewTransactionLogAction is the Action that retrieves the transaction log
 * records from the database
 */
public class ViewTransactionLogAction {
	private TransactionLogDAO transactionLogDAO;

	/**
	 * ViewTransactionLogAction is constructor of this class.
	 * 
	 * @param factory
	 */
	public ViewTransactionLogAction(DAOFactory factory) {
		this.transactionLogDAO = factory.getTransactionLogDAO();
	}

	/**
	 * getTransactionGroupBy is for grouping record of transaction logs by role
	 * of user or transaction type
	 * 
	 * @param type
	 * @return List<TransactionLogBean>
	 * @throws DBException
	 * @throws ITrustException
	 */
	public List<TransactionLogBean> getTransactionGroupBy(TransactionLogColumnType type)
			throws DBException, ITrustException {
		List<TransactionLogBean> result = transactionLogDAO.getTransactionGroupBy(type);
		return result;
	}

	/**
	 * getTransactionList is for receiving transaction log list in
	 * TransactionLogBean
	 * 
	 * @param loggedInRole
	 * @param secondaryRole
	 * @param start
	 *            : this is start time of condition
	 * @param end
	 *            : this is end time of condition
	 * @param transactionCode
	 * @return List<TransactionLogBean>
	 * @throws DBException
	 */
	public List<TransactionLogBean> getTransactionList(String loggedInRole, String secondaryRole, java.util.Date start,
			java.util.Date end, int transactionCode) throws DBException {
		return transactionLogDAO.getTransactionList(loggedInRole, secondaryRole, start, end, transactionCode);
	}
}

package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.TransactionLogBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.TransactionLogDAO;
import edu.ncsu.csc.itrust.enums.TransactionLogColumnType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class ViewTransactionLogAction {
	/**transactionLogDAO is the DAO that retrieves the orthopedic office
	 *  visit records from the database*/
	private TransactionLogDAO transactionLogDAO;
	
	public ViewTransactionLogAction(DAOFactory factory) {
		this.transactionLogDAO = factory.getTransactionLogDAO();
	}
	
	/**
     * gettransactionLogByMID returns a list of orthopedic office visits record beans for past orthopedic care.
     * @param mid the mid of the patient.
     * @return The list of orthopedic office visit records.
	 * @throws DBException 
     * @throws ITrustException When there is a bad user.
     */
	public List<TransactionLogBean> getTransactionGroupBy(TransactionLogColumnType type) throws DBException, ITrustException {
		List<TransactionLogBean> result = transactionLogDAO.getTransactionGroupBy(type);
		return result;
	}
	
	public List<TransactionLogBean> getTransactionList(String loggedInRole, String secondaryRole, 
			java.util.Date start, java.util.Date end, 
			int transactionCode) throws DBException {
		return transactionLogDAO.getTransactionList(loggedInRole, secondaryRole, start, end, transactionCode);
	}
}

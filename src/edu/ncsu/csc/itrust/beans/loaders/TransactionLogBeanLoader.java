package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.TransactionLogBean;
import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * A loader for TransactionLogBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class TransactionLogBeanLoader implements BeanLoader<TransactionLogBean> {

	public List<TransactionLogBean> loadList(ResultSet rs) throws SQLException {
		List<TransactionLogBean> list = new ArrayList<TransactionLogBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, TransactionLogBean bean) throws SQLException {
		throw new IllegalStateException("unimplemented!");
	}

	public TransactionLogBean loadSingle(ResultSet rs) throws SQLException {
		TransactionLogBean t = new TransactionLogBean();
		t.setAddedInfo(rs.getString("addedInfo"));
		t.setLoggedInRole(rs.getString("loggedInRole"));
		t.setSecondaryRole(rs.getString("secondaryRole"));
		t.setTimeLogged(rs.getTimestamp("timeLogged"));
		t.setTransactionType(TransactionType.parse(rs.getInt("transactionCode")));
		t.setTransactionID(rs.getLong("transactionID"));
		return t;
	}

}

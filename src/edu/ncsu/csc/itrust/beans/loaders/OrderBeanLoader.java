package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.OrderBean;

public class OrderBeanLoader implements BeanLoader<OrderBean> {

	@Override
	public List<OrderBean> loadList(ResultSet rs) throws SQLException {
		List<OrderBean> list = new ArrayList<OrderBean>();
		while(rs.next()) 
			list.add(loadSingle(rs));
		return list;
	}

	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, OrderBean bean) throws SQLException {
		int i=1;

		ps.setInt(i++, bean.getVisitID());
		ps.setLong(i++, bean.getOrderHCPID());
		ps.setLong(i++,  bean.getOrderedHCPID());
		ps.setLong(i++, bean.getPatientID());
		ps.setBoolean(i++, bean.isCompleted());
		
		return ps;
	}

	@Override
	public OrderBean loadSingle(ResultSet rs) throws SQLException {
		OrderBean order = new OrderBean();
		order.setOrderID(rs.getInt("OrderID"));
		order.setVisitID(rs.getInt("VisitID"));
		order.setOrderHCPID(rs.getLong("OrderHCPID"));
		order.setOrderedHCPID(rs.getLong("OrderedHCPID"));
		order.setPatientID(rs.getLong("PatientID"));
		order.setCompleted(rs.getBoolean("Completed"));
		return order;
	}

}

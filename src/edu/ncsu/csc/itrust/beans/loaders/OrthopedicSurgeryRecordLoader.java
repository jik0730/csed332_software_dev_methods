package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.OrthopedicSurgeryRecordBean;

/**
 * A loader for OrthopedicSurgeryRecordBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class OrthopedicSurgeryRecordLoader implements BeanLoader<OrthopedicSurgeryRecordBean> {
	
	/**
	 * Returns a list of beans with data from a ResultSet.
	 * @param rs ResultSet containing data from the database
	 * @return A list of OrthopedicSurgeryRecordBean created from the ResultSet parameter.
	 * @throws SQLException thrown when there is a error resulting from accessing a field of the ResultSet.
	 */
	public List<OrthopedicSurgeryRecordBean> loadList(ResultSet rs) throws SQLException {
		List<OrthopedicSurgeryRecordBean> list = new ArrayList<OrthopedicSurgeryRecordBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}
	
	/**
	 * Internal method used to load results into beans from ResultSets.
	 * @param rs ResultSet that data is coming out of.
	 * @param p bean that data is going into.
	 * @throws SQLException thrown when there is a error resulting from accessing a field of the ResultSet.
	 */
	private void loadCommon(ResultSet rs, OrthopedicSurgeryRecordBean p) throws SQLException {
		p.setMid(rs.getLong("mid"));
		p.setOid(rs.getLong("oid"));
		p.setVisitDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date(rs.getDate("dateVisit").getTime())));
		p.setLastName(rs.getString("docLastName"));
		p.setFirstName(rs.getString("docFirstName"));
		if(rs.getObject("surgery") != null){
			p.setSurgery(rs.getString("surgery"));
		} else{
			p.setSurgery(null);
		}
		if(rs.getObject("surgeryNotes") != null){
			p.setSurgeryNotes(rs.getString("surgeryNotes"));
		} else{
			p.setSurgeryNotes(null);
		}
	}
	
	/**
	 * Takes the first result out of a ResultSet and returns a bean with that data in it.
	 * @param rs ResultSet containing data from the database.
	 * @return p bean containing the data.
	 * @throws SQLException thrown when there is a error resulting from accessing a field of the ResultSet.
	 */
	public OrthopedicSurgeryRecordBean loadSingle(ResultSet rs) throws SQLException {
		OrthopedicSurgeryRecordBean p = new OrthopedicSurgeryRecordBean();
		loadCommon(rs, p);
		return p;
	}
	
	/**
	 * Loads values into the parameters of a preparedStatement from the given OrthopedicSurgeryRecordBean.
	 * @param ps The PreparedStatement that will have it's parameters filled in.
	 * @param p The OrthopedicSurgeryRecordBean used to fill in the parameters of the preparedStatement.
	 * @return The preparedStatement with the parameters filled in.
	 * @throws SQLException thrown when there is a error resulting from accessing a field of the ResultSet.
	 */
	public PreparedStatement loadParameters(PreparedStatement ps, OrthopedicSurgeryRecordBean p) throws SQLException {
		int i = 1;
	    ps.setLong(i++, p.getMid());
		ps.setDate(i++, new java.sql.Date(p.getVisitDate().getTime()));
		ps.setString(i++, p.getLastName());
		ps.setString(i++, p.getFirstName());
		if(p.getSurgery() != null){
			ps.setString(i++, p.getSurgery());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		if(p.getSurgeryNotes() != null){
			ps.setString(i++, p.getSurgeryNotes());
		} else{
			ps.setNull(i++, Types.NULL);
		}
		return ps;
	}
}
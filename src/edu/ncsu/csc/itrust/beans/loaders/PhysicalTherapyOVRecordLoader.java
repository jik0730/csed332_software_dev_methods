package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean;

/**
 * A loader for PhysicalTherapyOVRecordBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements.
 * Use the superclass to enforce consistency. For details on the paradigm for a
 * loader (and what its methods do), see {@link BeanLoader}
 */
public class PhysicalTherapyOVRecordLoader {
	public List<PhysicalTherapyOVRecordBean> loadList(ResultSet rs) throws SQLException {
		List<PhysicalTherapyOVRecordBean> list = new ArrayList<PhysicalTherapyOVRecordBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	private void loadCommon(ResultSet rs, PhysicalTherapyOVRecordBean p) throws SQLException {
		p.setMid(rs.getLong("mid"));
		p.setOid(rs.getLong("oid"));
		p.setVisitDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date(rs.getDate("dateVisit").getTime())));
		p.setLastName(rs.getString("docLastName"));
		p.setFirstName(rs.getString("docFirstName"));
		p.setWellnessSurveyResults(rs.getString("WellnessSurveyResults"));
		p.setWellnessSurveyScore(rs.getLong("WellnessSurveyScore"));
		p.setExercise(rs.getString("Exercise"));
	}

	/**
	 * load one record from ResultSet rs
	 * 
	 * @param rs
	 *            ResultSet containing data from DB
	 * @return p phsyical therapy bean containing the data
	 * @throws SQLException
	 *             thrown when there is a error resulting from accessing a field
	 *             of the ResultSet.
	 */
	public PhysicalTherapyOVRecordBean loadSingle(ResultSet rs) throws SQLException {
		PhysicalTherapyOVRecordBean p = new PhysicalTherapyOVRecordBean();
		loadCommon(rs, p);
		return p;
	}

	/**
	 * Loads values into the parameters of a preparedStatement from the given
	 * PhysicalTherapyOVRecordBean.
	 * 
	 * @param ps
	 * @param p
	 * @return ps PreparedStatement of each parameter loaded from bean.
	 * @throws SQLException
	 */
	public PreparedStatement loadParameters(PreparedStatement ps, PhysicalTherapyOVRecordBean p) throws SQLException {
		int i = 1;
		ps.setLong(i++, p.getMid());
		ps.setDate(i++, new java.sql.Date(p.getVisitDate().getTime()));
		ps.setString(i++, p.getLastName());
		ps.setString(i++, p.getFirstName());
		ps.setString(i++, p.getWellnessSurveyResults());
		ps.setLong(i++, p.getWellnessSurveyScore());
		ps.setString(i++, p.getExercise());

		return ps;
	}
}

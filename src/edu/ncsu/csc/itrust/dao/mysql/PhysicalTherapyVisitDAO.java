package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.PhysicalTherapyVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class PhysicalTherapyVisitDAO {

	private transient final DAOFactory factory;

	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, 
	 * which is used for obtaining SQL connections, etc.
	 */
	public PhysicalTherapyVisitDAO(final DAOFactory factory) {
		this.factory = factory;
	}
	
	public List<PhysicalTherapyVisitBean> getPhysicalTherapyVisits() throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<PhysicalTherapyVisitBean> loadlist = new ArrayList<PhysicalTherapyVisitBean>();
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM orthopedicPhysicalTherapy");
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				PhysicalTherapyVisitBean result = new PhysicalTherapyVisitBean();
				
				long visitID = rs.getLong("VisitID");
				long patientID = rs.getLong("PatientID");
				long hcpID = rs.getLong("HCPID");
				String wellnessSurvey = rs.getString("Wellness");
				long wellnessSurveyScore = rs.getLong("WellnessScore");
				String exercise = rs.getString("Exercise");
				
				result.setVisitID(visitID);
				result.setPatientID(patientID);
				result.setHcpID(hcpID);
				result.setWellnessSurvey(wellnessSurvey);
				result.setWellnessSurveyScore(wellnessSurveyScore);
				result.setExercise(exercise);
				
				loadlist.add(result);
			}
			rs.close();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return loadlist;
	}
}

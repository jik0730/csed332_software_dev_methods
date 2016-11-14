package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.beans.OrthopedicDiagnosisBean;

/**
 * A loader for OrthopedicDiagnosisBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency.
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader} 
 */

public class OrthopedicDiagnosisBeanLoader implements BeanLoader<OrthopedicDiagnosisBean> {
	private boolean loadOPDiagnosisID = false;

	public OrthopedicDiagnosisBeanLoader() {
		loadOPDiagnosisID = false;
	}

	public OrthopedicDiagnosisBeanLoader(boolean loadOPDiagnosisID) {
		this.loadOPDiagnosisID = loadOPDiagnosisID;
	}

	public List<OrthopedicDiagnosisBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<OrthopedicDiagnosisBean> list = new ArrayList<OrthopedicDiagnosisBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	public OrthopedicDiagnosisBean loadSingle(ResultSet rs) throws SQLException {

		OrthopedicDiagnosisBean diag = new OrthopedicDiagnosisBean(rs.getString("Code"), rs.getString("Description"), rs.getString("Chronic"), rs.getString("URL"));
		if (loadOPDiagnosisID) {
			diag.setOpDiagnosisID(rs.getInt("ID"));
			diag.setVisitID(rs.getLong("VisitID"));
		}
		return diag;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, OrthopedicDiagnosisBean bean) throws SQLException {
		throw new IllegalStateException("unimplemented!");
	}
}
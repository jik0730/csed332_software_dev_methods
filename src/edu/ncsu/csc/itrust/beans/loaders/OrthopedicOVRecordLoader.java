package edu.ncsu.csc.itrust.beans.loaders;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean;

public class OrthopedicOVRecordLoader implements BeanLoader<OrthopedicOVRecordBean> {

	@Override
	public List<OrthopedicOVRecordBean> loadList(ResultSet rs) throws SQLException {
		List<OrthopedicOVRecordBean> list = new ArrayList<OrthopedicOVRecordBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	private void loadCommon(ResultSet rs, OrthopedicOVRecordBean p) throws SQLException {
		p.setOid(rs.getInt("OrthopedicVisitID"));
		p.setPid(rs.getLong("PatientID"));
		p.setHid(rs.getLong("HCPID"));
		p.setVisitDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date(rs.getDate("DateVisit").getTime())));
		p.setInjured(rs.getString("Injured"));
		if(rs.getObject("MRIReport") != null) {
			p.setMriReport(rs.getString("MRIReport"));
		} else {
			p.setMriReport(null);
		}
	}

	@Override
	public OrthopedicOVRecordBean loadSingle(ResultSet rs) throws SQLException {
		OrthopedicOVRecordBean p = new OrthopedicOVRecordBean();
		loadCommon(rs, p);
		return p;
	}

	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, OrthopedicOVRecordBean p) throws SQLException {
		int i = 1;
		ps.setLong(i++, p.getPid());
		ps.setLong(i++, p.getHid());
		ps.setDate(i++, new java.sql.Date(p.getVisitDate().getTime()));
		ps.setString(i++, p.getInjured());
		if(p.getXray() != null) {
			ps.setBinaryStream(i++, new ByteArrayInputStream(p.getXray()), p.getXray().length);
		} else {
			ps.setNull(i++, Types.NULL);
		}
		if(p.getMri() != null) {
			ps.setBinaryStream(i++, new ByteArrayInputStream(p.getMri()), p.getMri().length);
		} else {
			ps.setNull(i++, Types.NULL);
		}
		if(p.getMriReport() != null) {
			ps.setString(i++, p.getMriReport());
		} else {
			ps.setNull(i++, Types.NULL);
		}
		return ps;
	}

}

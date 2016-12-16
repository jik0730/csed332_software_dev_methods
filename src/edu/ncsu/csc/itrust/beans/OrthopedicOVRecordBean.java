package edu.ncsu.csc.itrust.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author KimHeeGon
 *
 */
public class OrthopedicOVRecordBean {
	private int oid;
	private long pid;
	private long hid;
	private String injured;
	private String mriReport;
	private String visitDate;
	private byte[] xray;
	private byte[] mri;

	public OrthopedicOVRecordBean() {
	}

	/**
	 * A bean for storing data about OrthopedicOVRecord.
	 * @param oid
	 * @param pid
	 * @param hid
	 * @param xrayImageID
	 * @param mriImageID
	 * @param mriReport
	 * @param injured
	 * @param visitDate
	 */
	public OrthopedicOVRecordBean(int oid, int pid, int hid, String xrayImageID, String mriImageID, String mriReport,
			String injured, String visitDate) {
		this.oid = oid;
		this.pid = pid;
		this.hid = hid;
		this.mriReport = mriReport;
		this.injured = injured;
		this.visitDate = visitDate;
	}

	/**
	 * getter Oid
	 * @return oid
	 */
	public int getOid() {
		return oid;
	}

	/**
	 * set Oid
	 * @param oid
	 */
	public void setOid(int oid) {
		this.oid = oid;
	}

	/**
	 * get Pid
	 * @return pid
	 */
	public long getPid() {
		return pid;
	}

	/**
	 * set Pid
	 * @param pid
	 */
	public void setPid(long pid) {
		this.pid = pid;
	}

	/**
	 * get Hid
	 * @return
	 */
	public long getHid() {
		return hid;
	}

	/**
	 * set Hid
	 * @param hid
	 */
	public void setHid(long hid) {
		this.hid = hid;
	}

	/**
	 * get Mri report
	 * @return mriReport
	 */
	public String getMriReport() {
		return mriReport;
	}

	/**
	 * the setMriReport to set setMriReport
	 * @param mriReport
	 */
	public void setMriReport(String mriReport) {
		this.mriReport = mriReport;
	}

	/**
	 * the getVisitDate
	 * @return visitDate
	 */
	public Date getVisitDate() {
		if (visitDate == null)
			return null;
		Date date = null;
		try {
			date = new SimpleDateFormat("MM/dd/yyyy").parse(visitDate);
		} catch (ParseException e) {
			// If it can't be parsed, return null.
			return null;
		}
		return date;
	}

	/**
	 * the getVisitDateString
	 * @return visitDate
	 */
	public String getVisitDateString() {
		return visitDate;
	}

	/**
	 * the setVisitDate to set setVisitDate
	 * @param visitDate
	 */
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	/**
	 * the getXray
	 * @return xray
	 */
	public byte[] getXray() {
		return xray;
	}

	/**
	 * the setXray to set setXray
	 * @param xray
	 */
	public void setXray(byte[] xray) {
		this.xray = xray;
	}

	/**
	 * the getMri
	 * @return
	 */
	public byte[] getMri() {
		return mri;
	}

	/**
	 * the setMri to set setMri
	 * @param mri 
	 */
	public void setMri(byte[] mri) {
		this.mri = mri;
	}

	/**
	 * The getInjured
	 * @return getInjured
	 */
	public String getInjured() {
		return injured;
	}

	/**
	 * the setInjured to set setInjured 
	 * @param injured
	 */
	public void setInjured(String injured) {
		this.injured = injured;
	}

	// Do not need to define hashcode??
	/**
	 * Method used to determine if OrthopedicRecordBeans are equal. 
	 * @return true if the OrthopedicRecordBeans are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrthopedicOVRecordBean other = (OrthopedicOVRecordBean) obj;
		if (oid != other.getOid())
			return false;
		if (pid != other.getPid())
			return false;
		if (hid != other.getHid())
			return false;
		if (mriReport == null) {
			if (other.mriReport != null)
				return false;
		} else if (!mriReport.equals(other.mriReport))
			return false;
		if (injured == null) {
			if (other.injured != null)
				return false;
		} else if (!injured.equals(other.injured))
			return false;
		if (visitDate == null) {
			if (other.visitDate != null)
				return false;
		} else if (!visitDate.equals(other.visitDate))
			return false;
		if (xray == null) {
			if (other.xray != null)
				return false;
		} else if (!Arrays.equals(xray, other.xray))
			return false;
		if (mri == null) {
			if (other.mri != null)
				return false;
		} else if (!Arrays.equals(mri, other.mri))
			return false;
		return true;
	}

	/**
	 * Method used to compute the hashcode for a OrthopedicRecordBean. 
	 * @return true if the OrthopedicRecordBeans are equal, false otherwise.
	 */

	@Override
	public int hashCode() {
		final int prime = 37;
		int result = 1;
		result = prime * result + ((injured == null) ? 0 : injured.hashCode());
		result = prime * result + ((mriReport == null) ? 0 : mriReport.hashCode());
		result = prime * result + ((visitDate == null) ? 0 : visitDate.hashCode());
		result = prime * result + ((xray == null) ? 0 : Arrays.hashCode(xray));
		result = prime * result + ((mri == null) ? 0 : Arrays.hashCode(mri));
		result = prime * result + (int) (pid ^ (pid >>> 32));
		result = prime * result + (int) (oid ^ (oid >>> 16));
		result = prime * result + (int) (hid ^ (hid >>> 32));

		return result;
	}

	/**
	 * Creates a string representation of this object.
	 * @return The string representation.
	 */
	public String toString() {
		return "OrthopedicScheduleOVRecordBean [patientmid=" + pid + ", doctormid=" + hid + ", oid=" + oid + ", date="
				+ visitDate + ", mriReport=" + mriReport + ", xraySize=" + xray.length + ", mriSize=" + mri.length
				+ "]";
	}

}

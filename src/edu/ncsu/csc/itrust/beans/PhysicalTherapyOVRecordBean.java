package edu.ncsu.csc.itrust.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class PhysicalTherapyOVRecordBean {
	private long mid;
	private long oid;
	private String visitDate;
	private String docLastName;
	private String docFirstName;
	private String[] wellnessSurveyResults;
	private long wellnessSurveyScore;
	private String[] exercise;

	/**
	 * 
	 */
	public PhysicalTherapyOVRecordBean() {
	}

	/**
	 * getMid()
	 * @return mid
	 */
	public long getMid() {
		return mid;
	}

	/**
	 * setMid
	 * @param mid
	 */
	public void setMid(long mid) {
		this.mid = mid;
	}

	/**
	 * getOid() 
	 * @return oid
	 */
	public long getOid() {
		return oid;
	}

	/**
	 * setOid
	 * @param oid
	 */
	public void setOid(long oid) {
		this.oid = oid;
	}

	/**
	 * getVisitDate
	 * @return Date
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
	 * getVisitDateString
	 * @return visitDate
	 */
	public String getVisitDateString() {
		return visitDate;
	}

	/**
	 * setVisitDate
	 * @param visitDate
	 */
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	/**
	 * getLastName
	 * @return docLastName
	 */
	public String getLastName() {
		return docLastName;
	}

	/**
	 * setLastName
	 * @param docLastName
	 */
	public void setLastName(String docLastName) {
		this.docLastName = docLastName;
	}

	/**
	 * getFirstName
	 * @return docFirstName
	 */
	public String getFirstName() {
		return docFirstName;
	}

	/**
	 * setFirstName
	 * @param docFirstName
	 */
	public void setFirstName(String docFirstName) {
		this.docFirstName = docFirstName;
	}

	/**
	 * getWellnessSurveyResults
	 * @return WellnessSurveyResults
	 */
	public String getWellnessSurveyResults() {
		return String.join(",", Arrays.asList(wellnessSurveyResults));
	}

	/**
	 * getWellnessSurveyResultsInArray
	 * @return wellnessSurveyResults
	 */
	public String[] getWellnessSurveyResultsInArray() {
		return wellnessSurveyResults;
	}

	/**
	 * setWellnessSurveyResults
	 * @param wellnessSurveyResults
	 */
	public void setWellnessSurveyResults(String wellnessSurveyResults) {
		this.wellnessSurveyResults = wellnessSurveyResults.split(",");
	}

	/**
	 * getWellnessSurveyScore
	 * @return wellnessSurveyScore
	 */
	public long getWellnessSurveyScore() {
		return wellnessSurveyScore;
	}

	/**
	 * setWellnessSurveyScore
	 * @param wellnessSurveyScore
	 */
	public void setWellnessSurveyScore(long wellnessSurveyScore) {
		this.wellnessSurveyScore = wellnessSurveyScore;
	}

	/**
	 * getExercise
	 * @return exercise
	 */
	public String getExercise() {
		return String.join(",", Arrays.asList(exercise));
	}

	/**
	 * getExerciseInArray()
	 * @return exercise string
	 */
	public String[] getExerciseInArray() {
		return exercise;
	}

	/**
	 * setExercise
	 * @param exercise
	 */
	public void setExercise(String exercise) {
		this.exercise = exercise.split(",");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhysicalTherapyOVRecordBean other = (PhysicalTherapyOVRecordBean) obj;

		if (this.mid != other.mid)
			return false;
		if (this.oid != other.oid)
			return false;
		if (!this.visitDate.equals(other.visitDate))
			return false;
		if (!this.docLastName.equals(other.docLastName))
			return false;
		if (!this.docFirstName.equals(other.docFirstName))
			return false;
		if (!Arrays.equals(this.wellnessSurveyResults, other.wellnessSurveyResults))
			return false;
		if (this.wellnessSurveyScore != other.wellnessSurveyScore)
			return false;
		if (!Arrays.equals(this.exercise, other.exercise))
			return false;
		return true;
	}
}

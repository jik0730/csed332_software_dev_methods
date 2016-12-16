package edu.ncsu.csc.itrust.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Bean for Physical Therapy Office Visits. Holds all of the data needed for an
 * Physical Therapy Office Visit.
 */
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
	 * Getter for the mid value.
	 * 
	 * @return the mid.
	 */
	public long getMid() {
		return mid;
	}

	/**
	 * Set the mid value.
	 * 
	 * @param mid
	 *            the new value.
	 */
	public void setMid(long mid) {
		this.mid = mid;
	}

	/**
	 * Getter for the oid value.
	 * 
	 * @return the oid.
	 */
	public long getOid() {
		return oid;
	}

	/**
	 * Set the oid value.
	 * 
	 * @param oid
	 *            the new value.
	 */
	public void setOid(long oid) {
		this.oid = oid;
	}

	/**
	 * Getter for the date as Date object.
	 * 
	 * @return the date.
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
	 * Getter for the date as string.
	 * 
	 * @return the string of date.
	 */
	public String getVisitDateString() {
		return visitDate;
	}

	/**
	 * Set the visit date with string
	 * 
	 * @param visitDate
	 *            the new value
	 */
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	/**
	 * Getter for the last name of Doctor
	 * 
	 * @return the docLastName
	 */
	public String getLastName() {
		return docLastName;
	}

	/**
	 * Set the last name of doctor.
	 * 
	 * @param docLastName
	 *            the new value.
	 */
	public void setLastName(String docLastName) {
		this.docLastName = docLastName;
	}

	/**
	 * Getter for the first name of Doctor
	 * 
	 * @return the docFirstName
	 */
	public String getFirstName() {
		return docFirstName;
	}

	/**
	 * Set the first name of doctor.
	 * 
	 * @param docFirstName
	 *            the new value.
	 */
	public void setFirstName(String docFirstName) {
		this.docFirstName = docFirstName;
	}

	/**
	 * Getter for the wellness survey result as string
	 * 
	 * @return the String of wellnessSurveyResults
	 */
	public String getWellnessSurveyResults() {
		return String.join(",", Arrays.asList(wellnessSurveyResults));
	}

	/**
	 * Getter for the wellness survey result as string array
	 * 
	 * @return the String array of wellnessSurveyResult
	 */
	public String[] getWellnessSurveyResultsInArray() {
		return wellnessSurveyResults;
	}

	/**
	 * Set the wellness survey result with string
	 * 
	 * @param wellnessSurveyResults
	 *            the new value as string
	 */
	public void setWellnessSurveyResults(String wellnessSurveyResults) {
		this.wellnessSurveyResults = wellnessSurveyResults.split(",");
	}

	/**
	 * Getter for the wellness survey score
	 * 
	 * @return the wellnessSurveyScore
	 */
	public long getWellnessSurveyScore() {
		return wellnessSurveyScore;
	}

	/**
	 * Set the wellness survey score
	 * 
	 * @param wellnessSurveyScore
	 *            the new value
	 */
	public void setWellnessSurveyScore(long wellnessSurveyScore) {
		this.wellnessSurveyScore = wellnessSurveyScore;
	}

	/**
	 * Getter for the exercise list as string
	 * 
	 * @return the String of exercise
	 */
	public String getExercise() {
		return String.join(",", Arrays.asList(exercise));
	}

	/**
	 * Getter for the exercise list as string array
	 * 
	 * @return the String array of exercise
	 */
	public String[] getExerciseInArray() {
		return exercise;
	}

	/**
	 * Set Exercise with string data
	 * 
	 * @param exercise
	 *            the new value as string
	 */
	public void setExercise(String exercise) {
		this.exercise = exercise.split(",");
	}

	/**
	 * Method used to compute the hashcode for a PhysicalTherapyOVRecordBean.
	 */
	@Override
	public int hashCode() {
		final int prime = 173;
		int result = 1;
		result = prime * result + ((docFirstName == null) ? 0 : docFirstName.hashCode());
		result = prime * result + ((docLastName == null) ? 0 : docLastName.hashCode());
		result = prime * result + (int) (mid ^ (mid >>> 32));
		result = prime * result + (int) (oid ^ (oid >>> 32));
		result = prime * result + ((visitDate == null) ? 0 : visitDate.hashCode());

		for (int i = 0; i < wellnessSurveyResults.length; i++) {
			result = prime * result + Integer.valueOf(wellnessSurveyResults[i]);
		}

		result = prime * result + (int) (wellnessSurveyScore ^ (wellnessSurveyScore >>> 32));

		for (int i = 0; i < exercise.length; i++) {
			if (Boolean.valueOf(exercise[i])) {
				result = prime * result + 1;
			} else {
				result = prime * result + 2;
			}
		}

		return result;
	}

	/**
	 * Method used to determine if PhysicalTherapyOVRecordBeans are equal.
	 * 
	 * @return true if the PhysicalTherapyOVRecordBeans are equal, false
	 *         otherwise.
	 */
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

package edu.ncsu.csc.itrust.beans;

public class PhysicalTherapyVisitBean {
	private long visitID;
	private long patientID;
	private long hcpID;
	private long[] wellnessSurvey;
	private long wellnessSurveyScore;
	private boolean[] exercise;
	
	public PhysicalTherapyVisitBean() {
	}
	
	public long getVisitID() {
		return visitID;
	}
	
	public void setVisitID(long visitID) {
		this.visitID = visitID;
	}
	
	public long getPatientID() {
		return patientID;
	}
	
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	
	public long getHcpID() {
		return hcpID;
	}
	
	public void setHcpID(long hcpID) {
		this.hcpID = hcpID;
	}
	
	public long[] getWellnessSurvey() {
		return wellnessSurvey;
	}
	
	public void setWellnessSurvey(String wellnessSurvey) {
		String[] splitted = wellnessSurvey.split(",");
		this.wellnessSurvey = new long[splitted.length];
		
		for (int i = 0; i < splitted.length; i++) {
			this.wellnessSurvey[i] = Long.valueOf(splitted[i]);
		}
	}
	
	public long getWellnessSurveyScore() {
		return wellnessSurveyScore;
	}
	
	public void setWellnessSurveyScore(long wellnessSurveyScore) {
		this.wellnessSurveyScore = wellnessSurveyScore;
	}
	
	public boolean[] getExercise() {
		return exercise;
	}
	
	public void setExercise(String exercise) {
		String[] splitted = exercise.split(",");
		this.exercise = new boolean[splitted.length];
		
		for (int i = 0; i < splitted.length; i++) {
			this.exercise[i] = Boolean.valueOf(splitted[i]);
		}
	}	
}



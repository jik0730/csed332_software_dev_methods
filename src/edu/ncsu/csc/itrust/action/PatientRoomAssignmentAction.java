package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.WardRoomBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.WardDAO;
import edu.ncsu.csc.itrust.exception.DBException;

public class PatientRoomAssignmentAction {
	/**
	 * DAOFactory to use with the WardDAO
	 */
	WardDAO wardDAO = null;
	
	public PatientRoomAssignmentAction(DAOFactory factory){
		wardDAO = new WardDAO(factory);
	}
	
	public void assignPatientToRoom(WardRoomBean wardRoom, long patientMID) throws DBException{
		wardRoom.setOccupiedBy(patientMID);
		wardRoom.setWaiting(null);
		wardRoom.setAccepted(true);
		wardDAO.updateWardRoomOccupant(wardRoom);
		wardDAO.updateWardRoomWaiting(wardRoom);
		wardDAO.updateWardRoomState(wardRoom);
	}
	
	public void assignPatientToRoom(WardRoomBean wardRoom, PatientBean patient) throws DBException{
		assignPatientToRoom(wardRoom, patient.getMID());
	}
	
	public void removePatientFromRoom(WardRoomBean wardRoom, String reason) throws DBException{
		long mid = wardRoom.getOccupiedBy();
		wardDAO.checkOutPatientReason(mid, reason);
		wardRoom.setOccupiedBy(null);
		wardDAO.updateWardRoomOccupant(wardRoom);
		
	}
	
	public void removeWaitingFromRoom(WardRoomBean wardRoom) throws DBException{
		wardRoom.setWaiting(null);
		wardRoom.setAccepted(true);
		wardDAO.updateWardRoomWaiting(wardRoom);
		wardDAO.updateWardRoomState(wardRoom);
		
	}
	
	public void requestPatientToRoom(WardRoomBean wardRoom, long patientMID) throws DBException{
		wardRoom.setAccepted(false);
		wardRoom.setWaiting(patientMID);
		wardDAO.updateWardRoomWaiting(wardRoom);
		wardDAO.updateWardRoomState(wardRoom);
		
	}	
	
}

<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.*"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.HospitalBean"%>
<%@page import="edu.ncsu.csc.itrust.action.UpdateHospitalListAction"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@page import="edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.WardBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.WardRoomBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.WardDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>

<%@page import="edu.ncsu.csc.itrust.action.EditPersonnelAction"%>
<%@page import="edu.ncsu.csc.itrust.action.PatientRoomAssignmentAction"%>


<%
	String patientID = request.getParameter("patientIDSearch");
	String forwardPatientSearch = request.getParameter("forwardPatientSearch");
%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View Wards Change Request";
%>

<%@include file="/header.jsp"%>

<%
	long personnelMID = loggedInMID.longValue();

	WardDAO wardDAO = new WardDAO(prodDAO);
	PatientDAO patientDAO = new PatientDAO(prodDAO);

	//Get a list of all the hospitals
	HospitalsDAO hospitalsDAO = new HospitalsDAO(prodDAO);
	List<HospitalBean> hospitals = hospitalsDAO.getAllHospitals();

	//Get a list of the hospitals assinged to the current HCP
	PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);
	List<HospitalBean> hospitalsForHCP = personnelDAO.getHospitals(personnelMID);

	// Get a list of the wards the HCP is assigned to
	List<WardBean> wardsForHCP = wardDAO.getAllWardsByHCP(personnelMID);

	String hospital = null;
	Boolean roomState = Boolean.valueOf(false);

	// string to know which thing was searched by after clicking a button to update something
	String searchedBy = "";
	Boolean valueSearchedBy = null;
	String parameterName = "";

	//Create Patient Room Assignment Action object
	PatientRoomAssignmentAction praa = new PatientRoomAssignmentAction(prodDAO);

	if (forwardPatientSearch != null) {
		WardRoomBean wardRoom = wardDAO.getWardRoom(forwardPatientSearch);
		hospital = wardDAO.getHospitalByWard(forwardPatientSearch).getHospitalID();
		for (WardBean ward : wardDAO.getAllWardsByHospitalID(hospital)) {
			for (WardRoomBean room : wardDAO.getAllWardRoomsByWardID(ward.getWardID())) {
				if (room.getOccupiedBy() == Long.parseLong(patientID)) {
					praa.removePatientFromRoom(room, "Moved Patient");
				}
			}
		}
		praa.assignPatientToRoom(wardRoom, Long.parseLong(patientID));
		loggingAction.logEvent(TransactionType.PATIENT_ASSIGNED_TO_ROOM, loggedInMID, 0, "");
		//hospital = "";
	}

	String removePatient = request.getParameter("removePatient");
	String removePatientFromThisRoom = request.getParameter("removeRoomWard");

	String changeStatus = request.getParameter("changeStatus");
	// a room id
	String roomChangeStatus = request.getParameter("roomChangeStatus");

	//a room id
	String removePatientRoomID = request.getParameter("removePatientRoomID");

	//get hospital bean for the hospital that was selected
	HospitalBean currentHospital = null;

	if (hospital != null) {
		currentHospital = hospitalsDAO.getHospital(hospital);
	}

	// Change the status of a WardRoom ---> Make accept form and change
	if (changeStatus != null && request.getParameter("statusToChange") != null
			&& !request.getParameter("statusToChange").equals("")) {
		WardRoomBean statusUpdate = wardDAO.getWardRoom(roomChangeStatus);
		statusUpdate.setStatus(request.getParameter("statusToChange"));
		wardDAO.updateWardRoom(statusUpdate);
	}

	// Remove a patient ---> make Denied Form and change
	if (removePatient != null) {
		WardRoomBean remove = wardDAO.getWardRoom(removePatientRoomID);
		String reason = request.getParameter("reason");
		praa.removeWaitingFromRoom(remove);
		loggingAction.logEvent(TransactionType.PATIENT_REMOVED_FROM_ROOM, loggedInMID, 0, "");
	}

	//Get a list of all the WardRoomBeans that fit the search critera
	List<WardRoomBean> listOfRooms = listOfRooms = wardDAO.getWardRoomsByState(roomState, personnelMID);
	searchedBy = "searchState";
	valueSearchedBy = roomState;
	parameterName = "searchbyroomState";
%>


<%
	// Display results
	if (listOfRooms != null && !listOfRooms.isEmpty()) {

		// check if these rooms are full and if it is then log it
		boolean vacant = false;
		for (WardRoomBean room : listOfRooms) {
			if (room.getOccupiedBy() == null) {
				vacant = true;
			}
		}

		if (!vacant) {
			//log if those rooms were full
			loggingAction.logEvent(TransactionType.ROOMS_FULL, loggedInMID, 0, "");

		}
%>
<hr>
<table class="fTable" align="center">
	<tr>
		<th colspan="9">Results</th>
		<th colspan="2">Options</th>
	</tr>
	<tr class="subHeader">
		<td>Room Name</td>
		<td>Patient</td>
		<td>Requester</td>
		<td>Ward</td>
		<td>Status</td>
		<td>Hospital</td>
		<td>Price</td>
		<td>Size</td>
		<td>Occupied</td>
		<td>Accept</td>
		<td>Reject</td>
	</tr>
	<%
		for (WardRoomBean room : listOfRooms) {
	%>
	<tr>
		<td><%=room.getRoomName()%></td>
		<%
			if (room.getOccupiedBy() == 0 || !patientDAO.checkPatientExists(room.getOccupiedBy())) {
		%>
		<td>Unoccupied</td>
		<%
			} else {
		%>
		<td><%=patientDAO.getName(room.getOccupiedBy())%></td>
		<%
			}
		%>
		<td><%=patientDAO.getName(room.getWaiting())%></td>
		<td><%=wardDAO.getWard("" + room.getInWard()).getRequiredSpecialty()%></td>
		<td><%=room.getStatus()%></td>
		<td><%=wardDAO.getHospitalByWard(room.getRoomID() + "").getHospitalName()%></td>
		<td><%=room.getPrice()%></td>
		<td><%=room.getSize()%></td>
		<td><%=wardDAO.getNumberOfPatientsInWardRoom(room.getRoomID())%> / <%=room.getSize()%></td>
		<td>
			<form id="mainForm" method="post"
				action=<%="http://localhost:8080/iTrust/auth/hcp/viewWardRequests.jsp?patientIDSearch="
							+ room.getWaiting() + "&forwardPatientSearch=" + room.getRoomID()
							+ "&searchHospitals=true"%>>
				<input type="submit" value="Accept" name="assignPatient"
					<%if (room.getOccupiedBy() != 0) {%> disabled <%}%> /> <input
					type="hidden" value=<%=valueSearchedBy%> name=<%=searchedBy%> />
			</form>
		</td>
		<td align="center">
			<form id="mainForm" method="post"
				action=<%="viewWardRequests.jsp?" + parameterName + "=" + valueSearchedBy + "&" + searchedBy
							+ "=true"%>>
				<input type="hidden" value="<%=room.getRoomID()%>"
					name="removePatientRoomID" /> <input type="hidden"
					value=<%=valueSearchedBy%> name=<%=searchedBy%> /> <input
					type="submit" value="Reject" name="removePatient" />
			</form>
		</td>
	</tr>
	<%
		}
	%>
</table>

<%
	} else {
%>
<br>
<div align=center>No Results</div>
<%
	}
%>

<%@include file="/footer.jsp"%>

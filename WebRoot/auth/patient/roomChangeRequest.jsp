<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="java.net.URLEncoder" %>
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
	String patientID = (String) session.getAttribute("pid");
    session.removeAttribute("pid");
    String forwardPatientSearch = request.getParameter("forwardPatientSearch");
    String forwardPatientSearch2 = request.getParameter("forwardPatientSearch2");
%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Room Change Request";
%>

<%@include file="/header.jsp" %>

<%

long personnelMID = loggedInMID.longValue();

WardDAO wardDAO = new WardDAO(prodDAO);
PatientDAO patientDAO = new PatientDAO(prodDAO);
boolean is_hospital = true;
boolean is_ward = true;
boolean is_wardRoom = true;

String rqhospital = null;
// Get a hospital that the loggedin patient assigned.
HospitalBean hospital = wardDAO.getHospitalByPatientID(personnelMID);
if (hospital == null) {
	// TODO: A patient cannot request a room change.
	is_hospital = false;
}
//System.out.println("hospital??" + hospital.getHospitalCity());

// Get a ward that the loggedin patient stays.
WardBean ward = wardDAO.getWardByPid(personnelMID);
if (ward == null) {
	// TODO: A patient cannot request a room change.
	is_ward = false;
}
//System.out.println("hospital??" + is_ward);

//Get a wardroom that the loggedin patient stays.
WardRoomBean wardRoom = wardDAO.getWardRoomByPid(personnelMID);
if (wardRoom == null) {
	// TODO: A pateient cannot request a room change.
	is_wardRoom = false;
}
//System.out.println("hospital??" + is_wardRoom);

// Get the submitted parameters to search by
String searchPrice = request.getParameter("searchbyroomPrice");
String searchSize = request.getParameter("searchbyroomSize");

// this is the name of Search Button
String searchRooms = request.getParameter("searchRooms");



PatientRoomAssignmentAction praa = new PatientRoomAssignmentAction(prodDAO);

if (forwardPatientSearch != null) {
	WardRoomBean wardRoom2 = wardDAO.getWardRoom(forwardPatientSearch);
	WardRoomBean wardRoom3 = wardDAO.getWardRoomByWaiting(personnelMID);
	if (wardRoom3 != null) praa.removeWaitingFromRoom(wardRoom3);
	if (wardRoom2 != null) praa.requestPatientToRoom(wardRoom2, personnelMID);
	loggingAction.logEvent(TransactionType.PATIENT_ASSIGNED_TO_ROOM, loggedInMID, 0, "");
	//hospital = "";
}


if (forwardPatientSearch2 != null) {
	WardRoomBean wardRoom4 = wardDAO.getWardRoom(forwardPatientSearch2);
	praa.removeWaitingFromRoom(wardRoom4);
}



// List of wardrooms which will be used in the table of results or recommandation.
List<WardRoomBean> listOfRooms = null;
WardRoomBean listOfAlreday = wardDAO.getWardRoomByPid(personnelMID);
// If search request was sent, find wardrooms with restrictions.
if (searchRooms != null) {
	if (searchPrice.equals("All Price") && searchSize.equals("All Size")) {
		listOfRooms = wardDAO.getAllWardRoomsBySpecialty(personnelMID);
	} else if (searchPrice.equals("All Price")){
		int size;
		if (searchSize.equals("1s")) {
			size = 1;
		} else if (searchSize.equals("2s")) {
			size = 2;
		} else if (searchSize.equals("3s")) {
			size = 4;
		} else {
			size = 8;
		}
		listOfRooms = wardDAO.getAllWardRoomsBySpecialtyBySize(personnelMID, size);
	} else if (searchSize.equals("All Size")) {
		int l_price, u_price;
		if (searchPrice.equals("1p")) {
			l_price = 0;
			u_price = 25;
		} else if (searchPrice.equals("2p")) {
			l_price = 25;
			u_price = 50;
		} else if (searchPrice.equals("3p")) {
			l_price = 50;
			u_price = 100;
		} else {
			l_price = 100;
			u_price = Integer.MAX_VALUE;
		}
		listOfRooms = wardDAO.getAllWardRoomsBySpecialtyByPrice(personnelMID, l_price, u_price);
	} else {
		int size, l_price, u_price;
		if (searchPrice.equals("1p")) {
			l_price = 0;
			u_price = 25;
		} else if (searchPrice.equals("2p")) {
			l_price = 25;
			u_price = 50;
		} else if (searchPrice.equals("3p")) {
			l_price = 50;
			u_price = 100;
		} else {
			l_price = 100;
			u_price = Integer.MAX_VALUE;
		}
		if (searchSize.equals("1s")) {
			size = 1;
		} else if (searchSize.equals("2s")) {
			size = 2;
		} else if (searchSize.equals("3s")) {
			size = 4;
		} else {
			size = 8;
		}
		listOfRooms = wardDAO.getAllWardRoomsBySpecialtyBySizeByPrice(personnelMID, size, l_price, u_price);
	}
	
} else {
	if (is_wardRoom) {
		listOfRooms = wardDAO.getAllWardRoomsBySystemRecommanded(personnelMID, wardRoom.getPrice());
	} else {
		listOfRooms = wardDAO.getAllWardRoomsBySystemRecommanded(personnelMID, 50);
	}
}








%>
	<form id="mainForm" method="post" action="roomChangeRequest.jsp" align="center">
	<table class="fTable" align="center">
		<tr>
			<th colspan="2">Search for a Room 
			(Hospital: 
			<%if(is_hospital) { %>
				<%=hospital.getHospitalID()%>
			<%} else { %>
				None, 
			<%}%>
			Specialty: 
			<%if(is_ward) { %>
				<%=ward.getRequiredSpecialty()%>
			<%} else { %>
				None
			<%}%>)</th>
		</tr>
		<tr class="subHeader">
			<td>Price Range</td>
			<td>Size</td>
		</tr>
		<tr>
			<td>
				<select name="searchbyroomPrice">
					<option value="All Price">All Price</option>
					<option value="1p">$0 ~ 25</option>
					<option value="2p">$25 ~ 50</option>
					<option value="3p">$50 ~ 100</option>
					<option value="4p">Over $100</option>
				</select>
			</td>
			
			<td>
				<select name="searchbyroomSize">
					<option value = "All Size">All Size</option>
					<option value="1s">1 Person</option>
					<option value="2s">2 People</option>
					<option value="3s">4 People</option>
					<option value="4s">8 People</option>
				</select>
			</td>

		</tr>
		
		<tr>
			<td colspan="2">
				<input type="submit" value="Search" name="searchRooms" />
			</td>
		</tr>
	</table>
	</form>
	<br>
	
	
	
<%
	if(listOfAlreday != null){
	%>
	<hr>
	<table class="fTable" align="center">
		<tr>
			<th colspan="8">Already Assigned Rooms</th>
		</tr>
		<tr class="subHeader">
			<td>Room Name</td>
			<td>Ward</td>
			<td>Specialty</td>
			<td>Status</td>
			<td>Price</td>
			<td>Size</td>
			<!-- <td>Occupied</td> -->
		</tr>
		<tr>
			<td><%=listOfAlreday.getRoomName() %></td>
			<td><%=wardDAO.getWard("" + listOfAlreday.getInWard()).getWardID()%></td>
			<td><%=wardDAO.getSpecialtyOfWard(String.valueOf(listOfAlreday.getInWard())) %></td>
			<td><%=listOfAlreday.getStatus()%></td>
			<td><%=listOfAlreday.getPrice()%></td>
			<td><%=listOfAlreday.getSize()%></td>
			<%-- <td><%=wardDAO.getNumberOfPatientsInWardRoom(listOfAlreday.getRoomID())%> / <%=listOfAlreday.getSize()%></td> --%>
		</tr>
	</table>
	
	<% } else {
		%>
		<br>
		<div align=center>No Already Assigned Rooms</div>
		<%
	}
%>
	
	
	
	
	
	
	
	
	
	
	
	

<%
// Display results
if(searchPrice != null || searchSize != null){
	if(listOfRooms != null && !listOfRooms.isEmpty()){

		
	%>
	<hr>
	<table class="fTable" align="center">
		<tr>
			<th colspan="9">Results</th>
		</tr>
		<tr class="subHeader">
			<td>Room Name</td>
			<td>Ward</td>
			<td>Specialty</td>
			<td>Status</td>
			<td>Price</td>
			<td>Size</td>
			<!-- <td>Occupied</td> -->
			<td>Request</td>
			<td>Cancel</td>
		</tr>
		<%for(WardRoomBean room: listOfRooms){ %>
		<tr>
			<td><%=room.getRoomName() %></td>
			<td><%=wardDAO.getWard("" + room.getInWard()).getWardID()%></td>
			<td><%=wardDAO.getSpecialtyOfWard(String.valueOf(room.getInWard())) %></td>
			<td><%=room.getStatus()%></td>
			<td><%=room.getPrice()%></td>
			<td><%=room.getSize()%></td>
			<%-- <td><%=wardDAO.getNumberOfPatientsInWardRoom(room.getRoomID())%> / <%=room.getSize()%></td> --%>
			<td align="center">
			<!-- Need to actually request room change. -->
			<%if(is_hospital && is_ward && is_wardRoom) { %>
				<form id="mainForm" method="post" action=<%="http://localhost:8080/iTrust/auth/patient/roomChangeRequest.jsp?forwardPatientSearch="+room.getRoomID()%>">
				<input type="submit" value="Request" name="requestRoomChange"
				<%if (room.getState() != true) {%> disabled <%}%> />
				</form>
			<%}%>
			</td>
			<td align="center">
			<!-- Need to actually cancel room change. -->
			<%if(is_hospital && is_ward && is_wardRoom) { %>
				<form id="mainForm" method="post" action=<%="http://localhost:8080/iTrust/auth/patient/roomChangeRequest.jsp?forwardPatientSearch2="+room.getRoomID()%>">
				<input type="submit" value="Cancel" name="requestCancel"
				<%if (room.getWaiting() != personnelMID) {%> disabled <%}%> />
				</form>
			<%}%>
			</td>
		</tr>
		<%}%>
	</table>
	
	
	<% } else {
		%>
		<br>
		<div align=center>No Results</div>
		<%
	}


} else { %>
	<hr>
	<table class="fTable" align="center">
		<tr>
			<th colspan="9">Recommended Affordable Rooms</th>
		</tr>
		<tr class="subHeader">
			<td>Room Name</td>
			<td>Ward</td>
			<td>Specialty</td>
			<td>Status</td>
			<td>Price</td>
			<td>Size</td>
			<!-- <td>Occupied</td> -->
			<td>Request</td>
			<td>Cancel</td>
		</tr>
		<%for(WardRoomBean room: listOfRooms){ %>
		<tr>
			<td><%=room.getRoomName() %></td>
			<td><%=wardDAO.getWard("" + room.getInWard()).getWardID()%></td>
			<td><%=wardDAO.getSpecialtyOfWard(String.valueOf(room.getInWard())) %></td>
			<td><%=room.getStatus()%></td>
			<td><%=room.getPrice()%></td>
			<td><%=room.getSize()%></td>
			<%-- <td><%=wardDAO.getNumberOfPatientsInWardRoom(room.getRoomID())%> / <%=room.getSize()%></td> --%>
			<td align="center">
			<!-- Need to actually request room change. -->
			<%if(is_hospital && is_ward && is_wardRoom) { %>
				<form id="mainForm" method="post" action=<%="http://localhost:8080/iTrust/auth/patient/roomChangeRequest.jsp?forwardPatientSearch=" + room.getRoomID()%>">
				<input type="submit" value="Request" name="requestRoomChange"
				<%if (room.getState() != true) {%> disabled <%}%> />
				</form>
			<%}%>
			</td>
			<td align="center">
			<!-- Need to actually cancel room change. -->
			<%if(is_hospital && is_ward && is_wardRoom) { %>
				<form id="mainForm" method="post" action=<%="http://localhost:8080/iTrust/auth/patient/roomChangeRequest.jsp?forwardPatientSearch2=" + room.getRoomID()%>">
				<input type="submit" value="Cancel" name="requestCancel"
				<%if (room.getWaiting() != personnelMID) {%> disabled <%}%> />
				</form>
			<%}%>
			</td>
		</tr>
		<%}%>
	</table>
<%}%>

<%@include file="/footer.jsp" %>

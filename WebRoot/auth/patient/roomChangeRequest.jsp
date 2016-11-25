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

// Get a hospital that the loggedin patient assigned.
HospitalBean hospital = wardDAO.getHospitalByPatientID(personnelMID);
if (hospital == null) {
	// TODO: A patient cannot request a room change.
}

// Get a ward that the loggedin patient stays.
WardBean ward = wardDAO.getWardByPid(personnelMID);
if (ward == null) {
	// TODO: ...
}

// Get the submitted parameters to search by
String searchPrice = request.getParameter("searchbyroomPrice");
String searchSize = request.getParameter("searchbyroomSize");

// this is the name of Search Button
String searchRooms = request.getParameter("searchRooms");

// List of wardrooms which will be used in the table of results or recommandation.
List<WardRoomBean> listOfRooms = null;

// If search request was sent, find wardrooms with restrictions.
if (searchRooms != null) {
	// TODO: Search rooms conditioned by price or size.
	if (searchPrice.equals("All Price") && searchSize.equals("All Size")) {
		listOfRooms = wardDAO.getAllWardRoomsBySpecialty(personnelMID);
	} else if (searchPrice.equals("All Price")){
		// TODO: To be implemented after corresponding DAO implemented.
	} else if (searchSize.equals("All Size")) {
		// TODO: To be implemented after corresponding DAO implemented.
	} else {
		// TODO: To be implemented after corresponding DAO implemented.
	}
	
} else {
	// TODO: Recommended affordable rooms
	// TODO: At present, this will make the list of all wardrooms for purpose of testing.
	listOfRooms = wardDAO.getAllWardRoomsBySpecialty(personnelMID);
}



%>
	<form id="mainForm" method="post" action="manageWards.jsp" align="center">
	<table class="fTable" align="center">
		<tr>
			<th colspan="2">Search for a Room 
			(Hospital: <%=hospital.getHospitalID()%>, 
			Specialty: <%=ward.getRequiredSpecialty()%>)</th>
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
					<option value="4p">$50 ~ 100</option>
					<option value="8p">Over $100</option>
				</select>
			</td>
			
			<td>
				<select name="searchbyroomSize">
					<option value = "All Size">All Size</option>
					<option value="1s">1 Person</option>
					<option value="2s">2 People</option>
					<option value="4s">4 People</option>
					<option value="8s">8 People</option>
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
// Display results
if(searchPrice != null || searchSize != null){
	if(listOfRooms != null && !listOfRooms.isEmpty()){

		/* // check if these rooms are full and if it is then log it
		boolean vacant = false;
		for(WardRoomBean room: listOfRooms){
			if(room.getOccupiedBy() == null){
				vacant = true;
			}
		}
		
		if(!vacant){
		//log if those rooms were full
		loggingAction.logEvent(TransactionType.ROOMS_FULL, loggedInMID, 0, "");
			
			
		}  */
		
	%>
	<hr>
	<table class="fTable" align="center">
		<tr>
			<th colspan="7">Recommended Affordable Rooms</th>
		</tr>
		<tr class="subHeader">
			<td>Room Name</td>
			<td>Ward</td>
			<td>Status</td>
			<td>Price</td>
			<td>Size</td>
			<td>Occupied</td>
			<td>Request</td>
		</tr>
		<%for(WardRoomBean room: listOfRooms){ %>
		<tr>
			<td><%=room.getRoomName() %></td>
			<td><%=wardDAO.getWard("" + room.getInWard()).getWardID()%></td>
			<td><%=room.getStatus()%></td>
			<td><%// TODO: get price %></td>
			<td><%// TODO: get size %></td>
			<td><%// TODO: occupied ex. 2/4 %></td>
			<td align="center">
				<form id="mainForm" method="post" action=""<%//="manageWards.jsp?"+ parameterName +"=" + valueSearchedBy + "&"+searchedBy+"=true"%>>
				<input type="submit" value="Request" name="requestRoomChange" />
				</form>
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
			<th colspan="7">Recommended Affordable Rooms</th>
		</tr>
		<tr class="subHeader">
			<td>Room Name</td>
			<td>Ward</td>
			<td>Status</td>
			<td>Price</td>
			<td>Size</td>
			<td>Occupied</td>
			<td>Request</td>
		</tr>
		<%for(WardRoomBean room: listOfRooms){ %>
		<tr>
			<td><%=room.getRoomName() %></td>
			<td><%=wardDAO.getWard("" + room.getInWard()).getWardID()%></td>
			<td><%=room.getStatus()%></td>
			<td><%// TODO: get price %></td>
			<td><%// TODO: get size %></td>
			<td><%// TODO: occupied ex. 2/4 %></td>
			<td align="center">
				<form id="mainForm" method="post" action=""<%//="manageWards.jsp?"+ parameterName +"=" + valueSearchedBy + "&"+searchedBy+"=true"%>>
				<input type="submit" value="Request" name="requestRoomChange" />
				</form>
			</td>
		</tr>
		<%}%>
	</table>
<%}%>

<%@include file="/footer.jsp" %>

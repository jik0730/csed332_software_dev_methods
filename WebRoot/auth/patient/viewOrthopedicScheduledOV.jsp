<%@page import="edu.ncsu.csc.itrust.dao.mysql.ApptTypeDAO"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewOrthopedicScheduleOVAction"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPersonnelAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicScheduleOVRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.OrthopedicScheduleOVDAO"%>
<%@page import="java.util.List"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Scheduled Orthopedic Office Visit";
%>

<%@include file="/header.jsp" %>
<%
	PatientDAO pDAO = prodDAO.getPatientDAO();
	ViewOrthopedicScheduleOVAction scheduleAction = new ViewOrthopedicScheduleOVAction(prodDAO, loggedInMID);
	OrthopedicScheduleOVRecordBean original = null;
	
	if (request.getParameter("oid") != null) {
		String aptParameter = request.getParameter("oid");
		try {
			int oid = Integer.parseInt(aptParameter);
			original = scheduleAction.getOrthopedicScheduleOVForPatient(oid);
			if (original == null){
				response.sendRedirect("viewOrthopedicScheduleOV.jsp");
			}
		} catch (NullPointerException npe) {
			response.sendRedirect("viewOrthopedicScheduleOV.jsp");
		} catch (NumberFormatException e) {
			response.sendRedirect("viewOrthopedicScheduleOV.jsp");
		}
	} else {
		response.sendRedirect("viewOrthopedicScheduleOV.jsp");
	}
	
	if (original != null) {
		if (loggedInMID == original.getPatientmid()) {
			Date d = new Date(original.getDate().getTime());
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			
			String status = "";
			if(original.isPending()){
				status = "Pending";
			}else if(original.isAccepted()){
				status = "Accepted";
			}else{
				status = "Rejected";
			}
%>
			<div>
				<table width="99%">
					<tr>
						<th>Scheduled Orthopedic Office Visit Info</th>
					</tr>
					<tr>
						<td><b>Doctor:</b> <%= StringEscapeUtils.escapeHtml("" + ( original.getDocFirstName() + " " +original.getDocLastName() )) %></td>
					</tr>
					<tr>
						<td><b>Date/Time:</b> <%= StringEscapeUtils.escapeHtml("" + ( format.format(d) )) %></td>
					</tr>
					<tr>
						<td><b>Status:</b> <%= StringEscapeUtils.escapeHtml("" + ( status )) %></td>
					</tr>
				</table>
			</div>
			
			<table>
				<tr>
					<td colspan="2"><b>Comments:</b></td>
				</tr>
				<tr>
					<td colspan="2"><%= StringEscapeUtils.escapeHtml("" + ( (original.getComment()== null)?"No Comment":original.getComment() )) %></td>
				</tr>
			</table>
<%
		} else {
%>
		<div align=center>
			<span class="iTrustError">You are not authorized to view details of this appointment.</span>
		</div>
<%
		}
	}
%>

<%@include file="/footer.jsp" %>

<%@page import="edu.ncsu.csc.itrust.dao.mysql.ApptTypeDAO"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPhysicalTherapyScheduleOVAction"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPersonnelAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.PhysicalTherapyScheduleOVRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PhysicalTherapyScheduleOVDAO"%>
<%@page import="java.util.List"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Scheduled PhysicalTherapy Office Visit";
%>

<%@include file="/header.jsp" %>
<%
	//if specialty is not oph or opt, simply redirect them to the regular edit office visit page
	ViewPersonnelAction personnelAction = new ViewPersonnelAction(prodDAO, loggedInMID);
	PersonnelBean currentPersonnel = personnelAction.getPersonnel("" + loggedInMID);
	if (!currentPersonnel.getSpecialty().equalsIgnoreCase("physicaltherapist")) {
		response.sendRedirect("/iTrust/auth/hcp/viewMyAppts.jsp");
	}
%>

<%
	PatientDAO pDAO = prodDAO.getPatientDAO();
	ViewPhysicalTherapyScheduleOVAction scheduleAction = new ViewPhysicalTherapyScheduleOVAction(prodDAO, loggedInMID);
	PhysicalTherapyScheduleOVRecordBean original = null;
	
	if (request.getParameter("oid") != null) {
		String aptParameter = request.getParameter("oid");
		try {
			int oid = Integer.parseInt(aptParameter);
			original = scheduleAction.getPhysicalTherapyScheduleOVForHCP(oid);
			if (original == null){
				response.sendRedirect("viewPhysicalTherapyScheduleOV.jsp");
			}
		} catch (NullPointerException npe) {
			response.sendRedirect("viewPhysicalTherapyScheduleOV.jsp");
		} catch (NumberFormatException e) {
			response.sendRedirect("viewPhysicalTherapyScheduleOV.jsp");
		}
	} else {
		response.sendRedirect("viewPhysicalTherapyScheduleOV.jsp");
	}
	
	if (original != null) {
		if (loggedInMID == original.getDoctormid()) {
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
						<th>Scheduled PhysicalTherapy Office Visit Info</th>
					</tr>
					<tr>
						<td><b>Patient:</b> <%= StringEscapeUtils.escapeHtml("" + ( pDAO.getName(original.getPatientmid()) )) %></td>
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
			<span class="iTrustError">You are not authorized to view details of this appointment</span>
		</div>
<%
		}
	}
%>

<%@include file="/footer.jsp" %>

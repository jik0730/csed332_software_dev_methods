<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="edu.ncsu.csc.itrust.action.EditApptTypeAction"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyApptsActionUC92"%>
<%@page import="edu.ncsu.csc.itrust.beans.ApptBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.ApptRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.ApptTypeDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View My Messages";
%>

<%@include file="/header.jsp" %>

<div align=center>
	<h2>My Appointments</h2>
<%
	loggingAction.logEvent(TransactionType.APPOINTMENT_ALL_VIEW, loggedInMID.longValue(), 0, ""); //Transaction 후에 변경사항
	
	ViewMyApptsActionUC92 action = new ViewMyApptsActionUC92(prodDAO, loggedInMID.longValue());
	EditApptTypeAction types = new EditApptTypeAction(prodDAO, loggedInMID.longValue());
	ApptTypeDAO apptTypeDAO = prodDAO.getApptTypeDAO();
	List<ApptRequestBean> appts = action.getMyAppointments();
	session.setAttribute("appts", appts);
	if (appts.size() > 0) {
%>	
	<table class="fancyTable">
		<tr>
			<th>Patient</th>
			<th>Appointment Type</th>
			<th>Appointment Date/Time</th>
			<th>Duration</th>
			<th>Pending</th>
			<th>Comment</th>
			<th></th>
		</tr>
<%		 
		

		List<ApptRequestBean>conflicts = action.getAllConflictsUC92(loggedInMID.longValue());

		int index = 0;
		for(ApptRequestBean a : appts) { 
			String comment = "";
			if(a.getRequestedAppt().getComment() == null)
				comment = "No Comment";
			else
				comment = "<a href='viewAppt.jsp?apt=" + a.getRequestedAppt().getApptID() + "'>Read Comment</a>";
				
			Date d = new Date(a.getRequestedAppt().getDate().getTime());
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			
			String row = "";
			if(conflicts.contains(a))
				row = "<tr style='font-weight: bold;'";
			else
				row = "<tr";
%>
			<%=row+" "+((index%2 == 1)?"class=\"alt\"":"")+">"%>
				<td><%= StringEscapeUtils.escapeHtml("" + ( action.getName(a.getRequestedAppt().getHcp()) )) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + ( a.getRequestedAppt().getApptType() )) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + ( format.format(d) )) %></td>
 				<td><%= StringEscapeUtils.escapeHtml("" + ( apptTypeDAO.getApptType(a.getRequestedAppt().getApptType()).getDuration()+" minutes" )) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + ( a.isPending())) %></td>
				<td><%= comment %></td>
			</tr>
	<%		index ++; %>
	<%	} %>
	</table>
<%	} else { %>
	<div>
		<i>You have no Appointments</i>
	</div>
<%	} %>	
	<br />
</div>

<%@include file="/footer.jsp" %>

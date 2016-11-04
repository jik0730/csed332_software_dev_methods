<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>
<%@page import="edu.ncsu.csc.itrust.action.SendReminderMessageAction" %>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException" %>
<%@page import="java.lang.NumberFormatException" %>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Send Reminder Message";
%>

<%@include file="/header.jsp" %>

<%
	String input = request.getParameter("apptDaysLeftUpperBound"); 
	if (input != null && !input.equals("")) {
		String daysString = request.getParameter("apptDaysLeftUpperBound");
		try {
			long days = Long.valueOf(daysString);
			SendReminderMessageAction action = new SendReminderMessageAction(prodDAO, loggedInMID.longValue());
			action.sendReminderMessage(days);
%>
			<span class="iTrustMessage"><%=StringEscapeUtils.escapeHtml("Succeeded") %></span>
<%		
		}
		catch (NumberFormatException | ITrustException e) {
%>
			<span class="iTrustError"><%=StringEscapeUtils.escapeHtml("Failed") %></span>
<%
		}
	}
%>

<div class="page-header"><h1>Send Reminder Message</h1></div>

<form method="post">
<table class="fTable">
	<tr>
		<th colspan=2>HCP Information</th>
	</tr>
	<tr>
		<td class="subHeaderVertical">within:</td>
		<td><input type="text" name="apptDaysLeftUpperBound"></td>
	</tr>
</table>
<br />
<input type="submit" style="font-size: 16pt; font-weight: bold;" value="Send">
<br />
</form>

<%@include file="/footer.jsp" %>

<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View System Remider Message ";
session.setAttribute("outbox",true);
session.setAttribute("isHCP",userRole.equals("hcp"));
session.setAttribute("isReminder", true);
%>

<%@include file="/header.jsp" %>

<div align=center>
	<h2>My Messages</h2>
		<%
			if (userRole.equals("admin"))
		%>
				<%@ include file="/auth/mailbox.jsp" %>
</div>

<%@include file="/footer.jsp" %>

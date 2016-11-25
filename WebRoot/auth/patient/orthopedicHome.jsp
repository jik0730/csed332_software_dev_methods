<%@page import="java.util.LinkedList"%>
<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPersonnelAction"%>
<!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.enums.PregnancyStatus"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewOrthopedicOVAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Orthopedic";
%>

<%@include file="/header.jsp"%>
<%
	ViewOrthopedicOVAction viewAction = new ViewOrthopedicOVAction(prodDAO, loggedInMID);
	List<OrthopedicOVRecordBean> patientOVBeans = viewAction.getOrthopedicOVByMID(loggedInMID);
	List<PatientBean> dependents = viewAction.getDependents(loggedInMID);

	List<OrthopedicOVRecordBean> dependentBeans = null;
	String depIndex = request.getParameter("selectedDependent");
	String dependentMID = "";
	if (depIndex != null && request.getParameter("submit") != null) {
		dependentMID = String.valueOf(dependents.get(Integer.parseInt(depIndex)).getMID());
		viewAction = new ViewOrthopedicOVAction(prodDAO, Long.parseLong(dependentMID));
		dependentBeans = viewAction.getOrthopedicOVByMID(Long.parseLong(dependentMID));
	}
%>

<div align=center>
	<p>
		<%
			if (request.getParameter("viewDependent") == null) {
				out.write("<h3>View Prior Orthopedic Office Visits</h3>");
				if (patientOVBeans != null && patientOVBeans.size() > 0) {
					for (OrthopedicOVRecordBean bean : patientOVBeans) {
						if (bean != null) {
							out.write("<a href=\"/iTrust/auth/patient/viewOrthopedicOV.jsp?oid=" + bean.getOid() + "\">"
									+ bean.getVisitDateString() + "</a><br />");
						}
					}
				} else {
					out.write("No prior records");
				}
			}
		%>
	</p>
	<p>
		<%
			if (request.getParameter("view") == null) {
				out.write("<h3>View Dependent's Prior Orthopedic Office Visits</h3>");
				if (dependents.size() != 0) {
					if (request.getParameter("viewDependent") == null) {
		%>
	
	<form action="orthopedicHome.jsp" method="post" align="center">
		<%
			} else {
		%>
		<form action="orthopedicHome.jsp?viewDependent" method="post"
			align="center">
			<%
				}
			%>
			<span style="font-size: 12pt"><b>View Orthopedic Office
					Visits of: </b></span><select name="selectedDependent">
				<%
					for (int i = 0; i < dependents.size(); i++) {
				%>
				<option value=<%=String.valueOf(i)%>
					<%=dependentMID.equals(String.valueOf(dependents.get(i).getMID())) ? "selected" : ""%>><%=dependents.get(i).getFullName()%></option>

				<%
					}
				%>
			</select> <input type="submit" value="Submit" id="submit" name="submit"></input>
		</form>
		<%
			if (request.getParameter("submit") != null) {
						if (dependentBeans != null && dependentBeans.size() != 0) {
							for (OrthopedicOVRecordBean bean : dependentBeans) {
								if (bean != null) {
									out.write("<a href=\"/iTrust/auth/patient/viewOrthopedicOV.jsp?viewDependent&oid="
											+ bean.getOid() + "\">" + bean.getVisitDateString() + "</a><br />");
								}
							}
						} else {
							out.write("No prior records");
						}
					}
				} else {
					out.write("User has no dependents");
				}

			}
		%>
		</p>
		<br />
</div>
<%@include file="/footer.jsp"%>
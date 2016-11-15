<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPersonnelAction"%> <!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%> <!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.action.ViewOrthopedicOVAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPatientAction"%> 
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicDiagnosisBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditORDiagnosesAction"%>

<%@include file="/global.jsp"%>

<%pageTitle = "iTrust - View Orthopedic Record";%>

<%@include file="/header.jsp"%>
<%
	/* Require a Patient ID first */
	String pidString = (String) session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=/iTrust/auth/hcp/viewOrthopedicOV.jsp");
		return;
	}
	
	OrthopedicOVRecordBean bean = null;
	PatientBean patient = null;
	
	//get the OrthopedicOVRecordBean given by the URL param
	String oidString = request.getParameter("oid");
	if (oidString != null && !oidString.equals("")) {
		long oid = Long.parseLong(request.getParameter("oid"));
		ViewOrthopedicOVAction viewAction = new ViewOrthopedicOVAction(prodDAO, loggedInMID);
		bean = viewAction.getOrthopedicOVForHCP(oid);
		
		//then grab the associated PatientBean
		ViewPatientAction viewPatient = new ViewPatientAction(prodDAO, loggedInMID, pidString);
		patient = viewPatient.getPatient(pidString);
		
	}
	else {
		throw new ITrustException("Invalid Orthopedic ID passed to the View page");
	}
	
	//now check this bean's status AND the HCP's specialty to see if should redirect to the edit page
	ViewPersonnelAction personnelAction = new ViewPersonnelAction(prodDAO, loggedInMID);
	PersonnelBean currentPersonnel = personnelAction.getPersonnel("" + loggedInMID);
	if (currentPersonnel.getSpecialty().equalsIgnoreCase("Orthopedic")) {
		response.sendRedirect("/iTrust/auth/hcp/editOrthopedicOV.jsp?oid=" + oidString);
	}
%>
<div align=center>
	<form id="status">
	<table class="fTable" align="center">
		<tr><th colspan="3">View Orthopedic Record</th></tr>
		<tr>
			<td width="250px">Patient Name: </td>
			<td width="250px"><% out.write(patient.getFullName()); %></td>
			<td width="50px"></td>
		</tr>
		<%
		//show these if the the status is NOT complete
			out.write("<tr>");
			out.write("<td>Date of visit:</td>");
			out.write("<td>" + bean.getVisitDateString() + "</td>");
			out.write("<td></td>");
			out.write("</tr>");
			
			out.write("<tr>");
			out.write("<td>Injured Limb or joint:</td>");
			out.write("<td>" + bean.getInjured() + "</td>");
			out.write("<td></td>");
			out.write("</tr>");
			
			// TODO: How to display XRay image? Since it is optional, we may need to use if-else.
			out.write("<tr>");
			out.write("<td>X-Ray image:</td>");
			out.write("<td>" + "<img src=\"/iTrust/ImageDataServlet?table=orthopedic&col=XRay&pri=OrthopedicVisitID&key=" + bean.getOid() + "\" />" + "</td>");
			out.write("<td></td>");
			out.write("</tr>");
			
			// TODO: How to display XRay image? Since it is optional, we may need to use if-else.
			out.write("<tr>");
			out.write("<td>MRI image:</td>");
			out.write("<td>" + "<img src=\"/iTrust/ImageDataServlet?table=orthopedic&col=MRI&pri=OrthopedicVisitID&key=" + bean.getOid() + "\" />" + "</td>");
			out.write("<td></td>");
			out.write("</tr>");
			
			out.write("<tr>");
			out.write("<td>MRI Report:</td>");
			out.write("<td>" + bean.getMriReport() + "</td>");
			out.write("<td></td>");
			out.write("</tr>");
				
		out.write("</table><br />"); //end the main table
		%>
	</form>
	
	<table class="fTable" align="center" >
	<tr>
		<th colspan="3">Diagnoses</th>
	</tr>
	<tr  class="subHeader">
		<th>ICD Code</th>
		<th>Description</th>
		<th>URL</th>
	</tr>
	<%
	EditORDiagnosesAction diagAction = new EditORDiagnosesAction(prodDAO,oidString);
	if (diagAction.getDiagnoses().size() == 0) { %>
	<tr>
		<td colspan="3" >No Diagnoses for this visit</td>
	</tr>
	<% } else { 
		for(OrthopedicDiagnosisBean d : diagAction.getDiagnoses()) { String link = d.getURL();%>
		<tr>
			<td ><%= StringEscapeUtils.escapeHtml(d.getICDCode()) %></td>
			<td  style="white-space: nowrap;"><%= StringEscapeUtils.escapeHtml("" + (d.getDescription() )) %></td>
			<td><a href=<%= d.getURL() %>><%= link %></a></td>
		</tr>
	   <%} 
  	   }  %>
</table>
<br /><br />
</div>
<p><br/><a href="/iTrust/auth/hcp/orthopedicHome.jsp">Back to Home</a></p>
<%@include file="/footer.jsp" %>

<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPersonnelAction"%> <!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%> <!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.action.ViewOrthopedicOVAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPatientAction"%> 
<%@page import="edu.ncsu.csc.itrust.action.EditORDiagnosesAction"%> 
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicDiagnosisBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>


<%@include file="/global.jsp"%>

<%pageTitle = "iTrust - View Orthopedic Record";%>

<%@include file="/header.jsp"%>
<%
	OrthopedicOVRecordBean bean = null;
	PersonnelBean hcp = null;
	PatientBean patient = null;
	//get the OrthopedicOVRecordBean given by the URL param
	String oidString = request.getParameter("oid");
	if (oidString != null && !oidString.equals("")) {
		long oid = Long.parseLong(request.getParameter("oid"));
		ViewOrthopedicOVAction viewAction = new ViewOrthopedicOVAction(prodDAO, loggedInMID);
		if(request.getParameter("viewDependent") != null){
			bean = viewAction.getOrthopedicOVForDependent(oid);
		}else{
			bean = viewAction.getOrthopedicOVForPatient(oid);
		}
 		ViewPersonnelAction viewPersonnel = new ViewPersonnelAction(prodDAO, loggedInMID);
		hcp = viewPersonnel.getPersonnel(String.valueOf(bean.getHid())); 
		
 		ViewPatientAction viewPatient = new ViewPatientAction(prodDAO, loggedInMID, String.valueOf(loggedInMID));
		patient = viewPatient.getPatient(String.valueOf(bean.getPid()));
	}
	else {
		throw new ITrustException("Invalid Orthopedic ID passed to the View page");
	}
	
%>
<div align=center>
	<table class="fTable" align="center">
		<tr><th colspan="3">View Orthopedic Record</th></tr>
		<tr>
			<td width="250px">Orthopedic Name: </td>
 			<td width="250px" name="hcpName"><% out.write(hcp.getFullName()); %></td>
			<td width="50px"></td>
		</tr>
		<tr>
			<td width="250px">Patient Name: </td>
 			<td width="250px" name="patientName"><% out.write(patient.getFullName()); %></td>
			<td width="50px"></td>
		</tr>
		<%
		//show these if the the status is NOT complete
			out.write("<tr>");
			out.write("<td>Date of visit:</td>");
			out.write("<td name=\"visitDate\">" + bean.getVisitDateString() + "</td>");
			out.write("<td></td>");
			out.write("</tr>");
			
			out.write("<tr>");
			out.write("<td>Injured Limb or joint:</td>");
			out.write("<td name = \"injured\">" + bean.getInjured() + "</td>");
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
			out.write("<td name = \"mriReport\">" + bean.getMriReport() + "</td>");
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
			<td name="icdCode"><%= StringEscapeUtils.escapeHtml(d.getICDCode()) %></td>
			<td  style="white-space: nowrap;"><%= StringEscapeUtils.escapeHtml("" + (d.getDescription() )) %></td>
			<td><a href=<%= d.getURL() %>><%= link %></a></td>
		</tr>
	   <%} 
  	   }  %>
</table>
<br /><br />
</div>

<p><br/><a href="/iTrust/auth/patient/orthopedicHome.jsp">Back to Home</a></p>
<%@include file="/footer.jsp" %>
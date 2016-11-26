<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPersonnelAction"%> 
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPhysicalTherapyOVAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPatientAction"%> 
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>

<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>

<%@include file="/global.jsp"%>

<%pageTitle = "iTrust - View PhysicalTherapy Record";%>

<%@include file="/header.jsp"%>
<%


	PhysicalTherapyOVRecordBean bean = null;
	PatientBean patient = null;
	
	//get the PhysicalTherapyOVRecordBean given by the URL param
	String oidString = request.getParameter("oid");
	if (oidString != null && !oidString.equals("")) {
		long oid = Long.parseLong(request.getParameter("oid"));
		ViewPhysicalTherapyOVAction viewAction = new ViewPhysicalTherapyOVAction(prodDAO, loggedInMID);
		if(request.getParameter("viewDependent") != null){
			bean = viewAction.getPhysicalTherapyOVForDependent(oid);
		}else{
			bean = viewAction.getPhysicalTherapyOVForPatient(oid);
		}
		ViewPatientAction viewPatient = new ViewPatientAction(prodDAO, loggedInMID, String.valueOf(loggedInMID));
		patient = viewPatient.getPatient(String.valueOf(bean.getMid()));
	}
	else {
		throw new ITrustException("Invalid PhysicalTherapy ID passed to the View page");
	}
	
%>
<div align=center>
<form id="status">
		<table class="fTable" align="center">
			<tr>
				<th colspan="3">View PhysicalTherapy Record</th>
			</tr>
			<tr>
				<td width="250px">Patient Name:</td>
				<td width="250px" name="patientName">
					<%
						out.write(patient.getFullName());
					%>
				</td>
				<td width="50px"></td>
			</tr>
			<%
				//show these if the the status is NOT complete
				out.write("<tr>");
				out.write("<td>Date of visit:</td>");
				out.write("<td name=\"visitDate\">" + bean.getVisitDateString() + "</td>");
				out.write("<td></td>");
				out.write("</tr>");

				out.write("<tr><td></td><td></td><td></td></tr>");

				out.write("<tr>");
				out.write("<td>Wellness Survey Score:</td>");
				out.write("<td name=\"wellnessScore\">" + bean.getWellnessSurveyScore() + "</td>");
				out.write("<td></td>");
				out.write("</tr>");

				out.write("<tr><td></td><td></td><td></td></tr>");
				out.write("<tr><td>Exercise</td><td></td><td></td></tr>");

				String[] exercises = { "Quad Set: Slight Flexion", "Heel Slide (Supine)", "Calf Towel Stretch",
						"Straight Leg Raise", "Terminal Knee Extension", "Gastroc Stretch", "Wall Slide",
						"Proprioception: Step Over", "Hip Abduction", "Single-Leg Step Up" };

				String[] exercise = bean.getExerciseInArray();
				for (int i = 0; i < 10; i++) {
					out.write("<tr>");
					out.write("<td>" + exercises[i] + "</td>");
					
					String need = null;
					if (Boolean.valueOf(exercise[i]))
						need = "needed";
					else
						need = "not needed";
					out.write("<td>" + need + "</td>");
					
					out.write("<td></td>");
					out.write("</tr>");
				}

				out.write("</table><br />"); //end the main table
			%>
			</form>
</div>

<p><br/><a href="/iTrust/auth/patient/physicalTherapyHome.jsp">Back to Home</a></p>
<%@include file="/footer.jsp" %>
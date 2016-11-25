<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPersonnelAction"%>
<!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.action.ViewPhysicalTherapyOVAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View PhysicalTherapy Record";
%>

<%@include file="/header.jsp"%>
<%
	/* Require a Patient ID first */
	String pidString = (String) session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()) {
		response.sendRedirect(
				"/iTrust/auth/getPatientID.jsp?forward=/iTrust/auth/hcp/viewPhysicalTherapyOV.jsp");
		return;
	}

	PhysicalTherapyOVRecordBean bean = null;
	PatientBean patient = null;

	//get the PhysicalTherapyOVRecordBean given by the URL param
	String oidString = request.getParameter("oid");
	if (oidString != null && !oidString.equals("")) {
		long oid = Long.parseLong(request.getParameter("oid"));
		ViewPhysicalTherapyOVAction viewAction = new ViewPhysicalTherapyOVAction(prodDAO, loggedInMID);
		bean = viewAction.getPhysicalTherapyOVForHCP(oid);

		//then grab the associated PatientBean
		ViewPatientAction viewPatient = new ViewPatientAction(prodDAO, loggedInMID, pidString);
		patient = viewPatient.getPatient(pidString);

	} else {
		throw new ITrustException("Invalid PhysicalTherapy ID passed to the View page");
	}

	//now check this bean's status AND the HCP's specialty to see if should redirect to the edit page
	ViewPersonnelAction personnelAction = new ViewPersonnelAction(prodDAO, loggedInMID);
	PersonnelBean currentPersonnel = personnelAction.getPersonnel("" + loggedInMID);
	//TODO: Lower Orthopedic
	if (currentPersonnel.getSpecialty().equalsIgnoreCase("Orthopedic")
			|| currentPersonnel.getSpecialty().equalsIgnoreCase("physicaltherapist")) {
		response.sendRedirect("/iTrust/auth/hcp/editPhysicalTherapyOV.jsp?oid=" + oidString);
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
				<td width="250px">
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
				out.write("<td>" + bean.getVisitDateString() + "</td>");
				out.write("<td></td>");
				out.write("</tr>");

				out.write("<tr><td></td><td></td><td></td></tr>");
				out.write("<tr><td>Wellness Survey Results</td><td></td><td></td></tr>");

				String[] questions = { "Performing normal house work", "Getting into or out of bath",
						"Waking between rooms", "Squatting?", "Lift an object from floor", "Walking two blocks?",
						"Getting up or down stairs", "Standing for 1 hour", "Jumping", "Running on uneven ground" };

				String[] wellnessSurveyResults = bean.getWellnessSurveyResultsInArray();
				for (int i = 0; i < 10; i++) {
					out.write("<tr>");
					out.write("<td>" + questions[i] + "</td>");
					out.write("<td>" + wellnessSurveyResults[i] + "</td>");
					out.write("<td></td>");
					out.write("</tr>");
				}

				out.write("<tr>");
				out.write("<td>Wellness Survey Score:</td>");
				out.write("<td>" + bean.getWellnessSurveyScore() + "</td>");
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
			<p>
				<br /> <a href="/iTrust/auth/hcp/physicalTherapyHome.jsp">Back
					to Home</a>
			</p>
			<%@include file="/footer.jsp"%>
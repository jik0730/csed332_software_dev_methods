<%@page
	import="edu.ncsu.csc.itrust.dao.mysql.PhysicalTherapyOVRecordDAO"%>
<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.PhysicalTherapyOVRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPersonnelAction"%>
<!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.action.AddPhysicalTherapyOVAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPHRAction"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.action.EditOPDiagnosesAction"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Add PhysicalTherapy Office Visit";
%>

<%@include file="/header.jsp"%>
<%
	/* Require a Patient ID first */
	String pidString = (String) session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()) {
		response.sendRedirect(
				"/iTrust/auth/getPatientID.jsp?forward=/iTrust/auth/hcp/addPhysicalTherapyOV.jsp");
		return;
	}

	//get the current patient (Might be null!)
	ViewPatientAction patientAction = new ViewPatientAction(prodDAO, loggedInMID, pidString);
	PatientBean chosenPatient = patientAction.getPatient(pidString);

	//if specialty is not oph or opt, simply redirect them to the regular edit office visit page
	ViewPersonnelAction personnelAction = new ViewPersonnelAction(prodDAO, loggedInMID);
	PersonnelBean currentPersonnel = personnelAction.getPersonnel("" + loggedInMID);
	
	if (!currentPersonnel.getSpecialty().equalsIgnoreCase("orthopedic")
			&& !currentPersonnel.getSpecialty().equalsIgnoreCase("physicaltherapist")) {
		response.sendRedirect("/iTrust/auth/hcp-uap/editOfficeVisit.jsp");
	}

	//don't run unless the form was actually submitted
	if ("true".equals(request.getParameter("formIsFilled"))) {
		//prepare to add beans!
		AddPhysicalTherapyOVAction addAction = new AddPhysicalTherapyOVAction(prodDAO, loggedInMID);
		boolean addedSomething = false;

		//add the initial record
		String date = request.getParameter("date");
		String docLastName = currentPersonnel.getLastName();
		String docFirstName = currentPersonnel.getFirstName();
		final int surveyNum = 10;
		String[] wellnessSurveyResults = new String[surveyNum];
		long sum = 0;
		for (int i = 0; i < surveyNum; i++) {
			wellnessSurveyResults[i] = request.getParameter("wellnessSurveyResult" + String.valueOf(i));
			sum += Long.valueOf(wellnessSurveyResults[i]);
		}
		long wellnessSurveyScore = sum / surveyNum;

		final int exerciseNum = 10;
		String[] exercise = new String[exerciseNum];
		for (int i = 0; i < exerciseNum; i++) {
			exercise[i] = request.getParameter("exercise" + String.valueOf(i));
		}

		try {
			String clientSideErrors = "<p class=\"iTrustError\">This form has not been validated correctly. "
					+ "The following field are not properly filled in: [";
			boolean hasCSErrors = false;
			PhysicalTherapyOVRecordBean ptBean = new PhysicalTherapyOVRecordBean();
			ptBean.setMid(Long.parseLong(pidString));
			ptBean.setVisitDate(date);
			ptBean.setLastName(docLastName);
			ptBean.setFirstName(docFirstName);
			ptBean.setWellnessSurveyResults(String.join(",", Arrays.asList(wellnessSurveyResults)));
			ptBean.setWellnessSurveyScore(wellnessSurveyScore);
			ptBean.setExercise(String.join(",", Arrays.asList(exercise)));

			if (hasCSErrors) {
				out.write(clientSideErrors + "]</p>");
				clientSideErrors = "";
			} else {
				addAction.addPhysicalTherapyOV(ptBean);
				addedSomething = true;
			}

			if (addedSomething) {
				response.sendRedirect("/iTrust/auth/hcp/physicalTherapyHome.jsp?addOV");
			}
		} catch (FormValidationException e) {
			response.sendRedirect("/iTrust/auth/hcp/physicalTherapyHome.jsp");
			throw new ITrustException("Invalid data entered into PhysicalTherapy office visit creator.");
		}
	}
%>

<div id="mainpage" align="center">

	<form action="/iTrust/auth/hcp/addPhysicalTherapyOV.jsp" method="post"
		id="officeVisit">
		<input type="hidden" name="formIsFilled" value="true" />
		<table class="fTable" align="center">
			<tr>
				<th colspan="3">Add PhysicalTherapy Office Visit</th>
			</tr>
			<tr>
				<td width="200px"><label for="date">Date of visit: </label></td>
				<td width="200px"><input type="text" name="date" id="date"
					size="7" /> <input type="button" value="Select Date"
					onclick="displayDatePicker('date');" /></td>
				<td width="200px" id="date-invalid"></td>
			</tr>
			<tr>
				<td width="200px"></td>
				<td width="200px"></td>
				<td width="200px"></td>
			</tr>
			<tr>
				<td width="200px">Wellness Survey</td>
				<td width="200px"></td>
				<td width="200px"></td>
			</tr>
			<%
				String[] questions = { "Performing normal house work", "Getting into or out of bath",
						"Waking between rooms", "Squatting?", "Lift an object from floor", "Walking two blocks?",
						"Getting up or down stairs", "Standing for 1 hour", "Jumping", "Running on uneven ground" };
				for (int i = 0; i < 10; i++) {
					out.write("<tr>" + "<td width=\"200px\"><label for=\"wellnessSurvey\">" + questions[i] + "</label></td>"
							+ "<td width=\"3000px\">" + "<input type=\"radio\" name=\"wellnessSurveyResult"
							+ String.valueOf(i) + "\" value=\"0\" checked>Unable"
							+ "<input type=\"radio\" name=\"wellnessSurveyResult" + String.valueOf(i)
							+ "\" value=\"25\">Very difficult" + "<input type=\"radio\" name=\"wellnessSurveyResult"
							+ String.valueOf(i) + "\" value=\"50\">Moderate"
							+ "<input type=\"radio\" name=\"wellnessSurveyResult" + String.valueOf(i)
							+ "\" value=\"75\">Little bit" + "<input type=\"radio\" name=\"wellnessSurveyResult"
							+ String.valueOf(i) + "\" value=\"100\">No difficulty" + "</td>" + "<td width=\"200px\"></td>"
							+ "</tr>");
				}
			%>
			<tr>
				<td width="200px"></td>
				<td width="200px"></td>
				<td width="200px"></td>
			</tr>
			<tr>
				<td width="200px">Exercise</td>
				<td width="200px"></td>
				<td width="200px"></td>
			</tr>
			<%
				String[] exercises = { "Quad Set: Slight Flexion", "Heel Slide (Supine)",
						"Calf Towel Stretch", "Straight Leg Raise", "Terminal Knee Extension", "Gastroc Stretch",
						"Wall Slide", "Proprioception: Step Over", "Hip Abduction", "Single-Leg Step Up" };
				for (int i = 0; i < 10; i++) {
					out.write("<tr>" + "<td width=\"200px\"><label for=\"exercise\">" + exercises[i] + "</label></td>"
							+ "<td width=\"3000px\">" + "<input type=\"radio\" name=\"exercise"
							+ String.valueOf(i) + "\" value=\"false\" checked>not needed"
							+ "<input type=\"radio\" name=\"exercise" + String.valueOf(i)
							+ "\" value=\"true\">needed" + "</td>" + "<td width=\"200px\"></td>"
							+ "</tr>");
				}
			%>
		</table>
		<br /> <input type="submit" id="submit" value="Submit" />
	</form>
</div>

<p>
	<br /> <a href="/iTrust/auth/hcp/physicalTherapyHome.jsp">Back to
		Home</a>
</p>

<%@include file="/footer.jsp"%>
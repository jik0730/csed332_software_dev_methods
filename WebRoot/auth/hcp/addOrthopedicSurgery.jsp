<%@page import="edu.ncsu.csc.itrust.dao.mysql.OrthopedicSurgeryRecordDAO"%>
<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicSurgeryRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPersonnelAction"%> <!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%> <!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.action.AddOrthopedicSurgeryAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPHRAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicSurgeryRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException" %>
<%@page import="edu.ncsu.csc.itrust.action.EditORDiagnosesAction"%> 
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicDiagnosisBean"%>
<%@page import="edu.ncsu.csc.itrust.validate.OrthopedicDiagnosisBeanValidator"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>

<%@include file="/global.jsp"%>

<%pageTitle = "iTrust - Add Surgical Orthopedic Office Visit";%>

<%@include file="/header.jsp"%>
<%
	/* Require a Patient ID first */
	String pidString = (String) session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=/iTrust/auth/hcp/addOrthopedicSurgery.jsp");
		return;
	}
	
	//get the current patient (Might be null!)
	ViewPatientAction patientAction = new ViewPatientAction(prodDAO, loggedInMID, pidString);
	PatientBean chosenPatient = patientAction.getPatient(pidString);


	
	//if specialty is not oph, simply redirect them to the regular edit surgical office visit page
	ViewPersonnelAction personnelAction = new ViewPersonnelAction(prodDAO, loggedInMID);
	PersonnelBean currentPersonnel = personnelAction.getPersonnel("" + loggedInMID);
	
	
	if (!currentPersonnel.getSpecialty().equalsIgnoreCase("Orthopedic")) {
		response.sendRedirect("/iTrust/auth/hcp-uap/editOfficeVisit.jsp");
	}
	
	//don't run unless the form was actually submitted
	if ("true".equals(request.getParameter("formIsFilled"))) {
		//prepare to add beans!
		AddOrthopedicSurgeryAction addAction = new AddOrthopedicSurgeryAction(prodDAO, loggedInMID);
		boolean addedSomething = false;
		
		//add the initial record
		String date = request.getParameter("date");
		String docLastName = currentPersonnel.getLastName();
		String docFirstName = currentPersonnel.getFirstName();
		String surgery = request.getParameter("surgery");
		String surgeryNotes = request.getParameter("surgeryNotes");
		
		
		try {
			String clientSideErrors = "<p class=\"iTrustError\">This form has not been validated correctly. " +
			"The following field are not properly filled in: [";
			boolean hasCSErrors = false;
			OrthopedicSurgeryRecordBean ophBean = new OrthopedicSurgeryRecordBean();
				ophBean.setMid(Long.parseLong(pidString));
				ophBean.setVisitDate(date);
				ophBean.setLastName(docLastName);
				ophBean.setFirstName(docFirstName);
				ophBean.setSurgery(surgery);
				ophBean.setSurgeryNotes(surgeryNotes);
			if (hasCSErrors) {
				out.write(clientSideErrors + "]</p>");
				clientSideErrors = "";
			}else{
				addAction.addOrthopedicSurgery(ophBean);
				if(!request.getParameter("ICDCode").equals("")){
					EditORDiagnosesAction diagnoses =  new EditORDiagnosesAction(prodDAO,""+ophBean.getOid()); 
					OrthopedicDiagnosisBean beanSub = new BeanBuilder<OrthopedicDiagnosisBean>().build(request.getParameterMap(), new OrthopedicDiagnosisBean());
					//validator requires description but DiagnosesDAO does not. Set here to pass validation.
					beanSub.setDescription("no description");
			    	try {
			    		OrthopedicDiagnosisBeanValidator validator = new OrthopedicDiagnosisBeanValidator();
			    		validator.validate(beanSub);
			    		beanSub.setVisitID(ophBean.getOid());
			        	diagnoses.addDiagnosis(beanSub);
			   	 	} catch (FormValidationException e) {
			   	 		response.sendRedirect("/iTrust/auth/hcp/orthopedicSurgeryHome.jsp");
						throw new ITrustException("Invalid data entered into surgical Orthopedic office visit creator.");
			    	}
				}
				addedSomething = true;
			}
			if(addedSomething){
				response.sendRedirect("/iTrust/auth/hcp/orthopedicSurgeryHome.jsp?addSurgery");
			}
		}
		catch(FormValidationException e) {
			response.sendRedirect("/iTrust/auth/hcp/orthopedicSurgeryHome.jsp");
			throw new ITrustException("Invalid data entered into surgical Orthopedic office visit creator.");
		}
	}
%>

<div id="mainpage" align="center">

	<form action="/iTrust/auth/hcp/addOrthopedicSurgery.jsp" method="post" id="officeVisit" >
	
		<input type="hidden" name="formIsFilled" value="true" />
		
		<table class="fTable" align="center">
			<tr><th colspan="3">Add Surgical Orthopedic Office Visit</th></tr>
			<tr>
				<td width="200px"><label for="date">Date of visit: </label></td>
				<td width="200px">
					<input type="text" name="date" id="date" size="7" />
					<input type="button" value="Select Date" onclick="displayDatePicker('date');" />
				</td>

			</tr>
			<tr>
				<td><label for="surgery">Surgery: </label></td>
				<td><select name="surgery" id ="surgery">
					<option value="-- None Selected --">-- None Selected --</option>
					<option value="Total knee replacement">Total knee replacement</option>
					<option value="Total joint replacement">Total joint replacement</option>
					<option value="ACL reconstruction">ACL reconstruction</option>
					<option value="Ankle replacement">Ankle replacement</option>
					<option value="Spine surgery">Spine surgery</option>
					<option value="Arthroscopic surgery">Arthroscopic surgery</option>
					<option value="Rotator cuff repair">Rotator cuff repair</option>					
				</select></td>
			</tr>
			<tr>
				<td><label for="surgeryNotes">Surgery Notes: </label></td>
				<td><input type="text" name="surgeryNotes" id="surgeryNotes" size="50" /></td>
			</tr>
		</table>
		
		<br/>
		
		<table class="fTable" align="center">
			<tr>
	        	<th colspan="3" style="text-align: center;">Diagnosis</th>
	    	</tr>
	    	<tr>
	        	<td colspan="3" align=center>
	            	<select name="ICDCode" style="font-size:10" >
	            	<option value="">-- None Selected --</option>
	            	<%
	            	EditORDiagnosesAction diagAction = new EditORDiagnosesAction(prodDAO,"0");
	            	for(OrthopedicDiagnosisBean diag : diagAction.getDiagnosisCodes()) { %>
	            	<option value="<%=diag.getICDCode()%>"><%= StringEscapeUtils.escapeHtml("" + (diag.getICDCode())) %>
	            	- <%= StringEscapeUtils.escapeHtml("" + (diag.getDescription())) %></option>
	            	<%}%>
	            	</select>
	        	</td>
	    	</tr>
		</table>

		<input type="submit" id="submit" value="Submit" />

	</form>

</div>

<p><br/><a href="/iTrust/auth/hcp/orthopedicSurgeryHome.jsp">Back to Home</a></p>
<a name="diagnoses"></a>

<%@include file="/footer.jsp" %>
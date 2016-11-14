<%@page import="edu.ncsu.csc.itrust.dao.mysql.OrthopedicOVRecordDAO"%>
<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPatientAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPersonnelAction"%> <!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%> <!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.action.AddOrthopedicOVAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditPHRAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException" %>
<%@page import="edu.ncsu.csc.itrust.action.EditOPDiagnosesAction"%> 
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicDiagnosisBean"%>
<%@page import="edu.ncsu.csc.itrust.validate.OrthopedicDiagnosisBeanValidator"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>

<%@include file="/global.jsp"%>

<%pageTitle = "iTrust - Add Orthopedic Office Visit";%>

<%@include file="/header.jsp"%>
<%
	/* Require a Patient ID first */
	String pidString = (String) session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=/iTrust/auth/hcp/addOrthopedicOV.jsp");
		return;
	}
	
	//get the current patient (Might be null!)
	ViewPatientAction patientAction = new ViewPatientAction(prodDAO, loggedInMID, pidString);
	PatientBean chosenPatient = patientAction.getPatient(pidString);


	
	//if specialty is not oph or opt, simply redirect them to the regular edit office visit page
	//TODO: Check if Orthopedic
	ViewPersonnelAction personnelAction = new ViewPersonnelAction(prodDAO, loggedInMID);
	PersonnelBean currentPersonnel = personnelAction.getPersonnel("" + loggedInMID);
	if (!currentPersonnel.getSpecialty().equalsIgnoreCase("Orthopedic")) {
		response.sendRedirect("/iTrust/auth/hcp-uap/editOfficeVisit.jsp");
	}
	
	//don't run unless the form was actually submitted
	if ("true".equals(request.getParameter("formIsFilled"))) {
		//prepare to add beans!
		AddOrthopedicOVAction addAction = new AddOrthopedicOVAction(prodDAO, loggedInMID);
		boolean addedSomething = false;
		
		// Create a factory for disk-based file items
		GenOrthopedicOVRecordBeanFromFormAction genAction = new GenOrthopedicOVRecordBeanFromFormAction(prodDAO, loggedInMID);
		ServletContext servletContext = this.getServletConfig().getServletContext();

		String clientSideErrors = "<p class=\"iTrustError\">This form has not been validated correctly. "
				+ "The following field are not properly filled in: [";
		boolean hasCSErrors = false;
		try {
			OrthopedicOVRecordBean bean = genAction(request, servletContext);
		} catch (IllegalFormatException e) {
			clientSideErrors += "File Format Invalid: " + e.getMessage();
			hasCSErrors = true;
		}
		try {
			if ("".equals(bean.getInjured())) throw new Exception()
		} catch (IllegalArgumentException e) {
			clientSideErrors += "Injured is required field";
			hasCSErrors = true;
		}
			
		if (hasCSErrors) {
			out.write(clientSideErrors + "]</p>");
			clientSideErrors = "";
		} else {
			addAction.addOrthopedicOV(bean);
			if(!request.getParameter("ICDCode").equals("")){
				EditORDiagnosesAction diagnoses =  new EditORDiagnosesAction(prodDAO,""+bean.getOid()); 
				OrthopedicDiagnosisBean beanSub = new BeanBuilder<OrthopedicDiagnosisBean>().build(request.getParameterMap(), new OrthopedicDiagnosisBean());
				//validator requires description but DiagnosesDAO does not. Set here to pass validation.
				beanSub.setDescription("no description");
				try {
					OrthopedicDiagnosisBeanValidator validator = new OrthopedicDiagnosisBeanValidator();
					validator.validate(beanSub);
					beanSub.setVisitID(ophBean.getOid());
					diagnoses.addDiagnosis(beanSub);
					} catch (FormValidationException e) {
						response.sendRedirect("/iTrust/auth/hcp/orthopedicHome.jsp");
					throw new ITrustException("Invalid data entered into Orthopedic office visit creator."); 
				}
			}
			addedSomething = true;
		}
		if (addedSomething) {
			response.sendRedirect("/iTrust/auth/hcp/orthopedicHome.jsp?addOV");
		}
	}
%>

<div id="mainpage" align="center">

	<form action="/iTrust/auth/hcp/addOrthopedicOV.jsp" method="post" id="officeVisit" >
		<input type="hidden" name="formIsFilled" value="true" />
		<table class="fTable" align="center">
			<tr><th colspan="3">Add Orthopedic Office Visit</th></tr>
			<tr>
				<td width="200px"><label for="date">Date of visit: </label></td>
				<td width="200px">
					<input type="text" name="date" id="date" size="7" />
					<input type="button" value="Select Date" onclick="displayDatePicker('date');" />
				</td>
				<td width="200px" id="date-invalid"></td>
			</tr>
			<tr>
				<td><label for="Injured">Injured Limb or joint: </label></td>
				<td><input type="text" name="Injured" id="Injured" size="40" /> </td>
				<td></td>
			</tr>
			<tr>
				<td><label for="XRay">X-Ray image: </label></td>
				<td><input type="file" name="XRay" id="XRay"/> </td>
				<td></td>
			</tr>
			<tr>
				<td><label for="MRI">MRI image: </label></td>
				<td><input type="file" name="MRI" id="MRI"/> </td>
				<td></td>
			</tr>
			<tr>
				<td><label for="mriReport">MRI Report: </label></td>
				<td><input type="text" name="mriReport" id="mriReport" size="40" /> </td>
				<td></td>
			</tr>
		</table>
		
		<br />
		
		<table class="fTable" align="center">
	 		<tr><th colspan="3" style="text-align: center;">Diagnosis</th></tr>
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
 		<br />
		<input type="submit" id="submit" value="Submit" />
	</form>
</div>

<p><br/><a href="/iTrust/auth/hcp/orthopedicHome.jsp">Back to Home</a></p>
<a name="diagnoses"></a>

<%@include file="/footer.jsp" %>
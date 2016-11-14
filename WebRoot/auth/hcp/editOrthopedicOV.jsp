<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPersonnelAction"%> <!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%> <!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.action.EditOrthopedicOVAction"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewOrthopedicOVAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicOVRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPatientAction"%> 
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException" %>
<%@page import="edu.ncsu.csc.itrust.action.EditOPDiagnosesAction"%> 
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicDiagnosisBean"%>
<%@page import="edu.ncsu.csc.itrust.validate.OrthopedicDiagnosisBeanValidator"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>

<%@include file="/global.jsp"%>

<%pageTitle = "iTrust - Edit Orthopedic Record";%>

<%@include file="/header.jsp"%>
<%
	/* Require a Patient ID first */
	String clientSideErrors ="";
	String pidString = (String) session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=/iTrust/auth/hcp/viewOrthopedicOV.jsp");
		return;
	}
	
	OrthopedicOVRecordBean bean = null;
	PatientBean patient = null;
	ViewOrthopedicOVAction viewAction = new ViewOrthopedicOVAction(prodDAO, loggedInMID);
	
	//get the OrthopedicRecordBean given by the URL param
	String oidString = request.getParameter("oid");
	if (oidString != null && !oidString.equals("")) {
		long oid = Long.parseLong(request.getParameter("oid"));
		bean = viewAction.getOrthopedicOVForHCP(oid);
		
		//then grab the associated PatientBean
		ViewPatientAction viewPatient = new ViewPatientAction(prodDAO, loggedInMID, pidString);
		patient = viewPatient.getPatient(pidString);
	}
	else {
		throw new ITrustException("Invalid Orthopedic ID passed to the Edit page: " + oidString);
	}
	
	//now double-check this bean's status AND the HCP's specialty to see if should redirect back to view
	ViewPersonnelAction personnelAction = new ViewPersonnelAction(prodDAO, loggedInMID);
	PersonnelBean currentPersonnel = personnelAction.getPersonnel("" + loggedInMID);
	if (!currentPersonnel.getSpecialty().equalsIgnoreCase("Orthopedic")) {
		response.sendRedirect("/iTrust/auth/hcp/viewOrthopedicOV.jsp?oid=" + oidString);
	}
	
	if ("true".equals(request.getParameter("formIsFilled")) && "removediagnosesForm".equals(request.getParameter("submittedFormName"))){
	    EditORDiagnosesAction diagnoses = new EditORDiagnosesAction(prodDAO,oidString);
	    
	    String remID = request.getParameter("removeDiagID");
	    OrthopedicDiagnosisBean beanSub = new OrthopedicDiagnosisBean();
	    beanSub.setOpDiagnosisID(Long.parseLong(remID));
	    diagnoses.deleteDiagnosis(beanSub);
	}
	
	//check if a new diagnoses was added.
	else if ("true".equals(request.getParameter("formIsFilled")) && "diagnosesForm".equals(request.getParameter("submittedFormName"))){
		EditORDiagnosesAction diagnoses =  new EditORDiagnosesAction(prodDAO,oidString); 
		OrthopedicDiagnosisBean beanSub = new BeanBuilder<OrthopedicDiagnosisBean>().build(request.getParameterMap(), new OrthopedicDiagnosisBean());
		//validator requires description but DiagnosesDAO does not. Set here to pass validation.
		beanSub.setDescription("no description");
    	try {
    		OrthopedicDiagnosisBeanValidator validator = new OrthopedicDiagnosisBeanValidator();
    		validator.validate(beanSub);
    		beanSub.setVisitID(Integer.parseInt(oidString));
        	diagnoses.addDiagnosis(beanSub);
       		clientSideErrors = "Diagnosis information successfully updated.";
   	 	} catch (FormValidationException e) {
 			clientSideErrors = e.printHTMLasString();
    	}
	}
	//don't run unless the form was actually submitted
	else if ("true".equals(request.getParameter("formIsFilled"))) {
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
			addAction.editOrthopedicOV(bean.getOid(), bean);
			addedSomething = true;
		}
		if (addedSomething) {
			response.sendRedirect("/iTrust/auth/hcp/orthopedicHome.jsp?addOV");
		}
	}
%>
<script type="text/javascript">
    function removeDiagID(value) {
        document.getElementById("removeDiagID").value = value;
        document.forms["removeDiagnosisForm"].submit();
    }
</script>
<div id="mainpage" align="center">

	<form <% out.print("action=\"/iTrust/auth/hcp/editOrthopedicOV.jsp?oid=" + oidString + "\""); %> method="post" id="officeVisit" >
		<input type="hidden" name="formIsFilled" value="true" />
		<table class="fTable" align="center">
			<tr><th colspan="3">Edit Orthopedic Office Visit</th></tr>
			<tr>
				<td width="200px"><label for="date">Date of visit: </label></td>
				<td width="200px">
					<input type="text" name="date" id="date" <% out.write("value=\"" + bean.getVisitDateString() + "\""); %> size="7" /> -->
					<input type="button" value="Select Date" onclick="displayDatePicker('date');" />
				</td>
				<td width="200px" id="date-invalid"></td>
			</tr>
			<tr>
				<td><label for="Injured">Injured Limb or joint: </label></td>
				<td><input type="text" name="Injured" id="Injured" <% out.write("value=\"" + bean.getInjured() + "\""); %>size="40" /> </td>
				<td></td>
			</tr>
			<tr>
				<td><label for="XRay">X-Ray image: </label></td>
				<td><input type="file" name="XRay" id="XRay" <%-- out.write("value=\"" + bean.getXRay() + "\""); --%> /> </td>
				<td></td>
			</tr>
			<tr>
				<td><label for="MRI">MRI image: </label></td>
				<td><input type="file" name="MRI" id="MRI" <%-- out.write("value=\"" + bean.getMri() + "\""); --%> /> </td>
				<td></td>
			</tr>
			<tr>
				<td><label for="mriReport">MRI Report: </label></td>
				<td><input type="text" name="mriReport" id="mriReport" <% out.write("value=\"" + bean.getMriReport() + "\""); %>size="40" /> </td>
				<td></td>
			</tr>
		</table>
		
		<br />
		<br />
		
		<input type="submit" id="submit" value="Submit" form="officeVisit" hidden="hidden"/>
	</form>
</div>

<form <% out.print("action=\"/iTrust/auth/hcp/editOrthopedicOV.jsp?oid=" + oidString + "\""); %> method="post" id="removeDiagnosisForm" >
<input type="hidden" name="formIsFilled" value="true" />
<input type="hidden" name="submittedFormName" value="removediagnosesForm" />
<input type="hidden" id="removeDiagID" name="removeDiagID" value="" />
<input type="hidden" name="ovID" value="<%= StringEscapeUtils.escapeHtml("" + oidString) %>" />
</form>

<form <% out.print("action=\"/iTrust/auth/hcp/editOrthopedicOV.jsp?oid=" + oidString + "\""); %> method="post" id="diagnosesForm" >
		<input type="hidden" name="formIsFilled" value="true" />
		<input type="hidden" name="submittedFormName" value="diagnosesForm" />
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
			<td ><itrust:icd9cm code="<%= StringEscapeUtils.escapeHtml(d.getICDCode()) %>"/></td>
			<td  style="white-space: nowrap;"><%= StringEscapeUtils.escapeHtml("" + (d.getDescription() )) %></td>
			<td ><a
            href="javascript:removeDiagID('<%= StringEscapeUtils.escapeHtml("" + (d.getOpDiagnosisID())) %>');">Remove</a></td>
		</tr>
	   <%} 
  	   }  %>
  	       <tr>
        <th colspan="3" style="text-align: center;">New</th>
    </tr>
    <tr>
        <td colspan="3" align=center>
            <select name="ICDCode" style="font-size:10" >
            <option value="">-- None Selected --</option>
            <%for(OrthopedicDiagnosisBean diag : diagAction.getDiagnosisCodes()) { %>
            <option value="<%=diag.getICDCode()%>"><%= StringEscapeUtils.escapeHtml("" + (diag.getICDCode())) %>
            - <%= StringEscapeUtils.escapeHtml("" + (diag.getDescription())) %></option>
            <%}%>
            </select>
            <input type="submit" id="add_diagnosis" value="Add Diagnosis" >
        </td>
    </tr>
</table>
<br /><br />
</form>
<p align="middle">
<input type="submit" id="submitoutside" value="Submit" form="officeVisit"/>
</p>
<p><br/><a href="/iTrust/auth/hcp/orthopedicHome.jsp">Back to Home</a></p>
	
<br/><br/>
<%@include file="/footer.jsp" %>
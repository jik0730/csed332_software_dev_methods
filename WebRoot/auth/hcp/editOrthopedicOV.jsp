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
				EditOrthopedicOVAction editAction = new EditOrthopedicOVAction(prodDAO, loggedInMID);
				boolean addedSomething = false;
				
				//add the initial record
				String date = request.getParameter("date");
				String visAcNumOD = request.getParameter("vaNumOD");
				String visAcDenumOD = request.getParameter("vaDenOD");
				String visAcNumOS = request.getParameter("vaNumOS");
				String visAcDenumOS = request.getParameter("vaDenOS");
				String sphereOD = request.getParameter("sphereOD");
				String sphereOS = request.getParameter("sphereOS");
				String cylOD = request.getParameter("cylinderOD");
				String cylOS = request.getParameter("cylinderOS");
				String axisOD = request.getParameter("axisOD");
				String axisOS = request.getParameter("axisOS");
				String addOD = request.getParameter("addOD");
				String addOS = request.getParameter("addOS");
				
				try {
					clientSideErrors = "<p class=\"iTrustError\">This form has not been validated correctly. " +
					"The following field are not properly filled in: [";
					boolean hasCSErrors = false;
					OrthopedicOVRecordBean ophBean = new OrthopedicOVRecordBean();
						ophBean.setMid(Long.parseLong(pidString));
						ophBean.setVisitDate(date);
						//We reuse the first and last name that were already present
						//as an edit shouldn't change who created the office visit.
						ophBean.setLastName(bean.getLastName());
						ophBean.setFirstName(bean.getFirstName());
						//parse acuity numer OD
						try {
							int vaNum = Integer.parseInt(visAcNumOD);
							if(vaNum <= 0){
								throw new NumberFormatException();
							}
							ophBean.setVaNumOD(vaNum);
						}
						catch (NumberFormatException e) {
							clientSideErrors += "Visual Acuity Numerator OD is required, and must be a positive integer.";
							hasCSErrors = true;
						}
						//parse acuity numer OS
						try {
							int vaNum = Integer.parseInt(visAcNumOS);
							if(vaNum <= 0){
								throw new NumberFormatException();
							}
							ophBean.setVaNumOS(vaNum);
						}
						catch (NumberFormatException e) {
							clientSideErrors += "Visual Acuity Numerator OS is required, and must be a positive integer.";
							hasCSErrors = true;
						}
						//parse acuity denum OD
						try {
							int vaDen = Integer.parseInt(visAcDenumOD);
							if(vaDen <= 0){
								throw new NumberFormatException();
							}
							ophBean.setVaDenOD(vaDen);
						}
						catch (NumberFormatException e) {
							if (hasCSErrors)
								clientSideErrors += ", ";
							clientSideErrors += "Visual Acuity Denumerator OD is required, and must be a positive integer.";
							hasCSErrors = true;
						}
						//parse acuity denum OS
						try {
							int vaDen = Integer.parseInt(visAcDenumOS);
							if(vaDen <= 0){
								throw new NumberFormatException();
							}
							ophBean.setVaDenOS(vaDen);
						}
						catch (NumberFormatException e) {
							if (hasCSErrors)
								clientSideErrors += ", ";
							clientSideErrors += "Visual Acuity Denumerator OS is required, and must be a positive integer.";
							hasCSErrors = true;
						}
						//parse sphereOD
						try {
							double sphOD = Double.parseDouble(sphereOD);
							if(sphOD > 10.00 || sphOD < -10.00 || sphOD % 0.25 != 0){
								throw new NumberFormatException();
							}
							ophBean.setSphereOD(sphOD);
						}
						catch (NumberFormatException e) {
							if (hasCSErrors)
								clientSideErrors += ", ";
							clientSideErrors += "SphereOD is required, must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter";
							hasCSErrors = true;
						}
						//parse sphereOS
						try {
							double sphOS = Double.parseDouble(sphereOS);
							if(sphOS > 10.00 || sphOS < -10.00 || sphOS % 0.25 != 0){
								throw new NumberFormatException();
							}
							ophBean.setSphereOS(sphOS);
						}
						catch (NumberFormatException e) {
							if (hasCSErrors)
								clientSideErrors += ", ";
							clientSideErrors += "SphereOS is required, must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter";
							hasCSErrors = true;
						}
						//parse cylinderOD
						try {
							if(!cylOD.equals("")){
								double cylinOD = Double.parseDouble(cylOD);
								if(cylinOD > 10.00 || cylinOD < -10.00 || cylinOD % 0.25 != 0){
									throw new NumberFormatException();
								}
								ophBean.setCylinderOD(cylinOD);
							}
						}
						catch (NumberFormatException e) {
							if (hasCSErrors)
								clientSideErrors += ", ";
							clientSideErrors += "CylinderOD must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter";
							hasCSErrors = true;
						}
						//parse cylinderOS
						try {
							if(!cylOS.equals("")){
								double cylinOS = Double.parseDouble(cylOS);
								if(cylinOS > 10.00 || cylinOS < -10.00 || cylinOS % 0.25 != 0){
									throw new NumberFormatException();
								}
								ophBean.setCylinderOS(cylinOS);
							}
						}
						catch (NumberFormatException e) {
							if (hasCSErrors)
								clientSideErrors += ", ";
							clientSideErrors += "CylinderOS must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter";
							hasCSErrors = true;
						}
						//parse AxisOD
						try {
							if(!cylOD.equals("") && axisOD.equals("")){ //if we DO have a cylinder value, we need an axis, too.
								throw new NumberFormatException();
							}
							if(!axisOD.equals("")){ //we don't have to have one, so only try if we do.
								int axOD = Integer.parseInt(axisOD);
								if(axOD > 180 || axOD < 1){
									throw new NumberFormatException();
								}
									ophBean.setAxisOD(axOD);
							}
						}
						catch (NumberFormatException e) {
							if (hasCSErrors)
								clientSideErrors += ", ";
							clientSideErrors += "AxisOD is required if a CylinderOD is given, and must be between 1 and 180 inclusive";
							hasCSErrors = true;
						}
						//parse AxisOS
						try {
							if(!cylOS.equals("") && axisOS.equals("")){ //if we DO have a cylinder value, we need an axis, too.
								throw new NumberFormatException();
							}
							if(!axisOS.equals("")){ //we don't have to have one, so only try if we do.
								int axOS = Integer.parseInt(axisOD);
								if(axOS > 180 || axOS < 1){
									throw new NumberFormatException();
								}
									ophBean.setAxisOS(axOS);
							}
						}
						catch (NumberFormatException e) {
							if (hasCSErrors)
								clientSideErrors += ", ";
							clientSideErrors += "AxisOS is required if a CylinderOS is given, and must be between 1 and 180 inclusive";
							hasCSErrors = true;
						}
						//parse addOD
						try {
							double aOD = Double.parseDouble(addOD);
							if(aOD > 3.00 || aOD < 0.75 || aOD % 0.25 != 0){
								throw new NumberFormatException();
							}
							ophBean.setAddOD(aOD);
						}
						catch (NumberFormatException e) {
							if (hasCSErrors)
								clientSideErrors += ", ";
							clientSideErrors += "AddOD is required, must be between 0.75 and 3.00 inclusive, and must be rounded to the nearest quarter diopter";
							hasCSErrors = true;
						}
						//parse addOS
						try {
							double aOS = Double.parseDouble(addOS);
							if(aOS > 3.00 || aOS < 0.75 || aOS % 0.25 != 0){
								throw new NumberFormatException();
							}
							ophBean.setAddOS(aOS);
						}
						catch (NumberFormatException e) {
							if (hasCSErrors)
								clientSideErrors += ", ";
							clientSideErrors += "AddOS is required, must be between 0.75 and 3.00 inclusive, and must be rounded to the nearest quarter diopter";
							hasCSErrors = true;
						}
						
			if (hasCSErrors) {
				out.write(clientSideErrors + "]</p>");
			} else{	
			editAction.editOrthopedicOV(bean.getOid(), ophBean);
			addedSomething = true;
			}
			
			if (addedSomething) {
				response.sendRedirect("/iTrust/auth/hcp/orthopedicHome.jsp?editOV");
			}
		}
		catch(FormValidationException e) {
			out.write("<p class=\"iTrustError\">" + e.getMessage() + "</p>");
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
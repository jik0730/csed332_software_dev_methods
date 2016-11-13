<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPersonnelAction"%> <!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%> <!-- Used for specialty-checking -->
<%@page import="edu.ncsu.csc.itrust.action.EditOrthopedicSurgeryAction"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewOrthopedicSurgeryAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicSurgeryRecordBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPatientAction"%> 
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException" %>
<%@page import="edu.ncsu.csc.itrust.action.EditOPDiagnosesAction"%> 
<%@page import="edu.ncsu.csc.itrust.beans.OrthopedicDiagnosisBean"%>
<%@page import="edu.ncsu.csc.itrust.validate.OrthopedicDiagnosisBeanValidator"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>

<%@include file="/global.jsp"%>

<%pageTitle = "iTrust - Edit Surgical Ophththalmology Record";%>

<%@include file="/header.jsp"%>
<%
	/* Require a Patient ID first */
	String clientSideErrors ="";
	String pidString = (String) session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=/iTrust/auth/hcp/viewOrthopedicSurgery.jsp");
		return;
	}
	
	OrthopedicSurgeryRecordBean bean = null;
	PatientBean patient = null;
	ViewOrthopedicSurgeryAction viewAction = new ViewOrthopedicSurgeryAction(prodDAO, loggedInMID);
	
	//get the OrthopedicRecordBean given by the URL param
	String oidString = request.getParameter("oid");
	if (oidString != null && !oidString.equals("")) {
		long oid = Long.parseLong(request.getParameter("oid"));
		bean = viewAction.getOrthopedicSurgeryForHCP(oid);
		
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
	if (!currentPersonnel.getSpecialty().equalsIgnoreCase("orthopedic")) {
		response.sendRedirect("/iTrust/auth/hcp/viewOrthopedicSurgery.jsp?oid=" + oidString);
	}
	
	if ("true".equals(request.getParameter("formIsFilled")) && "removediagnosesForm".equals(request.getParameter("submittedFormName"))){
	    EditOPDiagnosesAction diagnoses = new EditOPDiagnosesAction(prodDAO,oidString);
	    
	    String remID = request.getParameter("removeDiagID");
	    OrthopedicDiagnosisBean beanSub = new OrthopedicDiagnosisBean();
	    beanSub.setOpDiagnosisID(Long.parseLong(remID));
	    diagnoses.deleteDiagnosis(beanSub);
	}
	
	//check if a new diagnoses was added.
	else if ("true".equals(request.getParameter("formIsFilled")) && "diagnosesForm".equals(request.getParameter("submittedFormName"))){
		EditOPDiagnosesAction diagnoses =  new EditOPDiagnosesAction(prodDAO,oidString); 
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
				EditOrthopedicSurgeryAction editAction = new EditOrthopedicSurgeryAction(prodDAO, loggedInMID);
				boolean addedSomething = false;
				
				//add the initial record
				String date = request.getParameter("date");
				String surgery = request.getParameter("surgery");
				String surgeryNotes = request.getParameter("surgeryNotes");
				
				try {
					clientSideErrors = "<p class=\"iTrustError\">This form has not been validated correctly. " +
					"The following field are not properly filled in: [";
					boolean hasCSErrors = false;
					OrthopedicSurgeryRecordBean ophBean = new OrthopedicSurgeryRecordBean();
						ophBean.setMid(Long.parseLong(pidString));
						ophBean.setVisitDate(date);
						//We reuse the first and last name that were already present
						//as an edit shouldn't change who created the surigcal office visit.
						ophBean.setLastName(bean.getLastName());
						ophBean.setFirstName(bean.getFirstName());
						//parse acuity numer OD
						ophBean.setSurgery(surgery);
						ophBean.setSurgeryNotes(surgeryNotes);
						
			if (hasCSErrors) {
				out.write(clientSideErrors + "]</p>");
			} else{	
			editAction.editOrthopedicSurgery(bean.getOid(), ophBean);
			addedSomething = true;
			}
			
			if (addedSomething) {
				response.sendRedirect("/iTrust/auth/hcp/OrthopedicSurgeryHome.jsp?editSurgery");
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

	<form <% out.print("action=\"/iTrust/auth/hcp/editOrthopedicSurgery.jsp?oid=" + oidString + "\""); %> method="post" id="officeVisit" >
		<input type="hidden" name="formIsFilled" value="true" />
		<table class="fTable" align="center">
			<tr><th colspan="3">Edit Surgical Orthopedic Office Visit</th></tr>
			<tr>
				<td width="200px"><label for="date">Date of visit: </label></td>
				<td width="200px">
					<input type="text" name="date" id="date" <% out.write("value=\"" + bean.getVisitDateString() + "\""); %> size="7" /> -->
					<input type="button" value="Select Date" onclick="displayDatePicker('date');" />
				</td>
			</tr>
			<tr>
				<td><label for="surgery">Surgery: </label></td>
				<td><select name="surgery" id ="surgery">
					<%if("-- None Selected --".equals(bean.getSurgery())) {
						%><option value="-- None Selected --">-- None Selected --</option><%;%>
					<%} else {
						%><option value="<%= StringEscapeUtils.escapeHtml("" + (bean.getSurgery())) %>"><%= StringEscapeUtils.escapeHtml("" + (bean.getSurgery())) %></option><%; }%>
					<%if(!"Cataract surgery".equals(bean.getSurgery()))
						%><option value="Cataract surgery">Cataract surgery</option><%;%>
					<%if(!"Laser surgery".equals(bean.getSurgery()))
						%><option value="Laser surgery">Laser surgery</option><%;%>
					<%if(!"Refractive surgery".equals(bean.getSurgery()))
						%><option value="Refractive surgery">Refractive surgery</option><%;%>
				</select></td>
			</tr>
			<tr>
				<td><label for="surgeryNotes">Surgery Notes: </label></td>
				<td><input type="text" name="surgeryNotes" id="surgeryNotes" <% out.write("value=\"" + bean.getSurgeryNotes() + "\""); %> size="50" /></td>
			</tr>
		</table>
		</table>
		
		<br />
		<br />
		
		<input type="submit" id="submit" value="Submit" form="officeVisit" hidden="hidden"/>
	</form>
</div>

<form <% out.print("action=\"/iTrust/auth/hcp/editOrthopedicSurgery.jsp?oid=" + oidString + "\""); %> method="post" id="removeDiagnosisForm" >
<input type="hidden" name="formIsFilled" value="true" />
<input type="hidden" name="submittedFormName" value="removediagnosesForm" />
<input type="hidden" id="removeDiagID" name="removeDiagID" value="" />
<input type="hidden" name="ovID" value="<%= StringEscapeUtils.escapeHtml("" + oidString) %>" />
</form>

<form <% out.print("action=\"/iTrust/auth/hcp/editOrthopedicSurgery.jsp?oid=" + oidString + "\""); %> method="post" id="diagnosesForm" >
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
	EditOPDiagnosesAction diagAction = new EditOPDiagnosesAction(prodDAO,oidString);
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
<p><br/><a href="/iTrust/auth/hcp/OrthopedicSurgeryHome.jsp">Back to Home</a></p>
	
<br/><br/>
<%@include file="/footer.jsp" %>
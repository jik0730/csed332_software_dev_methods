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
		
		try {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Configure a repository (to ensure a secure temp location is used)
			ServletContext servletContext = this.getServletConfig().getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			List<FileItem> items = upload.parseRequest(request);
			
			for (FileItem f : items) {
				switch (f.getFieldName()) {
				case 
				}
			}
			
		}
		
		try {
			String clientSideErrors = "<p class=\"iTrustError\">This form has not been validated correctly. " +
			"The following field are not properly filled in: [";
			boolean hasCSErrors = false;
			OrthopedicOVRecordBean ophBean = new OrthopedicOVRecordBean();
				ophBean.setMid(Long.parseLong(pidString));
				ophBean.setVisitDate(date);
				ophBean.setLastName(docLastName);
				ophBean.setFirstName(docFirstName);
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
					clientSideErrors += "SphereOD is required, must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter.";
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
					clientSideErrors += "SphereOS is required, must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter.";
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
					clientSideErrors += "CylinderOD must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter.";
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
					clientSideErrors += "CylinderOS must be between -10.00 and 10.00 inclusive, and must be rounded to the nearest quarter diopter.";
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
					clientSideErrors += "AxisOD is required if a CylinderOD is given, and must be between 1 and 180 inclusive.";
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
					clientSideErrors += "AxisOS is required if a CylinderOS is given, and must be between 1 and 180 inclusive.";
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
					clientSideErrors += "AddOD is required, must be between 0.75 and 3.00 inclusive, and must be rounded to the nearest quarter diopter.";
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
					clientSideErrors += "AddOS is required, must be between 0.75 and 3.00 inclusive, and must be rounded to the nearest quarter diopter.";
					hasCSErrors = true;
				}
				
				
			if (hasCSErrors) {
				out.write(clientSideErrors + "]</p>");
				clientSideErrors = "";
			}else{
				addAction.addOrthopedicOV(ophBean);
				if(!request.getParameter("ICDCode").equals("")){
					EditOPDiagnosesAction diagnoses =  new EditOPDiagnosesAction(prodDAO,""+ophBean.getOid()); 
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
			
			if(addedSomething){
				response.sendRedirect("/iTrust/auth/hcp/orthopedicHome.jsp?addOV");
			}
		}
		catch(FormValidationException e) {
				response.sendRedirect("/iTrust/auth/hcp/orthopedicHome.jsp");
				throw new ITrustException("Invalid data entered into Orthopedic office visit creator.");
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
	 <tr>
        <th colspan="3" style="text-align: center;">Diagnosis</th>
    </tr>
    <!--tr>
        <td colspan="3" align=center>
            <select name="ICDCode" style="font-size:10" >
            <option value="">-- None Selected --</option>
            <%
            EditOPDiagnosesAction diagAction = new EditOPDiagnosesAction(prodDAO,"0");
            for(OrthopedicDiagnosis	Bean diag : diagAction.getDiagnosisCodes()) { %>
            <option value="<%=diag.getICDCode()%>"><%= StringEscapeUtils.escapeHtml("" + (diag.getICDCode())) %>
            - <%= StringEscapeUtils.escapeHtml("" + (diag.getDescription())) %></option>
            <%}%>
            </select>
    </td-->
    </tr>
</table>
 	<br />
		<input type="submit" id="submit" value="Submit" />
	</form>
</div>

<p><br/><a href="/iTrust/auth/hcp/orthopedicHome.jsp">Back to Home</a></p>
<a name="diagnoses"></a>

<%@include file="/footer.jsp" %>
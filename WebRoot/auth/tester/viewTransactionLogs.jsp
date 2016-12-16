<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.enums.TransactionLogColumnType" %>
<%@page import="edu.ncsu.csc.itrust.action.ViewTransactionLogAction" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.ParseException"%>
<%@page import="edu.ncsu.csc.itrust.beans.TransactionLogBean" %>

<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Transaction Logs";
loginMessage = "";
session.removeAttribute("personnelList");
%>

<%@include file="/header.jsp" %>

<div align="center">
	<h2>View TransactionLog Logs</h2>
<%
	// Default start date and end date.
	Date dt = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	String startDate = sdf.format(dt).toString();
	String endDate = sdf.format(dt).toString();
	
	// Define transactionLogAction.
	ViewTransactionLogAction transactionLogAction = new ViewTransactionLogAction (prodDAO);
	//transactionLogAction transactionLogAction = DAOFactory.getProductionInstance().gettransactionLogAction();

    // Get all users mid, secondaryRoles, TransactionLogcodes.
	List<TransactionLogBean> loggedInRoles = transactionLogAction.getTransactionGroupBy(TransactionLogColumnType.parse(1));
	List<TransactionLogBean> secondaryRoles = transactionLogAction.getTransactionGroupBy(TransactionLogColumnType.parse(2));
	List<TransactionLogBean> TransactionLogCodes = transactionLogAction.getTransactionGroupBy(TransactionLogColumnType.parse(3));
	List<TransactionLogBean> TransactionLogResults = null;
	String loggedInRole = "";
	String secondaryRole = "";
	String TransactionLogCode = "";
	String transactionLogCodeType = "";
	String inputLoggedInRole = "";
	String inputSecondaryRole = "";
	String msg = "";
	int inputTransactionLogCode = 0;

	boolean formIsFilled = request.getParameter("formIsFilled") != null
			&& request.getParameter("formIsFilled").equals("true");

	if (formIsFilled) {
		
		// Set start date and end date.
		startDate = request.getParameter("startDate");
		startDate = startDate.trim();
		endDate = request.getParameter("endDate");
		endDate = endDate.trim();
		try {
			Date d1 = sdf.parse(startDate);
			Date d2 = sdf.parse(endDate);
			if (startDate.equals(sdf.format(d1)) && endDate.equals(sdf.format(d2))){					
			} else {
				msg = "ERROR: Date must by in the format: MM/dd/yyyy";
			}
		} catch (ParseException e) {
			msg = "ERROR: Date must by in the format: MM/dd/yyyy";
		}		
		// Set data from criteria.
		loggedInRole = request.getParameter("TransactionLogloggedInRole");
		secondaryRole = request.getParameter("TransactionLogsecondaryRole");
		TransactionLogCode = request.getParameter("TransactionLogCode");
		
		// Criteria condition to get desired TransactionLog logs.
		if(loggedInRole.equals("all ")) {
			inputLoggedInRole = "all roles";
		} else {
			inputLoggedInRole = loggedInRole;
		}
		
		if(secondaryRole.equals("all roles")) {
			inputSecondaryRole = "all roles";
		} else {
			inputSecondaryRole = secondaryRole;
		}
		if(TransactionLogCode.equals("all types")) {
			inputTransactionLogCode = -1;
		} else {
			for (TransactionType type : TransactionType.values()) {
				if (type.getDescription().equals(TransactionLogCode)){
					inputTransactionLogCode = type.getCode();
				}
			}
		}
		try {
			TransactionLogResults = transactionLogAction.getTransactionList(inputLoggedInRole, inputSecondaryRole, 
					sdf.parse(startDate), sdf.parse(endDate), inputTransactionLogCode); 
		} catch (Exception e){
		}
	}
	if (msg.contains("ERROR")) {
%>
<span class="iTrustError"><%=msg%></span>
<%
	}
%>

<form action="viewTransactionLogs.jsp" method="post"><input type="hidden" name="formIsFilled" value="true"> <br />
<div style="width: 50%; text-align:left;">
<br />
<br />
</div>
<table id = "criteria_table">
	<tr>
		<td>Mid:</td>
		<td><select name="TransactionLogloggedInRole">
				<option value="all roles">all roles</option>
				<%for(TransactionLogBean tran : loggedInRoles) {%>
					<%--System.out.println(tran.getLoggedInRole()); --%>
					<option value="<%=tran.getLoggedInRole()%>" <%if (tran.getLoggedInRole() == inputLoggedInRole) {%> selected="selected"<%}%>><%= StringEscapeUtils.escapeHtml("" + (tran.getLoggedInRole())) %></option><%
				}
				%>
			</select>
		</td>
		<td>secondaryRole:</td>
		<td><select name="TransactionLogsecondaryRole">
				<option value="all roles">all roles</option>
				<%for(TransactionLogBean tran : secondaryRoles) {%>
					<%--System.out.println(tran.getLoggedInRole()); --%>
					<option value="<%=tran.getSecondaryRole()%>"><%= StringEscapeUtils.escapeHtml("" + (tran.getSecondaryRole())) %></option><%
				}
				%>
				
			</select>
		</td>
		<td>TransactionLogCodes:</td>
		<td>
			<select name="TransactionLogCode">
				<option value="all types">all types</option>
				<%for(TransactionLogBean tran : TransactionLogCodes) {%>
					<%--System.out.println(tran.getLoggedInRole()); --%>
					<option value="<%=tran.getTransactionType().getDescription()%>"><%= StringEscapeUtils.escapeHtml("" + (tran.getTransactionType().getDescription())) %></option><%
				}
				%>
			</select>
		</td>
	</tr>
	<tr>
		<td>start Date : </td>
		<td>
			<input name="startDate" value="<%=StringEscapeUtils.escapeHtml("" + (startDate))%>" size="10">
		</td>
		<td>
			<input type=button value="Select Date" onclick="displayDatePicker('startDate');">
		</td>
		<td>End Date : </td>
		<td>
			<input name="endDate" value="<%=StringEscapeUtils.escapeHtml("" + (endDate))%>" size="10">
		</td>
		<td>
			<input type=button value="Select Date" onclick="displayDatePicker('endDate');">
		</td>
	</tr>
	<tr>
		<td colspan=5 align=right><input type="submit"
			style="font-size: 16pt; font-weight: bold;" name = "view" value="View"></td>
		<td colspan=5 align=right><input type="submit"
			style="font-size: 16pt; font-weight: bold;" name = "graph" value = "Summarize"></td>
	</tr>
</table>
<%
if (TransactionLogResults != null && request.getParameter("view") != null) {
%>
<table id = "list_table" class="fTable">
	<tr class="subHeader">
		<th>TransactionLog ID</th>
		<th>TimeLogged</th>
		<th>name</th>
		<th>code</th>
		<th>Description</th>
		<th>LoggedInRole</th>
		<th>SecondaryRole</th>
		<th>AddedInfo</th>
	</tr>
	<%
	// Iterate through TransactionLog logs results to build logs list.
	for (TransactionLogBean t : TransactionLogResults) {
	%>
	<tr>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionID())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTimeLogged())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionType().name())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionType().getCode())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionType().getDescription())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getLoggedInRole())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getSecondaryRole())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getAddedInfo())) %></td>
	</tr>
	<%
	}
	%>
</table>
	<%
}
%>
<%
if (TransactionLogResults != null && request.getParameter("graph") != null) {
	HashMap<String, Integer> loggedInUser = new HashMap<String, Integer>();
	HashMap<String, Integer> secondaryUser = new HashMap<String, Integer>();
	HashMap<String, Integer> TransactionLogMonth = new HashMap<String, Integer>();
	HashMap<String, Integer> TransactionType = new HashMap<String, Integer>();
	// Iterate TransactionLog logs results to build graphs.
	for (TransactionLogBean tb : TransactionLogResults) {
		String key = tb.getLoggedInRole();
		String key2 = tb.getSecondaryRole();
		String key3 = tb.getTimeLogged().toString();
		key3 = key3.substring(0, 7);
		String key4 = tb.getTransactionType().getDescription();
		// For first graph.
		if (loggedInUser.get(key) == null) {
			loggedInUser.put(key, 1);
		} else {
			loggedInUser.put(key, loggedInUser.get(key) + 1);
		}
		// For second graph.
		if (secondaryUser.get(key2) == null) {
			secondaryUser.put(key2, 1);
		} else {
			secondaryUser.put(key2, secondaryUser.get(key2) + 1);
		}
		// For third graph.
		if (TransactionLogMonth.get(key3) == null){
			TransactionLogMonth.put(key3, 1);
		} else {
			TransactionLogMonth.put(key3, TransactionLogMonth.get(key3) + 1);
		}
		// For fourth graph.
		if (TransactionType.get(key4) == null) {
			TransactionType.put(key4, 1);
		} else {
			TransactionType.put(key4, TransactionType.get(key4) + 1);
		}
	}
	%>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">

	      // Load the Visualization API and the corechart package.
	      google.charts.load('current', {'packages':['corechart']});

	      // Set a callback to run when the Google Visualization API is loaded.
	      google.charts.setOnLoadCallback(drawChart);

	      // Callback that creates and populates a data table,
	      // instantiates the pie chart, passes in the data and
	      // draws it.
	      function drawChart() {

	        // Create the data table.
	        var data = new google.visualization.DataTable();
	        data.addColumn('string', 'LoggedInMID');
	        data.addColumn('number', 'Count');
	        
	        // Second data table.
	        var data2 = new google.visualization.DataTable();
	        data2.addColumn('string', 'SecondaryMID');
	        data2.addColumn('number', 'Count');
	        
	     	// Fourth data table.
	        var data3 = new google.visualization.DataTable();
	        data3.addColumn('string', 'Month');
	        data3.addColumn('number', 'Count');
	        
	        // Fourth data table.
	        var data4 = new google.visualization.DataTable();
	        data4.addColumn('string', 'TransactionType');
	        data4.addColumn('number', 'Count');
	        
	        <%for (String key : loggedInUser.keySet()) {%>
	        	data.addRows([['<%=key%>', <%=loggedInUser.get(key)%>]]);
	        <%}%>
	        
	        <%for (String key : secondaryUser.keySet()) {%>
	        	data2.addRows([['<%=key%>', <%=secondaryUser.get(key)%>]]);
	        <%}%>
	        
	        <%for (String key : TransactionLogMonth.keySet()) {%>
        		data3.addRows([['<%=key%>', <%=TransactionLogMonth.get(key)%>]]);
        	<%}%>
	        
	        <%for (String key : TransactionType.keySet()) {%>
        		data4.addRows([['<%=key%>', <%=TransactionType.get(key)%>]]);
        	<%}%>

	        // Set chart options
	        var options = {'title':'LoggedInUser / Count',
	                       'width':400,
	                       'height':300};
	        // Second chart options.
	        var options2 = {'title':'SecondaryUser / Count',
                       'width':400,
                       'height':300};
	     	// Third chart options.
	        var options3 = {'title':'Month / Count',
                       'width':400,
                       'height':300};
	     	// Fourth chart options.
	        var options4 = {'title':'TransactionType / Count',
                       'width':400,
                       'height':300};

	        // Instantiate and draw our chart, passing in some options.
	        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
	        var chart2 = new google.visualization.ColumnChart(document.getElementById('chart_div2'));
	        var chart3 = new google.visualization.ColumnChart(document.getElementById('chart_div3'));
	        var chart4 = new google.visualization.ColumnChart(document.getElementById('chart_div4'));
	        chart.draw(data, options);
	        chart2.draw(data2, options2);
	        chart3.draw(data3, options3);
	        chart4.draw(data4, options4);
	      }
	</script>
<table id="graph_table" class="fTable">
	<tr>
		<td>
			<div id="chart_div"></div>
		</td>
		<td>
			<div id="chart_div2"></div>
		</td>
	</tr>
	<tr>
		<td>
			<div id="chart_div3"></div>
		</td>
		<td>
			<div id="chart_div4"></div>
		</td>
	</tr>
</table>
<%
}
%>

</form>

<%@include file="/footer.jsp" %>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="edu.ncsu.csc.itrust.beans.SurveyResultBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.HospitalBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewSurveyResultAction"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.enums.TransactionLogColumnType" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="edu.ncsu.csc.itrust.beans.TransactionBean" %>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.TransactionDAO" %>

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
	<h2>View Transaction Logs</h2>
<%
	// Default start date and end date.
	Date dt = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
	String startDate = sdf.format(dt).toString();
	String endDate = sdf2.format(dt).toString();
	
	// Define transactionDAO.
	TransactionDAO transactionDAO = DAOFactory.getProductionInstance().getTransactionDAO();

    // Get all users mid, secmids, transactioncodes.
	List<TransactionBean> mids = transactionDAO.getTransactionGroupBy(TransactionLogColumnType.parse(1));
	List<TransactionBean> secMids = transactionDAO.getTransactionGroupBy(TransactionLogColumnType.parse(2));
	List<TransactionBean> transactionCodes = transactionDAO.getTransactionGroupBy(TransactionLogColumnType.parse(3));
	List<TransactionBean> transactionResults = null;
	String mid = "";
	String secMid = "";
	String transactionCode = "";
	long inputMid = 0;
	long inputSecMid = 0;
	int inputTransactionCode = 0;

	boolean formIsFilled = request.getParameter("formIsFilled") != null
			&& request.getParameter("formIsFilled").equals("true");

	if (formIsFilled) {
		
		// Set start date and end date.
		startDate = request.getParameter("startDate");
		startDate = startDate.trim();
		endDate = request.getParameter("endDate");
		endDate = endDate.trim();
		
		// Set data from criteria.
		mid = request.getParameter("transactionMid");
		secMid = request.getParameter("transactionSecMid");
		transactionCode = request.getParameter("transactionCode");
		
		// Criteria condition to get desired transaction logs.
		if(mid.equals("All users")) {
			inputMid = -1;
		} else {
			inputMid = Long.valueOf(mid).longValue();
		}
		
		if(secMid.equals("All users")) {
			inputSecMid = -1;
		} else {
			inputSecMid = Long.valueOf(secMid).longValue();
		}
		if(transactionCode.equals("All codes")) {
			inputTransactionCode = -1;
		} else {
			inputTransactionCode = Integer.valueOf(transactionCode).intValue();
		}
		try {
			transactionResults = transactionDAO.getTransactionList(inputMid, inputSecMid, 
					sdf.parse(startDate), sdf2.parse(endDate), inputTransactionCode); 
		} catch (Exception e){
		}
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
		<td><select name="transactionMid">
				<option value="All users">All users</option>
				<%for(TransactionBean tran : mids) {%>
					<%--System.out.println(tran.getLoggedInMID()); --%>
					<option value="<%=tran.getLoggedInMID()%>" <%if (tran.getLoggedInMID() == inputMid) {%> selected="selected"<%}%>><%= StringEscapeUtils.escapeHtml("" + (tran.getLoggedInMID())) %></option><%
				}
				%>
			</select>
		</td>
		<td>secMid:</td>
		<td><select name="transactionSecMid">
				<option value="All users">All users</option>
				<%for(TransactionBean tran : secMids) {%>
					<%--System.out.println(tran.getLoggedInMID()); --%>
					<option value="<%=tran.getSecondaryMID()%>"><%= StringEscapeUtils.escapeHtml("" + (tran.getSecondaryMID())) %></option><%
				}
				%>
				
			</select>
		</td>
		<td>transactionCodes:</td>
		<td>
			<select name="transactionCode">
				<option value="All codes">All codes</option>
				<%for(TransactionBean tran : transactionCodes) {%>
					<%--System.out.println(tran.getLoggedInMID()); --%>
					<option value="<%=tran.getTransactionType().getCode()%>"><%= StringEscapeUtils.escapeHtml("" + (tran.getTransactionType().getCode())) %></option><%
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
			style="font-size: 16pt; font-weight: bold;" name = "graph" value = "Graph"></td>
	</tr>
</table>
<%
if (transactionResults != null && request.getParameter("view") != null) {
%>
<table id = "list_table" class="fTable">
	<tr class="subHeader">
		<th>Transaction ID</th>
		<th>TimeLogged</th>
		<th>name</th>
		<th>code</th>
		<th>Description</th>
		<th>LoggedInMID</th>
		<th>SecondaryMID</th>
		<th>AddedInfo</th>
	</tr>
	<%
	// Iterate through transaction logs results to build logs list.
	for (TransactionBean t : transactionResults) {
	%>
	<tr>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionID())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTimeLogged())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionType().name())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionType().getCode())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getTransactionType().getDescription())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getLoggedInMID())) %></td>
		<td><%= StringEscapeUtils.escapeHtml("" + (t.getSecondaryMID())) %></td>
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
if (transactionResults != null && request.getParameter("graph") != null) {
	HashMap<Long, Integer> loggedInUser = new HashMap<Long, Integer>();
	HashMap<Long, Integer> secondaryUser = new HashMap<Long, Integer>();
	HashMap<String, Integer> transactionMonth = new HashMap<String, Integer>();
	HashMap<Integer, Integer> transactionType = new HashMap<Integer, Integer>();
	// Iterate transaction logs results to build graphs.
	for (TransactionBean tb : transactionResults) {
		long key = tb.getLoggedInMID();
		long key2 = tb.getSecondaryMID();
		String key3 = tb.getTimeLogged().toString();
		key3 = key3.substring(0, 7);
		int key4 = tb.getTransactionType().getCode();
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
		if (transactionMonth.get(key3) == null){
			transactionMonth.put(key3, 1);
		} else {
			transactionMonth.put(key3, transactionMonth.get(key3) + 1);
		}
		// For fourth graph.
		if (transactionType.get(key4) == null) {
			transactionType.put(key4, 1);
		} else {
			transactionType.put(key4, transactionType.get(key4) + 1);
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
	        
	        <%for (long key : loggedInUser.keySet()) {%>
	        	data.addRows([['<%=key%>', <%=loggedInUser.get(key)%>]]);
	        <%}%>
	        
	        <%for (long key : secondaryUser.keySet()) {%>
	        	data2.addRows([['<%=key%>', <%=secondaryUser.get(key)%>]]);
	        <%}%>
	        
	        <%for (String key : transactionMonth.keySet()) {%>
        		data3.addRows([['<%=key%>', <%=transactionMonth.get(key)%>]]);
        	<%}%>
	        
	        <%for (int key : transactionType.keySet()) {%>
        		data4.addRows([['<%=key%>', <%=transactionType.get(key)%>]]);
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

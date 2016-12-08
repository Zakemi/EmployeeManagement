<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="employeeManagementApp">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Employee management</title>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.0-rc.2/angular.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-animate.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-sanitize.js"></script>
    <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-2.2.0.js"></script>
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
	<link href="/static/css/index.css" rel="stylesheet">
	
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
	<script src="http://cdn.jsdelivr.net/angular.chartjs/latest/angular-chart.min.js"></script>
	
	<script src="/static/js/app.js"></script>
	<script src="/static/js/controllers/ReportsCtrl.js"></script>
	<script src="/static/js/controllers/reports/AvgSalaryPerMonthsCtrl.js"></script>
	<script src="/static/js/services/reportsService.js"></script>
</head>
<body ng-controller="ReportsCtrl">
	<ul id="nav" class="nav nav-tabs">
	    <li role="presentation"><a href="/">Employee management</a></li>
	    <li role="presentation" class="active"><a href="/reports">Reports</a></li>
	</ul>
	<div class="row">
		<div class="col-md-3">
			<div class="list-group">
			    <a href="" class="list-group-item" ng-click="averageSalaryPerMonths()">Average salary per months</a>
			    <a href="" class="list-group-item">Track of the salaries</a>
			</div>
		</div>
		<div id="reports-ctrls" class="col-md-9">
		
		</div>
	</div>
</body>
</html>
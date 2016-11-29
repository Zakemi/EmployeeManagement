<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="employeeManagementApp">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.0-rc.2/angular.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-animate.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-sanitize.js"></script>
    <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-2.2.0.js"></script>
    <script src="/static/js/index.js"></script>
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
	<link href="/static/css/index.css" rel="stylesheet">
</head>
<body ng-controller="EmployeeManagementController as ctrl" class="modal-demo">
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">Search</h3>
	  </div>
	  <div class="panel-body">
	    Panel content
	  </div>
	</div>
	<div class="panel panel-default">
	  <!-- Default panel contents -->
	  <div class="panel-heading">
	  	<div class="row">
	  		<div class="col-md-6 col-sm-6">
	  			<button class="btn btn-primary" ng-click="openModal()">Add employee</button>
	  		</div>
	  		<div class="col-md-6 col-sm-6">
	  			<div class="loader pull-right" ng-show="loader"></div>
	  		</div>
	  	</div>
	  </div>
	
	  <!-- Table -->
	  <table class="table">
	  <thead>
	  	<tr>
	  		<td></td>
			<td>First name</td>
			<td>Last name</td>
			<td>Join date</td>
			<td>Phone</td>
			<td>Email</td>
			<td>Address</td>
			<td></td>
	  	</tr>
	  </thead>
	  <tbody>
	  	<tr ng-repeat="employee in employees">
	    	<td ng-click="deleteEmployee(employee)"><span class="glyphicon glyphicon-remove"></span></td>
	    	<td ng-bind="employee.firstName"></td>
	    	<td ng-bind="employee.lastName"></td>
	    	<td ng-bind="employee.joinDate.toString()"></td>
	    	<td ng-bind="employee.phone"></td>
	    	<td ng-bind="employee.email"></td>
	    	<td ng-bind="employee.address"></td>
	    	<td ng-click="openModal(employee)"><span class="glyphicon glyphicon-edit"></span></td>
	    </tr>
	  </tbody>
	    
	  </table>
	</div>
	
	<script type="text/ng-template" id="employeeModal.html">
        <div class="modal-header">
            <h3 class="modal-title" id="modal-title">I'm a modal!</h3>
        </div>
        <div class="modal-body" id="modal-body">
            <div class="employee-details">
				<label for="firstName">First name</label>
				<input type="text" class="form-control" name="firstName" ng-model="employee.firstName">
				<label for="lastName">Last name</label>
				<input type="text" class="form-control" name="lastName" ng-model="employee.lastName">
				<label for="joinDate">Join date</label>
				<p class="input-group">
                    <input type="text" class="form-control" name="joinDate" uib-datepicker-popup="yyyy/MM/dd" ng-model="employee.joinDate" is-open="datePopup.opened" datepicker-options="dateOptions" ng-required="true" close-text="Close" alt-input-formats="altInputFormats" />
                    <span class="input-group-btn">
                        <button type="button" class="btn btn-default" ng-click="open()"><span class="glyphicon glyphicon-calendar"></span></button>
                    </span>
                </p>
				
				<label for="phone">Phone</label>
				<input type="text" class="form-control" name="phone" ng-model="employee.phone">
				<label for="email">Email</label>
				<input type="text" class="form-control" name="email" ng-model="employee.email">
				<label for="address">Address</label>
				<input type="text" class="form-control" name="address" ng-model="employee.address">
			</div>
			<div class="employee-salary">
				Salary...
			</div>
        </div>
		<div class="modal-footer">
            <button class="btn btn-primary" type="button" ng-click="save()">OK</button>
            <button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>
        </div>
    </script>
	
</body>
</html>
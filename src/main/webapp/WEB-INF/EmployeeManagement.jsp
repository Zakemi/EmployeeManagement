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
	<script src="/static/js/controllers/EmployeeManagementCtrl.js"></script>
	<script src="/static/js/controllers/ModalInstanceCtrl.js"></script>
	<script src="/static/js/services/employeeService.js"></script>
</head>
<body ng-controller="EmployeeManagementCtrl as ctrl" class="modal-demo">
	<ul id="nav" class="nav nav-tabs">
	  <li role="presentation" class="active"><a href="/">Employee management</a></li>
	  <li role="presentation"><a href="/reports">Reports</a></li>
	</ul>
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">Search</h3>
	  </div>
	  <div class="panel-body">
	  	<form novalidate class="form-horizontal">
		    <div class="form-group">
		    	<label for="firstNameSearch" class="control-label col-md-2">First name:</label>
		    	<div class="col-md-4">
		    		<input type="text" class="form-control" ng-model="searchParams.firstName" id="firstNameSearch">
		    	</div>
		    	<label for="lastNameSearch" class="control-label col-md-2">Last name:</label>
		    	<div class="col-md-4">
		    		<input type="text" class="form-control" ng-model="searchParams.lastName" id="lastNameSearch">
		    	</div>
		    </div>
		    <div class="form-group">
		    	<label for="joinDateBeginSearch" class="control-label col-md-2">Join date from:</label>
		    	<div class="col-md-4">
		    		<p class="input-group">
			          <input type="text" id="joinDateBeginSearch" class="form-control" uib-datepicker-popup="{{format}}" ng-model="searchParams.joinDateBegin" is-open="popup.beginOpened" datepicker-options="beginDateOptions" close-text="Close" />
			          <span class="input-group-btn">
			            <button type="button" class="btn btn-default" ng-click="openBeginPicker()"><i class="glyphicon glyphicon-calendar"></i></button>
			          </span>
			        </p>
		    	</div>
		    	<label for="joinDateEndSearch" class="control-label col-md-2">Join date to:</label>
		    	<div class="col-md-4">
		    		<p class="input-group">
			          <input type="text" id="joinDateEndSearch" class="form-control" uib-datepicker-popup="{{format}}" ng-model="searchParams.joinDateEnd" is-open="popup.endOpened" min-date="searchParams.joinDateBegin" close-text="Close" datepicker-options="endDateOptions" />
			          <span class="input-group-btn">
			            <button type="button" class="btn btn-default" ng-click="openEndPicker()"><i class="glyphicon glyphicon-calendar"></i></button>
			          </span>
			        </p>
		    	</div>
		    </div>
		    <div class="form-group">
		    	<label for="phoneSearch" class="control-label col-md-2">Phone:</label>
		    	<div class="col-md-4">
		    		<input type="text" id="phoneSearch" class="form-control" ng-model="searchParams.phone">
		    	</div>
		    	<label for="emailSearch" class="control-label col-md-2">Email:</label>
		    	<div class="col-md-4">
		    		<input type="text" id="emailSearch" class="form-control" ng-model="searchParams.email">
		    	</div>
		    </div>
		    <div class="form-group">
		    	<label for="addressSearch" class="control-label col-md-2">Address:</label>
		    	<div class="col-md-10">
		    		<input type="text" id="addressSearch" class="form-control" ng-model="searchParams.address">
		    	</div>
		    </div>
	    	<button type="button" class="btn btn-default" ng-click="resetForm()">Reset</button>
	    	<button type="submit" class="btn btn-primary" ng-click="search()">Search</button>
	  	</form>
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
            <h3 class="modal-title" id="modal-title">Employee</h3>
        </div>
        <div class="modal-body" id="modal-body">
			<div class="row">
                <div class="employee-details col-md-6">
					<label for="firstName">First name</label>
					<input type="text" class="form-control" name="firstName" ng-model="employee.firstName">
					<label for="lastName">Last name</label>
					<input type="text" class="form-control" name="lastName" ng-model="employee.lastName">
					<label for="joinDate">Join date</label>
					<p class="input-group">
           	            <input type="text" class="form-control" name="joinDate" uib-datepicker-popup="yyyy/MM/dd" ng-model="employee.joinDate" is-open="datePopup.opened" ng-required="true" close-text="Close" alt-input-formats="altInputFormats" />
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
			
	    		<div class="employee-salary col-md-6">
					<div>Salary</div>
					<div class="row">
						<div class="col-md-5">Month</div>
						<div class="col-md-5">Amount</div>
						<div class="col-md-2">Delete</div>
					</div>

					<div ng-repeat="salaryUnit in employee.salary">
						<div class="row" ng-hide="salaryUnit.deleted">
							<div class="col-md-5">
					        	<p class="input-group">
        	   	                    <input type="text" class="form-control" name="date" uib-datepicker-popup="yyyy/MM" ng-model="salaryUnit.date" ng-required is-open="salaryUnit.pickerOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" alt-input-formats="altInputFormats" />
           		                    <span class="input-group-btn">
           	                            <button type="button" class="btn btn-default" ng-click="openSalaryDatePicker(salaryUnit)"><span class="glyphicon glyphicon-calendar"></span></button>
               	                    </span>
   	              	            </p>
							</div>
							<div class="col-md-5">
								<input type="number" step="any" class="form-control" name="amount" ng-required ng-model="salaryUnit.amount">
							</div>
							<div class="col-md-2">
								<button class="btn btn-default" ng-click="deleteUnit(salaryUnit, $index)">X</button>
							</div>
                    	</div>
					</div>
					<button class="btn btn-default" ng-click="addEmptyUnit()">+</button>
					
				</div>
			</div>
        </div>
		<div class="modal-footer">
            <button class="btn btn-primary" type="button" ng-click="save()">OK</button>
            <button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>
        </div>
    </script>
	
</body>
</html>
angular.module("employeeManagementApp").controller("TrackSalariesCtrl", ["$scope", "ReportsService", "$compile", function($scope, ReportsService, $compile){
	
	$scope.employees = [];
	
	ReportsService.getAllSalaryForEmployees([])
		.then(
				function(employees){
					$scope.employees = employees;
				}
		);
	
	setup();
	
	function setup(){
		var ctrlBody = angular.element(document.getElementById("ctrl-body"));
		ctrlBody.html('<div id="row">\
				<div id="employees" class="col-md-3">\
					<div class="list-group" > \
						<a href ng-repeat="employee in employees" class="list-group-item" ng-model="employee" ng-click="showData(employee)">{{employee.firstName + " " + employee.lastName}}</a> \
					</div> \
				</div> \
				<div id="displayer" class="col-md-9"> \
					<div id="chart"></div> \
					<div id="table"></div> \
				</div> \
				</div>');
		$compile(ctrlBody.contents())($scope);

		var chart = angular.element(document.getElementById("chart"));
		chart.html('<canvas id="lineChart" class="chart chart-line" chart-data="ydata" \
				chart-labels="xlabels" chart-series="series" chart-options="options" \
				chart-dataset-override="datasetOverride" chart-click="onClick" \
				</canvas> ');
		$compile(chart.contents())($scope);
		
		var table = angular.element(document.getElementById("table"));
		table.html('<table class="table"> \
				<thead> \
					<tr> \
						<td>Months</td> \
						<td>Amounts</td> \
					</tr> \
				</thead> \
				<tbody> \
					<tr ng-repeat="y in ydata"> \
						<td>{{xlabel[$index]}}</td> \
						<td>{{y}}</td> \
					</tr> \
				</tbody> \
				');
		$compile(table.contents())($scope);
	}
	
	$scope.showData = function(employee){
		$scope.ydata = [];
		$scope.xlabel = [];
		employee.salary.forEach(function(unit){
			$scope.xlabel.push(unit.year + " " + unit.month);
			$scope.ydata.push(unit.amount);
		})
		
	}
		
	
}]);
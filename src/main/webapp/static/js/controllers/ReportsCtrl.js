angular.module('employeeManagementApp').controller("ReportsCtrl", ["$scope", "$compile", function($scope, $compile){
	
	$scope.averageSalaryPerMonths = function(){
		console.log("Avg method called");
		var reportsElement = angular.element(document.getElementById("reports-ctrls"));
		reportsElement.html('<div id="ctrl-body" ng-controller="AvgSalaryPerMonthsCtrl as ctrl"></div>');
		$scope.activateView(reportsElement);
	}
	
	$scope.activateView = function(ele) {
	    $compile(ele.contents())($scope);
	  };
}]);
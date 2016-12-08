angular.module('employeeManagementApp').controller("AvgSalaryPerMonthsCtrl", ["$scope", "ReportsService", "$compile", function($scope, ReportsService, $compile){
	
	$scope.data = [];
	$scope.xlabels = [];
	$scope.ydata = [];
	
	ReportsService.averageSalaryPerMonths()
		.then(
				function(data){
					$scope.data = data;
					for (var i=0; i<$scope.data.length; i++){
						$scope.xlabels.push($scope.data[i].year + " " + $scope.data[i].month);
						$scope.ydata.push($scope.data[i].amount);
					}
					setChart();
				}
		);
	
	function setChart(){
		var mainElement = angular.element(document.getElementById("ctrl-body"));
		mainElement.html('<canvas id="lineChart" class="chart chart-line" chart-data="ydata" \
chart-labels="xlabels" chart-series="series" chart-options="options" \
chart-dataset-override="datasetOverride" chart-click="onClick" \
</canvas> ');
		$compile(mainElement.contents())($scope);
	}
	
	$scope.options = {
	    scales: {
	      yAxes: [
	        {
	          id: 'avgsalary',
	          type: 'linear',
	          display: true
	        }
	      ]
	    }
	  };
}]);
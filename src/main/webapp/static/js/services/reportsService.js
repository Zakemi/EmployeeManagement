angular.module('employeeManagementApp').service('ReportsService', ["$http", function($http){
	var factory = {
		averageSalaryPerMonths: averageSalaryPerMonths
	}
	
	return factory;
	
	function averageSalaryPerMonths(){
		return $http.get("/reports/averageSalaryPerMonths/")
			.then(
				function(response){
					console.log(response);
					return response.data;
				},
				function(errResponse){
					console.log("Something bad occured while getting averageSalaryPerMonths");
					console.log(errResponse);
					return {};
				}
			);
	}
}]);
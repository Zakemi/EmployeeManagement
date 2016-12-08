angular.module('employeeManagementApp').service('ReportsService', ["$http", function($http){
	var factory = {
		averageSalaryPerMonths: averageSalaryPerMonths,
		getAllSalaryForEmployees: getAllSalaryForEmployees
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
	
	// employees: list of employee id
	//            empty list means all employees
	function getAllSalaryForEmployees(employees){
		return $http.post("/reports/getAllSalaryForEmployees/", employees)
			.then(
					function(response){
						console.log(response);
						return response.data;
					},
					function(errResponse){
						console.log("Something bad occured while getting getAllSalaryForEmployees");
						console.log(errResponse);
						return [];
					}
			)
	}
}]);
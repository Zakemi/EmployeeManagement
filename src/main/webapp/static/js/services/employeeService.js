angular.module('employeeManagementApp').service('EmployeeService', ['$http', function($http){
	
	var factory = {
			getAllEmployees: getAllEmployees,
			updateEmployee: updateEmployee,
			addEmployee: addEmployee,
			deleteEmployee: deleteEmployee,
			search: search
	};
	
	return factory;
	
	function getAllEmployees(){
		return $http.get('/employees/')
		.then(
			function(response){
				console.log(response);
				return response.data;
			},
			function(errResponse){
				console.log("Something bad occured while getting employees")
				console.log(errResponse);
				return {};
			}
		);
	}
	
	function updateEmployee(employee){
		return $http.put('/employee/', employee)
			.then(
				function(response){
					console.log(response)
				},
				function(errResponse){
					console.log("Something bad occured while putting employee");
					console.log(errResponse);
				}
			);
	}
	
	function addEmployee(employee){
		return $http.post('/employee/', employee)
			.then(
				function(response){
					console.log(response)
				},
				function(errResponse){
					console.log("Something bad occured while posting employee");
					console.log(errResponse);
				}
			);
	}
	
	function deleteEmployee(employee){
		console.log("/employee/" + employee.webId);
		return $http.delete('/employee/' + employee.webId)
			.then(
				function(response){
					console.log(response);
				},
				function(errResponse){
					console.log("Something bad occured while deleting employee");
					console.log(errResponse);
				}
			);
	}
	
	function search(searchParams){
		return $http.post("/search/", searchParams)
			.then(
					function(response){
						console.log("Response data: " + response.data);
						return response.data;
					},
					function(errResponse){
						console.log("Something bad occured while searching employees");
					}
			);
	}
}]);
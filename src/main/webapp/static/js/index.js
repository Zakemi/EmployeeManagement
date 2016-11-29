var employeeManagementApp = angular.module('employeeManagementApp', ['ngAnimate', 'ngSanitize', 'ui.bootstrap']);

employeeManagementApp.service('EmployeeService', ['$http', function($http){
	
	var factory = {
			getAllEmployees: getAllEmployees,
			updateEmployee: updateEmployee,
			addEmployee: addEmployee,
			deleteEmployee: deleteEmployee
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
					console.log(response)
				},
				function(errResponse){
					console.log("Something bad occured while deleting employee");
					console.log(errResponse);
				}
			);
	}
}]);

employeeManagementApp.controller('EmployeeManagementController', ['$uibModal', '$scope', 'EmployeeService', 'uibDateParser', function($uibModal, $scope, EmployeeService, uibDateParser){
	
	$scope.employees = [];
	$scope.employee = null;
	$scope.dateFormat = "yyyy/MM/dd";
	$scope.loader = false;
	
	getEmployees();
	
	function getEmployees(){
		$scope.loader = true;
		EmployeeService.getAllEmployees()
			.then(
				function(data){
					$scope.employees = data;
					$scope.employees.forEach(employee => employee.joinDate = new Date(employee.joinDate));
					$scope.loader = false;
				}	
			);
	}
	
	$scope.deleteEmployee = function (employee){
		$scope.loader = true;
		EmployeeService.deleteEmployee(employee)
			.then(
				function(){
					$scope.loader = false;
					getEmployees();
				}	
			);
	}
	
	$scope.openModal = function (employee){
		if (employee != null){
			$scope.employee = angular.copy(employee);
		}
		else{
			$scope.employee = {
				id: null,
				firstName: "",
				lastName: "",
				address: "",
				joinDate: new Date(),
				phone: "",
				email: "",
				// salary?
			};
		}
		var modalInstance = $uibModal.open({
		      ariaLabelledBy: 'modal-title',
		      ariaDescribedBy: 'modal-body',
		      templateUrl: 'employeeModal.html',
		      controller: 'ModalInstanceCtrl',
		      controllerAs: '$ctrl',
		      size: "lg",
		      resolve: {
		        employee: function () {
		          return $scope.employee;
		        }
		      }
		    });
		modalInstance.result.then(
			function (employeeItem) {
		        console.log("Modal closed with saving");
		        $scope.loader = true;
		        if (employeeItem.id != null){
		        	EmployeeService.updateEmployee(employeeItem)
		    	    	.then(function(){
		    	    		console.log("Update done");
		    		        getEmployees();
		    		        $scope.loader = false;
		    	    	});
		        } else {
		        	EmployeeService.addEmployee(employeeItem)
		        		.then(function(){
		        			console.log("Addition done");
		    		        getEmployees();
		    		        $scope.loader = false;
		        		});
		        }
		    },
		    function () {
		        console.log('Modal dismissed at: ' + new Date());
		    });
	}
	
}]);

employeeManagementApp.controller('ModalInstanceCtrl', function ($uibModalInstance, $scope, EmployeeService, employee) {
  $scope.employee = employee;
  $scope.datePopup = {
    opened: false
  };
  
  $scope.dateOptions = {
  };
  
  $scope.open = function() {
	console.log("Try to open");
    $scope.datePopup.opened = true;
    console.log($scope.datePopup.opened);
  };

  $scope.save = function () {
    $uibModalInstance.close($scope.employee);
  };

  $scope.cancel = function () {
    $uibModalInstance.dismiss();
  };
});
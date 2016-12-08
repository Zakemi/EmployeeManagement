angular.module('employeeManagementApp').controller('EmployeeManagementCtrl', ['$uibModal', '$scope', 'EmployeeService', 'uibDateParser', function($uibModal, $scope, EmployeeService, uibDateParser){
	
	$scope.employees = [];
	$scope.employee = null;
	$scope.dateFormat = "yyyy/MM/dd";
	$scope.loader = false;
	
	$scope.searchParams = {
			firstName: "",
			lastName: "",
			joinDateBegin: null,
			joinDateEnd: null,
			phone: "",
			email: "",
			address: ""
	};
	$scope.popup = {
			beginOpened: false,
			endOpened: false
	};
	$scope.openBeginPicker = function (){
		$scope.popup.beginOpened = true;
	};
	
	$scope.openEndPicker = function (){
		$scope.popup.endOpened = true;
	};
	$scope.$watch('searchParams.joinDateBegin', function(newval){
		console.log($scope.searchParams.joinDateBegin);
		$scope.endDateOptions = {
				minDate: $scope.searchParams.joinDateBegin
		};
	});
	$scope.$watch('searchParams.joinDateEnd', function(newval){
		console.log($scope.searchParams.joinDateEnd);
		$scope.beginDateOptions = {
				maxDate: $scope.searchParams.joinDateEnd
		};
	});
	
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
				salary: []
				// TODO salary
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
	};
	
	$scope.resetForm = function (){
		$scope.searchParams = {
				firstName: "",
				lastName: "",
				joinDateBegin: null,
				joinDateEnd: null,
				phone: "",
				email: "",
				address: ""
		};
	};
	
	$scope.search = function (){
		EmployeeService.search($scope.searchParams)
			.then(
					function(employees){
						console.log("Search done");
						console.log(employees);
						$scope.employees = employees;
						$scope.employees.forEach(employee => employee.joinDate = new Date(employee.joinDate));
					}
			)
	}
	
	
}]);
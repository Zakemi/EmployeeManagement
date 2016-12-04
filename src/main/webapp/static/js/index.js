var employeeManagementApp = angular.module('employeeManagementApp', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'chart.js']);

employeeManagementApp.service('EmployeeService', ['$http', function($http){
	
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

employeeManagementApp.controller('EmployeeManagementController', ['$uibModal', '$scope', 'EmployeeService', 'uibDateParser', function($uibModal, $scope, EmployeeService, uibDateParser){
	
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

employeeManagementApp.controller('ModalInstanceCtrl', function ($uibModalInstance, $scope, EmployeeService, employee) {
  $scope.employee = employee;
  $scope.employee.salary.forEach(salary => salary.date = new Date(salary.date));
  $scope.datePopup = {
    opened: false
  };
  $scope.salaryDatePopup = {
	opened: false
  }
  
  $scope.dateOptions = {
    datepickerMode: "month",
    minMode: "month"
  };
  
  $scope.open = function() {
    $scope.datePopup.opened = true;
  };
  
  $scope.openSalaryDatePicker = function(salaryUnit){
	  salaryUnit.pickerOpened = true;
  }
  
  $scope.deleteUnit = function(salaryUnit, index){
	  if (salaryUnit.id == null){
		  $scope.employee.salary.splice(index, 1);
	  }
	  salaryUnit.deleted = true;
  }
  
  $scope.addEmptyUnit = function(){
	  $scope.employee.salary.push({
		  "id": null, 
		  "webId": null,
		  "employeeId": employee.id,
		  "date": null,
	  	  "amount": null
		  });
  }

  $scope.save = function () {
    $uibModalInstance.close($scope.employee);
  };

  $scope.cancel = function () {
    $uibModalInstance.dismiss();
  };
});

employeeManagementApp.service('ReportsService', ["$http", function($http){
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

employeeManagementApp.controller("ReportsController", ["$scope", "$compile", function($scope, $compile){
	
	$scope.averageSalaryPerMonths = function(){
		console.log("Avg method called");
		var reportsElement = angular.element(document.getElementById("reports-ctrls"));
		reportsElement.html('<div id="ctrl-body" ng-controller="averageSalaryPerMonthsController as ctrl"></div>');
		$scope.activateView(reportsElement);
	}
	
	$scope.activateView = function(ele) {
	    $compile(ele.contents())($scope);
	  };
}]);

employeeManagementApp.controller("averageSalaryPerMonthsController", ["$scope", "ReportsService", "$compile", function($scope, ReportsService, $compile){
	
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
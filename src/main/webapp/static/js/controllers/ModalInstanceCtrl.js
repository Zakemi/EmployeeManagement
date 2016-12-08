angular.module('employeeManagementApp').controller('ModalInstanceCtrl', function ($uibModalInstance, $scope, EmployeeService, employee) {
  $scope.employee = employee;
  $scope.employee.salary.forEach(salary => salary.date = new Date(salary.year, salary.month-1, 15));
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
		  "year": null,
		  "month": null,
		  "date": null,
	  	  "amount": null
		  });
  }

  $scope.save = function () {
	$scope.employee.salary.forEach(unit => unit.year = unit.date.getFullYear());
	$scope.employee.salary.forEach(unit => unit.month = unit.date.getMonth() + 1);
	$scope.employee.salary.forEach(unit => delete unit.date);
	console.log($scope.employee);
    $uibModalInstance.close($scope.employee);
  };

  $scope.cancel = function () {
    $uibModalInstance.dismiss();
  };
});
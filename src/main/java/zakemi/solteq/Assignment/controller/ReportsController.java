package zakemi.solteq.Assignment.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import zakemi.solteq.Assignment.database.MongoDBDatabase;
import zakemi.solteq.Assignment.database.SalaryDatabase;
import zakemi.solteq.Assignment.model.Employee;
import zakemi.solteq.Assignment.model.ReportsModels.MonthhySalary;

@Controller
@RequestMapping("/reports")
public class ReportsController {
	
	@Autowired
	SalaryDatabase salaryDatabase;
	
	@Autowired
	MongoDBDatabase employeeDatabase;
	
	@GetMapping
	public String getReportsIndex(){
		return "Reports";
	}
	
	@GetMapping("/averageSalaryPerMonths")
	public ResponseEntity<List<MonthhySalary>> getAverageSalary(){
		return new ResponseEntity<List<MonthhySalary>>(salaryDatabase.getAvarageSalaryPerMonthCompany(), HttpStatus.OK);
	}
	
	@PostMapping("/getAllSalaryForEmployees")
	public ResponseEntity<List<Employee>> getSalary(@RequestBody List<String> employeeIds){
		// if no id, return all
		List<Employee> employees = new ArrayList<Employee>();
		if (employeeIds.size() == 0){
			employees = employeeDatabase.getAllEmployee();
			for (Employee employee: employees){
				employee.setSalary(salaryDatabase.getSalaryByEmployeeId(employee.getId()));
			}
		}
		// TODO get employees by id
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}

}

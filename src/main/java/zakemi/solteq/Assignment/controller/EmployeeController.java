package zakemi.solteq.Assignment.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import zakemi.solteq.Assignment.database.MongoDBDatabase;
import zakemi.solteq.Assignment.database.SalaryDatabase;
import zakemi.solteq.Assignment.model.Employee;
import zakemi.solteq.Assignment.model.SalaryUnit;

@Controller
public class EmployeeController {

	@Autowired
	MongoDBDatabase database;
	
	@Autowired
	SalaryDatabase salaryDatabase;
	
	@GetMapping("/employees/")
	public ResponseEntity<List<Employee>> getAllEmployees(){
		List<Employee> employees = database.getAllEmployee();
		for (Employee employee: employees){
			employee.setSalary(salaryDatabase.getSalaryByEmployeeId(employee.getId()));
		}
		System.out.println("Size of the return list of employees: " + employees.size());
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}
	
	@PutMapping("/employee/")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
		if (employee == null)
			return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
		database.updateEmployeeById(employee);
		for (SalaryUnit unit: employee.getSalary()){
			System.out.println(unit.getEmployeeId().toHexString());
			if (unit.getId() == null)
				salaryDatabase.addSalaryUnit(unit);
			else if (unit.isDeleted())
				salaryDatabase.deleteSalaryUnit(unit);
			else
				salaryDatabase.updateSalaryUnit(unit);
		}
		return new ResponseEntity<Employee>(HttpStatus.OK);
	}
	
	@PostMapping("/employee/")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
		if (employee == null)
			return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
		ObjectId id = database.addEmployee(employee);
		if (employee.getSalary().size() > 0){
			for (SalaryUnit salary: employee.getSalary()){
				salary.setEmployeeId(id);
				salaryDatabase.addSalaryUnit(salary);
			}
		}
		return new ResponseEntity<Employee>(HttpStatus.OK);
	}
	
	@DeleteMapping("/employee/{webId}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable String webId){
		if (webId == null)
			return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
		database.deleteEmployeeByWebId(webId);
		return new ResponseEntity<Employee>(HttpStatus.OK);
	}
	
	
}

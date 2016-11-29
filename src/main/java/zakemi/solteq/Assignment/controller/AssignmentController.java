package zakemi.solteq.Assignment.controller;

import java.util.List;

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
import zakemi.solteq.Assignment.model.Employee;

@Controller
public class AssignmentController {

	@GetMapping("/employees/")
	public ResponseEntity<List<Employee>> getAllEmployees(){
		MongoDBDatabase database = new MongoDBDatabase();
		List<Employee> employees = database.getAllEmployee();
		System.out.println("Size of the return list of employees: " + employees.size());
		System.out.println(employees.get(0).getId());
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}
	
	@PutMapping("/employee/")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
		
		MongoDBDatabase database = new MongoDBDatabase();
		System.out.println(employee);
		if (employee == null)
			return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
		database.updateEmployeeById(employee);
		
		return new ResponseEntity<Employee>(HttpStatus.OK);
	}
	
	@PostMapping("/employee/")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
		MongoDBDatabase database = new MongoDBDatabase();
		if (employee == null)
			return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
		database.addEmployee(employee);
		return new ResponseEntity<Employee>(HttpStatus.OK);
	}
	
	@DeleteMapping("/employee/{webId}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable String webId){
		MongoDBDatabase database = new MongoDBDatabase();
		if (webId == null)
			return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
		database.deleteEmployeeByWebId(webId);
		return new ResponseEntity<Employee>(HttpStatus.OK);
	}
}

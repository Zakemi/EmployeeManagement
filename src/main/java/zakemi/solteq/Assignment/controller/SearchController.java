package zakemi.solteq.Assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import zakemi.solteq.Assignment.database.MongoDBDatabase;
import zakemi.solteq.Assignment.database.SalaryDatabase;
import zakemi.solteq.Assignment.model.Employee;
import zakemi.solteq.Assignment.model.SearchParams;

@Controller
public class SearchController {
	
	@Autowired
	MongoDBDatabase database;
	
	@Autowired
	SalaryDatabase salaryDatabase;
	
	@PostMapping("/search/")
	public ResponseEntity<List<Employee>> search(@RequestBody SearchParams params){
		List<Employee> result = database.search(params);
		for (Employee employee: result){
			employee.setSalary(salaryDatabase.getSalaryByEmployeeId(employee.getId()));
		}
		return new ResponseEntity<List<Employee>>(result, HttpStatus.OK);
	}

}

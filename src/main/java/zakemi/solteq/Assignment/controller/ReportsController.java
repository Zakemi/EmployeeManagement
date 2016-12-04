package zakemi.solteq.Assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import zakemi.solteq.Assignment.database.SalaryDatabase;
import zakemi.solteq.Assignment.model.ReportsModels.MonthhySalary;

@Controller
@RequestMapping("/reports")
public class ReportsController {
	
	@Autowired
	SalaryDatabase salaryDatabase;
	
	@GetMapping
	public String getReportsIndex(){
		return "Reports";
	}
	
	@GetMapping("/averageSalaryPerMonths")
	public ResponseEntity<List<MonthhySalary>> getAverageSalary(){
		return new ResponseEntity<List<MonthhySalary>>(salaryDatabase.getAvarageSalaryPerMonthCompany(), HttpStatus.OK);
	}

}

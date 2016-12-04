package zakemi.solteq.Assignment.database;

import java.util.List;

import org.bson.types.ObjectId;

import zakemi.solteq.Assignment.model.ReportsModels;
import zakemi.solteq.Assignment.model.SalaryUnit;


public interface SalaryDatabase {

	public void addSalaryUnit(SalaryUnit salaryUnit);
	public void updateSalaryUnit(SalaryUnit salaryUnit);
	public void deleteSalaryUnit(SalaryUnit salaryUnit);
	public List<SalaryUnit> getSalaryByEmployeeId(ObjectId employeeId);
	public List<ReportsModels.MonthhySalary> getAvarageSalaryPerMonthCompany();
	
}

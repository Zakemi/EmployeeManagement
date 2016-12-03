package zakemi.solteq.Assignment.database;

import java.util.List;

import org.bson.types.ObjectId;

import zakemi.solteq.Assignment.model.Employee;
import zakemi.solteq.Assignment.model.SearchParams;

public interface DatabaseConnection {
	
	public void addEmployee(Employee employee);
	public Employee getEmployeeById(ObjectId id);
	public void updateEmployeeById(Employee employee);
	public void deleteEmployeeById(ObjectId id);
	public List<Employee> search(SearchParams params);
	
}

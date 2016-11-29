package zakemi.solteq.Assignment.database;

import org.bson.types.ObjectId;

import zakemi.solteq.Assignment.model.Employee;

public interface DatabaseConnection {
	
	public void addEmployee(Employee employee);
	public Employee getEmployeeById(ObjectId id);
	public void updateEmployeeById(Employee employee);
	public void deleteEmployeeById(ObjectId id);
	
}

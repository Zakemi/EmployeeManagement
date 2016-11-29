package zakemi.solteq.Assignment.database;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import zakemi.solteq.Assignment.model.Employee;

public class MongoDBDatabase implements DatabaseConnection {
	
	private final String DATABASE = "soltequsermanagement";
	private final String EMPLOYEE_COLLECTION = "employee";
	private final String EMPLOYEE_ID = "_id";
	private final String EMPLOYEE_FIRSTNAME = "firstName";
	private final String EMPLOYEE_LASTNAME = "lastName";
	private final String EMPLOYEE_ADDRESS = "address";
	private final String EMPLOYEE_PHONE = "phone";
	private final String EMPLOYEE_EMAIL = "email";
	private final String EMPLOYEE_JOINDATE = "joinDate";
	
	private MongoClient mongoclient;
	private MongoDatabase database;
	private MongoCollection<Document> employeeCollection;
	
	public MongoDBDatabase(){
		// TODO remove
		String URI = "mongodb://admin:admin@ds151117.mlab.com:51117/soltequsermanagement";
		MongoClientURI connectionString = new MongoClientURI(URI);
		mongoclient = new MongoClient(connectionString);
		database = mongoclient.getDatabase(DATABASE);
		employeeCollection = database.getCollection(EMPLOYEE_COLLECTION);
	}

	public void addEmployee(Employee employee) {
		Document doc = new Document(EMPLOYEE_FIRSTNAME, employee.getFirstName())
				.append(EMPLOYEE_LASTNAME, employee.getLastName())
				.append(EMPLOYEE_ADDRESS, employee.getAddress())
				.append(EMPLOYEE_PHONE, employee.getPhone())
				.append(EMPLOYEE_EMAIL, employee.getEmail())
				.append(EMPLOYEE_JOINDATE, employee.getJoinDate());
		employeeCollection.insertOne(doc);
	}

	public Employee getEmployeeById(ObjectId id) {
		BasicDBObject searchQuery = new BasicDBObject(EMPLOYEE_ID, id);
		Document employeeDoc = employeeCollection.find(searchQuery).first();
		Employee employee = new Employee(
				employeeDoc.getObjectId(EMPLOYEE_ID),
				employeeDoc.getString(EMPLOYEE_FIRSTNAME),
				employeeDoc.getString(EMPLOYEE_LASTNAME),
				employeeDoc.getString(EMPLOYEE_ADDRESS),
				employeeDoc.getString(EMPLOYEE_PHONE),
				employeeDoc.getString(EMPLOYEE_EMAIL),
				employeeDoc.getDate(EMPLOYEE_JOINDATE));
		return employee;
	}

	public void updateEmployeeById(Employee employee) {
		BasicDBObject searchQuery = new BasicDBObject(EMPLOYEE_ID, employee.getId());
		Document employeeData = new Document()
			.append(EMPLOYEE_FIRSTNAME, employee.getFirstName())
			.append(EMPLOYEE_LASTNAME, employee.getLastName())
			.append(EMPLOYEE_ADDRESS, employee.getAddress())
			.append(EMPLOYEE_EMAIL, employee.getEmail())
			.append(EMPLOYEE_JOINDATE, employee.getJoinDate())
			.append(EMPLOYEE_PHONE, employee.getPhone());
		UpdateResult result = employeeCollection.updateOne(searchQuery, new Document("$set", employeeData));
		System.out.println(result);
		// TODO Update salary
	}

	public void deleteEmployeeById(ObjectId id) {
		BasicDBObject query = new BasicDBObject(EMPLOYEE_ID, id);
		DeleteResult result = employeeCollection.deleteOne(query);
		System.out.println(result.toString());
	}
	
	public void deleteEmployeeByWebId(String webId) {
		ObjectId id = new ObjectId(webId);
		BasicDBObject query = new BasicDBObject(EMPLOYEE_ID, id);
		DeleteResult result = employeeCollection.deleteOne(query);
		System.out.println(result.toString());
	}
	
	public List<Employee> getAllEmployee(){
		final List<Employee> employees = new ArrayList<Employee>();
		
		Block<Document> addDoc = new Block<Document>() {
			public void apply(Document employeeDoc) {
				Employee employee = new Employee(
						employeeDoc.getObjectId(EMPLOYEE_ID),
						employeeDoc.getString(EMPLOYEE_FIRSTNAME),
						employeeDoc.getString(EMPLOYEE_LASTNAME),
						employeeDoc.getString(EMPLOYEE_ADDRESS),
						employeeDoc.getString(EMPLOYEE_PHONE),
						employeeDoc.getString(EMPLOYEE_EMAIL),
						employeeDoc.getDate(EMPLOYEE_JOINDATE));
				employees.add(employee);
			}
		};
		
		employeeCollection.find().forEach(addDoc);;
		
		return employees;
	}

}

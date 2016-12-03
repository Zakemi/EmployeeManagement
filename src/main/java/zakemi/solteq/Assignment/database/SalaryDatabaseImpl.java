package zakemi.solteq.Assignment.database;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import zakemi.solteq.Assignment.model.SalaryUnit;

public class SalaryDatabaseImpl implements SalaryDatabase {

	MongoClient mongoclient;
	MongoDatabase database;
	MongoCollection<Document> salaryCollection;
	
	private static final String DATABASE = "soltequsermanagement";
	private static final String SALARY_COLLLECTION = "salary";
	private static final String ID = "_id";
	private static final String EMPLOYEE_ID = "employeeId";
	private static final String DATE = "date";
	private static final String AMOUNT = "amount";
	
	
	public SalaryDatabaseImpl() {
		String URI = "mongodb://admin:admin@ds151117.mlab.com:51117/soltequsermanagement";
		MongoClientURI connectionString = new MongoClientURI(URI);
		mongoclient = new MongoClient(connectionString);
		database = mongoclient.getDatabase(DATABASE);
		salaryCollection = database.getCollection(SALARY_COLLLECTION);
	}
	
	@Override
	public void addSalaryUnit(SalaryUnit salaryUnit) {
		BasicDBObject project = new BasicDBObject(EMPLOYEE_ID, "$" + EMPLOYEE_ID)
				.append("year", new BasicDBObject("$year", "$" + DATE))
				.append("month", new BasicDBObject("$month", "$" + DATE));
		BasicDBObject match = new BasicDBObject(EMPLOYEE_ID, salaryUnit.getEmployeeId())
				.append("year", salaryUnit.getDate().getYear())
				.append("month", salaryUnit.getDate().getMonth());
		Document result = salaryCollection.aggregate(Arrays.asList(
				new BasicDBObject("$project", project), 
				new BasicDBObject("$match", match))).first();
		// if we can't find document with the year and month, we can add it
		if (result == null){
			salaryCollection.insertOne(new Document(EMPLOYEE_ID, salaryUnit.getEmployeeId())
					.append(DATE, salaryUnit.getDate())
					.append(AMOUNT, Double.parseDouble(salaryUnit.getAmount().toString()))
					);
		}
	}
	
	@Override
	public void updateSalaryUnit(SalaryUnit salaryUnit) {
		BasicDBObject searchQuery = new BasicDBObject(ID, salaryUnit.getId());
		Document salaryDoc = salaryCollection.find(searchQuery).first();
		LocalDate date = salaryDoc.getDate(DATE).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		// if the date is unchanged, we can fresh the amount
		if ( date.getYear() == salaryUnit.getDate().getYear() && 
			date.getMonthValue() == salaryUnit.getDate().getMonth() ){
			BasicDBObject updateQuery = new BasicDBObject(EMPLOYEE_ID, salaryUnit.getEmployeeId())
					.append(DATE, salaryUnit.getDate())
					.append(AMOUNT, Double.parseDouble(salaryUnit.getAmount().toString()));
			salaryCollection.updateOne(searchQuery, updateQuery);
		} 
		// but if the date is changed, we must check is there any document with the same date
		// so delete the document and add it again
		else {
			deleteSalaryUnit(salaryUnit);
			addSalaryUnit(salaryUnit);
		}
		
	}

	@Override
	public void deleteSalaryUnit(SalaryUnit salaryUnit) {
		salaryCollection.deleteOne(new BasicDBObject(ID, salaryUnit.getId()));
	}

	@Override
	public List<SalaryUnit> getSalaryByEmployeeId(ObjectId employeeId) {
		List<SalaryUnit> result = new ArrayList<SalaryUnit>();
		
		Iterable<Document> salaries = salaryCollection.find(new BasicDBObject(EMPLOYEE_ID, employeeId));
		
		for (Document doc: salaries){
			//LocalDate date = doc.getDate(DATE).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			result.add(new SalaryUnit(doc.getObjectId(ID), 
					doc.getObjectId(EMPLOYEE_ID), 
					doc.getDate(DATE), 
					doc.getDouble(AMOUNT)));
		}
		
		return result;
	}

}

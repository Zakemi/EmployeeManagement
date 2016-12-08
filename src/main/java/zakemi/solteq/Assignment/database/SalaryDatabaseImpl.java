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

import zakemi.solteq.Assignment.model.ReportsModels;
import zakemi.solteq.Assignment.model.ReportsModels.MonthhySalary;
import zakemi.solteq.Assignment.model.SalaryUnit;

public class SalaryDatabaseImpl implements SalaryDatabase {

	MongoClient mongoclient;
	MongoDatabase database;
	MongoCollection<Document> salaryCollection;
	
	private static final String DATABASE = "soltequsermanagement";
	private static final String SALARY_COLLLECTION = "salary";
	private static final String ID = "_id";
	private static final String EMPLOYEE_ID = "employeeId";
	private static final String YEAR = "year";
	private static final String MONTH = "month";
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
				.append("year", "$" + YEAR)
				.append("month", "$" + MONTH);
		BasicDBObject match = new BasicDBObject(EMPLOYEE_ID, salaryUnit.getEmployeeId())
				.append("year", salaryUnit.getYear())
				.append("month", salaryUnit.getMonth());
		System.out.println(project);
		System.out.println(match);
		Document result = salaryCollection.aggregate(Arrays.asList(
				new BasicDBObject("$project", project), 
				new BasicDBObject("$match", match))).first();
		System.out.println(result);
		// if we can't find document with the year and month, we can add it
		if (result == null){
			salaryCollection.insertOne(new Document(EMPLOYEE_ID, salaryUnit.getEmployeeId())
					.append(YEAR, salaryUnit.getYear())
					.append(MONTH, salaryUnit.getMonth())
					.append(AMOUNT, Double.parseDouble(salaryUnit.getAmount().toString()))
					);
		}
	}
	
	@Override
	public void updateSalaryUnit(SalaryUnit salaryUnit) {
		BasicDBObject searchQuery = new BasicDBObject(ID, salaryUnit.getId());
		Document salaryDoc = salaryCollection.find(searchQuery).first();
		// if the date is unchanged, we can fresh the amount
		if ( salaryDoc.getInteger(YEAR) == salaryUnit.getYear() && 
				salaryDoc.getInteger(MONTH) == salaryUnit.getMonth() ){
			BasicDBObject updateQuery = new BasicDBObject(EMPLOYEE_ID, salaryUnit.getEmployeeId())
					.append(YEAR, salaryUnit.getYear())
					.append(MONTH, salaryUnit.getMonth())
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
		
		Iterable<Document> salaries = salaryCollection.find(new BasicDBObject(EMPLOYEE_ID, employeeId))
				.sort(new BasicDBObject(YEAR, 1).append(MONTH, 1));
		
		for (Document doc: salaries){
			//LocalDate date = doc.getDate(DATE).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			result.add(new SalaryUnit(doc.getObjectId(ID), 
					doc.getObjectId(EMPLOYEE_ID), 
					doc.getInteger(YEAR),
					doc.getInteger(MONTH),
					doc.getDouble(AMOUNT)));
		}
		
		return result;
	}

	@Override
	public List<MonthhySalary> getAvarageSalaryPerMonthCompany() {
		List<MonthhySalary> result = new ArrayList<MonthhySalary>();
		//db.salary.aggregate([{ $group: { _id: { year: {$year: "$date"}, month: {$month:"$date"} }, 
		//                                 averageSalary: {$avg: "$amount"} }}])
		BasicDBObject id = new BasicDBObject();
		id.append("year", "$" + YEAR);
		id.append("month", "$" + MONTH);
		BasicDBObject avgSalary = new BasicDBObject("$avg", "$" + AMOUNT);
		BasicDBObject group = new BasicDBObject();
		group.append("_id", id);
		group.append("avgSalary", avgSalary);
		BasicDBObject sort = new BasicDBObject("_id.year", 1)
				.append("_id.month", 1);
		Iterable<Document> aggRes = salaryCollection.aggregate(Arrays.asList(new BasicDBObject("$group", group),
																			 new BasicDBObject("$sort", sort)));
		for (Document doc: aggRes){
			Document doc_id = (Document) doc.get("_id");
			result.add(new ReportsModels.MonthhySalary(doc_id.getInteger("year"), doc_id.getInteger("month"), doc.getDouble("avgSalary")));
		}
		return result;
	}

}

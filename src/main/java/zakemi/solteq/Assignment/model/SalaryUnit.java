package zakemi.solteq.Assignment.model;

import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class SalaryUnit {

	private ObjectId id;
	private String webId;
	private ObjectId employeeId;
	private Integer year;
	private Integer month;
	private Double amount;
	private boolean deleted;
	
	public SalaryUnit(){
	}
	
	public SalaryUnit(ObjectId id, ObjectId employeeId, Integer year, Integer month, Double amount) {
		this.id = id;
		this.employeeId = employeeId;
		this.year = year;
		this.month = month;
		this.amount = amount;
		this.deleted = false;
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getWebId() {
		return webId;
	}

	public void setWebId(String webId) {
		this.webId = webId;
	}

	public ObjectId getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(ObjectId employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}

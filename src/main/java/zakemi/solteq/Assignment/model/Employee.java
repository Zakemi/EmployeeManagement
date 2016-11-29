package zakemi.solteq.Assignment.model;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class Employee {
	
	private ObjectId id;
	private String webId;

	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private String email;
	private Date joinDate;
	//private Salary salary;
	
	public Employee(){
		
	}

	public Employee(String firstName, String lastName, String address, String phone, String email, Date joinDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.joinDate = joinDate;
		//this.salary = new Salary();
	}
	
	
	
	public Employee(ObjectId id, String firstName, String lastName, String address, String phone, String email,
			Date joinDate) {
		this.id = id;
		this.webId = this.id.toHexString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.joinDate = joinDate;
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
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	/*public Salary getSalary() {
		return salary;
	}

	public void setSalary(Salary salary) {
		this.salary = salary;
	}*/
	
	

}

package zakemi.solteq.Assignment.model;

import java.util.Date;

public class SearchParams {

	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private String email;
	private Date joinDateBegin;
	private Date joinDateEnd;
	private Integer sortDirection;
	private String sortSelected;
	
	public SearchParams(){}

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

	public Date getJoinDateBegin() {
		return joinDateBegin;
	}

	public void setJoinDateBegin(Date joinDateBegin) {
		this.joinDateBegin = joinDateBegin;
	}

	public Date getJoinDateEnd() {
		return joinDateEnd;
	}

	public void setJoinDateEnd(Date joinDateEnd) {
		this.joinDateEnd = joinDateEnd;
	}

	public Integer getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(Integer sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getSortSelected() {
		return sortSelected;
	}

	public void setSortSelected(String sortSelected) {
		this.sortSelected = sortSelected;
	}
	
	
	
}

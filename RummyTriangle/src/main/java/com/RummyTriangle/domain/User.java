package com.RummyTriangle.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "app_user")
public class User {

	public User() {
		
	}
	public User(String userName, String mobileNo, String firstName, String lastName, String gender) {
		super();
		this.userName = userName;
//		this.mobileNo = mobileNo;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.gender = gender;
	}
	
	public User(String userName) {
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*
	 * public String getEmailAddress() { return emailAddress; } public void
	 * setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
	 * public String getMobileNo() { return mobileNo; } public void
	 * setMobileNo(String mobileNo) { this.mobileNo = mobileNo; } public String
	 * getFirstName() { return firstName; } public void setFirstName(String
	 * firstName) { this.firstName = firstName; } public String getLastName() {
	 * return lastName; } public void setLastName(String lastName) { this.lastName =
	 * lastName; } public Date getDob() { return dob; } public void setDob(Date dob)
	 * { this.dob = dob; } public String getGender() { return gender; } public void
	 * setGender(String gender) { this.gender = gender; }
	 */
	/*
	 * public Address getAddress() { return address; } public void
	 * setAddress(Address address) { this.address = address; } public BankAccount
	 * getBankDetails() { return bankDetails; } public void
	 * setBankDetails(BankAccount bankDetails) { this.bankDetails = bankDetails; }
	 */	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "userName is required")
	@Size(min = 2, max = 50)
	private String userName;

	private String password;

	private boolean active = true;

	@Size(max = 100)
	private String roles;
//	String emailAddress;
//	String mobileNo;
//	String firstName;
//	String lastName;
//	Date dob;
//	String gender;
	//Address address;
	//BankAccount bankDetails;
	
}

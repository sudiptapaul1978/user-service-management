package com.cts.user.application.to;

import java.util.List;

public class UserRequest {
	String userName;
	String password;
	String firstName;
	String lastName;
	UserBirthDate dateOfBirth;
	String address;
	String contactNo;
	String emailAddress;
	String role;
	String userError;
	List<UserPolicy> policies;
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the dateOfBirth
	 */
	public UserBirthDate getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(UserBirthDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the contactNo
	 */
	public String getContactNo() {
		return contactNo;
	}
	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the userError
	 */
	public String getUserError() {
		return userError;
	}
	/**
	 * @param userError the userError to set
	 */
	public void setUserError(String userError) {
		this.userError = userError;
	}
	/**
	 * @return the policies
	 */
	public List<UserPolicy> getPolicies() {
		return policies;
	}
	/**
	 * @param policies the policies to set
	 */
	public void setPolicies(List<UserPolicy> policies) {
		this.policies = policies;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRequest [");
		if (userName != null)
			builder.append("userName=").append(userName).append(", ");
		if (password != null)
			builder.append("password=").append(password).append(", ");
		if (firstName != null)
			builder.append("firstName=").append(firstName).append(", ");
		if (lastName != null)
			builder.append("lastName=").append(lastName).append(", ");
		if (dateOfBirth != null)
			builder.append("dateOfBirth=").append(dateOfBirth).append(", ");
		if (address != null)
			builder.append("address=").append(address).append(", ");
		if (contactNo != null)
			builder.append("contactNo=").append(contactNo).append(", ");
		if (emailAddress != null)
			builder.append("emailAddress=").append(emailAddress).append(", ");
		if (role != null)
			builder.append("role=").append(role).append(", ");
		if (userError != null)
			builder.append("userError=").append(userError).append(", ");
		if (policies != null)
			builder.append("policies=").append(policies);
		builder.append("]");
		return builder.toString();
	}

	
	
}

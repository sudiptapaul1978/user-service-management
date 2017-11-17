package com.cts.user.application.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cts.user.application.common.ApplicationConstants;
import com.cts.user.application.common.Utils;
import com.cts.user.application.dao.UserRepository;
import com.cts.user.application.document.User;
import com.cts.user.application.to.UserBirthDate;
import com.cts.user.application.to.UserPolicy;
import com.cts.user.application.to.UserRequest;

@Service
public class UserService {
	@Autowired
	public UserRepository userRepository;
	
	/**
	 * This method create the User in database
	 * @param requestedUser
	 * @return
	 * @throws Exception
	 */
	public UserRequest createUser(UserRequest requestedUser) throws Exception{
		User user = createUserObject(requestedUser);
		
		//insert the data
		userRepository.insert(user);
		System.out.println("User creted successfully as >> " + user);
		
		//now update the user name
		requestedUser.setUserName(user.getUserName());
		return requestedUser;
	}

	/**
	 * This method add a policy to a given user
	 * @param requestedUser
	 * @return
	 * @throws Exception
	 */
	public boolean addPolicyForUser(String userName, String policyId, 
			String amountPaid, String policyEndDate) {
		boolean success = false;
		
		UserPolicy userPolicy = new UserPolicy();
		userPolicy.setPolicyId(policyId);
		try {
			userPolicy.setAmountPaid(Float.parseFloat(amountPaid));
			userPolicy.setPolicyEndDate(Utils.DATE_FORMATER.parse(policyEndDate));
		} catch (ParseException e) {
			System.out.println("In Utils: "+ e.getMessage());
			//throw e;
		}
		
		User user = userRepository.findOne(userName);
		List<UserPolicy> userPolicies = user.getPolicies();
		if(userPolicies==null) {
			userPolicies = new ArrayList<UserPolicy> ();
		}
		userPolicies.add(userPolicy);
		user.setPolicies(userPolicies);
		
		userRepository.save(user);
		
		success = true;
		return success;
	}
	
	/**
	 * This method creates the 'Admin' user
	 * @param requestedUser
	 * @return
	 * @throws Exception
	 */
	public UserRequest createAdmin() {
		User user = new User();
		user.setUserName("Admin");
		user.setPassword("Admin");
		user.setRole(ApplicationConstants.ROLE_ADMIN);
		userRepository.save(user);
		UserRequest requestedUser = convertToUserRequestObject(user);
		
		return requestedUser;
	}
	
	/**
	 * This method creates the User object based on the UserRequest object
	 * @param requestedUser
	 * @return
	 * @throws ParseException
	 */
	private User createUserObject(UserRequest requestedUser) throws ParseException {
		User user = new User();
		user.setPassword(requestedUser.getPassword());
		String firstName = requestedUser.getFirstName();
		user.setFirstName(firstName);
		user.setLastName(requestedUser.getLastName());
		UserBirthDate dateRequest = requestedUser.getDateOfBirth();
		try {
			user.setDateOfBirth(Utils.convertDateRequestToDate(dateRequest));
		} catch (ParseException e) {
			System.out.println("In UserService:" + e.getMessage());
			//throw e;
		}
		user.setUserName(createUserName(firstName, dateRequest));
		user.setAddress(requestedUser.getAddress());
		user.setContactNo(requestedUser.getContactNo());
		user.setEmailAddress(requestedUser.getEmailAddress());
		if(!StringUtils.isEmpty(requestedUser.getRole()))
		{
			user.setRole(requestedUser.getRole());
		}else
		{
			user.setRole(ApplicationConstants.ROLE_GENERAL_USER);
		}
		
		if(requestedUser.getPolicies() != null && !requestedUser.getPolicies().isEmpty()){
			user.setPolicies(requestedUser.getPolicies());
		}
		return user;
	}
	
	/**
	 * This method creates the user name for the registering user
	 * @param firstName
	 * @param dateRequest
	 * @return
	 */
	private String createUserName(String firstName, UserBirthDate dateRequest) {
		String userName;
		String day = dateRequest.getDate().getDay();
		if(day!=null && day.length()==1) {
			day = "0" + day;
		}
		String month = dateRequest.getDate().getMonth();
		if(month!=null && month.length()==1) {
			month = "0" + month;
		}
		
		userName = firstName + day + month;
		return userName;
	}
	
	/**
	 * This method validates the user for a given user name and password
	 * @param userName
	 * @return
	 */
	public UserRequest validateUser(String userName, String password) {
		UserRequest userRequest = new UserRequest();
		User user = userRepository.findOne(userName);
		System.out.println("The User from database > " + user);
		if(user!=null && password!=null) {
			String userPwd = user.getPassword();
			if(userPwd!=null
					&& password.length()==userPwd.length() 
					&& password.indexOf(userPwd)==0) {
				userRequest = convertToUserRequestObject(user);
				userRequest.setUserError(ApplicationConstants.NO);
			} else if (user.getRole()!=null && user.getRole().equalsIgnoreCase(ApplicationConstants.ROLE_GENERAL_USER)) {
				userRequest.setUserError("You are a not registered User. Register to login");
			} else if (user.getRole()!=null && user.getRole().equalsIgnoreCase(ApplicationConstants.ROLE_ADMIN)) {
				userRequest.setUserError("Contact Admin Service");
			} 
		}else {
			userRequest.setUserError("You are a not registered User. Register to login");
		}
		
		return userRequest;
	}
	
	/**
	 * This return back the user by name. This is a test method
	 * @param userName
	 * @return
	 */
	public UserRequest getUserByUsername(String userName) {
		User user =  null;
		UserRequest userRequest = null;
		try {
			user = userRepository.findOne(userName);
			if(user != null) {
				userRequest = convertToUserRequestObject(user);
			}
		}catch(Exception exception) {
			//just ignore
		}
		return userRequest;
	}
	
	/**
	 * This method creates UserRequest object based on the database entity
	 * @param user
	 * @return
	 */
	private UserRequest convertToUserRequestObject(User user) {
		UserRequest userRequest = new UserRequest();
		BeanUtils.copyProperties(user, userRequest);
		userRequest.setFirstName(user.getFirstName());
		userRequest.setLastName(user.getLastName());
		userRequest.setUserName(user.getUserName());
		userRequest.setPassword(user.getPassword());
		userRequest.setRole(user.getRole());
		userRequest.setPolicies(user.getPolicies());
		return userRequest;
		
	}

	/**
	 * This method returns all the users
	 * @return
	 */
	public List<UserRequest> getAllUsers() {
		List<UserRequest> userRequests = new ArrayList<UserRequest>();		
		List<User> users = userRepository.findAll();
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			userRequests.add(convertToUserRequestObject(user));
		}
		return userRequests;
	}
	
	/**
	 * This method deletes a given user
	 * @param policyNumber
	 * @return
	 */
	public boolean delete(String userName) {
		boolean success = false;

		userRepository.delete(userName);
		success = true;
		return success;
	}

	/**
	 * This method deletes all the users
	 * @return
	 */
	public boolean deleteAll() {
		boolean success = false;

		userRepository.deleteAll();
		success = true;
		return success;
	}
}

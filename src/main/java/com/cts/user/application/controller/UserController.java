package com.cts.user.application.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.user.application.common.ApplicationConstants;
import com.cts.user.application.service.UserService;
import com.cts.user.application.to.LoginRequest;
import com.cts.user.application.to.UserRequest;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@CrossOrigin
	@PostMapping("/register")
	public Map<String, Object> createUser(@RequestBody UserRequest user) {

		System.out.println("Saving user: " + user);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			user = userService.createUser(user);
			dataMap.put(ApplicationConstants.RESPONSE_MESSAGE, "User created successfully");
			dataMap.put(ApplicationConstants.RESPONSE_STATUS, ApplicationConstants.SUCCESS);
			user.setPassword(null);
		} catch (Exception e) {
			dataMap.put(ApplicationConstants.RESPONSE_MESSAGE, "Error: User details not saved");
			dataMap.put(ApplicationConstants.RESPONSE_STATUS, ApplicationConstants.ERROR);
			user = null;
		}

		dataMap.put(ApplicationConstants.RESPONSE_OBJECT_USER, user);
		return dataMap;
	}

	@CrossOrigin
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody LoginRequest loginRequest) {
		UserRequest user = userService.validateUser(loginRequest.getUserName(), loginRequest.getPassword());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String message = null;
		String status = ApplicationConstants.ERROR;
		if (user != null && user.getUserError().equalsIgnoreCase(ApplicationConstants.NO)) {
			message = "User validated successfully";
			status = ApplicationConstants.SUCCESS;
		} else {
			message = user.getUserError();
		}
		dataMap.put(ApplicationConstants.RESPONSE_MESSAGE, message);
		dataMap.put(ApplicationConstants.RESPONSE_STATUS, status);
		dataMap.put(ApplicationConstants.RESPONSE_OBJECT_USER, user);
		return dataMap;
	}

	/**
	 * Test operation to get all the users in DB inPCF
	 * 
	 * @return
	 */
	@CrossOrigin
	@RequestMapping("/getAllUsers")
	public Map<String, Object> getAllUsers() {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(ApplicationConstants.RESPONSE_OBJECT_ALLUSER, userService.getAllUsers());
		return dataMap;
	}

	@CrossOrigin
	@RequestMapping("/addUserPolicy")
	public Map<String, Object> addUserPolicy(@RequestParam String userName, @RequestParam String policyId,
			@RequestParam String amountPaid, @RequestParam String policyEndDate) {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		boolean success = userService.addPolicyForUser(userName, policyId, amountPaid, policyEndDate);

		dataMap.put(ApplicationConstants.RESPONSE_POLICYADDED_IND, success);
		dataMap.put(ApplicationConstants.RESPONSE_MESSAGE, "User Policy Added Successfully");
		dataMap.put(ApplicationConstants.RESPONSE_STATUS, success ? ApplicationConstants.SUCCESS : ApplicationConstants.ERROR);
		return dataMap;
	}


	@CrossOrigin
	@RequestMapping("/createAdmin")
	public Map<String, Object> createAdmin() {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		UserRequest adminUser = userService.createAdmin();

		dataMap.put(ApplicationConstants.RESPONSE_MESSAGE, "User Policy Added Successfully");
		dataMap.put(ApplicationConstants.RESPONSE_STATUS, adminUser != null ? ApplicationConstants.SUCCESS : ApplicationConstants.ERROR);
		
		dataMap.put(ApplicationConstants.RESPONSE_OBJECT_USER, adminUser);
		return dataMap;
	}
	
	@CrossOrigin
	@RequestMapping("/deleteUser")
	public Map<String, Object> deletetPolicyById(@RequestParam String userName) {
		boolean success = userService.delete(userName);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(success) {
			dataMap.put(ApplicationConstants.RESPONSE_MESSAGE, "User deleted successfully");
			dataMap.put(ApplicationConstants.RESPONSE_STATUS, ApplicationConstants.SUCCESS);
		}else {
			dataMap.put(ApplicationConstants.RESPONSE_MESSAGE, "User not found/ not deleted");
			dataMap.put(ApplicationConstants.RESPONSE_STATUS, ApplicationConstants.ERROR);
		}
		return dataMap;
	}
	
	@CrossOrigin
	@RequestMapping("/deleteAll")
	public Map<String, Object> deletetAll() {
		boolean success = userService.deleteAll();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(success) {
			dataMap.put(ApplicationConstants.RESPONSE_MESSAGE, "All Users deleted successfully");
			dataMap.put(ApplicationConstants.RESPONSE_STATUS, ApplicationConstants.SUCCESS);
		}else {
			dataMap.put(ApplicationConstants.RESPONSE_MESSAGE, "Not deleted");
			dataMap.put(ApplicationConstants.RESPONSE_STATUS, ApplicationConstants.ERROR);
		}
		return dataMap;
	}

}

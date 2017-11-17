package com.cts.user.application.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.cts.user.application.service.UserService;
import com.cts.user.application.to.DateRequest;
import com.cts.user.application.to.UserBirthDate;
import com.cts.user.application.to.UserRequest;

@Component
public class UserApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	UserService userService;

	/**
	 * This event is executed as late as conceivably possible to indicate that the
	 * application is ready to service requests.
	 */
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		System.out.println("Inside onApplicationEvent() ... ");
		try{
			//let's create the admin User
			System.out.println("[StartUP Log]Going to create Admin User ... ");
			UserRequest adminUser  = userService.createAdmin();
			System.out.println("[StartUP Log]Admin User created successfully ... ");
			
			//Lst's create a dummy User
			UserRequest testUser = new UserRequest(); 
			testUser.setFirstName("dummytesterf");
			testUser.setLastName("dummytesterl");
			testUser.setAddress("address 1");
			testUser.setContactNo("12345");
			UserBirthDate userBirthDate = new UserBirthDate();
			DateRequest dateRequest = new DateRequest();
			dateRequest.setMonth("11");
			dateRequest.setDay("11");
			dateRequest.setYear("1978");
			userBirthDate.setDate(dateRequest);
			testUser.setDateOfBirth(userBirthDate);
			testUser.setEmailAddress("test_email@test.com");
			testUser.setPassword("12345");
			testUser.setRole("user");
			
			if(userService.getUserByUsername("dummytesterf1111") == null){
				testUser = userService.createUser(testUser);
				for (int arry = 3; arry <= 7; arry++) {
					userService.addPolicyForUser(testUser.getUserName(), "dummyid " + arry, (2530*arry*3)+"", "2017-11-"+(arry*4));
				}
				testUser = userService.getUserByUsername("dummytesterf1111");
				System.out.println("[StartUP Log]The Dummy test user is created");
			}else{
				testUser = userService.getUserByUsername("dummytesterf1111");
				if(testUser.getPolicies()== null || testUser.getPolicies().size()==0){
					System.out.println("No policies are attached with this User [testfirst1111], so adding Policies ...");
					for (int arry = 3; arry <= 7; arry++) {
						userService.addPolicyForUser(testUser.getUserName(), "dummyid " + arry, (2530*arry*3)+"", "2017-11-"+(arry*4));
					}
					testUser = userService.getUserByUsername("dummytesterf1111");
					System.out.println("[StartUP Log]Added policies are > " + testUser.getPolicies());
				}
				System.out.println("[StartUP Log]The Dummy User is already there, so not created newly!");
			}
			System.out.println("\n#################################################################################################################\n");
			System.out.println("[StartUP Log]The Admin test user is > " + adminUser);
			System.out.println("[StartUP Log]The Dummy test user is > " + testUser);
			System.out.println("\n#################################################################################################################\n");
		}catch(Exception exception){
			System.out.println("[StartUP Log]Exception occured during Admin user or Dummy user creation during Start up ... Please ignore!!!");
		}
		return;
	}

}

package com.cts.user.application.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.user.application.service.UserService;
import com.cts.user.application.to.UserRequest;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private String registerUserExpression = "{\r\n" + "	\"firstName\":\"testfirst\",\r\n"
			+ "	\"lastName\":\"testlast\",\r\n" + "	\"dateOfBirth\":{\r\n" + "		\"date\":{\r\n"
			+ "			\"year\":1978,\r\n" + "			\"month\":11,\r\n" + "			\"day\":11\r\n" + "		}\r\n"
			+ "	},\r\n" + "	\"address\":\"Kolkata\",\r\n" + "	\"contactNo\":\"8337093338\",\r\n"
			+ "	\"emailAddress\":\"tester@tester.com\",\r\n" + "	\"password\":\"12345\"\r\n" + "}";

	private String loginUserExpression = "{\r\n" + "	\"userName\":\"testfirst1111\",\r\n"
			+ "	\"password\":\"12345\"\r\n" + "}";

	@Test
	public void createAdmin() throws Exception {
		Map<String, Object> mockAdminResp = new HashMap<String, Object>();
		UserRequest user = new UserRequest();
		user.setUserName("Admin");
		user.setPassword("Admin");
		user.setRole("admin");
		mockAdminResp.put("users", user);
		Mockito.when(userService.createAdmin()).thenReturn(user);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/createAdmin/")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("Result is > " + result.getResponse().getContentAsString());
		String expected = "{\"message\":\"Admin User Added Successfully\",\"user\":{\"userName\":\"Admin\",\"password\":\"Admin\",\"firstName\":null,\"lastName\":null,\"dateOfBirth\":null,\"address\":null,\"contactNo\":null,\"emailAddress\":null,\"role\":\"admin\",\"userError\":null,\"policies\":null},\"status\":\"1\"}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void createUser() throws Exception {
		Map<String, Object> mockAdminResp = new HashMap<String, Object>();

		UserRequest user = new UserRequest();
		user.setUserName("testfirst1111");
		user.setFirstName("testfirst");
		user.setLastName("testlast");
		user.setContactNo("8337093338");
		user.setEmailAddress("tester@tester.com");
		user.setAddress("Kolkata");
		user.setRole("user");

		mockAdminResp.put("users", user);
		Mockito.when(userService.createUser(Matchers.any(UserRequest.class))).thenReturn(user);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register/")
				.accept(MediaType.APPLICATION_JSON).content(registerUserExpression)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("Result is > " + result.getResponse().getContentAsString());
		String expected = "{\"message\":\"User created successfully\","
				+ "\"user\":{\"userName\":\"testfirst1111\",\"password\":null,\"firstName\":\"testfirst\","
				+ "\"lastName\":\"testlast\",\"address\":\"Kolkata\",\"contactNo\":\"8337093338\","
				+ "\"emailAddress\":\"tester@tester.com\",\"role\":\"user\",\"userError\":null,"
				+ "\"policies\":null},\"status\":\"1\"}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void createUserFailed() throws Exception {
		Map<String, Object> mockAdminResp = new HashMap<String, Object>();

		UserRequest user = new UserRequest();
		user.setUserName("testfirst1111");
		user.setFirstName("testfirst");
		user.setLastName("testlast");
		user.setContactNo("8337093338");
		user.setEmailAddress("tester@tester.com");
		user.setAddress("Kolkata");
		user.setRole("user");

		mockAdminResp.put("users", user);
		Mockito.when(userService.createUser(Matchers.any(UserRequest.class))).thenReturn(null);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register/")
				.accept(MediaType.APPLICATION_JSON).content(registerUserExpression)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("Result is > " + result.getResponse().getContentAsString());
		String expected = "{\"message\":\"Error: User details not saved\","
				+ "\"user\":null,\"status\":\"0\"}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void loginSuccess() throws Exception {
		Map<String, Object> mockAdminResp = new HashMap<String, Object>();

		UserRequest user = new UserRequest();
		user.setUserName("testfirst1111");
		user.setPassword("12345");
		user.setFirstName("testfirst");
		user.setLastName("testlast");
		user.setContactNo("8337093338");
		user.setEmailAddress("tester@tester.com");
		user.setAddress("Kolkata");
		user.setUserError("N");
		user.setRole("user");

		mockAdminResp.put("users", user);
		Mockito.when(userService.validateUser(Matchers.anyString(), Matchers.anyString())).thenReturn(user);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login/").accept(MediaType.APPLICATION_JSON)
				.content(loginUserExpression).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("Result is > " + result.getResponse().getContentAsString());
		String expected = "{\"message\":\"User validated successfully\","
				+ "\"user\":{\"userName\":\"testfirst1111\",\"password\":\"12345\",\"firstName\":\"testfirst\","
				+ "\"lastName\":\"testlast\",\"address\":\"Kolkata\",\"contactNo\":\"8337093338\",\"emailAddress\":\"tester@tester.com\","
				+ "\"role\":\"user\",\"userError\":\"N\",\"policies\":null},\"status\":\"1\"}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void loginFailureAdmin() throws Exception {
		Map<String, Object> mockAdminResp = new HashMap<String, Object>();

		UserRequest user = new UserRequest();
		user.setUserName("testfirst1111");
		user.setPassword("12345");
		user.setFirstName("testfirst");
		user.setLastName("testlast");
		user.setContactNo("8337093338");
		user.setEmailAddress("tester@tester.com");
		user.setAddress("Kolkata");
		user.setUserError("Contact Admin Service");
		user.setRole("admin");

		mockAdminResp.put("users", user);
		Mockito.when(userService.validateUser(Matchers.anyString(), Matchers.anyString())).thenReturn(user);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login/").accept(MediaType.APPLICATION_JSON)
				.content(loginUserExpression).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("Result is > " + result.getResponse().getContentAsString());
		String expected = "{\"message\":\"Contact Admin Service\","
				+ "\"user\":{\"userName\":\"testfirst1111\",\"password\":\"12345\",\"firstName\":\"testfirst\","
				+ "\"lastName\":\"testlast\",\"address\":\"Kolkata\",\"contactNo\":\"8337093338\","
				+ "\"emailAddress\":\"tester@tester.com\",\"role\":\"admin\","
				+ "\"userError\":\"Contact Admin Service\",\"policies\":null}}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void loginFailureOther() throws Exception {
		Map<String, Object> mockAdminResp = new HashMap<String, Object>();

		UserRequest user = new UserRequest();
		user.setUserName("testfirst1111");
		user.setPassword("12345");
		user.setFirstName("testfirst");
		user.setLastName("testlast");
		user.setContactNo("8337093338");
		user.setEmailAddress("tester@tester.com");
		user.setAddress("Kolkata");
		user.setUserError("You are a not registered User. Register to login");
		user.setRole("user");

		mockAdminResp.put("users", user);
		Mockito.when(userService.validateUser(Matchers.anyString(), Matchers.anyString())).thenReturn(user);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login/").accept(MediaType.APPLICATION_JSON)
				.content(loginUserExpression).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("Result is > " + result.getResponse().getContentAsString());
		String expected = "{\"message\":\"You are a not registered User. Register to login\","
				+ "\"user\":{\"userName\":\"testfirst1111\",\"password\":\"12345\",\"firstName\":\"testfirst\","
				+ "\"lastName\":\"testlast\",\"address\":\"Kolkata\",\"contactNo\":\"8337093338\","
				+ "\"emailAddress\":\"tester@tester.com\",\"role\":\"user\","
				+ "\"userError\":\"You are a not registered User. Register to login\",\"policies\":null}}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}
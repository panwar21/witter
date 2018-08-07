
package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.impl.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Test
	public void test_getUser_ok() throws Exception {
		User user = getUser();
		when(userService.getByUserName("userNumber3")).thenReturn(user);
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/users/userNumber3")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isFound())
				.andReturn();
		String expectedString = getUserJSONString();
		JSONAssert.assertEquals(expectedString, result.getResponse().getContentAsString(), false);
		
	}
	
	
	@Test
	public void test_getUser_UserNotFound() throws Exception {
		
		when(userService.getByUserName("userNumber3")).thenReturn(null);
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/users/userNumber3")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isNotFound())
				.andReturn();
		String expectedString = "{\"message\":\"user not found\",\"details\":\"User Not Found\"}";
		JSONAssert.assertEquals(expectedString, result.getResponse().getContentAsString(), false);
		
	}
	
	private User getUser() {
		User user = new User();
		user.setId(4);
		user.setUserName("userNumber3");
		user.setEmailId("user3@witter.com");
		user.setDateCreated(new Date());
		user.setFirstName("user3");
		user.setLastName("user3LastName");
		user.setSummary("I am third user");
		return user;
	}
	private String getUserJSONString() {
		return "{\"id\":4,\"userName\":\"userNumber3\",\"emailId\":\"user3@witter.com\",\"firstName\":\"user3\",\"lastName\":\"user3LastName\",\"summary\":\"I am third user\"}";
	}
	private String getUserJSONStringObject() {
		return "{\"userName\":\"userNumber3\",\"emailId\":\"user3@witter.com\",\"firstName\":\"user3\",\"lastName\":\"user3LastName\",\"summary\":\"I am third user\"}";
	}
	@Test
	public void test_updateUser_ok() throws Exception {
		User user = getUser();
		String userJson = getUserJSONString();
		String body = (new ObjectMapper()).valueToTree(user).toString();
		when(userService.updateUser(user)).thenReturn(user);
		
		RequestBuilder request = MockMvcRequestBuilders
				.put("/users/userNumber3")
				.accept(MediaType.APPLICATION_JSON)
				.content(getUserJSONStringObject())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8");
				
		
		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();
		String expectedString = getUserJSONString();
		JSONAssert.assertEquals(expectedString, result.getResponse().getContentAsString(), false);
		
	}
	
	@Test
	public void test_postUser_ok() throws Exception {
		User user = new User();
		user.setUserName("userNumber3");
		user.setEmailId("user3@witter.com");
		user.setDateCreated(new Date());
		user.setFirstName("user3");
		user.setLastName("user3LastName");
		user.setSummary("I am third user");
		
		String userJson = getUserJSONString();
		String body = (new ObjectMapper()).valueToTree(user).toString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String json = mapper.writeValueAsString(user);
		when(userService.createUser(user)).thenReturn(user);
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/users")
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8");
			
		
		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isCreated())
				.andReturn();
		String expectedString = getUserJSONString();
		JSONAssert.assertEquals(expectedString, result.getResponse().getContentAsString(), false);
		
	}
	
	@Test
	public void test_deleteUser_ok() throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/users/userNumber3")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isNoContent())
				.andReturn();
		
		verify(userService, times(1)).deleteUser("userNumber3");;
		
	}
	
}

package com.example.demo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.demo.exception.EmailIdExistsException;
import com.example.demo.exception.UserExistsException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	
	@Mock
	UserRepository userRepositoryMock;
	
	@InjectMocks
	UserService userService;
	
	@Test
	public void testGetUserByName() {
		User user = new User();
		user.setId(1);
		user.setUserName("user1");
		user.setEmailId("user1@witter.com");
		user.setDateCreated(new Date());
		user.setFirstName("user1");
		user.setLastName("user1LastName");
		user.setSummary("user1 summary");
		
		when(userRepositoryMock.findByUserName("user1")).thenReturn(
				user);
		assertEquals(userService.getByUserName("user1").getUserName(), "user1");
	}
	
	
	@Test(expected = UserExistsException.class)
	public void testCreateUser_UserExistsException() throws Exception {
		User user = new User();
		user.setId(1);
		user.setUserName("user1");
		user.setEmailId("user1@witter.com");
		user.setDateCreated(new Date());
		user.setFirstName("user1");
		user.setLastName("user1LastName");
		user.setSummary("user1 summary");
		
		when(userRepositoryMock.existsByUserName("user1")).thenReturn(
				true);
		userService.createUser(user);
	}
	
	@Test(expected = EmailIdExistsException.class)
	public void testCreateUser_EmailIdExistsException() throws Exception {
		User user = new User();
		user.setId(1);
		user.setUserName("user1");
		user.setEmailId("user1@witter.com");
		user.setDateCreated(new Date());
		user.setFirstName("user1");
		user.setLastName("user1LastName");
		user.setSummary("user1 summary");
		
		when(userRepositoryMock.existsByEmailId("user1@witter.com")).thenReturn(
				true);
		userService.createUser(user);
	}
	
	
	
}

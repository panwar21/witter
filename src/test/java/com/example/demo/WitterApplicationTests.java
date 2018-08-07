package com.example.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class WitterApplicationTests {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Test
	public void contextLoads() {
		ResponseEntity<User> response = testRestTemplate.getForEntity("/users/userNumber3", User.class);
		
		assertEquals("userNumber3", response.getBody().getUserName());
		
	}

}

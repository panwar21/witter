package com.example.demo.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.EmailIdExistsException;
import com.example.demo.exception.UserExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
/**
* Business Service class for User Resource
* @version 1.0
* @author AP
*/

@Service
public class UserService {
	/**
	 * UserRepository dependency injection
	 */
	@Autowired
	private UserRepository userRepository;
	/**
	 * method to create a user
	 * @param user
	 * @return created User
	 * @throws Exception
	 */
	@Transactional
	public User createUser(User user) throws Exception {
		
		if(!userRepository.existsByUserName(user.getUserName())) {
			if(!userRepository.existsByEmailId(user.getEmailId())) {
				user.setDateCreated(new Date());
				userRepository.save(user);
				return user;
			}else {
				throw new EmailIdExistsException("user with email-id exists");
			}
		}else {
			throw new UserExistsException("userName already exists");
		}
		
		
	}
	/**
	 * method to find user by userName
	 * @param userName
	 * @return User
	 */
	@Transactional
	public User getByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	/**
	 * method to update a user
	 * @param user
	 * @return updated User
	 */
	@Transactional
	public User updateUser(User user) {
		
		if(userRepository.existsByUserName(user.getUserName())) {
			User userInDB = userRepository.findByUserName(user.getUserName());
			
			userInDB.setEmailId( user.getEmailId());
			userInDB.setFirstName(user.getFirstName());
			userInDB.setLastName(user.getLastName());
			userInDB.setSummary(user.getSummary());
			return userInDB;
		}else {
			throw new UserNotFoundException("user not found");
		}
		
		
	}
	/**
	 * method to delete a user
	 * @param userName
	 */
	@Transactional
	public void deleteUser(String userName) {
		if(userRepository.existsByUserName(userName)) {
			User userInDB = userRepository.findByUserName(userName);
			userRepository.delete(userInDB);
		}else {
			throw new UserNotFoundException("user not found");
		}
		
	}

}

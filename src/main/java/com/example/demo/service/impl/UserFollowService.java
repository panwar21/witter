package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserFollowService {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void createFollower(String userName, String userToFollow) throws Exception {
		User userDB = userService.getByUserName(userName);
		User userToFollowDB = userService.getByUserName(userToFollow);
		if(userDB != null && userToFollowDB != null) {
			if(!userToFollowDB.getFollowSet().contains(userDB)) {
				userToFollowDB.getFollowSet().add(userDB);
			}
		}else {
			throw new Exception("user follower can not be created");
		}
		
	}
	@Transactional
	public void deleteFollower(String userName, String userFollowed) throws Exception {
		User userDB = userService.getByUserName(userName);
		User userFollowedDB = userService.getByUserName(userFollowed);
		if(userDB != null && userFollowedDB != null) {
			if(userFollowedDB.getFollowSet().contains(userDB)) {
				userFollowedDB.getFollowSet().remove(userDB);
			}
		}else {
			throw new Exception("user follower can not be deleted");
		}
		
	}
	@Transactional
	public List<User> getFollowers(String userName) {
		User user = userService.getByUserName(userName);
		if(user != null) {
			return new ArrayList<User>(user.getFollowSet());
		}else {
			throw new UserNotFoundException("user not found");
		}
		
	}
	
	@Transactional
	public List<User> getFollowees(String userName) {
		User user = userService.getByUserName(userName);
		if(user != null) {
			return userRepository.getFollowees(user.getId());
		}else {
			throw new UserNotFoundException("user not found");
		}
	}
}

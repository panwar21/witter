package com.example.demo.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.UserFeedService;

@Service
public class DefaultUserFeedService implements UserFeedService {

	@Autowired
	private UserService userService;
	
	
	@Autowired
	private PostRepository postRepository;
	
	/* (non-Javadoc)
	 * @see com.example.demo.service.impl.UserFeedService#getFeed(java.lang.String)
	 */
	@Override
	@Transactional
	public List<Post> getFeed(String userName) {
		int feedLength = 10;
		User userDB = userService.getByUserName(userName);
		if(userDB != null) {

			return postRepository.getUserFeed(userDB.getId(), feedLength);
			
		}else {
			throw new UserNotFoundException("user not found");
		}
		}
	
}

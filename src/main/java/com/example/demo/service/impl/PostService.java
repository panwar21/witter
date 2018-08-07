package com.example.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostRepository postRepository;
	
	@Transactional
	public Post createPost(String userName, Post post) {
		User user = userService.getByUserName(userName);
		if(user!=null) {
			post.setUser(user);
			post.setDateCreated(new Date());
			postRepository.save(post);
			
			return post;
			
		}else {
			throw new UserNotFoundException("user not found");
		}
		
	}
	
	@Transactional
	public Post getPost(String userName, long postId) throws Exception {
		User user = userService.getByUserName(userName);
		if(user!=null) {
			Post post = postRepository.getPostOfUser(postId, user.getId());
			if(post != null) {
				return post;
			}else {
				throw new Exception("post not found");
			}
			
		}else {
			throw new UserNotFoundException("user not found"); 
		}
	}
	
	@Transactional
	public List<Post> getPosts(String userName) throws Exception {
		User user = userService.getByUserName(userName);
		if(user!=null) {
			return postRepository.findByUser(user);
			
		}else {
			throw new UserNotFoundException("user not found"); 
		}
	}
	
	@Transactional
	public Post updatePost(String userName, long postId, Post post) throws Exception {
		User user = userService.getByUserName(userName);
		if(user!=null) {
			Optional<Post> postDB = postRepository.findById(postId);
			if(postDB.isPresent() && postDB.get().getUser().equals(user)) {
				postDB.get().setContent(post.getContent());
				
				return postDB.get();
			}else {
				throw new Exception("post not found");
			}
			
		}else {
			throw new UserNotFoundException("user not found");
		}
		
	}
	
	@Transactional
	public void deletePost(String userName, long postId) throws Exception {
		User user = userService.getByUserName(userName);
		if(user!=null) {
			Optional<Post> postDB = postRepository.findById(postId);
			if(postDB.isPresent() && postDB.get().getUser().equals(user)) {
				//todo delete only my created post
				postRepository.delete(postDB.get());
			}else {
				throw new Exception("post can not be deleted");
			}
			
		}else {
			throw new UserNotFoundException("user not found");
		}
		
	}
}

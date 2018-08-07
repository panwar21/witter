package com.example.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;


@Service
public class CommentService {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Transactional
	public Comment createComment(String userName, long postId, Comment comment) throws Exception {
		
		User user = userService.getByUserName(userName);
		if(user!=null) {
			Post post = postService.getPost(userName, postId);
			if(post!=null) {
				comment.setPost(post);
				comment.setUser(user);
				comment.setDateCreated(new Date());
				commentRepository.save(comment);
				return comment;
			}else {
				throw new Exception("post not found");
			}
			
		}else {
			throw new UserNotFoundException("user not found");
		}
			
	}
	
	@Transactional
	public Comment getComment(String userName, long postId, long commentId) throws Exception {
		
		User user = userService.getByUserName(userName);
		if(user!=null) {
			Post post = postService.getPost(userName, postId);
			if(post!=null) {
				Optional<Comment> comment = commentRepository.findById(commentId);
				if(comment.isPresent() && comment.get().getPost().equals(post)) {
					return comment.get();
				}else {
					throw new Exception("comment not found");
				}
				
			}else {
				throw new Exception("post not found");
			}
			
		}else {
			throw new UserNotFoundException("user not found");
		}
			
	}
	
	
	@Transactional
	public List<Comment> getComments(String userName, long postId) throws Exception {
		
		User user = userService.getByUserName(userName);
		if(user!=null) {
			Post post = postService.getPost(userName, postId);
			if(post!=null) {
				return commentRepository.findByPost(post);
				
			}else {
				throw new Exception("post not found");
			}
			
		}else {
			throw new UserNotFoundException("user not found");
		}
			
	}

	@Transactional
	public Comment updateComment(String userName, long postId, long commentId, Comment comment) throws Exception {
	
		User user = userService.getByUserName(userName);
		if(user!=null) {
			Post post = postService.getPost(userName, postId);
			if(post!=null) {
				Optional<Comment> commentDB = commentRepository.findById(commentId);
				if(commentDB.isPresent() && commentDB.get().getPost().equals(post)
						&& commentDB.get().getUser().equals(user)) {
					commentDB.get().setContent(comment.getContent());
					return commentDB.get();
				}else {
					throw new Exception("comment can not be updated");
				}
				
			}else {
				throw new Exception("post not found");
			}
			
		}else {
			throw new UserNotFoundException("user not found");
		}
			
	}
	
	@Transactional
	public void deleteComment(String userName, long postId, long commentId) throws Exception {
		
		User user = userService.getByUserName(userName);
		if(user!=null) {
			Post post = postService.getPost(userName, postId);
			if(post!=null) {
				Optional<Comment> commentDB = commentRepository.findById(commentId);
				if(commentDB.isPresent() && commentDB.get().getPost().equals(post)
						&& commentDB.get().getUser().equals(user)) {
					commentRepository.delete(commentDB.get());
				}else {
					throw new Exception("comment can not be deleted");
				}
				
			}else {
				throw new Exception("post not found");
			}
			
		}else {
			throw new UserNotFoundException("user not found");
		}
			
	}






	
}

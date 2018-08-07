package com.example.demo.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.service.impl.CommentService;

@RestController
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(path = "/user/{userName}/posts/{postId}/comments", method = RequestMethod.POST)
	public ResponseEntity<Resource<Comment>> createComment(@PathVariable String userName,
			@PathVariable long postId,
			@Valid @RequestBody Comment comment) throws Exception{
		
		
		Comment commentCreated = commentService.createComment(userName, postId, comment);
		
		ControllerLinkBuilder linkSelf = linkTo(methodOn(this.getClass()).getComment(userName, postId, commentCreated.getId()));
		
		ControllerLinkBuilder linkPost = linkTo(methodOn(PostController.class).getPost(userName, postId));
		ControllerLinkBuilder linkUser = linkTo(methodOn(UserController.class).getUser(userName));
		
		Resource<Comment> resource = new Resource<Comment>(commentCreated);
		resource.add(linkSelf.withSelfRel());
		resource.add(linkPost.withRel("post"));
		resource.add(linkUser.withRel("user"));
		
		
		return new ResponseEntity<Resource<Comment>>(resource, HttpStatus.FOUND);
	}
	
	@RequestMapping(path = "/user/{userName}/posts/{postId}/comments/{commentId}", method = RequestMethod.GET)
	public ResponseEntity<Resource<Comment>> getComment(@PathVariable String userName,
			@PathVariable long postId,
			@PathVariable long commentId) throws Exception{
		
		Comment comment = commentService.getComment(userName, postId, commentId);
		
		ControllerLinkBuilder linkSelf = linkTo(methodOn(this.getClass()).getComment(userName, postId, commentId));
		
		ControllerLinkBuilder linkPost = linkTo(methodOn(PostController.class).getPost(userName, postId));
		ControllerLinkBuilder linkUser = linkTo(methodOn(UserController.class).getUser(userName));
		
		Resource<Comment> resource = new Resource<Comment>(comment);
		resource.add(linkSelf.withSelfRel());
		resource.add(linkPost.withRel("post"));
		resource.add(linkUser.withRel("user"));
		
		
		return new ResponseEntity<Resource<Comment>>(resource, HttpStatus.FOUND);
		
	}
	
	@RequestMapping(path = "/user/{userName}/posts/{postId}/comments", method = RequestMethod.GET)
	public ResponseEntity<List<Resource<Comment>>> getComments(@PathVariable String userName,
			@PathVariable long postId) throws Exception{
		
		
		List<Comment> comments = commentService.getComments(userName, postId);
		List<Resource<Comment>> listResource = new ArrayList<>();
		for(Comment comment : comments) {
			
			ControllerLinkBuilder linkSelf = linkTo(methodOn(this.getClass()).getComment(userName, postId, comment.getId()));
			
			ControllerLinkBuilder linkPost = linkTo(methodOn(PostController.class).getPost(userName, postId));
			ControllerLinkBuilder linkUser = linkTo(methodOn(UserController.class).getUser(userName));
			
			Resource<Comment> resource = new Resource<Comment>(comment);
			resource.add(linkSelf.withSelfRel());
			resource.add(linkPost.withRel("post"));
			resource.add(linkUser.withRel("user"));
			
			listResource.add(resource);
		}
		
		return new ResponseEntity<List<Resource<Comment>>>(listResource, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/user/{userName}/posts/{postId}/comments/{commentId}", method = RequestMethod.PUT)
	public ResponseEntity<Resource<Comment>> updateComment(@PathVariable String userName,
			@PathVariable long postId,
			@PathVariable long commentId,
			@Valid @RequestBody Comment comment) throws Exception{
		
		
		Comment commnetUpdated = commentService.updateComment(userName, postId, commentId, comment);
		
		ControllerLinkBuilder linkSelf = linkTo(methodOn(this.getClass()).getComment(userName, postId, commentId));
		
		ControllerLinkBuilder linkPost = linkTo(methodOn(PostController.class).getPost(userName, postId));
		ControllerLinkBuilder linkUser = linkTo(methodOn(UserController.class).getUser(userName));
		
		Resource<Comment> resource = new Resource<Comment>(commnetUpdated);
		resource.add(linkSelf.withSelfRel());
		resource.add(linkPost.withRel("post"));
		resource.add(linkUser.withRel("user"));
		
		
		return new ResponseEntity<Resource<Comment>>(resource, HttpStatus.FOUND);
	}
	
	@RequestMapping(path = "/user/{userName}/posts/{postId}/comments/{commentId}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteComment(@PathVariable String userName,
			@PathVariable long postId,
			@PathVariable long commentId) throws Exception{
		
		
		commentService.deleteComment(userName, postId, commentId);
		return ResponseEntity.noContent().build();
	}
	
}

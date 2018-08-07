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

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.impl.PostService;

/**
* Controller class for Post resource
* @version 1.0
* @author AP
*/
@RestController
public class PostController {
	
	

	
	@Autowired
	private PostService postService;
	
	@RequestMapping(path = "/user/{userName}/posts", method = RequestMethod.POST)
	public ResponseEntity<Resource<Post>> createPost(@PathVariable String userName, @Valid @RequestBody Post post) throws Exception{
		
		Post postCreated = postService.createPost(userName, post);
		

		ControllerLinkBuilder linkSelf = linkTo(methodOn(this.getClass()).getPost(userName, postCreated.getId()));
		
		ControllerLinkBuilder linkComments = linkTo(methodOn(CommentController.class).getComments(userName, postCreated.getId()));
		ControllerLinkBuilder linkUser = linkTo(methodOn(UserController.class).getUser(userName));
		
		Resource<Post> resource = new Resource<Post>(post);
		resource.add(linkSelf.withSelfRel());
		resource.add(linkComments.withRel("comments"));
		resource.add(linkUser.withRel("user"));
		
		
		return new ResponseEntity<Resource<Post>>(resource, HttpStatus.FOUND);
		
	}
	
	@RequestMapping(path = "/user/{userName}/posts", method = RequestMethod.GET)
	public ResponseEntity<List<Resource<Post>>> getPosts(@PathVariable String userName) throws Exception  {
		
		List<Post> posts = postService.getPosts(userName);
		
		List<Resource<Post>> listResource = new ArrayList<>();
 		
		for(Post post : posts) {
			

			ControllerLinkBuilder linkSelf = linkTo(methodOn(this.getClass()).getPost(userName, post.getId()));
			
			ControllerLinkBuilder linkComments = linkTo(methodOn(CommentController.class).getComments(userName, post.getId()));
			ControllerLinkBuilder linkUser = linkTo(methodOn(UserController.class).getUser(userName));
			
			Resource<Post> resource = new Resource<Post>(post);
			resource.add(linkSelf.withSelfRel());
			resource.add(linkComments.withRel("comments"));
			resource.add(linkUser.withRel("user"));
			
			listResource.add(resource);
			
		}
		
		return new ResponseEntity<List<Resource<Post>>>(listResource, HttpStatus.OK);
		
		
	}
	
	@RequestMapping(path = "/user/{userName}/posts/{postId}", method = RequestMethod.GET)
	public ResponseEntity<Resource<Post>> getPost(@PathVariable String userName, @PathVariable long postId) throws Exception {
		
		Post post = postService.getPost(userName, postId);

		ControllerLinkBuilder linkSelf = linkTo(methodOn(this.getClass()).getPost(userName, postId));
		
		ControllerLinkBuilder linkComments = linkTo(methodOn(CommentController.class).getComments(userName, postId));
		ControllerLinkBuilder linkUser = linkTo(methodOn(UserController.class).getUser(userName));
		
		Resource<Post> resource = new Resource<Post>(post);
		resource.add(linkSelf.withSelfRel());
		resource.add(linkComments.withRel("comments"));
		resource.add(linkUser.withRel("user"));
		
		
		return new ResponseEntity<Resource<Post>>(resource, HttpStatus.FOUND);
	}
	
	@RequestMapping(path = "/user/{userName}/posts/{postId}", method = RequestMethod.PUT)
	public ResponseEntity<Resource<Post>> updatePost(@PathVariable String userName, @PathVariable long postId,
			@Valid @RequestBody Post post) throws Exception {
		
		Post postUpdated = postService.updatePost(userName, postId, post);
		
		ControllerLinkBuilder linkSelf = linkTo(methodOn(this.getClass()).getPost(userName, postId));
		
		ControllerLinkBuilder linkComments = linkTo(methodOn(CommentController.class).getComments(userName, postId));
		ControllerLinkBuilder linkUser = linkTo(methodOn(UserController.class).getUser(userName));
		
		Resource<Post> resource = new Resource<Post>(postUpdated);
		resource.add(linkSelf.withSelfRel());
		resource.add(linkComments.withRel("comments"));
		resource.add(linkUser.withRel("user"));
		
		return new ResponseEntity<Resource<Post>>(resource, HttpStatus.OK);
		
	}
	
	@RequestMapping(path = "/user/{userName}/posts/{postId}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deletePost(@PathVariable String userName, @PathVariable long postId) throws Exception {
		
		postService.deletePost(userName, postId);
		return ResponseEntity.noContent().build();
		
	}

}

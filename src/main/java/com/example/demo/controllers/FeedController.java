package com.example.demo.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Post;
import com.example.demo.service.UserFeedService;
/**
* Controller class for User Feed
* @version 1.0
* @author AP
*/

@RestController
public class FeedController {
	/**
	 * UserFeedService dependency injection
	 */
	@Autowired
	private UserFeedService userFeedService;
	
	/**
	 * method to retrieve feed of user
	 * @param userName
	 * @return list of posts
	 * @throws Exception
	 */
	@RequestMapping(path = "/user/{userName}/feed", method = RequestMethod.GET)
	public ResponseEntity<List<Resource<Post>>> getFeed(@PathVariable("userName")String userName) throws Exception{
		
		List<Post> posts = userFeedService.getFeed(userName);
		
		
		List<Resource<Post>> listResource = new ArrayList<>();
 		
		for(Post post : posts) {
			

			ControllerLinkBuilder linkSelf = linkTo(methodOn(PostController.class).getPost(post.getUser().getUserName(), post.getId()));
			
			ControllerLinkBuilder linkComments = linkTo(methodOn(CommentController.class).getComments(post.getUser().getUserName(), post.getId()));
			ControllerLinkBuilder linkUserCreator = linkTo(methodOn(UserController.class).getUser(post.getUser().getUserName()));
			
			Resource<Post> resource = new Resource<Post>(post);
			resource.add(linkSelf.withSelfRel());
			resource.add(linkComments.withRel("comments"));
			resource.add(linkUserCreator.withRel("user-poster"));
			
			listResource.add(resource);
			
		}
		
		return new ResponseEntity<List<Resource<Post>>>(listResource, HttpStatus.OK);
		
	}

}

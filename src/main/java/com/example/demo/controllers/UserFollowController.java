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
import com.example.demo.service.impl.UserFollowService;

/**
* Controller class for operations of follow and unfollow
* @version 1.0
* @author AP
*/
@RestController
public class UserFollowController {
	
	@Autowired
	private UserFollowService userFollowService;
	
	/**
	 * method to create a follower between userName and userToFollow
	 * @param userName
	 * @param userToFollow
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = "/user/{userName}/follow", method = RequestMethod.POST)
	public ResponseEntity<Object> follow(@PathVariable String userName,
			@RequestBody String userToFollow) throws Exception{
		userFollowService.createFollower(userName, userToFollow);
		
		
		return ResponseEntity.ok().build();
		
		
	}
	/**
	 * method to delete the follower relationship between userName and userFollowed
	 * @param userName
	 * @param userToFollow
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = "/user/{userName}/unfollow", method = RequestMethod.POST)
	public ResponseEntity<Object> unfollow(@PathVariable String userName,
			@RequestBody String userFollowed) throws Exception{
		userFollowService.deleteFollower(userName, userFollowed);
		
		return ResponseEntity.noContent().build();
		
	}
	
	/**
	 * method to retrieve the list of followers of a user
	 * @param userName
	 * @return
	 */
	@RequestMapping(path = "/user/{userName}/followers", method = RequestMethod.GET)
	public ResponseEntity<List<Resource<User>>> getfollowers(@PathVariable String userName){
		
		List<User> followers = userFollowService.getFollowers(userName);	
		List<Resource<User>> listResource = new ArrayList<>();
 		
		for(User follower : followers) {
			

			ControllerLinkBuilder linkSelf = linkTo(methodOn(this.getClass()).getfollowers(userName));
			
			ControllerLinkBuilder linkFollower = linkTo(methodOn(UserController.class).getUser(follower.getUserName()));
			
			
			Resource<User> resource = new Resource<User>(follower);
			resource.add(linkSelf.withSelfRel());
			resource.add(linkFollower.withRel("user-follower"));
			
			listResource.add(resource);
			
		}
		
		return new ResponseEntity<List<Resource<User>>>(listResource, HttpStatus.OK);

	}
	
	/**
	 * method to retrieve the list of users whom user is following
	 * @param userName
	 * @return
	 */
	@RequestMapping(path = "/user/{userName}/followees", method = RequestMethod.GET)
	public ResponseEntity<List<Resource<User>>> getfollowees(@PathVariable String userName){
		List<User> followees = userFollowService.getFollowees(userName);
		
		List<Resource<User>> listResource = new ArrayList<>();
 		
		for(User followee : followees) {
			

			ControllerLinkBuilder linkSelf = linkTo(methodOn(this.getClass()).getfollowers(userName));
			
			ControllerLinkBuilder linkFollower = linkTo(methodOn(UserController.class).getUser(followee.getUserName()));
			
			
			Resource<User> resource = new Resource<User>(followee);
			resource.add(linkSelf.withSelfRel());
			resource.add(linkFollower.withRel("user-followee"));
			
			listResource.add(resource);
			
		}
		
		return new ResponseEntity<List<Resource<User>>>(listResource, HttpStatus.OK);
	}
}

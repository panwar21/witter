package com.example.demo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.impl.UserService;
/**
* Controller class for User Resource
* @version 1.0
* @author AP
*/
@RestController
public class UserController {
	/**
	 * UserService dependency injection
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * method for processing /users/{userName} get requests
	 * @param userName
	 * @return the hateoas User resource
	 */
	@RequestMapping(path = "/users/{userName}", method = RequestMethod.GET)
	public ResponseEntity<Resource<User>> getUser(@PathVariable String userName){

		
		User user = userService.getByUserName(userName);
		if(user!=null) {
			ControllerLinkBuilder linkSelf = linkTo(methodOn(this.getClass()).getUser(userName));
			
			ControllerLinkBuilder linkFollowers = linkTo(methodOn(UserFollowController.class).getfollowers(userName));
			ControllerLinkBuilder linkFollowees = linkTo(methodOn(UserFollowController.class).getfollowees(userName));
			
			Resource<User> resource = new Resource<User>(user);
			resource.add(linkSelf.withSelfRel());
			resource.add(linkFollowers.withRel("followes"));
			resource.add(linkFollowees.withRel("followees"));
			
			return new ResponseEntity<Resource<User>>(resource, HttpStatus.FOUND);
		}else {
			throw new UserNotFoundException("User Not Found");
		}
	}
	/**
	 * method to process post request. Request body must be User object in Json serialized form. 
	 * @param user
	 * @return the hateoas User resource created by the request, status code 201
	 * @throws Exception
	 */
	@RequestMapping(path = "/users", method = RequestMethod.POST)
	public ResponseEntity<Resource<User>> addUser(@Valid @RequestBody User user) throws Exception{
		
		User userCreated = userService.createUser(user);
		
		ControllerLinkBuilder linkSelf = linkTo(methodOn(this.getClass()).getUser(user.getUserName()));
		
		ControllerLinkBuilder linkFollowers = linkTo(methodOn(UserFollowController.class).getfollowers(user.getUserName()));
		ControllerLinkBuilder linkFollowees = linkTo(methodOn(UserFollowController.class).getfollowees(user.getUserName()));
		
		Resource<User> resource = new Resource<User>(userCreated);
		resource.add(linkSelf.withSelfRel());
		resource.add(linkFollowers.withRel("followes"));
		resource.add(linkFollowees.withRel("followees"));
		
		return new ResponseEntity<Resource<User>>(resource, HttpStatus.CREATED);
	}
	/**
	 * method to process put request. Request body must be User object in Json serialized form. 
	 * @param userName
	 * @param user
	 * @return the hateoas User resource updated by the request, status code 200
	 * @throws Exception
	 */
	@RequestMapping(path = "/users/{userName}", method = RequestMethod.PUT)
	public ResponseEntity<Resource<User>> updateUser(@PathVariable String userName, @Valid @RequestBody User user) throws Exception{
		
		User userUpdated = userService.updateUser(user);
		
		ControllerLinkBuilder linkSelf = linkTo(methodOn(this.getClass()).getUser(user.getUserName()));
		
		ControllerLinkBuilder linkFollowers = linkTo(methodOn(UserFollowController.class).getfollowers(user.getUserName()));
		ControllerLinkBuilder linkFollowees = linkTo(methodOn(UserFollowController.class).getfollowees(user.getUserName()));
		
		Resource<User> resource = new Resource<User>(userUpdated);
		resource.add(linkSelf.withSelfRel());
		resource.add(linkFollowers.withRel("followes"));
		resource.add(linkFollowees.withRel("followees"));
		
		
		return new ResponseEntity<Resource<User>>(resource, HttpStatus.OK);
	}
	
	/**
	 * method to delete a User resource
	 * @param userName
	 * @return status code -  no content
	 * @throws Exception
	 */
	@RequestMapping(path = "/users/{userName}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUser(@PathVariable String userName) throws Exception{
		userService.deleteUser(userName);
		return ResponseEntity.noContent().build();
	}
	
	
	

}

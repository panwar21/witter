package com.example.demo.service;

import java.util.List;


import com.example.demo.model.Post;

public interface UserFeedService {

	List<Post> getFeed(String userName);

}
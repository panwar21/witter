package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
	
	public List<Comment> findByPost(Post post);
}

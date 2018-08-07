package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Post;
import com.example.demo.model.User;
/**
* Repository Service class for Post entity
* @version 1.0
* @author AP
*/
@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	
	public List<Post> findByUser(User user);
	/**
	 * method to get List of posts of user followees ordered by date in descending order
	 * @param userId
	 * @param feedLength
	 * @return List of posts
	 */
	@Query(value="select * from Post"
			+ " inner join"
			+ " (select user_id as followee from user_follower where follower_id = :userId) as TempTable"
			+ " on Post.user_id = TempTable.followee"
			+ " order by Post.date_created desc"
			+ " limit :feedLength", nativeQuery=true)
	public List<Post> getUserFeed(@Param("userId")long userId, @Param("feedLength")int feedLength);
	
	/**
	 * method to get a post of user
	 * @param postId
	 * @param userId
	 * @return post
	 */
	@Query(value="select * from Post"
			+ " where Post.id = :postId and Post.user_id = :userId", nativeQuery=true)
	public Post getPostOfUser(@Param("postId")long postId, @Param("userId")long userId);
}

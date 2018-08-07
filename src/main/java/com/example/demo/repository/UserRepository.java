package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;
/**
* Repository Service class for User entity
* @version 1.0
* @author AP
*/
@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	public User findByUserName(String userName);
	
	
	public Boolean existsByUserName(String userName);
	
	
	public Boolean existsByEmailId(String emailId);
	/**
	 * method to get users whom the user is following
	 * @param userId
	 * @return List of followees
	 */
	@Query(value="select * from User"
			+ " inner join"
			+ " (select user_id as followed from user_follower where follower_id = :userId) as TempTable"
			+ " on User.id = TempTable.followed", nativeQuery=true)
	public List<User> getFollowees(@Param("userId")long userId);
}

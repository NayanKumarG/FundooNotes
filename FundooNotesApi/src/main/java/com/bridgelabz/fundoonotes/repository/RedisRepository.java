package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.User;

@Repository
public class RedisRepository {

    private HashOperations<String, Long , Object> hashOperations;

    private RedisTemplate<String , Object> redisTemplate;

	public RedisRepository(RedisTemplate<String , Object> redisTemplate) {
		   this.redisTemplate = redisTemplate;
	       this.hashOperations = this.redisTemplate.opsForHash();
	}
    
	public void save(User user) {
        hashOperations.put("USER", user.getId(), user);
}
	public User findById(long userId){
        return (User) hashOperations.get("USER", userId);
    }
}

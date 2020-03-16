/**
 * @author Nayan Kumar G
 * purpose : Redis configuration
 * date    :13-03-2020
 * 
 */
package com.bridgelabz.fundoonotes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

	@Bean
	JedisConnectionFactory jedisConnectionFactory()
	{
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
        //redisStandaloneConfiguration.setPassword(RedisPassword.of("foobared"));
        return new JedisConnectionFactory(redisStandaloneConfiguration);
		
	}
	
	   @Bean
	    public RedisTemplate<String, Object> redisTemplate() {
	        RedisTemplate<String , Object> template = new RedisTemplate<>();
	        template.setConnectionFactory(jedisConnectionFactory());
	        return template;
	    }
}

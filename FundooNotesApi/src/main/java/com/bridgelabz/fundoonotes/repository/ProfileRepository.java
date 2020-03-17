/**
 * @author Nayan Kumar G
 * purpose : Reposiory for user Profile
 * date    :16-03-2020
 */
package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.fundoonotes.entity.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

	@Query("from ProfileEntity where user_id=:userId")
	ProfileEntity findByUserId(long userId);
	
}

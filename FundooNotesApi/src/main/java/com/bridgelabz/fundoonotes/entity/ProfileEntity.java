/**
 * @author Nayan Kumar G
 * purpose :Entity for user profile
 * date    :16-03-2020
 */
package com.bridgelabz.fundoonotes.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_profile")
public class ProfileEntity {



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long profileId;
	
	private String profileName;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	public ProfileEntity(String profileName, User user) {
		super();
		this.profileName = profileName;
		this.user = user;
	}
		
}

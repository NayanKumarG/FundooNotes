/**
 * @author Nayan Kumar G
 * purpose : Entity for User
 * date    :25-02-2020
 */
package com.bridgelabz.fundoonotes.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Component
@Entity
public class User implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	
	@Column(name = "email" , nullable = false, unique = true)
	@Email(message = "Please provide a valid e-mail")
	private String email;
	
	@NotEmpty(message = "Password not be null")
	@Column(name = "password")
	private String password;
	
	@Column(name = "phone_number")
	private long phoneNumber;
	
	@Column(name = "register_date")
	private LocalDateTime dateTime;
	
	private LocalDateTime updateDateTime;
	
	@Column(name = "is_verified")
	private boolean isVerified;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<NoteEntity> notes;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "collaborator_note" , joinColumns = { @JoinColumn (name = "user_id")} , inverseJoinColumns = {@JoinColumn(name = "note_id")})
	@JsonManagedReference
	@JsonIgnore
	private List<NoteEntity> collaboratorNotes;
}

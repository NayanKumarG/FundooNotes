/**
 * @author Nayan Kumar G
 * purpose :Entity for Note 
 * date    :03-03-2020
 */
package com.bridgelabz.fundoonotes.entity;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Component
@Entity
@Table(name = "note")
public class NoteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long noteId;
	
	private String title;
	
	private String description;
	
	private boolean isPinned;
	
	private boolean isArchieved;
	
	private boolean isTrashed;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime updateDate;
	
	private LocalDateTime reminder;
	
	private String color;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "note_label" ,joinColumns = { @JoinColumn(name = "note_id")} , inverseJoinColumns = {@JoinColumn(name = "label_id")})
	@JsonBackReference
	@JsonIgnore
	private List<LabelEntity> labels;
}

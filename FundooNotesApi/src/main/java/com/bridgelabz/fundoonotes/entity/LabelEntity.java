/**
 * @author Nayan Kumar G
 * purpose :Entity for label
 * date    :03-03-2020
 */
package com.bridgelabz.fundoonotes.entity;

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
import lombok.Data;

@Data
@Component
@Entity
@Table(name = "label")
public class LabelEntity {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long labelId;

private String labelName;

private long userId;

@ManyToMany(cascade = CascadeType.ALL)
@JoinTable(name = "note_label" ,joinColumns = { @JoinColumn(name = "label_id")} , inverseJoinColumns = {@JoinColumn(name = "note_id")})
@JsonBackReference

private List<NoteEntity> notes;
}

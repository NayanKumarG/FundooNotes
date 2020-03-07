/**
 * @author Nayan Kumar G
 * purpose :Entity for label
 * date    :03-03-2020
 */
package com.bridgelabz.fundoonotes.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

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

}

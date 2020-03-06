/**
 * @author Nayan Kumar G
 * purpose :dto to add note reminder
 * date    :05-03-2020
 */
package com.bridgelabz.fundoonotes.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoteReminderDto {
	
	private LocalDateTime reminder;

}

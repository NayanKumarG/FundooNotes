/**
 * @author Nayan Kumar G
 * purpose :Dto for updatind notes
 * date    :04-04-2020
 */
package com.bridgelabz.fundoonotes.dto;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoteUpdateDto {

	private long id;
	
	private String title;
	
	private String description;
	


}

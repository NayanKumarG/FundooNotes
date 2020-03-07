/**
 * @author Nayan Kumar G
 * purpose :Dto to update label
 * date    :07-03-2020
 */
package com.bridgelabz.fundoonotes.dto;

import lombok.Data;

@Data
public class LabelUpdateDto {

	private String labelName;
	
	private long labelId;
}

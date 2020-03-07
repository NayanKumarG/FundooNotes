/**
 * @author Nayan Kumar G
 * purpose :Services for label
 * date    :06-03-2020
 */
package com.bridgelabz.fundoonotes.service;
import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdateDto;
public interface LabelService {

	void createLabel(LabelDto labelDto, String token);

	void updateLabel(LabelUpdateDto labelUpdateDto, String token);

	
}

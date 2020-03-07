/**
 * @author Nayan Kumar G
 * purpose :Model of the mail
 * date    :28-02-2020
 */
package com.bridgelabz.fundoonotes.response;
import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class EmailModel implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String email;
	private String subject;
	private String message;
	
}

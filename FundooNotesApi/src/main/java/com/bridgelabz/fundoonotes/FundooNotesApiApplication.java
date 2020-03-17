/**
 * @author Nayan Kumar G
 * purpose : Starts spring application
 * date    :25-02-2020
 * 
 */
package com.bridgelabz.fundoonotes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class FundooNotesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooNotesApiApplication.class, args);
		
	}
}

/**
 * @author Nayan Kumar G
 * purpose :Publisher for RabbitMQ
 * date    :14-03-2020
 */
package com.bridgelabz.fundoonotes.configuration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.response.EmailModel;

@Component
public class RabbitMQPublisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("rmq.rube.exchange")
	private String exchange;
	
	@Value("rube.key")
	private String routingKey;
	
	public void send(EmailModel message)
	{
		rabbitTemplate.convertAndSend(exchange , routingKey , message);
	}
	   
}

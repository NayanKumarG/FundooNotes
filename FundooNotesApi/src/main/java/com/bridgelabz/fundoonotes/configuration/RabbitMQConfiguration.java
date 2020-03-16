/**
 * @author Nayan Kumar G
 * purpose : Configuration for RabbitMQ server
 * date    :14-03-2020
 */
package com.bridgelabz.fundoonotes.configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

	
	@Value("rmq.rube.exchange")
	private String exchange;
	
	@Value("rube.key")
	private String routingKey;
	
	@Value("rmq.rube.queue")
	private String queue;
	
	@Autowired
	private ConnectionFactory rabbitConnectionFactory;
	
	@Bean
	DirectExchange rubeExchange() {
		  return new DirectExchange(exchange, true, false);
	 }
	
	 @Bean
	 public Queue rubeQueue() {
	  return new Queue(queue, true);
	 }

	 @Bean
	 Binding rubeExchangeBinding(DirectExchange rubeExchange, Queue rubeQueue) {
	  return BindingBuilder.bind(rubeQueue).to(rubeExchange).with(routingKey);
	 }

	 @Bean
	 public RabbitTemplate rubeExchangeTemplate() {
	  RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
	  rabbitTemplate.setExchange(exchange);
	  rabbitTemplate.setRoutingKey(routingKey);
	  rabbitTemplate.setConnectionFactory(rabbitConnectionFactory);
	  return rabbitTemplate;
	 }
	 
	}

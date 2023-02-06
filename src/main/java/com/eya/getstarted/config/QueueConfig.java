package com.eya.getstarted.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
	public static final String DIRECT_EXCHANGE_NAME = "amqp.direct";
	// Main queue
	public static final String MESSAGE_QUEUE = "MESSAGES";
	// In case of error, messages will be rerouted here before being routed back to main queue for retry
	public static final String MESSAGE_WAIT_QUEUE = MESSAGE_QUEUE + ".wait";
	// In case max retries are reached, messages will be sent here to die (can be revived manually)
	public static final String MESSAGE_PARKING_QUEUE = MESSAGE_QUEUE + ".parking";
	// Message routing key
	public static final String MESSAGE_ROUTING_KEY = MESSAGE_QUEUE + "-key";

	@Bean
	public DirectExchange exchange() {
		return new DirectExchange(DIRECT_EXCHANGE_NAME);
	}

	// Instantiate the message queues
	@Bean
	public Queue messagesQueue() {
		return QueueBuilder.durable(MESSAGE_QUEUE)
				.deadLetterExchange(DIRECT_EXCHANGE_NAME)
				.deadLetterRoutingKey(MESSAGE_WAIT_QUEUE) // In case of error, route to wait stream
				.build();
	}

	@Bean
	public Queue messagesWaitQueue() {
		return QueueBuilder.durable(MESSAGE_WAIT_QUEUE)
				.deadLetterExchange(DIRECT_EXCHANGE_NAME)
				.deadLetterRoutingKey(MESSAGE_ROUTING_KEY)
				.ttl(10000)
				.build();
	}

	@Bean
	public Queue messagesParkingQueue() {
		return new Queue(MESSAGE_PARKING_QUEUE);
	}

	// Message queue bindings
	@Bean
	public Binding messageQueueBinding(Queue messagesQueue, DirectExchange exchange) {
		return BindingBuilder.bind(messagesQueue).to(exchange).with(MESSAGE_ROUTING_KEY);
	}

	@Bean
	public Binding messageWaitQueueBinding(Queue messagesWaitQueue, DirectExchange exchange) {
		return BindingBuilder.bind(messagesWaitQueue).to(exchange).with(MESSAGE_WAIT_QUEUE);
	}

	@Bean
	public Binding messageParkingQueueBinding(Queue messagesParkingQueue, DirectExchange exchange) {
		return BindingBuilder.bind(messagesParkingQueue).to(exchange).with(MESSAGE_PARKING_QUEUE);
	}
}

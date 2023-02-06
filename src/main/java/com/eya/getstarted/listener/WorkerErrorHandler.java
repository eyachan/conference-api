package com.eya.getstarted.listener;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

@Component
public class WorkerErrorHandler implements RabbitListenerErrorHandler {
	@Override
	public Object handleError(Message originalMessage, org.springframework.messaging.Message<?> convertedMessage,
							  ListenerExecutionFailedException e) throws Exception {
		// Don't requeue as it will fail anyway
		if (e.getCause() instanceof IllegalArgumentException) return null;
		// Throw error again to retry
		throw new AmqpRejectAndDontRequeueException(e.getCause());
	}
}

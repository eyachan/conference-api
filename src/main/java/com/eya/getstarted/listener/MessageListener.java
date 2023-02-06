package com.eya.getstarted.listener;

import com.eya.getstarted.config.QueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MessageListener {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	private static final Logger log = LoggerFactory.getLogger(MessageListener.class);

	@RabbitListener(queues = QueueConfig.MESSAGE_QUEUE)
	public void receiveMessage(Message in) throws Exception {
		log.info("Messages queue received <" + in + ">");
		String message = new String(in.getBody());

		if (hasExceededRetryCount(in)) {
			log.info("Retries exceeded putting into parking lot");
			// Reset failed counter so that on retry, the message has a new retry counter
			in.getMessageProperties().setHeader("x-death", null);
			// Send to parking queue for manual retry later
			this.rabbitTemplate.send(QueueConfig.MESSAGE_PARKING_QUEUE, in);
			return;
		}
	}

	// Check if the message has been retried more than 3 times
	private boolean hasExceededRetryCount(Message in) {
		List<Map<String, ?>> xDeathHeader = in.getMessageProperties().getXDeathHeader();
		if (xDeathHeader != null && xDeathHeader.size() >= 1) {
			Long count = (Long) xDeathHeader.get(0).get("count");
			System.out.println("Retry number " + count);
			return count >= 3;
		}

		return false;
	}
}

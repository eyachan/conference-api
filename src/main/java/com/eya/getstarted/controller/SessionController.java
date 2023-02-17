package com.eya.getstarted.controller;

import com.eya.getstarted.config.QueueConfig;
import com.eya.getstarted.model.Session;
import com.eya.getstarted.repository.SessionRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {
	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@GetMapping("/rabbit/{name}")
	public String handleRequest(@PathVariable String name) {
		rabbitTemplate.convertAndSend(QueueConfig.MESSAGE_QUEUE, name);
		return "Hello " + name;
	}

	@GetMapping("/security")
	public String getUser(Principal user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "Hello " + authentication.getName();
	}

	@GetMapping
	public List<Session> getAll() {
		return sessionRepository.findAll();
	}

	@GetMapping
	@RequestMapping(value = "{id}")
	public Session getById(@PathVariable Long id) {
		return sessionRepository.findById(id).get();
	}

	@PostMapping
	public Session createSession(@RequestBody Session session) {
		return sessionRepository.saveAndFlush(session);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteSession(@PathVariable Long id) {
		sessionRepository.deleteById(id);
	}


	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Session update(@PathVariable Long id, @RequestBody Session session) {
		Session existSession = sessionRepository.findById(id).get();
		BeanUtils.copyProperties(session, existSession, "session_id");
		return sessionRepository.saveAndFlush(existSession);
	}
}

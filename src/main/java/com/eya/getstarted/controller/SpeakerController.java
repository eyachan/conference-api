package com.eya.getstarted.controller;

import com.eya.getstarted.model.Session;
import com.eya.getstarted.model.Speaker;
import com.eya.getstarted.repository.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speaker")
public class SpeakerController {
	@Autowired
	private SpeakerRepository speakerRepository;

	@GetMapping
	public List<Speaker> getAll() {
		return speakerRepository.findAll();
	}

	@GetMapping
	@RequestMapping(value = "{id}")
	public Speaker getById(@PathVariable Long id) {
		return speakerRepository.getOne(id);
	}

	@PostMapping
	public Speaker createSession(@RequestBody Speaker speaker) {
		return speakerRepository.saveAndFlush(speaker);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteSession(@PathVariable Long id) {
		speakerRepository.deleteById(id);
	}


	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
		Speaker existingSpeaker = speakerRepository.getOne(id);
		BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
		return speakerRepository.saveAndFlush(existingSpeaker);
	}
}

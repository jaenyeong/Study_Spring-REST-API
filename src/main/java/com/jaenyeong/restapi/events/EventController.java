package com.jaenyeong.restapi.events;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {
	private final EventRepository eventRepository;

	public EventController(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@PostMapping
	public ResponseEntity<?> createEvent(@RequestBody Event event) {
		Event newEvent = this.eventRepository.save(event);

//		URI createUri = linkTo(
//				methodOn(EventController.class).createEvent(null))
//				.slash("{id}")
//				.toUri();

//		URI createUri = linkTo(EventController.class).slash("{id}").toUri();
		URI createUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
		event.setId(10);

//		return ResponseEntity.created(createUri).build();
		return ResponseEntity.created(createUri).body(event);
	}
}

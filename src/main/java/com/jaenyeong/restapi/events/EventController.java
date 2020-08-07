package com.jaenyeong.restapi.events;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {
	private final EventRepository eventRepository;
	private final ModelMapper modelMapper;

	public EventController(EventRepository eventRepository, ModelMapper modelMapper) {
		this.eventRepository = eventRepository;
		this.modelMapper = modelMapper;
	}

	@PostMapping
//	public ResponseEntity<?> createEvent(@RequestBody Event event) {
//	public ResponseEntity<?> createEvent(@RequestBody EventDto eventDto) {
	public ResponseEntity<?> createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		Event event = modelMapper.map(eventDto, Event.class);

		// 테스트시에 파라미터로 넘겨준 Event 객체가 리포지터리에 저장되어야 목객체를 생성
		// 하지만 파라미터를 EventDto 객체로 받고 새 Event객체를 생성, 저장시 목객체를 생성하지 않음
		// 따라서 테스트시에는 savedEvent가 Null
		Event savedEvent = this.eventRepository.save(event);

//		URI createUri = linkTo(
//				methodOn(EventController.class).createEvent(null))
//				.slash("{id}")
//				.toUri();

//		URI createUri = linkTo(EventController.class).slash("{id}").toUri();

		// 테스트시에는 savedEvent가 Null이기 떄문에 savedEvent.getId()는 NullPointerException
		// @SpringBootTest 사용 및 @MockBean 제거
		URI createUri = linkTo(EventController.class).slash(savedEvent.getId()).toUri();
//		event.setId(10);

//		return ResponseEntity.created(createUri).build();
		return ResponseEntity.created(createUri).body(event);
	}
}

package com.jaenyeong.restapi.events;

import com.jaenyeong.restapi.common.ErrorResource;
import com.jaenyeong.restapi.index.IndexController;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {
	private final EventRepository eventRepository;
	private final ModelMapper modelMapper;
	private final EventValidator eventValidator;

	public EventController(EventRepository eventRepository, ModelMapper modelMapper, EventValidator eventValidator) {
		this.eventRepository = eventRepository;
		this.modelMapper = modelMapper;
		this.eventValidator = eventValidator;
	}

	@PostMapping
//	public ResponseEntity<?> createEvent(@RequestBody Event event) {
//	public ResponseEntity<?> createEvent(@RequestBody EventDto eventDto) {
	public ResponseEntity<?> createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
		if (errors.hasErrors()) {
//			return ResponseEntity.badRequest().build();
			// errors는 JSON으로 변환할 수가 없음
//			return ResponseEntity.badRequest().body(errors);

			// EntityModel로 반환
			return getErrorEntityModelResponseEntity(errors);
			// ErrorResource로 반환
//			return getErrorResourceResponseEntity(errors);
		}

		eventValidator.validate(eventDto, errors);
		if (errors.hasErrors()) {
//			return ResponseEntity.badRequest().build();
//			return ResponseEntity.badRequest().body(errors);

			// EntityModel로 반환
			return getErrorEntityModelResponseEntity(errors);
			// ErrorResource로 반환
//			return getErrorResourceResponseEntity(errors);
		}

		Event event = modelMapper.map(eventDto, Event.class);

		event.update();

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
		WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(savedEvent.getId());
		URI createUri = selfLinkBuilder.toUri();
//		event.setId(10);

//		return ResponseEntity.created(createUri).build();
//		return ResponseEntity.created(createUri).body(event);

		// [HATEOAS 추가]

		// [1] Event 클래스에서 RepresentationModel<Event> 클래스를 상속하여 그대로 사용
//		savedEvent.add(selfLinkBuilder.withSelfRel());
//		savedEvent.add(linkTo(EventController.class).withRel("query-events"));
//		savedEvent.add(selfLinkBuilder.withRel("update-event"));
//		// 프로필 추가
//		savedEvent.add(selfLinkBuilder.withRel("/docs/index.html#resources-events-create").withRel("profile"));
//		return ResponseEntity.created(createUri).body(savedEvent);

		// [2] EntityModel 클래스를 상속한 EventResource 구현하여 사용
		// EventResource 생성자에 링크 설정 포함하여 사용 가능
//		EventResource<Event> eventResource = new EventResource<>(savedEvent);
//		return ResponseEntity.created(createUri).body(eventResource);

		// [3] 또는 EntityModel 클래스 직접 사용
//		EntityModel<Event> eventResource = EntityModel.of(savedEvent);
//		eventResource.add(selfLinkBuilder.withSelfRel());
//		eventResource.add(linkTo(EventController.class).withRel("query-events"));
//		eventResource.add(selfLinkBuilder.withRel("update-event"));
//		// 프로필 추가
//		eventResource.add(selfLinkBuilder.withRel("/docs/index.html#resources-events-create").withRel("profile"));
//		return ResponseEntity.created(createUri).body(eventResource);

		// [3-1] EntityModel 클래스 직접 사용시 Link 리스트 객체 생성 후 인자로 넘김
		List<Link> links = Arrays.asList(
				selfLinkBuilder.withSelfRel(),
				selfLinkBuilder.withRel("query-events"),
				selfLinkBuilder.withRel("update-event"),
				// 프로필 추가
				selfLinkBuilder.withRel("/docs/index.html#resources-events-create").withRel("profile")
		);
		EntityModel<Event> eventResource = EntityModel.of(savedEvent, links);
		return ResponseEntity.created(createUri).body(eventResource);
	}

	private ResponseEntity<EntityModel<Errors>> getErrorEntityModelResponseEntity(Errors errors) {
		List<Link> links = Collections.singletonList(
				linkTo(methodOn(IndexController.class).index()).withRel("index"));
		EntityModel<Errors> errorsEntityModel = EntityModel.of(errors, links);
		return ResponseEntity.badRequest().body(errorsEntityModel);
	}

	private ResponseEntity<ErrorResource> getErrorResourceResponseEntity(Errors errors) {
		ErrorResource errorResource = new ErrorResource(errors);
		errorResource.add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
		return ResponseEntity.badRequest().body(errorResource);
	}
}

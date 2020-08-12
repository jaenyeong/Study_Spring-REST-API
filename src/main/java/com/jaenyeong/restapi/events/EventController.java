package com.jaenyeong.restapi.events;

import com.jaenyeong.restapi.accounts.Account;
import com.jaenyeong.restapi.accounts.CurrentUser;
import com.jaenyeong.restapi.common.ErrorResource;
import com.jaenyeong.restapi.index.IndexController;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

	@PutMapping("/{id}")
	public ResponseEntity<?> updateEvent(@PathVariable Integer id,
	                                     @Valid @RequestBody EventDto eventDto,
	                                     Errors errors,
	                                     @CurrentUser Account currentUser) {
		if (currentUser == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		Optional<Event> optionalEvent = this.eventRepository.findById(id);

		if (optionalEvent.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		if (errors.hasErrors()) {
			return getErrorEntityModelResponseEntity(errors);
//			return getErrorResourceResponseEntity(errors);
		}

		this.eventValidator.validate(eventDto, errors);

		if (errors.hasErrors()) {
			return getErrorEntityModelResponseEntity(errors);
//			return getErrorResourceResponseEntity(errors);
		}

		// 트랜잭션이 아니기 때문에 변경사항이 더티체킹되어 저장되지 않기 때문에
		// 명시적으로 저장소에 저장
		Event existingEvent = optionalEvent.get();

		// 로그인 유저와 해당 이벤트의 관리자 정보가 일치하지 않는 경우
		if (!(existingEvent.getManager().equals(currentUser))) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		this.modelMapper.map(eventDto, existingEvent);
		Event savedEvent = this.eventRepository.save(existingEvent);

		List<Link> links = Arrays.asList(
				linkTo(EventController.class).slash(existingEvent.getId()).withSelfRel(),
				Link.of("/docs/index.html#resources-events-update").withRel("profile")
		);
		EntityModel<Event> eventEntityModel = EntityModel.of(savedEvent, links);

		return ResponseEntity.ok(eventEntityModel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEvent(@PathVariable Integer id, @CurrentUser Account currentUser) {
		Optional<Event> optionalEvent = this.eventRepository.findById(id);

		if (optionalEvent.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Event event = optionalEvent.get();
		List<Link> links = Arrays.asList(
				linkTo(EventController.class).slash(event.getId()).withSelfRel(),
				Link.of("/docs/index.html#resources-events-get").withRel("profile")
		);

		EntityModel<Event> eventEntityModel = EntityModel.of(event, links);

		// 관리자인 경우 조회 이벤트 수정 링크 추가
		if (event.getManager().equals(currentUser)) {
			eventEntityModel.add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));
		}

		return ResponseEntity.ok(eventEntityModel);
	}

	@GetMapping
	public ResponseEntity<?> queryEvents(Pageable pageable,
	                                     PagedResourcesAssembler<Event> assembler,
//	                                     @AuthenticationPrincipal User user
//	                                     @AuthenticationPrincipal AccountAdapter currentUser
//	                                     @AuthenticationPrincipal(expression = "account") Account account
	                                     // 애노테이션 커스터마이징
	                                     @CurrentUser Account account
	) {

//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User principal = (User) authentication.getPrincipal();

		Page<Event> page = this.eventRepository.findAll(pageable);

		var pageResources = assembler.toModel(page, e -> {
			Link link = linkTo(EventController.class).slash(e.getId()).withSelfRel();
			return EntityModel.of(e, link);
		});
		pageResources.add(Link.of("/docs/index.html#resources-events-list").withRel("profile"));

		// 로그인 여부 판단하여 이벤트 생성 링크 추가
		if (account != null) {
			pageResources.add(linkTo(EventController.class).withRel("create-event"));
		}
		return ResponseEntity.ok(pageResources);
	}

	@PostMapping
//	public ResponseEntity<?> createEvent(@RequestBody Event event) {
//	public ResponseEntity<?> createEvent(@RequestBody EventDto eventDto) {
	public ResponseEntity<?> createEvent(@RequestBody @Valid EventDto eventDto,
	                                     Errors errors,
	                                     @CurrentUser Account currentUser) {
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

		event.setManager(currentUser);

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

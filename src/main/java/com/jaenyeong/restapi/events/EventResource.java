package com.jaenyeong.restapi.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

// objectMapper가 EventResource를 Json으로 Serialization (마샬링)
// 빈으로 등록하지 않고 매번 생성하여 사용하는 객체
public class EventResource<E extends Event> extends EntityModel<Event> {
	// Event {} 형태로 감싸고 싶지 않은 경우
	// 1) Event 객체내 필드들을 직접 선언
	// 2) @JsonUnwrapped 애노테이션 사용하여 래핑 제거
	@JsonUnwrapped
	private final Event event;

	public EventResource(Event event) {
		this.event = event;
		// Type safe하지 않음
//		add(Link.of("http://localhost:808/api/events" + event.getId()));
		add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
		add(linkTo(EventController.class).slash(event.getId()).withRel("query-events"));
		add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));
		add(linkTo(EventController.class).slash(event.getId()).withRel("/docs/index.html#resources-events-create").withRel("profile"));
	}
}

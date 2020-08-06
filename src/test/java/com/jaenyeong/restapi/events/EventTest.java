package com.jaenyeong.restapi.events;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

	@Test
	void builder() {
		Event event = Event.builder()
				.name("Spring REST API")
				.description("REST API development with Spring")
				.build();
		assertThat(event).isNotNull();
	}

	@Test
	void javaBean() {
		// given
		String name = "Event";
		String description = "Spring";

		// when
		Event event = createEvent(name, description);

		// then
		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getDescription()).isEqualTo(description);
	}

	private Event createEvent(String name, String description) {
		Event event = new Event();
		event.setName(name);
		event.setDescription(description);
		return event;
	}
}

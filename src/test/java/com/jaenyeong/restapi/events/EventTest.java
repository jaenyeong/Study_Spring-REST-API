package com.jaenyeong.restapi.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

	//	@Test
	@ParameterizedTest
	// null 값은 아무것도 넣지 않음
//	@CsvSource({"서울특별시 강서구 화곡동, true", "'', false", ", false"})
//	@MethodSource("parametersForTestOffline")
	@MethodSource("parametersForTestOffline")
	void testOffline(String location, boolean isOffline) throws Exception {
//		// given
//		Event event1 = Event.builder().location("서울특별시 강서구 화곡동").build();
//		// when
//		event1.update();
//		// then
//		assertThat(event1.isOffline()).isTrue();
//
//		// given
//		Event event2 = Event.builder().build();
//		// when
//		event2.update();
//		// then
//		assertThat(event2.isOffline()).isFalse();

		// given
		Event event = Event.builder().location(location).build();
		// when
		event.update();
		// then
		assertThat(event.isOffline()).isEqualTo(isOffline);
	}

	private static Stream<Arguments> parametersForTestOffline() {
		return Stream.of(
				Arguments.of("서울특별시 강서구 화곡동", true),
				Arguments.of("", false),
				Arguments.of(null, false)
		);
	}

	//	@Test
	//	@Parameters({"0, 0, true", "100, 0, false", "0, 100, false"})
//	void testFree() throws Exception {
	@ParameterizedTest
//	@CsvSource({"0, 0, true", "0, 100, false", "100, 0, false"})
	@MethodSource("parametersForTestFree")
	void testFree(int basePrice, int maxPrice, boolean isFree) throws Exception {
//		// given
//		Event event1 = Event.builder().basePrice(0).maxPrice(0).build();
//		// when
//		event1.update();
//		// then
//		assertThat(event1.isFree()).isTrue();
//
//		// given
//		Event event2 = Event.builder().basePrice(100).maxPrice(0).build();
//		// when
//		event2.update();
//		// then
//		assertThat(event2.isFree()).isFalse();
//
//		// given
//		Event event3 = Event.builder().basePrice(0).maxPrice(100).build();
//		// when
//		event3.update();
//		// then
//		assertThat(event3.isFree()).isFalse();

		// given
		Event event = Event.builder().basePrice(basePrice).maxPrice(maxPrice).build();
		// when
		event.update();
		// then
		assertThat(event.isFree()).isEqualTo(isFree);
	}

	private static Object[] parametersForTestFree() {
		return new Object[]{
				new Object[]{0, 0, true},
				new Object[]{100, 0, false},
				new Object[]{0, 100, false},
				new Object[]{100, 100, false}
		};
	}

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

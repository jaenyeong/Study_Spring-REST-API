package com.jaenyeong.restapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class EventControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	EventRepository eventRepository;

	@Test
	void createEvent() throws Exception {
		Event event = eventBuilder();

		// eventRepository가 목 객체이기 때문에 리포지터리에 event 객체 저장시 event 반환
		Mockito.when(eventRepository.save(event)).thenReturn(event);

		mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON).content(objectMapper.writeValueAsString(event))
		)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("id").exists())
//				.andExpect(header().exists("Location"))
				.andExpect(header().exists(HttpHeaders.LOCATION))
//				.andExpect(header().string("Content-Type", "application/hal+json"))
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
		;
	}

	private Event eventBuilder() {
		return Event.builder()
				.id(10)
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2020, 8, 7, 12, 30, 20))
				.closeEnrollmentDateTime(LocalDateTime.of(2020, 8, 8, 12, 30, 20))
				.beginEventDateTime(LocalDateTime.of(2020, 8, 9, 12, 30, 20))
				.endEventDateTime(LocalDateTime.of(2020, 8, 10, 12, 30, 20))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(10)
				.location("강서구 화곡동")
				.build();
	}
}

package com.jaenyeong.restapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EventControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void createEvent() throws Exception {
		Event event = eventBuilder();

		mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON).content(objectMapper.writeValueAsString(event))
		)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("id").exists());
	}

	private Event eventBuilder() {
		return Event.builder()
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

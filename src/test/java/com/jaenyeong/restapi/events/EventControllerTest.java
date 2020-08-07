package com.jaenyeong.restapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaenyeong.restapi.common.TestDescription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	// SpringBootTest 사용시 주석처리
//	@MockBean
//	EventRepository eventRepository;

	@Test
	@TestDescription("입력 값이 잘못된 경우 에러가 발생하는 테스트")
	void createEventBadRequestWrongInput() throws Exception {
		EventDto eventDto = wrongEventDtoBuilder();

		mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(eventDto))
		)
				.andDo(print())
				.andExpect(status().isBadRequest())
				// 필드 에러를 먼저 삽입했기 때문에 통과
				.andExpect(jsonPath("$[0].objectName").exists())
				.andExpect(jsonPath("$[0].defaultMessage").exists())
				.andExpect(jsonPath("$[0].code").exists())
				// 필드 에러가 없는 경우를 위해 주석처리
//				.andExpect(jsonPath("$[0].field").exists())
//				.andExpect(jsonPath("$[0].rejectedValue").exists())
		;
	}

	private EventDto wrongEventDtoBuilder() {
		return EventDto.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				// 종료일이 시작일보다 작을 때
				.beginEnrollmentDateTime(LocalDateTime.of(2020, 8, 8, 12, 30, 20))
				.closeEnrollmentDateTime(LocalDateTime.of(2020, 8, 7, 12, 30, 20))
				.beginEventDateTime(LocalDateTime.of(2020, 8, 10, 12, 30, 20))
				.endEventDateTime(LocalDateTime.of(2020, 8, 9, 12, 30, 20))
				// 최대 가격보다 비쌀 때
				.basePrice(10000)
				.maxPrice(200)
				.limitOfEnrollment(10)
				.location("강서구 화곡동")
				.build();
	}

	@Test
	@TestDescription("입력 값이 비어있는 경우 에러가 발생하는 테스트")
	void createEventBadRequestEmptyInput() throws Exception {
		EventDto eventDto = EventDto.builder().build();

		mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(eventDto))
		)
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@TestDescription("입력 받을 수 없는 값을 사용한 경우 에러가 발생하는 테스트")
	void createEventBadRequest() throws Exception {
		Event event = eventBuilder();

		mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON).content(objectMapper.writeValueAsString(event)))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	private Event eventBuilder() {
		return Event.builder()
				.id(100)
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
				// 계산 되어야 하는 값
				.free(true)
				.offline(false)
				.eventStatus(EventStatus.PUBLISHED)
				.build();
	}

	@Test
	@TestDescription("정상적으로 이벤트를 생성하는 테스트")
	void createEvent() throws Exception {
		EventDto eventdto = eventDtoBuilder();

		// eventRepository가 목 객체이기 때문에 리포지터리에 event 객체 저장시 event가 저장되지 않음
		// 따라서 savedEvent가 반환되지 않기 때문에 임의로 저장시에 event객체를 저장, 반환해줌

		// [에러] 컨트롤러에서 Event가 아닌 EventDto로 받아 Event로 변환하여 처리하는 것으로 수정시 에러 발생
		// 아래 명령은 넘겨준 Event 객체가 리포지터리에 저장될 때 목객체(Event)를 생성하는데
		// 리포지터리에 저장하는 Event 객체와 파라미터로 넘겨주는 Event 객체가 다르기 때문에 동작하지 않음

		// (수정) 주석 처리 후 @SpringBootTest로 테스트로 변경
//		Mockito.when(eventRepository.save(event)).thenReturn(event);

		mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON).content(objectMapper.writeValueAsString(eventdto))
		)
				.andDo(print())

				// Bad Request 처리
//				.andExpect(status().isBadRequest())

				.andExpect(status().isCreated())
//				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("id").exists())
//				.andExpect(header().exists("Location"))
				.andExpect(header().exists(HttpHeaders.LOCATION))
//				.andExpect(header().string("Content-Type", "application/hal+json"))
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
				// id, 계산이 필요한 값은 넘겨주는 값과 달라야 함
//				.andExpect(jsonPath("id").value(Matchers.not(100)))
//				.andExpect(jsonPath("free").value(Matchers.not(true)))
				.andExpect(jsonPath("free").value(false))
				.andExpect(jsonPath("offline").value(true))
				.andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
		;
	}

	private EventDto eventDtoBuilder() {
		return EventDto.builder()
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

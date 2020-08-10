package com.jaenyeong.restapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaenyeong.restapi.common.RestDocsConfiguration;
import com.jaenyeong.restapi.common.TestDescription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
// 커스터마이징한 RestDocsMockMvcConfigurationCustomizer 클래스 사용 설정을 위해
// RestDocsConfiguration 클래스 임포트
@Import(RestDocsConfiguration.class)
// 테스트 설정 파일 사용 (H2 InMemory DB)
@ActiveProfiles("test")
class EventControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	// SpringBootTest 사용시 주석처리
//	@MockBean
	@Autowired
	EventRepository eventRepository;

	@Test
	@DisplayName("데이터가 없는 특정 이벤트 조회")
	void getEventNotFound() throws Exception {
		// given
		Event event = this.generateEvent(100);

		// when
		this.mockMvc.perform(get("/api/events/13072819", event.getId()))
				// then
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("기존의 특정 이벤트 한 개 조회")
	void getEvent() throws Exception {
		// given
		Event event = this.generateEvent(100);

		// when
		this.mockMvc.perform(
				get("/api/events/{id}", event.getId())
		)
				// then
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").exists())
				.andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("_links.self").exists())
				.andExpect(jsonPath("_links.profile").exists())
				.andDo(document("get-an-event",
						links(
								linkWithRel("self").description("link to clicked event page")
								, linkWithRel("profile").description("link to profile")
						),
						requestHeaders(
								// empty
						),
						requestBody(
								// empty
						),
						responseHeaders(
								headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
						),
						responseFields(
								fieldWithPath("id").description("identifier of new event")
								, fieldWithPath("name").description("name of new event")
								, fieldWithPath("description").description("description of new event")
								, fieldWithPath("beginEnrollmentDateTime").description("beginEnrollmentDateTime of begin of new event")
								, fieldWithPath("closeEnrollmentDateTime").description("closeEnrollmentDateTime of close of new event")
								, fieldWithPath("beginEventDateTime").description("beginEventDateTime of begin of new event")
								, fieldWithPath("endEventDateTime").description("endEventDateTime of end of new event")
								, fieldWithPath("location").description("location of new event")
								, fieldWithPath("basePrice").description("basePrice of new event")
								, fieldWithPath("maxPrice").description("maxPrice of new event")
								, fieldWithPath("limitOfEnrollment").description("limit of enrollment")
								, fieldWithPath("free").description("it tells if this event is free or not")
								, fieldWithPath("offline").description("it tells if this event is offline meeting or not")
								, fieldWithPath("eventStatus").description("event status")
								, fieldWithPath("_links.self.href").description("link to clicked event page")
								, fieldWithPath("_links.profile.href").description("link to profile")
						)
				))
		;
	}

	@Test
	@DisplayName("30개의 이벤트를 10개씩, 두번째 페이지 조회")
	void queryEvents() throws Exception {
		// given
		IntStream.range(0, 30).forEach(this::generateEvent);

		// when
		this.mockMvc.perform(
				get("/api/events")
						.param("page", "1")
						.param("size", "10")
						.param("sort", "name,DESC")
		)
				// then
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("page").exists())
				.andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
				.andExpect(jsonPath("_links.self").exists())
				.andExpect(jsonPath("_links.profile").exists())
				.andDo(document("query-events",
						links(
								linkWithRel("first").description("link to first page")
								, linkWithRel("prev").description("link to prev page")
								, linkWithRel("self").description("link to clicked event page")
								, linkWithRel("next").description("link to next page")
								, linkWithRel("last").description("link to last page")
								, linkWithRel("profile").description("link to profile")
						),
						requestHeaders(
								// empty
						),
						requestBody(
								// empty
						),
						responseHeaders(
								headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
						),
						responseFields(
								fieldWithPath("_embedded.eventList[0].id").description("identifier of new event")
								, fieldWithPath("_embedded.eventList[0].eventStatus").description("event status")
								, fieldWithPath("_embedded.eventList[0].offline").description("it tells if this event is offline meeting or not")
								, fieldWithPath("_embedded.eventList[0].free").description("it tells if this event is free or not")
								, fieldWithPath("_embedded.eventList[0].name").description("name of new event")
								, fieldWithPath("_embedded.eventList[0].description").description("description of new event")
								, fieldWithPath("_embedded.eventList[0].beginEnrollmentDateTime").description("beginEnrollmentDateTime of begin of new event")
								, fieldWithPath("_embedded.eventList[0].closeEnrollmentDateTime").description("closeEnrollmentDateTime of close of new event")
								, fieldWithPath("_embedded.eventList[0].beginEventDateTime").description("beginEventDateTime of begin of new event")
								, fieldWithPath("_embedded.eventList[0].endEventDateTime").description("endEventDateTime of end of new event")
								, fieldWithPath("_embedded.eventList[0].location").description("location of new event")
								, fieldWithPath("_embedded.eventList[0].basePrice").description("basePrice of new event")
								, fieldWithPath("_embedded.eventList[0].maxPrice").description("maxPrice of new event")
								, fieldWithPath("_embedded.eventList[0].limitOfEnrollment").description("limit of enrollment")
								, fieldWithPath("_embedded.eventList[0]._links.self.href").description("link to profile")
								, fieldWithPath("_links.first.href").description("link to first page")
								, fieldWithPath("_links.prev.href").description("link to prev page")
								, fieldWithPath("_links.self.href").description("link to clicked event page")
								, fieldWithPath("_links.next.href").description("link to next page")
								, fieldWithPath("_links.last.href").description("link to last page")
								, fieldWithPath("_links.profile.href").description("link to profile")
								, fieldWithPath("page.size").description("the size of events per page")
								, fieldWithPath("page.totalElements").description("the number of total events")
								, fieldWithPath("page.totalPages").description("the number of all page")
								, fieldWithPath("page.number").description("current page")
						)
						)
				)
		;
	}

	private Event generateEvent(int i) {
		Event event = Event.builder()
				.name("event " + i)
				.description("test event")
				.build();
		return this.eventRepository.save(event);
	}

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
//				.andExpect(jsonPath("$[0].objectName").exists())
//				.andExpect(jsonPath("$[0].defaultMessage").exists())
//				.andExpect(jsonPath("$[0].code").exists())
				// ErrorResource를 반환하면서 발생하는 에러 수정
				// > JsonArray는 unwrap이 되지 않아 데이터가 content 안에 래핑됨
				.andExpect(jsonPath("content[0].objectName").exists())
				.andExpect(jsonPath("content[0].defaultMessage").exists())
				.andExpect(jsonPath("content[0].code").exists())

				// 필드 에러가 없는 경우를 위해 주석처리
//				.andExpect(jsonPath("$[0].field").exists())
//				.andExpect(jsonPath("$[0].rejectedValue").exists())
				.andExpect(jsonPath("_links.index").exists())
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
				// 링크 정보 추가
				// links를 아래 document에서 확인 테스트를 진행하기 때문에 주석처리
//				.andExpect(jsonPath("_links.self").exists())
//				.andExpect(jsonPath("_links.query-events").exists())
//				.andExpect(jsonPath("_links.update-event").exists())
//				.andExpect(jsonPath("_links.profile").exists())
				// Spring REST Docs
//				.andDo(document("create-event"))
				.andDo(document("create-event",
						links(
								linkWithRel("self").description("link to self")
								, linkWithRel("query-events").description("link to query events")
								, linkWithRel("update-event").description("link to update an existing event")
								, linkWithRel("profile").description("link to profile")
						),
						requestHeaders(
								headerWithName(HttpHeaders.ACCEPT).description("accept header")
								, headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
						),
						requestFields(
								// {"name":"Spring","description":"REST API Development with Spring","beginEnrollmentDateTime":"2020-08-07T12:30:20","closeEnrollmentDateTime":"2020-08-08T12:30:20","beginEventDateTime":"2020-08-09T12:30:20","endEventDateTime":"2020-08-10T12:30:20","location":"강서구 화곡동","basePrice":100,"maxPrice":200,"limitOfEnrollment":10}
								fieldWithPath("name").description("name of new event")
								, fieldWithPath("description").description("description of new event")
								, fieldWithPath("beginEnrollmentDateTime").description("beginEnrollmentDateTime of begin of new event")
								, fieldWithPath("closeEnrollmentDateTime").description("closeEnrollmentDateTime of close of new event")
								, fieldWithPath("beginEventDateTime").description("beginEventDateTime of begin of new event")
								, fieldWithPath("endEventDateTime").description("endEventDateTime of end of new event")
								, fieldWithPath("location").description("location of new event")
								, fieldWithPath("basePrice").description("basePrice of new event")
								, fieldWithPath("maxPrice").description("maxPrice of new event")
								, fieldWithPath("limitOfEnrollment").description("limit of enrollment")
						),
						responseHeaders(
								headerWithName(HttpHeaders.LOCATION).description("location header")
								, headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
						),
						// responseFields 메서드 사용하는 경우
						// 모든 필드를 문서화하지 않으면 에러 발생 (예제에서 _links)
						responseFields(
								fieldWithPath("id").description("identifier of new event")
								, fieldWithPath("name").description("name of new event")
								, fieldWithPath("description").description("description of new event")
								, fieldWithPath("beginEnrollmentDateTime").description("beginEnrollmentDateTime of begin of new event")
								, fieldWithPath("closeEnrollmentDateTime").description("closeEnrollmentDateTime of close of new event")
								, fieldWithPath("beginEventDateTime").description("beginEventDateTime of begin of new event")
								, fieldWithPath("endEventDateTime").description("endEventDateTime of end of new event")
								, fieldWithPath("location").description("location of new event")
								, fieldWithPath("basePrice").description("basePrice of new event")
								, fieldWithPath("maxPrice").description("maxPrice of new event")
								, fieldWithPath("limitOfEnrollment").description("limit of enrollment")
								, fieldWithPath("free").description("it tells if this event is free or not")
								, fieldWithPath("offline").description("it tells if this event is offline meeting or not")
								, fieldWithPath("eventStatus").description("event status")
								// 모든 필드를 문서화
								, fieldWithPath("_links.self.href").description("link to self")
								, fieldWithPath("_links.query-events.href").description("link to query event list")
								, fieldWithPath("_links.update-event.href").description("link to update existing event")
								, fieldWithPath("_links.profile.href").description("link to profile")
						)
						// 일부분만 문서화하는 경우
//						relaxedResponseFields(
//								fieldWithPath("id").description("identifier of new event")
//								, fieldWithPath("name").description("name of new event")
//								, fieldWithPath("description").description("description of new event")
//								, fieldWithPath("beginEnrollmentDateTime").description("beginEnrollmentDateTime of begin of new event")
//								, fieldWithPath("closeEnrollmentDateTime").description("closeEnrollmentDateTime of close of new event")
//								, fieldWithPath("beginEventDateTime").description("beginEventDateTime of begin of new event")
//								, fieldWithPath("endEventDateTime").description("endEventDateTime of end of new event")
//								, fieldWithPath("location").description("location of new event")
//								, fieldWithPath("basePrice").description("basePrice of new event")
//								, fieldWithPath("maxPrice").description("maxPrice of new event")
//								, fieldWithPath("limitOfEnrollment").description("limit of enrollment")
//								, fieldWithPath("free").description("it tells if this event is free or not")
//								, fieldWithPath("offline").description("it tells if this event is offline meeting or not")
//								, fieldWithPath("eventStatus").description("event status")
//						)
				))
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

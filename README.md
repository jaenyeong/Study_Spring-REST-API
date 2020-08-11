# Study_Spring-REST-API
### 인프런 스프링 REST API 개발 (백기선)
https://www.inflearn.com/course/spring_rest-api/dashboard
-----

## [Settings]
#### Project Name
* Study_Spring-REST-API
#### java
* zulu jdk 11
#### gradle
* IDEA gradle wrapper
#### Spring boot
* 2.3.2
-----

### REST API
* REST (Representational State Transfer)
  * 웹과 같은 분산 하이퍼 미디어 시스템을 위한 소프트웨어 아키텍처의 한 형식
    * 아키텍처 스타일이면서 동시에 아키텍처 스타일의 집합 (하이브리드 아키텍처 스타일)
  * 웹에 존재하는 리소스(이미지, 동영상, 데이터, 제공되는 서비스)에 고유한 URI(주소)를 부여  
    HTTP 메서드를 활용하여 리소스에 접근, 호출 및 처리하는 아키텍처 방식
    * URI (Uniform Resource Identifier)
  * 리소스 기반(ROA, Resource Oriented Architecture)의 아키텍처
  * 인터넷 상에 시스템간 상호 운용성을 제공하는 방법 중 하나
  * 시스템 각각의 독립적인 진화를 보장하기 위한 방법

* API (Application Programming Interface)
  * 운영체제 또는 애플리케이션에서 제공하는 기능을 제어할 수 있게 만든 인터페이스

* MS REST API 가이드라인 (REST API라고 할 수 없음)
  * URI 형식
    * https://{serviceRoot}/{collection}/{id}
  * GET, POST, PUT, DELTE, HEAD, PATCH, OPTIONS
  * API Versioning
    * Major.minor 방식
    * URI에 버전 정보를 포함

* Roy T.Fielding (REST 설계자)
  * 하이퍼텍스트
  * REST API 버저닝을 하지 않는 것이 최고의 버저닝
  * 웹과 같은 분산 하이퍼 미디어 시스템을 위한 소프트웨어 아키텍처 스타일
    * 아키텍처 스타일은 제약 조건의 집합

* REST 아키텍처 스타일 (특징)
  * Client-Server (서버-클라이언트 구조)
    * 클라이언트 요청에 따라 서버가 리소스를 제공
    * 서버와 클라이언트에 작업을 분리
      * 구성요소를 단순화하여 확장성 용이 (각각 서버, 클라이언트가 별도로 진화 가능)
  * Stateless (무상태성)
    * 해당 요청을 이해할 수 있는 모든 정보가 해당 요청에 포함되어야 함
    * context를 별도로 저장하지 않음 (구현이 단순해짐)
    * 서버는 각각의 요청을 별개로 판단하여 처리 (요청간 절차나 순서가 연관되지 않음)
  * Cache (Cacheable - 캐시)
    * 해당 요청에 대한 응답에 캐싱 여부를 명시해야 함
      * 캐싱이 가능하다면 동일 요청시 응답 데이터 재사용 가능해야 함
    * Last-Modifed 태그 또는 E-Tag를 이용하여 캐싱 기능 구현
  * Uniform Interface (인터페이스 일관성)
    * 지정한 리소스가 URI를 통해 조작
    * 통일되고 한정적인 인터페이스로 조작되어야 함
    * HTTP 프로토콜을 따르는 모든 플랫폼에서 사용 가능
      * 특정 언어나 기술, 플랫폼에 종속되지 않음
  * Layered System (계층화)
    * 리소스 계층적 구성
    * 보안, 로드밸런싱, 암호화, 사용자 인증 등 구조상 유연성
    * 프록시, 게이트웨이 같은 네트워크 기반 중간 매체 사용 가능
  * Code-On-Demand (optional)
    * 서버에서 전송한 코드를 클라이언트에서 실행
    * 반드시 충족할 필요 없음

* Uniform Interface 제약조건
  * Identification of resources
    * URI로 리소스를 식별
  * manipulation of resources through representations
    * representation 전송을 통해 리소스 조작
  * Self-descriptive messages
    * 메시지는 스스로를 설명할 수 있어야 함
    * 서버가 변경되어 메시지가 변경되어도 클라이언트는 메시지를 해석할 수 있어야 함
    * 확장 가능한 커뮤니케이션
    * 잘못된 예시
      * 요청에 목적지(Host) URI 누락
        * http://localhost:8080
      * 응답에 Content-type 누락
        * Content-type: application/json-patch+json
  * hypermedia as the engine of application state (HATEOAS)
    * 애플리케이션의 상태는 하이퍼링크를 통해 전이가 되어야 함
    * 링크 정보를 동적으로 변경

* Uniform Interface 지켜야 하는 이유
  * 독립적 진화
    * 서버와 클라이언트가 각각 독립적으로 진화
    * 서버의 기능이 변경되어도 클라이언트를 업데이트 할 필요가 없음
    * REST 아키텍처를 만들게 된 계기와 같음
      * How do I improve HTTP without breaking the web?

* 웹은 독립적 진화에 대표 사례
  * 웹 페이지를 변경해도 웹 브라우저를 변경할 필요 없음
  * 웹 브라우저를 변경해도 웹 페이지를 변경할 필요 없음
  * HTTP 명세, HTML 명세가 변경되어도 웹은 잘 동작함

* 상호 운용성(interoperability)에 대한 집착
  * Referer
    * 오타를 수정하지 않음
  * charset (인코딩)
    * 잘못 지은 이름을 고치지 않음
  * HTTP
    * 상태코드 416 포기 (I'm a teapot)
  * HTTP/0.9
    * 아직도 지원(크롬, 파이어폭스)

* 이것들을 위한 노력
  * W3C - HTML
  * IETF - HTTP
  * 그 외 웹 브라우저 개발자, 웹 서버 개발자

* REST가 웹의 독립적 진화에 준 도움
  * HTTP에 지속적으로 영향
  * Host 헤더 추가
  * 길이 제한을 다루는 방법 명시
  * URI에서 리소스의 정의가 추상적으로 변경됨
    * 식별하고자 하는 무언가
  * 기타 HTTP와 URI에 많은 영향을 줌
  * HTTP/1.1 명세 최신판에서 REST에 대한 언급이 들어감
  * Roy T.Fielding은 REST뿐만 아니라 HTTP와 URI 명세 저자이기도 함

* HTTP methods CRUD Operations
  * GET
  * POST
  * PUT
  * DELTE

* RESTFUL
  * REST 아키텍처의 특징, 스타일 또는 구조, 구성요소 등과 같은 설계 가이드를 준수한 상태

* REST API
  * REST 아키텍처 스타일을 따르는 API
  * Roy T.Fielding 명시적으로 선언
    * 하이퍼텍스트를 포함한 Self-descriptive한 메시지의 Uniform Interface를 통해 리소스에 접근하는 API
      * Self-descriptive는 custom media type이나 profile link relation 등으로 만족시킬 수 있음
      * HATEOAS는 HTTP 헤더, 본문에 링크를 담아 만족시킬 수 있음
    * 반드시 REST API일 필요는 없음
      * 시스템 전체 통제가 가능하거나 진화에 관심이 없는 경우
      * 하지만 이 경우 HTTP API 등 REST API 외에 다른 단어를 사용할 것

* REST API를 충족시키기 위한 방법
  * Self-descriptive message
    * [1] 미디어 타입을 정의 및 IANA에 등록, 그 미디어 타입을 리소스 리턴할 때 Content-Type으로 사용
    * [2] 브라우저들이 아직 스펙 지원이 미흡하기 때문에 대안으로 HAL의 링크 데이터에 profile 링크 추가
  * HATEOAS
    * [1] 데이터에 링크 제공 (HAL로 링크 정의)
    * [2] 링크 헤더나 Location 제공

* HAL (Hypertext Application Language)
  * API의 리소스들을 쉽고 일관적인 하이퍼링크로 연결, 제공하는 형식 또는 규칙
  * 사용시 쉽게 API 탐색이 가능하고 API 자체에서 문서를 쉽게 찾음
  * 하이퍼링크를 표현하기 위해 JSON, XML 형식, 규칙 제공
    * 따라서 API의 데이터 파싱, 문서화에 집중 가능

### "Event" REST API
* 이벤트 등록, 조회 및 수정 API

#### GET /api/events
* 이벤트 목록 조회 REST API (로그인 안 한 상태)
  * 응답에 포함할 데이터
    * 이벤트 목록
    * 링크
      * self
      * profile : 이벤트 목록 조회 API 문서로 링크
      * get-an-event : 이벤트 하나 조회하는 API 링크
      * next : 다음 페이지 (optional)
      * prev : 이전 페이지 (optional)
    * 문서
      * 스프링 REST Docs로 만들 예정

* 이벤트 목록 조회 REST API (로그인 한 상태)
  * 응답에 포함할 데이터
    * 이벤트 목록
    * 링크
      * self
      * profile : 이벤트 목록 조회 API 문서로 링크
      * get-an-event : 이벤트 하나 조회하는 API 링크
      * create-new-event : 이벤트를 생성할 수 있는 API 링크
      * next : 다음 페이지 (optional)
      * prev : 이전 페이지 (optional)
  * 로그인 상태
    * 사실은 Bearer 헤더에 유효한 AccessToken이 들어있는 경우

#### POST /api/events
* 이벤트 생성

#### GET /api/events/{id}
* 특정 이벤트 조회

#### PUT /api/events/{id}
* 특정 이벤트 수정

#### Events API 사용 예제
* 토큰 없이 이벤트 목록 조회
  * create 안 보임
* access token 발급 (A 사용자 로그인)
* 유효한 A 토큰 가지고 이벤트 목록 조회
  * create event 보임
* 유효한 A 토큰 가지고 이벤트 생성
* 토큰 없이 이벤트 조회
  * update 링크 안 보임
* 유효한 A 토큰 가지고 이벤트 조회
  * update 링크 보임
* access token 발급 받기 (B 사용자 로그인)
* 유효한 B 토큰 가지고 이벤트 조회
  * update 안 보임

#### 예제 프로젝트 생성
* 의존성 추가
  * Spring Web
  * Spring Data JPA
  * Spring HATEOAS
  * Spring REST Docs
  * H2 Database
  * PostgreSQL Driver
  * Lombok

* 의존성 설정
  * H2
    * 테스트 스코프로 변경 (testImplementation)
  * postgresql
    * 컴파일 스코프로 변경 (compileOnly)
  * DB 우선순위
    * 데이터베이스 설정이 없는 상태에서는 인메모리 디비 사용

* 스프링 부트 핵심 원리
  * 의존성 설정 (pom.xml, build.gradle)
  * 자동 설정 (@EnableAutoConfiguration)
  * 내장 웹 서버 (의존성과 자동 설정의 일부)
  * 독립적으로 실행 가능한 JAR (pom.xml, build.gradle의 플러그인)

* Lombok 설정
  * Preference (command + ,)
    * Build, Execution, Deployment > Compiler > Annotation Processors
      * Enable annotation processing 체크박스 선택

#### Event 생성 API 구현

##### Event 클래스 구현
* ```
  @Getter
  @Setter
  @EqualsAndHashCode(of = "id")
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public class Event {
      private Integer id;
      private EventStatus eventStatus = EventStatus.DRAFT;
      private boolean offline;
      private boolean free;
  
      // 생성시 입력 받음
  
      // 이벤트 명
      private String name;
      // 이벤트 설명
      private String description;
      // 이벤트 등록 시작 시간
      private LocalDateTime beginEnrollmentDateTime;
      // 이벤트 등록 종료 시간
      private LocalDateTime closeEnrollmentDateTime;
      // 이벤트 시작 시간
      private LocalDateTime beginEventDateTime;
      // 이벤트 종료 시간
      private LocalDateTime endEventDateTime;
      // 이벤트 장소
      private String location; // (optional) 이게 없으면 온라인 모임
      // 이벤트 등록비
      private int basePrice;   // (optional)
      // 이벤트 등록비
      private int maxPrice;    // (optional)
      // 이벤트 참석자 제한 수
      private int limitOfEnrollment;
  }
  ```

* Lombok 애노테이션
  * Lombok 애노테이션 간소화
    * Lombok 애노테이션은 메타 애노테이션을 커스터마이징하여 모아서 사용할 수 없음
  * @EqualsAndHasCode
    * of를 사용하여 id 필드를 지정하는 이유
      * 양방향 참조를 하게되는 경우에 스택 오버플로우 방지하기 위해 특정 필드만 지정
  * @Builder
    * @AllArgsConstructor (@NoArgsConstructor 포함) 애노테이션을 사용하는 이유
      * 기본 생성자를 생성해주지 않음
      * 모든 필드를 매개변수로 하는 생성자 또한 public이 아닌 package private
      * 따라서 다른 패키지에서 객체를 생성하기 애매함
  * @Data
    * @EqualsAndHasCode 애노테이션까지 구현해주기 때문에 위와 같은 문제 발생
    * 엔티티 (@Entity 애노테이션을 태깅한 곳)에는 가급적 사용하지 말 것

##### Event price 정책
* basePrice = 0, maxPrice = 100
  * 선착순 등록
* basePrice = 0, maxPrice = 0
  * 무료
* basePrice = 100, maxPrice = 0
  * 무제한 경매 (높은 금액을 낸 사람이 등록)
* basePrice = 100, maxPrice = 200
  * 제한가 선착순 등록
  * 처음부터 200을 낸 사람은 바로 등록됨
  * 100을 내도 등록이 되나 더 많이 낸 사람에 의해 밀려 등록되지 않을 수 있음

##### EventStatus enum 구현
* ```
  public enum EventStatus {
      // 보이지 않는 상태
      DRAFT,
      // 공개한 상태
      PUBLISHED,
      // 등록 시작 상태
      BEGAN_ENROLLMENT,
      // 등록 종료 상태
      CLOSED_ENROLLMENT,
      // 이벤트 시작 상태
      STARTED,
      // 이벤트 종료 상태
      ENDED
  }
  ```

##### Event API 테스트 구현
* @WebMvcTest
  * MockMvc 빈 자동 설정
  * 웹 관련된 빈만 등록해주는 슬라이스 테스트

* MockMvc
  * 스프링 MVC 테스트의 핵심 클래스
  * 웹 서버를 띄우지 않고 스프링 MVC가 요청을 처리하는 과정 확인 가능
  * 컨트롤러 테스트로 자주 사용됨

* 테스트 할 것
  * 입력값들을 전달하면 JSON 응답으로 201이 나오는지 확인
    * Location 헤더에 생성된 이벤트를 조회할 수 있는 URI 담겨 있는지 확인
    * id는 DB에 들어갈 때 자동생성된 값으로 나오는지 확인
  * 입력값으로 누가 id나 eventStatus, offline, free와 같은 데이터까지 같이 주는 경우
    * Bad_Request로 응답 vs 받기로 한 값 이외 무시
  * 입력 데이터가 이상한 경우 Bad_Request로 응답
    * 입력값이 이상한 경우 에러
    * 비즈니스 로직으로 검사할 수 있는 에러
    * 에러 응답 메시지에 에러에 대한 정보가 있어야 함
  * 비즈니스 로직 적용 여부 응답 메시지 확인
    * offline과 free 값 확인
  * 응답에 HATEOAS와 profile 관련 링크가 있는지 확인
    * self (view)
    * update (이벤트 생성자는 수정 가능)
    * events (목록으로 이동하는 링크)
  * API 문서 만들기
    * 요청 문서화
    * 응답 문서화
    * 링크 문서화
    * profile 링크 추가

#### Event API 201 응답
* @RestController
  * @ResponseBody를 모든 메서드에 적용한 것과 동일

* ResponseEntity 사용 이유
  * 응답 코드, 헤더, 본문 등을 모두 다루기 편한 API

* Location URI 생성
  * HATEOS가 제공하는 linkTo(), methodOn() 메서드 사용

* 테스트 할 것
  * 입력값들을 전달하면 JSON 응답으로 201이 나오는지 확인
  * id는 DB에 들어갈 때 자동생성된 값으로 나오는지 확인

#### EventRepository 구현
* Spring Data JPA
  * JpaRepository 상속 받아 생성

* Enum을 JPA 맵핑시 주의
  * @Enumerated(EnumType.STRING)

* @MockBean
  * Mockito를 사용해서 mock 객체를 생성 후 빈으로 등록해줌
  * 주의
    * 기존 빈을 테스트용 빈이 대체

* 테스트 할 것
  * 입력값들을 전달하면 JSON 응답으로 201이 나오는지 확인
  * id는 DB에 들어갈 때 자동생성된 값으로 나오는지 확인

#### 입력 값 제한
* 입력값 제한
  * id, 입력 받은 데이터로 계산해야 하는 값들은 입력을 받지 않아야 함
  * EventDto 적용

* DTO -> 도메인 객체로 값 복사
  * 의존성 추가
    * ```
      implementation group: 'org.modelmapper', name: 'modelmapper', version: '2.3.8'
      ```

* 통합 테스트로 전환
  * @WebMvcTest 애노테이션 제거
  * @SpringBootTest, @AutoConfigureMockMvc 애노테이션 추가
  * @MockBean Repository 주입 제거

* 테스트 할 것
  * 입력값으로 누가 id나 eventStatus, offline, free와 같은 데이터까지 같이 주는 경우
    * Bad_Request로 응답 vs 받기로 한 값 이외 무시

##### 제한한 입력값 이외 값이 입력되는 경우 에러 발생시키기
* ObjectMapper 커스터마이징 (application.yaml)
  * spring.jackson.deserialization.fail-on-unknown-properties=true

* 테스트 할 것
  * 입력값으로 누가 id나 eventStatus, offline, free와 같은 데이터까지 같이 주는 경우
    * Bad_Request로 응답 vs 받기로 한 값 이외 무시

#### Bad Request 처리
* 의존성 추가
  * 스프링 부트 최신 버전에서는 validation 모듈이 분리되었기 때문에 별도 추가
  * ```
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-validation', version: '2.3.2.RELEASE'
    ```

* @Valid, BindingResult (또는 Errors)
  * BindingResult는 항상 @Valid 바로 다음 인자로 사용해야 함 (스프링 MVC)
  * @NotNull, @NotEmpty, @Min, @Max, ... 사용해서 입력값 바인딩할 때 에러 확인 가능

* 도메인 Validator 생성
  * Validator 인터페이스 없이 만들어도 상관없음

* 테스트 설명용 애노테이션 생성
  * @Target, @Retention

* 테스트 할 것
  * 입력 데이터가 이상한 경우 Bad_Request로 응답
    * 입력값이 이상한 경우 에러
    * 비즈니스 로직으로 검사할 수 있는 에러
    * 에러 응답 메시지에 에러에 대한 정보가 있어야 함

#### Bad Request 응답 본문
* Errors 객체는 JSON 변환이 안되서 본문에 담을 수 없음
  * 예제에서 생성한 Event 객체는 자바빈 규격을 준수하기 때문에 가능
    * 내부적으로 objectMapper를 사용하여 Event 객체를 JSON으로 변환
      * objectMapper는 BeanSerializer를 사용
  * Errors 객체는 자바빈 규격을 준수하고 있지 않음

* 커스텀 JSON Serializer 구현
  * ``` public Class ErrorsSerializer extends JsonSerializer<Errors> ``` (Jackson JSON 제공)
  * @JsonComponent (스프링 부트 제공)
    * @JsonComponent 애노테이션을 사용해 구현한 ErrorsSerializer를 ObjectMapper에 등록

* BindingError
  * FieldError 와 GlobalError (ObjectError)가 있음
  * objectName
  * defaultMessage
  * code
  * field
  * rejectedValue

* 테스트 할 것
  * 입력 데이터가 이상한 경우 Bad_Request로 응답
    * 입력값이 이상한 경우 에러
    * 비즈니스 로직으로 검사할 수 있는 에러
    * 에러 응답 메시지에 에러에 대한 정보가 있어야 함

#### 비즈니스 로직 적용
* 테스트 할 것
  * 비즈니스 로직 적용 여부 응답 메시지 확인
    * offline과 free 값 확인

#### 매개변수를 이용한 테스트
* 테스트 코드 리팩토링
  * 테스트에서 중복 코드 제거
  * 매개변수만 바꿀 수 있으면 좋겠는데?
  * JUnitParams

* 의존성 추가
  * ```
    // JUnitParams
    // testImplementation group: 'pl.pragmatists', name: 'JUnitParams', version: '1.1.1'
    
    // jupiter-params (Junit5에서 JUnitParams 호환성 문제 발생)
    // 버전 명시 필요 (5.7.0-M1 버전 에러)
    testImplementation group: 'org.junit.jupiter', name: 'junit-jupiter-params', version: '5.6.2'
    ```
  * 예제에서 JUnitParams 라이브러리를 사용하나 Junit4에서만 동작
  * Junit5 사용을 위해 jupiter-params 라이브러리 사용

* JupiterParams
  * ```
    @ParameterizedTest
    // @CsvSource, @MethodSource 중 선택하여 사용
    @CsvSource({"서울특별시 강서구 화곡동, true", "'', false", ", false"})
    @MethodSource("parametersForTestOffline")
    void testOffline(String location, boolean isOffline) throws Exception {
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
    ```
  * ```
    @ParameterizedTest
    // @CsvSource, @MethodSource 중 선택하여 사용
    @CsvSource({"0, 0, true", "0, 100, false", "100, 0, false"})
    @MethodSource("parametersForTestFree")
    void testFree(int basePrice, int maxPrice, boolean isFree) throws Exception {
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
    ```

### Spring HATEOAS
* HATEOAS (Hypermedia as the Engine of Application State)
  * 다른 네트워크 애플리케이션 아키텍처와 구별되는 REST 애플리케이션 아키텍처의 구성 요소
  * 클라이언트는 서버가 제공하는 하이퍼미디어를 통해 동적으로 정보를 제공하는 네트워크 애플리케이션과 상호작용

* Spring HATEOAS (라이브러리 또는 툴)
  * HATEOAS 원칙을 지키는 REST 아키텍처 생성을 돕는 API를 제공
    * 링크 생성 기능 (linkTo() 메서드)
      * 문자열 가지고 생성
      * 컨트롤러와 메서드로 생성
        * 컨트롤러 매핑 또는 linkTo, methodOn 등 메서드를 통해 생성
        * 예를 들어 ``` linkTo(methodOn(Controller.class).show(2L)) ```
      * 링크에 href, relation(기본적으로 self) 등 다시 설정 가능
    * 리소스 생성 기능
      * 리소스 (데이터 + 링크)
      * 리소스는 리소스를 포함할 수 있음 (복합적-composite)
    * 링크 찾아주는 기능
      * Traverson
      * LinkDiscoverers
    * 링크
      * HREF (하이퍼미디어 레퍼런스)
        * URI, URL 설정
      * REL
        * 리소스와의 관계 표현
          * self
            * 자신을 가리킬 때(URI, URL)
          * profile
            * 응답 본문에 대한 문서로 링크
          * update
            * 이벤트 생성자는 수정 가능
          * events
            * 목록으로 이동하는 링크

#### Spring HATEOAS 적용
* 의존성 추가
  * ```
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-hateoas', version: '2.3.2.RELEASE'
    ```

* 구현
  * [1] Event 클래스의 RepresentationModel 클래스 상속
    * Event 클래스
      * ``` public class Event extends RepresentationModel<Event> {} ```
    * EventController 클래스
      * ```
        savedEvent.add(linkTo(EventController.class).withRel("query-events"));
        savedEvent.add(selfLinkBuilder.withSelfRel());
        savedEvent.add(selfLinkBuilder.withRel("update-event"));
        
        return ResponseEntity.created(createUri).body(savedEvent);
        ```
  * [2] EntityModel 클래스를 상속한 EventResource 클래스 구현
    * 빈으로 등록하지 않고 사용할 것
    * EventResource 클래스
      * EntityModel 클래스 상속
        * ```
          public class EventResource<E extends Event> extends EntityModel<Event> {
              @JsonUnwrapped
              private final Event event;
             
              public EventResource(Event event) {
                  this.event = event;
                  // 링크 정보를 생성자에서 설정하는 경우
                  add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
                  add(linkTo(EventController.class).withRel("query-events"));
                  add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));
              }
          }
          ```
          * 내부의 Event 필드 선언
          * Event {} 형태로 감싸고 싶지 않은 경우
            * Event 객체내 필드들을 EventResource 클래스 안에 직접 선언
            * @JsonUnwrapped 애노테이션 사용하여 래핑 제거
    * EventController 클래스
      * ```
        // [2] EntityModel 클래스를 상속한 EventResource 구현하여 사용
        EventResource<Event> eventResource = new EventResource<>(savedEvent);
        
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilder.withSelfRel());
        eventResource.add(selfLinkBuilder.withRel("update-event"));
        
        return ResponseEntity.created(createUri).body(eventResource);
        ```
  * [3] EntityModel 클래스를 직접 사용
    * EventController 클래스
      * ```
        // [3] 또는 EntityModel 클래스 직접 사용
        EntityModel<Event> eventResource = EntityModel.of(savedEvent);
        
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilder.withSelfRel());
        eventResource.add(selfLinkBuilder.withRel("update-event"));
        
        return ResponseEntity.created(createUri).body(eventResource);
        ```
      * ```
        // EntityModel 클래스 직접 사용시 Link 리스트 객체 생성 후 인자로 넘김
        List<Link> links = Arrays.asList(
        //      selfLinkBuilder.slash(savedEvent.getId()).withSelfRel(),
                selfLinkBuilder.withSelfRel(),
                selfLinkBuilder.withRel("query-events"),
                selfLinkBuilder.withRel("update-event")
        );
        
        EntityModel<Event> eventResource = EntityModel.of(savedEvent, links);
        
        return ResponseEntity.created(createUri).body(eventResource);
        ```

* 변경 사항
  * ResourceSupport > RepresentationModel
  * Resource > EntityModel
  * Resources > CollectionModel
  * PagedResources > PagedModel

* 테스트 할 것
  * 응답에 HATEOAS 관련 링크가 있는지 확인
    * self (view)
    * update (이벤트 생성자는 수정 가능)
    * events (목록으로 이동하는 링크)
  * 아직 profile 링크는 추가하지 않음

### Spring REST Docs
* 직접 작성한 문서와 Spring Test로 생성된 자동 생성 스니펫(문서 조각)을 결합하여 RESTFUL 서비스 문서화
  * RESTFUL 서비스에 대한 정확하고 읽기 쉬운 문서 생성

* REST Docs 구현
  * andDo(document("doc-name", snippets))
  * snippets
    * links()
    * requestParameters() + parameterWithName()
    * pathParameters() + parametersWithName()
    * requestParts() + partWithName()
    * requestPartBody()
    * requestPartFields()
    * requestHeaders() + headerWithName()
    * requestFields() + fieldWithPath()
    * responseHeaders() + headerWithName()
    * responseFields() + fieldWithPath()
    * 기타
  * Relaxed 접두어
  * Processor
    * preprocessRequest(prettyPrint())
    * preprocessResponse(prettyPrint())

#### Spring REST Docs 적용
* 스프링 부트 사용시 REST Docs 자동 설정
  * @AutoConfigureRestDocs
    * EventControllerTest 클래스에 추가

* 생성 경로
  * EventControllerTest 클래스 createEvent 테스트 메서드 docs 경로
    * build/generated-snippets/create-event
      * curl-request.adoc
      * http-request.adoc
      * http-response.adoc
      * httpie-request.adoc
        * httpie CLI 툴
      * request-body.adoc
      * response-body.adoc

* RestDocMockMvc 커스터마이징
  * REST docs 요청 응답 포매팅을 위해 커스터마이징
  * 방법
    * RestDocsMockMvcConfigurationCustomizer 구현한 빈 등록
    * @TestConfiguration
      * 테스트에서만 사용하는 설정을 알려주는 애노테이션
    * ```
      @TestConfiguration
      public class RestDocsConfiguration {
      
          @Bean
          public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {
              return configurer -> {
                  configurer.operationPreprocessors()
                          .withRequestDefaults(prettyPrint())
                          .withResponseDefaults(prettyPrint());
              };
          }
      }
      ```

* 테스트 할 것
   * API 문서 만들기
     * 요청 본문 문서화
     * 응답 본문 문서화
     * 응답 헤더 문서화
     * 링크 문서화
       * profile 링크 추가

#### 링크, (Req, Res) 필드와 헤더 문서화
* 요청 필드 문서화
  * requestFields() + fieldWithPath()
  * responseFields() + fieldWithPath()
  * requestHeaders() + headerWithName()
  * responseHeaders() + headerWithName()
  * links() + linkWithRel()

* 테스트 할 것
  * API 문서 만들기
    * 요청 본문 문서화
    * 응답 본문 문서화
    * 링크 문서화
      * self
      * query-events
      * update-event
      * profile 링크 추가 (추후)
    * 요청 헤더 문서화
    * 요청 필드 문서화
    * 응답 헤더 문서화
    * 응답 필드 문서화

* Relaxed 접두어
  * 장점
    * 문서 일부분만 테스트 가능
  * 단점
    * 정확한 문서 생성 불가

#### 문서 빌드
* Maven 설정
  * ```
    <dependency> 
    	<groupId>org.springframework.restdocs</groupId>
    	<artifactId>spring-restdocs-mockmvc</artifactId>
    	<version>{project-version}</version>
    	<scope>test</scope>
    </dependency>
    
    <build>
    	<plugins>
    		<plugin> 
    			<groupId>org.asciidoctor</groupId>
    			<artifactId>asciidoctor-maven-plugin</artifactId>
    			<version>1.5.8</version>
    			<executions>
    				<execution>
    					<id>generate-docs</id>
    					<phase>prepare-package</phase> 
    					<goals>
    						<goal>process-asciidoc</goal>
    					</goals>
    					<configuration>
    						<backend>html</backend>
    						<doctype>book</doctype>
    					</configuration>
    				</execution>
    			</executions>
    			<dependencies>
    				<dependency> 
    					<groupId>org.springframework.restdocs</groupId>
    					<artifactId>spring-restdocs-asciidoctor</artifactId>
    					<version>{project-version}</version>
    				</dependency>
    			</dependencies>
    		</plugin>
    	</plugins>
    </build>
    ```

* Gradle 설정
  * ```
    plugins { 
    	id "org.asciidoctor.convert" version "1.5.9.2"
    }
    
    dependencies {
    	asciidoctor 'org.springframework.restdocs:spring-restdocs-asciidoctor:{project-version}' 
    	testCompile 'org.springframework.restdocs:spring-restdocs-mockmvc:{project-version}' 
    }
    
    ext { 
    	snippetsDir = file('build/generated-snippets')
    }
    
    test { 
    	outputs.dir snippetsDir
    }
    
    asciidoctor { 
    	inputs.dir snippetsDir 
    	dependsOn test 
    }
    
    bootJar {
    	dependsOn asciidoctor 
    	from ("${asciidoctor.outputDir}/html5") { 
    		into 'static/docs'
    	}
    }
    ```
  * 참조
    * https://docs.spring.io/spring-restdocs/docs/current/reference/html5/#getting-started-build-configuration

* 템플릿 파일 추가
  * maven
    * src/main/asciidoc/index.adoc
  * gradle
    * src/docs/asciidoc/index.adoc

* 문서 생성 (adoc 파일 기반으로 html 파일 생성 및 resource 경로 이동)
  * maven
    * mvn package
      * test
      * prepare-package :: process-asciidoc 기능
        * 모든 adoc 파일을 html 파일로 생성
      * prepare-package :: copy-resources 기능
        * target/classes/static/docs 경로에 html 파일을 이동시켜줌
  * gradle
    * gradle build (documentation asciidoctor)
    * gradle 설정
      * ```
        test {
            outputs.dir snippetsDir
            useJUnitPlatform()
        }
        
        asciidoctor {
        //	attributes 'snippets': snippetsDir
            inputs.dir snippetsDir
            dependsOn test
        }
        
        asciidoctor.doFirst {
        	println ' [doFirst Log - asciidoctor] It will be deleted docs file now! '
        	delete file('src/main/resources/static/docs')
        }
        
        asciidoctor.doLast {
        	println ' [doLast Log - asciidoctor] Nothing is gonna work '
        }
        
        task copyDocs(type: Copy) {
            println " [Task copyDocs - dependsOn asciidoctor] "
            dependsOn asciidoctor
            from file("build/asciidoc/html5/")
            into file("src/main/resources/static/docs")
        }
        
        build {
            dependsOn copyDocs
        }
        ```
     
* 문서 확인
  * maven
    * target/generated-docs/index.html
    * target/classes/static/docs/index.html
  * gradle
    * build/asciidoc/htm5/index.html
    * build/resources/static/docs/index.html

* 테스트 할 것
   * API 문서 만들기
     * 요청 본문 문서화
     * 응답 본문 문서화
     * 응답 헤더 문서화
     * 링크 문서화
       * profile 링크 추가

### DB 설정

#### PostgreSQL 적용
* 테스트시 H2, 운영시 PostgreSQL 적용

* 의존성 추가
  * ```
    implementation group: 'org.postgresql', name: 'postgresql', version: '42.2.14'
    ```

* Docker
  * PostgreSQL 컨테이너 설정 
    * ```
      docker run --name ndb -p 5432:5432 -e POSTGRES_PASSWORD=pass -d postgres
      docker exec -i -t ndb bash
      su - postgres
      psql -d postgres -U postgres
      \l
      \dt
      ```

* application.properties(yaml) 파일 설정
  * 데이터소스
    * spring.datasource.username=postgres
    * spring.datasource.password=pass
    * spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
    * spring.datasource.driver-class-name=org.postgresql.Driver
  * JPA
    * spring.jpa.hibernate.ddl-auto=create-drop
    * spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
    * spring.jpa.properties.hibernate.format_sql=true
      * 포매팅
    * logging.level.org.hibernate.SQL=DEBUG
      * 쿼리 출력
    * logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
      * 쿼리에 바인딩되는 값 확인

* 애플리케이션 설정과 테스트 설정 중복 줄이기
  * 프로파일과 @ActiveProfiles 애노테이션 활용

* application-test.properties(yaml) 파일 설정
  * spring.datasource.username=sa
  * spring.datasource.password=
  * spring.datasource.url=jdbc:h2:mem:testdb
  * spring.datasource.driver-class-name=org.h2.Driver
  * spring.datasource.hikari.jdbc-url=jdbc:h2:mem:testdb
  * spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect

### Gradle Dependencies configuration
* implementation (former - compile)
  * 컴파일 클래스 경로에 모듈 추가, 빌드 출력에 해당 모듈 패키징
  * 컴파일 타임에 다른 모듈에게 해당 모듈을 유출하지 않음을 명시
  * 따라서 해당 모듈은 런타임에만 다른 모듈에서 이용 가능
  * 빌드 시스템이 리컴파일 해야하는 프로젝트의 양이 줄어 빌드 타임이 향상됨
  * 해당 모듈 수정 시 직접적인 의존관계에 있는 모듈까지만 리컴파일(리빌드)
  * 대부분 앱과 테스트 모듈은 implementation 설정을 사용해야 함
* testImplementation (former - testCompile)
  * 테스트에 사용되는 모듈을 선언(추가)하는데 사용
  * implementation 설정을 확장하고 있음
  * 빌드과정에서 배포본에 포함되지 않음
  * 이는 멀티 프로젝트 구조에서 다른 프로젝트가 참조하는 경우에도 동일하게 적용
* API (former - compile)
  * 컴파일 클래스 경로와 빌드 출력에 해당 모듈 추가
  * 해당 모듈을 다른 모듈로 의존관계를 이전하여 다른 모듈에서도 런타임, 컴파일 상관없이 모두 사용 가능함을 명시
  * 종속된 하위 모듈 모두를 패키지에 포함
  * api 모듈이 외부 API를 변경하면 컴파일 시 해당 모듈에 액세스 권한이 있는 모든 모듈을 다시 컴파일하기 때문에
  * 다른 업스트림 소비자에게 이전해야 하는 모듈과만 사용해야 함
  * 따라서 api 모듈 수가 많으면 빌드 시간이 크게 증가
  * 별도의 모듈에 노출하지 않으려면 대신 implementation 설정을 사용할 것
* compileOnly (former - provided)
  * 컴파일 클래스 경로에만 의존성 추가 (빌드 출력에는 추가되지 않음)
  * 컴파일 타임에만 의존성이 필요한 경우 유용함 (런타임 환경에는 다른 의존성을 사용한다고 가정)
  * 즉, 라이브러리 모듈에 런타임 조건이 포함되어 의존성 사용 가능한지 확인한 다음
  * 제공되지 않은 경우에도 계속 작동할 수 있도록 동작을 정상적으로 변경해야함
  * 중요하지 않은 일시적인 모듈을 추가하지 않음으로써 파일 크기를 줄이는데 도움이 됨
* testCompileOnly
  * 테스트 컴파일 타임에만 필요한 모듈 추가
* runtimeOnly (former - apk)
  * 런타임 중에 사용하기 위해 빌드 출력에만 해당 모듈을 추가
  * 즉, 컴파일 클래스 경로에 추가되지 않음
* testRuntimeOnly
  * 테스트 런타임에만 필요한 모듈 추가
* annotationProcessor
  * 주석 프로세서인 라이브러리에 모듈 추가시 사용
  * 파일 클래스 경로를 주석 프로세서 클래스 경로에서 분리, 빌드 성능이 개선됨
  * 컴파일 클래스 경로에서 주석 프로세서를 찾으면 컴파일 회피를 비활성화
  * 이는 빌드시간에 부정적인 영향을 미침
    * Gradle 5.0 이상 부터는 컴파일 클래스 경로에서 발견된 주석 프로세서를 무시함
  * JAR 파일에 META-INF/services/javax.annotation.processing.Processor 파일이 포함되어 있는 경우
  * 종속성이 주석 프로세서라고 가정
* implementation vs api
  * 'A'(implementation or api) <- 'B' <- 'C' 경우
    * implementation
      * 'C' 에서 'A'를 접근할 수 없음
      * 'A' 수정 시 'B'까지 다시 빌드 
    * api
      * 'C' 에서 'A' 를 접근할 수 있음
      * A 수정시 B 와 C 모두 다시 빌드
  * 가능하다면 가급적 implementation 설정을 사용하는 것이 빌드 시간을 줄임

### 인덱스 핸들러 생성
* 인덱스 핸들러
  * 다른 리소스에 대한 링크 제공
  * 문서화

* 구현
  * ```
    @GetMapping("/api")
    public RepresentationModel<?> index() {
        var index = new RepresentationModel<>();
        index.add(linkTo(EventController.class).withRel("events"));
        return index;
    }
    ```

* 테스트 컨트롤러 리팩터링
  * 중복 코드 제거
  
* 에러 리소스
  * 인덱스로 가는 링크 제공

### Event 목록 조회, 수정 REST API 개발

#### Event 목록 조회 API 구현
* 페이징, 정렬
  * 스프링 데이터 JPA가 제공하는 Pageable 사용

* Page<Event>에 안에 들어있는 Event들은 리소스로 변경하는 방법
  * 하나씩 순회하면서 직접 EventResource로 맵핑?
  * PagedResourceAssembler<T> 사용
    * toModel 메서드

* 테스트 할 때 Pageable 파라미터 제공하는 방법
  * page
    * 0부터 시작
  * size
    * 기본값 20
  * sort
    * property, property(ASC | DESC)

* 테스트 할 것
  * Event 목록 Page 정보와 함께 받기
    * content[0].id 확인
    * pageable 경로 확인
  * Sort과 Paging 확인
    * 30개 생성, 10개 사이즈로 두번째 페이지 조회하면 이전, 다음 페이지로 가는 링크가 있어야 함
    * 이벤트 이름순으로 정렬
    * page 관련 링크
  * Event를 EventResource로 변환해서 받기
    * 각 이벤트 마다 self
  * 링크 확인
    * self
    * profile
    * (create)
  * 문서화

#### Event 조회 API
* 테스트 할 것
  * 조회하는 이벤트가 있는 경우 이벤트 리소스 확인
    * 링크
    * self
    * profile
    * (update)
    * 이벤트 데이터 
  * 조회하는 이벤트가 없는 경우 404 응답 확인

#### Events 수정 API
* 테스트 할 것
  * 수정하려는 이벤트가 없는 경우 404 NOT_FOUND
  * 입력 데이터 (데이터 바인딩)가 이상한 경우에 400 BAD_REQUEST
  * 도메인 로직으로 데이터 검증 실패하면 400 BAD_REQUEST
  * (권한이 충분하지 않은 경우에 403 FORBIDDEN)
  * 정상적으로 수정한 경우에 이벤트 리소스 응답
    * 200 OK
    * 링크
    * 수정한 이벤트 데이터

### Test Class Refactoring
* 여러 컨트롤러 간의 중복 코드 제거
  * 클래스 상속을 사용하는 방법
  * @Disabled (Junit4 -> @Ignore) 애노테이션으로 테스트로 간주되지 않도록 설정

### Spring Security
* 스프링 시큐리티
  * 웹 시큐리티 (Filter 기반 시큐리티)
    * 웹 요청에 보안 (인증, 인가) 처리
    * 시큐리티 필터 체인 (서블릿 필터)
    * 절차
      * [1] 웹 요청
      * [2] 서블릿 필터(DelegatingFilterProxy > FilterChainProxy)가 가로챔
        * FilterChainProxy가 필터 체인 목록을 가지고 있음
      * [3] 웹 시큐리티 인터셉터로 요청이 전달됨
      * [4] 필터 체인 적용 여부를 확인
      * [5] 필터 체인 적용시 SecurityContextHolder(ThreadLocal 객체)에서 인증 정보를 꺼냄
        * 인증 정보(Authentication)
      * [6] 꺼낸 데이터 유무를 통해 인증된 유저 여부 확인
        * 없는 경우 AuthenticationManager를 사용해 로그인
          * AuthenticationManager가 사용하는 2가지 인터페이스
            * UserDetailsService
            * PasswordEncoder
          * BasicAuthentication
            * 인증 요청 헤더의 Authentication, Basic, usernamePassword 등을 합친 것을 인코딩한 문자열을 입력 받음
            * UserDetailsService 인터페이스를 사용하여 입력받은 username에 매핑된 password 정보를 가져옴
            * passwordEncoder 인터페이스를 사용하여 입력받은 password와 가져온 password 데이터 일치 여부 확인
            * 로그인 처리 후 Authentication 객체를 생성하여 SecurityContextHolder에 담음
      * [7] 요청(리소스)에 관한 권한을 확인
        * User의 Role을 통해 처리
  * 메서드 시큐리티
    * 웹 요청은 별개로 메서드 호출 시 보안 (인증, 인가) 처리
    * 예를 들어 AOP, 프록시
  * 웹 시큐리티, 메서드 시큐리티 모두 시큐리티 인터셉터(Security Interceptor 인터페이스)를 사용
  * 리소스 접근 허용 여부를 결정하는 로직이 들어있음

* 의존성 추가
  * ```
    implementation group: 'org.springframework.security.oauth.boot', name: 'spring-security-oauth2-autoconfigure', version: '2.3.2.RELEASE'
    ```

* UserDetailsService 구현
  * 예외 테스트
    * expected
    * @Rule ExpectedException
    * try-catch

* 의존성 추가시 테스트 깨짐 (401 Unauthorized)
  * 스프링 부트가 제공하는 스프링 시큐리티 기본 설정 때문

#### Account 도메인 추가
* OAuth2로 인증을 위해서 Account 구현
  * id
  * email
  * password
  * roles

* AccountRoles
  * ADMIN, USER

* JPA 맵핑
  * @Table("Users")

* JPA enumeration collection mapping
  * @ElementCollection(fetch = FetchType.EAGER)
  * @Enumerated(EnumType.STRING)
  * private Set<AccountRole> roles;

* Event 클래스에 owner 추가
  * ```
    @ManyToOne
    Account manager;
    ```

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

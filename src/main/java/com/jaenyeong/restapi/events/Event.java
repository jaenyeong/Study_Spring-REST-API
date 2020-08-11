package com.jaenyeong.restapi.events;

import com.jaenyeong.restapi.accounts.Account;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Event extends RepresentationModel<Event> {
	// 식별자
	@Id
	@GeneratedValue
	private Integer id;
	// 이벤트 상태
	@Builder.Default
	@Enumerated(EnumType.STRING)
	private EventStatus eventStatus = EventStatus.DRAFT;
	// 오프라인 여부
	private boolean offline;
	// 유료 여부
	private boolean free;

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
	// 이벤트 등록비 (기본)
	private int basePrice;   // (optional)
	// 이벤트 등록비 (최대)
	private int maxPrice;    // (optional)
	// 이벤트 참석자 제한 수
	private int limitOfEnrollment;
	// 권한 설정 등을 위한 유저 정보 (단방향 참조)
	@ManyToOne
	private Account manager;

	public void update() {
		// free (무료 여부)
		this.free = (this.basePrice == 0) && (this.maxPrice == 0);

		// offline (오프라인 여부)
		this.offline = (this.location != null) && !(this.location.isBlank());
	}
}

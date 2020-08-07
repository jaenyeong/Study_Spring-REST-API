package com.jaenyeong.restapi.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
	// 이벤트 명
	@NotEmpty
	private String name;
	// 이벤트 설명
	@NotEmpty
	private String description;
	// 이벤트 등록 시작 시간
	@NotNull
	private LocalDateTime beginEnrollmentDateTime;
	// 이벤트 등록 종료 시간
	@NotNull
	private LocalDateTime closeEnrollmentDateTime;
	// 이벤트 시작 시간
	@NotNull
	private LocalDateTime beginEventDateTime;
	// 이벤트 종료 시간
	@NotNull
	private LocalDateTime endEventDateTime;
	// 이벤트 장소
	private String location; // (optional) 이게 없으면 온라인 모임
	// 이벤트 등록비 (기본)
	@Min(0)
	private int basePrice;   // (optional)
	// 이벤트 등록비 (최대)
	@Min(0)
	private int maxPrice;    // (optional)
	// 이벤트 참석자 제한 수
	@Min(0)
	private int limitOfEnrollment;
}

package com.jaenyeong.restapi.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
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
}

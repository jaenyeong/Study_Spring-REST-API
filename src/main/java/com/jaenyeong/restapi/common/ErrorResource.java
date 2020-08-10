package com.jaenyeong.restapi.common;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.Errors;

@Getter
public class ErrorResource extends RepresentationModel<ErrorResource> {
	// 테스트를 위해 필드명을 content로 작명, 생성자 직접 선언 (lombok은 객체와 필드명이 같은지 확인하여 사용 불가)
	private final Errors content;

	public ErrorResource(Errors errors) {
		this.content = errors;
	}
}

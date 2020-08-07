package com.jaenyeong.restapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

// 유효성 검사 커스터마이징 클래스 생성
@Component
public class EventValidator {

	public void validate(EventDto eventDto, Errors errors) {
		if ((eventDto.getBasePrice() > eventDto.getMaxPrice()) &&
				(eventDto.getMaxPrice() != 0)) {
			errors.rejectValue("basePrice", "wrongValue", "BasePrice is wrong value");
			errors.rejectValue("maxPrice", "wrongValue", "maxPrice is wrong value");
		}

		LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
		if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
				endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
				endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {

			errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong value");
		}

		LocalDateTime beginEventDateTime = eventDto.getBeginEventDateTime();
		if (beginEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime()) ||
				beginEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime())) {

			errors.rejectValue("beginEventDateTime", "wrongValue", "beginEventDateTime is wrong value");
		}

		LocalDateTime closeEnrollmentDateTime = eventDto.getCloseEnrollmentDateTime();
		if (closeEnrollmentDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {

			errors.rejectValue("closeEnrollmentDateTime", "wrongValue", "closeEnrollmentDateTime is wrong value");
		}
	}
}

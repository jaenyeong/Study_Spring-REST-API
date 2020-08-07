package com.jaenyeong.restapi.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;

// @JsonComponent 애노테이션을 사용해 구현한 ErrorsSerializer를 ObjectMapper에 등록
@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {

	@Override
	public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		// Errors 안에 Error가 여러개라서 배열 사용
		gen.writeStartArray();

		// rejectValue 메서드 사용하여 필드 에러에 담긴 에러 처리
		errors.getFieldErrors().forEach(fieldError -> {
			try {
				gen.writeStartObject();
				gen.writeStringField("field", fieldError.getField());
				gen.writeStringField("objectName", fieldError.getObjectName());
				gen.writeStringField("code", fieldError.getCode());
				gen.writeStringField("defaultMessage", fieldError.getDefaultMessage());

				Object rejectedValue = fieldError.getRejectedValue();
				if (rejectedValue != null) {
					gen.writeStringField("rejectedValue", rejectedValue.toString());
				}

				gen.writeEndObject();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		});

		// reject 메서드 사용하여 글로벌 에러에 담긴 에러 처리
		errors.getGlobalErrors().forEach(objectError -> {
			try {
				gen.writeStartObject();
				gen.writeStringField("objectName", objectError.getObjectName());
				gen.writeStringField("code", objectError.getCode());
				gen.writeStringField("defaultMessage", objectError.getDefaultMessage());

				gen.writeEndObject();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		});

		gen.writeEndArray();
	}
}

package com.jaenyeong.restapi.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
// Spring boot starter configuration processor 의존성 추가
// 의존성 추가 후 빌드하면 application.properties(yaml) 파일에서 아래 필드 속성 사용 가능
@ConfigurationProperties(prefix = "jaenyeong-app")
@Getter
@Setter
public class AppProperties {
	@NotEmpty
	private String adminEmail;

	@NotEmpty
	private String adminPassword;

	@NotEmpty
	private String userEmail;

	@NotEmpty
	private String userPassword;

	@NotEmpty
	private String clientId;

	@NotEmpty
	private String clientSecret;
}

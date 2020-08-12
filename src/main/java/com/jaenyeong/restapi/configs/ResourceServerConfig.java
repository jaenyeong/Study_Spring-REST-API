package com.jaenyeong.restapi.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

// 리소스 서버 설정
@Configuration
// deprecated
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("event");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.anonymous()
				.and()
				// GEt /api/** 요청을 anonymous()으로 설정하면 익명 사용자만 요청 가능
//				.authorizeRequests().mvcMatchers(HttpMethod.GET, "/api/**").anonymous()
				// 따라서 모든 사용자가 요청할 수 있도록 permitAll() 설정으로 변경
				.authorizeRequests().mvcMatchers(HttpMethod.GET, "/api/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}

package com.jaenyeong.restapi.configs;

import com.jaenyeong.restapi.accounts.AccountService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final AccountService accountService;
	private final PasswordEncoder passwordEncoder;

	public SecurityConfig(AccountService accountService, PasswordEncoder passwordEncoder) {
		this.accountService = accountService;
		this.passwordEncoder = passwordEncoder;
	}

	// Deprecated
	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(accountService).passwordEncoder(passwordEncoder);
	}

	// 시큐리티 필터 적용 여부 설정
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers("/docs/index.html");
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	// HttpSecurity 파라미터를 받은 configure 메서드에서 설정시
	// Spring Security 안으로 들어온 후 처리하는 것
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//				.mvcMatchers("/docs/index.html").anonymous()
//				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).anonymous();

		http.anonymous()
				.and()
				.formLogin()
				.and()
//				.authorizeRequests().mvcMatchers(HttpMethod.GET, "/api/**").anonymous()
				.authorizeRequests().mvcMatchers(HttpMethod.GET, "/api/**").authenticated()
				.anyRequest().authenticated();
	}
}

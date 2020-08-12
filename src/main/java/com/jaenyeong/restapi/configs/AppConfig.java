package com.jaenyeong.restapi.configs;

import com.jaenyeong.restapi.accounts.Account;
import com.jaenyeong.restapi.accounts.AccountRole;
import com.jaenyeong.restapi.accounts.AccountService;
import com.jaenyeong.restapi.common.AppProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {
			// AccountService를 AppConfig에 선언해 주입 받으면 순환 참조 에러 발생
			// AccountService에서 PasswordEncoder를 주입 받음
			@Autowired
			AccountService accountService;

			@Autowired
			AppProperties appProperties;

			@Override
			public void run(ApplicationArguments args) throws Exception {
				accountService.saveAccount(createAndSaveAccount(
						appProperties.getAdminEmail(),
						appProperties.getAdminPassword(),
						Set.of(AccountRole.ADMIN, AccountRole.USER)));

				accountService.saveAccount(createAndSaveAccount(
						appProperties.getUserEmail(),
						appProperties.getUserPassword(),
						Set.of(AccountRole.USER)));
			}
		};
	}

	private Account createAndSaveAccount(String email, String password, Set<AccountRole> roles) {
		return Account.builder()
				.email(email)
				.password(password)
				.roles(roles)
				.build();
	}
}

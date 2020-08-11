package com.jaenyeong.restapi.accounts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

// BaseControllerTest를 상속하지 않고 구현
@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {
	@Autowired
	AccountService accountService;

	@Autowired
	AccountRepository accountRepository;

	@Test
	@DisplayName("사용자 인증 (이름이 아닌 이메일로 인증)")
	void findByUsername() {
		// 이름이 아닌 이메일로 인증
		String userEmail = "jaenyeong.dev@gmail.com";
		String password = "1234";

		// given
		Account account = Account.builder()
				.email(userEmail)
				.password(password)
				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build();

		this.accountRepository.save(account);

		// when
		UserDetailsService userDetailsService = accountService;
		UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

		// then
		assertEquals(userDetails.getPassword(), password);
	}
}

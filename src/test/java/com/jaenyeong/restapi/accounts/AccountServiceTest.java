package com.jaenyeong.restapi.accounts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	@DisplayName("[2] 사용자 인증 시 실패")
	void secondFailFindByUsername() {
		String userEmail = "NotExistingEmail@gmail.com";

		try {
			accountService.loadUserByUsername(userEmail);
			fail("supposed to be failed");
		} catch (UsernameNotFoundException ue) {
			ue.printStackTrace();
			assertTrue(ue.getMessage().contains(userEmail));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("[1] 사용자 인증 시 실패")
	void firstFailFindByUsername() {
		String userEmail = "NotExistingEmail@gmail.com";

		Exception usernameNotFoundException =
				assertThrows(UsernameNotFoundException.class, () -> accountService.loadUserByUsername(userEmail));

		assertEquals(usernameNotFoundException.getClass(), UsernameNotFoundException.class);
	}

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

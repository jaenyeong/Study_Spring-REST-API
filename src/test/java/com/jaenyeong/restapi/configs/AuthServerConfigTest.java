package com.jaenyeong.restapi.configs;

import com.jaenyeong.restapi.accounts.Account;
import com.jaenyeong.restapi.accounts.AccountRole;
import com.jaenyeong.restapi.accounts.AccountService;
import com.jaenyeong.restapi.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthServerConfigTest extends BaseControllerTest {
	@Autowired
	AccountService accountService;

	@Test
	@DisplayName("인증 토큰 발급 테스트")
	void getAuthToken() throws Exception {
		// given
		final String clientId = "jaenyeongApp";
		final String clientSecret = "pass";
		final String userEmail = "jaenyeong.dev@gmail.com";
		final String password = "1234";

		// AuthServerConfig 클래스의 runner에서 데이터 삽입으로 주석처리
//		Account account = Account.builder()
//				.email("jaenyeong.dev@gmail.com")
//				.password("1234")
//				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
//				.build();
//		this.accountService.saveAccount(account);

		// when
		// 인증 서버 등록 시 /oauth/token 핸들러가 적용됨
		this.mockMvc.perform(post("/oauth/token")
				.with(httpBasic(clientId, clientSecret))
				.param("username", userEmail)
				.param("password", password)
				.param("grant_type", "password")
		)
				// then
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("access_token").exists());
	}
}

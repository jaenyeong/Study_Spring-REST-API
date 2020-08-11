package com.jaenyeong.restapi.configs;

import com.jaenyeong.restapi.accounts.AccountService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

// 인증 서버 설정
@Configuration
// deprecated
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final AccountService accountService;
	private final TokenStore tokenStore;

	public AuthServerConfig(PasswordEncoder passwordEncoder,
	                        AuthenticationManager authenticationManager,
	                        AccountService accountService,
	                        TokenStore tokenStore) {
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.accountService = accountService;
		this.tokenStore = tokenStore;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("jaenyeongApp")
				// access_token 발급 시 refresh_token도 같이 발급
				// refresh_token으로 새 access_token 발급 가능
				.authorizedGrantTypes("password", "refresh_token")
				.scopes("read", "write")
				.secret(this.passwordEncoder.encode("pass"))
				.accessTokenValiditySeconds(10 * 60)
				.refreshTokenValiditySeconds(6 * 10 * 60);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
				.userDetailsService(accountService)
				.tokenStore(tokenStore);
	}
}

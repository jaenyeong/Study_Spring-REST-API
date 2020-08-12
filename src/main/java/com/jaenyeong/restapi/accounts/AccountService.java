package com.jaenyeong.restapi.accounts;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;

	public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// 계정 정보 저장시 비밀번호 인코딩 추가
	public Account saveAccount(Account account) {
		account.setPassword(this.passwordEncoder.encode(account.getPassword()));
		return this.accountRepository.save(account);
	}

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		// 이름이 아닌 이메일로 인증
		Account userAccount = this.accountRepository
				.findByEmail(userEmail)
				.orElseThrow(() -> new UsernameNotFoundException(userEmail));

//		return new User(userAccount.getEmail(), userAccount.getPassword(), authorities(userAccount.getRoles()));

		return new AccountAdapter(userAccount);
	}

//	private Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
//		return roles.stream()
//				.map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
//				.collect(Collectors.toSet());
//	}
}

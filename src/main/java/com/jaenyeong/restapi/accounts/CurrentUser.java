package com.jaenyeong.restapi.accounts;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
//@AuthenticationPrincipal(expression = "account") // account getter를 사용

// 익명 유저인 경우 AccountAdapter가 아닌 anonymous 문자열이기 때문에
// @CurrentUser Account account 형태와 같은 파라미터로 받을 수 없음
// 따라서 @CurrentUser 애노테이션 속성값을 아래와 같이 변경
// @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")
public @interface CurrentUser {
}

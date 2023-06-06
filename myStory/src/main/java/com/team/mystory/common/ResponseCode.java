package com.team.mystory.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    // fail
    REQUEST_FAIL("-1" , "유효하지 않는 요청입니다."),
    RESPONSE_FAIL("-1" , "요청에 응답하지 못했습니다."),
    INVALID_TOKEN("-2" , "유효하지 않은 토큰입니다."),
    AUTHENTICATION_ERROR("-4" , "인증되지 않은 사용자입니다."),
    AUTHORIZATION_ERROR("-4" , "인가되지 않은 사용자입니다."),

    // success
    REQUEST_SUCCESS("1" , "요청이 성공적으로 완료되었습니다."),
    LOGOUT_SUCCESS("1" , "로그아웃에 성공하였습니다."),
    CREATE_ACCESS_TOKEN("2" , "Access Token 이 재발급 되었습니다.");

    private final String code;
    private final String message;
}

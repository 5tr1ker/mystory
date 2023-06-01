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

    // success
    CREATE_ACCESS_TOKEN("2" , "Access Token 이 재발급 되었습니다.");

    private final String code;
    private final String message;
}

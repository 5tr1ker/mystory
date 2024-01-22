package com.team.mystory.common.response.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailMessage {

    NOT_MATCHED_CODE("일치하지 않는 인증 코드입니다."),
    UNUSUAL_APPROACH("잘못된 접근입니다."),
    SMTP_SERVER_ERROR("메일을 전송하는 도중에 오류가 발생했습니다.");

    private final String message;

}

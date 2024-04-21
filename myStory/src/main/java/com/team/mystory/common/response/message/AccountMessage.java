package com.team.mystory.common.response.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountMessage {
    NOT_FOUNT_ACCOUNT("존재하지 않는 계정입니다."),
    EXISTS_ACCOUNT("해당 이메일로 존재하는 계정입니다."),
    EXISTS_EMAIL("이미 존재하는 이메일입니다."),
    NOT_MATCH_PASSWORD("일치하지 않는 비밀번호입니다."),
    IS_DELETE_ACCOUNT("탈퇴한 사용자입니다."),
    UNUSUAL_APPROACH("비정상적인 접근입니다.");

    private final String message;

}

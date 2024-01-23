package com.team.mystory.common.response.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum S3Message {

    INVALID_FILE("올바르지 않은 파일입니다."),
    INVALID_IMAGE("올바르지 않은 이미지입니다.");

    private final String message;

}

package com.team.mystory.common.response.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentMessage {

    ONLY_OWNER_CAN_DELETE("댓글 작성자만 제거할 수 있습니다.");

    private final String message;

}

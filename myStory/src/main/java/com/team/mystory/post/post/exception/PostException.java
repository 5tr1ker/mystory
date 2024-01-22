package com.team.mystory.post.post.exception;

import com.team.mystory.common.response.message.PostMessage;

public class PostException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PostException(PostMessage message) {
        super(message.getMessage());
    }

    public PostException(String message) {
        super(message);
    }
}

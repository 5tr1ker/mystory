package com.team.mystory.account.user.exception;

import com.team.mystory.common.response.Message;

public class LoginException extends RuntimeException {

    public LoginException(Message message) {
        super(message.toString());
    }

    public LoginException(String message) {
        super(message);
    }

}

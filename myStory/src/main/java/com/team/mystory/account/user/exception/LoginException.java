package com.team.mystory.account.user.exception;

import com.team.mystory.common.response.message.AccountMessage;

public class LoginException extends RuntimeException {

    public LoginException(AccountMessage message) {
        super(message.getMessage());
    }

    public LoginException(String message) {
        super(message);
    }

}

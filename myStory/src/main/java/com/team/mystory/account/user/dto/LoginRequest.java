package com.team.mystory.account.user.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String id;
    private String password;
    private String checkPassword;
}

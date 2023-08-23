package com.team.mystory.account.user.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String id;
    private String password;
    private String checkPassword;
}

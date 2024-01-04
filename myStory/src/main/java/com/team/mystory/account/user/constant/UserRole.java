package com.team.mystory.account.user.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "어드민"),
    MANAGER("ROLE_MANAGER", "관리자");

    private final String role;
    private final String title;

}

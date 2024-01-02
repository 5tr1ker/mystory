package com.team.mystory.admin.authority.dto;

import com.team.mystory.account.user.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class AuthorityResponse {

    private String id;

    private LocalDate joinDate;

    private LocalDate lastLoginDate;

    private LocalDate suspensionDate;

    private boolean isSuspension;

    private UserRole userRole;

}

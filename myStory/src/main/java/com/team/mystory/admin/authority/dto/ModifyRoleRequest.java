package com.team.mystory.admin.authority.dto;

import com.team.mystory.account.user.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyRoleRequest {

    private long userKey;

    private UserRole userRole;

}

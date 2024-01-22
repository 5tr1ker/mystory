package com.team.mystory.admin.login.service;

import com.team.mystory.account.user.constant.UserRole;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.dto.LoginRequest;
import com.team.mystory.account.user.exception.LoginException;
import com.team.mystory.account.user.service.LoginService;
import com.team.mystory.admin.login.dto.AdminLoginRequest;
import com.team.mystory.admin.login.dto.AdminLoginResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;

import static com.team.mystory.common.response.Message.NOT_FOUNT_ACCOUNT;

@Service
@RequiredArgsConstructor
public class AdminLoginService {

    private final LoginService loginService;

    public AdminLoginResponse adminLogin(AdminLoginRequest request, HttpServletResponse response) {
        LoginRequest loginRequest = LoginRequest.createLoginRequest(request);

        User result = loginService.login(loginRequest, response);

        if(result.getRole() != UserRole.MANAGER) {
            throw new LoginException(NOT_FOUNT_ACCOUNT);
        }

        return AdminLoginResponse.createResponse(result);
    }

}

package com.team.mystory.admin.login.service;

import com.team.mystory.account.user.constant.UserRole;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.dto.LoginRequest;
import com.team.mystory.account.user.service.LoginService;
import com.team.mystory.admin.login.dto.AdminLoginRequest;
import com.team.mystory.admin.login.dto.AdminLoginResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;

@Service
@RequiredArgsConstructor
public class AdminLoginService {

    private final LoginService loginService;

    public AdminLoginResponse adminLogin(AdminLoginRequest request, HttpServletResponse response) throws AccountException {
        LoginRequest loginRequest = LoginRequest.createLoginRequest(request);

        User user = loginService.isValidAccount(loginRequest);

        if(user.getRole() != UserRole.MANAGER) {
            throw new AccountException("찾을 수 없는 아이디입니다.");
        }

        loginService.createJwtToken(user, response);

        return AdminLoginResponse.createResponse(user);
    }

}

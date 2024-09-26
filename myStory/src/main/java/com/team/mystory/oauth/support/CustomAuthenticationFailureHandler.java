package com.team.mystory.oauth.support;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;

import static com.team.mystory.common.exception.FilterExceptionHandler.setErrorResponse;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${client.url}")
    private String clientUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        setErrorResponse(response , HttpStatus.UNAUTHORIZED.value() , exception.getMessage());
        String errorMessage = URLEncoder.encode(exception.getMessage(), "UTF-8");

        getRedirectStrategy().sendRedirect(request, response, createRedirectUrl(clientUrl + "/oauth/error?message=" + errorMessage));
    }

    public String createRedirectUrl(String url) {
        return UriComponentsBuilder.fromUriString(url).build().toUriString();
    }

}

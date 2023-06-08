package com.team.mystory.oauth.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.oauth.dto.UserSession;
import com.team.mystory.oauth.exception.OAuth2EmailNotFoundException;
import com.team.mystory.oauth.support.OAuthAttributes;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final LoginRepository loginRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuthAttributes attributes = createOauthAttributes(userRequest);

        User user = saveOrUpdateUser(attributes);
        httpSession.setAttribute("user", UserSession.of(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    public OAuthAttributes createOauthAttributes(OAuth2UserRequest userRequest) {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        return OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
    }

    public User saveOrUpdateUser(OAuthAttributes attributes) {
        String userName = getIdWithoutEmail(attributes.getEmail());
        if (userName == null) {
            throw new OAuth2EmailNotFoundException("OAuth2 계정의 이메일을 찾을 수 없거나, 이메일 수집 동의를 허용하지 않았습니다.");
        }

        return loginRepository.findById(userName)
                .orElseGet(() -> createAndSaveUser(attributes));
    }

    public User createAndSaveUser(OAuthAttributes attributes) {
        User createUser = User.createOAuthUser(getIdWithoutEmail(attributes.getEmail()));

        return loginRepository.save(createUser);
    }

    public String getIdWithoutEmail(String email) {
        return email.split("@")[0];
    }
}

package com.team.mystory.oauth.service;

import com.team.mystory.account.user.constant.UserType;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.oauth.dto.UserSession;
import com.team.mystory.oauth.support.OAuthAttributes;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final LoginRepository loginRepository;
    private final HttpSession httpSession;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuthAttributes attributes = createOauthAttributes(userRequest);

        User user = saveOrUpdateUser(attributes);
        isValidAccount(user);

        httpSession.setAttribute("user", UserSession.of(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    public void isValidAccount(User user) {
        if(user.isSuspension() && user.getSuspensionDate().compareTo(LocalDate.now()) > 0) {
            throw new OAuth2AuthenticationException(new OAuth2Error("null"), "해당 계정은 " + user.getSuspensionDate() + " 일 까지 정지입니다. \n사유 : " + user.getSuspensionReason());
        }

        if(user.getUserType() == UserType.GENERAL_USER) {
            throw new OAuth2AuthenticationException(new OAuth2Error("null"), "해당 이메일로 일반 계정이 가입되어있습니다.");
        }

        if(user.isDelete()) {
            throw new OAuth2AuthenticationException(new OAuth2Error("null"), "탈퇴한 사용자입니다.");
        }

        user.updateLoginDate();
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
        if(attributes.getEmail() == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error("null"), "계정의 이메일을 찾을 수 없거나, 이메일 수집 여부에 동의하지 않았습니다.");
        }

        return loginRepository.findByEmail(attributes.getEmail())
                .orElseGet(() -> createAndSaveUser(attributes.getEmail()));
    }

    public User createAndSaveUser(String email) {
        User createUser = User.createOAuthUser(getIdWithoutEmail(email), email);

        return loginRepository.save(createUser);
    }

    public String getIdWithoutEmail(String email) {
        return email.split("@")[0];
    }
}

package com.team.mystory.security.config;

import java.util.Arrays;

import com.team.mystory.oauth.service.CustomOAuth2UserService;
import com.team.mystory.oauth.support.OAuth2AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import com.team.mystory.security.jwt.support.JwtAuthenticationFilter;
import com.team.mystory.security.jwt.support.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomOAuth2UserService oauth2UserService;
	private final OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().
			httpBasic().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Session
				.and().authorizeRequests()
				.requestMatchers("/auth/**").authenticated()
				.anyRequest().permitAll()
                .and()
				.logout()
				.logoutUrl("/logout")
				.deleteCookies("refreshToken")
				.deleteCookies("accessToken")
				.logoutSuccessHandler((request, response, authentication) -> {
					response.sendRedirect("/logout/message");
				})
				.and()
				.oauth2Login().successHandler(oauth2AuthenticationSuccessHandler)
				.userInfoEndpoint().userService(oauth2UserService);

		http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
				UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
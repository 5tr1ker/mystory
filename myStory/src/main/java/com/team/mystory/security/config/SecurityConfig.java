package com.team.mystory.security.config;

import com.team.mystory.account.user.constant.UserRole;
import com.team.mystory.common.FilterExceptionHandler;
import com.team.mystory.oauth.service.CustomOAuth2UserService;
import com.team.mystory.oauth.support.CustomAuthenticationFailureHandler;
import com.team.mystory.oauth.support.OAuth2AuthenticationSuccessHandler;
import com.team.mystory.security.jwt.support.JwtAuthenticationFilter;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import com.team.mystory.security.support.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomOAuth2UserService oauth2UserService;
	private final OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
	private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().
			httpBasic().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeRequests()
				.requestMatchers(HttpMethod.GET , "/**").permitAll()
				.requestMatchers(HttpMethod.POST , "/logins" , "/registers" , "/oauth/token").permitAll()
				.requestMatchers(HttpMethod.PATCH , "/posts/views/**").permitAll()
				.requestMatchers(HttpMethod.DELETE , "/**").hasRole(UserRole.USER.name())
				.requestMatchers(HttpMethod.PATCH , "/**").hasRole(UserRole.USER.name())
				.requestMatchers(HttpMethod.PUT , "/**").hasRole(UserRole.USER.name())
				.requestMatchers(HttpMethod.POST , "/**").hasRole(UserRole.USER.name())
				.and()
				.logout()
				.logoutUrl("/logout")
				.deleteCookies("refreshToken")
				.deleteCookies("accessToken")
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.accessDeniedPage("/authorization/denied")
				.and()
				.oauth2Login()
				.successHandler(oauth2AuthenticationSuccessHandler)
				.failureHandler(customAuthenticationFailureHandler)
				.userInfoEndpoint().userService(oauth2UserService);

		http.addFilterBefore(new FilterExceptionHandler() ,
				UsernamePasswordAuthenticationFilter.class);

		http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
				UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
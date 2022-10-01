package noticeboard.preferences;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import noticeboard.security.JwtAuthenticationFilter;
import noticeboard.security.JwtTokenProvider;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		List<String> scopes = Arrays.asList("profile_nickname");
		return new InMemoryClientRegistrationRepository(ClientRegistration.withRegistrationId("kakao")
				.clientId("7cef126b7f8ea367330765d225a271a6").clientSecret("vmili4euIBd59hDRlRP49mbcAxgNwy8i")
				.authorizationGrantType(new AuthorizationGrantType("authorization_code"))
				.redirectUriTemplate("http://localhost:3000/noticelist").scope(scopes)
				.clientAuthenticationMethod(ClientAuthenticationMethod.POST).clientName("Kakao")
				.authorizationUri("https://kauth.kakao.com/oauth/authorize")
				.tokenUri("https://kauth.kakao.com/oauth/token").userInfoUri("https://kapi.kakao.com/v2/user/me")
				.userNameAttributeName("id").build());
	}

	@Bean
	public OAuth2AuthorizedClientService authorizedClientService() {
		return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and(). // 일반적인 루트가 아닌 다른 방식으로 요청시 거절, header에 id, pw가 아닌 token(jwt)을 달고 간다. 그래서 basic이
		// 아닌 bearer를 사용한다.
				httpBasic().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Session
				// 비활성화
				.and().authorizeRequests()// 요청에 대한 사용권한 체크
				.antMatchers("/auth/**").authenticated() // 인증이 필요한 url
				.antMatchers("/auth/**").hasRole("ADMIN") // url 접근 시 필요한 Role
				.antMatchers("/auth/**").hasRole("USER") // 보안
				.antMatchers("/**").permitAll()	// 그외 모든 권한을 허락 .anyRequest() 와동일
				.and()
				.oauth2Login()
				.clientRegistrationRepository(clientRegistrationRepository())
				.authorizedClientService(authorizedClientService())
				;
				// 소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록

		http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), // 필터
				UsernamePasswordAuthenticationFilter.class); // JwtAuthenticationFilter를
		// UsernamePasswordAuthenticationFilter 전에 넣는다

	}
	
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
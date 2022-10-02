package noticeboard.preferences;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import noticeboard.security.JwtAuthenticationFilter;
import noticeboard.security.JwtTokenProvider;
import noticeboard.security.oauth.CustomOAuth2UserService;
import noticeboard.security.oauth.OAuth2Provider;

@RequiredArgsConstructor
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";
    private static List<String> clients = Arrays.asList("google", "kakao");
    @Resource private Environment env;
    @Autowired CustomOAuth2UserService customOAuth2UserService;
	
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = clients.stream()
                .map(c -> getRegistration(c))
                .filter(registration -> registration != null)
                .collect(Collectors.toList());
        return new InMemoryClientRegistrationRepository(registrations);
    }
    
    private ClientRegistration getRegistration(String client) {
        // API Client Id 불러오기
        String clientId = env.getProperty(
                CLIENT_PROPERTY_KEY + client + ".client-id");

        // API Client Id 값이 존재하는지 확인하기
        if (clientId == null) {
            return null;
        }

        // API Client Secret 불러오기
        String clientSecret = env.getProperty(
                CLIENT_PROPERTY_KEY + client + ".client-secret");

        if (client.equals("google")) {
            return OAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build();
        }
        
        if (client.equals("kakao")) {
            return OAuth2Provider.KAKAO.getBuilder(client)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build();
        }

        return null;
    }
    
//	@Bean
//	public ClientRegistrationRepository clientRegistrationRepository() {
//		List<String> scopes = Arrays.asList("profile_nickname");
//		return new InMemoryClientRegistrationRepository(ClientRegistration.withRegistrationId("kakao")
//				.clientId("7cef126b7f8ea367330765d225a271a6").clientSecret("vmili4euIBd59hDRlRP49mbcAxgNwy8i")
//				.authorizationGrantType(new AuthorizationGrantType("authorization_code"))
//				.redirectUriTemplate("http://localhost:3000/noticelist").scope(scopes)
//				.clientAuthenticationMethod(ClientAuthenticationMethod.POST).clientName("Kakao")
//				.authorizationUri("https://kauth.kakao.com/oauth/authorize")
//				.tokenUri("https://kauth.kakao.com/oauth/token").userInfoUri("https://kapi.kakao.com/v2/user/me")
//				.userNameAttributeName("id").build());
//	}

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
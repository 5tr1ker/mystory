package com.team.mystory.security.service;

import com.team.mystory.common.response.message.AccountMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final LoginRepository loginRepository;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User result = loginRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(AccountMessage.NOT_FOUNT_ACCOUNT.getMessage()));
		
		return result;
    }
}
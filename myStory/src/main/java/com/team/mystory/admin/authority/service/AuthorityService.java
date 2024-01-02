package com.team.mystory.admin.authority.service;

import com.team.mystory.admin.authority.dto.AuthorityResponse;
import com.team.mystory.admin.authority.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public Long findNumberOfAllUsers() {
        return authorityRepository.countBy();
    }

    public List<AuthorityResponse> findAllAuthorityUser(Pageable pageable) {
        return authorityRepository.findAllAuthorityUser(pageable);
    }

}

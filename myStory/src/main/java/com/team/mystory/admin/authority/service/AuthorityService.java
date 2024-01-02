package com.team.mystory.admin.authority.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.admin.authority.dto.ModifyRoleRequest;
import com.team.mystory.admin.authority.dto.AuthorityResponse;
import com.team.mystory.admin.authority.dto.SuspensionRequest;
import com.team.mystory.admin.authority.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final LoginRepository loginRepository;

    public Long findNumberOfAllUsers() {
        return authorityRepository.countBy();
    }

    public List<AuthorityResponse> findAllAuthorityUser(Pageable pageable) {
        return authorityRepository.findAllAuthorityUser(pageable);
    }

    @Transactional
    public void modifyAuthorityUserRole(ModifyRoleRequest request) throws AccountException {
        User user = loginRepository.findById(request.getUserKey())
                .orElseThrow(() -> new AccountException("사용자 정보를 찾을 수 없습니다."));

        user.setRole(request.getUserRole());
    }

    @Transactional
    public void modifySuspensionOfUse(SuspensionRequest request) throws AccountException {
        User user = loginRepository.findById(request.getUserKey())
                .orElseThrow(() -> new AccountException("사용자 정보를 찾을 수 없습니다."));

        modifySuspensionDate(user, request.getSuspensionDate());
    }

    private void modifySuspensionDate(User user, int date) {
        if(date > 0) {
            user.addSuspensionDate(date);

            return;
        }

        user.minusSuspensionDate(date * -1);
    }
}

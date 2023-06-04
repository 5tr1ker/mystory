package com.team.mystory.account.user.repository;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.dto.UserResponse;

import java.util.Optional;

public interface CustomLoginRepository {
    Optional<UserResponse> findUserResponseById(String id);
}

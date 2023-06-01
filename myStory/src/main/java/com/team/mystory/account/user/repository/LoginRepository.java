package com.team.mystory.account.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team.mystory.account.user.domain.IdInfo;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<IdInfo, Long> {
	Optional<IdInfo> findById(String id);
}

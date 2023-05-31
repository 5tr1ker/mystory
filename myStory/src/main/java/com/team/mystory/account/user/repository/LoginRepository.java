package com.team.mystory.account.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team.mystory.account.user.domain.IdInfo;

public interface LoginRepository extends JpaRepository<IdInfo, Long> {
	public IdInfo findById(String id);
}

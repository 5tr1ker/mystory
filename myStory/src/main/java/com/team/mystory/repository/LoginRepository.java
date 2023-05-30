package com.team.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team.mystory.entity.userdata.IdInfo;

public interface LoginRepository extends JpaRepository<IdInfo, Long> {
	public IdInfo findById(String id);
}

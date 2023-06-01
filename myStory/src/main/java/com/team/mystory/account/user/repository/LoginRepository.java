package com.team.mystory.account.user.repository;

import com.team.mystory.account.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<User, Long> {
	Optional<User> findById(String id);
}

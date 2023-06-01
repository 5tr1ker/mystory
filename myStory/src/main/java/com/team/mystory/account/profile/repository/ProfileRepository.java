package com.team.mystory.account.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team.mystory.account.profile.domain.ProfileSetting;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileSetting, Long> , CustomProfileRepository {

}

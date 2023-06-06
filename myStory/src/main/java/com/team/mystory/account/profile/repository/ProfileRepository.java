package com.team.mystory.account.profile.repository;

import com.team.mystory.account.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> , CustomProfileRepository {

}

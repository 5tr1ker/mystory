package com.team.mystory.account.profile.repository;

import com.team.mystory.account.profile.domain.ProfileSetting;
import com.team.mystory.account.profile.dto.StatisticsResponse;

import java.util.Optional;

public interface CustomProfileRepository {
    Optional<ProfileSetting> findProfileByUserId(String userId);

    StatisticsResponse getStatisticsOfUser(String userId);
}

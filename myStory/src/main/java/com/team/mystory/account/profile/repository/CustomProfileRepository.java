package com.team.mystory.account.profile.repository;

import com.team.mystory.account.profile.domain.Profile;
import com.team.mystory.account.profile.dto.StatisticsResponse;

import java.util.Optional;

public interface CustomProfileRepository {

    StatisticsResponse getStatisticsOfUser(String userId);

}

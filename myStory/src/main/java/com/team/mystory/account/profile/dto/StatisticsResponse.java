package com.team.mystory.account.profile.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StatisticsResponse {
    private long totalPost;
    private long totalComment;
    private long totalPostView;
    private String joinData;
}

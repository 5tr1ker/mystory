package com.team.mystory.meeting.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantResponse {

    private long userKey;

    private String userId;

    private String profileImage;

}

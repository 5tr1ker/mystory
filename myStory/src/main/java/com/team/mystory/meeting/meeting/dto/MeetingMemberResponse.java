package com.team.mystory.meeting.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MeetingMemberResponse {
    private long userKey;

    private String userId;

    private String profileImage;

    private List<ParticipantResponse> participantResponses;

    public MeetingMemberResponse(long userKey , String userId , String profileImage) {
        this.userKey = userKey;
        this.userId = userId;
        this.profileImage = profileImage;
    }

    public void setParticipantResponses(List<ParticipantResponse> participantResponses) {
        this.participantResponses = participantResponses;
    }
}

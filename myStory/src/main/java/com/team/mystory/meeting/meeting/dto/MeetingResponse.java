package com.team.mystory.meeting.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingResponse {

    private long meetingId;

    private double locateX;

    private double locateY;

    private String address;

    private String meetingImage;

    // !-
    private String detailAddress;

    private String description;

    private String title;

    private int maxParticipants;

    private long nowParticipants;

}

package com.team.mystory.meeting.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRequest {

    private double locateX;

    private double locateY;

    private String address;

    private String title;

    private String description;

}

package com.team.mystory.meeting.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    private Date meetingDate;

    private String meetingAddress;

    private String meetingLocateX;

    private String meetingLocateY;

}

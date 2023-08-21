package com.team.mystory.meeting.reservation.dto;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.dto.UserResponse;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {

    private long reservationId;

    private List<UserResponse> participates = new ArrayList<>();

    private Date meetingDate;

    private String meetingAddress;

    private String meetingLocateX;

    private String meetingLocateY;

}

package com.team.mystory.meeting.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.dto.UserResponse;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {

    private long reservationId;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy년MM월dd일 HH시MM분" , timezone = "Asia/Seoul")
    private LocalDateTime date;

    private String address;

    private String description;

    private String detailAddress;

    private String locateX;

    private String locateY;

    private int maxParticipants;

    private long userCount;

}

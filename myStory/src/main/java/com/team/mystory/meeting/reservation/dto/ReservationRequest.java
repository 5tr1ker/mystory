package com.team.mystory.meeting.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd'T'HH:mm" , timezone = "Asia/Seoul")
    private LocalDateTime date;

    private String address;

    private String detailAddress;

    private String locateX;

    private String locateY;

    private int maxParticipants;

}

package com.team.mystory.meeting.reservation.entity;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.meeting.reservation.dto.ReservationRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "participates")
    private List<User> participates = new ArrayList<>();

    private Date meetingDate;

    private String meetingAddress;

    private String meetingLocateX;

    private String meetingLocateY;

    public static Reservation createReservation(ReservationRequest request) {
        return Reservation.builder()
                .meetingDate(request.getMeetingDate())
                .meetingAddress(request.getMeetingAddress())
                .meetingLocateX(request.getMeetingLocateX())
                .meetingLocateY(request.getMeetingLocateY())
                .build();
    }

    public void leaveReservation(User user) {
        participates.remove(user);
    }

}

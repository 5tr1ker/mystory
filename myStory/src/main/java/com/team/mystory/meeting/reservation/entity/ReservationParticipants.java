package com.team.mystory.meeting.reservation.entity;

import com.team.mystory.account.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationParticipants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long reservationParticipantsId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    public static ReservationParticipants createReservationParticipants(User user , Reservation reservation) {
        return ReservationParticipants.builder()
                .user(user)
                .reservation(reservation)
                .build();
    }

}

package com.team.mystory.meeting.reservation.entity;

import com.team.mystory.meeting.meeting.domain.Meeting;
import com.team.mystory.meeting.reservation.dto.ReservationRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @OneToMany(cascade = CascadeType.PERSIST , mappedBy = "reservation")
    private List<ReservationParticipants> participates = new ArrayList<>();

    @ManyToOne
    @Setter
    private Meeting meetings;

    private LocalDateTime date;

    private String address;

    private String description;

    private String detailAddress;

    private String locateX;

    private String locateY;

    private int maxParticipants;

    public static Reservation createReservation(ReservationRequest request) {
        return Reservation.builder()
                .date(request.getDate())
                .address(request.getAddress())
                .detailAddress(request.getDetailAddress())
                .description(request.getDescription())
                .locateX(request.getLocateX())
                .locateY(request.getLocateY())
                .maxParticipants(request.getMaxParticipants())
                .build();
    }

}

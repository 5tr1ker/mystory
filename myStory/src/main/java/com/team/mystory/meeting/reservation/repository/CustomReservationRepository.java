package com.team.mystory.meeting.reservation.repository;

import com.team.mystory.meeting.reservation.dto.ReservationResponse;
import com.team.mystory.meeting.reservation.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface CustomReservationRepository {

    List<ReservationResponse> findAllReservationByMeetingId(long meetingId);

    long deleteReservation(long reservationId , long userKey);

    Optional<Reservation> findReservationBeforeExpiry(long reservationId);
    
}

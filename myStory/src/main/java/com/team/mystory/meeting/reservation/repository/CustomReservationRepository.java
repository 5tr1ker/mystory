package com.team.mystory.meeting.reservation.repository;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.meeting.meeting.dto.ParticipantResponse;
import com.team.mystory.meeting.reservation.dto.ReservationResponse;
import com.team.mystory.meeting.reservation.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface CustomReservationRepository {

    List<ReservationResponse> findAllReservationByMeetingId(long meetingId);

    Optional<Reservation> findReservationByIdAndUser(long reservationId , User userData);

    Optional<Reservation> findReservationBeforeExpiry(long reservationId , String userPk);

    long leaveReservationById(long reservationId , String userPk);

    Optional<Reservation> findReservationAndParticipantsById(long reservationId);

    List<ParticipantResponse> findParticipantsById(long reservationId);

}

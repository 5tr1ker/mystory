package com.team.mystory.meeting.reservation.repository;

import com.team.mystory.meeting.reservation.entity.ReservationParticipants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationParticipantsRepository extends JpaRepository<ReservationParticipants , Long> {
}

package com.team.mystory.meeting.reservation.repository;

import com.team.mystory.meeting.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation , Long> , CustomReservationRepository {
}

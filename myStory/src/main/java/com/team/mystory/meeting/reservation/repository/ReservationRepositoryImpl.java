package com.team.mystory.meeting.reservation.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.account.user.dto.UserResponse;
import com.team.mystory.meeting.meeting.domain.QMeeting;
import com.team.mystory.meeting.reservation.dto.ReservationRequest;
import com.team.mystory.meeting.reservation.dto.ReservationResponse;
import com.team.mystory.meeting.reservation.entity.QReservation;
import com.team.mystory.meeting.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;

import static com.team.mystory.account.user.domain.QUser.user;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.mystory.meeting.meeting.domain.QMeeting.meeting;
import static com.team.mystory.meeting.reservation.entity.QReservation.reservation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements CustomReservationRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReservationResponse> findAllReservationByMeetingId(long meetingId) {
        return jpaQueryFactory.select(Projections.constructor(
                        ReservationResponse.class,
                        reservation.reservationId,
                        select(Projections.constructor(
                                UserResponse.class,
                                user.userKey,
                                user.id,
                                user.password,
                                user.joinDate,
                                null
                        )).from(user).innerJoin(reservation.participates),
                        reservation.meetingDate,
                        reservation.meetingAddress,
                        reservation.meetingLocateX,
                        reservation.meetingLocateY
                )).from(reservation)
                .innerJoin(meeting.reservations, reservation)
                .orderBy(reservation.reservationId.desc())
                .fetch();
    }

    @Override
    public long deleteReservation(long reservationId, long userKey) {
        return jpaQueryFactory.delete(reservation)
                .where(reservation.reservationId.eq(select(reservation.reservationId)
                        .from(meeting)
                        .innerJoin(meeting.reservations , reservation).on(reservation.reservationId.eq(reservationId))
                        .where(meeting.meetingOwner.userKey.eq(userKey))
                        .fetchOne()))
                .execute();
    }

    @Override
    public Optional<Reservation> findReservationBeforeExpiry(long reservationId) {
        Date currentTime = new Date();

        Reservation result = jpaQueryFactory.select(reservation)
                .from(reservation)
                .where(reservation.reservationId.eq(reservationId).and(reservation.meetingDate.gt(currentTime)))
                .fetchOne();

        return Optional.of(result);
    }

}

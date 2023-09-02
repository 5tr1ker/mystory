package com.team.mystory.meeting.reservation.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.account.user.dto.UserResponse;
import com.team.mystory.meeting.meeting.domain.QMeeting;
import com.team.mystory.meeting.meeting.dto.ParticipantResponse;
import com.team.mystory.meeting.reservation.dto.ReservationRequest;
import com.team.mystory.meeting.reservation.dto.ReservationResponse;
import com.team.mystory.meeting.reservation.entity.QReservation;
import com.team.mystory.meeting.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.team.mystory.account.user.domain.QUser.user;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.mystory.meeting.meeting.domain.QMeeting.meeting;
import static com.team.mystory.meeting.reservation.entity.QReservationParticipants.reservationParticipants;
import static com.team.mystory.meeting.reservation.entity.QReservation.reservation;

import java.time.LocalDateTime;
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
                        reservation.date,
                        reservation.address,
                        reservation.description,
                        reservation.detailAddress,
                        reservation.locateX,
                        reservation.locateY,
                        reservation.maxParticipants,
                        reservationParticipants.count()
                )).from(reservation)
                .innerJoin(reservation.meetings, meeting).on(meeting.meetingId.eq(meetingId))
                .leftJoin(reservation.participates , reservationParticipants)
                .groupBy(reservation.reservationId)
                .orderBy(reservation.date.desc())
                .fetch();
    }

    @Override
    public Optional<Reservation> findReservationByIdAndUserId(long reservationId, String userKey) {
        Reservation result = jpaQueryFactory.select(reservation).from(reservationParticipants)
                .innerJoin(reservationParticipants.user , user).on(user.id.eq(userKey))
                .innerJoin(reservationParticipants.reservation , reservation).on(reservation.reservationId.eq(reservationId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Reservation> findReservationBeforeExpiry(long reservationId , String userPk) {
        LocalDateTime currentTime = LocalDateTime.now();

        Reservation result = jpaQueryFactory.select(reservation)
                .from(reservationParticipants)
                .innerJoin(reservationParticipants.reservation , reservation).on(reservation.reservationId.eq(reservationId).and(reservation.date.gt(currentTime)))
                .innerJoin(reservationParticipants.user , user).on(user.id.eq(userPk))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public long leaveReservationById(long reservationId, String userPk) {
        long result = jpaQueryFactory.select(reservationParticipants.reservationParticipantsId).from(reservationParticipants)
                .innerJoin(reservationParticipants.user , user).on(user.id.eq(userPk))
                .innerJoin(reservationParticipants.reservation , reservation).on(reservation.reservationId.eq(reservationId))
                .fetchOne();

        return jpaQueryFactory.delete(reservationParticipants)
                .where(reservationParticipants.reservationParticipantsId.eq(result))
                .execute();
    }

    @Override
    public Optional<Reservation> findReservationAndParticipantsById(long reservationId) {
        Reservation result = jpaQueryFactory.select(reservation).from(reservation)
                .leftJoin(reservation.participates).fetchJoin()
                .where(reservation.reservationId.eq(reservationId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<ParticipantResponse> findParticipantsById(long reservationId) {
        return jpaQueryFactory.select(Projections.constructor(ParticipantResponse.class,
                        user.userKey,
                        user.id,
                        user.profileImage
                )).from(reservationParticipants)
                .innerJoin(reservationParticipants.user , user)
                .innerJoin(reservationParticipants.reservation , reservation).on(reservation.reservationId.eq(reservationId))
                .fetch();
    }

}

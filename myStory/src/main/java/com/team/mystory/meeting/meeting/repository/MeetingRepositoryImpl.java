package com.team.mystory.meeting.meeting.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.team.mystory.meeting.meeting.domain.MeetingParticipant;
import com.team.mystory.meeting.meeting.domain.QMeeting;
import com.team.mystory.meeting.meeting.dto.MeetingMemberResponse;
import com.team.mystory.meeting.meeting.dto.MeetingResponse;
import com.team.mystory.meeting.meeting.dto.ParticipantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.mystory.account.user.domain.QUser.user;
import static com.team.mystory.meeting.meeting.domain.QMeetingParticipant.meetingParticipant;
import static com.team.mystory.meeting.meeting.domain.QMeeting.meeting;

@RequiredArgsConstructor
public class MeetingRepositoryImpl implements CustomMeetingRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MeetingResponse> findAllMeeting(Pageable pageable) {
        QMeeting meeting1 = new QMeeting("meeting1");

        return jpaQueryFactory.select(Projections.constructor(
                        MeetingResponse.class,
                        meeting.meetingId,
                        meeting.locateX,
                        meeting.locateY,
                        meeting.address,
                        meeting.meetingImage,
                        meeting.detailAddress,
                        meeting.description,
                        meeting.title,
                        meeting.maxParticipants,
                        select(count(meetingParticipant)).from(meetingParticipant)
                                .innerJoin(meetingParticipant.meetingList, meeting1).on(meeting1.meetingId.eq(meeting.meetingId))
                )).from(meeting)
                .orderBy(meeting.meetingId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Optional<MeetingResponse> findMeetingByMeetingId(long meetingId) {
        QMeeting meeting1 = new QMeeting("meeting1");

        MeetingResponse result = jpaQueryFactory.select(Projections.constructor(
                        MeetingResponse.class,
                        meeting.meetingId,
                        meeting.locateX,
                        meeting.locateY,
                        meeting.address,
                        meeting.meetingImage,
                        meeting.detailAddress,
                        meeting.description,
                        meeting.title,
                        meeting.maxParticipants,
                        select(count(meetingParticipant)).from(meetingParticipant)
                                .innerJoin(meetingParticipant.meetingList, meeting1).on(meeting1.meetingId.eq(meeting.meetingId))
                )).from(meeting)
                .where(meeting.meetingId.eq(meetingId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<MeetingParticipant> findMeetingParticipantByMeetingIdAndUserId(long meetingId, String userId) {
        MeetingParticipant result = jpaQueryFactory.select(meetingParticipant)
                .from(meetingParticipant)
                .innerJoin(meetingParticipant.meetingList, meeting).on(meeting.meetingId.eq(meetingId))
                .innerJoin(meetingParticipant.userList, user).on(user.id.eq(userId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public long findAllMeetingCount() {
        return jpaQueryFactory.select(meeting.count()).from(meeting)
                .orderBy(meeting.meetingId.desc())
                .fetchOne();
    }

    @Override
    public List<MeetingResponse> findMeetingByTitleOrAddress(Pageable pageable, String data) {
        QMeeting meeting1 = new QMeeting("meeting1");

        return jpaQueryFactory.select(Projections.constructor(
                        MeetingResponse.class,
                        meeting.meetingId,
                        meeting.locateX,
                        meeting.locateY,
                        meeting.address,
                        meeting.meetingImage,
                        meeting.detailAddress,
                        meeting.description,
                        meeting.title,
                        meeting.maxParticipants,
                        select(count(meetingParticipant)).from(meetingParticipant)
                                .innerJoin(meetingParticipant.meetingList, meeting1).on(meeting1.meetingId.eq(meeting.meetingId))
                )).from(meeting)
                .where(meeting.title.contains(data).or(meeting.address.contains(data)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public long findMeetingByTitleOrAddressCount(String data) {
        return jpaQueryFactory.select(meeting.count()).from(meeting)
                .where(meeting.title.contains(data).or(meeting.address.contains(data)))
                .fetchOne();
    }

    @Override
    public List<MeetingResponse> findAllMeetingByUserId(Pageable pageable, String userId) {
        QMeeting meeting1 = new QMeeting("meeting1");

        return jpaQueryFactory.select(Projections.constructor(
                        MeetingResponse.class,
                        meeting.meetingId,
                        meeting.locateX,
                        meeting.locateY,
                        meeting.address,
                        meeting.meetingImage,
                        meeting.detailAddress,
                        meeting.description,
                        meeting.title,
                        meeting.maxParticipants,
                        select(count(meetingParticipant)).from(meetingParticipant)
                                .innerJoin(meetingParticipant.meetingList, meeting1).on(meeting1.meetingId.eq(meeting.meetingId))
                )).from(meetingParticipant)
                .innerJoin(meetingParticipant.userList, user).on(user.id.eq(userId))
                .innerJoin(meetingParticipant.meetingList, meeting)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public long findAllMeetingByUserIdCount(String userId) {
        return jpaQueryFactory.select(meeting.count()).from(meetingParticipant)
                .innerJoin(meetingParticipant.userList, user).on(user.id.eq(userId))
                .innerJoin(meetingParticipant.meetingList, meeting)
                .fetchOne();
    }

    @Override
    public List<ParticipantResponse> findParticipantsByMeetingId(long meetingId) {
        return jpaQueryFactory.select(Projections.constructor(
                        ParticipantResponse.class,
                        user.userKey,
                        user.id,
                        user.profileImage
                        )).from(meetingParticipant)
                .innerJoin(meetingParticipant.meetingList, meeting).on(meeting.meetingId.eq(meetingId))
                .innerJoin(meetingParticipant.userList , user)
                .fetch();
    }

    @Override
    public MeetingMemberResponse findMeetingOwnerByMeetingId(long meetingId) {
        return jpaQueryFactory.select(Projections.constructor(
                        MeetingMemberResponse.class,
                        user.userKey,
                        user.id,
                        user.profileImage
                )).from(meeting)
                .innerJoin(meeting.meetingOwner , user)
                .where(meeting.meetingId.eq(meetingId))
                .fetchOne();
    }

    @Override
    public long deleteParticipantsByMeetingIdAndUserId(long meetingId, String userId) {
        long id = jpaQueryFactory.select(meetingParticipant.meetingParticipantId).from(meetingParticipant)
                .innerJoin(meetingParticipant.userList , user).on(user.id.eq(userId))
                .innerJoin(meetingParticipant.meetingList , meeting).on(meeting.meetingId.eq(meetingId)).fetchOne();

        return jpaQueryFactory.delete(meetingParticipant)
                .where(meetingParticipant.meetingParticipantId.eq(id)).execute();
    }

}

package com.team.mystory.meeting.meeting.repository.meeting;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.meeting.meeting.domain.Meeting;
import com.team.mystory.meeting.meeting.domain.QMeeting;
import com.team.mystory.meeting.meeting.dto.MeetingMemberResponse;
import com.team.mystory.meeting.meeting.dto.MeetingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.mystory.account.user.domain.QUser.user;
import static com.team.mystory.meeting.meeting.domain.QMeeting.meeting;
import static com.team.mystory.meeting.meeting.domain.QMeetingParticipant.meetingParticipant;

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
                .where(meeting.isDelete.eq(false))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Optional<Meeting> findMeetingByMeetingIdAndMeetingOwner(long meetingId, String userId) {
        Meeting result = jpaQueryFactory.select(meeting).from(meeting)
                .innerJoin(meeting.meetingOwner , user).on(user.id.eq(userId))
                .where(meeting.meetingId.eq(meetingId).and(meeting.isDelete.eq(false)))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Meeting> findMeetingAndChatById(long meetingId) {
        Meeting result = jpaQueryFactory.select(meeting).from(meeting)
                .leftJoin(meeting.chats).fetchJoin()
                .where(meeting.meetingId.eq(meetingId).and(meeting.isDelete.eq(false)))
                .fetchOne();

        return Optional.ofNullable(result);
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
                .where(meeting.meetingId.eq(meetingId).and(meeting.isDelete.eq(false)))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public long countAllMeeting() {
        return jpaQueryFactory.select(meeting.count()).from(meeting)
                .orderBy(meeting.meetingId.desc())
                .where(meeting.isDelete.eq(false))
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
                .where(meeting.title.contains(data).or(meeting.address.contains(data)).and(meeting.isDelete.eq(false)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public long countMeetingByTitleOrAddress(String data) {
        return jpaQueryFactory.select(meeting.count()).from(meeting)
                .where(meeting.title.contains(data).or(meeting.address.contains(data)).and(meeting.isDelete.eq(false)))
                .fetchOne();
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
                .where(meeting.meetingId.eq(meetingId).and(meeting.isDelete.eq(false)))
                .fetchOne();
    }

}

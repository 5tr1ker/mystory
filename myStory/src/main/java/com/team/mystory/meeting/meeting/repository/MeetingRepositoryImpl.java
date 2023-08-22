package com.team.mystory.meeting.meeting.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.meeting.meeting.domain.QMeetingParticipant;
import com.team.mystory.meeting.meeting.dto.MeetingResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.team.mystory.account.user.domain.QUser.user;
import static com.team.mystory.meeting.meeting.domain.QMeetingParticipant.meetingParticipant;
import static com.team.mystory.meeting.meeting.domain.QMeeting.meeting;

@RequiredArgsConstructor
public class MeetingRepositoryImpl implements CustomMeetingRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MeetingResponse> findAllMeeting() {
        return jpaQueryFactory.select(Projections.constructor(
                MeetingResponse.class,
                meeting.meetingId,
                meeting.locateX,
                meeting.locateY,
                meeting.address,
                meeting.meetingImage
        )).from(meeting)
                .orderBy(meeting.meetingId.desc())
                .fetch();
    }

    @Override
    public List<MeetingResponse> findMeetingByAddress(String address) {
        return jpaQueryFactory.select(Projections.constructor(
                        MeetingResponse.class,
                        meeting.meetingId,
                        meeting.locateX,
                        meeting.locateY,
                        meeting.address,
                        meeting.meetingImage
                )).from(meeting)
                .where(meeting.address.startsWith(address))
                .fetch();
    }

    @Override
    public List<MeetingResponse> findMeetingByTitle(String title) {
        return jpaQueryFactory.select(Projections.constructor(
                        MeetingResponse.class,
                        meeting.meetingId,
                        meeting.locateX,
                        meeting.locateY,
                        meeting.address,
                        meeting.meetingImage
                )).from(meeting)
                .where(meeting.title.like(title))
                .fetch();
    }

    @Override
    public List<MeetingResponse> getMeetingsParticipantIn(String userId) {
        return jpaQueryFactory.select(Projections.constructor(
                        MeetingResponse.class,
                        meeting.meetingId,
                        meeting.locateX,
                        meeting.locateY,
                        meeting.address,
                        meeting.meetingImage
                )).from(meetingParticipant)
                .innerJoin(meetingParticipant.userList , user).on(user.id.eq(userId))
                .innerJoin(meetingParticipant.meetingList , meeting)
                .fetch();
    }

    @Override
    public long deleteMeetingById(String userPk, long meetingId) {
        return jpaQueryFactory.delete(meeting)
                .where(meetingParticipant.meetingList.meetingId.eq(meetingId)
                        .and(meetingParticipant.userList.id.eq(userPk)))
                .execute();
    }

}

package com.team.mystory.meeting.meeting.repository.participant;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.mystory.account.user.domain.User;
import com.team.mystory.meeting.meeting.domain.MeetingParticipant;
import com.team.mystory.meeting.meeting.domain.QMeeting;
import com.team.mystory.meeting.meeting.dto.MeetingResponse;
import com.team.mystory.meeting.meeting.dto.ParticipantResponse;
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
public class MeetingParticipantRepositoryImpl implements CustomMeetingParticipantRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<MeetingParticipant> findMeetingParticipantByMeetingIdAndUser(long meetingId, User userData) {
        MeetingParticipant result = jpaQueryFactory.select(meetingParticipant)
                .from(meetingParticipant)
                .innerJoin(meetingParticipant.meetingList, meeting).on(meeting.meetingId.eq(meetingId))
                .innerJoin(meetingParticipant.userList, user).on(user.eq(userData))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<MeetingResponse> findAllMeetingByParticipant(Pageable pageable, String userId) {
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
                .orderBy(meeting.meetingId.desc())
                .where(meeting.isDelete.eq(false))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public long countAllMeetingByParticipant(String userId) {
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
    public long deleteParticipantsByMeetingIdAndUserId(long meetingId, String userId) {
        long id = jpaQueryFactory.select(meetingParticipant.meetingParticipantId).from(meetingParticipant)
                .innerJoin(meetingParticipant.userList , user).on(user.id.eq(userId))
                .innerJoin(meetingParticipant.meetingList , meeting).on(meeting.meetingId.eq(meetingId)).fetchOne();

        return jpaQueryFactory.delete(meetingParticipant)
                .where(meetingParticipant.meetingParticipantId.eq(id)).execute();
    }

}

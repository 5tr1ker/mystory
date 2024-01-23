package com.team.mystory.meeting.chat.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.team.mystory.account.user.domain.QUser.user;

import com.team.mystory.account.user.domain.QUser;
import com.team.mystory.meeting.chat.dto.ChatResponse;
import com.team.mystory.meeting.chat.dto.ChatRoomResponse;
import com.team.mystory.meeting.chat.entity.QChat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.team.mystory.meeting.meeting.domain.QMeetingParticipant.meetingParticipant;
import static com.team.mystory.meeting.chat.entity.QChatRoom.chatRoom;
import static com.team.mystory.meeting.meeting.domain.QMeeting.meeting;
import static com.team.mystory.meeting.chat.entity.QChat.chat;

@RequiredArgsConstructor
public class ChatRepositoryImpl implements CustomChatRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChatResponse> findChatDataByMeetingId(long meetingId) {
        return jpaQueryFactory.select(Projections.constructor(
                        ChatResponse.class,
                        chat.chatId,
                        chat.sender,
                        chat.message,
                        chat.sendTime,
                        chat.senderImage
                )).from(chat)
                .innerJoin(chat.meetingId , meeting).on(meeting.meetingId.eq(meetingId))
                .fetch();
    }

    @Override
    public List<ChatRoomResponse> findChatRoomByUserId(String userId) {
        return jpaQueryFactory.select(Projections.constructor(
                        ChatRoomResponse.class,
                        chatRoom.chatId,
                        meeting.meetingId,
                        meeting.title,
                        meeting.meetingImage,
                        chatRoom.createDate
                )).from(meetingParticipant)
                .innerJoin(meetingParticipant.userList, user).on(user.id.eq(userId))
                .innerJoin(meetingParticipant.meetingList, meeting).on(meeting.isDelete.eq(false))
                .innerJoin(meeting.chatRoom, chatRoom)
                .fetch();
    }
}

package com.team.mystory.meeting.chat.service;

import com.team.mystory.meeting.chat.dto.ChatResponse;
import com.team.mystory.meeting.chat.dto.ChatRoomResponse;
import com.team.mystory.meeting.chat.entity.ChatRoom;
import com.team.mystory.meeting.chat.repository.ChatRepository;
import com.team.mystory.meeting.meeting.domain.Meeting;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void createChatRoom(Meeting meeting) {
        ChatRoom chatRoom = ChatRoom.createChatRoom(meeting);

        meeting.setChatRoom(chatRoom);
    }

    public List<ChatRoomResponse> findChattingRoomByUserId(String accessToken) {
        String userId = jwtTokenProvider.getUserPk(accessToken);

        return chatRepository.findChatRoomByUserId(userId);
    }

    public List<ChatResponse> findChatDataByMeetingId(long meetingId) {
        return chatRepository.findChatDataByMeetingId(meetingId);
    }

}

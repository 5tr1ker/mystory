package com.team.mystory.meeting.chat.repository;

import com.team.mystory.meeting.chat.dto.ChatResponse;
import com.team.mystory.meeting.chat.dto.ChatRoomResponse;

import java.util.List;

public interface CustomChatRepository {

    List<ChatResponse> findChatDataByMeetingId(long meetingId);

    List<ChatRoomResponse> findChatRoomByUserId(String userId);

}

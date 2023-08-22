package com.team.mystory.meeting.chat.repository;

import com.team.mystory.meeting.chat.dto.ChatResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomChatRepository {

    List<ChatResponse> findChatsByMeetingId(Pageable pageable , long meetingId);

}

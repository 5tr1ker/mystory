package com.team.mystory.meeting.chat.controller;

import com.team.mystory.meeting.chat.dto.ChatResponse;
import com.team.mystory.meeting.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatRepository chatRepository;

    @GetMapping("/{meetingId}")
    public ResponseEntity getPreviousChats(Pageable pageable , @PathVariable long meetingId) {
        List<ChatResponse> result = chatRepository.findChatsByMeetingId(pageable , meetingId);

        return ResponseEntity.ok().body(result);
    }

}

package com.team.mystory.meeting.chat.controller;

import com.team.mystory.meeting.chat.dto.ChatResponse;
import com.team.mystory.meeting.chat.dto.ChatRoomResponse;
import com.team.mystory.meeting.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/{meetingId}")
    public ResponseEntity findChatDataById(@PathVariable long meetingId) {
        List<ChatResponse> result = chatService.findChatDataByMeetingId(meetingId);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/participants")
    public ResponseEntity findChattingRoomByUserId(@CookieValue String accessToken) {
        List<ChatRoomResponse> result = chatService.findChattingRoomByUserId(accessToken);

        return ResponseEntity.ok().body(result);
    }

}

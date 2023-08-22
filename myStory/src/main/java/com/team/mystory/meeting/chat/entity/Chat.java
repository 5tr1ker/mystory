package com.team.mystory.meeting.chat.entity;

import com.team.mystory.meeting.chat.dto.ChatRequest;
import com.team.mystory.meeting.chat.dto.ChatResponse;
import com.team.mystory.meeting.meeting.domain.Meeting;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Meeting meetingId;

    private String message;

    private String sendTime;

    private String sender;

    public static Chat createChat(ChatResponse chatResponse) {
        return Chat.builder()
                .message(chatResponse.getMessage())
                .sendTime(chatResponse.getSendTime())
                .sender(chatResponse.getSender())
                .build();
    }
}

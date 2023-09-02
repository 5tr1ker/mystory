package com.team.mystory.meeting.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatResponse {

    private long chatId;

    private String sender;

    private String message;

    private String sendTime;

    private String senderImage;

    public static ChatResponse createChatResponse(ChatRequest chatMessage) {
        return ChatResponse.builder()
                .sender(chatMessage.getSender())
                .sendTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .message(chatMessage.getMessage())
                .senderImage(chatMessage.getSenderImage())
                .build();
    }

}

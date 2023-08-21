package com.team.mystory.meeting.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {

    private String sender;

    private String message;

    private long meetingId;

}

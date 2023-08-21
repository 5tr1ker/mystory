package com.team.mystory.meeting.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;

@Builder
public class ChatResponse {

    private String userName;

    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:MM" , timezone = "Asia/Seoul")
    private Date sendTime;

}

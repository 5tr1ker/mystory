package com.team.mystory.meeting.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatRoomResponse {
    private long chatId;

    private long meetingId;

    private String meetingTitle;

    private String meetingImage;

    private LocalDate createDate;

}

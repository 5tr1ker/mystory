package com.team.mystory.meeting.meeting.exception;

import com.team.mystory.common.response.message.MeetingMessage;

public class MeetingException extends RuntimeException {

    public MeetingException(String message) {
        super(message);
    }

    public MeetingException(MeetingMessage message) {
        super(message.getMessage());
    }

}

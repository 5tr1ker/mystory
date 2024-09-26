package com.team.mystory.meeting.reservation.exception;

import com.team.mystory.common.response.message.MeetingMessage;

public class ReservationException extends RuntimeException {

    public ReservationException(String message) {
        super(message);
    }

    public ReservationException(MeetingMessage message) {
        super(message.getMessage());
    }
}

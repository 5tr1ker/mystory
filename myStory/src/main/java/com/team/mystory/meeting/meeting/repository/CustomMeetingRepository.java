package com.team.mystory.meeting.meeting.repository;

import com.team.mystory.meeting.meeting.dto.MeetingResponse;

import java.util.List;

public interface CustomMeetingRepository {

    List<MeetingResponse> findAllMeeting();

    List<MeetingResponse> findMeetingByAddress(String address);

    List<MeetingResponse> findMeetingByTitle(String title);

    long deleteMeetingById(String userPk, long meetingId);
}

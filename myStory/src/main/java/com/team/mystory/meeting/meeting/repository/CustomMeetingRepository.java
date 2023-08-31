package com.team.mystory.meeting.meeting.repository;

import com.team.mystory.meeting.meeting.dto.MeetingResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomMeetingRepository {

    List<MeetingResponse> findAllMeeting(Pageable pageable);

    long findAllMeetingCount();

    List<MeetingResponse> findMeetingByTitleOrAddress(Pageable pageable , String data);

    long findMeetingByTitleOrAddressCount(String data);

    List<MeetingResponse> findAllMeetingByUserId(Pageable pageable , String userId);

    long findAllMeetingByUserIdCount(String userId);


    List<MeetingResponse> getMeetingsParticipantIn(String userId);

    long deleteMeetingById(String userPk, long meetingId);
}

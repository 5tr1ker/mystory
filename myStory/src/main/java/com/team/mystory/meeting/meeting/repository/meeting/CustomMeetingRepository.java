package com.team.mystory.meeting.meeting.repository.meeting;

import com.team.mystory.meeting.meeting.domain.Meeting;
import com.team.mystory.meeting.meeting.dto.MeetingMemberResponse;
import com.team.mystory.meeting.meeting.dto.MeetingResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomMeetingRepository {

    List<MeetingResponse> findAllMeeting(Pageable pageable);

    Optional<Meeting> findMeetingByMeetingIdAndMeetingOwner(long meetingId , String userId);

    Optional<Meeting> findMeetingAndChatById(long meetingId);

    Optional<MeetingResponse> findMeetingByMeetingId(long meetingId);

    long countAllMeeting();

    List<MeetingResponse> findMeetingByTitleOrAddress(Pageable pageable , String data);

    long countMeetingByTitleOrAddress(String data);

    MeetingMemberResponse findMeetingOwnerByMeetingId(long meetingId);

}

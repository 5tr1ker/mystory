package com.team.mystory.meeting.meeting.repository;

import com.team.mystory.meeting.meeting.domain.Meeting;
import com.team.mystory.meeting.meeting.domain.MeetingParticipant;
import com.team.mystory.meeting.meeting.dto.MeetingMemberResponse;
import com.team.mystory.meeting.meeting.dto.MeetingResponse;
import com.team.mystory.meeting.meeting.dto.ParticipantResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomMeetingRepository {

    List<MeetingResponse> findAllMeeting(Pageable pageable);

    Optional<Meeting> findMeetingByMeetingIdAndMeetingOwner(long meetingId , String userId);

    Optional<Meeting> findMeetingAndChatById(long meetingId);

    Optional<MeetingResponse> findMeetingByMeetingId(long meetingId);

    Optional<MeetingParticipant> findMeetingParticipantByMeetingIdAndUserId(long meetingId , String userId);

    long findAllMeetingCount();

    List<MeetingResponse> findMeetingByTitleOrAddress(Pageable pageable , String data);

    long findMeetingByTitleOrAddressCount(String data);

    List<MeetingResponse> findAllMeetingByUserId(Pageable pageable , String userId);

    long findAllMeetingByUserIdCount(String userId);

    List<ParticipantResponse> findParticipantsByMeetingId(long meetingId);

    MeetingMemberResponse findMeetingOwnerByMeetingId(long meetingId);

    long deleteParticipantsByMeetingIdAndUserId(long meetingId , String userId);

}

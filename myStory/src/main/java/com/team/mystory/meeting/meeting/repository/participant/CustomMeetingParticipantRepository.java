package com.team.mystory.meeting.meeting.repository.participant;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.meeting.meeting.domain.MeetingParticipant;
import com.team.mystory.meeting.meeting.dto.MeetingResponse;
import com.team.mystory.meeting.meeting.dto.ParticipantResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomMeetingParticipantRepository {

    long deleteParticipantsByMeetingIdAndUserId(long meetingId , String userId);

    List<ParticipantResponse> findParticipantsByMeetingId(long meetingId);

    Optional<MeetingParticipant> findMeetingParticipantByMeetingIdAndUser(long meetingId , User user);

    List<MeetingResponse> findAllMeetingByParticipant(Pageable pageable , String userId);

    long countAllMeetingByParticipant(String userId);

}

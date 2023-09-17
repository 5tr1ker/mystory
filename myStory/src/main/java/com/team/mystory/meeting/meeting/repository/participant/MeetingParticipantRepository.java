package com.team.mystory.meeting.meeting.repository.participant;

import com.team.mystory.meeting.meeting.domain.MeetingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant , Long> , CustomMeetingParticipantRepository {

}

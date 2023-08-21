package com.team.mystory.meeting.meeting.repository;

import com.team.mystory.meeting.meeting.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting , Long> , CustomMeetingRepository {


}

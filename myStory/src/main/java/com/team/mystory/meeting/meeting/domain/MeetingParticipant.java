package com.team.mystory.meeting.meeting.domain;

import com.team.mystory.account.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long meetingParticipantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meetingId")
    private Meeting meetingList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User userList;

    public static MeetingParticipant createMeetingParticipant(Meeting meeting , User user) {
        return MeetingParticipant.builder()
                .meetingList(meeting)
                .userList(user)
                .build();
    }

}

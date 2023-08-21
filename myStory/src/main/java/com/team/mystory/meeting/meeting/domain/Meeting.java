package com.team.mystory.meeting.meeting.domain;

import com.team.mystory.meeting.meeting.dto.MeetingRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long meetingId;

    @Column(nullable = false)
    private double locateX;

    @Column(nullable = false)
    private double locateY;

    @Column(nullable = false)
    private String address;

    private String meetingImage;

    private String title;

    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "meetingList")
    private List<MeetingParticipant> meetingParticipants = new ArrayList<>();

    public static Meeting createMeetingEntity(MeetingRequest meetingRequest) {
        return Meeting.builder()
                .locateY(meetingRequest.getLocateY())
                .locateX(meetingRequest.getLocateX())
                .address(meetingRequest.getAddress())
                .title(meetingRequest.getTitle())
                .description(meetingRequest.getDescription())
                .build();
    }

    public void updateMeetingImage(String image) {
        meetingImage = image;
    }

    public void updateData(MeetingRequest meetingRequest) {
        this.locateX = meetingRequest.getLocateX();
        this.locateY = meetingRequest.getLocateY();
        this.title = meetingRequest.getTitle();
        this.address = meetingRequest.getAddress();
        this.description = meetingRequest.getDescription();
    }
}

package com.team.mystory.meeting.meeting.domain;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.common.config.BooleanConverter;
import com.team.mystory.meeting.chat.entity.Chat;
import com.team.mystory.meeting.chat.entity.ChatRoom;
import com.team.mystory.meeting.meeting.dto.MeetingRequest;
import com.team.mystory.meeting.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long meetingId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User meetingOwner;

    @Builder.Default
    @OneToMany(cascade = { CascadeType.PERSIST , CascadeType.REMOVE } , mappedBy = "meetings" , orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @Setter
    @OneToOne(cascade = { CascadeType.PERSIST , CascadeType.REMOVE } , orphanRemoval = true)
    private ChatRoom chatRoom;

    @Column(nullable = false)
    private double locateX;

    @Column(nullable = false)
    private double locateY;

    @Column(nullable = false)
    private String address;

    private String meetingImage;

    private String title;

    private String description;

    private String detailAddress;

    private int maxParticipants;

    @Column(nullable = false)
    @Convert(converter = BooleanConverter.class)
    private boolean isDelete;

    @Builder.Default
    @OneToMany(mappedBy = "meetingList" , cascade = {CascadeType.REMOVE} , orphanRemoval = true)
    private List<MeetingParticipant> meetingParticipants = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "meetingId" , cascade = { CascadeType.PERSIST , CascadeType.REMOVE } , orphanRemoval = true)
    private List<Chat> chats = new ArrayList<>();

    public static Meeting createMeetingEntity(MeetingRequest meetingRequest , User user) {
        return Meeting.builder()
                .meetingOwner(user)
                .locateY(meetingRequest.getLocateY())
                .locateX(meetingRequest.getLocateX())
                .address(meetingRequest.getAddress())
                .title(meetingRequest.getTitle())
                .description(meetingRequest.getDescription())
                .maxParticipants(meetingRequest.getMaxParticipants())
                .detailAddress(meetingRequest.getDetailAddress())
                .isDelete(false)
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
        this.detailAddress = meetingRequest.getDetailAddress();
        this.maxParticipants = meetingRequest.getMaxParticipants();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setMeetings(this);
    }

    public void deleteMeeting() {
        this.isDelete = true;
    }

}

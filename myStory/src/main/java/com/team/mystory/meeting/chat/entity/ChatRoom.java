package com.team.mystory.meeting.chat.entity;

import com.team.mystory.common.config.BooleanConverter;
import com.team.mystory.meeting.meeting.domain.Meeting;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Meeting meetingId;

    @Column(nullable = false)
    @Convert(converter = BooleanConverter.class)
    private boolean isDelete;

    @CreationTimestamp
    private LocalDate createDate;

    public static ChatRoom createChatRoom(Meeting meeting) {
        return ChatRoom.builder()
                .meetingId(meeting)
                .build();
    }

}

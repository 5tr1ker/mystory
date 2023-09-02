package com.team.mystory.meeting.chat.entity;

import com.team.mystory.meeting.meeting.domain.Meeting;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(cascade = { CascadeType.PERSIST , CascadeType.REMOVE } , orphanRemoval = true)
    private List<Chat> chatData;

    private LocalDate lastChat;

    public static ChatRoom createChatRoom(Meeting meeting) {
        return ChatRoom.builder()
                .meetingId(meeting)
                .lastChat(LocalDate.now())
                .build();
    }

}

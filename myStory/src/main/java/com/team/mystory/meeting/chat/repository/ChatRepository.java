package com.team.mystory.meeting.chat.repository;

import com.team.mystory.meeting.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> , CustomChatRepository {

}

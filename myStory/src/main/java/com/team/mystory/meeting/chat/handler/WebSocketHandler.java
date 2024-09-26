package com.team.mystory.meeting.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.mystory.common.response.message.MeetingMessage;
import com.team.mystory.meeting.chat.dto.ChatRequest;
import com.team.mystory.meeting.chat.dto.ChatResponse;
import com.team.mystory.meeting.chat.dto.MessageType;
import com.team.mystory.meeting.chat.entity.Chat;
import com.team.mystory.meeting.chat.repository.ChatRepository;
import com.team.mystory.meeting.meeting.domain.Meeting;
import com.team.mystory.meeting.meeting.exception.MeetingException;
import com.team.mystory.meeting.meeting.repository.meeting.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team.mystory.common.response.message.MeetingMessage.NOT_FOUND_MEETING;
import static com.team.mystory.meeting.chat.dto.ChatResponse.createChatResponse;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private Map<Long , List<WebSocketSession>> sessionList = new HashMap<>();
    private final MeetingRepository meetingRepository;
    private final ChatRepository chatRepository;

    @Override
    @Transactional
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatRequest chatMessage = objectMapper.readValue(message.getPayload(), ChatRequest.class);
        long meetingId = chatMessage.getMeetingId();

        if(chatMessage.getMessageType() == MessageType.ENTER) {
            joinChatBySession(meetingId , session);

        } else if(chatMessage.getMessageType() == MessageType.SEND) {
            sendChatToSameRootId(meetingId , objectMapper , chatMessage);
        }
    }

    private void joinChatBySession(long meetingId , WebSocketSession session) {
        if(!sessionList.containsKey(meetingId)) {
            sessionList.put(meetingId , new ArrayList<>());
        }
        List<WebSocketSession> sessions = sessionList.get(meetingId);
        if(!sessions.contains(session)) {
            sessions.add(session);
        }
    }

    private void sendChatToSameRootId(long meetingId , ObjectMapper objectMapper , ChatRequest chatMessage) throws IOException {
        List<WebSocketSession> sessions = sessionList.get(meetingId);
        ChatResponse chatResponse = createChatResponse(chatMessage);
        saveChatData(meetingId , chatResponse);

        for(WebSocketSession webSocketSession : sessions) {
            String result = objectMapper.writeValueAsString(chatResponse);
            webSocketSession.sendMessage(new TextMessage(result));
        }
    }

    private void saveChatData(long meetingId , ChatResponse chatResponse) {
        Meeting meeting = meetingRepository.findMeetingAndChatById(meetingId)
                .orElseThrow(() -> new MeetingException(NOT_FOUND_MEETING));

        Chat chat = Chat.createChat(chatResponse , meeting);
        meeting.getChats().add(chat);
        chatRepository.save(chat);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        for(Long meetingId : sessionList.keySet()) {
            List<WebSocketSession> webSocketSessions = sessionList.get(meetingId);

            for(int i = 0; i < webSocketSessions.size(); i++) {
                WebSocketSession socket = webSocketSessions.get(i);

                if(socket.getId().equals(session.getId())) {
                    webSocketSessions.remove(i);
                    break;
                }
            }
        }
    }
}

package com.team.mystory.meeting.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.mystory.meeting.chat.dto.ChatRequest;
import com.team.mystory.meeting.chat.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private Map<Long , List<WebSocketSession>> sessionList = new HashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatRequest chatMessage = objectMapper.readValue(message.getPayload(), ChatRequest.class);
        long meetingId = chatMessage.getMeetingId();
        System.out.println("메세지 발사!");

        if(!sessionList.containsKey(meetingId)) {
            sessionList.put(meetingId , new ArrayList<>());
        }
        List<WebSocketSession> sessions = sessionList.get(meetingId);
        sessions.add(session);

        for(WebSocketSession webSocketSession : sessions) {
            Date date = new Date();
            // SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
            ChatResponse chatResponse = ChatResponse.builder()
                    .userName(chatMessage.getSender())
                    .sendTime(date)
                    .message(chatMessage.getMessage())
                    .build();

            String result = objectMapper.writeValueAsString(chatResponse);
            webSocketSession.sendMessage(new TextMessage(result));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        for(Long meetingId : sessionList.keySet()) {
            List<WebSocketSession> webSocketSessions = sessionList.get(meetingId);

            for(int i = 0; i < webSocketSessions.size(); i++) {
                WebSocketSession socket = webSocketSessions.get(i);

                if(socket.equals(session)) {
                    webSocketSessions.remove(i);
                    System.out.println(socket.getId() + " has disconnected. :( ");
                    break;
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("씨발");
    }
}

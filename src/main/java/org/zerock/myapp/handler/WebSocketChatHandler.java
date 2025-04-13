package org.zerock.myapp.handler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final Map<Long, Set<WebSocketSession>> chatRoomSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 쿼리파라미터에서 채팅방 ID 추출
        Long chatId = getChatIdFromSession(session);
        chatRoomSessions.computeIfAbsent(chatId, k -> ConcurrentHashMap.newKeySet()).add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)  {
        Long chatId = getChatIdFromSession(session);
        Set<WebSocketSession> sessions = chatRoomSessions.get(chatId);
        if (sessions != null) {
            for (WebSocketSession s : sessions) {
                try {
                    s.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long chatId = getChatIdFromSession(session);
        Set<WebSocketSession> sessions = chatRoomSessions.get(chatId);
        if (sessions != null) {
            sessions.remove(session);
        }
    }

    private Long getChatIdFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery(); // 예: "chatId=3"
        return Long.parseLong(query.split("=")[1]);
    }
}
package org.zerock.myapp.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.zerock.myapp.domain.MessageDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.annotation.HandlesTypes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor

public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;

    // 현재 연결된 세션들
    private final Set<WebSocketSession> sessions = new HashSet<>();

    // chatRoomId: {session1, session2}
    private final Map<Long,Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결됨", session.getId());
        this.sessions.add(session);
    }

    // 소켓 통신 시 메세지의 전송을 다루는 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        // 페이로드 -> chatMessageDto로 변환
        MessageDTO MessageDto = mapper.readValue(payload, MessageDTO.class);
        log.info("session {}", MessageDto.toString());

        Long chatRoomId = MessageDto.getChat().getId();
        // 메모리 상에 채팅방에 대한 세션 없으면 만들어줌
        if(!chatRoomSessionMap.containsKey(chatRoomId)){
            chatRoomSessionMap.put(chatRoomId,new HashSet<>());
        }
        
        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);
        

    }

    // 소켓 종료 확인
    @Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.debug("afterConnectionClosed({},{}) invoked.", session, status);
		
		this.sessions.remove(session);
		this.sessions.forEach(s-> log.info("\t + Active Session:{}", s));
	}//afterConnectionClosed

    // ====== 채팅 관련 메소드 ======
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !this.sessions.contains(sess));
    }

    private void sendMessageToChatRoom(MessageDTO MessageDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, MessageDto));//2
    }


    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    
}

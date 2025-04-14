package org.zerock.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.zerock.myapp.handler.WebSocketChatHandler;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

//@EnableWebSocket
@Configuration						
public class WebSocketConfig implements WebSocketConfigurer {

	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		log.debug("registerWebSocketHandlers({}) invoked.",registry);

		registry.addHandler(new WebSocketChatHandler(), "/chatroom").setAllowedOrigins("*");	
		
	}// registerWebSocketHandlers
	
	
	@Bean
	ServerEndpointExporter serverEndpointExporter() {
		log.debug("serverEndpointExporter() invoked.");
		return new ServerEndpointExporter();
	}// serverEndpointExporter

	
	
}// end class
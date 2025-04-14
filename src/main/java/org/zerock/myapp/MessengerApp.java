package org.zerock.myapp;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@ServletComponentScan
@EnableWebSocket

@SpringBootApplication
@EnableJpaAuditing
public class MessengerApp {

	public static void main(String[] args) {
		log.debug("main({}) invoked.",Arrays.toString(args));
		
		SpringApplication app = new SpringApplication(MessengerApp.class);
		
		app.setWebApplicationType(WebApplicationType.SERVLET);
		app.setBannerMode(Mode.CONSOLE);
		
		app.addListeners(e -> {
			log.info("<<<{}>>>",e.getClass().getSimpleName());
		}); // addListeners
		
		app.run(args);
	} // main

} // end class

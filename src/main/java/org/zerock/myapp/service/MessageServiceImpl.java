package org.zerock.myapp.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.MessageDTO;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.Message;
import org.zerock.myapp.handler.WebSocketChatHandler;
import org.zerock.myapp.persistence.ChatRepository;
import org.zerock.myapp.persistence.EmployeeRepository;
import org.zerock.myapp.persistence.MessageRepository;

import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.common.content.ContentPart.ChatContentPart;
import io.github.sashirestela.openai.common.content.ContentPart.ContentPartText;
import io.github.sashirestela.openai.domain.chat.ChatMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.UserMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired private MessageRepository messageRepository;
    @Autowired private ChatRepository chatRepository;
	@Autowired private EmployeeRepository employeeRepository;
	
	@PostConstruct
    void postConstruct(){
        log.debug("MessageServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", messageRepository);
    }//postConstruct

	@Override
	public Message saveMessage(MessageDTO dto) {
		
		Message message = new Message();
		
		message.setDetail(dto.getDetail());
		message.setEmployee(employeeRepository.findById(dto.getEmpno())
				.orElseThrow(() -> new IllegalArgumentException("사원이 존재하지 않습니다.")));
		message.setChat(chatRepository.findById(dto.getChatId())
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다.")));
		message.setType(dto.getType());
		return this.messageRepository.save(message);
	} // saveMessage


	@Override
	public List<Message> getByChatId(Long chatId) {
		
		return this.messageRepository.findByChatIdOrderByCrtDate(chatId);
		
	} // getByChatId

	@Override
	public String summarizeMessage(Long id, String start, String end) {
		try {
			// 1. 날짜 문자열을 Date로 변환
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        Date startDate = formatter.parse(start);
	        Date endDate = formatter.parse(end);
			
	        // 2. 채팅메세지 조회
			List<Message> selectMessages = 
						this.messageRepository.findByChatIdAndCrtDateBetweenOrderByCrtDate(id, startDate, endDate);
			
			 // 3. 대화 내용 구성
	        StringBuilder contentBuilder = new StringBuilder();
	        for (Message msg : selectMessages) {
	            contentBuilder.append(msg.getEmployee().getName())
	                          .append(": ")
	                          .append(msg.getDetail())
	                          .append("\n");
	        }
	        String chatContent = contentBuilder.toString();
			
	        // 4. AI 구성
			var model = "gpt-4o-mini";
	  		var temperature = .0;
	  		var n = 1;
	  		var maxTokens = 500;
	  		
	  		var prompt = "해당 대화에 대한 요점을 100 단어 이하로 요약해줘! 대화가 한마디도 없다면 대화내용이 없다고 말해줘"
	  				+ "참고로 지금 대화는 사내 메신저 대화야!" + chatContent;
		     
		    var messages = List.<ChatMessage>of(   
		             UserMessage.of(
		                   List.<ChatContentPart>of(
		                         ContentPartText.of(prompt)
		                   ) // .of
		            ) // .of
		     ); // .of
	
		     var openAI = SimpleOpenAI.builder().apiKey(System.getenv("OPENAI_API_KEY")).build();      
		     
		     var chatRequest = ChatRequest.builder().
		                                         messages(messages).
		                                         model(model).
		                                         temperature(temperature).
		                                         n(n).
		                                         maxCompletionTokens(maxTokens).
		                                         build();
		     
		     var chatResponse = openAI.chatCompletions().create(chatRequest).join();
		     log.info("2. assistant reply: {}", chatResponse.firstContent());      
		     String summary = chatResponse.firstContent();
		     
	     	return summary;
		}catch(Exception e) {
			log.error("요약실패:{}", e.getMessage());
			return "요약 중 오류 발생";
		}
	} // summarizeMessage
	
	
	
}//end class

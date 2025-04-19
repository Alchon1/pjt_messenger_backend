package org.zerock.myapp.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.MessageDTO;
import org.zerock.myapp.entity.Message;
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
    @Autowired MessageRepository messageRepository;
    @Autowired private ChatRepository chatRepository;
	@Autowired private EmployeeRepository employeeRepository;
    
	
	@PostConstruct
    void postConstruct(){
        log.debug("MessageServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", messageRepository);
    }//postConstruct


	@Override
	public List<Message> getAllList() {	//검색 없는 전체 리스트
		log.debug("MessageServiceImpl -- getAllList() invoked");
		
		List<Message> list = messageRepository.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public List<Message> getSearchList(MessageDTO dto) {	//검색 있는 전체 리스트
		log.debug("MessageServiceImpl -- getSearchList(()) invoked", dto);

		List<Message> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public Message create(MessageDTO dto) {	//등록 처리
		log.debug("MessageServiceImpl -- create({}) invoked", dto);
		
		Message data = new Message();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	} // create
	
	@Override
	public Message getById(String id) {	// 단일 조회
		log.debug("MessageServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Message data = new Message();//dao.findById(id).orElse(new Message());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(MessageDTO dto) {//수정 처리
		log.debug("MessageServiceImpl -- update({}) invoked", dto);
		
//		Message data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("MessageServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	} // deleteById


	@Override
	public Message saveMessage(MessageDTO dto) {
		
		Message message = new Message();
		
		message.setDetail(dto.getDetail());
		message.setEmployee(employeeRepository.findById(dto.getEmpno())
				.orElseThrow(() -> new IllegalArgumentException("사원이 존재하지 않습니다.")));
		message.setChat(chatRepository.findById(dto.getChatId())
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다.")));
//		message.setChat(dto.getChat()); / 어떤 방법이 맞는지 궁금
//		message.setCrtDate(LocalDateTime.now());
//		message.setCrtDate(dto.getCrtDateAsLocalDateTime());
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
	  					+ chatContent;
		     
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

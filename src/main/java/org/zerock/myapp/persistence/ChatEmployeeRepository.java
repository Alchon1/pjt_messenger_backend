package org.zerock.myapp.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Chat;
import org.zerock.myapp.entity.ChatEmployee;
import org.zerock.myapp.entity.ChatEmployeePK;


/**
 * 채팅 Repository
 */

@Repository
public interface ChatEmployeeRepository extends JpaRepository<ChatEmployee, ChatEmployeePK>, JpaSpecificationExecutor<Chat> {
	public abstract List<ChatEmployee> findByIdChatId(Long chatId);
	public abstract List<ChatEmployee> findByEnabledAndIdChatId(Boolean b, Long chatId);
//	List<ChatEmployee> findByIdEmployeeId(String empno);
	public abstract ChatEmployee findByIdChatIdAndIdEmpno(Long chatId, String empno);
} // end interface

package org.zerock.myapp.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	public abstract List<ChatEmployee> findByEnabledAndIdEmpno(Boolean b, String empno);
	public abstract ChatEmployee findByIdChatIdAndIdEmpno(Long chatId, String empno);
	
	final String nativeSQL_board_empname = """
			SELECT C.EMPNO, C.CHAT_ID, C.ENABLED, C.CRT_DATE, C.UDT_DATE, CHATCNT.CHATCNT AS cnt
			FROM T_CHAT_EMPLOYEE C
			    LEFT JOIN ( 
			        SELECT CHAT.ID, 
			        CASE 
				        WHEN MAX(MESSAGE.CRT_DATE) IS NULL THEN CHAT.CRT_DATE
				        ELSE MAX(MESSAGE.CRT_DATE)
			        END AS CRT_DATE
			        FROM T_CHAT CHAT
			            LEFT JOIN T_MESSAGE MESSAGE ON CHAT.ID = MESSAGE.CHAT_ID
			        GROUP BY CHAT.ID , CHAT.CRT_DATE
			    ) M ON C.CHAT_ID = M.ID
			    LEFT JOIN ( SELECT CHAT_ID, COUNT(CHAT_ID) AS CHATCNT
									  FROM T_CHAT_EMPLOYEE
									  WHERE ENABLED = 1
									  GROUP BY CHAT_ID ) CHATCNT ON C.CHAT_ID = CHATCNT.CHAT_ID
			WHERE C.ENABLED = :enabled AND C.EMPNO = :empno
			ORDER BY M.CRT_DATE DESC
		""";
	@Query(value = nativeSQL_board_empname, nativeQuery = true)
	List<ChatEmployee> findChatByEmpno(
		@Param("enabled") Boolean enabled,
		@Param("empno") String empno
	);
} // end interface

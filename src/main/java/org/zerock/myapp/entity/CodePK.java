package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;


@Data

// 코드 PK 엔티티

//@Embeddable
public class CodePK implements Serializable {
	@Serial private static final long serialVersionUID = 1L;
	
	//@Column(name="CATEGORY")
	private String category; // 카테고리

	//@Column(name="CODE")
	private Long code; // 코드번호

} // end class
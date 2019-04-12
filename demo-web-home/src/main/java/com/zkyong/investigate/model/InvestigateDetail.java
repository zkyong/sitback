package com.zkyong.investigate.model;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 会员问卷调查登记明细表
 * 
 * @author administrator
 * @date 2018年10月16日 12:13:37
 */
@Table(name = "investigate_detail")
public class InvestigateDetail {
	/** 明细ID */
	private Integer id;
	/** 会员帐号 */
	@Column(name = "user_id")
	private Integer userId;
	/** 题目ID */
	@Column(name = "question_id")
	private Integer questionId;
	/** 选项ID */
	private Integer optionId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}
}
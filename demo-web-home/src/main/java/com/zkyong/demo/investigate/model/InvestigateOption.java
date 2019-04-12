package com.zkyong.demo.investigate.model;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 问卷调查题库选项表
 * 
 * @author administrator
 * @date 2018年10月16日 12:13:41
 */
@Table(name = "investigate_option")
public class InvestigateOption {
	/** 选项ID */
	private Integer id;
	/** 题目ID */
	@Column(name = "question_id")
	private Integer questionId;
	/** 选项序号 */
	@Column(name = "option_index")
	private String optionIndex;
	/** 选项内容 */
	private String content;
	/** 选项分数 */
	private Integer score;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getOptionIndex() {
		return optionIndex;
	}

	public void setOptionIndex(String optionIndex) {
		this.optionIndex = optionIndex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
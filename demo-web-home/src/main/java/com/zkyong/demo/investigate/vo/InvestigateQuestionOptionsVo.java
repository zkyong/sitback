package com.zkyong.demo.investigate.vo;

import java.util.List;

/**
 * 问卷调查题库题目(含选项) 视图层类
 * 
 * @author administrator
 * @date 2018年10月16日 13:00:58
 */
public class InvestigateQuestionOptionsVo {
	/** 题目ID */
	private Integer id;
	/** 题目序号 */
	private String questionIndex;
	/** 题目内容 */
	private String content;
	/** 题目状态 */
	private Integer status;
	/** 选项列表 */
	private List<InvestigateOptionVo> options;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestionIndex() {
		return questionIndex;
	}

	public void setQuestionIndex(String questionIndex) {
		this.questionIndex = questionIndex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<InvestigateOptionVo> getOptions() {
		return options;
	}

	public void setOptions(List<InvestigateOptionVo> options) {
		this.options = options;
	}
}
package com.zkyong.demo.model;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 问卷调查题库题目表
 * 
 * @author administrator
 * @date 2018年10月16日 12:13:21
 */
@Table(name = "risk_question")
public class RiskQuestion {
    /** 题目ID */
    private Integer id;
    /** 题目序号 */
    @Column(name = "question_index")
    private String  questionIndex;
    /** 题目内容 */
    private String  content;
    /** 题目状态 */
    private Integer status;

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
}
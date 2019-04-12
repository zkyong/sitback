package com.zkyong.demo.vo;

/**
 * 风险评估题库题目选项 视图类
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 14:54:12
 */
public class RiskOptionVo {
    /** 选项ID */
    private Integer id;
    /** 选项序号 */
    private String  optionIndex;
    /** 题目ID */
    private String  questionId;
    /** 选项内容 */
    private String  content;
    /** 选项分数 */
    private Integer score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptionIndex() {
        return optionIndex;
    }

    public void setOptionIndex(String optionIndex) {
        this.optionIndex = optionIndex;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
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
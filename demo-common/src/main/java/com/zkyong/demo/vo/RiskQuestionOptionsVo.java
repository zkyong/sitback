package com.zkyong.demo.vo;

import java.util.List;

/**
 * 风险评估题库题目(含选项) 视图类
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 14:54:25
 */
public class RiskQuestionOptionsVo {
    /** 题目ID */
    private Integer            id;
    /** 题目序号 */
    private String             questionIndex;
    /** 题目内容 */
    private String             content;
    /** 题目状态 */
    private Integer            status;
    /** 选项列表 */
    private List<RiskOptionVo> options;

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

    public List<RiskOptionVo> getOptions() {
        return options;
    }

    public void setOptions(List<RiskOptionVo> options) {
        this.options = options;
    }
}
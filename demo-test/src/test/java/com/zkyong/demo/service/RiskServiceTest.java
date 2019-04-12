package com.zkyong.demo.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zkyong.demo.service.RiskService;
import com.zkyong.demo.vo.RiskQuestionOptionsVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext_tk.xml" })
public class RiskServiceTest {
    @Autowired
    private RiskService riskService;

    @Test
    public void selectInvestigateQuestions() {
        List<RiskQuestionOptionsVo> list = riskService.selectQuestionOptions();
        System.out.println(list);
    }
}

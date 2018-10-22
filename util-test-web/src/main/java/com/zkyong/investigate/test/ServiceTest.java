package com.zkyong.investigate.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zkyong.investigate.service.InvestigateService;
import com.zkyong.investigate.vo.InvestigateQuestionOptionsVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext_tk.xml" })
public class ServiceTest {
	@Autowired
	private InvestigateService investigateService;

	@Test
	public void selectInvestigateQuestions() {
		List<InvestigateQuestionOptionsVo> list = investigateService.selectQuestionOptions();
		System.out.println(list);
	}
}

package com.zkyong.investigate.controller;

import com.zkyong.investigate.model.InvestigateDetail;
import com.zkyong.investigate.service.InvestigateService;
import com.zkyong.investigate.vo.InvestigateOptionVo;
import com.zkyong.investigate.vo.InvestigateQuestionOptionsVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import javax.annotation.Resource;

/**
 * 问卷调查接口
 * 
 * @author administrator
 * @date 2018年10月16日 14:19:54
 */
@Controller
public class RiskController {
	private final static Logger logger = LoggerFactory.getLogger(RiskController.class);

	@Resource
	private InvestigateService investigateService;

	/**
	 * 获取问卷调查题目及选项信息
	 * 
	 * @author administrator
	 * @date 2018年10月16日 13:10:32
	 * @return List
	 */
	@RequestMapping(name = "/getInvestigateQuestions", method = RequestMethod.GET)
	public List<InvestigateQuestionOptionsVo> selectInvestigateQuestions() {
		return investigateService.selectQuestionOptions();
	}

	/**
	 * 用户调查问卷登记
	 * 
	 * @author administrator
	 * @date 2018年10月16日 13:13:29
	 * @throws Exception
	 */
	@RequestMapping(name = "/saveInvestigateDetail", method = RequestMethod.POST)
	public void insertInvestigateLog(Integer userId, List<InvestigateOptionVo> options) throws Exception {
		List<InvestigateDetail> logs = investigateService.selectInvestigateDetailByUser(userId);
		if (!CollectionUtils.isEmpty(logs)) {
			logger.info("该用户已完成过问卷调查");
			throw new Exception("该用户已完成过问卷调查");
		}
		investigateService.insertInvestigateDetail(userId, options);
	}
}

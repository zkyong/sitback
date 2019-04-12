package com.zkyong.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zkyong.demo.exception.ServiceException;
import com.zkyong.demo.model.RiskDetail;
import com.zkyong.demo.service.RiskService;
import com.zkyong.demo.vo.RiskOptionVo;
import com.zkyong.demo.vo.RiskQuestionOptionsVo;

import java.util.List;

/**
 * 风险评估接口
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 14:43:59
 */
@Controller
public class RiskController {
    private final static Logger LOGGER = LoggerFactory.getLogger(RiskController.class);

    @Autowired
    private RiskService         riskService;

    /**
     * 获取风险评估题目及选项信息
     * 
     * @return List
     */
    @RequestMapping(name = "/getRiskQuestions", method = RequestMethod.GET)
    public List<RiskQuestionOptionsVo> selectRiskQuestions() {
        return riskService.selectQuestionOptions();
    }

    /**
     * 用户风险评估登记
     * 
     * @param userId 用户ID
     * @param options 风险评估详情
     * @throws ServiceException 业务异常
     */
    @RequestMapping(name = "/saveRiskDetail", method = RequestMethod.POST)
    public void insertInvestigateLog(Integer userId, List<RiskOptionVo> options) throws Exception {
        List<RiskDetail> logs = riskService.selectInvestigateDetailByUser(userId);
        if (!CollectionUtils.isEmpty(logs)) {
            LOGGER.info("该用户已完成过风险评估");
            throw new ServiceException("该用户已完成过风险评估");
        }
        riskService.insertInvestigateDetail(userId, options);
    }
}

package com.zkyong.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zkyong.demo.mapper.RiskDetailMapper;
import com.zkyong.demo.mapper.RiskOptionMapper;
import com.zkyong.demo.mapper.RiskQuestionMapper;
import com.zkyong.demo.model.RiskDetail;
import com.zkyong.demo.model.RiskOption;
import com.zkyong.demo.model.RiskQuestion;
import com.zkyong.demo.service.RiskService;
import com.zkyong.demo.vo.RiskOptionVo;
import com.zkyong.demo.vo.RiskQuestionOptionsVo;

/**
 * 风险评估Service 实现类
 * 
 * @author administrator
 * @date 2018年10月16日 14:45:39
 */
@Service
public class RiskServiceImpl implements RiskService {

    @Autowired
    public RiskQuestionMapper riskQuestionMapper;

    @Autowired
    public RiskOptionMapper   riskOptionMapper;

    @Autowired
    public RiskDetailMapper   RiskDetailMapper;

    /**
     * 查询题库
     */
    @Override
    public List<RiskQuestionOptionsVo> selectQuestionOptions() {
        // 查询状态为启用的题目列表
        RiskQuestion questionRecord = new RiskQuestion();
        questionRecord.setStatus(1);// 查询状态为启用的记录
        List<RiskQuestion> questions = riskQuestionMapper.select(questionRecord);

        // 查询所有的选项并按题目ID分组
        List<RiskOption> options = riskOptionMapper.selectAll();
        Map<Integer, List<RiskOption>> optionsMap = options.stream()
            .collect(Collectors.groupingBy(RiskOption::getQuestionId));
        // 组装返回结果
        List<RiskQuestionOptionsVo> vos = new ArrayList<>();
        for (RiskQuestion question : questions) {
            RiskQuestionOptionsVo vo = new RiskQuestionOptionsVo();
            BeanUtils.copyProperties(question, vo);
            List<RiskOption> source = optionsMap.get(question.getId());
            List<RiskOptionVo> target = new ArrayList<>();
            source.forEach(option -> {
                RiskOptionVo riskOptionVo = new RiskOptionVo();
                BeanUtils.copyProperties(option, riskOptionVo);
                target.add(riskOptionVo);
            });
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 根据用户ID查询用户调查记录
     */
    @Override
    public List<RiskDetail> selectInvestigateDetailByUser(Integer userId) {
        RiskDetail logRecord = new RiskDetail();
        logRecord.setUserId(userId);
        return RiskDetailMapper.select(logRecord);
    }

    /**
     * 根据用户ID保存用户调查记录
     */
    @Override
    public int insertInvestigateDetail(Integer userId, List<RiskOptionVo> options) {
        int count = 0;
        if (CollectionUtils.isEmpty(options)) {
            return count;
        }
        for (RiskOptionVo option : options) {
            RiskDetail logRecord = new RiskDetail();
            BeanUtils.copyProperties(option, logRecord);
            count += RiskDetailMapper.insert(logRecord);
        }
        return count;
    }
}

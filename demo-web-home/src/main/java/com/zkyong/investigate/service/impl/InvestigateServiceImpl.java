package com.zkyong.investigate.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zkyong.investigate.mapper.InvestigateOptionMapper;
import com.zkyong.investigate.mapper.InvestigateQuestionMapper;
import com.zkyong.investigate.model.InvestigateDetail;
import com.zkyong.investigate.model.InvestigateOption;
import com.zkyong.investigate.model.InvestigateQuestion;
import com.zkyong.investigate.mapper.InvestigateDetailMapper;
import com.zkyong.investigate.service.InvestigateService;
import com.zkyong.investigate.vo.InvestigateOptionVo;
import com.zkyong.investigate.vo.InvestigateQuestionOptionsVo;

/**
 * 问卷调查service 实现类
 * 
 * @author administrator
 * @date 2018年10月16日 14:45:39
 */
@Service
public class InvestigateServiceImpl implements InvestigateService {

	@Autowired
	public InvestigateQuestionMapper investigateQuestionMapper;

	@Autowired
	public InvestigateOptionMapper investigateOptionMapper;

	@Autowired
	public InvestigateDetailMapper InvestigateDetailMapper;

	/**
	 * 查询题库
	 */
	@Override
	public List<InvestigateQuestionOptionsVo> selectQuestionOptions() {
		// 查询状态为启用的题目列表
		InvestigateQuestion questionRecord = new InvestigateQuestion();
		questionRecord.setStatus(1);// 查询状态为启用的记录
		List<InvestigateQuestion> questions = investigateQuestionMapper.select(questionRecord);

		// 查询所有的选项并按题目ID分组
		List<InvestigateOption> options = investigateOptionMapper.selectAll();
		Map<Integer, List<InvestigateOption>> optionsMap = options.stream()
				.collect(Collectors.groupingBy(InvestigateOption::getQuestionId));
		// 组装返回结果
		List<InvestigateQuestionOptionsVo> vos = new ArrayList<>();
		for (InvestigateQuestion question : questions) {
			InvestigateQuestionOptionsVo vo = new InvestigateQuestionOptionsVo();
			BeanUtils.copyProperties(question, vo);
			List<InvestigateOption> source = optionsMap.get(question.getId());
			List<InvestigateOptionVo> target = new ArrayList<>();
			source.forEach(option -> {
				InvestigateOptionVo optionVo = new InvestigateOptionVo();
				BeanUtils.copyProperties(option, optionVo);
				target.add(optionVo);
			});
			vos.add(vo);
		}
		return vos;
	}

	/**
	 * 根据用户ID查询用户调查记录
	 */
	@Override
	public List<InvestigateDetail> selectInvestigateDetailByUser(Integer userId) {
		InvestigateDetail logRecord = new InvestigateDetail();
		logRecord.setUserId(userId);
		return InvestigateDetailMapper.select(logRecord);
	}

	/**
	 * 根据用户ID保存用户调查记录
	 */
	@Override
	public int insertInvestigateDetail(Integer userId, List<InvestigateOptionVo> options) {
		int count = 0;
		if (CollectionUtils.isEmpty(options)) {
			return count;
		}
		for (InvestigateOptionVo option : options) {
			InvestigateDetail logRecord = new InvestigateDetail();
			BeanUtils.copyProperties(option, logRecord);
			count += InvestigateDetailMapper.insert(logRecord);
		}
		return count;
	}
}

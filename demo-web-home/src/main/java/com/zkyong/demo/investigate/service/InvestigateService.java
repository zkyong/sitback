package com.zkyong.demo.investigate.service;

import java.util.List;

import com.zkyong.demo.investigate.model.InvestigateDetail;
import com.zkyong.demo.investigate.vo.InvestigateOptionVo;
import com.zkyong.demo.investigate.vo.InvestigateQuestionOptionsVo;

/**
 * 问卷调查service接口
 * 
 * @author administrator
 * @date 2018年10月16日 14:45:05
 */
public interface InvestigateService {
	/**
	 * 获取问卷调查题库
	 * 
	 * @author administrator
	 * @date 2018年10月16日 14:47:47
	 * @return List
	 */
	public List<InvestigateQuestionOptionsVo> selectQuestionOptions();

	/**
	 * 查询用户问卷调查记录
	 * 
	 * @param userId 用户ID
	 * @author administrator
	 * @date 2018年10月16日 14:48:37
	 * @return List
	 */
	public List<InvestigateDetail> selectInvestigateDetailByUser(Integer userId);

	/**
	 * 插入用户问卷调查记录
	 * 
	 * @param userId  用户ID
	 * @param options 待插入记录
	 * @return int 受影响记录条数
	 */
	public int insertInvestigateDetail(Integer userId, List<InvestigateOptionVo> options);
}

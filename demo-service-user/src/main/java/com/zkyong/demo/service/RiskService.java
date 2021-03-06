package com.zkyong.demo.service;

import java.util.List;

import com.zkyong.demo.model.RiskDetail;
import com.zkyong.demo.vo.RiskOptionVo;
import com.zkyong.demo.vo.RiskQuestionOptionsVo;

/**
 * 风险评估Service接口
 * 
 * @author administrator
 * @date 2018年10月16日 14:45:05
 */
public interface RiskService {
	/**
	 * 获取风险评估题库
	 * 
	 * @author administrator
	 * @date 2018年10月16日 14:47:47
	 * @return List
	 */
	public List<RiskQuestionOptionsVo> selectQuestionOptions();

	/**
	 * 查询用户风险评估记录
	 * 
	 * @param userId 用户ID
	 * @author administrator
	 * @date 2018年10月16日 14:48:37
	 * @return List
	 */
	public List<RiskDetail> selectInvestigateDetailByUser(Integer userId);

	/**
	 * 插入用户风险评估记录
	 * 
	 * @param userId  用户ID
	 * @param options 待插入记录
	 * @return int 受影响记录条数
	 */
	public int insertInvestigateDetail(Integer userId, List<RiskOptionVo> options);
}

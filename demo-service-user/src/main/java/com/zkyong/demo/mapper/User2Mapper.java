package com.zkyong.demo.mapper;

import com.zkyong.demo.model.User2;

/**
 * 用户表2 Mapper接口
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 15:22:18
 */
public interface User2Mapper {
	
	int insert(User2 record);
	
	User2 selectByPrimaryKey(Integer id);
}
package com.zkyong.demo.mapper;

import com.zkyong.demo.model.User2;

public interface User2Mapper {
	
	int insert(User2 record);
	
	User2 selectByPrimaryKey(Integer id);
}
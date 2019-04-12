package com.zkyong.demo.mapper;

import com.zkyong.demo.model.User1;

public interface User1Mapper {
	
	int insert(User1 record);
	
	User1 selectByPrimaryKey(Integer id);
}
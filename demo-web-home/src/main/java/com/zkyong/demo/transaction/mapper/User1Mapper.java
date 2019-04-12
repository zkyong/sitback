package com.zkyong.demo.transaction.mapper;

import com.zkyong.demo.transaction.model.User1;

public interface User1Mapper {
	
	int insert(User1 record);
	
	User1 selectByPrimaryKey(Integer id);
}
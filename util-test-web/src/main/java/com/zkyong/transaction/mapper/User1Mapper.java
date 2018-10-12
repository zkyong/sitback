package com.zkyong.transaction.mapper;

import com.zkyong.transaction.model.User1;

public interface User1Mapper {
	
	int insert(User1 record);
	
	User1 selectByPrimaryKey(Integer id);
}
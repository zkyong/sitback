package com.zkyong.transaction.mapper;

import com.zkyong.transaction.model.User2;

public interface User2Mapper {
	
	int insert(User2 record);
	
	User2 selectByPrimaryKey(Integer id);
}
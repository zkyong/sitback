package com.zkyong.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zkyong.demo.mapper.User1Mapper;
import com.zkyong.demo.model.User1;
import com.zkyong.demo.service.User1Service;

@Service
public class User1ServiceImpl implements User1Service {
	@Autowired
	User1Mapper user1Mapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addRequired(User1 user) {
		user1Mapper.insert(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addRequiresNew(User1 user) {
		user1Mapper.insert(user);
	}
	
	@Override
	@Transactional(propagation = Propagation.NESTED)
	public void addNested(User1 user) {
		user1Mapper.insert(user);
	}
	
	@Override
	public void noTransactional(User1 user) {
		user1Mapper.insert(user);
	}
}
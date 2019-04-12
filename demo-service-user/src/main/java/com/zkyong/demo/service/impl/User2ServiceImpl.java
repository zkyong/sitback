package com.zkyong.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zkyong.demo.mapper.User2Mapper;
import com.zkyong.demo.model.User2;
import com.zkyong.demo.service.User2Service;

@Service
public class User2ServiceImpl implements User2Service {
	@Autowired
	User2Mapper user2Mapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addRequired(User2 user) {
		user2Mapper.insert(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addRequiredException(User2 user) {
		user2Mapper.insert(user);
		throw new RuntimeException();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addRequiresNew(User2 user) {
		user2Mapper.insert(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addRequiresNewException(User2 user) {
		user2Mapper.insert(user);
		throw new RuntimeException();
	}

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public void addNested(User2 user) {
		user2Mapper.insert(user);
	}

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public void addNestedException(User2 user) {
		user2Mapper.insert(user);
		throw new RuntimeException();
	}
	
	@Override
	public void noTransactional(User2 user) {
		user2Mapper.insert(user);
	}

	@Override
	public void noTransactionalException(User2 user) {
		user2Mapper.insert(user);
		throw new RuntimeException();
	}
}
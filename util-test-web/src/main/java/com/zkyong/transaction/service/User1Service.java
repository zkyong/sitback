package com.zkyong.transaction.service;

import com.zkyong.transaction.model.User1;

public interface User1Service {
	public void addRequired(User1 user);

	public void addRequiresNew(User1 user);

	public void addNested(User1 user);

	public void noTransactional(User1 user);
}
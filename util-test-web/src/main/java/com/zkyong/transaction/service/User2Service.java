package com.zkyong.transaction.service;

import com.zkyong.transaction.model.User2;

public interface User2Service {
	public void addRequired(User2 user);

	public void addRequiredException(User2 user);

	public void addRequiresNew(User2 user);

	public void addRequiresNewException(User2 user);

	public void addNested(User2 user);

	public void addNestedException(User2 user);

	public void noTransactional(User2 user);
	
	public void noTransactionalException(User2 user);
}
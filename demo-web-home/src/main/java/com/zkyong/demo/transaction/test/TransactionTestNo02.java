package com.zkyong.demo.transaction.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zkyong.demo.transaction.model.User1;
import com.zkyong.demo.transaction.model.User2;
import com.zkyong.demo.transaction.service.User1Service;
import com.zkyong.demo.transaction.service.User2Service;

/**
 * 场景二：外围方法开启事务。
 * 结论：
 * 		试验结果证明，在外围方法开启事务的情况下，若内围未开启事务，则默认和外围在同一个事务
 * 
 * @author: zhangkaiyong
 * @date: 2018年11月1日 14:26:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TransactionTestNo02 {
	@Autowired
	private User1Service user1Service;
	@Autowired
	private User2Service user2Service;

	/**
	 * 外围方法开启事务，内围未开启事务，默认和外围为同一事务，外围方法回滚，内围方法也要回滚。
	 * 运行结果："张三"、"李四"均未插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年11月1日 14:26:28
	 */
	@Test
	@Transactional
	public void notransaction_exception_no_no() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.noTransactional(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.noTransactional(user2);
		
		throw new RuntimeException();
	}

	/**
	 * 外围方法开启事务，内围未开启事务，默认和外围为同一事务，内围方法回滚，外围方法也要回滚。
	 * 运行结果："张三"、"李四"均未插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年11月1日 14:26:32
	 */
	@Test
	@Transactional
	public void notransaction_no_no_exception() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.noTransactional(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.noTransactionalException(user2);
	}
}

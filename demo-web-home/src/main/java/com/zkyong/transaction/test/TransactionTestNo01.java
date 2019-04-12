package com.zkyong.transaction.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zkyong.transaction.model.User1;
import com.zkyong.transaction.model.User2;
import com.zkyong.transaction.service.User1Service;
import com.zkyong.transaction.service.User2Service;

/**
 * 场景一：外围方法没有开启事务。
 * 结论：
 * 		试验结果证明，在外围方法未开启事务，内围方法也未开启事务时，默认无事务，每次调用dao数据均会插入。
 * 
 * @author: zhangkaiyong
 * @date: 2018年11月1日 14:26:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TransactionTestNo01 {
	@Autowired
	private User1Service user1Service;
	@Autowired
	private User2Service user2Service;

	/**
	 * 外围方法未开启事务，内围方法也未开启事务，默认无事务，每次调用dao数据均会插入
	 * 运行结果："张三"、"李四"均插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年11月1日 14:26:56
	 */
	@Test
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
	 * 外围方法未开启事务，内围方法也未开启事务，默认无事务，每次调用dao数据均会插入
	 * 运行结果："张三"、"李四"均插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年11月1日 14:27:00
	 */
	@Test
	public void notransaction_no_no_exception() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.noTransactional(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.noTransactionalException(user2);
	}
}

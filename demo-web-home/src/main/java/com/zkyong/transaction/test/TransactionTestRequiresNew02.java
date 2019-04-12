package com.zkyong.transaction.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zkyong.transaction.model.User1;
import com.zkyong.transaction.model.User2;
import com.zkyong.transaction.service.User1Service;
import com.zkyong.transaction.service.User2Service;

/**
 * 场景二：外围方法开启事务。
 * 结论：
 * 		在外围方法开启事务的情况下Propagation.REQUIRES_NEW修饰的内部方法依然会单独开启独立事务，
 * 		且与外部方法事务也独立，内部方法之间、内部方法和外部方法事务均相互独立，互不干扰。
 * 
 * @author: zhangkaiyong
 * @date: 2018年10月12日 13:58:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TransactionTestRequiresNew02 {
	@Autowired
	private User1Service user1Service;
	@Autowired
	private User2Service user2Service;

	/**
	 * 外围方法开启事务，插入"张三"方法和外围方法一个事务，插入"李四"方法、插入"王五"方法分别在独立的新建事务中，
	 * 外围方法抛出异常只回滚和外围方法同一事务的方法，故插入"张三"的方法回滚。
	 * 运行结果："张三"未插入，"李四"插入，"王五"插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年10月12日 14:02:07
	 */
	@Test
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_exception_required_requiresNew_requiresNew() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.addRequired(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.addRequiresNew(user2);

		User2 user3 = new User2();
		user3.setName("王五");
		user2Service.addRequiresNew(user3);
		throw new RuntimeException();
	}

	/**
	 * 外围方法开启事务，插入"张三"方法和外围方法一个事务，插入"李四"方法、插入"王五"方法分别在独立的新建事务中。
	 * 插入"王五"方法抛出异常，首先插入 "王五"方法的事务被回滚，异常继续抛出被外围方法感知，外围方法事务亦被回滚，故插入"张三"方法也被回滚。
	 * 运行结果："张三"未插入，"李四"插入，"王五"未插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年10月12日 14:02:11
	 */
	@Test
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_required_requiresNew_requiresNew_exception() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.addRequired(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.addRequiresNew(user2);

		User2 user3 = new User2();
		user3.setName("王五");
		user2Service.addRequiresNewException(user3);
	}
	
	/**
	 * 外围方法开启事务，插入"张三"方法和外围方法一个事务，插入"李四"方法、插入"王五"方法分别在独立的新建事务中。
	 * 插入"王五"方法抛出异常，首先插入"王五"方法的事务被回滚，异常被catch不会被外围方法感知，外围方法事务不回滚，故插入"张三"方法插入成功。
	 * 运行结果："张三"插入，"李四"插入，"王五"未插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年10月12日 14:02:16
	 */
	@Test
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_required_requiresNew_requiresNew_exception_try() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.addRequired(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.addRequiresNew(user2);
		User2 user3 = new User2();
		user3.setName("王五");
		try {
			user2Service.addRequiresNewException(user3);
		} catch (Exception e) {
			System.out.println("回滚");
		}
	}
}

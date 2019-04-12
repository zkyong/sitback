package com.zkyong.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zkyong.demo.model.User1;
import com.zkyong.demo.model.User2;
import com.zkyong.demo.service.User1Service;
import com.zkyong.demo.service.User2Service;

/**
 * 场景二：外围方法开启事务，这个是使用率比较高的场景。
 * 结论：
 * 		试验结果证明，在外围方法开启事务的情况下Propagation.REQUIRED修饰的内部方法会加入到外围方法的事务中，
 * 		所有Propagation.REQUIRED修饰的内部方法和外围方法均属于同一事务，只要一个方法回滚，整个事务均回滚。
 * 
 * @author: zhangkaiyong
 * @date: 2018年10月12日 13:35:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TransactionTestRequired02 {
	@Autowired
	private User1Service user1Service;
	@Autowired
	private User2Service user2Service;

	/**
	 * 外围方法开启事务，内部方法加入外围方法事务，外围方法回滚，内部方法也要回滚。 
	 * 运行结果："张三"、"李四"均未插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年10月12日 13:36:57
	 */
	@Test
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_exception_required_required() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.addRequired(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.addRequired(user2);

		throw new RuntimeException();
	}

	/**
	 * 外围方法开启事务，内部方法加入外围方法事务，内部方法抛出异常回滚，外围方法感知异常致使整体事务回滚。
	 * 运行结果："张三"、"李四"均未插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年10月12日 13:37:50
	 */
	@Test
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_required_required_exception() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.addRequired(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.addRequiredException(user2);
	}

	/**
	 * 外围方法开启事务，内部方法加入外围方法事务，内部方法抛出异常回滚，即使方法被catch不被外围方法感知，整个事务依然回滚。
	 * 运行结果："张三"、"李四"均未插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年10月12日 13:38:05
	 */
	@Test
	@Transactional(propagation = Propagation.REQUIRED)
	public void transaction_required_required_exception_try() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.addRequired(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		try {
			user2Service.addRequiredException(user2);
		} catch (Exception e) {
			System.out.println("方法回滚");
		}
	}
}

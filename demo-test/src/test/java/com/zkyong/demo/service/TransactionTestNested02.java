package com.zkyong.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zkyong.demo.model.User1;
import com.zkyong.demo.model.User2;
import com.zkyong.demo.service.User1Service;
import com.zkyong.demo.service.User2Service;

/**
 * 场景二：外围方法开启事务。
 * 结论：
 * 		试验结果证明，在外围方法开启事务的情况下Propagation.NESTED修饰的内部方法属于外部事务的子事务，
 * 		外围主事务回滚，子事务一定回滚，而内部子事务可以单独回滚而不影响外围主事务和其他子事务
 * 		
 * 
 * @author: zhangkaiyong
 * @date: 2018年10月12日 14:14:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TransactionTestNested02 {
	@Autowired
	private User1Service user1Service;
	@Autowired
	private User2Service user2Service;

	/**
	 * 外围方法开启事务，内部事务为外围事务的子事务，外围方法回滚，内部方法也要回滚。
	 * 运行结果："张三"、"李四"均未插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年10月12日 14:15:03
	 */
	@Test
	@Transactional
	public void transaction_exception_nested_nested() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.addNested(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.addNested(user2);
		throw new RuntimeException();
	}

	/**
	 * 外围方法开启事务，内部事务为外围事务的子事务，内部方法抛出异常回滚，且外围方法感知异常致使整体事务回滚。
	 * 运行结果："张三"、"李四"均未插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年10月12日 14:15:39
	 */
	@Test
	@Transactional
	public void transaction_nested_nested_exception() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.addNested(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.addNestedException(user2);
	}
	
	/**
	 * 外围方法开启事务，内部事务为外围事务的子事务，插入"李四"内部方法抛出异常，可以单独对子事务回滚。
	 * 运行结果："张三"插入、"李四"未插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年10月12日 14:16:03
	 */
	@Test
	@Transactional
	public void transaction_nested_nested_exception_try() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.addNested(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		try {
			user2Service.addNestedException(user2);
		} catch (Exception e) {
			System.out.println("方法回滚");
		}
	}
}

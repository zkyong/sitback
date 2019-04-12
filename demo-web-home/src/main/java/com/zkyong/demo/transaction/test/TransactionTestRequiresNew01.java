package com.zkyong.demo.transaction.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zkyong.demo.transaction.model.User1;
import com.zkyong.demo.transaction.model.User2;
import com.zkyong.demo.transaction.service.User1Service;
import com.zkyong.demo.transaction.service.User2Service;

/**
 * 场景一：外围方法没有开启事务。 
 * 结论：
 * 		通过这两个方法我们证明了在外围方法未开启事务的情况下Propagation.REQUIRES_NEW修饰的内部方法会新开启自己的事务，
 * 		且开启的事务相互独立，互不干扰。
 * 
 * @author: zhangkaiyong
 * @date: 2018年10月12日 13:54:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TransactionTestRequiresNew01 {
	@Autowired
	private User1Service user1Service;
	@Autowired
	private User2Service user2Service;

	/**
	 * 外围方法没有事务，插入"张三"、"李四"方法都在自己的事务中独立运行,外围方法抛出异常回滚不会影响内部方法。 
	 * 运行结果："张三"插入，"李四"插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年10月12日 13:55:19
	 */
	@Test
	public void notransaction_exception_requiresNew_requiresNew() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.addRequiresNew(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.addRequiresNew(user2);
		
		throw new RuntimeException();
	}

	/**
	 * 外围方法没有开启事务，插入"张三"方法和插入"李四"方法分别开启自己的事务，插入"李四"方法抛出异常回滚，其他事务不受影响。
	 * 运行结果："张三"插入，"李四"未插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年10月12日 13:56:01
	 */
	@Test
	public void notransaction_requiresNew_requiresNew_exception() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.addRequiresNew(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.addRequiresNewException(user2);
	}
}

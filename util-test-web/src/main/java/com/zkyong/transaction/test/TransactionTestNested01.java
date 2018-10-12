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
 * 		试验结果证明，在外围方法未开启事务的情况下Propagation.NESTED和Propagation.REQUIRED作用相同，修饰的内部方法都会新开启自己的事务，
 * 		且开启的事务相互独立，互不干扰。
 * 
 * @author: zhangkaiyong
 * @date: 2018年10月12日 14:10:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TransactionTestNested01 {
	@Autowired
	private User1Service user1Service;
	@Autowired
	private User2Service user2Service;

	/**
	 * 外围方法未开启事务，插入"张三"、"李四"方法在自己的事务中独立运行，外围方法异常不影响内部插入"张三"、"李四"方法独立的事务。
	 * 运行结果："张三"、"李四"均插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年10月12日 14:11:25
	 */
	@Test
	public void notransaction_exception_nested_nested() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.addNested(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.addNested(user2);
		
		throw new RuntimeException();
	}

	/**
	 * 外围方法没有事务，插入"张三"、"李四"方法都在自己的事务中独立运行,所以插入"李四"方法抛出异常只会回滚插入"李四"方法，插入"张三"方法不受影响。
	 * 运行结果："张三"插入，"李四"未插入。
	 * 
	 * @author: zhangkaiyong
	 * @date: 2018年10月12日 14:11:52
	 */
	@Test
	public void notransaction_nested_nested_exception() {
		User1 user1 = new User1();
		user1.setName("张三");
		user1Service.addNested(user1);

		User2 user2 = new User2();
		user2.setName("李四");
		user2Service.addNestedException(user2);
	}
}

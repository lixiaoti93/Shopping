package TestCase;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.User;

import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.impl.UserServiceImpl;

public class testCase {
	private AbstractApplicationContext ac;
	private UserMapper userMapper;

	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext("spring-dao.xml");
		userMapper = ac.getBean("userMapper", UserMapper.class);
	}

	@After
	public void doAfter() {
		ac.close();
	}

	@Test
	public void insert() {
		User user = new User();
		user.setId(2);
		user.setUsername("李白25");
		user.setPassword("libai");
		user.setPhone("12345678963");
		Integer in = userMapper.insert(user);
		System.out.println("in:" + in);

	}

	@Test
	public void getUserByUsername() {
		String username = "李白";
		User user = userMapper.getUserByUsername(username);
		System.out.println("user:" + user);
	}

	@Test
	public void reg() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-dao.xml", "spring-service.xml",
				"spring-mvc.xml");
		IUserService userService = ac.getBean("userService", IUserService.class);
		User user = new User();
		user.setUsername("貂蝉");
		user.setPassword("libai");
		user.setPhone("12345678963");
		User result = userService.reg(user);
		try {
			System.out.println("注册成功" + result);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void getUserById() {
		Integer id = 2;
		User result = userMapper.getUserById(id);

		System.out.println("user:" + result);

	}

	@Test
	public void changePassword() {

		Integer result = userMapper.changePassword(1, "lixiaoti");

		System.out.println("user:" + result);

	}

	@Test
	public void changeInfo() {
		User user = new User();
		user.setId(19);
		user.setUsername("你喜不喜我");
		user.setAvatar("66");
		user.setPhone("17539097785");
		user.setGender(1);
		user.setModifiedTime(new Date());
		user.setModifiedUser("你喜不喜欢我");

		Integer result = userMapper.changeInfo(user);

		System.out.println("user:" + result);

	}

}

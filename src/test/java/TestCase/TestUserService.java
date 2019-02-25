package TestCase;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.User;

import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;

public class TestUserService {
	private AbstractApplicationContext ac;
	private IUserService userService;

	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext("spring-dao.xml", "spring-service.xml");
		userService = ac.getBean("userService", IUserService.class);
	}

	public void doAfter() {
		ac.close();
	}

	@Test
	public void reg() {
		User user = new User();
		user.setUsername("我只喜欢你");
		user.setPassword("123456");
		user.setPhone("12345678963");
		try {
			User result = userService.reg(user);
			System.out.println("result:" + result);
		} catch (UsernameConflictException e) {
			System.out.println(e.getMessage());
		} catch (InsertDataException e) {
			System.out.println(e.getMessage());
		}
		ac.close();

	}

	@Test
	public void login() {

		try {
			String username = "我只喜欢你";
			String password = "123456";
			User result = userService.login(username, password);
			System.out.println("登录成功" + result);
		} catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (PasswordNotMatchException e) {
			System.out.println(e.getMessage());
		}
		ac.close();

	}

	@Test
	public void changePassword() {

		try {
			userService.changePassword(2, "lixiaobai");
			System.out.println("修改成功");
		} catch (UpdateDataException e) {
			System.out.println(e.getMessage());
		}
		ac.close();

	}

	@Test
	public void changePasswordByOldPassword() {

		try {
			userService.changePasswordByOldPassword(2, "123456", "我叫李小逖");
			System.out.println("修改成功");
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
		ac.close();

	}
	@Test
	public void changeInfo() {
		User user=new User();
		user.setId(18);
		user.setUsername("123654");
		user.setAvatar("66");
		user.setPhone("17539097785");
		user.setGender(1);
		try {
		userService.changeInfo(user);
		System.out.println("修改成功");
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
		ac.close();

	}
}

package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.entity.User;

import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserMapper userMapper;

	public User reg(User user) throws UsernameConflictException, InsertDataException {
		// 根据用户名查询用户数据信息
		User result = getUserByUsername(user.getUsername());
		// 判断是否查到信息
		if (result == null) {
			// 否:用户名可用，执行注册
			insert(user);
			// 返回
			return user;
		} else {
			// 是：用户名已经被占用，抛出UsernameConflictException异常
			throw new UsernameConflictException("您尝试的注册用户名(" + user.getUsername() + ")已被占用");

		}

	}

	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		// 根据输入username来查找数据
		User result = getUserByUsername(username);
		// 判断result是否为空
		if (result != null) {
			// 否，获取盐值，根据盐值和输入的password根据md5加密获取加密值，与数据库中的password进行比较
			String md5Password = getEncryptedPassword(password, result.getSalt());
			// 判断密码是否匹配
			if (md5Password.equals(result.getPassword())) {
				// 是，登录成功返回user
				return result;
			} else {
				// 否，返回密码不匹配异常
				throw new PasswordNotMatchException("输入密码错误");
			}

		} else {
			// 是，返回用户不存在异常
			throw new UserNotFoundException("用户名(" + username + ")不存在");
		}

	}
	public void changeInfo(User user) throws UserNotFoundException,UpdateDataException {
		User result=getUserById(user.getId());
		if(result!=null) {
			//补充日志即：modifiedUser和modifiedTime
			//修改人信息
			user.setModifiedUser(user.getUsername()==null?result.getUsername():user.getUsername());
			//修改日期
			user.setModifiedTime(new Date());
			Integer rows=userMapper.changeInfo(user);
			if(rows!=1) {
				throw new UpdateDataException("数据修改异常，请联系管理员。");
			}
		}else {
			//用户不存在
			throw new UserNotFoundException("尝试访问的用户数据未找到");
		}
		
	}

	public void insert(User user) throws InsertDataException {
		  //加密密码
		 
		String salt = getRandomSalt();
		String md5Password = getEncryptedPassword(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(md5Password);
		// 为用户没有提交的属性设置值
		user.setStatus(1);
		user.setIsDelete(0);
		// 设置用户数据的日志
		Date now = new Date();
		user.setCreatedUser(user.getUsername());
		user.setCreatedTime(now);
		user.setModifiedUser(user.getUsername());
		user.setModifiedTime(now);
		// 执行注册
		Integer rows = userMapper.insert(user);
		// 判断执行结果
		if (rows != 1) {
			throw new InsertDataException("注册发生异常，请联系管理员");
		}

	}

	public User getUserByUsername(String username) {

		return userMapper.getUserByUsername(username);

	}

	public User getUserById(Integer uid) {
		return userMapper.getUserById(uid);
	}

	public void changePasswordByOldPassword(Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException, PasswordNotMatchException, UpdateDataException {
		// 根据id查找用户信息
		User user = getUserById(uid);
		// 检查是否查找到用户信息
		if (user != null) {
			// 存在：
			// -- 将用户输入的秘密加密
			String str = getEncryptedPassword(oldPassword, user.getSalt());
			// --判断原密码是否正确
			if (user.getPassword().equals(str)) {
				// -----正确：将新密码加密，执行修改密码
				String newMd5Pssword=getEncryptedPassword(newPassword, user.getSalt());
				Integer rows = userMapper.changePassword(uid, newMd5Pssword);
				if (rows != 1) {
					throw new UpdateDataException("修改密码时候出现异常错误，请联系管理员");
				}
			} else {
				// -----错误：抛出PasswordNotMatchException异常
				throw new PasswordNotMatchException("输入原密码错误，请重新输入");
			}

		} else {
			// 用户不存在，抛出UserNotFoundException异常
			throw new UserNotFoundException("用户不存在");
		}

	}

	public void changePassword(Integer uid, String password) throws UpdateDataException {
		// 调用持久层对象的同名方法实现修改功能
		Integer rows = userMapper.changePassword(uid, password);
		// 由于是根据用户id来改变，正确操作时应返回受影响的行数是1，若不是1，则视为操作错误
		if (rows != 1) {
			throw new UpdateDataException("修改密码时候出现异常错误，请联系管理员");
		}
	}

	/**
	 * 获取盐值
	 * 
	 * @return 得到的盐值
	 */
	private String getRandomSalt() {
		return UUID.randomUUID().toString().toUpperCase();
	}

	/**
	 * 获取加密后的密码
	 * 
	 * @param src
	 *            原始密码
	 * @param salt
	 *            盐值
	 * @return 加密后密码
	 */
	private String getEncryptedPassword(String src, String salt) {
		// 将原密码加密
		String s1 = md5(src);
		// 将盐加密
		String s2 = md5(salt);
		// 将两次加密结果拼接，再加密
		String s3 = s1 + s2;
		String result = md5(s3);
		// 将以上结果再加密五次
		for (int i = 0; i < 5; i++) {
			result = md5(result);
		}
		// 返回
		return result;
	}

	private String md5(String str) {
		return DigestUtils.md5DigestAsHex(str.getBytes()).toUpperCase();
	}

	

}

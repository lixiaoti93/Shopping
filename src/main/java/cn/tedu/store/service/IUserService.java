package cn.tedu.store.service;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;

public interface IUserService {
	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户信息
	 * @return 成功注册的数据
	 * @throws UsernameConflictException
	 */
	User reg(User user) throws UsernameConflictException,InsertDataException;

	/**
	 * 插入用户数据
	 * 
	 * @param user
	 * @return 受影响的行数
	 */
	void insert(User user) throws InsertDataException;

	/**
	 * 查找用户数据
	 * 
	 * @param username
	 *            输入用户名
	 * @return 查找到的数据
	 */
	User getUserByUsername(String username);

	/**
	 * 用户登录
	 * 
	 * @param username
	 *            输入用户名
	 * @param password
	 *            输入密码
	 * @return 根据用户名查找到的用户数据
	 * @throws UserNotFoundException
	 *             用户未找到异常
	 * @throws PasswordNotMatchException
	 *             密码不匹配异常
	 */
	User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException;

	/**
	 * 根据用户id查询用户数据信息
	 * 
	 * @param uid
	 * @return
	 */
	User getUserById(Integer uid);
	/**
	 * 通过验证老密码来修改密码
	 * @param uid 用户id
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @throws UserNotFoundException 用户未发现异常
	 * @throws PasswordNotMatchException 密码错误异常
	 * @throws UpdateDataException 密码修改错误异常
	 */
	void changePasswordByOldPassword(Integer uid,String oldPassword,String newPassword)
	throws UserNotFoundException,PasswordNotMatchException,UpdateDataException;

	/**
	 * 
	 * @param uid
	 *            用户id
	 * @param password
	 *            用户新输入的密码
	 * @throws UpdateDataException
	 *             密码修改错误异常
	 */
	void changePassword(Integer uid, String password) throws UpdateDataException;
	/**
	 * 根据用户输入数据修改用户的数据
	 * @param user 用户输入数据
	 * @throws UserNotFoundException 用户不存在异常
	 * @throws UpdateDataException 用户信息更新失败异常
	 */
	void changeInfo(User user)throws UserNotFoundException,UpdateDataException;;

}

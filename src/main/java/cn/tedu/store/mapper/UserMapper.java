package cn.tedu.store.mapper;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;


public interface UserMapper {
	/**
	 * 插入用户数据
	 * @param user 用户数据
	 * @return 返回受影响的行数
	 */
	Integer insert(User user);
	/**
	 * 根据用户名查找用户数据
	 * @param username 用户名
	 * @return 查找到的用户数据
	 */
	User getUserByUsername(String username);
	/**
	 * 根据用户id来查询用户数据
	 * @param id
	 * @return 查到的数据
	 */
	User getUserById(Integer uid);
	/**
	 * 修改用户密码
	 * @param uid
	 * @param password
	 * @return 受影响的行数
	 */
	Integer changePassword(@Param("uid")Integer uid,@Param("password")String password);
	
	/**
	 * 根据用户输入信息修改用户数据。包括用户名、性别、头像、电话、邮箱
	 * @param user 被修改的用户的新信息，至少包括用户id，可修改的数据
	 * @return 受影响的行数
	 */
	Integer changeInfo(User user);

}

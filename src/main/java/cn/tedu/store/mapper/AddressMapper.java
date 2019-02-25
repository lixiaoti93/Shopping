package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Address;

public interface AddressMapper {
	/**
	 * 插入收获地址信息
	 * @param address
	 * @return 受影响的行数
	 */
	Integer insert(Address address);
	/**
	 * 根据uid获取用户的收获地址数量
	 * @param uid
	 * @return
	 */
	Integer getCountByUid(Integer uid);
	/**
	 * 根据uid获取收获地址的详情
	 * @param uid
	 * @return 全部的收获地址
	 */
	List<Address> getList(Integer uid);
	
	
	/**
	 * 设置地址为非默认
	 * @param uid
	 * @return 受影响的行数
	 */
	Integer setNonDefault(Integer uid);
	/**
	 * 设置某个地址为默认收获地址
	 * @param uid 
	 * @param id 某个地址
	 * @return 受影响的行数
	 */
	Integer setDefault(@Param("uid")Integer uid,@Param("id")Integer id);

}

package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.InsertDataException;

public interface IAddressService {
	/**
	 * 新增收获地址
	 * @param address 新增地址
	 * @return 返回的新增地址
	 * @throws InsertDataException 数据插入异常
	 */
	Address addNew(String currentUser,Address address)throws InsertDataException;
	/**
	 * 获取地址列表
	 * @param uid  用户id
	 * @return 地址列表
	 */
	List<Address> getList(Integer uid);
	/**
	 * 设置某用户的某条收获地址为默认收获地址
	 * @param uid 用户id
	 * @param id 地址id
	 */
	void setDefaultAddress(Integer uid,Integer id);

}

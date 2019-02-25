package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.UpdateDataException;

@Service("addressService")
public class AddressServiceImpl implements IAddressService {
	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private IDistrictService districtService;

	public Address addNew(String currentUser, Address address) throws InsertDataException {
		// 根据省市区的代号，获取它们的名称
		String recvDistrict = getRecvDistrict(address);
		address.setRecvDistrict(recvDistrict);
		// 业务：当前增加的地址是否为默认的收货地址
		// --是，设置为默认收获地址 address.setIsDefault
		// --否，设置为非默认收获地址
		Integer count = getCountByUid(address.getUid());
		address.setIsDefault(count == 0 ? 1 : 0);
		// 执行
		insert(currentUser, address);
		return address;

	}

	// 该注解表示该方法的执行是有事务保护的，即开启了事务，如果全部成功，提交事务，失败时会会滚事务。
	@Transactional
	public void setDefaultAddress(Integer uid, Integer id) {
		// 先将用户的所有地址都设置为非默认
		setNonDefault(uid);
		System.out.println("已将该用户的所有地址都设置为非默认");
		// 将某个地址设置为默认地址
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefault(uid, id);
	}

	/**
	 * 获取用户的地址列表，
	 */
	public List<Address> getList(Integer uid) {
		List<Address> address = addressMapper.getList(uid);
		return address;
	}

	/**
	 * 插入收获地址数据
	 * 
	 * @param address
	 *            收货地址数据
	 * @throws InsertDataException
	 */
	private void insert(String currentUser, Address address) throws InsertDataException {
		// 添加日志数据
		Date date = new Date();
		address.setCreatedTime(date);
		address.setCreatedUser(currentUser);
		address.setModifiedUser(currentUser);
		address.setModifiedTime(date);
		Integer rows = addressMapper.insert(address);
		if (rows != 1) {
			throw new InsertDataException("增加收货地址时候出现未知错误，请联系系统管理员。");
		}

	}

	private Integer getCountByUid(Integer uid) {
		return addressMapper.getCountByUid(uid);

	}

	/**
	 * 根据地址获取省市区的中文名
	 * 
	 * @param address
	 * @return 省市区的中文名
	 */
	private String getRecvDistrict(Address address) {

		District province = districtService.getInfo(address.getRecvProvince());
		District city = districtService.getInfo(address.getRecvCity());
		District area = districtService.getInfo(address.getRecvArea());
		String recvDistrict = province.getName() + city.getName() + area.getName();
		return recvDistrict;
	}

	/**
	 * 设置地址为非默认
	 * 
	 * @param uid
	 * @return 受影响的行数
	 */
	private Integer setNonDefault(Integer uid) {
		Integer rows = addressMapper.setNonDefault(uid);
		if (rows == 0) {
			throw new UpdateDataException("地址设置默认异常失败，请联系系统管理员！！！");
		}
		return rows;
	}

	/**
	 * 设置某个地址为默认收获地址
	 * 
	 * @param uid
	 * @param id
	 *            某个地址
	 * @return 受影响的行数
	 */
	private Integer setDefault(Integer uid, Integer id) {
		Integer rows = addressMapper.setDefault(uid, id);
		if (rows == 0) {
			throw new UpdateDataException("地址设置默认异常失败，请联系系统管理员！！！");
		}
		return rows;
	}

}

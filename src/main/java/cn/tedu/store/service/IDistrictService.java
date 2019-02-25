package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.District;

public interface IDistrictService {
	String PROVINCE_PARENT="86";
	/**
	 * 获取省市区信息列表
	 * @param parent
	 * @return
	 */
	List<District> getList(String parent);
	/**
	 * 获取某个省市区的信息
	 * @param code
	 * @return
	 */
	District getInfo(String code);

}

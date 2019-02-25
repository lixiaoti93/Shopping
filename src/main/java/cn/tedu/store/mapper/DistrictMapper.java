package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.District;

public interface DistrictMapper {
	String PROVINCE_PARENT="86";
	
	/**
	 * 获取省/市/区的列表
	 * @param parent 父级的代号
	 * @return 省/市/区的列表
	 * @see#PROVINCE_PARENT
	 */
	List<District> getList(String parent);
	/**
	 * 获取省/市/区的详情列表
	 * @param code
	 * @return
	 */
	District getInfo(String code);

}

package TestCase;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.DistrictMapper;
import cn.tedu.store.mapper.UserMapper;

public class TestDistrict {
	private AbstractApplicationContext ac;
	private DistrictMapper districtMapper;

	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext("spring-dao.xml");
		districtMapper=ac.getBean("districtMapper", DistrictMapper.class);
	}

	@After
	public void doAfter() {
		ac.close();
	}
	@Test
	public void getList() {
		List<District> list=districtMapper.getList("411600");
		for(District district:list) {
			System.out.println(district);
		}
		
	}
	@Test
	public void getInfo() {
		District ditrict =districtMapper.getInfo("411600");
		System.out.println(ditrict);
	}

}

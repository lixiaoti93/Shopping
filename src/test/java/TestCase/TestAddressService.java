package TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.IAddressService;

public class TestAddressService {
	private AbstractApplicationContext ac;
	private IAddressService addressService;
	@Before
	public void doBefore() {
		ac=new ClassPathXmlApplicationContext("spring-service.xml","spring-dao.xml");
		addressService=ac.getBean("addressService", IAddressService.class);
	}
	@After
	public void doAfter() {
		ac.close();
	}
	@Test
	public void addnew() {

		String currentUser="孙尚香";
		
		Address address=new Address();
		address.setId(18);
		Address result=addressService.addNew(currentUser, address);
		System.out.println(result);
	}
	@Test
	public void setDefaultAddress() {
		addressService.setDefaultAddress(23, 15);
		System.out.println("设置成功");
	}
DataSourceTransactionManager aa;
}

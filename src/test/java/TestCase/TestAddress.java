package TestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.Address;
import cn.tedu.store.mapper.AddressMapper;

public class TestAddress {
	
	private AbstractApplicationContext ac;
	private AddressMapper addressMapper;
	@Before
	public void doBefore() {
		ac=new ClassPathXmlApplicationContext("spring-dao.xml");
		 addressMapper=ac.getBean("addressMapper",AddressMapper.class);
	}
	@After
	public void doAfter() {
		ac.close();
	}
  @Test
	public void insert() {
	  Address address=new Address();
	  address.setUid(2);
	  address.setRecvName("李雪兵");
	  addressMapper.insert(address);
	  System.out.println("添加成功");
		
	}
  @Test
	public void getCountByUid() {
	  
	 Integer rows= addressMapper.getCountByUid(18);
	  
	  System.out.println(rows);
		
	}
  @Test
	public void getList() {
	  
	 List<Address> address= addressMapper.getList(23);
	 for(Address ad:address) {
		 System.out.println(ad);
	 }
	
	 
		
	}
  @Test
	public void changeIsDefault() {
	  
	addressMapper.setNonDefault(23);
    addressMapper.setDefault(23, 17);
	 }



}

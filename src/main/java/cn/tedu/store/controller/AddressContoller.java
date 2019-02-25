package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.service.IAddressService;

@Controller
@RequestMapping("/address")
public class AddressContoller extends BaseController {
	@Autowired
	IAddressService addressService;
	@RequestMapping(value="/handle_addnew.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleAddNew(HttpSession session,Address address){
		//从session中获取当前登录的用户的用户名
		String username=(String) session.getAttribute("username");
		//从session中获取当前登录的用户的id
		Integer uid=Integer.valueOf(session.getAttribute("uid").toString());
		//将用户id封装到参数address对象
		address.setUid(uid);
		//调用业务层对象的addnew方法
		addressService.addNew(username, address);
		return new ResponseResult<Void>();
	}
	@RequestMapping("/list.do")
	@ResponseBody
	public ResponseResult<List<Address>> list(HttpSession session){
		//获取uid
		Integer uid=Integer.valueOf(session.getAttribute("uid").toString());
		//调用业务层方法来获取地址列表
		List<Address> addresses=addressService.getList(uid);
		ResponseResult<List<Address>> rr=new ResponseResult<List<Address>>();
		//封装到返回返回值中
		rr.setData(addresses);
		return rr;
	}

	

}

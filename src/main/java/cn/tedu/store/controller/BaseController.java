package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.UploadAvatarException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;

public abstract class BaseController {
	
	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public ResponseResult<Void> handleException(Exception e) {
		//准备返回值
		//判断异常的类型
		if(e instanceof UsernameConflictException) {
			//用户名冲突异常
			return new ResponseResult<Void>(401,e);
		}else if(e instanceof UserNotFoundException) {
			//未发现用户异常
			return new ResponseResult<Void>(402,e);
		}else if(e instanceof PasswordNotMatchException) {
			//密码错误异常
			return new ResponseResult<Void>(403,e);
		}else if(e instanceof UpdateDataException) {
			//数据修改错误异常
			return new ResponseResult<Void>(405, e);
		}else if(e instanceof UploadAvatarException) {
			//上传头像失败异常
			return new ResponseResult<Void>(406,e);
		}else if(e instanceof InsertDataException) {
			//插入数据错误异常
			return new ResponseResult<Void>(501,e);
		}else {
			//未知异常
			return new ResponseResult<Void>(600,e);
		}
		
		
	}
	/**
	 * 根据session对象获取uid
	 * @param session session对象
	 * @return 获取session对象绑定的uid
	 */
	protected final Integer getUidFromSession(HttpSession session) {
		return Integer.valueOf(session.getAttribute("uid").toString());
		
	}

}

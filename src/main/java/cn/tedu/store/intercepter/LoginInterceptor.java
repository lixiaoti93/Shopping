package cn.tedu.store.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)throws Exception {
		System.out.println("LoginInterceptor.preHandle()");
		//获取session
		HttpSession session=request.getSession();
		//判断session中是否有登录信息
		if(session.getAttribute("username")==null) {
			//没有登录信息，则重定向到登录页
			response.sendRedirect("../user/login.html");
			return false;
		}else {
			//有登录信息，则允许正常访问,直接放行
			return true;
			
		}
	
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	

	} 


}

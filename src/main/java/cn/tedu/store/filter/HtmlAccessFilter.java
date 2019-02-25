package cn.tedu.store.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HtmlAccessFilter implements Filter{
	
	private List<String> accessableHtml;
	
	public void init(FilterConfig arg0) throws ServletException {
		//初始化方法
		System.out.println("HtmlAccessFilter.init()");
		//穷举允许直接访问的html文件
		accessableHtml=new ArrayList<String>();
		accessableHtml.add("register.html");
		accessableHtml.add("login.html");
	}
	
	public void doFilter(ServletRequest str0, ServletResponse str1, FilterChain filterChain)
			throws IOException, ServletException {
		//执行过滤的方法
		System.out.println("HtmlAccessFilter.doFilter()");
		//获取请求路径
		HttpServletRequest request=(HttpServletRequest)str0;
		String uri=request.getRequestURI();
		System.out.println("\turi="+uri);
		String[] pathArray=uri.split("/");
		String file=pathArray[pathArray.length-1];
		System.out.println("\tfile="+file);
		//判断当前页面是否直接放行
		if(accessableHtml.contains(file)) {
			System.out.println("\t【放行】");
			//继续执行过滤器链中后续的其它过滤器
			filterChain.doFilter(str0, str1);
			return;
		}
			System.out.println("\t【拦截】");
			//判断是否登录
			HttpSession session=request.getSession();
			if(session.getAttribute("uid")!=null) {
				//session中有uid，即已登录，放行
				filterChain.doFilter(request, str1);
				return;
			}
				//重定向到登录页
				HttpServletResponse response=(HttpServletResponse)str1;
				String loginURI=request.getContextPath()+"/web/login.html";
				System.out.println(loginURI);
				response.sendRedirect(loginURI);		
	}

	public void destroy() {
		//
		
		
	}


}

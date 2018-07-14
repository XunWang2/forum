package com.jsm.forum.config;

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

import com.jsm.forum.model.BasicUser;


public class MyFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	
		
	}
	
	private  List<String> list;

	@Override
	public void doFilter(ServletRequest request, ServletResponse respons, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session =  req.getSession();
		BasicUser user =  (BasicUser) session.getAttribute("user");
		String url = req.getRequestURI();
		if(user != null) {
			chain.doFilter(request, respons);
		}else if(list.contains(url) || url.contains(".")) {
			chain.doFilter(request, respons);
			
		}else {
			
			System.out.println("进来了"+url);
		 	HttpServletResponse repons =  (HttpServletResponse) respons;
		 	repons.sendRedirect("/forum/LoginAndRegMapping/login");
			
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		List<String> list = new ArrayList<String>();
		list.add("/forum/");
		list.add("/forum/detailMapping/detail2");
		list.add("/forum/LoginAndRegMapping/login");
		list.add("/forum/LoginAndRegMapping/reg");
		list.add("/forum/homeController/getData");
		list.add("/forum/detailController/addLook");
		list.add("/forum/detailController/detail");
		list.add("/forum/homeController/getCommentCount");
		list.add("/forum/detailController/getChildComment");
		list.add("/forum/LoginAndRegController/login");
		list.add("/forum/LoginAndRegController/CheckUserName");
		list.add("/forum/LoginAndRegController/reg");
		list.add(".");
		this.list = list;
	}

	
}

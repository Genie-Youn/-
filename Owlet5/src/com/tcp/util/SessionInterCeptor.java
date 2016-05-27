package com.tcp.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tcp.logger.LoggerConfig;

public class SessionInterCeptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		try{
			
			if(request.getSession().getAttribute("loginUserInfo")==null){
				response.sendRedirect("/login.do");
				
				return false;
			}
		}
		catch(Exception e){
			LoggerConfig.getEngineLogger().debug(e.getMessage());
		}
		return true;
	}
}

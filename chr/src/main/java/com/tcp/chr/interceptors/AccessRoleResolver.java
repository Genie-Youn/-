package com.tcp.chr.interceptors;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import com.tcp.chr.annotations.AccessRole;
import com.tcp.chr.annotations.AccessRole.Role;
import com.tcp.chr.entities.Account;
import com.tcp.chr.mappers.AccountMapper;

/**
 *  AccessRoleResolver
 * 
 *  접근 권한을 체크하는 어노테이션.
 *  @date 2016.05.25
 *  @author genie
 *  @sicne 1.0
 */
public class AccessRoleResolver extends WebContentInterceptor {
	
	private static final Logger LOG = LoggerFactory.getLogger(AccessRoleResolver.class);
	@Resource(name="accountMapper")
	private AccountMapper accountMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws ServletException{
		
		AccessRole anno = ((HandlerMethod)handler).getMethodAnnotation(AccessRole.class);
		Account ac = (Account)req.getSession().getAttribute("account");
		
		if(anno.role() == Role.selector){
			if(ac == null){
				Account guest = new Account();
				guest.setEmail("guest");
				guest.setSeqAccount((long)0);
				guest.setName("손님");
				req.getSession().setAttribute("account", guest);
			}
		}
		
		return true;
	}

}

package com.tcp.util;

import javax.servlet.http.HttpSession;

import org.eclipse.jdt.internal.compiler.lookup.MemberTypeBinding;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.MemberStoreMapBean;

public class SessionManager {

	private static SessionManager instance = null;

	public static SessionManager getInstance(){
		if(instance == null){
			instance = new SessionManager();
		}
		return instance;
	}

	/**
	 * Session 존재 유무 처리
	 * @return
	 */
	public boolean isSessionExist(){

		ServletRequestAttributes attr = (ServletRequestAttributes)
				RequestContextHolder.currentRequestAttributes();

		HttpSession session=attr.getRequest().getSession();

		Object checkSession = session.getAttribute("loginUserInfo");
		if(checkSession == null)
			return false;
		else
			return true;
	}

	/**
	 * Session이 존재할 경우 url 로 View를 던져준다
	 * 만약 그러지 않을 경우 login 창 강제 소환
	 * @param url
	 * @return
	 */
	public String isExistMoveLogin(String url){
		ServletRequestAttributes attr = (ServletRequestAttributes)
				RequestContextHolder.currentRequestAttributes();

		HttpSession session=attr.getRequest().getSession();

		Object checkSession = session.getAttribute("loginUserInfo");

		if(checkSession == null)
			return "login";
		else
			return url;
	}

	/**
	 * cmd 로 tcp 가 확인됐을 경우
	 * 해당 Session 의 StoreCode를 넘겨줍니다.
	 * @param cmd
	 * @return
	 */
	public int getSessionCode(String cmd){
		ServletRequestAttributes attr = (ServletRequestAttributes)
				RequestContextHolder.currentRequestAttributes();

		HttpSession session=attr.getRequest().getSession();

		if(cmd.equalsIgnoreCase("")){
			Object checkSession = session.getAttribute("loginUserInfo");

			MemberStoreMapBean bean = (MemberStoreMapBean)checkSession;

			return bean.getStoreCode();
		}else{
			return 0;
		}
	}

	/**
	 * session의 코드가 임의로 필요한 경우 사용하는 메소드
	 */
	public int getSessionCode(){
		MemberStoreMapBean bean=null;
		try{
			ServletRequestAttributes attr = (ServletRequestAttributes)
					RequestContextHolder.currentRequestAttributes();

			HttpSession session=attr.getRequest().getSession();

			Object checkSession = session.getAttribute("loginUserInfo");

			bean = (MemberStoreMapBean)checkSession;
		}catch(NullPointerException e){
			LoggerConfig.getWebLogger().error(e.getMessage());
			
			bean = new MemberStoreMapBean();
		}
		return bean.getStoreCode();
	}

	/**
	 * Session을 통째로..
	 * @return {@link MemberStoreMapBean}
	 */
	public MemberStoreMapBean getSession(){
		ServletRequestAttributes attr = (ServletRequestAttributes)
				RequestContextHolder.currentRequestAttributes();

		HttpSession session=attr.getRequest().getSession();

		Object checkSession = session.getAttribute("loginUserInfo");

		MemberStoreMapBean bean = (MemberStoreMapBean)checkSession;

		return bean;
	}
}

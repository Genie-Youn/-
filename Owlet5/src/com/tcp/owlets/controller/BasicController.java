package com.tcp.owlets.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.bean.AppInfo;
import com.tcp.owlets.bean.MemberBean;
import com.tcp.owlets.bean.MemberStoreMapBean;
import com.tcp.owlets.bean.StoreInfo;
import com.tcp.owlets.bean.StoreKindsBean;
import com.tcp.owlets.handler.AdderHandler;
import com.tcp.owlets.handler.BasicHandler;
import com.tcp.owlets.handler.MobileHandler;
import com.tcp.util.AppManager;
import com.tcp.util.CalendarFactory;
import com.tcp.util.FileManager;
import com.tcp.util.PathFactory;
import com.tcp.util.SessionManager;
import com.tcp.util.TaskManager;

/**
 * 로그인 부터 회원가입 기본 Page 관련한 
 * 사항은 여기에 전부 넣도록 한다.
 * @author blessldk
 *
 */
@Controller
public class BasicController {
	@Autowired
	BasicHandler basicService;
	
	@Autowired
	AdderHandler adderService;
	
	@Autowired
	MobileHandler mobileService;
	
	/**
	 * 일반적으로 jsp를 그냥 호출하고 싶다면 이렇게 하면된다.
	 * String 으로 return 값을 잡으면 알아서 호출됨
	 * @return
	 */
	@RequestMapping("/login")
	public String login(){
		
//		System.out.println(PathFactory.getInstance().getDefaultPath());
		
		/**
		 * Login 시에 모바일 화면 뿌려줄 기본 Path 정보 삽입.
		 */
		if(PathFactory.getInstance().getDefaultPath()==null)
			PathFactory.getInstance().setDefaultPath();
		
		if(PathFactory.getInstance().getIndexPath()==null)
			PathFactory.getInstance().setIndexPath();
		
		
		return "login";
	}

	@RequestMapping("/test")
	public String test(){
		FileManager.getInstance().getRealPath();
		return "joinConfirm";
	}

	@RequestMapping("/join")
	public String join(@ModelAttribute MemberBean memberBean){
		return "join";
	}


	/**
	 * mypage jsp 파일을 열어준다.
	 * @return
	 */
	@RequestMapping("/myPage")
	public ModelAndView getUserInfo(@ModelAttribute(value="userInfo") MemberBean userInfo ,
							@ModelAttribute(value="storeInfos") StoreInfo store){
		
		MemberStoreMapBean session = SessionManager.getInstance().getSession();
		
		MemberBean user = basicService.getMember(session.getnIndex());
		
		StoreInfo info = basicService.getStoreInfo(session.getStoreCode());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("store", info);
		
		return new ModelAndView("myPage" , "info" , map);
	}


	/**
	 * json 으로 member 정보 전달해주는 메소드
	 * json 하고 싶다면 produces="application/json" 만 하면된다.
	 * 원리를 알기까지는 어려웠으나 알고 나니 참쉽다.. Spring 만만세!
	 * 
	 * Json 전달하는 방법은 여러가지가 있으나 이 방법이 가장 편하고 쉬움
	 * 
	 *
	 * @param index
	 * @return memberBean
	 */
	@RequestMapping(value="/memberInfo" , produces="application/json")
	public @ResponseBody MemberBean getMember(@RequestParam int index){
		MemberBean bean = new MemberBean();
		bean = basicService.getMember(index);
		return bean;
	}

	/**
	 * 기본적인 Store 정보 (code와 Address) 와 관계맵을 함께 Insert 하는 메소드
	 * @param memberBean
	 * @param storeInfo
	 * @return
	 */
	@RequestMapping("/memberInsert") 
	public String getMember(@ModelAttribute MemberBean memberBean 
			, @ModelAttribute(value="storeInfo") StoreInfo storeInfo , @ModelAttribute StoreKindsBean storeKindsBean){
		basicService.insertMember(memberBean);
		basicService.insertStoreInfo(storeInfo);
		basicService.insertStoreKinds(storeKindsBean);
		basicService.insertMemStoreMap();
		
		
		return "joinConfirm";
	}

	/**
	 * login confirm method
	 * @param mail
	 * @param passwd
	 * @param session
	 * @return int (Success : 1 / fail : 0)
	 */
	@RequestMapping(value="/loginCheck" , method=RequestMethod.POST , produces="application/json")
	public @ResponseBody int checkLogin(@RequestParam(value="mail" , required=false) String mail ,
			@RequestParam(value="passwd" , required=false) String passwd , HttpSession session){

		MemberStoreMapBean bean = basicService.getCheckLogin(mail, passwd);

		// Member / nIndex를 가지고 왔다면, 로그인 성공..
		if(bean != null && bean.getnIndex() != 0){
			StoreInfo store = basicService.getStoreInfo(bean.getStoreCode());	// 통계 페이지 등에서 업체명이 필요할때가 있음
			
			session.setAttribute("loginUserInfo", bean);
			session.setAttribute("UserName", bean.getName());
			session.setAttribute("StoreName", store.getStoreName());
			session.setMaxInactiveInterval(60 * 30);
//			session.setMaxInactiveInterval(3);

			// 클라이언트는 MemberBean의 checkLogin이 1이면 성공, 0이면 실패.
			return 1;
		}else{
			return 0;
		}
	}

	@RequestMapping("/logout")
	public String logout(){
		ServletRequestAttributes attr = (ServletRequestAttributes)
				RequestContextHolder.currentRequestAttributes();
		
		Object attribute = attr.getAttribute("loginUserInfo", RequestAttributes.SCOPE_SESSION);
		
		if(attribute != null){

			MemberStoreMapBean bean = (MemberStoreMapBean)attr.getAttribute("loginUserInfo", RequestAttributes.SCOPE_SESSION);

			HttpServletRequest req = attr.getRequest();

			HttpSession session=req.getSession();

			if(session != null)
				session.invalidate();
			
			return "redirect:/login.do";
			
		}else{
			return "redirect:/login.do";
		}
	}

	/**
	 * myPage에서 수정된 사항에 대해  Update 한다.
	 * @param memberIndex
	 * @return
	 */
	@RequestMapping("/mypageUpdate")
	public String setMemberInfo(@ModelAttribute("userInfo") MemberBean memberBean, 
							@ModelAttribute("storeInfos") StoreInfo storeInfo , @RequestParam("logo") MultipartFile file){
		MemberStoreMapBean session = SessionManager.getInstance().getSession();
		
		basicService.updateMember(memberBean);
		basicService.updateStoreInfo(storeInfo, session.getStoreCode() , file);
		
		int index=adderService.isStoreFrameExist();
		
		if(index < 0){
			AdderInfo adderInfo = new AdderInfo();
			
			adderInfo.setTitle(storeInfo.getStoreName());
			adderInfo.setContent("");
			
			adderService.insertFrame(adderInfo, 2);
		}else{
			AdderInfo adderInfo = new AdderInfo();
			
			adderInfo.setnIndex(index);
			adderInfo.setTitle(storeInfo.getStoreName());
			adderInfo.setContent("");
			
			AppInfo info=adderService.getAppInfo(session.getStoreCode());
			
			info.setName(storeInfo.getStoreName());
			
			adderService.updateFrame(adderInfo);
			adderService.updateAppInfo(info, session.getStoreCode());
		}
		return "redirect:/myPage.do";
	}
	
	@RequestMapping(value="/storeInfo" , produces="application/json")
	public @ResponseBody StoreInfo getStoreInfo(@RequestParam("code") int storeCode,
						@RequestParam("num") int index){
		
		//스토어정보에 접근 시 PageReport 의 index를 1씩 증가 시킨다.
		mobileService.updatePageReport(index);
		
		//StorePage 접근시에도 VISIT COUNT 증가
		TaskManager.getInstance().setVisitData(index, storeCode, false);
		
		StoreInfo info = new StoreInfo();
		
		info =basicService.getStoreInfo(storeCode);
		
		return info;
	}
}

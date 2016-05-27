package com.tcp.owlets.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.handler.AdderHandler;
import com.tcp.util.SessionManager;

/**
 * Preview에 관련된 사항은 여기서 작업하면 됨.
 * 
 * 역시 간단함.
 * @author blessldk
 *
 */
@Controller
@RequestMapping("/preview")
public class PreViewController {
	@Autowired
	AdderHandler adderService;
	
	@RequestMapping("/list")
	public String getList(@ModelAttribute AdderInfo adderInfo){
		return "frameList";
	}
	
	/**
	 * JSON 으로 넘기는 방식과 ModelAttribute 에서 Map으로 넘기는 방법
	 * 같이 구현 
	 * @param adderInfo
	 * @return
	 */
	@RequestMapping(value="/framesData" , produces="application/json")
	public @ResponseBody ArrayList<AdderInfo> getListJson(){
		int code = SessionManager.getInstance().getSessionCode();
		
		ArrayList<AdderInfo> list = adderService.getFrameList(code);
		return list;
	}
	
	@RequestMapping("/frames")  
	public ModelAndView getFrameLists() {  
		int code = SessionManager.getInstance().getSessionCode();
		
		ArrayList<AdderInfo> list = adderService.getFrameList(code);
		
		return new ModelAndView("frameList", "frameList", list);  
	} 
	
	@RequestMapping("/totalUrl")  
	public ModelAndView getTotalUrls() {  
		int code = SessionManager.getInstance().getSessionCode();
		
		ArrayList<AdderInfo> list = adderService.getUrlList(code);
		
		return new ModelAndView("urlList", "frameList", list);  
	} 
	
	@RequestMapping("/frameList")
	public String getFrameList(){
		return "preview";
	}
	
	/**
	 * 프로그램에서 생성한 모든 Frame 정보를 가져간다.
	 * @return
	 */
	@RequestMapping(value="/FrameListAll" , produces="application/json")
	public @ResponseBody ArrayList<AdderInfo> getFrameListAll(){
		int code = SessionManager.getInstance().getSessionCode();
		
		ArrayList<AdderInfo> list = adderService.getTotalList(code);
		return list;
	}
}

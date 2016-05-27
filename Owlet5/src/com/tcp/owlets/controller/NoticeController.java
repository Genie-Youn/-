package com.tcp.owlets.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.EventNoticeBean;
import com.tcp.owlets.handler.NoticeHandler;
import com.tcp.util.SessionManager;

/**
 * 공지사항 관련 내용은 여기에서 작업한다.
 * @author blessldk
 *
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	NoticeHandler noticeService;

	@RequestMapping(value="/noticeList")
	public ModelAndView getNoticeList(){
		int code = SessionManager.getInstance().getSessionCode();
		ArrayList<EventNoticeBean> list = noticeService.getList(code);

		return new ModelAndView("notice" , "noticeList" , list);
	}

	@RequestMapping("/list")
	public String getList(){
		return "notice";
	}

	@RequestMapping(value="/noticeListToJson" , produces="application/json")
	public @ResponseBody ArrayList<EventNoticeBean> getNoticeListToJson(@RequestParam(value="code") int storeCode
			, @RequestParam(value="num") int frameIndex){
		ArrayList<EventNoticeBean> list = noticeService.getList(storeCode);

		return list;
	}
}

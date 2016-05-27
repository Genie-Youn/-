package com.tcp.chr.controllers;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tcp.chr.annotations.API;
import com.tcp.chr.annotations.API.Useage;
import com.tcp.chr.annotations.AccessRole;
import com.tcp.chr.annotations.AccessRole.Role;
import com.tcp.chr.entities.Account;
import com.tcp.chr.services.StoreService;
import com.tcp.chr.utils.BCrypt;

/**
 *  StoreController
 * 
 *  상점에 관한 요청을 처리하는 컨트롤러
 *  @date 2016.05.26
 *  @author genie
 *  @sicne 1.0
 */

@RestController
public class StoreController extends AbstractController {
	
	private static final Logger LOG = LoggerFactory.getLogger(StoreController.class);
	
	@Resource(name="storeService")
	private StoreService storeService;
	/**
	 * 추천 목록을 가져온다. 3개
	 * 
	 * @param req
	 * @param res
	 */
	@API(Useage.Experimental)
	@AccessRole(role = Role.selector)
	@RequestMapping(value = "/curations", method=RequestMethod.GET)
	public void getCuratingList(HttpServletRequest req, HttpServletResponse res){
		List curationList = storeService.getCurationList(req);
		sendResponse(curationList, res);
	}

}

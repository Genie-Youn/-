package com.tcp.chr.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *  AbstractController
 * 
 *  컨트롤러의 공통 속성을 묶어놓은 추상클래스
 *  @date 2016.05.26
 *  @author genie
 *  @sicne 1.0
 */
public class AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);
	
	/**
	 *  객체를 받아서 json 문자열로 변환한 뒤 응답한다.
	 *  cross-domain을 처리한다.
	 */
	public void sendResponse(Object res, HttpServletResponse response) {
		String json = new String();
		try {
			json = new ObjectMapper().writeValueAsString(res);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			LOG.error("json parse error");
			LOG.error(e.getStackTrace().toString());
		}
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getStackTrace().toString());
		}
	}
}

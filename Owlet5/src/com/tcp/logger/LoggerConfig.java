package com.tcp.logger;

import org.slf4j.LoggerFactory;


/**
 * Logger 기능이다.
 * 
 * 기본적으로 서버에서 활용될 engine 로그
 * web에서 활용될 web 로그
 * DB 쿼리처리시 활용될 DB 로그로 구분하여 등록하였음
 * 
 * 현재 Logger는 날짜별로 Setting 되지는 않음. 단, 서비스 시점에는 날짜별로 구분할 예정
 * 
 * 사용법은 예시로 AdminServlet 쪽에 몇개 만들어 놓았으니 보고 참고하면 됨.
 * 혹 추가로 Log를 만들고자 하는 분들은 소스보고 따라 하세용.
 * 
 * @author blessldk
 *
 */
public class LoggerConfig extends org.apache.log4j.Logger{

	protected LoggerConfig(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 서버 엔진 활동에 관한 로그
	 * @return
	 */
	public static org.slf4j.Logger getEngineLogger(){
		return LoggerFactory.getLogger("owlet.Engine");
	}
	
	/**
	 * 웹 관련 로그
	 * @return
	 */
	public static org.slf4j.Logger getWebLogger(){
		return LoggerFactory.getLogger("owlet.web");
	}
	
	/**
	 * DB쿼리 로그
	 * @return
	 */
	public static org.slf4j.Logger getDBLogger(){
		return LoggerFactory.getLogger("owlet.db");
	}
	
	/**
	 * File 저장 로그
	 * @return
	 */
	public static org.slf4j.Logger getFileLogger(){
		return LoggerFactory.getLogger("owlet.file");
	}
	
	//Logger 더 추가하고 싶으면 이 밑에다가 차례차례...
}

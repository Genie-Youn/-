package com.tcp.owlets.handler;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.CalendarInfo;
import com.tcp.owlets.bean.EventNoticeBean;
import com.tcp.owlets.dao.CalendarSqlMapper;

@Service
public class CalendarHandler {
	
	@Autowired
	CalendarSqlMapper calendarDao;

	public CalendarInfo insertCalendarInfo(CalendarInfo bean,int storeCode) {
		try{
			calendarDao.insertCalendar(bean, storeCode);
		}catch(Exception e){
				LoggerConfig.getDBLogger().debug(e.getMessage());
				
				/*catch 시에는 기존에 입력한 제목을 null 처리하자..
				* Client 에서 받아서 null로 Return 되면 입력오류 뭐 메세지 암꺼나 띄워....
				*/
				bean.setTitle(null);
		}
		return bean;
	}
	

	public CalendarInfo getCalendarInfo(int storeCode, int calID) {
		CalendarInfo bean = calendarDao.getCalendarInfo(storeCode, calID);
		bean.setContents(calendarDao.getContents(storeCode, calID));
		
		return bean;
	}
	
	public ArrayList<CalendarInfo> getAllCalendarInfo(int storeCode) {
		return calendarDao.getAllCalendarInfo(storeCode);
	}

	public void deleteCalendarInfo(int storeCode, int calID) {
		calendarDao.deleteCalendarInfo(storeCode, calID);
	}


	public boolean updateCalendarInfo(int storeCode, int calID, CalendarInfo bean) {
		return calendarDao.updateCalendar(storeCode,calID, bean);
	}
	
	public EventNoticeBean getEventInfo(int storeCode, int calID){
		return calendarDao.getType(storeCode, calID);
	}

}

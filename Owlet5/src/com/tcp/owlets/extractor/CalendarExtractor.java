package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tcp.owlets.bean.CalendarInfo;

public class CalendarExtractor implements ResultSetExtractor<CalendarInfo> {

	@Override
	public CalendarInfo extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		
		CalendarInfo bean = new CalendarInfo();
		
		bean.setnIndex(rs.getInt(1));
		bean.setTitle(rs.getString(2));
		bean.setStartDate(rs.getString(3));
		bean.setEndDate(rs.getString(4));
		bean.setUpdateDate(rs.getString(5));
		bean.setType(rs.getInt(6));
		bean.setPopupEnable(rs.getInt(7));
		bean.setAlarmEnable(rs.getInt(8));
		bean.setAlarmMethod(rs.getInt(9));
		bean.setAlarmClock(rs.getInt(10));
		bean.setAlarmPeriod(rs.getInt(11));		
		
		return bean;
	}
	

}

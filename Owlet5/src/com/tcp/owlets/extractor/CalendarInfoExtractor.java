package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tcp.owlets.bean.CalendarInfo;
import com.tcp.util.CalendarFactory;

public class CalendarInfoExtractor implements ResultSetExtractor<CalendarInfo> {

	@Override
	public CalendarInfo extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		CalendarInfo bean = new CalendarInfo();
		
		bean.setnIndex(rs.getInt("nIndex"));
		bean.setTitle(rs.getString("title"));
		bean.setStartDate(CalendarFactory.getInstance().setCustomDateTime(rs.getString("startDate") ,3));
		bean.setEndDate(CalendarFactory.getInstance().setCustomDateTime(rs.getString("endDate") ,3));
		bean.setPopupEnable(rs.getInt("popupEnable"));
		bean.setAlarmEnable(rs.getInt("alarmEnable"));
		bean.setAlarmPeriod(rs.getInt("alarmPeriod"));
		bean.setType(rs.getInt("type"));
		
		return bean;
	}
}

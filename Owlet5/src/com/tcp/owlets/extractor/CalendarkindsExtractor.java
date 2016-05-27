package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tcp.owlets.bean.CalendarInfo;

public class CalendarkindsExtractor implements ResultSetExtractor<CalendarInfo> {

	@Override
	public CalendarInfo extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		CalendarInfo bean = new CalendarInfo();
		
		bean.setnIndex(rs.getInt("nIndex"));
		bean.setType(rs.getInt("type"));
		bean.setContents(rs.getString("contents"));
		
		return bean;
	}

}

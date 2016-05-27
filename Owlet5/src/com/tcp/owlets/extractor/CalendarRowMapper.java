package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.CalendarInfo;

public class CalendarRowMapper implements RowMapper<CalendarInfo> {

	@Override
	public CalendarInfo mapRow(ResultSet rs, int index) throws SQLException {
		CalendarExtractor extractor = new CalendarExtractor();
		return extractor.extractData(rs);
	}

}

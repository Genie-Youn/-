package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.CalendarInfo;
import com.tcp.owlets.extractor.CalendarkindsExtractor;

public class CalendarkindsRowMapper implements RowMapper<CalendarInfo> {

	@Override
	public CalendarInfo mapRow(ResultSet rs, int arg1) throws SQLException {
		CalendarkindsExtractor extractor = new CalendarkindsExtractor();
		return extractor.extractData(rs);
	}
}

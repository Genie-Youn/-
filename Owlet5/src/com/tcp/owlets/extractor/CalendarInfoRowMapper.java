package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.CalendarInfo;
import com.tcp.owlets.extractor.CalendarInfoExtractor;

public class CalendarInfoRowMapper implements RowMapper<CalendarInfo> {

	@Override
	public CalendarInfo mapRow(ResultSet rs, int index)
			throws SQLException {
		CalendarInfoExtractor extractor = new CalendarInfoExtractor();
		return extractor.extractData(rs);
	}
}

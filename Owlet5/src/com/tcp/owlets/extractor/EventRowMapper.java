package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.EventNoticeBean;

public class EventRowMapper implements RowMapper<EventNoticeBean>{

	@Override
	public EventNoticeBean mapRow(ResultSet rs, int line) throws SQLException {
		// TODO Auto-generated method stub
		EventExtractor extractor = new EventExtractor();
		return extractor.extractData(rs);
	}
}
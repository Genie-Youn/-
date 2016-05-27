package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.EventNoticeBean;

public class NoticeRowMapper implements RowMapper<EventNoticeBean>{

	@Override
	public EventNoticeBean mapRow(ResultSet rs, int line) throws SQLException {
		// TODO Auto-generated method stub
		NoticeExtractor extractor = new NoticeExtractor();
		return extractor.extractData(rs);
	}

}

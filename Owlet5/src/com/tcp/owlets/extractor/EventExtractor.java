package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tcp.owlets.bean.EventNoticeBean;

public class EventExtractor implements ResultSetExtractor<EventNoticeBean>{

	@Override
	public EventNoticeBean extractData(ResultSet rs) throws SQLException,
	DataAccessException {
		EventNoticeBean bean = new EventNoticeBean();
		try{
			bean.setnIndex(rs.getInt(1));
			bean.setName(rs.getString(2));
			bean.setContext(rs.getString(3));
			bean.setShowStartDate(rs.getString(4));
			bean.setShowEndDate(rs.getString(5));
			bean.setType(rs.getString(6));
			
			return bean;
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return bean;
	}
}

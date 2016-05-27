package com.tcp.owlets.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tcp.owlets.bean.EventNoticeBean;
import com.tcp.owlets.extractor.NoticeRowMapper;

@Repository
public class NoticeSqlMapper {
	@Autowired
	DataSource dataSource;
	
	
	public ArrayList<EventNoticeBean> getNoticeList(int index) {
		List<EventNoticeBean> list = new ArrayList<EventNoticeBean>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("Select no.nIndex , no.name , no.content , cal.startDate , cal.endDate ");
		sql.append("from notices no , calendarInfos cal ");
		sql.append("where no.nIndex in (");
		sql.append(" select nIndex from calendar_store_maps ");
		sql.append(" where code = ");sql.append(index);
		sql.append(") ");
		sql.append(" AND (no.nIndex=cal.nIndex) ");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		list = jdbcTemplate.query(sql.toString(), new NoticeRowMapper());
		
		return (ArrayList<EventNoticeBean>) list;
	}

}

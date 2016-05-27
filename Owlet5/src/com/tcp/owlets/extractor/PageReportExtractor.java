package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tcp.owlets.bean.PageInfoBean;

public class PageReportExtractor implements ResultSetExtractor<PageInfoBean>{

	@Override
	public PageInfoBean extractData(ResultSet rs) throws SQLException,
	DataAccessException {
		PageInfoBean bean = new PageInfoBean();
		bean.setPageIndex(rs.getInt(1));
		bean.setPageName(rs.getString(2));
		bean.setVisitCount(rs.getInt(3));
		bean.setTime(rs.getString(4));
		
		return bean;
	}


}

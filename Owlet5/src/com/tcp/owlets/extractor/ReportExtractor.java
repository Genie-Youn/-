package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tcp.owlets.bean.ReportBean;

public class ReportExtractor implements ResultSetExtractor<ReportBean> {

	@Override
	public ReportBean extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		ReportBean bean = new ReportBean();
		bean.setnIndex(rs.getInt(1));
		bean.setDownCount(rs.getInt(2));
		bean.setVisitCount(rs.getInt(3));
		bean.setVisitorCount(rs.getInt(4));
		bean.setDate(rs.getString(5));
		
		return bean;
	}

}

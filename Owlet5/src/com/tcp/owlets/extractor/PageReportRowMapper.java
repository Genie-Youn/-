package com.tcp.owlets.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tcp.owlets.bean.PageInfoBean;

public class PageReportRowMapper implements RowMapper<PageInfoBean>{

	@Override
	public PageInfoBean mapRow(ResultSet rs, int line) throws SQLException {
		PageReportExtractor extractor = new PageReportExtractor();
		return extractor.extractData(rs);
	}

}
